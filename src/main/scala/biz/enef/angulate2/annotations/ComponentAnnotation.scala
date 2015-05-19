// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade for the Angular2 ComponentAnnotation
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2.annotations

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("angular.ComponentAnnotation")
class ComponentAnnotation(annotations: js.Any) extends js.Object

object ComponentAnnotation {
  import js.Dynamic.literal

  def apply(selector: String,
            injectables: js.Array[js.Any] = null) : ComponentAnnotation = {
    val dict = js.Dictionary[Any]()
    dict("selector") = selector
    if (injectables != null)
      dict("injectables") = injectables
    new ComponentAnnotation(dict)
  }

}
