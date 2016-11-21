//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Injectable annotation.

// Copyright (c) 2015, 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import angulate2.internal.{DecoratedClass, JsWhiteboxMacroTools}

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

class Injectable extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Injectable.Macro.impl
}


object Injectable {

  private[angulate2] class Macro(val c: whitebox.Context) extends DecoratedClass {
    import c.universe._
    override val mainAnnotation: String = "Injectable"

    override val annotationParamNames: Seq[String] = Seq()

    override def mainAnnotationObject = q"angulate2.core.Injectable"
  }
}
