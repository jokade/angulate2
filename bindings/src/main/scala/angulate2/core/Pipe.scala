//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator
import de.surfice.smacrotools.createJS

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","Pipe")
object PipeFacade extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

// NOTE: keep the constructor parameter list and Directive.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Pipe(name: String, pure: Boolean = true) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Pipe.Macro.impl
}

object Pipe {

  private[angulate2] class Macro(val c: whitebox.Context) extends ClassDecorator {
    import c.universe._
    val annotationParamNames = Seq(
      "name",
      "pure"
    )

    override val annotationName: String = "Pipe"

    override def mainAnnotationObject = q"angulate2.core.PipeFacade"
  }
}

@createJS
trait PipeTransform0[T,R] {
  def transform(value: T): R
}

@createJS
trait PipeTransform1[T,A1,R] {
  def transform(value: T, arg1: A1): R
}

@createJS
trait PipeTransform2[T,A1,A2,R] {
  def transform(value: T, arg1: A1, arg2: A2): R
}

@createJS
trait PipeTransform3[T,A1,A2,A3,R] {
  def transform(value: T, arg1: A1, arg2: A2, arg3: A3): R
}