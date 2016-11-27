//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.extra.inMemoryWebApi

import angulate2.core.ModuleWithProviders
import angulate2.internal.JSType

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("angular-in-memory-web-api","InMemoryWebApiModule")
object InMemoryWebApiModule extends js.Object {
  def forRoot(dbCreator: JSType, options: js.UndefOr[InMemoryBackendConfigArgs] = js.undefined): ModuleWithProviders = js.native
}
