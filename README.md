# floripapublictransportation

Florianópolis´s Public Transportation


Android Studio 1.5.1
- Min SDK 17
Genymotion - Samsung Galaxy S4 4.4.4 (Have to install google play)
Google play services library have to be installed on Android Studio

#To generate code coverage report
gradlew.bat createDebugCoverageReport
check at \app\build\reports\coverage\debug\index.html

#If the coverage report was not generate
on Android Studio use Build > Clean Project / Build > Rebuild Project.

#To execute all the tests sucessfully an emulator must be running.

#To use google maps:
keytool -list -v -keystore C:\Users\GabrielPacheco\.android\debug.keystore
At genymotion its necessary install the google play and login.


*****************  WARNING WARNING WARNING  *****************
* The integrity of the information stored in your keystore  *
* has NOT been verified!  In order to verify its integrity, *
* you must provide your keystore password.                  *
*****************  WARNING WARNING WARNING  *****************

Keystore type: JKS
Keystore provider: SUN

Your keystore contains 1 entry

Alias name: androiddebugkey
Creation date: 21-Dec-2015
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: CN=Android Debug, O=Android, C=US
Issuer: CN=Android Debug, O=Android, C=US
Serial number: e6e7010
Valid from: Mon Dec 21 20:05:18 BRST 2015 until: Wed Dec 13 20:05:18 BRST 2045
Certificate fingerprints:
         MD5:  06:49:B0:2F:7F:32:5B:50:4E:EE:6C:88:0B:AC:A4:34
         SHA1: 40:BB:E5:7D:8D:73:E0:02:FB:7B:34:55:7F:49:FF:C2:44:FE:35:CE
         SHA256: 66:16:43:02:81:B2:F3:9B:90:16:D6:41:52:AF:BA:E6:A1:87:02:C6:74:3A:B7:DB:B9:3E:5F:8A:33:4C:2C:79
         Signature algorithm name: SHA256withRSA
         Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: F0 1D 3F 35 B7 9D 78 B2   21 B8 5B 32 1E 26 56 EE  ..?5..x.!.[2.&V.
0010: 46 A3 C2 5A                                        F..Z
]
]



*******************************************
*******************************************