val smacrotoolsVersion = "0.0.2"
val sjsxVersion = "0.3.0"
val rxjsVersion = "0.0.1"

lazy val commonSettings = Seq(
  organization := "de.surfice",
  version := "0.0.2",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  autoCompilerPlugins := true,
  //addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.sonatypeRepo("snapshots")
)


lazy val angulate2 = project.in(file(".")).
  enablePlugins(ScalaJSPlugin).
  aggregate(plugin,stubs).
  settings(commonSettings: _*).
  settings(publishingSettings: _*).
  settings( 
    name := "angulate2",
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-js"   %%% "scalajs-dom" % "0.8.0",
      "de.surfice" %%% "smacrotools-sjs" % smacrotoolsVersion,
      "de.surfice" %%% "sjsx" % sjsxVersion,
      "de.surfice" %%% "scalajs-rxjs_cjsm" % rxjsVersion
      //"be.doeraene" %%% "scalajs-jquery" % "0.8.0" % "provided",
    ),
    crossScalaVersions := Seq("2.11.8","2.12.0"),
    scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/jokade/angulate2"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      }))
  )


lazy val plugin = project.
  settings(commonSettings:_*).
  settings(publishingSettings:_*).
  settings(
    name := "sbt-angulate2",
    description := "sbt plugin for angulate2 (Angular2 bindings for Scala.js)",
    sbtPlugin := true,
    scalaVersion := "2.10.6",
    addSbtPlugin("de.surfice" % "sbt-sjsx" % sjsxVersion),
    sourceGenerators in Compile += Def.task {
      val file = (sourceManaged in Compile).value / "Version.scala"
      IO.write(file,
        s"""package de.surfice.angulate2.sbtplugin
          |object Version { val angulateVersion = "${version.value}" }
        """.stripMargin)
      Seq(file)
    }.taskValue
  )

lazy val stubs = project
  .settings(commonSettings:_*)
  .settings(publishingSettings:_*)
  .settings(
    name := "angulate2-stubs",
    crossScalaVersions := Seq("2.11.8","2.12.0")
  )

//lazy val tests = project.
//  dependsOn(angulate2).
//  enablePlugins(ScalaJSPlugin).
//  settings(commonSettings: _*).
//  settings(
//    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full),
//    publish := {},
//    scalacOptions ++= angulateDebugFlags,
//    scalaJSStage in Test := FastOptStage,
//    testFrameworks += new TestFramework("utest.runner.Framework"),
//    requiresDOM := true,
//    libraryDependencies ++= Seq(
//      "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
//      "org.querki" %%% "jquery-facade" % "0.6"
//    ),
//    jsDependencies += RuntimeDOM,
//    jsDependencies ++= Seq(
//      "org.webjars.npm" % "angular2" % "2.0.0-beta.1" / "bundles/angular2-polyfills.js",
//      "org.webjars.npm" % "angular2" % "2.0.0-beta.1" / "bundles/angular2-all.umd.js"
//    ),
//    scalacOptions ++= angulateDebugFlags
//    //scalacOptions += "-Xmacro-settings:angulate2.debug.Component"
//  )
//

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
 

