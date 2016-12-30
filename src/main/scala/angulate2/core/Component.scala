//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Component macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core


import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","Component")
object ComponentFacade extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String = null,
                inputs: js.Array[String] = null,
                outputs: js.Array[String] = null,
                host: js.Any = null,
                exportAs: String = null,
                moduleId: js.Any = null,
                providers: js.Array[js.Any] = null,
                viewProviders: js.Array[js.Any] = null,
                changeDetection: js.Any = null,
                queries: js.Any = null,
                templateUrl: String = null,
                template: String = null,
                styles: js.Array[String] = null,
                styleUrls: js.Array[String] = null,
                animations: js.Array[js.Any] = null,
                encapsulation: js.Any = null,
                interpolation: js.Any = null,
                entryComponents: js.Array[js.Any] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}

object Component {
  private [angulate2] class Macro(val c: whitebox.Context) extends Directive.BaseMacro {
    import c.universe._

    override def annotationName = "Component"

    // IMPORTANT: this list must contain all annotation arguments in the SAME order they are
    // defined in the annotations constructor!
    override def annotationParamNames: Seq[String] = Seq(
      "selector",
      "inputs",
      "outputs",
      "host",
      "exportAs",
      "moduleId",
      "providers",
      "viewProviders",
      "changeDetection",
      "queries",
      "templateUrl",
      "template",
      "styles",
      "styleUrls",
      "animations",
      "encapsulation",
      "interpolation",
      "entryComponents"
    )

    override def mainAnnotationObject: c.universe.Tree = q"angulate2.core.ComponentFacade"

  }

}
