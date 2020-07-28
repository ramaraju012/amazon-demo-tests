package com.demo.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import com.demo.property.PropertyLoader;

public class DataDriven {
	private static final Logger LOGGER = Logger.getLogger(DataDriven.class);

	private static final String TEST_DATA_FILE = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "testdata.xlsx";

	private DataDriven() {
	}

	/**
	 * Method to return test data placed in excel file.
	 * 
	 * @param className
	 *            class name of the test case
	 * @param methodName
	 *            method name or test case name
	 * @return returns two dimensional array containing test data based on matched
	 *         class and method name.
	 */
	public static Object[][] getData(String className, String methodName) {
		Object[][] testData = null;
		LOGGER.info("Reading test data from " + TEST_DATA_FILE + " file");
		try {
			FileInputStream filePath = new FileInputStream(new File(TEST_DATA_FILE));
			XSSFWorkbook workbook = new XSSFWorkbook(filePath);
			workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			Sheet sheet = workbook.getSheet(className);

			// saving header names in list
			List<String> headersList = new ArrayList<String>();
			for (Cell cell : sheet.getRow(0)) {
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
					headersList.add(cell.getStringCellValue());
				}
			}
			LOGGER.debug("Headers list: " + headersList);

			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String firstCellValue = row.getCell(0).getStringCellValue();
				if (!firstCellValue.isEmpty() && firstCellValue.equals(methodName)) {
					Map<String, Object> inputMap = new LinkedHashMap<String, Object>();
					for (int i = 0; i < headersList.size(); i++) {
						Object cellValue = getCellValue(row.getCell(i));
						inputMap.put(headersList.get(i), cellValue);
					}
					dataList.add(inputMap);
				}
			}
			workbook.close();
			LOGGER.debug("Data list: " + dataList);
			if (dataList.size() > 0) {
				testData = new Object[dataList.size()][1];
				for (int i = 0; i < dataList.size(); i++) {
					testData[i][0] = dataList.get(i);
				}
			}
		} catch (IOException ioe) {
			LOGGER.error("IO Exception occurred while reading test data file. Exception details are : " + ioe);
		}
		return testData;
	}

	/**
	 * Method to return excel cell value based on passed arguments
	 * 
	 * @param sheetName
	 *            sheet name or class name of the test case
	 * @param rowNumber
	 *            row number in which data is present
	 * @param columnNumber
	 *            column number of the cell
	 * @return returns an object type containing the cell value
	 */
	public static Object getData(String sheetName, int rowNumber, int columnNumber) {
		LOGGER.info("Reading test data from " + TEST_DATA_FILE + " file");
		Object data = "";
		try {
			FileInputStream filePath = new FileInputStream(new File(TEST_DATA_FILE));
			XSSFWorkbook workbook = new XSSFWorkbook(filePath);
			workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNumber - 1);
			data = getCellValue(row.getCell(columnNumber - 1));
			workbook.close();
		} catch (IOException ioe) {
			LOGGER.error("IO Exception occurred while reading test data file. Exception details are : " + ioe);
		}
		return data;
	}

	/**
	 * Method to return excel cell value based on cell type
	 * 
	 * @param cell
	 *            cell object
	 * @return returns a object type having the cell value
	 */
	private static Object getCellValue(Cell cell) {
		Object cellValue = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			String value = cell.getStringCellValue();
			//Code for replacing cell value with the configuration value
			Pattern pattern = Pattern.compile("\\{\"(.*)\"\\}");
			Matcher matcher = pattern.matcher(value);
			while(matcher.find()) {
				value = value.replace(matcher.group(), PropertyLoader.getProperty(matcher.group(1)));
			}
			cellValue = value;
			break;

		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(CellType.STRING);
			cellValue = cell.getStringCellValue();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;

		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;

		default:
			LOGGER.error("Un supported cell type found while reading test data");
		}
		return cellValue;
	}

	/**
	 * Data provider method to read test data from excel file. This method will be
	 * passed as a argument for Data provider test case e.g. @Test(dataProvider =
	 * "getTestData", dataProviderClass = DataDriven.class)
	 * 
	 * @param testMethod
	 *            test method name
	 * @return returns a two dimensional array
	 */
	@DataProvider
	public static Object[][] getTestData(ITestNGMethod testMethod) {
		return getData(testMethod.getTestClass().getRealClass().getSimpleName(), testMethod.getMethodName());
	}

}
