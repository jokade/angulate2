//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Macro trait for handling annotated fields in classes

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

protected[angulate2] trait FieldDecorator extends ClassDecorator {
  import c.universe._

  override def analyze: Analysis = super.analyze andThen {
    case (cls: ClassParts, data) =>
      val metadata = cls.body.collect {
        case t @ ValDef(InputAnnot(externalName),name,_,_) =>
          inputMetadata(cls.fullName,name.toString,externalName,t)
        case t @ DefDef(InputAnnot(externalName),name,_,_,_,_) =>
          val method = name.toString
          val setter =
            if(method.endsWith("_$eq")) method.stripSuffix("_$eq")
            else {
              error("@Input() may only be used on vars and setters (i.e. 'def foo_=(a: Any) {}')")
              ""
            }
          inputMetadata(cls.fullName,setter,externalName,t)
        case t @ ValDef(OutputAnnot(externalName),name,_,_) =>
          outputMetadata(cls.fullName,name.toString,externalName,t)
        case t @ DefDef(OutputAnnot(externalName),name,_,_,_,_) =>
          val method = name.toString
          val setter =
            if(method.endsWith("_$eq")) method.stripSuffix("_$eq")
            else {
              error("@Output() may only be used on vars and setters (i.e. 'def foo_=(a: Any) {}')")
              ""
            }
          outputMetadata(cls.fullName,setter,externalName,t)
        case t @ DefDef(HostListenerAnnot(eventName,arg),name,_,_,_,_) =>
          hostListenerMetadata(cls.fullName, name.toString, eventName, t)
        case t @ ValDef(HostBindingAnnot(hostPropertyName), name, _, _) =>
          hostBindingMetadata(cls.fullName, name.toString, hostPropertyName, t)
        case t @ DefDef(HostBindingAnnot(hostPropertyName), name, _, _, _, _) =>
          hostBindingMetadata(cls.fullName, name.toString, hostPropertyName, t)
        case t @ ValDef(ViewChildAnnot(annot), name, _, _) =>
          viewChildMetadata(cls.fullName, name.toString, annot, t)
        case t @ DefDef(ViewChildAnnot(annot), name, _, _, _, _) =>
          viewChildMetadata(cls.fullName, name.toString, annot, t)
      }
      (cls, ClassDecoratorData.addSjsxStatic(data, metadata.map((1000,_))))
    case default => default
  }

  private def hostBindingMetadata(target: String, method: String, hostPropertyName: String, tree: Tree): String = {
    s"__decorate([core.HostBinding('$hostPropertyName')," + genMetadataString(tree) +
      s"], $exports.$target.prototype,'$method',null);"
  }

  private def hostListenerMetadata(target: String, method: String, eventName: String, tree: Tree): String = {
    s"__decorate([core.HostListener('$eventName'),"+ genMetadataString(tree)+
      s"],$exports.$target.prototype,'$method',null);"
  }

  private def outputMetadata(target: String, method: String, property: Option[String], tree: Tree): String = {
    val decorator = if(property.isDefined) s"core.Output('${property.get}')" else "core.Output()"
    s"__decorate([$decorator,"+ genMetadataString(tree)+
    s"],$exports.$target.prototype,'$method',null);"
  }

  private def inputMetadata(target: String, method: String, property: Option[String], tree: Tree): String = {
    val decorator = if(property.isDefined) s"core.Input('${property.get}')" else "core.Input()"
    s"__decorate([$decorator,"+ genMetadataString(tree)+
      s"],$exports.$target.prototype,'$method',null);"
  }

  private def viewChildMetadata(target: String, method: String, annot: ViewChildAnnot.Data, tree: Tree): String = {
    s"__decorate([$annot, ${genMetadataString(tree)}],$exports.$target.prototype,'$method',null);"
  }

  private def genMetadataString(tree: Tree): String = genMetadata(tree) map {
    case (name, value) => s"__metadata('$name',$value)"
  } mkString ","

  private def genMetadata(tree: Tree): Metadata = {
    def jsType(tpe: Tree): String = tpe match {
      case Ident(TypeName("String")) => "String"
      case Ident(TypeName("Boolean")) => "Boolean"
      case Ident(TypeName("Int")) |
           Ident(TypeName("Long")) |
           Ident(TypeName("Float")) |
           Ident(TypeName("Double"))  => "Number"
      case _ => "Object"
    }

    tree match {
      case ValDef(_, _, tpe, _) => Map(
        "design:type" -> jsType(tpe) )
      case _ => Map()
    }
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

  object HostBindingAnnot {
    // TODO: simplify
    def unapply(annotations: Seq[Tree]): Option[String] =
      findAnnotation(annotations,"HostBinding")
        .map { t =>
          val args = extractAnnotationParameters(t, Seq("hostPropertyName"))
          extractStringConstant(args("hostPropertyName").get).get
        }
    def unapply(modifiers: Modifiers): Option[String]  = unapply(modifiers.annotations)
  }

   object ViewChildAnnot {
     case class Data(selector: String, read: Option[String]) {
       override def toString =
         s"core.ViewChild('$selector'" +
           (if(read.isDefined) s",{read: ${read.get}}" else "") +
           ")"
     }
    // TODO: simplify
    def unapply(annotations: Seq[Tree]): Option[Data] =
      findAnnotation(annotations,"ViewChild")
        .map { t =>
          val args = extractAnnotationParameters(t, Seq("selector","read"))
          Data(
            extractStringConstant(args("selector").get).get,
            args.get("read") flatMap {
              // TODO: handle all types (currently we simply prefix the type with 'core', which only works for token types in package core :)
              case Some(TypeApply((f,List(tpe)))) if f.toString == "$percent$percent"  => Some(s"core.$tpe")
              case Some(x) => error(s"invalid argument for 'read': $x"); None
              case None => None
            })
        }
    def unapply(modifiers: Modifiers): Option[Data]  = unapply(modifiers.annotations)

  }


  object OutputAnnot {
    def unapply(annotations: Seq[Tree]): Option[Option[String]] = findAnnotation(annotations,"Output")
      .map( t => extractAnnotationParameters(t,Seq("bindingPropertyName")).apply("bindingPropertyName").flatMap(extractStringConstant) )
    def unapply(modifiers: Modifiers): Option[Option[String]] = unapply(modifiers.annotations)
  }

  object InputAnnot {
    def unapply(annotations: Seq[Tree]): Option[Option[String]] = findAnnotation(annotations,"Input")
      .map( t => extractAnnotationParameters(t,Seq("externalName")).apply("externalName").flatMap(extractStringConstant) )
    def unapply(modifiers: Modifiers): Option[Option[String]] = unapply(modifiers.annotations)
  }

}
