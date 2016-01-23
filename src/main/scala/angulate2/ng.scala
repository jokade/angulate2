//     Project: angulate2
//      Module: ES5 API
// Description: Façade trait for the Angular2 ng root object.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.es5.{Common, Platform}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
 * Scala.js façade trait for the Angular 2 ng object.
 */
@JSName("ng")
@js.native
object ng extends js.Object {
  val common: Common = js.native
  val platform: Platform = js.native
}
