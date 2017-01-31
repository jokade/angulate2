//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.dynamic

import angulate2.compiler.COMPILER_PROVIDERS
import angulate2.http.HttpModule
import angulate2.std._

@NgModule(
  imports = @@[HttpModule],
  declarations = @@[XNgIncludeDirective],
  exports = @@[XNgIncludeDirective],
  providers = @@@( @@[DynamicComponentBuilder,TemplateLoader], COMPILER_PROVIDERS )
)
class DynamicExtModule
