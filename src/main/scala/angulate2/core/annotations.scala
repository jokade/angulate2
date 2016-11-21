//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for the Angular2 runtime core annotations

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","NgModule")
object NgModule extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

@js.native
@JSImport("@angular/core","Component")
object Component extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

@js.native
@JSImport("@angular/core","Injectable")
object Injectable extends js.Object {
  def apply() : js.Object = js.native
}

@js.native
@JSImport("@angular/core","Directive")
class Directive(options: js.Object) extends js.Object