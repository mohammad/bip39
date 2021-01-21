lazy val root = (project in file("."))
  .settings(
    name := "bip39",
    scalaVersion := "2.12.7"
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "org.scodec" %% "scodec-bits" % "1.1.5"
libraryDependencies += "org.consensusresearch" %% "scrypto" % "1.2.0-RC3"
libraryDependencies += "io.github.nremond" %% "pbkdf2-scala" % "0.6.5"


