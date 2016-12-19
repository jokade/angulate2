//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade/implementation of @angular/core/HostListener

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation
import scala.scalajs.js

class HostListener(eventName: String,
                   args: js.UndefOr[js.Array[String]] = js.undefined) extends StaticAnnotation

object HostListener {
  protected[angulate2] trait HostListenerDecorator extends ClassDecorator {
    import c.universe._


    override def analyze: Analysis = super.analyze andThen {
       case (cls: ClassParts,data) =>
        val metadata = cls.body.collect {
          case DefDef(HostListenerAnnot(eventName,arg),name,_,_,_,_) => HostListenerAnnot(cls.fullName,name.toString,eventName,arg)
        } map hostListenerMetadata
        (cls,ClassDecoratorData.addSjsxStatic(data,metadata))
       case default => default
    }

    private def hostListenerMetadata(annot: HostListenerAnnot): String = {
      import annot._
      s"__decorate([require('@angular/core').HostListener('$eventName'),"+
      "__metadata('design:type',Function),__metadata('design:paramtypes',[]),__metadata('design:returntype',void 0)],"+
      s"$exports.$target.prototype,'$method',null);"
    }


    object HostListenerAnnot {
      // TODO: simplify
      def unapply(annotations: Seq[Tree]): Option[(String,Option[Tree])] =
        findAnnotation(annotations,"HostListener")
          .map { t =>
            val args = extractAnnotationParameters(t, Seq("eventName", "args"))
            (extractStringConstant(args("eventName").get).get,args("args"))
          }
      def unapply(modifiers: Modifiers): Option[(String,Option[Tree])]  = unapply(modifiers.annotations)
    }

    case class HostListenerAnnot(target: String, method: String, eventName: String, arg: Option[Tree])
  }
}
