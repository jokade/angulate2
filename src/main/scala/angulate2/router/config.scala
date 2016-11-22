//     Project: angulate2
//      Module: @angular/router/config.ts (v2.2.1)
// Description: Façade traits for angular2 router configuration

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import angulate2.internal.JSType
import de.surfice.smacrotools.JSOptionsObject

import scala.scalajs.js

@JSOptionsObject
case class Route(path: js.UndefOr[String] = js.undefined,
                 pathMatch: js.UndefOr[String] = js.undefined,
                 matcher: js.UndefOr[UrlMatcher] = js.undefined,
                 component: js.UndefOr[JSType] = js.undefined)
