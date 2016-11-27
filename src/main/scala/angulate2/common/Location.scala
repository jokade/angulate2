//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.common

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/common","Location")
class Location extends js.Object {
  def back(): Unit = js.native
  def forward(): Unit = js.native
}
