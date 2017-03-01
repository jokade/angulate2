//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.http

import org.scalajs.dom.Blob

import scala.scalajs.js
import scala.scalajs.js.typedarray.ArrayBuffer

@js.native
trait Body extends js.Object {
  def json(): js.Dynamic = js.native
  def text(): String = js.native
  def arrayBuffer(): ArrayBuffer = js.native
  def blob(): Blob = js.native
}

@js.native
trait Response extends Body {
  def ok: Boolean = js.native
  def url: String = js.native
  def status: Int = js.native
  def statusText: String = js.native
  def bytesLoaded: Int = js.native
  def totalBytes: Int = js.native
}

object Response {

  implicit final class RichResponse(val r: Response) extends AnyVal {
    def jsonData[T<:js.Any]: T = r.json().data.asInstanceOf[T]
  }
}
