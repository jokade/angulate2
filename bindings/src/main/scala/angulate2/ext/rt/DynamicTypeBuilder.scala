//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.rt

import angulate2.core.{ComponentFacade, NgModuleFacade, Type}

import scala.scalajs.js

object DynamicTypeBuilder {
  import angulate2.ext.runtime.decorate

  def createModule(declarations: js.Array[js.Any] = js.Array(),
                   imports: js.Array[js.Any] = js.Array()): Type[js.Dynamic] = {
    val t = createConstructor()

    val opts = js.Dynamic.literal()
    if(declarations.nonEmpty)
      opts.declarations = declarations
    if(imports.nonEmpty)
      opts.imports = imports

    decorate(js.Array(NgModuleFacade(opts)),t).asInstanceOf[Type[js.Dynamic]]
  }

  def createComponent(template: js.UndefOr[String] = js.undefined): Type[js.Dynamic] = {
    val t = createConstructor()

    val opts = js.Dynamic.literal()
    if(template.isDefined)
      opts.template = template.get

    decorate(js.Array(ComponentFacade(opts)),t).asInstanceOf[Type[js.Dynamic]]
  }

  private def createConstructor(): js.Any = new js.Function()
}
