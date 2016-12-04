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

/**
 * Lifecycle hook that is called when a directive or pipe is destroyed.
 */
trait OnDestroy {
  def ngOnDestroy() : Unit
}