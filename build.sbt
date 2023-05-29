ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "indexVerse",
  )

libraryDependencies +=
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"