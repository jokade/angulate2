//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
import angulate2.internal.{JsBlackboxMacroTools, JSType}

import scala.language.experimental.macros
import scala.scalajs.js
import scala.reflect.macros.blackbox

package object angulate2 {

  def @#[T]: js.Any = macro Macros.jsRef[T]
  def @@[T1] : js.Array[js.Any] = macro Macros.jsRefArray1[T1]
  def @@[T1,T2] : js.Array[js.Any] = macro Macros.jsRefArray2[T1,T2]
  def @@[T1,T2,T3] : js.Array[js.Any] = macro Macros.jsRefArray3[T1,T2,T3]
  def @@[T1,T2,T3,T4] : js.Array[js.Any] = macro Macros.jsRefArray4[T1,T2,T3,T4]
  def @@[T1,T2,T3,T4,T5] : js.Array[js.Any] = macro Macros.jsRefArray5[T1,T2,T3,T4,T5]
  def @@[T1,T2,T3,T4,T5,T6] : js.Array[js.Any] = macro Macros.jsRefArray6[T1,T2,T3,T4,T5,T6]
  def @@[T1,T2,T3,T4,T5,T6,T7] : js.Array[js.Any] = macro Macros.jsRefArray7[T1,T2,T3,T4,T5,T6,T7]
  def @@[T1,T2,T3,T4,T5,T6,T7,T8] : js.Array[js.Any] = macro Macros.jsRefArray8[T1,T2,T3,T4,T5,T6,T7,T8]
  def @@[T1,T2,T3,T4,T5,T6,T7,T8,T9] : js.Array[js.Any] = macro Macros.jsRefArray9[T1,T2,T3,T4,T5,T6,T7,T8,T9]

  def @@(items: String*) : js.Array[String] = js.Array(items:_*)

}

package angulate2 {
  private[angulate2] class Macros(val c: blackbox.Context) extends JsBlackboxMacroTools {
    import c.universe._

    def bootstrapWith[T: c.WeakTypeTag] = {
      val t = selectGlobalDynamic[T]
      val r =
        q"""angulate2.es5.ng.platform.browser.bootstrap($t)"""
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


    private def findJSRef(symbol: Symbol) = findAnnotations(symbol).collectFirst{
      case ("scala.scalajs.js.annotation.JSExport",_) => selectExported(symbol.fullName)
      case ("scala.scalajs.js.annotation.JSImport",a) => q"scalajs.runtime.constructorOf(classOf[$symbol])"
    }

    def jsRef[T: c.WeakTypeTag]: Tree = findJSRef(weakTypeOf[T].typeSymbol)
      .getOrElse{error(s"Cannot get JS reference for type ${weakTypeOf[T].typeSymbol.fullName}");q""}

    def jsRefArray1[T: c.WeakTypeTag] = q"scalajs.js.Array(${jsRef[T]})"

    def jsRefArray2[T1: c.WeakTypeTag, T2: c.WeakTypeTag] = q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]})"

    def jsRefArray3[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]})"

    def jsRefArray4[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]})"

    def jsRefArray5[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]})"

    def jsRefArray6[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
                    T6: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]})"

    def jsRefArray7[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
                    T6: c.WeakTypeTag, T7: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]})"

    def jsRefArray8[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
    T6: c.WeakTypeTag, T7: c.WeakTypeTag, T8: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]},${jsRef[T8]})"

    def jsRefArray9[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
    T6: c.WeakTypeTag, T7: c.WeakTypeTag, T8: c.WeakTypeTag, T9: c.WeakTypeTag] =
      q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]},${jsRef[T8]},${jsRef[T9]})"
  }
}
