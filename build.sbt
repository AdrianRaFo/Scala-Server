scalacOptions += "-Ywarn-unused-import"

lazy val root = project
  .in(file("."))
  .settings(name := "Server")
  .settings(moduleName := "root")
  .settings(scalaMetaSettings: _*)
  .settings(libraryDependencies ++= Seq(
    %%("akka-actor")
  ) ++ commonDeps ++ freestyleCoreDeps())


