//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade trait for Angular2 router.RouteDefinition

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import angulate2.JSClass

import scala.scalajs.js

@js.native
trait RouteDefinition extends js.Object {
  def path: js.UndefOr[String] = js.native
  def name: js.UndefOr[String] = js.native
  def component: js.UndefOr[JSClass] = js.native
}

object RDef {
  def apply(path: String = null,
            name: String = null,
            component: js.Any = null) : RouteDefinition = {
    val d = js.Dynamic.literal()
    if(path!=null)
      d.path = path
    if(name!=null)
      d.name = name
    if(component!=null)
      d.component = component
    d.asInstanceOf[RouteDefinition]
  }
}
