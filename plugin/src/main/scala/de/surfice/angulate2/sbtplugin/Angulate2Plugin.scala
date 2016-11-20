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

  object autoImport {
    val ngBootstrap = settingKey[Option[String]]("Name of the Component to bootstrap")
    val ngPlattform = settingKey[String]("Javascript for accessing the angular plattform object")
    val ngPreamble = settingKey[String]("ng2-specific preamble for the sjsx file")
    val ngScalaModule = settingKey[String]("Name of the Scala.js module to be loaded from the sjsx file")
  }
  import autoImport._

  override def projectSettings = Seq(
    ngBootstrap := None,
    ngPlattform := "require('@angular/platform-browser-dynamic').platformBrowserDynamic()",
    ngPreamble :=
      """var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        |  var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        |  if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        |  else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        |  return c > 3 && r && Object.defineProperty(target, key, r), r;
        |};
        |var __metadata = (this && this.__metadata) || function (k, v) {
        |  if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
        |};
      """.stripMargin,
    ngScalaModule := "scalaModule",
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies += DepBuilder.toScalaJSGroupID("de.surfice") %%% "angulate2" % Version.angulateVersion,
    sjsxSnippets += SJSXSnippet(0,ngPreamble.value),
    sjsxSnippets += SJSXSnippet(0,"var $s = require('"+ngScalaModule.value+"');"),
    sjsxSnippets <++= ((ngPlattform,ngBootstrap) map boostrap)
//    sjsxDeps <++= ngBootstrap map ( d => if(d.isDefined) Seq(SJSXDependency("ng.platform.browser","angular2/platform/browser")) else Nil )
  )

  private def boostrap(plattform: String, comp: Option[String]): Seq[SJSXSnippet] = comp map {comp =>
    val script = s"""$plattform.bootstrapModule($$s.$comp);"""
    Seq(SJSXSnippet(2000,script))
  } getOrElse Nil

  private object DepBuilder extends DependencyBuilders
}
