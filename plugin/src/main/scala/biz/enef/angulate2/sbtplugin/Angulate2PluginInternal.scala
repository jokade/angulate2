// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2.sbtplugin

import sbt._
import sbt.inc.Analysis
import xsbti.api.{Definition, Projection}

object Angulate2PluginInternal {

  def writeAnnotations(file: String, annotations: Iterable[String]) : Unit = {
    println("WRITING ANNOTATIONS")
  }

  def discoverAnnotations(analysis: Analysis) : Iterable[String] = {

    Tests.allDefs(analysis).collect {
      case AngulateAnnotated(annot) => annot
    }
  }

  object AngulateAnnotated {
    val annotated = "AngulateAnnotated"
    def unapply(t: Definition) : Option[String] = t.annotations().
      find( _.base().asInstanceOf[Projection].id == annotated ).
      map { l =>
        val s = l.arguments.apply(0).value()
        s.substring(2,s.length-2)
      }
  }
}
