package com.inner_circle.i18nSpreadsheetSync;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "google.sheets")
public class GoogleSheetsProperties {
    private String spreadsheetId;
    private String range;
    private String serviceAccountKeyPath;
}
