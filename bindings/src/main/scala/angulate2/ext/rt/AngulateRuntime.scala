//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:

// Copyright (c) 2017 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2.ext.rt

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel, ScalaJSDefined}

@ScalaJSDefined
trait AngulateRuntime extends js.Object {
  /**
   * Takes the relative path of a resource to be loaded and returns its absolute path.
   *
   * @param arg1 relative path of the resource to be loaded
   */
  def $resource: js.Function1[String,String]

  /**
   * Takes the relative path of an HTML template to be loaded and returns its absolute path.
   *
   * @param arg1 relative path of the HTML template to be loaded
   */
  def $html: js.Function1[String,String]

  /**
   * Takes the relative path of a CSS style file to be loaded and returns its absolute path.
   *
   * @param arg1
   */
  def $css: js.Function1[String,String]

  def decorate: js.Function2[js.Array[js.Any],js.Any,js.Any]
}

object AngulateRuntime {

  var defaultConfig: AngulateConfig = js.Dynamic.literal(
    resourcePrefix = ""
  ).asInstanceOf[AngulateConfig]

  @js.native
  trait AngulateConfig extends js.Object {
    def resourcePrefix: js.UndefOr[String] = js.native
    val resource: js.UndefOr[js.Function1[String,String]] = js.native
    val htmlPrefix: js.UndefOr[String] = js.native
    val html: js.UndefOr[js.Function1[String,String]] = js.native
    val css: js.UndefOr[js.Function1[String,String]] = js.native
  }

  @ScalaJSDefined
  final class Default(config: AngulateConfig) extends AngulateRuntime {
    private val prefix = config.resourcePrefix.getOrElse("")

    @inline
    private def ensureSuffix(path: String, suffix: String): String =
      if(path.endsWith(suffix)) path
      else path + suffix

    override val $resource = config.resource.getOrElse( ((relPath: String) => prefix + "/" + relPath):js.Function1[String,String] )

    override val $html = config.html.getOrElse {
      val p = config.htmlPrefix.getOrElse(prefix + "html")
      ((relPath: String) => ensureSuffix(p + "/" + relPath, ".html")): js.Function1[String, String]
    }

    override val $css = config.css.getOrElse {
      val p = prefix + "css"
      ((relPath: String) => ensureSuffix(p + "/" +relPath, ".css")):js.Function1[String,String]
    }

    override val decorate = AngulateRuntimeSJSXConfig.decorate.getOrElse(???)
  }

}

/**
 * This object contains default values defined in the project's SJSX file.
 */
@JSExport
object AngulateRuntimeSJSXConfig {
  @JSExport
  var decorate: js.UndefOr[js.Function2[js.Array[js.Any],js.Any,js.Any]] = js.undefined
}