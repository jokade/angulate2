//     Project: angulate2
// Description: Angulate2 extension for definition of data objects/classes via an @Data annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

@compileTimeOnly("enable macro paradise to expand macro annotations")
class Data extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Data.Macro.impl
}

object Data {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {

    import c.universe._

    lazy val debug = isSet("angulate2.debug.Data")

    //    val annotationParamNames = Seq(
    //      "styles")

    def impl(annottees: c.Expr[Any]*): c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Data")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)
      import parts._

      val members = params map {
        case q"$mods val $name: $tpe = $rhs" => ("val",name,tpe)
        case q"$_ var $name: $tpe = $_" => ("var",name,tpe)
      }
      val bodyMembers = members map {
        case ("val",name,tpe) => q"val $name: $tpe = scalajs.js.native"
        case ("var",name,tpe) => q"var $name: $tpe = scalajs.js.native"
      }
      val args = members map ( p => q"${p._2}: ${p._3}" )
      val literalArgs = members map ( p => q"${p._2} = ${p._2}" )


      val tree =
        if(isCase) {
          q"""{@scalajs.js.native
              trait $name extends scalajs.js.Object {
              ..$bodyMembers
              }
              object ${name.toTermName} {
                def apply(..$args) = scalajs.js.Dynamic.literal(..$literalArgs).asInstanceOf[$name]
              }
              }"""
        } else c.abort(c.enclosingPosition,"@Data may only be used on case classes")

      if(debug) printTree(tree)

      c.Expr[Any](tree)
    }
  }

}

