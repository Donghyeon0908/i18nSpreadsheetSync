package com.inner_circle.i18nSpreadsheetSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class I18nSpreadsheetSyncCommand implements CommandLineRunner {
    private final GoogleSheetsService googleSheetsService;
    private final JsonFileGenerator jsonFileGenerator;

    @Autowired
    public I18nSpreadsheetSyncCommand(GoogleSheetsService googleSheetsService, JsonFileGenerator jsonFileGenerator) {
        this.googleSheetsService = googleSheetsService;
        this.jsonFileGenerator = jsonFileGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello, World!");
    }
}
