//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
import scala.language.experimental.macros
import scala.scalajs.js

package object angulate2 {

  final val angular : Angular = js.Dynamic.global.angular.asInstanceOf[Angular]

  def @@[T] : js.Any = macro Angular.Macros.jsClassOf[T]

}
