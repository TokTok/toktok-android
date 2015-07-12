// General settings
organization := "im.tox"
name := "toktok"
scalaVersion := "2.12.0-M1"

// Dependencies.
libraryDependencies ++= Seq(
  "com.android.support" % "gridlayout-v7" % "22.1.1",
  "com.android.support" % "support-v4" % "22.2.0",
  "com.android.support" % "appcompat-v7" % "22.1.1",
  "com.android.support" % "cardview-v7" % "21.0.+",
  "com.android.support" % "recyclerview-v7" % "22.2.+",
  "com.github.siyamed" % "android-shape-imageview" % "0.9.+",
  "com.timehop.stickyheadersrecyclerview" % "library" % "0.4.1",
  "com.android.support" % "design" % "22.2.0"

  //organization.value %% "tox4j" % version.value
)

proguardOptions in Android ++= Seq(
  "-keepattributes Signature",
  "-keep class com.melnykov.fab.ObservableScrollView$*",

  "-optimizationpasses 5",
  "-allowaccessmodification",

  "-dontwarn org.xmlpull.v1.**"
)