//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.JSType

import scala.annotation.StaticAnnotation

class ViewChild(selector: String, read: JSType = null) extends StaticAnnotation
