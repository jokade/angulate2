//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2

import scala.annotation.StaticAnnotation
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName



@JSName("ng")
@js.native
trait Angular extends js.Object {

  def bootstrap(appComponentType: js.Object) : Unit = js.native

  def bootstrap(appComponentType: js.Dynamic) : Unit = js.native

  //--------------- DIRECTIVES -----------------
  def NgFor: js.Object = js.native
  def NgIf: js.Object = js.native

}