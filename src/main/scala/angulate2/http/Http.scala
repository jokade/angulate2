//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.http

import rxjs.core.Observable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
 * Performs HTTP requests using `XMLHttpRequest` as the default backend.
 *
 * @see [[https://angular.io/docs/js/latest/api/http/Http-class.html]]
 */
@js.native
@JSName("ng.http.Http")
trait Http extends js.Object {
  /**
   * Perfomrs a request with the `get` http method.
   *
   * @param url
   */
  def get(url: String): Observable[Response] = js.native
}
