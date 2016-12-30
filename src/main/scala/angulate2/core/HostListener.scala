//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Façade/implementation of @angular/core/HostListener

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.core

import angulate2.internal.ClassDecorator

import scala.annotation.StaticAnnotation
import scala.scalajs.js

class HostListener(eventName: String,
                   args: js.UndefOr[js.Array[String]] = js.undefined) extends StaticAnnotation


