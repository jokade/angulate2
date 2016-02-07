//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
import scala.language.experimental.macros
import scala.scalajs.js

package object angulate2 {

  @js.native
  trait JSClass extends js.Any

  def @#[T]: JSClass = macro impl.Macros.jsClassOf[T]
  def @@[T1] : js.Array[js.Any] = macro impl.Macros.jsClassArray1[T1]
  def @@[T1,T2] : js.Array[js.Any] = macro impl.Macros.jsClassArray2[T1,T2]
  def @@[T1,T2,T3] : js.Array[js.Any] = macro impl.Macros.jsClassArray3[T1,T2,T3]
  def @@[T1,T2,T3,T4] : js.Array[js.Any] = macro impl.Macros.jsClassArray4[T1,T2,T3,T4]

  def @@(items: String*) : js.Array[String] = js.Array(items:_*)

}
