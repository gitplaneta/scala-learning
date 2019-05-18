name := "scala-learning-sbt"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq("com.typesafe.play" %% "play-json" % "2.7.3"
  , "org.typelevel" %% "cats-core" % "1.6.0"
  , "org.scalaz" %% "scalaz-core" % "7.3.0-M29"
  , "com.typesafe.akka" %% "akka-actor" % "2.5.22"
  , "com.typesafe.akka" %% "akka-persistence" % "2.5.22"
  , "org.iq80.leveldb" % "leveldb" % "0.11"
  , "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

  , "org.mockito" % "mockito-all" % "1.10.19" % Test
  , "org.scalatest" %% "scalatest" % "3.0.7" % Test
  , "junit" % "junit" % "4.12" % Test)


libraryDependencies ++= Seq(
  "com.github.mpilquist" %% "simulacrum"      % "0.13.0",
  "com.chuusai"          %% "shapeless"       % "2.3.3",
  "org.scalaz"           %% "scalaz-effect"   % "7.2.25",
  "org.scalaz"           %% "scalaz-ioeffect" % "2.10.1",
  "eu.timepit"           %% "refined-scalaz"  % "0.9.2",
  "com.lihaoyi"          %% "sourcecode"      % "0.1.4",
  "io.estatico"          %% "newtype"         % "0.4.2"
)

val derivingVersion = "1.0.0"
libraryDependencies ++= Seq(
  "org.scalaz" %% "deriving-macro" % derivingVersion,
  compilerPlugin("org.scalaz" %% "deriving-plugin" % derivingVersion),
  "org.scalaz" %% "scalaz-deriving"            % derivingVersion,
  "org.scalaz" %% "scalaz-deriving-magnolia"   % derivingVersion,
  "org.scalaz" %% "scalaz-deriving-scalacheck" % derivingVersion
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")
addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
)

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")

scalacOptions in (Compile, console) -= "-Xfatal-warnings"
initialCommands in (Compile, console) := Seq(
  "scalaz._, Scalaz._"
).mkString("import ", ",", "")