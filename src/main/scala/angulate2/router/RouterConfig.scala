package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
@JSName("ng.router.RouterConfig")
trait RouterConfig extends js.Object

object RouterConfig {
  def apply(defs: RouteDefinition*): RouterConfig = {
    js.Array(defs: _*).asInstanceOf[RouterConfig]
  }
}