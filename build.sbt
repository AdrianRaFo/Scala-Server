scalacOptions += "-Ywarn-unused-import"

lazy val root = project
  .in(file("."))
  .settings(name := "Server")
  .settings(moduleName := "root")
  .settings(scalaMetaSettings: _*)
  .settings(libraryDependencies ++= Seq(
    %%("akka-actor")
  ) ++ commonDeps ++ freestyleCoreDeps())
  .aggregate(`serverJS`, `serverJVM`)

lazy val `server` = crossProject
  .in(file("server"))
  .settings(moduleName := "server")
  .jsSettings(sharedJsSettings: _*)

lazy val `serverJVM` = `server`.jvm
lazy val `serverJS`  = `server`.js
