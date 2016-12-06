name := "tapl"

organization := "tapl"

version := "0.1.0"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.0")

resolvers ++= Seq(
  "local Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(

  //"com.chuusai" %% "shapeless" % "2.3.2",
  //"org.typelevel" %% "cats" % "0.8.0",

  "com.github.pathikrit" %% "better-files" % "2.16.0",
  "com.typesafe" % "config" % "1.3.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.lihaoyi" %% "fastparse" % "0.4.1",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros",
  "-unchecked",
  //"-Ywarn-unused-import",
  "-Ywarn-nullary-unit",
  "-Xfatal-warnings",
  "-Xlint",
  //"-Yinline-warnings",
  "-Ywarn-dead-code",
  "-Xfuture")

lazy val srcGenDir = (sourceDirectory in Compile) (_ / "src_managed")

antlr4Settings
javaSource in Antlr4 := srcGenDir.value
antlr4PackageName in Antlr4 := Some("tapl.parsers")
antlr4GenListener in Antlr4 := true
antlr4GenVisitor in Antlr4 := false

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

import scalariform.formatter.preferences._
//
//SbtScalariform.scalariformSettings
SbtScalariform.defaultScalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, false)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(RewriteArrowSymbols, true)