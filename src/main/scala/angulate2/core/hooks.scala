//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade traits for the Angular2 runtime core lifecycle hooks

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import scala.scalajs.js

/**
 * Lifecycle hook that is called after data-bound properties of a directive are initialized.
 */
@js.native
trait OnInit extends js.Object {
  def ngOnInit(): Unit = js.native
}

/**
 * Lifecycle hook that is called when a directive or pipe is destroyed.
 */
@js.native
trait OnDestroy extends js.Object {
  def ngOnDestroy() : Unit
}