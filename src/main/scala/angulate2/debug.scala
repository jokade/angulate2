//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Annotation to enable debugging of its annottee

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import scala.annotation.StaticAnnotation

/**
 * Enables debugging of an annotated angulate2 class during macro expansion and/or at runtime.
 *
 * @param showExpansion If true, the expanded macro code is logged during compilation
 * @param logInstances If true, log every instantiation during runtime
 */
class debug(showExpansion: Boolean = true,
            logInstances: Boolean = true) extends StaticAnnotation

object debug {
  private[angulate2] case class DebugConfig(showExpansion: Boolean,
                                            logInstances: Boolean)
  private[angulate2] lazy val defaultDebugConfig = DebugConfig(false,false)
}
