# TokTok - Toxing on Android

GSoC Project - New Tox Android Client

Up to 2016/11/10: This is a demo application showing new modern design and all the basic UI interactions.
No connection, messages, audio/video calls are presented here yet.

## Installation

### 1. Install the latest gradle-android-scala-plugin from git:

#### Unix:
```
$ git clone --depth=1 https://github.com/saturday06/gradle-android-scala-plugin
$ cd gradle-android-scala-plugin
$ ./gradlew install
```

#### Windows:
1) Use TortoiseGIT or any other git client to clone https://github.com/saturday06/gradle-android-scala-plugin with specifying depth = 1.

2) Use commandline to install the plugin:
```
cd /path-to-gradle-android-scala-plugin
gradlew.bat install
```

### 2. Pull the toktok project from git:

#### Unix:
```
$ git clone https://github.com/TokTok/toktok.git
```

To build .apk from commandline (files may appear in /toktok/build/outputs/apk):
```
$ cd toktok
$ ./gradlew build
```

### Windows:
Use TortoiseGIT or any other git client to clone https://github.com/TokTok/toktok.git.

To build .apk from commandline (files may appear in /toktok/build/outputs/apk):
```
cd /path-to-toktok
gradlew.bat build
```

### To run from Android Studio consider some requirements:
- Android studio 2.1.2 - 2.2.2
- Scala, SBT and Android Scala plugins installed
- Android device with 23 API and higher (Android 6)

