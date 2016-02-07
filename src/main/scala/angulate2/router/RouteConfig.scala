//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angular2 @RouteConfig annotation

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.annotation.StaticAnnotation
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

class RouteConfig(defs: RouteDefinition*) extends StaticAnnotation

object RouteConfig {
  @js.native
  @JSName("ng.router.RouteConfig")
  class JSAnnot(defs: js.Array[RouteDefinition]) extends js.Object

  def apply(defs: RouteDefinition*): JSAnnot = new JSAnnot(js.Array(defs:_*))
}