import sbt._
import sbt.Classpaths.publishTask
import Keys._

import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

import sbtassembly.AssemblyPlugin.autoImport._

import com.typesafe.sbt.SbtNativePackager._, autoImport._
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd, CmdLike}

import com.aianonymous.sbt.standard.libraries.StandardLibraries


object AianashBuild extends Build with StandardLibraries {

  lazy val makeScript = TaskKey[Unit]("make-script", "make bash script in local directory to run main classes")

  def sharedSettings = Seq(
    organization := "com.aianonymous",
    version := "0.1.0",
    scalaVersion := Version.scala,
    crossScalaVersions := Seq(Version.scala, "2.10.4"),
    scalacOptions := Seq("-unchecked", "-optimize", "-deprecation", "-feature", "-language:higherKinds", "-language:implicitConversions", "-language:postfixOps", "-language:reflectiveCalls", "-Yinline-warnings", "-encoding", "utf8"),
    retrieveManaged := true,

    fork := true,
    javaOptions += "-Xmx2500M",

    resolvers ++= StandardResolvers,

    publishMavenStyle := true
  ) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings


  lazy val aianash = Project(
    id = "aianash",
    base = file("."),
    settings = Project.defaultSettings ++
      sharedSettings
  ).aggregate(behaviour)


  lazy val behaviour = Project(
    id = "aianash-behaviour",
    base = file("behaviour"),
    settings = Project.defaultSettings ++
      sharedSettings
  )
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "aianash-behaviour",
    libraryDependencies ++= Seq(
    ) ++ Libs.jna,
  makeScript <<= (stage in Universal, stagingDirectory in Universal, baseDirectory in ThisBuild, streams) map { (_, dir, cwd, streams) =>
      var path = dir / "bin" / "aianash-behaviour"
      sbt.Process(Seq("ln", "-sf", path.toString, "aianash-behaviour"), cwd) ! streams.log
    }
  )

}