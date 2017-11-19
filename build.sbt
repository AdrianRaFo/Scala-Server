val commonDeps: Seq[Def.Setting[_]] = Seq(
  scalaVersion := "2.12.2",
  scalacOptions += "-Ywarn-unused-import",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor"     % "2.5.3",
    "com.typesafe.akka" % "akka-slf4j_2.12" % "2.5.3",
    "ch.qos.logback"    % "logback-classic" % "1.2.3"
  )
)

lazy val root = project in file(".") settings (name := "Akka") aggregate (client, server)

lazy val client = project in file("client") settings commonDeps

lazy val server = project in file("server") settings commonDeps
