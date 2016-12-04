//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @Component macro annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.collection.immutable.Iterable
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
    private[angulate2] class Macro(val c: whitebox.Context) extends ClassDecorator {
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

}
