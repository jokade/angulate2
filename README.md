# angulate2
[Scala.js](http://www.scala-js.org/) bindings for [Angular 2](http://www.angular.io).

**NOTE: This is work in progress (not even alpha, more like a playground)!**  
Most of the features of Angular2 are still missing and the API may change at any time without notice!

Getting Started
---------------
### SBT Settings
Add the following lines to your `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

libraryDependencies += "biz.enef" %% "angulate2" % "0.1-SNAPSHOT"
```
angulate2 is build for Scala.js 0.6.2+.

### First Example
Here is the [startup example from the Angular 2 guide](https://angular.io/docs/js/latest/guide/setup.html) using angulate2:
