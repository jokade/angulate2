//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js

@js.native
trait Injector extends js.Object {
  def get(token: js.Any, notFoundValue: js.UndefOr[js.Any] = js.undefined): js.Any = js.native
}
