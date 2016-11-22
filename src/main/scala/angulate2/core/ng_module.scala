//     Project: angulate2
//      Module: @angular/core/ng_module (v2.2.1)
// Description: Façade traits for @angular/core/ng_module

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js

/**
 * A wrapper around a module that also includes the providers.
 *
 * @note stable
 */
@js.native
trait ModuleWithProviders extends js.Object {
  def ngModule: js.Dynamic = js.native
  def providers: js.UndefOr[js.Array[Provider]] = js.native
}



