name := "renamer"

version := "0.1.0"

scalaVersion := "2.11.8"
scalacOptions += "-target:jvm-1.8"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies += "com.github.pathikrit" %% "better-files" % "2.16.0"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

mainClass in Compile := Some("com.aivean.renamer.Main")