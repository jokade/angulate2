// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade for the Angular2 ComponentAnnotation
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2.annotations

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("angular.ComponentAnnotation")
class ComponentAnnotation(annotations: js.Object) extends js.Object

object ComponentAnnotation {
  import js.Dynamic.literal

  def apply(selector: String) : ComponentAnnotation = new ComponentAnnotation(literal(
    selector = selector
  ))
}
