//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.dynamic

import angulate2.compiler.JitCompiler
import angulate2.core.ComponentFactory
import angulate2.ext.classModeScala
import angulate2.ext.rt.DynamicTypeBuilder
import angulate2.std._
import rxjs.RxPromise
import slogging.LazyLogging

import scala.scalajs.js

@Injectable
@classModeScala
class DynamicComponentBuilder(val compiler: JitCompiler) extends LazyLogging {
  logger.trace("initializing DynamicComponentBuilder")
  private val _cache = js.Dictionary.empty[RxPromise[ComponentFactory[js.Dynamic]]]

  def createComponentFactory(template: Template): RxPromise[ComponentFactory[js.Dynamic]] =
    createComponentFactory(template.id,template.template,template.cache)

  def createComponentFactory(id: String, template: String, cache: Boolean) : RxPromise[ComponentFactory[js.Dynamic]] = {
    def create() = {
      logger.debug("creating ComponentFactory for template id '{}' (cache={})",id,cache)
      val comp = DynamicTypeBuilder.createComponent(template)
      val module = DynamicTypeBuilder.createModule(declarations = @@@(comp))
      compiler
          .compileModuleAndAllComponentsAsync(module)
        .map( _.componentFactories.find(_.selector == "ng-component").get )
    }
    if(cache) _cache.getOrElseUpdate(id,create())
    else create()
  }

}


