// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation

class Input() extends StaticAnnotation {
  def this(externalName: String) = this()
}


