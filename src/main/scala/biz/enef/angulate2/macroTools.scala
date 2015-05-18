// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Provides macro utility functions
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import scala.language.reflectiveCalls
import biz.enef.smacrotools.{WhiteboxMacroTools, CommonMacroTools, BlackboxMacroTools}

trait JsCommonMacroTools {
  this: CommonMacroTools =>

  import c.universe._

  def selectGlobalDynamic[T: c.WeakTypeTag] : Tree = selectGlobalDynamic(weakTypeOf[T].typeSymbol.fullName)

  def selectGlobalDynamic(fullName: String) : Tree = fullName.split("\\.").
    foldLeft(q"scalajs.js.Dynamic.global":Tree)((b,name) => q"""$b.selectDynamic($name)""")
}

abstract class JsBlackboxMacroTools extends BlackboxMacroTools with JsCommonMacroTools

abstract class JsWhiteboxMacroTools extends WhiteboxMacroTools with JsCommonMacroTools
