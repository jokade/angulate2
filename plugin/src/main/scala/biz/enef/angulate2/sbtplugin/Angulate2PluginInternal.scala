// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package de.surfice.angulate2.sbtplugin

import sbt.Keys.TaskStreams
import sbt._
import sbt.inc.Analysis
import xsbti.api.{Definition, Projection}

object Angulate2PluginInternal {

  def discoverAnnotations(analysis: Analysis) : Iterable[String] = {
    Tests.allDefs(analysis).collect {
      case AngulateAnnotated(annot) => annot
    }
  }

  def writeAnnotations(file: File, annotations: Iterable[String],streams: TaskStreams): Unit = {
    streams.log.info(s"Writing Angular annotations to $file")
    // TODO: currently we need to replace \' quotes; find out why/where quotation of ' occurs...
    val annots = annotations.map( _.replaceAll("\\\\'","'")).mkString("\n")
    IO.write(file,annots)
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
