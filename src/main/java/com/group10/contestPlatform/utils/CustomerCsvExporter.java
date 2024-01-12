package com.group10.contestPlatform.utils;

import com.group10.contestPlatform.entities.User;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;


public class CustomerCsvExporter extends AbstractExporter{

public void export(List<User> listCustomer, HttpServletResponse response) throws IOException {
		
		super.setResponseHeader(response, "text/csv", ".csv", "customers_");
		
		Writer writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
		writer.write('\uFEFF');
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer,
				CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = {"User ID", "First Name", "Last Name", "E-mail"};
		String[] fieldMapping = {"id", "firstName", "lastName", "email"  };

		csvWriter.writeHeader(csvHeader);

		for (User customer : listCustomer) {
			csvWriter.write(customer, fieldMapping);
		}

		csvWriter.close();
	}
}
