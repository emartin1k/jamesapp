# JamesApp
James App Code Challenge

This document will cover the steps to run the generated tests and export the results to a Report which can be opened on the browser.

### Pre-requisites
- [Java SDK](https://www.java.com/es/download/help/java_mac.html)
- [Android Studio](https://developer.android.com/studio?hl=es-419)
- Android Emulator (this version was tested with Pixel 7 TiramisuPrivacySandbox - V14.0)
- [Homebrew](https://brew.sh/)
- [npm](https://formulae.brew.sh/formula/node)

### [Allure Reporting](https://allurereport.org/docs/gettingstarted-installation/)
Run the following command to install Allure:
```
    brew install allure
```

### [Maven](https://mvnrepository.com/)
Run the following command to install mvn agent
```
    brew install maven
```

### [Appium](https://appium.io/docs/en/2.0/quickstart/)

#### Installation
Run the following commands:
```
    npm i -g appium
    appium driver install uiautomator2
    npm install -g @appium/doctor
```

#### Validation
Run the following commands:
```
    appium-doctor
```
**Note:** Make sure the following message is displayed:

```
    info AppiumDoctor ### Diagnostic for necessary dependencies completed, no fix needed. ###
```

#### Run Appium Server
```
    appium
```

### Run Tests
- Open the Android Emulator and start a virtual device
- Make sure the Appium Server is running
- Run the following command to identify the emulator UUID
    ```
        adb devices
    ```
    Once the uuid is located (for example "emulator-5554") use that value to replace the constant "EMULATOR_UUID" located in: **jamesapp/src/test/java/TestsAll/AbstractTest,java**
- Open the app and login using a valid phone number and the corresponding OTP (This is due to the pending feature to get the necessary OTP received by SMS)
- Run the following commands at the root jamesapp folder:
    ```
        mvn test 
        allure generate --clean
    ```
    **Note:** This is expected to fail on the last test.
- Once the last command is executed an execution report will be generated with the results, to open the report navigate to **jamesapp/allure-report** and open the **index.html** file.
