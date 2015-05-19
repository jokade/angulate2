// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: angulate2 implementation of Angular's @Injectable annotation
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

class Injectable extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Injectable.Macro.impl
}


object Injectable {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {

    import c.universe._

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedClassDecl(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Injectable")
    }

    def modifiedClassDecl(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)
      import parts._

      val tree =
        q"""{@scalajs.js.annotation.JSExport($fullName)
             @scalajs.js.annotation.JSExportAll
             class $name ( ..$params ) extends ..$parents { ..$body }
            }"""

      c.Expr(tree)
    }
  }
}
