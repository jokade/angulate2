//     Project: angulate2
//      Module: @angular/router
// Description: Type aliases and top-level functions for @angular/router

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import scala.scalajs.js

package object router {

  type UrlMatcher = (js.Dynamic,js.Dynamic,Route) => js.Dynamic
  type Routes = js.Array[Route]

}
