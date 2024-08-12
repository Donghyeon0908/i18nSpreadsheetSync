# i18nSpreadsheetSync
`i18nSpreadsheetSync` is a Spring Boot library that generates internationalization (i18n) JSON files based on data stored in a Google Spreadsheet. This library allows developers to manage translation tasks efficiently by directly fetching data from a spreadsheet and generating up-to-date translation files.

### Installation
You can add this library as a dependency in your Gradle project. Include the following in your build.gradle file:
```
dependencies {
implementation 'com.github.Donghyeon0908:i18nSpreadsheetSync:1.0.3'(change the version number to the latest version)
}
```

### Usage
Google API Setup:
Create a service account in Google Cloud Platform and download the service account key file (.json).

Configure Your Application:
Set up the Google Sheets API key and spreadsheet ID in your application.yml or application.properties file.
```
google:
  sheets:
    service-account-key-path: classpath:service-account-key.json
    spreadsheet-id: YOUR_SPREADSHEET_ID
    sheet-name: SHEET_NAME
```
Run the Command:
Execute the syncTranslations command from the terminal to generate translation files based on the spreadsheet data.
```
java -jar build/libs/i18nSpreadsheetSync-1.0.3.jar syncTranslations
```

### Example Spreadsheet Structure
| key | en | fr | es |
| --- | -- | -- | -- |
| hello | Hello | Bonjour | Hola |
| welcome | Welcome | Bienvenue | Bienvenido |
- The Key column contains the translation keys.
- The en, fr, es columns contain translations for each language.
- This spreadsheet will generate en.json, fr.json, es.json files.

### License
This project is licensed under the MIT License. See the LICENSE file for details.

