//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.common

import angulate2.core.ClassProvider
import angulate2.internal.JSType

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/common","LocationStrategy")
class LocationStrategy extends js.Object {
  def path(includeHash: js.UndefOr[Boolean] = js.undefined): String = js.native
  def prepareExternalUrl(internal: String): String = js.native
  def pushState(state: js.Any, title: String, url: String, queryParams: String): Unit = js.native
  def replaceState(state: js.Any, title: String, url: String, queryParams: String): Unit = js.native
  def forward(): Unit = js.native
  def back(): Unit = js.native
  def onPopState(fn: js.Function): Unit = js.native
  def getBaseHref(): String = js.native
}


@js.native
@JSImport("@angular/common","HashLocationStrategy")
class HashLocationStrategy extends LocationStrategy

@js.native
@JSImport("@angular/common","PathLocationStrategy")
class PathLocationStrategy extends LocationStrategy
