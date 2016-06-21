// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: sbt plugin for angulate2
//
// Distributed under the MIT License (see included file LICENSE)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package de.surfice.angulate2.sbtplugin

import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.impl.DependencyBuilders
import sbt.Keys._
import sbt._
import sjsx.sbtplugin.SJSXPlugin


object Angulate2Plugin extends sbt.AutoPlugin {
  import SJSXPlugin.autoImport._

  override def requires = ScalaJSPlugin && SJSXPlugin

  lazy val ngBootstrap = settingKey[Option[String]]("Name of the Component to bootstrap")

  override def projectSettings = Seq(
    ngBootstrap := None,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies += DepBuilder.toScalaJSGroupID("de.surfice") %%% "angulate2" % Version.angulateVersion,
    sjsxSnippets <++= (ngBootstrap map boostrap),
    sjsxDeps <++= ngBootstrap map ( d => if(d.isDefined) Seq(SJSXDependency("ng.platform.browser","angular2/platform/browser")) else Nil )
  )

  private def boostrap(comp: Option[String]): Seq[SJSXSnippet] = comp map {comp =>
    val script =
      s"""document.addEventListener('DOMContentLoaded', function() {
         |  ng.platform.browser.bootstrap($comp);
         |});
       """.stripMargin
    Seq(SJSXSnippet(Int.MaxValue,script))
  } getOrElse Nil

  private object DepBuilder extends DependencyBuilders
}
