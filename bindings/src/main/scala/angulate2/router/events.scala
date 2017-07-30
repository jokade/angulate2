//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","NavigationStart")
class NavigationStart(val id: Int, val url: String) extends js.Object

@js.native
@JSImport("@angular/router","NavigationEnd")
class NavigationEnd(val id: Int, val url: String, val urlAfterRedirects: String) extends js.Object

@js.native
@JSImport("@angular/router","NavigationCancel")
class NavigationCancel(val id: Int, val url: String, val reason: String) extends js.Object

@js.native
@JSImport("@angular/router","NavigationError")
class NavigationError(val id: Int, val url: String, val error: js.Any) extends js.Object
