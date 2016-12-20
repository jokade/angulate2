//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation

class Output extends StaticAnnotation {
  def this(bindingPropertyName: String) = this()
}

object Output {

  protected[angulate2] trait OutputDecorator extends ClassDecorator {
    import c.universe._

    override def analyze: Analysis = super.analyze andThen {
      case (cls: ClassParts, data) =>
        import cls._

        val inputs = (body collect {
          case ValDef(OutputAnnot(externalName),term,_,_) => OutputAnnot(fullName,term.toString,externalName)
          case DefDef(OutputAnnot(externalName),term,_,_,_,_) =>
            val method = term.toString
            val setter =
              if(method.endsWith("_$eq")) method.stripSuffix("_$eq")
              else {
                error("@Output() may only be used on vars and setters (i.e. 'def foo_=(a: Any) {}')")
                ""
              }
            OutputAnnot(fullName,setter,externalName)
        }).map(_.toJS)

        (cls,ClassDecoratorData.addSjsxStatic(data,inputs))

      case default => default
    }

    object OutputAnnot {
      def unapply(annotations: Seq[Tree]): Option[Option[String]] = findAnnotation(annotations,"Output")
        .map( t => extractAnnotationParameters(t,Seq("bindingPropertyName")).apply("bindingPropertyName").flatMap(extractStringConstant) )
      def unapply(modifiers: Modifiers): Option[Option[String]] = unapply(modifiers.annotations)

      def apply(prototype: String, method: String, property: Option[String]): MethodDecoration = {
        val decorator = if(property.isDefined) s"core.Output('${property.get}')" else "core.Output()"
        //        val designType = DecorationMetadata.designType(null)
        MethodDecoration(decorator,prototype,method,Nil)
      }
    }

  }

}
