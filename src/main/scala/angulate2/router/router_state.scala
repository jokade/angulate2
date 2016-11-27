//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for @angular/router/router_state.ts (v2.2.1)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import rxjs.Observable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","ActivatedRoute")
class ActivatedRoute extends js.Object {
  def params: Observable[js.Dictionary[String]] = js.native
}
