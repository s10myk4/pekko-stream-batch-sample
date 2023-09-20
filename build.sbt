scalaVersion := "2.13.12"

name := "pekko-stream-batch-sample"
version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-stream" % "1.0.1",
  "org.typelevel" %% "cats-core" % "2.10.0",
)
