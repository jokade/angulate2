//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Object to allow simple import of the most commonly used annotations and operations

// Copyright (c) 2016 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included LICENSE file)
package angulate2

import angulate2.core.AnimationEntryMetadata
import angulate2.router.Route

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.scalajs.js

/**
 * Import this object to get the most commonly used angulate2 annotations and operators.
 *
 * @example ```import angulate2.std._```
 */
object std extends OpsTrait {

  // duplicate definition of core.Component since `type Component = core.Component` won't compile
  // NOTE: keep in sync with core.Component()!!
  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class Component(selector: String = null,
                  inputs: js.Array[String] = null,
                  outputs: js.Array[String] = null,
                  host: js.Any = null,
                  exportAs: String = null,
                  moduleId: js.Any = null,
                  providers: js.Array[js.Any] = null,
                  viewProviders: js.Array[js.Any] = null,
                  changeDetection: js.Any = null,
                  queries: js.Any = null,
                  templateUrl: String = null,
                  template: String = null,
                  styles: js.Array[String] = null,
                  styleUrls: js.Array[String] = null,
                  animations: js.Array[AnimationEntryMetadata] = null,
                  encapsulation: js.Any = null,
                  interpolation: js.Any = null,
                  entryComponents: js.Array[js.Any] = null) extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro core.Component.Macro.impl
  }

  // duplicate definition of core.Injectable since `type = core.Injectable` won't compile
  // NOTE: keep in sync with core.Injectable()!!
  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class Injectable extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro core.Injectable.Macro.impl
  }

  // duplicate definition of core.NgModule since `type NgModule = core.NgModule` won't compile
  // NOTE: keep in sync with core.NgModule()!!
  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class NgModule(providers: js.Array[js.Any] = null,
                 declarations: js.Array[js.Any] = null,
                 imports: js.Array[js.Any] = null,
                 exports: js.Array[js.Any] = null,
                 entryComponents: js.Array[js.Any] = null,
                 bootstrap: js.Array[js.Any] = null,
                 schemas: js.Array[js.Any] = null,
                 id: String = null) extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro core.NgModule.Macro.impl
  }

  // duplicate definition of core.Directive since `type Directive = core.Directive` won't compile
  // NOTE: keep in sync with core.Directive()!!
  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class Directive(selector: String = null,
                  inputs: js.Array[String] = null,
                  outputs: js.Array[String] = null,
                  host: js.Dictionary[String] = null,
                  template: String = null,
                  providers: js.Array[js.Any] = null,
                  exportAs: String = null,
                  queries: js.Any = null) extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro core.Directive.Macro.impl
  }

  type HostListener = core.HostListener
  type HostBinding = core.HostBinding
  type Input = core.Input
  type Output = core.Output
  type ViewChild = core.ViewChild
  type OnInit = core.OnInit
  type OnDestroy = core.OnDestroy
  type OnChanges = core.OnChanges


  // duplicate definition of ext.Data since `type Data = ext.Data` won't compile
  // NOTE: keep in sync with ext.Data()!!
//  @compileTimeOnly("enable macro paradise to expand macro annotations")
//  class Data extends StaticAnnotation {
//    def macroTransform(annottees: Any*): Any = macro Data.Macro.impl
//  }

  // duplicate definition of ext.Routes since `type Routes = ext.Routes` won't compile
  // NOTE: keep in sync with ext.Routes()!!
  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class Routes(root: Boolean, routes: Route*) extends StaticAnnotation {
    def this(providers: js.Array[js.Any])(root: Boolean, routes: Route*) = this(root,routes:_*)
    def macroTransform(annottees: Any*): Any = macro ext.Routes.Macro.impl
  }

  type Route = router.Route
  lazy val Route = router.Route
//  type Router = router.Router
//  type Location = common.Location

  type debug = de.surfice.smacrotools.debug

  @inline
  final def undef[T]: js.UndefOr[T] = js.undefined.asInstanceOf[js.UndefOr[T]]

  /**
   * Takes the relative path of a HTML template to be loaded and returns the absolute path to the template.
   *
   * @param relPath relative path of the HTML template
   */
  @inline
  final def $html(relPath: String): String = ext.runtime.$html(relPath)
//  @inline
//  final def $css(relPath: String): String = ext.runtime.$css(relPath)

  /**
   * Takes the relative path of a CSS file to be loaded and returns the absolute path to the style file.
   *
   * @param relPath relative path to the CSS file
   */
  @inline
  final def $css(relPath: String): String = ext.runtime.$css(relPath)

  implicit final class ToDynamic(val o: Any) extends AnyVal {
    def dynamic: js.Dynamic = o.asInstanceOf[js.Dynamic]
    def dynamic_=(d: Any): Unit = macro OpsMacros.assignDynamic
  }

}

