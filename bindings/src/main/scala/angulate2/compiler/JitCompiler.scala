//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.compiler

import angulate2.core.{ModuleWithComponentFactories, NgModuleFactory, Type}
import rxjs.RxPromise
import sun.security.pkcs11.Secmod.ModuleType

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/compiler","JitCompiler")
class JitCompiler extends js.Object {
  def compileModuleSync[T](moduleType: Type[T]): NgModuleFactory[T] = js.native
  def compileModuleAsync[T](moduleType: Type[T]): RxPromise[NgModuleFactory[T]] = js.native
  def compileModuleAndAllComponentsSync[T](moduleType: Type[T]): ModuleWithComponentFactories[T] = js.native
  def compileModuleAndAllComponentsAsync[T](moduleType: Type[T]): RxPromise[ModuleWithComponentFactories[T]] = js.native
}
