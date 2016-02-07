//     Project: Default (Template) Project
// Description: Common angulate2 macro implementations.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.impl

import angulate2.JSClass

import scala.reflect.macros.blackbox


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

  def jsClassOf[T: c.WeakTypeTag] = q"${selectGlobalDynamic[T]}.asInstanceOf[angulate2.JSClass]"

  def jsClassArray1[T: c.WeakTypeTag] =
    q"scalajs.js.Array(${selectGlobalDynamic[T]})"

  def jsClassArray2[T1: c.WeakTypeTag, T2: c.WeakTypeTag] =
    q"scalajs.js.Array(${selectGlobalDynamic[T1]},${selectGlobalDynamic[T2]})"

  def jsClassArray3[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag] =
    q"scalajs.js.Array(${selectGlobalDynamic[T1]},${selectGlobalDynamic[T2]},${selectGlobalDynamic[T3]})"

  def jsClassArray4[T1: c.WeakTypeTag, T2: c.WeakTypeTag, T3: c.WeakTypeTag, T4: c.WeakTypeTag] =
    q"""scalajs.js.Array(${selectGlobalDynamic[T1]},${selectGlobalDynamic[T2]},${selectGlobalDynamic[T3]},
        ${selectGlobalDynamic[T4]})"""
}


