# Installation

## Backend set-up

Follow [README](https://github.com/mathieu-tulpinck/ehb-ss) to set up backend project.

## FrontEnd

Clone repo: `git clone <url> FrontEnd`.

The app sends requests to a backend server. In a local set-up, the `hosts` file of the emulator should be modified to map the ip address of the docker host running the backend project to the correct domains. This seems to only be possible with generic images that do not contain the Google apis.

Create an avd based on a generic image with api level 16 or higher.

Make the image writable after a remount: `emulator -avd <name> -writable-system`.

In a separate terminal:
```bash
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
On subsequent boots of the emulator, run `emulator -avd <name> -writable-system` in a terminal to load the correct image. In other words, do not launch the emulator from Android Studio. Afterwards, run the application in Android Studio and the compiled APK will be sent to the running emulator.

# Use

Only users whose username and role are known to the backend database may register. For demonstration purposes, the username of the tenant admin is `admin@demo.backend.test`.

Tapping the FAB on the Transaction tab saves the current transaction to the local database. An overview of past transactions is displayed under the Overview tab.

