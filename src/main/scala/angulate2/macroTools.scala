//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import biz.enef.smacrotools.{BlackboxMacroTools, CommonMacroTools, WhiteboxMacroTools}

import scala.language.reflectiveCalls

trait JsCommonMacroTools {
  this: CommonMacroTools =>

  import c.universe._

  def selectGlobalDynamic[T: c.WeakTypeTag] : Tree = selectGlobalDynamic(weakTypeOf[T].typeSymbol.fullName)

  def selectGlobalDynamic(fullName: String) : Tree = fullName.split("\\.").
    foldLeft(q"scalajs.js.Dynamic.global":Tree)((b,name) => q"""$b.selectDynamic($name)""")


}

abstract class JsBlackboxMacroTools extends BlackboxMacroTools with JsCommonMacroTools

abstract class JsWhiteboxMacroTools extends WhiteboxMacroTools with JsCommonMacroTools {
  import c.universe._

  def getJSBaseClass(parents: Iterable[c.Tree]) = parents match {
        case Seq(x) if x.toString == "scala.AnyRef" => Seq(tq"scalajs.js.Object")
        case x => x
      }
}

