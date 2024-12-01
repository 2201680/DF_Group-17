# Anti-Forensic Tool for AOSP

This project is an anti-forensic tool developed as part of the ICT3215 (Digital Forensics) module. It implements anti-forensic features in a customized Android Open Source Project (AOSP) build, allowing the deletion of files both remotely and automatically. These features are embedded within system apps to make detection of the anti-forensic tools difficult.

## Features

1. **Automated Triggers**: Files are deleted automatically when specific conditions are met.
2. **Web Application**: A web interface that allows remote deletion of files.

## Automated Triggers

The automated triggers are designed with the assumption that any seized devices will be placed in a Faraday bag to block electromagnetic transmissions and outside signals. This prevents external actions that could modify, delete, or corrupt data on the seized device.

Our trigger mechanism works by detecting when the device is disconnected from all networks. Currently, the selected files will be deleted if the device is disconnected from both Wi-Fi and cellular networks for 100 seconds. This time limit is short for testing purposes, but it can be increased (e.g., up to 24 hours) in a production environment.

## Web Application

The web interface allows users to specify which files should be deleted when the trigger condition is met. This provides users the ability to set files for deletion when the device is seized but not yet placed in a Faraday bag.

## Implementation

This project is based on the Android Open Source Project (AOSP) and is specifically designed for Google Pixel 5A devices (device name: `barbet`). Modifications were primarily made in the `documentsui` folder.

### To Build the Project

1. Clone the AOSP repository From [Here:] ("https://android.googlesource.com/device/google/barbet")
2. Navigate to the project folder.
3. Clone The DocumentsUI From this repo. 
4. Replace the DocumentsUI Folder from Step(1) with the DocumentsUI from step(3)
5. Source the build environment:
   ```bash
   source build/envsetup.sh
   ```
   Set up the build environment for the barbet device:
    ```bash 
    lunch aosp_barbet-ap2a-userdebug
    ```
    Build the project:
    ```bash
    m
    ```

    To Flash the Code to the Device,Reboot the device into bootloader mode:
    ```bash
    adb reboot bootloader
    ```
    Flash the build to the device:
    
        fastboot flashall

Credits

This project was developed by the following students as part of their ICT3215 Digital Forensics module:

    Carmen Wong Jiawen
    Evan Chua Wee Yang
    Isaiah Loh Jun Hao
    Naushad Ali Bin Hassan Mydin
    Tan Kowit

The project is licensed under the WTFPL.


