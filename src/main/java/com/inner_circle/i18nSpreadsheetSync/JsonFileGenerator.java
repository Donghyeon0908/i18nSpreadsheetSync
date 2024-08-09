package com.inner_circle.i18nSpreadsheetSync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JsonFileGenerator {

    private final ObjectMapper objectMapper;

    public JsonFileGenerator() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // JSON 파일을 읽기 쉽게 포맷팅
    }

    public void generateJsonFiles(Map<String, Map<String, String>> translations)
        throws IOException {
        File i18nDir = new File("src/main/resources/i18n");
        if (!i18nDir.exists()) {
            if (i18nDir.mkdirs()) {
                System.out.println("Created i18n directory at: " + i18nDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create i18n directory");
                return;
            }
        }

        for (Map.Entry<String, Map<String, String>> entry : translations.entrySet()) {
            String languageCode = entry.getKey();
            Map<String, String> languageTranslations = entry.getValue();
            File jsonFile = new File(i18nDir, languageCode + ".json");
            objectMapper.writeValue(jsonFile, languageTranslations);
            System.out.println("Generated " + jsonFile.getAbsolutePath());
        }
    }
}
