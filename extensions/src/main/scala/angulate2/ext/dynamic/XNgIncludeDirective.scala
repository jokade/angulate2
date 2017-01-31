//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.dynamic

import angulate2.core.ViewContainerRef
import angulate2.std._
import rxjs.Observable

import scala.scalajs.js

@Directive(
  selector = "[xngInclude]"
)
class XNgIncludeDirective(viewContainerRef: ViewContainerRef,
                          dynamicComponentBuilder: DynamicComponentBuilder,
                          templateLoader: TemplateLoader) {

  @Input
  def xngInclude_=(exp: Any): Unit = (exp match {
    case url: String =>
      templateLoader.loadTemplate(url)
    case f: js.Function =>
      val tpl = f.call(viewContainerRef).toString
      Observable.of(Template(tpl,tpl,cache=false))
    case x =>
      val tpl = x.toString
      Observable.of(Template(tpl,tpl))
  }).subscribe{ tpl => dynamicComponentBuilder
      .createComponentFactory(tpl)
      .map( cf => viewContainerRef.createComponent(cf) )
  }

}
