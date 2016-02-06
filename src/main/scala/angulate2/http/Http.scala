//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade trait for Angular2 http.Http

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.http

import rxjs.core.IObservable

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
   * @param options
   */
  def get(url: String, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] = js.native

  /**
   * Performs a request with the `post` http method.
   *
   * @param url
   * @param body
   * @param options
   */
  def post(url: String, body: String, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] = js.native

  /**
   * Performs a request with the `put` http method.
   *
   * @param url
   * @param body
   * @param options
   */
  def put(url: String, body: String, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] = js.native

  /**
   * Performs a request with the `delete` http method.
   *
   * @param url
   * @param options
   */
  def delete(url: String, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] = js.native
}

object Http {
  implicit class RichHttp(val http: Http) extends AnyVal {
    @inline
    final def postObject[T](url: String, obj: T, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] =
      http.post(url,js.JSON.stringify(obj.asInstanceOf[js.Any]),options)
    @inline
    final def putObject[T](url: String, obj: T, options: js.UndefOr[RequestOptionsArgs] = js.undefined): IObservable[Response] =
      http.put(url,js.JSON.stringify(obj.asInstanceOf[js.Any]),options)
  }
}
