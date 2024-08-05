package com.inner_circle.i18nSpreadsheetSync;

import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleSheetsService {
    private final GoogleSheetsProperties googleSheetsProperties;
    private Sheets sheetsService;

    @Autowired
    public GoogleSheetsService(GoogleSheetsProperties googleSheetsProperties) {
        this.googleSheetsProperties = googleSheetsProperties;
    }
}
