name := "solr-plugin-admin-handlers"

description := "Solr plugin to enable multiple admin handlers"

organization := "com.netaporter"

version := "0.1"

scalaVersion := "2.11.4"

crossPaths := false

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

libraryDependencies ++=
  "org.apache.solr" % "solr-core" % "4.10.2" % "provided" ::
  Nil

// Generate junit xml reports
testOptions <+= (target in Test) map { t =>
  Tests.Argument("-oD", "-u", t + "/test-reports")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/net-a-porter/scala-uri</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:net-a-porter/solr-plugin-cloudwatch.git</url>
    <connection>scm:git@github.com:net-a-porter/solr-plugin-cloudwatch.git</connection>
  </scm>
  <developers>
    <developer>
      <id>if</id>
      <name>Ian Forsey</name>
    </developer>
  </developers>)
