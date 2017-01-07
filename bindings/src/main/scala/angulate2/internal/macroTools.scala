//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Common utility functions for angulate2 macros

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import de.surfice.smacrotools.{JsBlackboxMacroTools, JsWhiteboxMacroTools}

import scala.language.reflectiveCalls

trait AngulateCommonMacroTools extends de.surfice.smacrotools.JsCommonMacroTools {
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
  def getInjectionDependencies(params: Iterable[Tree]): Iterable[Dependency] =
    if(params.isEmpty) None
    else
      params map {
        case q"$mods val $name: $tpe = $e" =>
          val t = c.typecheck(tpe,c.TYPEmode).tpe
          t.typeSymbol.annotations.map(_.tree).collectFirst{
            case q"new $name( ..$params )" if name.toString == "scala.scalajs.js.annotation.JSImport" => params match {
              case Seq(Literal(Constant(module)),Literal(Constant(name))) => RequireDependency(module.toString,name.toString)
            }
          }.getOrElse(ScalaDependency(t.toString))
      }


}

trait AngulateWhiteboxMacroTools extends JsWhiteboxMacroTools with AngulateCommonMacroTools
trait AngulateBlackboxMacroTools extends JsBlackboxMacroTools with AngulateCommonMacroTools

sealed trait Dependency
case class ScalaDependency(fqn: String) extends Dependency
case class RequireDependency(module: String, name: String) extends Dependency