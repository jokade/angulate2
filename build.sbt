//import SonatypeKeys._

lazy val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.6",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
  scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/jokade/angulate2"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      }))
)


lazy val angulate2 = project.in(file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(publishingSettings: _*).
  //settings(sonatypeSettings: _*).
  settings( 
    name := "angulate2",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-js"   %%% "scalajs-dom" % "0.8.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.8.0" % "provided"
    ),
    resolvers += Resolver.sonatypeRepo("releases")
  )


lazy val tests = project.
  dependsOn(angulate2).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    publish := {},
    scalacOptions ++= angulateDebugFlags,
    scalaJSStage in Test := FastOptStage,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    requiresDOM := true,
    libraryDependencies ++= Seq("com.lihaoyi" %%% "utest" % "0.3.0" % "test",
      "be.doeraene" %%% "scalajs-jquery" % "0.8.0"),
    jsDependencies += RuntimeDOM
    //jsDependencies += "org.webjars" % "angularjs" % "1.3.8" / "angular.min.js" % "test",
    //jsDependencies += ("org.webjars" % "angularjs" % "1.3.8" / "angular-mocks.js" dependsOn "angular.min.js") % "test"
  )


lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/angulate2</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/angulate2</url>
      <connection>scm:git:git@github.com:jokade/angulate2.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jokade</id>
        <name>Johannes Kastner</name>
        <email>jokade@karchedon.de</email>
      </developer>
    </developers>
  )
)
 
lazy val angulateDebugFlags = Seq(
  // include some code for runtime debugging
  //"runtimeLogging",
  //"ModuleMacros.debug",
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
  //"ComponentMacros.debug"
  //"HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angulate.$f" )

