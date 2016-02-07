//     Project: angulate2
// Description: Façade trait for Angular2 router.ComponentInstruction.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js

/**
 * A ComponentInstruction represents the route state for a single component.
 * An Instruction is composed of a tree of these ComponentInstructions.
 *
 * @see [[https://angular.io/docs/ts/latest/api/router/ComponentInstruction-class.html]]
 */
@js.native
trait ComponentInstruction extends js.Object {
  def reuse: Boolean = js.native
  def urlPath: String = js.native
  def urlParams: js.Array[String] = js.native
  def params: js.Dictionary[js.Any]
}
