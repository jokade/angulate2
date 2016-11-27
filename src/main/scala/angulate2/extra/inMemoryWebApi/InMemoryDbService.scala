//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.extra.inMemoryWebApi

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
abstract class InMemoryDbService extends js.Object {
  def createDb(): js.Any
}
