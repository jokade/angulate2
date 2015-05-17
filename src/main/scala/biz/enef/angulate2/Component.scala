// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import biz.enef.smacrotools.WhiteboxMacroTools

import scala.annotation.{compileTimeOnly, StaticAnnotation}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}


object Component {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._

    val annotationParamNames = Seq("selector")

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val (name,params,parents,body) = extractClassParts(classDecl)
      val fullName = getEnclosingNamespace().map( ns => s"$ns.$name" ).getOrElse(name.toString)
      val annotationParams = extractAnnotationParameters(c.prefix.tree,annotationParamNames).
        collect({
        case (name,Some(rhs)) => q"${TermName(name)} = $rhs"
      })

      val tree =
      /*q"""{@scalajs.js.annotation.JSExport($fullName)
           class $name ( ..$params ) extends ..$parents { ..$body }
           object ${name.toTermName} {
             def _fn = js.Dynamic.global.${TermName(fullName)}
             def _register() : Unit = {
               import biz.enef.angulate2.annotations._
               _fn.annotations = js.Array(
                 ComponentAnnotation(..$annotationParams)
               )
               ()
             }
           }
          }"""*/
        q"""{@scalajs.js.annotation.JSExport($fullName)
         class $name ( ..$params ) extends ..$parents with biz.enef.angulate2.Angular.Annotated { ..$body }
         object ${name.toTermName} extends biz.enef.angulate2.Angular.Annotated {
           import biz.enef.angulate2.annotations._
           def _annotations = js.Array(
             ComponentAnnotation( ..$annotationParams )
           )
         }
        }"""

      println(tree)

      c.Expr[Any](tree)
    }
  }

}
