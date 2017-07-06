scalacOptions += "-Ywarn-unused-import"

scalaVersion := "2.12.2"

lazy val root = project
  .in(file("."))
  .settings(name := "Server")
  .settings(moduleName := "root")
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.3"
  ))


