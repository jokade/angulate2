// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade for the Angular2 ViewAnnotation
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2.annotations

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("angular.ViewAnnotation")
class ViewAnnotation(annotations: js.Object) extends js.Object

object ViewAnnotation {
  import js.Dynamic.literal
  def apply(template: String) : ViewAnnotation = new ViewAnnotation(literal(
    template = template
  ))
}
