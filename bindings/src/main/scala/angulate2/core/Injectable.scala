//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Injectable annotation.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","Injectable")
object InjectableFacade extends js.Object {
  def apply() : js.Object = js.native
  def apply(options: js.Object) : js.Object = js.native
}

// NOTE: keep the constructor parameter list and Injectable.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Injectable extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Injectable.Macro.impl
}


object Injectable {

  private[angulate2] class Macro(val c: whitebox.Context) extends ClassDecorator {
    import c.universe._
    override val annotationName: String = "Injectable"

    override val annotationParamNames: Seq[String] = Seq()

    override def mainAnnotationObject = q"angulate2.core.InjectableFacade"
  }
}
