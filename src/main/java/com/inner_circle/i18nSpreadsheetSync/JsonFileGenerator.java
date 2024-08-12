package com.inner_circle.i18nSpreadsheetSync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonFileGenerator {

    private final ObjectMapper objectMapper = new ObjectMapper().enable(
        SerializationFeature.INDENT_OUTPUT);

    public void generateJsonFiles(Map<String, Map<String, String>> translations) {
        File i18nDir = createI18nDirectory();

        if (i18nDir == null) {
            return;
        }

        translations.forEach((languageCode, languageTranslations) -> {
            File jsonFile = new File(i18nDir, languageCode + ".json");
            try {
                objectMapper.writeValue(jsonFile, languageTranslations);
                log.info("Generated {}", jsonFile.getAbsolutePath());
            } catch (IOException e) {
                log.error("Error writing JSON file for {}: {}", languageCode, e.getMessage(), e);
            }
        });
    }

    private File createI18nDirectory() {
        File i18nDir = new File("src/main/resources/i18n");
        if (!i18nDir.exists() && !i18nDir.mkdirs()) {
            log.error("Failed to create i18n directory at: {}", i18nDir.getAbsolutePath());
            return null;
        }
        return i18nDir;
    }
}
