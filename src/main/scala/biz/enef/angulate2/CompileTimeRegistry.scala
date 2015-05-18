// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Provides a registry for all annotated types detected during macro expansion
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2

private[angulate2] object CompileTimeRegistry {

  private var _injectables = Map.empty[String,Any]
  def injectables = _injectables
  def registerInjectable(fullName: String, tree: Any) : Unit = this.synchronized{ _injectables += fullName -> tree }

  private var _components = Map.empty[String,Any]
  def components = _components
  def registerComponent(fullName: String, tree: Any) : Unit = this.synchronized( _components += fullName -> tree )

  def annottees : Iterable[(String,Any)] = components.toIterable ++ injectables.toIterable
}
