name := "atomtestmother"

version := "0.1.0"

organization := "org.smop.atom"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.5",
  "org.specs2" %% "specs2-scalaz-core" % "6.0.RC2" % "test",
  "org.apache.abdera" % "abdera-client" % "1.1.2"
)

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
                   "releases" at "http://scala-tools.org/repo-releases")
