// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation

class Input() extends StaticAnnotation {
  def this(externalName: String) = this()
}

object Input {
  protected[angulate2] trait InputDecorator extends ClassDecorator {
    import c.universe._

    override def analyze: Analysis = super.analyze andThen {
      case (cls: ClassParts, data) =>
        import cls._

        val inputs = (body collect {
          case ValDef(InputAnnot(externalName),term,_,_) => InputAnnot(fullName,term.toString,externalName)
          case DefDef(InputAnnot(externalName),term,_,_,_,_) =>
            val method = term.toString
            val setter =
              if(method.endsWith("_$eq")) method.stripSuffix("_$eq")
              else {
                error("@Input() may only be used on vars and setters (i.e. 'def foo_=(a: Any) {}')")
                ""
              }
            InputAnnot(fullName,setter,externalName)
        }).map(_.toJS)

        (cls,ClassDecoratorData.addSjsxStatic(data,inputs))

      case default => default
    }


    object InputAnnot {
      def unapply(annotations: Seq[Tree]): Option[Option[String]] = findAnnotation(annotations,"Input")
        .map( t => extractAnnotationParameters(t,Seq("externalName")).apply("externalName").flatMap(extractStringConstant) )
      def unapply(modifiers: Modifiers): Option[Option[String]] = unapply(modifiers.annotations)

      def apply(prototype: String, method: String, property: Option[String]): MethodDecoration = {
        val decorator = if(property.isDefined) s"core.Input('${property.get}')" else "core.Input()"
//        val designType = DecorationMetadata.designType(null)
        MethodDecoration(decorator,prototype,method,Nil)
      }
    }


  }
}
