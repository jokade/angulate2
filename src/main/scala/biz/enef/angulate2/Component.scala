// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import biz.enef.smacrotools.WhiteboxMacroTools

import scala.annotation.{compileTimeOnly, StaticAnnotation}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String,
                template: String = null,
                directives: js.Array[Any] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}


object Component {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._

    val annotationParamNames =
      Seq("selector",
          "template",
          "directives")

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val (name,params,parents,body) = extractClassParts(classDecl)
      val fullName = getEnclosingNamespace().map( ns => s"$ns.$name" ).getOrElse(name.toString)
      val annots = annotations( extractAnnotationParameters(c.prefix.tree,annotationParamNames) )

      val tree =
        q"""{@scalajs.js.annotation.JSExport($fullName) @scalajs.js.annotation.JSExportAll
         class $name ( ..$params ) extends ..$parents with biz.enef.angulate2.Angular.Annotated { ..$body }
         object ${name.toTermName} extends biz.enef.angulate2.Angular.Annotated {
           import biz.enef.angulate2.annotations._
           @inline final def _annotations = $annots
         }
        }"""

      println(tree)

      c.Expr[Any](tree)
    }

    def annotations(params: Map[String,Option[Tree]]) = {
      val groups = params.collect {
        case (name, Some(rhs)) => (name, rhs)
      }.groupBy {
        case ("selector", _) => "ComponentAnnotation"
        case ("template"|"directives", _) => "ViewAnnotation"
        case _ => ???
      }.map{
        case (atype,m) =>
          val annot = TermName(atype)
          val params = m.toList.map( p => q"${TermName(p._1)} = ${p._2}")
          q"$annot( ..$params )"
      }

      q"scalajs.js.Array( ..$groups )"
    }
  }


}
