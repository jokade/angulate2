# angulate2
[Scala.js](http://www.scala-js.org/) bindings for [Angular 2](http://www.angular.io).

**NOTE: This is work in progress (not even alpha, more like a playground)!**  
Most of the features of Angular2 are still missing and the API may change at any time without notice!

For Scala.js bindings to [Angular 1.x](https://angularjs.org) see [scalajs-angulate](https://github.com/jokade/scalajs-angulate).

There is a separate repository with [Examples](https://github.com/jokade/angulate2-examples).

Getting Started
---------------
### SBT Settings
Add the following lines to your `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

libraryDependencies += "biz.enef" %% "angulate2" % "0.1-SNAPSHOT"
```
angulate2 is built for Scala.js 0.6.2+.

### Defining a Component and Bootstrapping
```scala
import biz.enef.angulate2._
import scalajs.js.annotation.JSExport

// Components are defined by annotating a class with @Component
// NOTE: angulate2 currently has no separate @View annotation
@Component(
  selector = "my-app",
  template = "<h1>Hello, {{name}}!</h1>"
)
class AppComponent {
  // all public members are available in the template
  val name = "Alice"
}

// call Main() from index.html to start the application
@JSExport
object Main {
  
  // register the component
  // (this step is currently required by angulate2 due to some
  //  limitations of JS code that can be generated from within Scala)
  angular.register[AppComponent]
  
  // Start the app with AppComponent
  angular.bootstrapWith[AppComponent]
  
}
```


License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT).
