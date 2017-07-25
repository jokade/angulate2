angulate2
===========
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/angulate2/Lobby)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg)](https://www.scala-js.org)
<!--[![Build Status](https://travis-ci.org/jokade/angulate2.svg?branch=master)](https://travis-ci.org/jokade/angulate2)-->

[Scala.js](http://www.scala-js.org/) bindings for [Angular](http://www.angular.io). The goal is to provide an API/ experience very similar to the [TypeScript API](https://angular.io/docs/ts/latest/guide/cheatsheet.html) of Angular.

**IMPORTANT: angulate2 currently only supports Scala 2.11.x**

A basic [Quickstart Example](https://github.com/jokade/angulate2-quickstart) that may serve as template is available, as well as set of **[extended examples](https://github.com/jokade/angulate2-examples)**.

**[Release Notes](https://github.com/jokade/angulate2/wiki/Release-Notes)**

Getting Started
---------------
### SBT settings
Add the following lines to your `project/plugins.sbt`:
```scala
addSbtPlugin("de.surfice" % "sbt-angulate2" % "0.1.0-RC1")
```
and this to your `build.sbt`:
```scala
enablePlugins(Angulate2Plugin)

ngBootstrap := Some("AppModule") //qualified name (including packages) of Scala class to bootstrap
```
The current version of angulate2 is built for Angular 4 and Scala.js 0.6.18.

### Create application module and component
```scala
import angulate2.std._
import angulate2.platformBrowser.BrowserModule

@NgModule(
  imports = @@[BrowserModule],
  declarations = @@[AppComponent],
  bootstrap = @@[AppComponent]
)
class AppModule {}

@Component(
  selector = "my-app",
  template = "<h1>{{greeting}}</h1>
}
class AppComponent {
  val greeting = "Hello Angular!"
}
```

### Build and run with System.js
With the above configuration, a separate JS file `PROJECT-sjsx.js` is written to `target/scala-2.11/` every time you run `fastOptJS` or `fullOptJS`. This file contains the class decorators generated from Angular2 annotations (@Component, ...) and represents the entry module of your Angular application. The annotations file loads the Scala.js package and all Angular libraries via `require`, so you need to load the annotations file with a module loader. 

One way to achieve this is to use (System.js)[https://github.com/systemjs/systemjs]. The angulate2 sbt plugin provides some tasks to simplify the creation of the System.js configuration, and for running (lite-server)[https://github.com/johnpapa/lite-server]. Although you can build your angulate2 project without Node.js, some of these tasks require a Node.js installation, so make sure `npm` and `node` are in your `PATH` before you proceed.

#### Add the HTML index
Create `src/main/resources/index-fastopt.html` with the following content:
```html
<html>
  <head>
    <title>Angulate2 QuickStart</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- <link rel="stylesheet" href="styles.css"> -->
    <!-- 1. Load libraries -->
    <!-- Polyfill for older browsers -->
    <script src="node_modules/core-js/client/shim.min.js"></script>
    <script src="node_modules/zone.js/dist/zone.js"></script>
    <script src="node_modules/reflect-metadata/Reflect.js"></script>
    <script src="node_modules/systemjs/dist/system.src.js"></script>
    <!-- 2. Configure SystemJS -->
    <script src="systemjs-fastopt.config.js"></script>
    <script>
      System.import('app').catch(function(err){ console.error(err); });
    </script>
  </head>
  <!-- 3. Display the application -->
  <body>
    <my-app>Loading...</my-app>
  </body>
</html>
```

#### Create the System.js configuration 
Most of the settings required for System.js config file are pre-configured by the angulate2 sbt plugin; however, you need to add the following System.js module mappings to your `build.sbt`:
```scala
    systemJSMappings in fastOptJS ++= Seq(
      "@angular/core"                     -> "npm:@angular/core/bundles/core.umd.js",
      "@angular/common"                   -> "npm:@angular/common/bundles/common.umd.js",
      "@angular/compiler"                 -> "npm:@angular/compiler/bundles/compiler.umd.js",
      "@angular/platform-browser"         -> "npm:@angular/platform-browser/bundles/platform-browser.umd.js",
      "@angular/platform-browser-dynamic" -> "npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js",
      "@angular/http"                     -> "npm:@angular/http/bundles/http.umd.js",
      "@angular/router"                   -> "npm:@angular/router/bundles/router.umd.js",
      "@angular/forms"                    -> "npm:@angular/forms/bundles/forms.umd.js",
      "@angular/upgrade"                  -> "npm:@angular/upgrade/bundles/upgrade.umd.js",
      "rxjs"                              -> "npm:rxjs"
    )
```

then run `sbt fastOptJS::systemJS`. This creates the System.js configuration at `target/scala-2.11/systemjs-fastopt.config.js`.

#### Create lite-server configuration
Next run `sbt fastOptJS::liteServerWriteConfigFile`. This will install all required npm dependencies (which may take some time), copy your `index-fastopt.html` to `target/scala-2.11/`, and create `bs-config-fastopt.json` (the lite-server configuration).

#### Compile and run project
Run `sbt fastOptJS`; this will build the Scala.js application file (`angulate2-quickstart-fastopt.js`) and the corresponding decorator file (`angulate2-quickstart-sjsx.js`). To run you project, start
```bash
$ node_modules/lite-server/bin/lite-server --config=target/scala-2.11/bs-config-fastopt.json
```
from your shell, and point your browser to `http://localhost:3000/index-fastopt.html`.

### Bundle the application with webpack
TBD

License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT).
