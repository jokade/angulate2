//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.internal.{AngulateBlackboxMacroTools, JSType}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import scala.scalajs.js

object ops extends OpsTrait

private[angulate2] trait OpsTrait {

  import js.JSConverters._

  def %%[T]: JSType = macro OpsMacros.jsType[T]

  def @@[T1]: js.Array[js.Any] = macro OpsMacros.jsRefArray1[T1]

  def @@[T1, T2]: js.Array[js.Any] = macro OpsMacros.jsRefArray2[T1, T2]

  def @@[T1, T2, T3]: js.Array[js.Any] = macro OpsMacros.jsRefArray3[T1, T2, T3]

  def @@[T1, T2, T3, T4]: js.Array[js.Any] = macro OpsMacros.jsRefArray4[T1, T2, T3, T4]

  def @@[T1, T2, T3, T4, T5]: js.Array[js.Any] = macro OpsMacros.jsRefArray5[T1, T2, T3, T4, T5]

  def @@[T1, T2, T3, T4, T5, T6]: js.Array[js.Any] = macro OpsMacros.jsRefArray6[T1, T2, T3, T4, T5, T6]

  def @@[T1, T2, T3, T4, T5, T6, T7]: js.Array[js.Any] = macro OpsMacros.jsRefArray7[T1, T2, T3, T4, T5, T6, T7]

  def @@[T1, T2, T3, T4, T5, T6, T7, T8]: js.Array[js.Any] = macro OpsMacros.jsRefArray8[T1, T2, T3, T4, T5, T6, T7, T8]

  def @@[T1, T2, T3, T4, T5, T6, T7, T8, T9]: js.Array[js.Any] = macro OpsMacros.jsRefArray9[T1, T2, T3, T4, T5, T6, T7, T8, T9]

  def @@[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10]: js.Array[js.Any] = macro OpsMacros.jsRefArray10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10]

  def @@[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11]: js.Array[js.Any] = macro OpsMacros.jsRefArray11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11]

  def @@[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12]: js.Array[js.Any] = macro OpsMacros.jsRefArray12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12]

  def @@@[T](items: T*): js.Array[T] = items.toJSArray

  val %%% = js.Dynamic.literal

  def $compPath: String = macro OpsMacros.compPath


}

private[angulate2] class OpsMacros(val c: blackbox.Context) extends AngulateBlackboxMacroTools {
  import c.universe._


  private def findJSRef(symbol: Symbol) = findAnnotations(symbol).collectFirst{
    case ("scala.scalajs.js.annotation.JSExport",_) => selectExported(symbol.fullName)
    case ("scala.scalajs.js.annotation.JSImport",a) => q"scalajs.runtime.constructorOf(classOf[$symbol])"
  }

  def jsType[T: c.WeakTypeTag]: Tree = findJSRef(weakTypeOf[T].typeSymbol) match {
    case Some(t) => q"$t.asInstanceOf[angulate2.internal.JSType]"
    case None =>
      error(s"Cannot get JS reference for type ${weakTypeOf[T].typeSymbol.fullName}; maybe you forgot @JSExport?")
      q""
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

  def jsRefArray10[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
  T6: c.WeakTypeTag, T7: c.WeakTypeTag, T8: c.WeakTypeTag, T9: c.WeakTypeTag, T10: c.WeakTypeTag] =
    q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]},${jsRef[T8]},${jsRef[T9]},${jsRef[T10]})"

  def jsRefArray11[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
  T6: c.WeakTypeTag, T7: c.WeakTypeTag, T8: c.WeakTypeTag, T9: c.WeakTypeTag, T10: c.WeakTypeTag, T11: c.WeakTypeTag] =
    q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]},${jsRef[T8]},${jsRef[T9]},${jsRef[T10]},${jsRef[T11]})"

  def jsRefArray12[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag, T5: c.WeakTypeTag,
  T6: c.WeakTypeTag, T7: c.WeakTypeTag, T8: c.WeakTypeTag, T9: c.WeakTypeTag, T10: c.WeakTypeTag, T11: c.WeakTypeTag,
  T12: c.WeakTypeTag] =
    q"scalajs.js.Array(${jsRef[T1]},${jsRef[T2]},${jsRef[T3]},${jsRef[T4]},${jsRef[T5]},${jsRef[T6]},${jsRef[T7]},${jsRef[T8]},${jsRef[T9]},${jsRef[T10]},${jsRef[T11]},${jsRef[T12]})"

  def compPath = Literal(Constant(
    getEnclosingNamespace().getOrElse("").stripSuffix("._decorators").replaceAll("\\.","/") ))

  def assignDynamic(d: c.Expr[Any]) = c.prefix match {
    case Expr(Apply(_,List(target))) =>
      val (base,name) = {
        val res = target.toString.split('.')
        (TermName(res.init.mkString(".")),TermName(res.last))
      }
      val tree = q"{val dyn = $base.asInstanceOf[scalajs.js.Dynamic]; dyn.$name = $d}"
      c.Expr[Unit](tree)
  }
}

