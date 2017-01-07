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
  def path(includeHash: js.UndefOr[Boolean] = js.undefined): String = js.native
  def isCurrentPathEqualTo(path: String, query: js.UndefOr[String] = js.undefined): Boolean = js.native
  def normalize(url: String): String = js.native
  def prepareExternalUrl(url: String): String = js.native
  def back(): Unit = js.native
  def forward(): Unit = js.native
  def go(path: String, query: String = ""): Unit = js.native
  def replaceState(path: String, query: String = ""): Unit = js.native
  def subscribe(onNext: js.Function1[js.Any,_],
                onThrow: js.UndefOr[js.Function1[js.Any,_]] = js.undefined,
                onReturn: js.UndefOr[js.Function0[_]] = js.undefined): js.Dynamic = js.native
}
