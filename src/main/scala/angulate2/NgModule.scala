//     Project: angulate2
// Description: Angular2 @NgModule macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.internal.JsWhiteboxMacroTools

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class NgModule(imports: js.Array[js.Any] = null,
               declarations: js.Array[js.Any] = null,
               bootstrap: js.Array[js.Any] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro NgModule.Macro.impl
}

object NgModule {
  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._

    val annotationParamNames = Seq(
      "imports",
      "declarations",
      "bootstrap")

    def impl(annottees: c.Expr[Any]*): c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @NgModule")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)

      import parts._

      implicit val debug = getDebugConfig(modifiers)

      val decoratorParams = decoratorParameters(c.prefix.tree,annotationParamNames)
      val annotation =
        q"angulate2.core.NgModule( scalajs.js.Dynamic.literal(..$decoratorParams) )"
      val tree = makeDecoratedClass(parts,q"..$annotation",s"created NgModule $fullName")

      c.Expr[Any](tree)
    }
  }

}
