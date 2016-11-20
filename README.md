angulate2
===========
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.13.svg)](https://www.scala-js.org)
<!--[![Build Status](https://travis-ci.org/jokade/angulate2.svg?branch=master)](https://travis-ci.org/jokade/angulate2)-->

[Scala.js](http://www.scala-js.org/) bindings for [Angular 2](http://www.angular.io). The goal is to provide an API/ experience very similar to the [TypeScript API](https://angular.io/docs/ts/latest/guide/cheatsheet.html) of Angular 2.

**WARNING: This is work in progress (alpha status)!**
Most of the features of Angular2 are still missing and the API may change at any time without notice!

**IMPORTANT: angulate2 now uses the CommonJS module format introduced with Scala.js 0.6.13 instead of global scope.
  The entire code basis is currently being refactored to facilitate this.**

The old version with global scope is available [here](https://github.com/jokade/angulate2/tree/v0.1-global).

For Scala.js bindings to [Angular 1.x](https://angularjs.org) see [scalajs-angulate](https://github.com/jokade/scalajs-angulate).

A basic [Quickstart Example](https://github.com/jokade/angulate2-quickstart) is available.


Getting Started
---------------
### SBT Settings
Add the following lines to your `project/plugins.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.13")

addSbtPlugin("de.surfice" % "sbt-angulate2" % "0.1-SNAPSHOT")
```
and this to your `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

enablePlugins(Angulate2Plugin)

ngBootstrap := Some("NAME_OF_THE_MODULE_TO_BOOTSTRAP")
```
The current version of angulate2 is built for Angular-2.2.0 and Scala.js 0.6.13+.


License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT).
