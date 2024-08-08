package com.inner_circle.i18nSpreadsheetSync;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class GoogleSheetsService {

    private final GoogleSheetsProperties properties;
    private Sheets sheetsService;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    @Autowired
    public GoogleSheetsService(GoogleSheetsProperties properties) {
        this.properties = properties;
    }

    private Sheets getSheetsService() throws GeneralSecurityException, IOException {
        if (sheetsService == null) {
            GoogleCredentials credentials;
            try (var serviceAccountStream = new ClassPathResource(
                properties.getServiceAccountKeyPath()).getInputStream()) {
                credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
            }

            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, requestInitializer).build();
        }
        return sheetsService;
    }

}
