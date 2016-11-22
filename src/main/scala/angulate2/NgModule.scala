//     Project: angulate2
// Description: Angular2 @NgModule macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.internal.{JSType, DecoratedClass, JsWhiteboxMacroTools}

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class NgModule(providers: js.Array[js.Any] = null,
               declarations: js.Array[js.Any] = null,
               imports: js.Array[js.Any] = null,
               exports: js.Array[js.Any] = null,
               entryComponents: js.Array[js.Any] = null,
               bootstrap: js.Array[js.Any] = null,
               schemas: js.Array[js.Any] = null,
               id: String = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro NgModule.Macro.impl
}

object NgModule {
  private[angulate2] class Macro(val c: whitebox.Context) extends DecoratedClass {
    import c.universe._

    val annotationParamNames = Seq(
      "providers",
      "declarations",
      "imports",
      "exports",
      "entryComponents",
      "bootstrap",
      "schemas",
      "id"
    )

    override val mainAnnotation: String = "NgModule"

    override def mainAnnotationObject = q"angulate2.core.NgModule"
  }

}
