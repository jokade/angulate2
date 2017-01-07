//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.http

import scala.scalajs.js

@js.native
trait Response extends js.Object {
  def json(): js.Dynamic = js.native
}

object Response {

  implicit final class RichResponse(val r: Response) extends AnyVal {
    def jsonData[T<:js.Any]: T = r.json().data.asInstanceOf[T]
  }
}
