//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for @angular/router/router_state.ts (v2.2.1)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import rxjs.Observable
import rxjs.core.Subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","RouterState")
class RouterState(root: ActivatedRoute, val snapshot: RouterStateSnapshot) extends Tree[ActivatedRoute](root)

@js.native
@JSImport("@angular/router","routerState")
class RouterStateSnapshot(val url: String, root: ActivatedRouteSnapshot) extends Tree[ActivatedRouteSnapshot](root)

@js.native
@JSImport("@angular/router","ActivatedRoute")
class ActivatedRoute(val url: Observable[js.Array[UrlSegment]],
                     val params: Observable[Params],
                     val queryParams: Observable[Params],
                     val fragment: Observable[String],
                     val data: Observable[Data],
                     val outlet: String,
                     val component: js.Any,
                     futureSnapshot: ActivatedRouteSnapshot) extends js.Object {
//  def params: Observable[js.Dictionary[String]] = js.native
}
object ActivatedRoute {
  implicit final class RichActivatedRoute(val r: ActivatedRoute) extends AnyVal {
    @inline
    def dataAs[T]: Observable[T] = r.data.asInstanceOf[Observable[T]]
    @inline
    def subscribeData[T](key: String)(f: T=>_): Subscription = r.data.subscribe((d:Data) => f(d(key).asInstanceOf[T]) )
  }
}

@js.native
@JSImport("@angular/router","ActivatedRouteSnapshot")
class ActivatedRouteSnapshot(val url: js.Array[UrlSegment],
                             val params: Params,
                             val queryParams: Params,
                             val fragment: String,
                             val data: Data,
                             val outlet: String,
                             val component: js.Any,
                             routeConfig: Route,
                             urlSegment: UrlSegmentGroup,
                             lastPathIndex: Int,
                             resolve: ResolveData) extends js.Object {
}
