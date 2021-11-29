# StreamDex: A Unified Stream Tracker

## Getting started

StreamDex uses the standard suite of tools provided by Android studio for building and running the app. The minimum SDK level is 24, so ensure you are running the app on a device that at least meets SDK level 24 requirements.

In order to set up your local repository:
- First ensure you have the latest version of Android Studio
- Clone the repo
- Run Gradle sync to load dependencies
- Before building, you must copy the `twitch_auth.properties.example` file in the root directory to `twitch_auth.properties` so Gradle can load Twitch API authentication info for use in the application. You will not need valid auth info to build the app, but you will need it to run the app. See the [Twitch API documentation](https://dev.twitch.tv/docs/api/) for information on obtaining a valid auth token and client ID. **Do not check this file into git.**

That's it! Once you've put the valid auth info into the properties file, the app is ready to run!
