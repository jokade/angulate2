// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade trait and rich wrapper for the Angular2 angular object
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import biz.enef.smacrotools.BlackboxMacroTools

import scala.scalajs.js

trait Angular extends js.Object {
  def bootstrap(appComponentType: js.Object) : Unit = js.native

  def bootstrap(appComponentType: js.Dynamic) : Unit = js.native
}

object Angular {
  import scala.reflect.macros.blackbox
  import scala.language.experimental.macros

  @inline final implicit class RichAngular(val self: Angular) extends AnyVal {

    @inline def bootstrapWith[T] : Unit = macro Macros.bootstrapWith[T]

    @inline def register[T<:Annotated] : Unit = macro Macros.register[T]
  }

  private[angulate2] class Macros(val c: blackbox.Context) extends JsBlackboxMacroTools {
    import c.universe._

    def bootstrapWith[T: c.WeakTypeTag] = {
      val t = selectGlobalDynamic[T]
      val angular = Select(c.prefix.tree, TermName("self"))
      //val r = c.typecheck(q"""$angular.bootstrap($t)""")
      val r = q"""$angular.bootstrap($t)"""
      //println(r)
      r
    }

    def register[T: c.WeakTypeTag] = {
      val t = weakTypeOf[T].companion
      val x = t.decls.filter(_.name.toString == "_annotations").head
      val d = selectGlobalDynamic[T]
      val tree = q"""$d.updateDynamic("annotations")($x)"""
      tree
    }
  }

  /**
   * Internally used trait to mark classes with companion objects that carry Angular annotations
   */
  trait Annotated
}

