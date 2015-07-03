//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import scala.annotation.StaticAnnotation
import scala.scalajs.js

object Angular {
  import scala.reflect.macros.blackbox
  import scala.language.experimental.macros

  @inline final implicit class RichAngular(val self: Angular) extends AnyVal {

    @inline def bootstrapWith[T] : Unit = macro Macros.bootstrapWith[T]

  }


  private[angulate2] class Macros(val c: blackbox.Context) extends JsBlackboxMacroTools {
    import c.universe._

    def bootstrapWith[T: c.WeakTypeTag] = {
      val t = selectGlobalDynamic[T]
      val angular = Select(c.prefix.tree, TermName("self"))
      val r =
        q"""$angular.bootstrap($t)"""
      r
    }

    private def registerAll(annottees: Map[String,Any]) =
      annottees.toSeq.map{
        case (_,tree:Tree) => tree
      }

    def jsClassOf[T: c.WeakTypeTag] = selectGlobalDynamic[T]
  }

  /**
   * Internally used to mark classes with companion objects that carry Angular annotations
   */
  class AngulateAnnotated(annotation: String) extends StaticAnnotation
}

trait Angular extends js.Object {

  def bootstrap(appComponentType: js.Object) : Unit = js.native

  def bootstrap(appComponentType: js.Dynamic) : Unit = js.native

  //--------------- DIRECTIVES -----------------
  def For: js.Object = js.native
  def If: js.Object = js.native
}