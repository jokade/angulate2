//     Project: angulate2
//      Module: ES5 API
// Description: Façade trait for the Angular 2 ng.platform object.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.es5

import scala.scalajs.js

@js.native
trait Platform extends js.Object {
  val browser: platform.Browser = js.native
}
