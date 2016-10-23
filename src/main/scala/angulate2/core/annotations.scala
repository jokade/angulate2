//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import sjsx.SJSXRequire

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.core.Component")
@js.native
@SJSXRequire("angular2/core","ng.core")
class Component(options: js.Object) extends js.Object

@JSName("ng.core.NgModule")
@js.native
@SJSXRequire("angular2/core", "ng.core")
class NgModule(options: js.Object) extends js.Object

@JSName("ng.core.Injectable")
@js.native
@SJSXRequire("angular2/core","ng.core")
class Injectable(options: js.Object) extends js.Object
