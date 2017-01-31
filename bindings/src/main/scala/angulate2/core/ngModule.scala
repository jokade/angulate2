//     Project: angulate2
//      Module: @angular/core/ng_module (v2.2.1)
// Description: Façade traits for @angular/core/ng_module

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js

/**
 * A wrapper around a module that also includes the providers.
 *
 * @note stable
 */
@js.native
trait ModuleWithProviders extends js.Object {
  def ngModule: js.Dynamic = js.native
  def providers: js.UndefOr[js.Array[Provider]] = js.native
}

@js.native
trait ModuleWithComponentFactories[T] extends js.Object {
  def ngModuleFactory: NgModuleFactory[T] = js.native
  def componentFactories: js.Array[ComponentFactory[js.Dynamic]] = js.native
}

@js.native
trait NgModuleFactory[T] extends js.Object {
  def moduleType: Type[T] = js.native
  def create(parentInjector: Injector): NgModuleRef[T] = js.native
}

@js.native
trait NgModuleRef[T] extends js.Object {
  def injector: Injector = js.native
  def componentFactoryResolver: js.Dynamic = js.native
  def instance: T = js.native
}



