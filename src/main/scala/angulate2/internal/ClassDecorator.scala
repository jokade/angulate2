//     Project: angulate2
// Description: Macro base for classes decorated with TypeScript annotations

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import angulate2.ext.ClassMode
import de.surfice.smacrotools.MacroAnnotationHandler

import scala.language.reflectiveCalls

abstract class ClassDecorator extends MacroAnnotationHandler with AngulateWhiteboxMacroTools {
  import c.universe._

  override def supportsClasses: Boolean = true
  override def supportsTraits: Boolean = false
  override def supportsObjects: Boolean = false
  override def createCompanion: Boolean = true

  type Metadata = Map[String,String]
  case class ClassDecoratorData(objName: String,
                                decorators: Seq[Tree],
                                metadata: Metadata,
                                userDefinedCompanion: Boolean,
                                classMode: ClassMode.Value
                               )
  object ClassDecoratorData {
    def apply(data: Data): ClassDecoratorData = data("decoratorData").asInstanceOf[ClassDecoratorData]
  }

  // Prefix for accessing the Scala module's exports object from within the sjsx module
  private val exports = "$s"


  def mainAnnotationObject: Tree

  def annotationParamNames: Seq[String]

  override def modifiedClassDef(classParts: ClassParts, data: Data): (c.universe.Tree, Data) = {
    super.modifiedClassDef(classParts, decoratorData(classParts,data))
  }


  override def modifiedAnnotations(parts: CommonParts, data: Data): (List[c.universe.Tree], Data) = {
    val decoratorData = ClassDecoratorData(data)
    import decoratorData._
    import parts._
    val classModeAnnotation = classMode match {
      case ClassMode.Scala => q"new scalajs.js.annotation.JSExportAll"
      case ClassMode.JS => q"new scalajs.js.annotation.ScalaJSDefined"
    }
    parts match {
      // modify annotations for the class carrying the macro annotation
      case parts: ClassParts =>
        // assemble JS for class decoration
        val decoration =
          if(metadata.isEmpty) s"$exports.$objName()._decorators"
          else s"$exports.$objName()._decorators.concat(" + metadata.map(p => s"__metadata('${p._1}',${p._2})").mkString("[",",","]") + ")"
        val js = s"$exports.$fullName = __decorate($decoration,$exports.$fullName);"
        (parts.modifiers.annotations ++ Seq(
          classModeAnnotation,
          q"new scalajs.js.annotation.JSExport(${parts.fullName})",
          q"new sjsx.SJSXStatic(1000,$js)"
        ),data)
      // modify annotations for the companion object
      case parts: ObjectParts =>
        (parts.modifiers.annotations ++ Seq(
          classModeAnnotation,
          q"new scalajs.js.annotation.JSExport($objName)"
        ),data)
      case _ =>
        super.modifiedAnnotations(parts,data)
    }
  }

  private def extendFromJSObject(parents: Seq[Tree]): Seq[Tree] = {
        tq"scalajs.js.Object" +: parents.filter(_.toString != "scala.AnyRef")
//    parents
  }

  override def modifiedParents(parts: CommonParts, data: Data): (Seq[Tree],Data) = {
    val decoratorData = ClassDecoratorData(data)
    import decoratorData._
    parts match {
      case parts: ObjectParts =>
        if(userDefinedCompanion || classMode==ClassMode.Scala) (parts.parents,data)
        else (Seq(tq"scalajs.js.Object"),data)
      case parts: ClassParts =>
        if(classMode==ClassMode.JS)
          (extendFromJSObject(parts.parents),data)
        else
          (parts.parents,data)
      case _ =>
        super.modifiedParents(parts,data)
    }
  }

  override def modifiedBody(parts: CommonParts, data: Data): (Iterable[c.universe.Tree], Data) =
    if(parts.isObject) {
      val decoratorData = data("decoratorData").asInstanceOf[ClassDecoratorData]
      import decoratorData._
      val stats = q"""val _decorators = scalajs.js.Array( ..$decorators )"""
      (parts.body :+ stats,data)
    }
    else
      super.modifiedBody(parts, data)

  def decoratorParameters(parts: ClassParts, annotationParamNames: Seq[String]) =
    extractAnnotationParameters(c.prefix.tree, annotationParamNames).collect {
      case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
    }

  private def decoratorData(parts: ClassParts,data: Data): Data = {
    import parts._

    val objName = fullName + "_"
    val mainAnnotationParams = decoratorParameters(parts,annotationParamNames)
    val mainAnnotation =
      if(mainAnnotationParams.isEmpty)
        q"$mainAnnotationObject()"
      else
        q"$mainAnnotationObject( scalajs.js.Dynamic.literal(..$mainAnnotationParams) )"

    val diTypes = getInjectionDependencies(params) map {
      case ScalaDependency(fqn) => s"$exports.$fqn"
      case RequireDependency(module,name) => s"require('$module').$name"
    }

    // determine class mode
    // (default: Scala; with @classModeJS => JS)
    val classMode =
      if(findAnnotation(modifiers.annotations,"classModeJS").isDefined) ClassMode.JS
      else ClassMode.Scala

    val metadata: Metadata =
      if(diTypes.isEmpty) Map.empty[String,String]
      else Map("design:paramtypes"->diTypes.mkString("[",",","]"))

    data + ("decoratorData"->ClassDecoratorData(
      objName,
      Seq(mainAnnotation),
      metadata,
      companion.isDefined,
      classMode
    ))
  }

}
