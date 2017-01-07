//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for the Angular2 runtime core lifecycle hooks

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import de.surfice.smacrotools.createJS

import scala.scalajs.js

/**
 * Lifecycle hook that is called after data-bound properties of a directive are initialized.
 */
@createJS
trait OnInit {
  def ngOnInit(): Unit
}

/**
 * Lifecycle hook that is called when a directive or pipe is destroyed.
 */
@createJS
trait OnDestroy {
  def ngOnDestroy() : Unit
}

/**
 * Lifecycle hook that is called when any data-bound property of a directive changes.
 */
@createJS
trait OnChanges {
  def ngOnChanges(changes: OnChanges.SimpleChanges) : Unit
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
@createJS
trait DoCheck {
  def ngDoCheck(): Unit
}

/**
 * Lifecycle hook that is called after a directive's content has been fully initialized.
 */
@createJS
trait AfterContentInit {
  def ngAfterContentInit(): Unit
}

/**
 * Lifecycle hook that is called after every check of a directive's content.
 */
@createJS
trait AfterContentChecked {
  def ngAfterContentChecked(): Unit
}

/**
 * Lifecycle hook that is called after a component's view has been fully initialized.
 */
@createJS
trait AfterViewInit {
  def ngAfterViewInit(): Unit
}

/**
 * Lifecycle hook that is called after every check of a component's view.
 */
@createJS
trait AfterViewChecked {
  def ngAfterViewChecked(): Unit
}

