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

  def getDebugConfig(modifiers: Modifiers): debug.DebugConfig = modifiers.annotations collectFirst {
    case d @ Apply((q"new debug",_)) =>
      val args = extractAnnotationParameters(d:Tree,Seq("showExpansion","logInstances"))
      debug.DebugConfig(
        booleanDebugArg(args,"showExpansion"),
        booleanDebugArg(args,"logInstances")
      )
  } getOrElse(debug.defaultDebugConfig)


  private def booleanDebugArg(args: Map[String,Option[Tree]], name: String): Boolean = args(name) match {
    case None => true
    case Some(q"false") => false
    case Some(q"true") => true
    case Some(x) => c.abort(c.enclosingPosition,s"Invalid value for @debug parameter $name: $x (must be true or false)")
  }
}

abstract class JsBlackboxMacroTools extends BlackboxMacroTools with JsCommonMacroTools

abstract class JsWhiteboxMacroTools extends WhiteboxMacroTools with JsCommonMacroTools {
  import c.universe._

  def getJSBaseClass(parents: Iterable[c.Tree]) = parents match {
        case Seq(x) if x.toString == "scala.AnyRef" => Seq(tq"scalajs.js.Object")
        case x => x
      }
}

