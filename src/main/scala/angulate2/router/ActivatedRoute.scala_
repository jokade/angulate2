package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSName
import rxjs.core.IObservable

@js.native
@JSName("ng.router.ActivatedRoute")
trait ActivatedRoute extends js.Object {
  val snapshot: ActivatedRouteSnapshot = js.native
  val url: IObservable[UrlPathWithParams] = js.native
  val params: IObservable[js.Dynamic] = js.native
  val data: IObservable[js.Any] = js.native
  val outlet: String = js.native
  val component: js.Any = js.native
}

@js.native
trait ActivatedRouteSnapshot extends js.Object {
  val url: js.Array[UrlPathWithParams] = js.native
  val params: js.Dynamic = js.native
  val data: js.Any = js.native
  val outlet: String = js.native
  val component: js.Any = js.native
}

@js.native
trait UrlPathWithParams extends js.Object {
  val path: String = js.native
  val parameters: Map[String, String] = js.native
}