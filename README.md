# APP Floripa Public Transportation
Florianópolis´s Public Transportation



#Requirements
Android Studio 1.5.1

Min SDK 17
Google play services library have to be installed on Android Studio

#Devices used
Smartphone - LG Nexus 4 Adroid version 5.1.1

Genymotion virtual device - Samsung Galaxy S4 4.4.4 - 
At genymotion its necessary install the google play and login on google.

# Code coverage
To generate code coverage report (To execute all tests sucessfully an emulator must be running.)
gradlew.bat createDebugCoverageReport
check code coverage at \app\build\reports\coverage\debug\index.html

If the coverage report was not generate
on Android Studio use Build > Clean Project / Build > Rebuild Project.

#APK 
file is located on \app\build\outputs\apk\app-debug.apk
