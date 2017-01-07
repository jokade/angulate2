//     Project: angulate2
// Description: Façade traits for @angular/router/router_module.ts (v2.2.1)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import angulate2.core.ModuleWithProviders

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","RouterModule")
class RouterModule extends js.Object

@js.native
@JSImport("@angular/router","RouterModule")
object RouterModule extends js.Object {
  def forRoot(routes: Routes, config: js.UndefOr[js.Dynamic] = js.undefined): ModuleWithProviders = js.native
  def forChild(routes: Routes): ModuleWithProviders = js.native
}
