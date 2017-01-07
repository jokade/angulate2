//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import rxjs.{Observable, RxPromise}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","Router")
class Router extends js.Object {
  def errorHandler: js.Dynamic = js.native
  def navigated: Boolean = js.native
  def urlHandlingStrategy: js.Dynamic = js.native
  def config: Routes = js.native
  def initialNavigation(): Unit = js.native
  def setUpLocationChangeListener(): Unit = js.native
  def routerState: js.Dynamic = js.native
  def url: String = js.native
  def events: Observable[js.Dynamic] = js.native
  def resetConfig(config: Routes): Unit = js.native
  def dispose(): Unit = js.native
  def createUrlTree(commands: js.Array[js.Any], extras: js.UndefOr[js.Object] = js.undefined): UrlTree = js.native
  def navigateByUrl(url: js.|[String,UrlTree], extras: js.UndefOr[js.Object] = js.undefined): RxPromise[Boolean] = js.native
  def navigate(commands: js.Array[js.Any], extras: js.UndefOr[js.Object] = js.undefined): RxPromise[Boolean] = js.native
  def serializeUrl(urlTree: UrlTree): String = js.native
  def parseUrl(url: String): UrlTree = js.native
  def isActive(url: js.|[String,UrlTree], exact: Boolean): Boolean = js.native
}

object Router {
  implicit final class RichRouter(val r: Router) extends AnyVal {
    import scalajs.js.JSConverters._
    import js.Dynamic.literal

    /**
     * Use `navigateTo("/foo",bar.id)`
     * in place of `navigate(js.Array("/foo",bar.id))`.
     *
     * @param commands routing commands
     */
    @inline
    def navigateTo(commands: js.Any*): RxPromise[Boolean] = r.navigate(commands.toJSArray)

    @inline
    def navigateTo(extras: NavigationExtras)(commands: js.Any*): RxPromise[Boolean] = r.navigate(commands.toJSArray,extras)

    /**
     * Use `navigateRelativeTo(route,bar.id)`
     * in place of `navigate(js.Array(bar.id), js.Dynamic.literal(relativeTo = route))`
     */
    @inline
    def navigateRelativeTo(route: ActivatedRoute, commands: js.Any*): RxPromise[Boolean] =
      r.navigate(commands.toJSArray,literal(relativeTo = route))
  }
}
