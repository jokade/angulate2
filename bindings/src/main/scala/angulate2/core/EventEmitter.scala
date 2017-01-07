//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","EventEmitter")
class EventEmitter[T](isAsync: js.UndefOr[Boolean] = js.undefined) extends js.Any {
  def emit(value: js.UndefOr[T] = js.undefined): Unit = js.native
  def subscribe(generatorOrNext: js.UndefOr[js.Any] = js.undefined, error: js.UndefOr[js.Any] = js.undefined, complete: js.UndefOr[js.Any] = js.undefined): js.Any = js.native
}
