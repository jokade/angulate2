//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext

import scala.scalajs.js

package object data {
  import scala.scalajs.js.JSConverters._

  implicit def optionToUndef[T](o: Option[T]): js.UndefOr[T] = o match {
    case Some(x) => x
    case None => js.undefined
  }

  @inline
  implicit def iterableToArray[T](it: Iterable[T]): js.Array[T] = it.toJSArray

  @inline
  implicit def mapToDictionary[K,V](map: Map[K,V]): js.Dictionary[V] = map.map(p => (p._1.toString,p._2)).toJSDictionary

}
