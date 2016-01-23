//     Project: angulate2
//      Module: ES5 API
// Description: Façade trait for the Angular 2 ng.platform.browser object.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.es5.platform

import scala.scalajs.js

@js.native
trait Browser extends js.Object {
  def bootstrap(appComponentType: js.Any): Unit = js.native
}
