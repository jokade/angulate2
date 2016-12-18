//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.internal

import de.surfice.smacrotools.MacroAnnotationHandler

import scala.reflect.macros.whitebox

trait MethodDecorator {
  self: ClassDecorator =>
  val c: whitebox.Context
  import c.universe._

  def findMethodDecorations(body: Seq[Tree], classDecoratorData: ClassDecoratorData, decorations: Seq[MethodDecoration]): Seq[MethodDecoration] = Seq()

  case class MethodDecoration(decoration: String,
                              paramtypes: Seq[String],
                              returntype: String)
}

