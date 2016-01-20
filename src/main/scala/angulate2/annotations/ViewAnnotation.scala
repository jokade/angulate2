// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade for the Angular2 ViewAnnotation
//
// Distributed under the MIT License (see included file LICENSE)
package angulate2.annotations

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("angular.ViewAnnotation")
@js.native
class ViewAnnotation(annotations: js.Any) extends js.Object

// TODO: is there a more efficient way to create the annotation object? In particular w.r.t. the usage in macros ...
object ViewAnnotation {
  def apply(template: String = null,
            directives: js.Array[Any] = null) : ViewAnnotation = {
    val dict = js.Dictionary[Any]()
    if (template != null)
      dict("template") = template
    if (directives != null)
      dict("directives") = directives
    new ViewAnnotation(dict)
  }
}
