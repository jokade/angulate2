//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angulate2 extension for definition of data objects/classes via an @Data annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext

import angulate2.internal.JsWhiteboxMacroTools

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

/**
 * Annotation for case classes to mark them as a pure JavaScript data object.
 *
 * @example
 * {{{
 * @Data
 * case class Foo(id: Int, var bar: String)
 * }}}
 * is expanded to
 * {{{
 * @js.native
 * trait Foo extends js.Object {
 *   val id: Int = js.native
 *   var bar: String = js.native
 * }
 *
 * object Foo {
 *   def apply(id: Int, bar: String): Foo =
 *     js.Dynamic.literal(id = id, bar = bar).asInstanceOf[Foo]
 * }
 * }}}
 */
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Data extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Data.Macro.impl
}

object Data {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {

    import c.universe._

    def impl(annottees: c.Expr[Any]*): c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Data")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)
      import parts._

      val debug = getDebugConfig(modifiers)

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

      val log =
        if(debug.logInstances) {
          val msg = s"created Data object $fullName:"
          q"""scalajs.js.Dynamic.global.console.debug($msg,this)"""
        }
        else q""

      val tree =
        if(isCase) {
          q"""{@scalajs.js.native
              trait $name extends scalajs.js.Object {
              ..$bodyMembers
              }
              object ${name.toTermName} {
                def apply(..$args) = {$log;scalajs.js.Dynamic.literal(..$literalArgs).asInstanceOf[$name]}
              }
              }"""
        } else c.abort(c.enclosingPosition,"@Data may only be used on case classes")

      if(debug.showExpansion) printTree(tree)

      c.Expr[Any](tree)
    }
  }

}

