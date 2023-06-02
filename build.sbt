ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "indexVerse",
  )

libraryDependencies +=
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

// program arguments parsing
libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"

// serialization / deserialization
libraryDependencies += "io.github.vigoo" %% "desert-core" % "0.3.1"
libraryDependencies += "io.github.vigoo" %% "desert-zio-schema" % "0.3.1"

// logging
libraryDependencies ++= Seq(
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.20.0" % Runtime
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"