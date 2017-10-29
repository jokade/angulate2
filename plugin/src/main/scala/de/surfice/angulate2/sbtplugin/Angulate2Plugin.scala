// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: sbt plugin for angulate2
//
// Distributed under the MIT License (see included file LICENSE)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package de.surfice.angulate2.sbtplugin

import org.scalajs.sbtplugin.{ScalaJSPlugin, ScalaJSPluginInternal, Stage}
import org.scalajs.sbtplugin.impl.DependencyBuilders
import sbt.Keys._
import sbt._
import sjsx.sbtplugin.SJSXPlugin


object Angulate2Plugin extends sbt.AutoPlugin {

  override def requires = ScalaJSPlugin && SJSXPlugin

  object autoImport {
    val ngBootstrap = settingKey[Option[String]]("Name of the Component to bootstrap")
    val ngPlattform = settingKey[String]("Javascript for accessing the angular plattform object")
    val ngPreamble = settingKey[String]("ng2-specific preamble for the sjsx file")
    val ngScalaModule = settingKey[String]("Name of the Scala.js module to be loaded from the sjsx file")
    val ngEnableProdMode = settingKey[Boolean]("Set to true to enable Angular's production mode at runtime")
    val ngComponentTemplatePrefix = settingKey[String]("Prefix added to the component template path loaded by the @LoadTemplate() annotation")
    val ngComponentStylesPrefix = settingKey[String]("Prefix added to component stylesheet path loaded by the @LoadStyles() annotation")

  }

  import SJSXPlugin.autoImport._
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
        |var __loadTemplate = (this && this.__loadTemplate) || function(target, template) {
        |  target._annotation.template = template;
        |};
        |var __loadStyles = (this && this.__loadStyles) || function(target, styles) {
        |  target._annotation.styles = styles;
        |};
        |var core = require('@angular/core');
      """.stripMargin,
    ngScalaModule := "scalaModule",
    ngEnableProdMode := false,
    ngComponentTemplatePrefix := "",
    ngComponentStylesPrefix := "",

    scalacOptions ++= Seq(
      "-Xmacro-settings:angulate2.templatePrefix="+ngComponentTemplatePrefix.value,
      "-Xmacro-settings:angulate2.stylePrefix="+ngComponentStylesPrefix.value
    ),

    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies ++= Seq("angulate2","angulate2-extensions") map { dep =>
      DepBuilder.toScalaJSGroupID("de.surfice") %%% dep % Version.angulateVersion
    }
//    sjsxSnippets += SJSXSnippet(0,ngPreamble.value),
//    sjsxSnippets += SJSXSnippet(0,
//      s"""var s = require('${ngScalaModule.value}');
//        |
//        |var config = s.angulate2.ext.rt.AngulateRuntimeSJSXConfig();
//        |config.decorate = __decorate;
//      """.stripMargin),
//    sjsxSnippets <++= ((ngPlattform,ngBootstrap,ngEnableProdMode) map boostrap)
  ) ++
    perScalaJSStageSettings(Stage.FullOpt) ++
    perScalaJSStageSettings(Stage.FastOpt)

  private def perScalaJSStageSettings(stage: Stage): Seq[Def.Setting[_]] = {
    val stageTask = ScalaJSPluginInternal.stageKeys(stage)

    Seq(
      sjsxConfig in stageTask := {
        (sjsxConfig in stageTask).value
          .withSnippets(Seq(
            SJSXSnippet(0,ngPreamble.value),
            SJSXSnippet(0,
              s"""var s = require('${ngScalaModule.value}');
                 |
                 |var config = s.angulate2.ext.rt.AngulateRuntimeSJSXConfig();
                 |config.decorate = __decorate;
               """.stripMargin)
          ))
          .addSnippets(bootstrap(ngPlattform.value,ngBootstrap.value,ngEnableProdMode.value))
      }
    )
  }


  private def bootstrap(plattform: String, comp: Option[String], enableProdMode: Boolean): Seq[SJSXSnippet] = comp map {comp =>
    val script =
      s"""${if(enableProdMode) "core.enableProdMode();" else ""}
         |$plattform.bootstrapModule(s.$comp);""".stripMargin
    Seq(SJSXSnippet(2000,script))
  } getOrElse Nil



  private object DepBuilder extends DependencyBuilders


}
