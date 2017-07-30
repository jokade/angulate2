//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package de.surfice.angulate2.sbtplugin

import de.surfice.sbtnpm.assets.AssetsPlugin
import de.surfice.sbtnpm.liteserver.LiteServerPlugin
import de.surfice.sbtnpm.sass.SassPlugin
import de.surfice.sbtnpm.systemjs.SystemJSPlugin
import de.surfice.sbtnpm.systemjs.SystemJSPlugin.SystemJSPackage
import de.surfice.sbtnpm.webpack.WebpackPlugin
import org.scalajs.sbtplugin.{ScalaJSPluginInternal, Stage}
import sbt.Keys._
import sbt._

object Angulate2BundlingPlugin extends AutoPlugin {
  type StageTask = TaskKey[Attributed[File]]

  override def requires =
    Angulate2Plugin &&
      AssetsPlugin &&
      WebpackPlugin &&
      SystemJSPlugin &&
      LiteServerPlugin &&
      SassPlugin

  object autoImport {

    val ngBundleConfig: SettingKey[Config] =
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

  import AssetsPlugin.autoImport._
  import SystemJSPlugin.autoImport._
  import WebpackPlugin.autoImport._
  import autoImport._
  import sjsx.sbtplugin.SJSXPlugin.autoImport._


  override def projectSettings = Seq(
  ) ++
    perScalaJSStageSettings(Stage.FullOpt) ++
    perScalaJSStageSettings(Stage.FastOpt)

  private def perScalaJSStageSettings(stage: Stage): Seq[Def.Setting[_]] = {
    val stageTask = ScalaJSPluginInternal.stageKeys(stage)

    Seq(
      defineNgConfig(stageTask)
    ) ++ defineBundling(stageTask)
  }

  private def defineNgConfig(scoped: StageTask) =
    ngBundleConfig in scoped := {
      if (scoped.key.label == "fullOptJS" )
      NgConfig.Webpack
      else
      NgConfig.SystemJS
    }

  private def defineBundling(scoped: StageTask): Seq[Def.Setting[_]] = Seq(
    webpackConfig in scoped :=  {
      val sjsx = (sjsxConfig in scoped).value
      val config = (ngBundleConfig in scoped).value
      val targetDir = (crossTarget in (NodeAssets,scoped)).value
      (webpackConfig in scoped).value
        .withMainEntry(sjsx.file)
        .withAliases(
          "scalaModule" -> (artifactPath in (Compile,scoped)).value.getAbsolutePath,
          "html" -> (targetDir / "classes" / "html").getAbsolutePath,
          "css" -> (targetDir / "classes" / "css").getAbsolutePath
        )
        .withPreamble(config.bundlePreamble)
        .withRules(WebpackRules.html,WebpackRules.css)
    },
    systemJSConfig in scoped := {
      val sjsx = (sjsxConfig in scoped).value
      (systemJSConfig in scoped).value
        .withPackages(Seq(
          "$app$" -> SystemJSPackage(
            main = Some("./" + sjsx.file.getName),
            format = Some("cjs"),
            defaultExtension = Some("js")
          )
        ))
        .addMappings(
          "scalaModule" -> ("./" + (artifactPath in (Compile,scoped)).value.getName)
        )
    },

    ngBundle in scoped <<= (ngBundleConfig in scoped,(webpack in scoped).task, (systemJS in scoped).task) apply { (config, webpack, systemJS) =>
      config.bundleMode match {
        case NgBundleMode.Webpack =>
          webpack.map(Some(_))
        case NgBundleMode.SystemJS =>
          systemJS.map(f => Some(f.file))
        case _ => task{
          None
        }
      }
    }
  )


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
