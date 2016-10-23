package angulate2

import angulate2.impl.JsWhiteboxMacroTools

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@compileTimeOnly("enable macro paradise to expand macro annotations")
class NgModule(
    imports: js.Array[js.Any] = js.Array(),
    declarations: js.Array[js.Any] = js.Array(),
    providers: js.Array[js.Any] = js.Array(),
    bootstrap: js.Array[js.Any] = js.Array()
) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro NgModuleImpl.ngModule
}

class NgModuleImpl(val c: whitebox.Context) extends JsWhiteboxMacroTools {

  import c.universe._

  def ngModule(annottees: Tree*): Tree = {
    val classDecl: ClassDef = annottees.toList match {
      case (cd: ClassDef) :: Nil => cd
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @NgModule")
    }

    val parts = extractClassParts(classDecl)
    val base = getJSBaseClass(parts.parents)
    val objName = parts.fullName + "_"

    val annotationParams = extractAnnotationParameters(c.prefix.tree, List(
      "imports",
      "declarations",
      "providers",
      "bootstrap"
    )).collect {
      case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
    }

    val annotations = q"""
      new angulate2.core.NgModule(scalajs.js.Dynamic.literal(..$annotationParams))
    """

    val parameterAnnot = parameterAnnotation(parts.fullName, parts.params)
    val angulateAnnotation = s"${parts.fullName}.annotations = $objName().annotations; $parameterAnnot"

    q"""{
      @scalajs.js.annotation.JSExport(${parts.fullName})
      @scalajs.js.annotation.ScalaJSDefined
      @sjsx.SJSXStatic(1000, $angulateAnnotation)
      class ${parts.name} (..${parts.params}) extends ..$base { ..${parts.body} }

      @scalajs.js.annotation.JSExport($objName)
      @scalajs.js.annotation.ScalaJSDefined
      object ${parts.name.toTermName} extends scalajs.js.Object {
        def annotations = scalajs.js.Array(..$annotations)
      }
    }"""
  }

}