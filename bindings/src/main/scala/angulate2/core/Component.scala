//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Component macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core


import angulate2.animations.AnimationEntryMetadata
import sourcecode.FullName

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.api.Trees
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
                animations: js.Array[AnimationEntryMetadata] = null,
                encapsulation: js.Any = null,
                interpolation: js.Any = null,
                entryComponents: js.Array[js.Any] = null
               ) extends StaticAnnotation {
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

    override def analyze: Analysis = super.analyze andThen {
      case (cls: ClassParts, data) =>
        var decor = ClassDecoratorData(data)
        // handle @LoadTemplate
        decor = findAnnotation(cls.modifiers.annotations,"LoadTemplate") map {
          case q"new LoadTemplate($arg)" => handleLoadTemplateAnnotation(decor,Some(arg),cls.fullName)
          case q"new LoadTemplate()" => handleLoadTemplateAnnotation(decor,None,cls.fullName)
        } getOrElse decor
        // handle @LoadStyles
        decor = findAnnotation(cls.modifiers.annotations,"LoadStyles") map {
          case q"new LoadStyles(..$params)" => handleLoadStylesAnnotation(decor,params,cls.fullName)
          case q"new LoadStyles()" => handleLoadStylesAnnotation(decor,Nil,cls.fullName)
        } getOrElse decor
        (cls, ClassDecoratorData.update(data,decor))
      case default => default
    }

    private lazy val _stylePrefix = setting("angulate2.stylePrefix","")
    private lazy val _templatePrefix = setting("angulate2.templatePrefix","")

    private def handleLoadTemplateAnnotation(decor: ClassDecoratorData, arg: Option[Trees#Tree], fullName: String): ClassDecoratorData = {
      val target = decor.jsAccessor

      val template = arg map {
        case Literal(Constant(x)) => x.toString
        case x => c.error(c.enclosingPosition, "Only literal constants allowed as arguments to @LoadTemplate()")
      } getOrElse( _templatePrefix + fullName.replaceAll("\\.","/") + ".html" )

      decor.addSjsxStatic(500 -> s"__loadTemplate($target, require('$template').toString());")
    }

    private def handleLoadStylesAnnotation(decor: ClassDecoratorData, args: Seq[Trees#Tree], fullName: String): ClassDecoratorData = {
      val target = decor.jsAccessor

      val styles = args map {
        case Literal(Constant(x)) => x.toString
        case x => c.error(c.enclosingPosition, "Only literal constants allowed as arguments to @LoadStyles()")
      }

      val requires = (if(styles.isEmpty) Seq( _stylePrefix + fullName.replaceAll("\\.","/") + ".css" ) else styles)
        .map {
          style => s"require('$style').toString()"
        } mkString("__loadStyles("+target+", [",", ","]);")

      decor.addSjsxStatic(500 -> requires)
    }
  }

}
