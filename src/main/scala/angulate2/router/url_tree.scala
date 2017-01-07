//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/router","UrlTree")
class UrlTree(val root: UrlSegmentGroup, val queryParams: js.Dictionary[String], val fragment: String) extends js.Object {

}

@js.native
@JSImport("@angular/router","UrlSegmentGroup")
class UrlSegmentGroup(val segments: js.Array[UrlSegment], children: js.Dictionary[UrlSegmentGroup]) extends js.Object {

}

@js.native
@JSImport("@angular/router","UrlSegment")
class UrlSegment(val path: String, val parameters: js.Dictionary[String]) extends js.Object
