ThisBuild / version := "0.1." + sys.env.getOrElse("BUILD_NUMBER", "0")
ThisBuild / scalaVersion := "3.5.2"

lazy val root = (project in file("."))
  .enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)
  .settings(
    name := "BookCatZioServer",
    maintainer := "misha@zgharbi.ge",
    idePackagePrefix := Some("ge.zgharbi.books"),
    semanticdbEnabled := true,
    scalacOptions ++= Seq(
      "-Wunused:all",
      "-Xmax-inlines:64",
      "-deprecation",
      "-explain-cyclic",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-rewrite",
      "-source",
      "3.4-migration",
    ),
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.5.12",
      "com.github.jwt-scala" %% "jwt-zio-json" % "10.0.1",
      "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "2.31.1",
      "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.31.1",
      "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.11.7",
      "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % "1.11.7",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % "1.11.7",
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.11.7",
      "com.softwaremill.sttp.tapir" %% "tapir-testing" % "1.11.7" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % "1.11.7",
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % "1.11.7",
      "dev.zio" %% "zio" % "2.1.11",
      "dev.zio" %% "zio-config-typesafe" % "4.0.2",
      "dev.zio" %% "zio-http" % "3.0.1",
      "dev.zio" %% "zio-logging" % "2.3.2",
      "dev.zio" %% "zio-logging-slf4j" % "2.3.2",
      "dev.zio" %% "zio-test" % "2.1.11" % Test,
      "dev.zio" %% "zio-test-junit" % "2.1.11" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.1.11" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.11" % Test,
      "org.junit.jupiter" % "junit-jupiter-api" % "5.11.3" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.scalatestplus" %% "scalacheck-1-18" % "3.2.19.0" % Test,
    ),
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / excludeLintKeys += idePackagePrefix
Compile / run / fork := true

docker / imageNames := Seq(
  ImageName(
    namespace = Some(organization.value),
    repository = name.value.toLowerCase,
    tag = Some(version.value),
  ),
  ImageName(
    namespace = Some(organization.value),
    repository = name.value.toLowerCase,
    tag = Some("latest"),
  ),
)
docker / dockerfile := {
  val log = streams.value.log
  val appDir: File = stage.value
  val targetDir = "/app"
  log.info(s"Building image ${name.value} from dir $appDir")
  log.info(s"Executable script: $targetDir/bin/${executableScriptName.value}")
  new Dockerfile {
    from("openjdk:8-jre")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir, chown = "daemon:daemon")
  }
}

// @note: when changing something in Jsoniter
// macros, incremental compilation just won't do.
addCommandAlias("cleanCompile", "clean; compile")
addCommandAlias("cleanReStart", "clean; reStart")
addCommandAlias("cleanTestQuick", "clean; testQuick")
