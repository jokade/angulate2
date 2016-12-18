//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for the Angular2 runtime core lifecycle hooks

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
 * Lifecycle hook that is called after data-bound properties of a directive are initialized.
 */
trait OnInit {
  def ngOnInit(): Unit
}

@ScalaJSDefined
trait OnInitJS extends js.Object {
  def ngOnInit(): Unit
}

/**
 * Lifecycle hook that is called when a directive or pipe is destroyed.
 */
trait OnDestroy {
  def ngOnDestroy() : Unit
}

@ScalaJSDefined
trait OnDestroyJS extends js.Object {
  def ngOnDestroy() : Unit
}

/**
 * Lifecycle hook that is called when any data-bound property of a directive changes.
 */
trait OnChanges {
  import OnChanges._
  def ngOnChanges(changes: SimpleChanges) : Unit
}

@ScalaJSDefined
trait OnChangesJS extends js.Object {
  import OnChanges._
  def ngOnChanges(changes: SimpleChanges) : Unit
}

object OnChanges {
  type SimpleChanges = js.Dictionary[SimpleChange]
}

@js.native
trait SimpleChange extends js.Any {
  def previousValue: Any = js.native
  def currentValue: Any = js.native
  def isFirstChange(): Boolean = js.native
}

/**
 * Lifecycle hook that is called when Angular dirty checks a directive.
 */
trait DoCheck {
  def ngDoCheck(): Unit
}

@ScalaJSDefined
trait DoCheckJS extends js.Object {
  def ngDoCheck(): Unit
}

/**
 * Lifecycle hook that is called after a directive's content has been fully initialized.
 */
trait AfterContentInit {
  def ngAfterContentInit(): Unit
}

@ScalaJSDefined
trait AfterContentInitJS extends js.Object {
  def ngAfterContentInit(): Unit
}

/**
 * Lifecycle hook that is called after every check of a directive's content.
 */
trait AfterContentChecked {
  def ngAfterContentChecked(): Unit
}

@ScalaJSDefined
trait AfterContentCheckedJS extends js.Object {
  def ngAfterContentChecked(): Unit
}

/**
 * Lifecycle hook that is called after a component's view has been fully initialized.
 */
trait AfterViewInit {
  def ngAfterViewInit(): Unit
}

@ScalaJSDefined
trait AfterViewInitJS extends js.Object {
  def ngAfterViewInit(): Unit
}

/**
 * Lifecycle hook that is called after every check of a component's view.
 */
trait AfterViewChecked {
  def ngAfterViewChecked(): Unit
}

@ScalaJSDefined
trait AfterViewCheckedJS extends js.Object {
  def ngAfterViewChecked(): Unit
}
