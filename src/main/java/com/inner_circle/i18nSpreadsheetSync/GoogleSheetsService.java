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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class GoogleSheetsService {

    private final GoogleSheetsProperties properties;
    private Sheets sheetsService;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final ResourceLoader resourceLoader;


    @Autowired
    public GoogleSheetsService(GoogleSheetsProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;

    }

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

    public List<List<Object>> getSpreadsheetData() throws IOException, GeneralSecurityException {
        Sheets service = getSheetsService();
        String spreadsheetId = properties.getSpreadsheetId();
        String range = "Sheet1";

        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();

        return response.getValues();
    }

}
