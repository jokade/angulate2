//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Common utility functions for angulate2 macros

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import angulate2.debug
import angulate2.debug.DebugConfig

import scala.language.reflectiveCalls

trait JsCommonMacroTools extends de.surfice.smacrotools.JsCommonMacroTools {
  import c.universe._


  private val ignoreAnnotations = Seq("debug")

  /**
   * Translate additional annotations found in the specified modifiers list into the form
   * required by the Angular2 annotations array.
   * To this end, every annotation in this must provide an apply() method on its companion object
   * that takes the annotation's parameters and returns the Angular2 annotation object.
   **/
  def translateAngulateAnnotations(modifiers: Modifiers): List[Tree] = modifiers.annotations collect {
    case annot @ q"new $name(..$params)" if !ignoreAnnotations.contains(name.toString) =>
      q"${TermName(name.toString)}.apply(..$params)"
  }


  // TODO: simpler :)
  def getDINames(params: Iterable[Tree]): Iterable[String] =
    if(params.isEmpty) None
    else
      params map {
        case q"$mods val $name: $tpe = $e" =>
          val t = c.typecheck(tpe,c.TYPEmode).tpe
          t.typeSymbol.annotations.map(_.tree).collectFirst{
            case q"new $name( ..$params )" if name.toString == "scala.scalajs.js.annotation.JSName" => params.head match {
              case Literal(Constant(x)) => x.toString
            }
          }.getOrElse(t.toString)
      }


  def parameterAnnotation(fullClassName: String, params: Iterable[Tree]) : String = getDINames(params) match {
    case Nil => ""
    case list => list.map( p => "["+p+"]").mkString(s"$fullClassName.parameters = [",",","];")
  }

}

abstract class JsBlackboxMacroTools extends de.surfice.smacrotools.JsBlackboxMacroTools with JsCommonMacroTools

abstract class JsWhiteboxMacroTools extends de.surfice.smacrotools.JsWhiteboxMacroTools with JsCommonMacroTools {
  import c.universe._


}

