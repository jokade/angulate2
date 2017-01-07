//     Project: angulate2
//      Module: @angular/router/config.ts (v2.2.1)
// Description: Façade traits for angular2 router configuration

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import angulate2.internal.JSType
import de.surfice.smacrotools.JSOptionsObject

import scala.scalajs.js

//@js.native
//trait Data extends js.Object

@js.native
trait ResolveData extends js.Object

@js.native
trait LoadChildren extends js.Object

@JSOptionsObject
case class Route(path: js.UndefOr[String] = js.undefined,
                 pathMatch: js.UndefOr[String] = js.undefined,
                 matcher: js.UndefOr[UrlMatcher] = js.undefined,
                 component: js.UndefOr[JSType] = js.undefined,
                 redirectTo: js.UndefOr[String] = js.undefined,
                 outlet: js.UndefOr[String] = js.undefined,
                 canActivate: js.UndefOr[js.Array[js.Any]] = js.undefined,
                 canActivateChild: js.UndefOr[js.Array[js.Any]] = js.undefined,
                 canDeactivate: js.UndefOr[js.Array[js.Any]] = js.undefined,
                 canLoad: js.UndefOr[js.Array[js.Any]] = js.undefined,
                 data: js.UndefOr[js.Any] = js.undefined,
                 resolve: js.UndefOr[js.Any] = js.undefined,
                 children: js.UndefOr[Routes] = js.undefined,
                 loadChildren: js.UndefOr[js.Any] = js.undefined)
