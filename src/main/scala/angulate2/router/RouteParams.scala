//     Project: angulate2
// Description: Façade trait for Angular2 router.RouteParams

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
 * RouteParams is an immutable map of parameters for the given route based on the url matcher and optional parameters for that route.
 *
 * @see [[https://angular.io/docs/ts/latest/api/router/RouteParams-class.html]]
 */
@js.native
@JSName("ng.router.RouteParams")
trait RouteParams extends js.Object {
  def params: js.Dictionary[String] = js.native
  def get(param: String): js.UndefOr[String] = js.native
}
