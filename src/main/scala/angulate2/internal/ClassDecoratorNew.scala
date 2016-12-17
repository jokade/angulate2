//     Project: angulate2
// Description: Macro base for classes decorated with TypeScript annotations

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import angulate2.ext.ClassMode
import de.surfice.smacrotools.MacroAnnotationHandlerNew

import scala.language.reflectiveCalls

abstract class ClassDecoratorNew extends MacroAnnotationHandlerNew
  with AngulateWhiteboxMacroTools {

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
                                classMode: ClassMode.Value,
                                sjsxStatic: Seq[String] = Nil
                               )
  object ClassDecoratorData {
    def apply(data: Data): ClassDecoratorData = data("decoratorData").asInstanceOf[ClassDecoratorData]
//    def update(data: Data, f: ClassDecoratorData=>ClassDecoratorData): Data = data + ("decoratorData" -> f(ClassDecoratorData(data)))
    def update(data: Data, cdd: ClassDecoratorData): Data = data + ("decoratorData" -> cdd)
    def addSjsxStatic(data: Data, js: Seq[String]): Data = {
      val cdd = ClassDecoratorData(data)
      update(data,cdd.copy(sjsxStatic = cdd.sjsxStatic++js))
    }
  }

  // Prefix for accessing the Scala module's exports object from within the sjsx module
  protected val exports = "$s"


  def mainAnnotationObject: Tree

  def annotationParamNames: Seq[String]


  override def analyze: Analysis = super.analyze andThen {
    case (origParts:ClassParts,data) =>
      (origParts,initClassDecoratorData(origParts,data))
    case default => default
  }


  override def transform: Transformation = commonTransform andThen {
    // modify annotations for the class carrying the macro annotation
    case cls: ClassTransformData =>
      val classDecoratorData = ClassDecoratorData(cls.data)
      // assemble JS for class decoration
      val js = classDecoratorData.sjsxStatic.mkString("","\n","\n") + genClassDecoration(cls.modParts,classDecoratorData)
      cls.addAnnotations(
        q"new scalajs.js.annotation.JSExport(${cls.modParts.fullName})",
        q"new sjsx.SJSXStatic(1000,$js)"
      )
    case obj: ObjectTransformData =>
      val classDecoratorData = ClassDecoratorData(obj.data)
      import classDecoratorData._
      obj
        .addAnnotations(
          q"new scalajs.js.annotation.JSExport($objName)"
        )
        .addStatements(
          q"""val _decorators = scalajs.js.Array( ..$decorators )"""
        )
    case default => default
  }

  private val typeSeqJsObject = Seq(tq"scalajs.js.Object")

  private def commonTransform: Transformation = { tdata =>
    val classDecoratorData = ClassDecoratorData(tdata.data)
    import classDecoratorData._
    tdata
      .addAnnotations( classModeAnnotation(classMode) )
      .updParents(
        if(classMode==ClassMode.JS) tdata.modParts.parents match {
          case Nil => typeSeqJsObject
          case Seq(x) if x.toString == "scala.AnyRef" => typeSeqJsObject
          case x => x
        }
        else tdata.modParts.parents )
  }

  private def classModeAnnotation(classMode: ClassMode.Value) = classMode match {
    case ClassMode.Scala => q"new scalajs.js.annotation.JSExportAll"
    case ClassMode.JS => q"new scalajs.js.annotation.ScalaJSDefined"
  }


  def decoratorParameters(parts: ClassParts, annotationParamNames: Seq[String]) =
    extractAnnotationParameters(c.prefix.tree, annotationParamNames).collect {
      case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
    }

  /**
   * Assemble initial ClassDecoratorData
   */
  private def initClassDecoratorData(parts: ClassParts, data: Data): Data = {
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

  /**
   * Generate the JS string used to decorate the class.
   *
   * @param parts
   * @param data
   * @return
   */
  private def genClassDecoration(parts: ClassParts, data: ClassDecoratorData): String = {
    import data._
    import parts._

    val decoration =
      if(metadata.isEmpty) s"$exports.$objName()._decorators"
      else s"$exports.$objName()._decorators.concat(" + metadata.map(p => s"__metadata('${p._1}',${p._2})").mkString("[",",","]") + ")"
    s"$exports.$fullName = __decorate($decoration,$exports.$fullName);"
  }
}

