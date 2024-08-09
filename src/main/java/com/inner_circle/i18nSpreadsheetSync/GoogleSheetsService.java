package com.inner_circle.i18nSpreadsheetSync;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GoogleSheetsService {

    private final GoogleSheetsProperties properties;
    private Sheets sheetsService;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final ResourceLoader resourceLoader;
    private final JsonFileGenerator jsonFileGenerator;


    private Sheets getSheetsService() throws GeneralSecurityException, IOException {
        if (sheetsService == null) {
            GoogleCredentials credentials;
            Resource resource = resourceLoader.getResource(properties.getServiceAccountKeyPath());

            try (var serviceAccountStream = resource.getInputStream()) {
                credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
            } catch (IOException e) {
                System.err.println("Error loading service account key: " + e.getMessage());
                throw e;
            }
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, requestInitializer).build();
        }

        return sheetsService;
    }

    public void processSpreadsheetData() throws IOException, GeneralSecurityException {
        Sheets service = getSheetsService();
        String spreadsheetId = properties.getSpreadsheetId();
        String range = properties.getSheetName();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No Data");
            return;
        }

        List<Object> header = values.get(0);
        Map<String, Map<String, String>> translations = new HashMap<>();

        // B1부터 언어 코드가 들어가 있음
        for (int i = 1; i < header.size(); i++) {
            String languageCode = header.get(i).toString();
            translations.put(languageCode, new HashMap<>());
        }

        for (int i = 1; i < values.size(); i++) {
            List<Object> row = values.get(i);
            if (!row.isEmpty()) {
                String key = row.get(0).toString();
                for (int j = 1; j < row.size(); j++) {
                    String languageCode = header.get(j).toString();
                    String translation = row.get(j).toString();
                    translations.get(languageCode).put(key, translation);
                }
            }
        }

        jsonFileGenerator.generateJsonFiles(translations);
    }
}
