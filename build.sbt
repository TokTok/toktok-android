// General settings.
organization  := "org.toktok"
name          := "toktok"
version       := "0.1.0"
scalaVersion  := "2.11.7"

enablePlugins(AndroidApp)

wartremoverErrors in (Compile, Keys.compile) := Nil
wartremoverErrors in (Test, Keys.compile) := Nil

// Tox4j library.
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += Resolver.bintrayRepo("toktok", "maven")

// Dependencies.
libraryDependencies ++= Seq(
  "com.android.support" % "appcompat-v7" % "25.0.0",
  "com.android.support" % "recyclerview-v7" % "25.0.0",
  "com.android.support" % "cardview-v7" % "25.0.0",
  "com.android.support" % "palette-v7" % "25.0.0",
  "com.android.support" % "design" % "25.0.0",

  "com.sothree.slidinguppanel" % "library" % "3.3.1",
  "com.timehop.stickyheadersrecyclerview" % "library" % "0.4.3",
  "com.tonicartos" % "superslim" % "0.4.13",
  "de.hdodenhof" % "circleimageview" % "2.1.0",

  "org.scaloid" %% "scaloid" % "4.2",

  "com.jayway.android.robotium" % "robotium-solo" % "5.5.3",

  "org.slf4j" % "slf4j-android" % "1.7.13",

  organization.value %% "tox4j-api" % "0.1.0"
)

proguardOptions in Android += "@proguard-rules.pro"

proguardCache ++= Seq(
  "com.google.common.collect",
  "com.google.protobuf",
  "scalaz",
  "scodec.bits",
  "scodec.codecs",
  "shapeless"
)

packagingOptions in Android := PackagingOptions(
  List(
    "META-INF/INDEX.LIST",
    "META-INF/LICENSE",
    "META-INF/LICENSE.txt",
    "META-INF/NOTICE",
    "META-INF/NOTICE.txt",
    "META-INF/io.netty.versions.properties",
    "META-INF/services/io.grpc.ManagedChannelProvider",
    "META-INF/services/io.grpc.ServerProvider"
  ),
  Nil,
  Nil
)

if (sys.env.contains("PROTIFY")) {
  new Def.SettingList(protifySettings)
} else {
  sys.props("maximum.inlined.code.length") = "8"

  // Enable optimisation by removing default proguard options that were added
  // by android-sdk-plugin.
  proguardConfig ~= (_.filterNot(Seq(
    "-dontoptimize",
    "-verbose"
  ).contains))
}

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
