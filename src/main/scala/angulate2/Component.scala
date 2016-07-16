//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Component macro annotation

// Copyright (c) 2015, 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.impl.JsWhiteboxMacroTools

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

// NOTE: keep the constructor parameter list and Component.Macro.annotationParamNames in sync!
@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String = null,
                inputs: js.Array[String] = null,
                outputs: js.Array[String] = null,
                providers: js.Array[js.Any] = null,
                template: String = null,
                templateUrl: String = null,
                directives: js.Array[js.Any] = null,
                styles: js.Array[String] = null,
                styleUrls: js.Array[String] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}

object Component {
//  @JSName("ng.core.Component")
//  @js.native
//  class JSAnnot(annots: js.Dynamic) extends js.Object

  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._

    val annotationParamNames = Seq(
      "selector",
      "inputs",
      "outputs",
      "providers",
      "template",
      "templateUrl",
      "directives",
      "styles",
      "styleUrls")

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)

      import parts._

      // load debug annotation values (returns default config, if there is no @debug on this component)
      val debug = getDebugConfig(modifiers)

      val inputStrLiterals = body collect {
        case q"@Input() var $iName: $iType = $iRhs" => iName.toString
        case q"@Input($extName) var $iName: $iType = $iRhs" => extName match {
          case Literal(Constant(value)) => s"$iName:$value"
          case currentTree => c.abort(currentTree.pos, "@Input() parameter must be a literal String")
        }
      }


      val objName = fullName + "_"
      val allComponentAnnotationParams = extractAnnotationParameters(c.prefix.tree, annotationParamNames).collect {
        case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
      }

      val (nonInputAnnotationParams, inputAnnotationParams) = allComponentAnnotationParams.partition {
        case q"inputs = $v" => false
        case _ => true
      }

      val inputs = inputAnnotationParams match {
        case q"inputs = js.Array(..$ins)" :: Nil =>
          q"inputs = scalajs.js.Array(..${ins ++ inputStrLiterals.map(s => Literal(Constant(s)))})"
        case _ =>
          q"inputs = scalajs.js.Array(..$inputStrLiterals)"
      }

      val componentAnnotationParams = Iterable(inputs) ++ nonInputAnnotationParams

      val parameterAnnot = parameterAnnotation(fullName,params)

      // string to be written to the annotations.js file
      val angulateAnnotation = s"$fullName.annotations = $objName().annotations; $parameterAnnot"

      // list of trees to be included in the component's annotation array
      val annotations =
        q"new angulate2.core.Component(scalajs.js.Dynamic.literal( ..$componentAnnotationParams ))" +: translateAngulateAnnotations(modifiers)

      val base = getJSBaseClass(parents)
      val log =
        if(debug.logInstances) {
          val msg = s"created Component $fullName:"
          q"""scalajs.js.Dynamic.global.console.debug($msg,this)"""
        }
        else q""

      val tree =
        q"""{@scalajs.js.annotation.JSExport($fullName)
             @scalajs.js.annotation.ScalaJSDefined
             @sjsx.SJSXStatic(1000, $angulateAnnotation )
             @sjsx.SJSXRequire("angular2/core","ng.core")
             class $name ( ..$params ) extends ..$base { ..$body; $log }
             @scalajs.js.annotation.JSExport($objName)
             @scalajs.js.annotation.ScalaJSDefined
             object ${name.toTermName} extends scalajs.js.Object {
               def annotations = scalajs.js.Array( ..$annotations )
             }
            }
         """

      if(debug.showExpansion) printTree(tree)

      c.Expr[Any](tree)
    }

  }
}
