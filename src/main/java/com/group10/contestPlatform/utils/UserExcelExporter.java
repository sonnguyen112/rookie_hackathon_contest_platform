package com.group10.contestPlatform.utils;

import java.io.IOException;
import java.util.List;


import com.group10.contestPlatform.entities.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class UserExcelExporter extends AbstractExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<User> customerList;

	public UserExcelExporter(List<User> customerList) {
		this.customerList = customerList;
		workbook = new XSSFWorkbook();
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style){
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer){
			cell.setCellValue((Integer) value);
		}else if (value instanceof Double){
			cell.setCellValue((Double) value);
		}else if (value instanceof Boolean){
			cell.setCellValue((Boolean) value);
		}else if (value instanceof Long){
			cell.setCellValue((Long) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void createHeaderRow(){
		sheet   = workbook.createSheet("Customer Information");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(row, 0, "Customer Information", style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		font.setFontHeightInPoints((short) 10);

		row = sheet.createRow(1);
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(row, 0, "ID", style);
		createCell(row, 1, "First Name", style);
		createCell(row, 2, "Last Name", style);
		createCell(row, 3, "Email", style);
		createCell(row, 4, "Country", style);
		createCell(row, 5, "State", style);
		createCell(row, 6, "City", style);
		createCell(row, 7, "Address", style);
	}

	private void writeCustomerData(){
		int rowCount = 2;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (User user : customerList){
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;


			createCell(row, columnCount++, user.getId(), style);
			createCell(row, columnCount++, user.getEmail(), style);
			createCell(row, columnCount++, user.getFirstName(), style);
			createCell(row, columnCount++, user.getLastName(), style);
			createCell(row, columnCount++, user.getRole().toString(), style);
		}

	}

	public void exportDataToExcel(HttpServletResponse response) throws IOException {
		createHeaderRow();
		writeCustomerData();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}


}
