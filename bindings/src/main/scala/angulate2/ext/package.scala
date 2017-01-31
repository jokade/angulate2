//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2


import angulate2.ext.rt.AngulateRuntime

import scala.scalajs.js

package object ext {

  lazy val runtime: AngulateRuntime = {
    val config = js.Dynamic.global.angulateConfig.asInstanceOf[js.UndefOr[AngulateRuntime.AngulateConfig]]
      .getOrElse(AngulateRuntime.defaultConfig)
    new AngulateRuntime.Default(config)
  }
}
