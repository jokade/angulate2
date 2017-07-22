// -   Project: angulate2 (https://github.com/jokade/angulate2)
// Description: sbt plugin for angulate2
//
// Distributed under the MIT License (see included file LICENSE)

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package de.surfice.angulate2.sbtplugin

import de.surfice.sbtnpm.systemjs.SystemJSPlugin
import de.surfice.sbtnpm.webpack.WebpackPlugin
import org.scalajs.sbtplugin.{ScalaJSPlugin, ScalaJSPluginInternal, Stage}
import org.scalajs.sbtplugin.impl.DependencyBuilders
import sbt.Keys._
import sbt._
import sjsx.sbtplugin.SJSXPlugin


object Angulate2Plugin extends sbt.AutoPlugin {
  import SJSXPlugin.autoImport._
  type StageTask = TaskKey[Attributed[File]]

  override def requires = ScalaJSPlugin && SJSXPlugin && WebpackPlugin && SystemJSPlugin

  object autoImport {
    val ngBootstrap = settingKey[Option[String]]("Name of the Component to bootstrap")
    val ngPlattform = settingKey[String]("Javascript for accessing the angular plattform object")
    val ngPreamble = settingKey[String]("ng2-specific preamble for the sjsx file")
    val ngScalaModule = settingKey[String]("Name of the Scala.js module to be loaded from the sjsx file")
    val ngEnableProdMode = settingKey[Boolean]("Set to true to enable Angular's production mode at runtime")

    val ngAngularVersion: SettingKey[String] =
      settingKey[String]("Angular version to be used at runtime")

    val ngAngularPackages: SettingKey[Seq[String]] =
      settingKey[Seq[String]]("Angular packages required at runtime")

    val ngOtherPackages: SettingKey[Seq[(String,String)]] =
      settingKey[Seq[(String,String)]]("Additional packages required by Angular at runtime")

    val ngNpmDependencies: SettingKey[Seq[(String,String)]] =
      settingKey[Seq[(String,String)]]("Angular NPM dependencies")

    val ngConfig: SettingKey[Config] =
      settingKey[Config]("Angulate2 configuration scoped to fastOptJS or fullOptJS")

    val ngBundle: TaskKey[Option[File]] =
      taskKey[Option[File]]("Runs the defined bundler and returns the bundle file (scoped to fastOptJS or fullOptJS)")

    object NgConfig {
      val Webpack = ngDefaultConfig
        .withBundleMode(NgBundleMode.Webpack)
        .withPreamble(
          """import 'reflect-metadata';
            |require('zone.js/dist/zone');
          """.stripMargin)

      val SystemJS = ngDefaultConfig
        .withBundleMode(NgBundleMode.SystemJS)
    }

    object NgBundleMode extends Enumeration {
      val Custom = Value
      val SystemJS = Value
      val Webpack = Value
    }

    val ngDefaultConfig = Config(
      bundleMode = NgBundleMode.Custom,
      bundlePreamble = "",
      emitResourceImports = false)
  }

  import autoImport._
  import de.surfice.sbtnpm.NpmPlugin.autoImport._
  import WebpackPlugin.autoImport._
  import SystemJSPlugin.autoImport._
  import SJSXPlugin.autoImport._

  override def projectSettings = Seq(
    ngAngularVersion := NPM.angularVersion,
    ngAngularPackages := NPM.angularPackages,
    ngOtherPackages := NPM.npmDependencies,
    ngNpmDependencies := {
      val ngVersion = ngAngularVersion.value
      ngAngularPackages.value.map((_,ngVersion)) ++ ngOtherPackages.value
    },
    npmDependencies ++= ngNpmDependencies.value,
    npmDevDependencies ++= NPM.npmDevDependencies,
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

    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies ++= Seq("angulate2","angulate2-extensions") map { dep =>
      DepBuilder.toScalaJSGroupID("de.surfice") %%% dep % Version.angulateVersion
    },
    sjsxSnippets += SJSXSnippet(0,ngPreamble.value),
    sjsxSnippets += SJSXSnippet(0,
      s"""var s = require('${ngScalaModule.value}');
        |
        |var config = s.angulate2.ext.rt.AngulateRuntimeSJSXConfig();
        |config.decorate = __decorate;
      """.stripMargin),
    sjsxSnippets <++= ((ngPlattform,ngBootstrap,ngEnableProdMode) map boostrap)
  ) ++
    perScalaJSStageSettings(Stage.FullOpt) ++
    perScalaJSStageSettings(Stage.FastOpt)

  private def boostrap(plattform: String, comp: Option[String], enableProdMode: Boolean): Seq[SJSXSnippet] = comp map {comp =>
    val script =
      s"""${if(enableProdMode) "core.enableProdMode();" else ""}
         |$plattform.bootstrapModule(s.$comp);""".stripMargin
    Seq(SJSXSnippet(2000,script))
  } getOrElse Nil

  private def perScalaJSStageSettings(stage: Stage): Seq[Def.Setting[_]] = {
    val stageTask = ScalaJSPluginInternal.stageKeys(stage)

    Seq(
      defineNgConfig(stageTask)
    ) ++ defineBundling(stageTask)
  }

  private def defineNgConfig(scoped: StageTask) =
    ngConfig in scoped := {
      if (scoped.key.label == "fullOptJS" )
      NgConfig.Webpack
      else
      NgConfig.SystemJS
    }

  private def defineBundling(scoped: StageTask): Seq[Def.Setting[_]] = Seq(
    webpackConfig in scoped <<= (webpackConfig in scoped,ngConfig in scoped, sjsxFile,artifactPath in (Compile,scoped), crossTarget in (Compile,scoped))
      apply { (webpackConfig,ngConfig,sjsxFile,artifactPath,crossTarget) =>
      webpackConfig
        .withMainEntry(sjsxFile)
        .withAliases(
          "scalaModule" -> artifactPath.getAbsolutePath,
          "html" -> (crossTarget / "classes" / "html").getAbsolutePath,
          "css" -> (crossTarget / "classes" / "css").getAbsolutePath
        )
        .withPreamble(ngConfig.bundlePreamble)
        .withRules(WebpackRules.html,WebpackRules.css)
    },
    ngBundle in scoped <<= (ngConfig in scoped,(webpack in scoped).task) apply { (config,webpack) =>
      config.bundleMode match {
        case NgBundleMode.Webpack =>
          webpack.map(Some(_))
        case _ => task{
          None
        }
      }
    }
  )

  private object DepBuilder extends DependencyBuilders

  /**
   *
   * @param bundleMode bundler to be used
   * @param emitResourceImports If true, ES6 import statements are emitted for resources required by Components
   */
  case class Config private[angulate2](bundleMode: NgBundleMode.Value,
                                       bundlePreamble: String,
                                       emitResourceImports: Boolean) {
    def withBundleMode(mode: NgBundleMode.Value): Config = copy(bundleMode = mode)
    def withEmitResourceImports(flag: Boolean): Config = copy(emitResourceImports = flag)
    def withPreamble(preamble: String): Config = copy(bundlePreamble = preamble)
    def appendToPreamble(chunk: String): Config = copy(bundlePreamble = s"$bundlePreamble\n$chunk")
  }
}
