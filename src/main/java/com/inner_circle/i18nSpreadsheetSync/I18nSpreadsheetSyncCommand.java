package com.inner_circle.i18nSpreadsheetSync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("!test")
@Slf4j
public class I18nSpreadsheetSyncCommand implements CommandLineRunner {

    private final GoogleSheetsService googleSheetsService;
    private final ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        if (args.length > 0 && "syncTranslations".equals(args[0])) {
            processTranslations();
        } else {
            log.warn("No command specified");
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }

    private void processTranslations() {
        try {
            googleSheetsService.processSpreadsheetData();
            log.info("Spreadsheet data processed successfully.");
            SpringApplication.exit(applicationContext, () -> 0);
        } catch (Exception e) {
            log.error("Error processing spreadsheet data: {}", e.getMessage(), e);
            SpringApplication.exit(applicationContext, () -> 1);
        }
    }
}
