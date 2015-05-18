// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: angulate2 implementation of Angular's @Component annotation
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

import scala.annotation.{compileTimeOnly, StaticAnnotation}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String,
                template: String = null,
                directives: js.Array[Any] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}


object Component {

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._
    lazy val debug = isSet("biz.enef.angulate2.debug.Component")

    val annotationParamNames =
      Seq("selector",
          "template",
          "directives")

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)
      import parts._

      val annots = annotations( extractAnnotationParameters(c.prefix.tree,annotationParamNames) )
      val d = selectGlobalDynamic(fullName)
      CompileTimeRegistry.registerComponent(fullName, q"""$d.updateDynamic("annotations")($annots)""")

      val tree =
        q"""{@scalajs.js.annotation.JSExport($fullName) @scalajs.js.annotation.JSExportAll
         class $name ( ..$params ) extends ..$parents with biz.enef.angulate2.Angular.Annotated { ..$body }
         }"""

      if(debug) printTree(tree)

      c.Expr[Any](tree)
    }

    def annotations(params: Map[String,Option[Tree]]) = {
      val groups = params.collect {
        case (name, Some(rhs)) => (name, rhs)
      }.groupBy {
        case ("selector", _) => "ComponentAnnotation"
        case ("template"|"directives", _) => "ViewAnnotation"
        case _ => ???
      }.map{
        case (atype,m) =>
          val annot = TermName(atype)
          val params = m.toList.map( p => q"${TermName(p._1)} = ${p._2}")
          q"$annot( ..$params )"
      }

      q"scalajs.js.Array( ..$groups )"
    }
  }


}
