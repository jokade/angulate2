// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: sbt plugin for angulate2
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate2.sbtplugin

import sbt.Keys._
import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin

object Angulate2Plugin extends sbt.AutoPlugin {
  import Angulate2PluginInternal._
  import ScalaJSPlugin.AutoImport.{fastOptJS,fullOptJS}

  override def requires = ScalaJSPlugin

  lazy val angulateAnnotationsFile = settingKey[File]("target file")

  lazy val angulateAnnotated = taskKey[Iterable[String]]("List with all annotated angular types")

  lazy val angulateWriteAnnotations = taskKey[Unit]("Write the JS file with Angular2 annotations")

  override def projectSettings = Seq(
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full),
    angulateAnnotationsFile := (crossTarget in compile).value / "annotations.js",
    angulateAnnotated := discoverAnnotations((compile in Compile).value),
    angulateWriteAnnotations <<= (angulateAnnotationsFile, angulateAnnotated, streams) map {
      (file: File, annotations: Iterable[String],streams: TaskStreams) =>
        streams.log.info(s"Writing Angular annotations to $file")
        IO.write(file,annotations.mkString("\n"))
    },
    (fastOptJS in Compile) <<= (fastOptJS in Compile).dependsOn(angulateWriteAnnotations)
  )

}
