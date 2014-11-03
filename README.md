sweng-team-andro-foot
=====================

###Warning
If you just pulled from master and it broke your build it is because we stopped tracking the .project and .classpath files that are specific to eclipse. To repair your eclipse project just import it again as a gradle build.

###Compiling from the command line
To compile the project from the command line just navigate to the root of the project and execute (in bash shells) :

- `./gradlew desktop:run` to run the desktop version of the project
- `./gradlew android:installDebug android:run` to install the project on a connected android device or emulator
- `./gradlew tasks` to see all the tasks that gradle can execute.

###Prototype
An apk of the prototype is in the prototypeBuilds folder. You can install it through adb by typing `adb install -r prototype.apk`.
