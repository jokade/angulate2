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

  //--------------- DIRECTIVES -----------------
  def For: js.Object = js.native
  def If: js.Object = js.native
}

object Angular {
  import scala.reflect.macros.blackbox
  import scala.language.experimental.macros

  @inline final implicit class RichAngular(val self: Angular) extends AnyVal {

    @inline def bootstrapWith[T] : Unit = macro Macros.bootstrapWith[T]

  }

  private[angulate2] class Macros(val c: blackbox.Context) extends JsBlackboxMacroTools {
    import c.universe._

    def bootstrapWith[T: c.WeakTypeTag] = {
      val annots = registerAll(CompileTimeRegistry.components)
      val t = selectGlobalDynamic[T]
      val angular = Select(c.prefix.tree, TermName("self"))
      val r =
        q"""{import biz.enef.angulate2.annotations._
             ..$annots
             }
            $angular.bootstrap($t)"""
      println(r)
      r
    }

    private def registerAll(annottees: Map[String,Any]) =
      annottees.toSeq.map{
        case (_,tree:Tree) => tree
      }
  }

  /**
   * Internally used to mark classes with companion objects that carry Angular annotations
   */
  trait Annotated
}

