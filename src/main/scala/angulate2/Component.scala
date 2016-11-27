//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Component macro annotation

// Copyright (c) 2015, 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.internal.DecoratedClass

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.collection.immutable.Iterable
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

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
    private[angulate2] class Macro(val c: whitebox.Context) extends DecoratedClass {
      import c.universe._

      override def mainAnnotation: String = "Component"

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

      override def mainAnnotationObject: c.universe.Tree = q"angulate2.core.Component"

      object InputAnnot {
        def unapply(annotations: Seq[Tree]): Option[Option[Tree]] = findAnnotation(annotations,"Input")
          .map( t => extractAnnotationParameters(t,Seq("externalName")).apply("externalName") )
        def unapply(modifiers: Modifiers): Option[Option[Tree]] = unapply(modifiers.annotations)
      }

      override def decoratorParameters(parts: ClassParts, annotationParamNames: Seq[String]) = {
        import parts._

        val inputStrLiterals = body collect {
          case ValDef(InputAnnot(externalName),term,_,_) => externalName.flatMap(extractStringConstant).getOrElse(term.toString)
        }
        val (nonInputAnnotationParams, inputAnnotationParams) = super.decoratorParameters(parts,annotationParamNames).partition {
          case q"inputs = $v" => false
          case _ => true
        }
        val inputs = inputAnnotationParams match {
          case q"inputs = $call(..$ins)" :: Nil =>
            q"inputs = scalajs.js.Array(..${ins ++ inputStrLiterals.map(s => Literal(Constant(s)))})"
          case _ =>
            q"inputs = scalajs.js.Array(..$inputStrLiterals)"
        }

        Iterable(inputs) ++ nonInputAnnotationParams
      }

    }
//
//  private[angulate2] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
//    import c.universe._
//
//    val annotationParamNames = Seq(
//      "selector",
//      "inputs",
//      "outputs",
//      "providers",
//      "template",
//      "templateUrl",
//      "directives",
//      "styles",
//      "styleUrls")
//
//    object InputAnnot {
//      def unapply(annotations: Seq[Tree]): Option[Option[Tree]] = findAnnotation(annotations,"Input")
//        .map( t => extractAnnotationParameters(t,Seq("externalName")).apply("externalName") )
//      def unapply(modifiers: Modifiers): Option[Option[Tree]] = unapply(modifiers.annotations)
//    }
//
//    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
//      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
//      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
//    }
//
//    def modifiedDeclaration(classDecl: ClassDef) = {
//      val parts = extractClassParts(classDecl)
//
//      import parts._
//
//      // load debug annotation values (returns default config, if there is no @debug on this component)
//      val debug = getDebugConfig(modifiers)
//
//      val inputStrLiterals = body collect {
//        case ValDef(InputAnnot(externalName),term,_,_) => externalName.flatMap(extractStringConstant).getOrElse(term.toString)
//      }
//
//      val objName = fullName + "_"
//      val allComponentAnnotationParams = extractAnnotationParameters(c.prefix.tree, annotationParamNames).collect {
//        case (name,Some(value)) => q"${Ident(TermName(name))} = $value"
//      }
//
//      val (nonInputAnnotationParams, inputAnnotationParams) = allComponentAnnotationParams.partition {
//        case q"inputs = $v" => false
//        case _ => true
//      }
//
//      val inputs = inputAnnotationParams match {
//        case q"inputs = $call(..$ins)" :: Nil =>
//          q"inputs = scalajs.js.Array(..${ins ++ inputStrLiterals.map(s => Literal(Constant(s)))})"
//        case _ =>
//          q"inputs = scalajs.js.Array(..$inputStrLiterals)"
//      }
//
//      val componentAnnotationParams = Iterable(inputs) ++ nonInputAnnotationParams
//
////      val parameterAnnot = parameterAnnotation(fullName,params)
//      val diTypes = getDINames(params) map ("$s."+_)
//      val metadata = s"""__metadata('design:paramtypes',[${diTypes.mkString(",")}])"""
//
//      // string to be written to the annotations.js file
//      val decoration = s"$$s.$fullName = __annotate($$s.$objName().decorators,$metadata,$$s.$fullName);"
//
//      // list of trees to be included in the component's annotation array
//      val annotations =
//        q"angulate2.core.Component( scalajs.js.Dynamic.literal( ..$componentAnnotationParams ))" // +: translateAngulateAnnotations(modifiers)
//
//      val base = getJSBaseClass(parents)
//      val log =
//        if(debug.logInstances) {
//          val msg = s"created Component $fullName:"
//          q"""scalajs.js.Dynamic.global.console.debug($msg,this)"""
//        }
//        else q""
//
//      val tree =
//        q"""{@scalajs.js.annotation.JSExport($fullName)
//             @scalajs.js.annotation.ScalaJSDefined
//             @sjsx.SJSXStatic(1000, $decoration )
//             class $name ( ..$params ) extends ..$base { ..$body; $log }
//             @scalajs.js.annotation.JSExport($objName)
//             @scalajs.js.annotation.ScalaJSDefined
//             object ${name.toTermName} extends scalajs.js.Object {
//               val decorators = scalajs.js.Array( ..$annotations )
//             }
//            }
//         """
//
//      if(debug.showExpansion) printTree(tree)
//
//      c.Expr[Any](tree)
//    }
//
//  }
}
