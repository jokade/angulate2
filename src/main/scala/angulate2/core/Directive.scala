//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Directive macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.{ClassDecorator, JsWhiteboxMacroTools}

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","Directive")
object DirectiveFacade extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

// NOTE: keep the constructor parameter list and Directive.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Directive(selector: String = null,
                inputs: js.Array[String] = null,
                outputs: js.Array[String] = null,
                host: js.Dictionary[String] = null,
                template: String = null,
                providers: js.Array[js.Any] = null,
                exportAs: String = null,
                queries: js.Any = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Directive.Macro.impl
}


object Directive {

  private[angulate2] class Macro(val c: whitebox.Context) extends ClassDecorator {
    import c.universe._

    override def mainAnnotation: String = "Directive"

    override def mainAnnotationObject: c.universe.Tree = q"angulate2.core.DirectiveFacade"

    override def annotationParamNames: Seq[String] = Seq(
      "selector",
      "inputs",
      "outputs",
      "host",
      "template",
      "providers",
      "exportAs",
      "queries"
    )
  }
}

