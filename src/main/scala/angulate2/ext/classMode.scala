//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Angulate2 annotations for explicit selection of the class mode (Scala/JS native) for annotated classes

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext

import scala.annotation.StaticAnnotation

/**
 * Marks a class annotated with a macro annotation (`@Component`, `@NgModule`, etc.)
 * to be translated into a normal Scala class.
 */
class classModeScala extends StaticAnnotation

/**
 * Marks a class annotated with a macro annotation (`@Component`, `@NgModule`, etc.)
 * to be translated into a native JS class (i.e. a `@ScalaJSDefined` class).
 */
class classModeJS extends StaticAnnotation

protected[angulate2] object ClassMode extends Enumeration {
  val Scala = Value
  val JS = Value
}
