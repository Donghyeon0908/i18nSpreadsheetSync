package com.inner_circle.i18nSpreadsheetSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.GeneralSecurityException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class I18nSpreadsheetSyncApplication implements CommandLineRunner {

	@Autowired
	private GoogleSheetsService googleSheetsService;

	public static void main(String[] args) {
		SpringApplication.run(I18nSpreadsheetSyncApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			List<List<Object>> data = googleSheetsService.getSpreadsheetData();
			System.out.println("FFFFF");

			data.forEach(row -> {
				row.forEach(cell -> System.out.print(cell + "\t"));
				System.out.println("FFFFF");
			});
		} catch (GeneralSecurityException | IOException e) {
			System.out.println("FFFFF" + e.getMessage());
		}
	}
}
