// Common tox4j build rules.
resolvers += Resolver.bintrayIvyRepo("toktok", "sbt-plugins")
addSbtPlugin("org.toktok" % "sbt-plugins" % "0.1.2")

// Android plugins.
addSbtPlugin("org.scala-android" % "sbt-android" % "1.7.1")
addSbtPlugin("org.scala-android" % "sbt-android-protify" % "1.4.0")
