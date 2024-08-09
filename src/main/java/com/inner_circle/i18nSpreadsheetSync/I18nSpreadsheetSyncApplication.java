package com.inner_circle.i18nSpreadsheetSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.GeneralSecurityException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class I18nSpreadsheetSyncApplication {


    public static void main(String[] args) {
        SpringApplication.run(I18nSpreadsheetSyncApplication.class, args);
    }

}
