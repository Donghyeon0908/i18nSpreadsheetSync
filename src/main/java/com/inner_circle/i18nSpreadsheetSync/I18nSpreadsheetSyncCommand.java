package com.inner_circle.i18nSpreadsheetSync;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("!test")
public class I18nSpreadsheetSyncCommand implements CommandLineRunner {

    private final GoogleSheetsService googleSheetsService;
    private final ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        if (args.length > 0 && "syncTranslations".equals(args[0])) {
            try {
                googleSheetsService.processSpreadsheetData();
                System.out.println("Spreadsheet data processed successfully.");
                SpringApplication.exit(applicationContext, () -> 0);
            } catch (Exception e) {
                System.err.println("Error processing spreadsheet data: " + e.getMessage());
                SpringApplication.exit(applicationContext, () -> 1);
            }
        } else {
            System.out.println("no command specified");
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }
}
