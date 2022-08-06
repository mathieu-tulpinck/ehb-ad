# Installation

## Backend set-up

Follow [README](https://github.com/mathieu-tulpinck/ehb-ss) of backend repo.

# FrontEnd

Clone repo: `git clone <url> FrontEnd`

The app sends requests to a backend server. In local set-up, the `hosts` file of the emulator should be modified to map the ip address of the docker host to the correct domains.

```bash
emulator -avd <name> -writable-system
// In separate terminal:
adb root
adb shell avbctl disable-verification
adb reboot
adb root
adb remount
adb shell
$ echo '<docker host ip> backend.test' >> /system/etc/hosts
$ echo '<docker host ip> demo.backend.test' >> /system/etc/hosts
adb reboot
// Verify if changes were written to file:
adb shell cat /system/etc/hosts
```
On subsequent boots of the emulator, run `emulator -avd <name> -writable-system` in a terminal to load the correct image. When you run the application in Android Studio, the APK will be sent to that emulator.

# Use

Only users whose username and role have been inserted in the database may register. For demonstration purposes, the username of the tenant admin is `admin@demo.backend.test`.

Tapping the FAB on the Transaction tab saves the current transaction to the local database. An overview of past transactions is displayed under the Overview tab.

