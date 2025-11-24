# Convention Plugins

This `build-logic` folder contains convention plugins for the configuration of all modules in this
project, to avoid code duplication.

The code contained in `build-config` is inspired from the following sources:
- [Now in Android](https://github.com/android/nowinandroid/tree/main/build-logic)
- [Stop Copy-Pasting Build Logic: Use Gradle Convention Plugins Instead](https://proandroiddev.com/stop-copy-pasting-build-logic-use-gradle-convention-plugins-instead-i-ce0eed95138d)


`build-logic` is and included build in  [`settings.gradle.kts`](../settings.gradle.kts) in the root
project. `build-logic` contains a series of convention plugins that can be included in modules of
the root project. The convention plugins take a base configuration from functions contained in
Kotlin files. These files define a configuration that is shared between application and library
configurations.