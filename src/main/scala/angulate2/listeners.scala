//     Project: angulate2
// Description: Traits for Angular2 core lifecycle hooks.

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, ScalaJSDefined}

/**
 * Implement this interface to execute custom initialization logic after your directive's data-bound properties have been initialized.
 *
 * @see [[https://angular.io/docs/ts/latest/api/core/OnInit-interface.html]]
 */
@ScalaJSDefined
trait OnInit extends js.Object {
  def ngOnInit(): Unit
}

/**
 * Implement this interface to get notified when your directive is destroyed.
 *
 * @see [[https://angular.io/docs/ts/latest/api/core/OnDestroy-interface.html]]
 */
@ScalaJSDefined
trait OnDestroy extends js.Object {
  def ngOnDestroy(): Unit
}
