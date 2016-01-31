//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.http

import scala.scalajs.js

/**
 * Represents the response to an HTTP request.
 *
 * @see [[https://angular.io/docs/js/latest/api/http/Response-class.html]]
 */
@js.native
trait Response extends js.Object {
  /**
   * True if the response's status is within 200-299
   */
  def ok: Boolean = js.native

  /**
   * Status code returned by server.
   */
  def status: Int = js.native

  /**
   * Text representing the corresponding reason phrase to the [[status]].
   */
  def statusText: String = js.native

  def bytesLoaded: Int = js.native
  def totalBytes: Int = js.native

  def json[T<:js.Any](): T = js.native
}
