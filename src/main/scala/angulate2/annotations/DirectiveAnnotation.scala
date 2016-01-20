//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade for the Angular2 DirectiveAnnotation

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.annotations

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("angular.DirectiveAnnotation")
@js.native
class DirectiveAnnotation(annotations: js.Any) extends js.Object

object DirectiveAnnotation {

  def apply(selector: String) : DirectiveAnnotation = {
    val dict = js.Dictionary[Any]()
    dict("selector") = selector
    new DirectiveAnnotation(dict)
  }

}
