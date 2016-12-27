//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation

class HostBinding(hostPropertyName: String) extends StaticAnnotation

object HostBinding {
  protected[angulate2] trait HostBindingDecorator extends ClassDecorator {
    import c.universe._


    override def analyze: Analysis = super.analyze andThen {
       case (cls: ClassParts,data) =>
        val metadata = cls.body.collect {
          case t @ ValDef(HostBindingAnnot(hostPropertyName),name,_,_) =>
            hostBindingMetadata(cls.fullName,name.toString,hostPropertyName,t)
          case t @ DefDef(HostBindingAnnot(hostPropertyName),name,_,_,_,_) =>
            hostBindingMetadata(cls.fullName,name.toString,hostPropertyName,t)
        }
        (cls,ClassDecoratorData.addSjsxStatic(data,metadata))
       case default => default
    }

    private def hostBindingMetadata(target: String, method: String, hostPropertyName: String, tree: Tree): String = {
      s"__decorate([core.HostBinding('$hostPropertyName'),"+ genMetadataString(tree) +
      s"], $exports.$target.prototype,'$method',null);"
    }

    private def genMetadataString(tree: Tree): String = genMetadata(tree) map {
        case (name,value) => s"__metadata('$name',$value)"
      } mkString ","

    private def genMetadata(tree: Tree): Metadata = {
      def jsType(tpe: Tree): String = tpe.tpe.typeSymbol.fullName match {
        case "scala.Boolean" => "Boolean"
        case "java.lang.String"  => "String"
        case "scala.Int"     => "Number"
        case "scala.Long"    => "Number"
        case "scala.Float"   => "Number"
        case "scala.Double"  => "Number"
        case x =>
          println(x)
          "Object"

//        case x =>
//          error(s"Unsupported type for metadata: $x")
//          ???
      }
      c.typecheck(tree.duplicate) match {
        case ValDef(_,_,tpe,_) => Map(
          "design:type" -> jsType(tpe)
        )
        case _ => Map()
      }
    }


    object HostBindingAnnot {
      // TODO: simplify
      def unapply(annotations: Seq[Tree]): Option[String] =
        findAnnotation(annotations,"HostBinding")
          .map { t =>
            val args = extractAnnotationParameters(t, Seq("hostPropertyName"))
            (extractStringConstant(args("hostPropertyName").get).get)
          }
      def unapply(modifiers: Modifiers): Option[String]  = unapply(modifiers.annotations)
    }

    case class HostBindingAnnot(target: String, method: String, hostPropertyName: String)
  }
}
