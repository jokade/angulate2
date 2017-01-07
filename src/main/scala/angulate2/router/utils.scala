//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","Tree")
class Tree[T](val root: T) extends js.Object {
}

@js.native
@JSImport("@angular/router","TreeNode")
class TreeNode[T](val value: T, val children: js.Array[TreeNode[T]]) extends js.Object
