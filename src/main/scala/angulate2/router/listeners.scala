//     Project: angulate2
// Description: Traits for Angular2 router lifecycle hooks.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
 * Defines route lifecycle method routerCanDeactivate,
 * which is called by the router to determine if a component can be removed as part of a navigation.
 *
 * @see [[https://angular.io/docs/ts/latest/api/router/CanDeactivate-interface.html]]
 */
@ScalaJSDefined
trait CanDeactivate extends js.Object {
  def routerCanDeactivate(nextInstruction: ComponentInstruction, prevInstruction: ComponentInstruction) : js.Any
}
