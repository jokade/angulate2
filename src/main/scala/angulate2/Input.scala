package angulate2

import scala.annotation.StaticAnnotation

class Input() extends StaticAnnotation {
  def this(externalName: String) = this()
}
