//     Project: angulate2
// Description: Macro base for classes decorated with TypeScript annotations

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import scala.language.reflectiveCalls

abstract class DecoratedClass extends JsWhiteboxMacroTools {
  import c.universe._

  // Prefix for accessing the Scala module's exports object from within the sjsx module
  private val exports = "$s"

  def mainAnnotation: String

  def mainAnnotationObject: Tree

  def annotationParamNames: Seq[String]

  def impl(annottees: c.Expr[Any]*): c.Expr[Any] = annottees.map(_.tree).toList match {
    case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
    case _ => c.abort(c.enclosingPosition, s"Invalid annottee for @$mainAnnotation")
  }

  def modifiedDeclaration(classDecl: ClassDef) = {
    val parts = extractClassParts(classDecl)

    import parts._

    // load debug annotation values (returns default config, if there is no @debug on this component)
    implicit val debug = getDebugConfig(modifiers)

    val decoratorParams = decoratorParameters(parts,annotationParamNames)

    val annotation =
      if(decoratorParams.isEmpty)
        q"$mainAnnotationObject()"
      else
        q"$mainAnnotationObject( scalajs.js.Dynamic.literal(..$decoratorParams) )"

    val diTypes = getDINames(params) map ("$s."+_)

    val metadata =
      if(diTypes.isEmpty) Map.empty[String,String]
      else Map("design:paramtypes"->diTypes.mkString("[",",","]"))

    val tree = makeDecoratedClass(parts,q"..$annotation",metadata,s"created $mainAnnotation $fullName")

    c.Expr[Any](tree)
  }


  def decoratorParameters(parts: ClassParts, annotationParamNames: Seq[String]) =
    extractAnnotationParameters(c.prefix.tree, annotationParamNames).collect {
      case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
    }


  def makeDecoratedClass(parts: ClassParts, decorators: Tree, metadata: Map[String,String], debugMsg: String)(implicit debug: de.surfice.smacrotools.debug.DebugConfig): Tree = {
    import parts._
    val objName = fullName + "_"
    val decoration =
      if(metadata.isEmpty) s"$exports.$objName().decorators"
      else s"$exports.$objName().decorators.concat(" + metadata.map(p => s"__metadata('${p._1}',${p._2})").mkString("[",",","]") + ")"
    val annotation = s"$exports.$fullName = __decorate($decoration,$exports.$fullName)"
    val base = getJSBaseClass(parents)
    val log =
      if(debug.logInstances)
        q"""scalajs.js.Dynamic.global.console.debug($debugMsg,this)"""
      else q""

    val tree = q"""@scalajs.js.annotation.JSExport($fullName)
                   @scalajs.js.annotation.ScalaJSDefined
                   @sjsx.SJSXStatic(1000, $annotation )
                   class $name ( ..$params ) extends ..$base { ..$body; $log }
                   @scalajs.js.annotation.JSExport($objName)
                   @scalajs.js.annotation.ScalaJSDefined
                   object ${name.toTermName} extends scalajs.js.Object {
                     val decorators = scalajs.js.Array( ..$decorators )
                   }"""

    if(debug.showExpansion) printTree(tree)

    tree
  }

}
