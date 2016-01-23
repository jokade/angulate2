//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

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

      val base = getJSBaseClass(parents)

      val tree =
        q"""{@scalajs.js.annotation.JSExport($fullName)
             @scalajs.js.annotation.ScalaJSDefined
             class $name ( ..$params ) extends ..$base { ..$body }
            }"""

      c.Expr(tree)
    }
  }
}
