//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/core","ElementRef")
class ElementRef(_nativeElement: js.Any) extends js.Any {
  def nativeElement: js.Dynamic = js.native
}

object ElementRef {
  import org.scalajs.dom.html.Element

  final implicit class RichElementRef(val e: ElementRef) extends AnyVal {
    def htmlElement: Element = e.nativeElement.asInstanceOf[Element]
  }
}

@js.native
trait ComponentFactory[T] extends js.Object {
  def selector: String = js.native
  def create(injector: Injector,
             projectableNodes: js.UndefOr[js.Array[js.Array[js.Any]]] = js.undefined,
             rootSelectorOrNode: js.UndefOr[js.Any] = js.undefined): ComponentRef[T] = js.native
}

@js.native
trait ComponentRef[T] extends js.Object {
  def location: ElementRef = js.native
  def injector: Injector = js.native
  def instance: T = js.native
  def hostView: ViewRef = js.native
}

@js.native
trait ViewRef extends js.Object {
  def destroy(): Unit = js.native
  def destroyed: Boolean = js.native
  def onDestroy(callback: js.Function): Unit = js.native
}

@js.native
@JSImport("@angular/core","ViewContainerRef")
class ViewContainerRef extends js.Object {
  def element: ElementRef = js.native
  def injector: Injector = js.native
  def parentInjector: Injector = js.native
  def clear(): Unit = js.native
  def get(index: Int): ViewRef = js.native
  def length: Int = js.native
  def createComponent[T](componentFactory: ComponentFactory[T],
                         index: js.UndefOr[Int] = js.undefined,
                         injector: js.UndefOr[Injector] = js.undefined,
                         projectableNodes: js.UndefOr[js.Array[js.Array[js.Any]]] = js.undefined): ComponentRef[T] = js.native
  def insert(viewRef: ViewRef, index: js.UndefOr[Int] = js.undefined): ViewRef = js.native
  def move(viewRef: ViewRef, currentIndex: Int): ViewRef = js.native
  def indexOf(viewRef: ViewRef): Int = js.native
  def remove(index: js.UndefOr[Int] = js.undefined): Unit = js.native
  def detach(index: js.UndefOr[Int]): ViewRef = js.native
}