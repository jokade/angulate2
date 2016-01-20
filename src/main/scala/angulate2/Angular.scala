//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import scala.annotation.StaticAnnotation
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

object Angular {
  import scala.reflect.macros.blackbox
  import scala.language.experimental.macros

  @inline final implicit class RichAngular(val self: Angular) extends AnyVal {

    @inline def bootstrapWith[T] : Unit = macro Macros.bootstrapWith[T]

    @inline def register[T]: Unit = macro Macros.register[T]
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

    def register[T: c.WeakTypeTag] = {
      val name = weakTypeOf[T].typeSymbol.fullName
      val t = selectGlobalDynamic(name)
      val obj = selectGlobalDynamic(name+"_")
      val res = q"""{$t.annotations = $obj().annotations()
           ()
          }"""
      res
    }

    private def registerAll(annottees: Map[String,Any]) =
      annottees.toSeq.map{
        case (_,tree:Tree) => tree
      }

    def jsClassOf[T: c.WeakTypeTag] = selectGlobalDynamic[T]

    // TODO: should we use this instead of jsClassOf?
    def jsClassArray[T: c.WeakTypeTag] =
      q"scalajs.js.Array(${selectGlobalDynamic[T]})"
  }

  /**
   * Internally used to mark classes with companion objects that carry Angular annotations
   */
  class AngulateAnnotated(annotation: String) extends StaticAnnotation
}

@JSName("angular")
@js.native
trait Angular extends js.Object {

  def bootstrap(appComponentType: js.Object) : Unit = js.native

  def bootstrap(appComponentType: js.Dynamic) : Unit = js.native

  //--------------- DIRECTIVES -----------------
  def NgFor: js.Object = js.native
  def NgIf: js.Object = js.native

}