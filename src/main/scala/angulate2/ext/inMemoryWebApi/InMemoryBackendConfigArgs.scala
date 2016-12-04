//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.inMemoryWebApi

import de.surfice.smacrotools.JSOptionsObject

import scala.scalajs.js

@JSOptionsObject
case class InMemoryBackendConfigArgs(caseSensitiveSearch: js.UndefOr[Boolean] = js.undefined,
                                     defaultResponseOptions: js.UndefOr[js.Dynamic] = js.undefined,
                                     delay: js.UndefOr[Int] = js.undefined,
                                     delete404: js.UndefOr[Boolean] = js.undefined,
                                     passThruUnknownUrl: js.UndefOr[Boolean] = js.undefined,
                                     host: js.UndefOr[String] = js.undefined,
                                     rootPath: js.UndefOr[String] = js.undefined)
