// Common tox4j build rules.
resolvers += Resolver.bintrayIvyRepo("toktok", "sbt-plugins")
addSbtPlugin("org.toktok" % "sbt-plugins" % "0.1.0")

// Android plugins.
addSbtPlugin("org.scala-android" % "sbt-android" % "1.6.18")
addSbtPlugin("org.scala-android" % "sbt-android-protify" % "1.3.7")
