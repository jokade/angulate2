// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.annotation.StaticAnnotation

class Input() extends StaticAnnotation {
  def this(externalName: String) = this()
}
