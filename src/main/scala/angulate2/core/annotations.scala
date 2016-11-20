//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import sjsx.SJSXRequire

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

@js.native
@JSImport("@angular/core","NgModule")
object NgModule extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

@js.native
//@JSName("ng.core.Component")
//@SJSXRequire("@angular/core","ng.core")
@JSImport("@angular/core","Component")
object Component extends js.Object {
  def apply(options: js.Object) : js.Object = js.native
}

@js.native
@JSName("ng.core.Injectable")
@SJSXRequire("angular2/core","ng.core")
class Injectable(options: js.Object) extends js.Object

@js.native
@JSName("ng.core.Directive")
@SJSXRequire("angular2/core","ng.core")
class Directive(options: js.Object) extends js.Object