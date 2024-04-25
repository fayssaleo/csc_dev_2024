package com.ta.csc.service.excelGenerator;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import com.ta.csc.dto.ShippingLinesInvoicing;
import com.ta.csc.intervals.StandardContainerIntervals;
import com.ta.csc.service.ContainerService;
import com.ta.csc.service.StandardContainerService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ta.csc.helper.Constants.*;
import static com.ta.csc.helper.Fixtures.*;
import static com.ta.csc.intervals.ContainerIntervals.*;





@Service
public class ExcelGeneratorService {

    private final ContainerService containerService;
    private final StandardContainerService standardContainerService;

    public ExcelGeneratorService(ContainerService containerService, StandardContainerService standardContainerService) {
        this.containerService = containerService;
        this.standardContainerService = standardContainerService;
    }

    public void generateExcel(SXSSFWorkbook workbook, String sheetName, List<Container> containers, List<String> titles, int numberOfColumns, String month, String year) throws IOException, ParseException {
        String inputFileName = "input.csv";
        FileWriter csvWriter = new FileWriter(inputFileName);
        titles.forEach(title -> {
            try {
                csvWriter.append(title);
                csvWriter.append(",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.append("\n");
        for (Container container :containers) {
            List<String> data = new ArrayList<>();
            data.add(container.getShippingLine());
            data.add(container.getContainerNumber());
            data.add(container.getLength());
            data.add(container.getType());
            data.add(container.getFullOrEmpty());
            data.add(container.getInvoiceCategory());
            data.add(container.isReef() ? "Yes" : "No");
            data.add(container.isImdg() ? "Yes" : "No");
            data.add(container.isOog() ? "Yes" : "No");
            data.add(container.isDmg() ? "Yes" : "No");
            data.add(String.valueOf(container.getIncDate()));
            data.add(String.valueOf(container.getOutDate()));
            data.add(String.valueOf(container.getInvoiceStorageDuration()));
            data.add(String.valueOf(containerService.calculateHazardousContainerSurcharge(container, month, year)));
            data.add(String.valueOf(containerService.calculateOOGSurchargeContainer(container, month, year)));
            data.add(String.valueOf(containerService.calculateReeferConnectionSurcharge(container, month, year)));
            data.add(String.valueOf(containerService.calculateDMGSurchargeContainer(container, month, year)));
            data.add(String.valueOf(containerService.calculateTankSurchargeContainer(container, month, year)));
            data.add(String.valueOf(containerService.calculateTotalSurchargePrice(container, month, year)));
            containerService.calculateStorageOfEachInterval(container, month, year).forEach(value -> data.add(String.valueOf(value)));
            data.add(String.valueOf(containerService.calculateTotalPrice(container, month, year)));

            data.forEach(item -> {

                try {
                    csvWriter.append(item);
                    csvWriter.append(",");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();

        BufferedReader br = new BufferedReader(new FileReader(inputFileName));

        SXSSFSheet sheet = workbook.createSheet(sheetName);

        String line = br.readLine();
        Row row = sheet.createRow(0);
        String[] items = line.split(",");

        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        XSSFCellStyle  titlesCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        titlesCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titlesCellStyle.setFillPattern(FillPatternType.DIAMONDS);
        titlesCellStyle.setBorderBottom(BorderStyle.THIN);
        titlesCellStyle.setBorderTop(BorderStyle.THIN);
        titlesCellStyle.setBorderRight(BorderStyle.THIN);
        titlesCellStyle.setBorderLeft(BorderStyle.THIN);

        XSSFCellStyle  totalPriceCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        totalPriceCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("0.00€"));
        totalPriceCellStyle.setBorderBottom(BorderStyle.THIN);
        totalPriceCellStyle.setBorderTop(BorderStyle.THIN);
        totalPriceCellStyle.setBorderRight(BorderStyle.THIN);
        totalPriceCellStyle.setBorderLeft(BorderStyle.THIN);
        totalPriceCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        totalPriceCellStyle.setFillPattern(FillPatternType.DIAMONDS);

        XSSFCellStyle  cellStyle2 = (XSSFCellStyle) workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle2.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        XSSFCellStyle pricesCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        pricesCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00€"));
        pricesCellStyle.setBorderBottom(BorderStyle.THIN);
        pricesCellStyle.setBorderTop(BorderStyle.THIN);
        pricesCellStyle.setBorderRight(BorderStyle.THIN);
        pricesCellStyle.setBorderLeft(BorderStyle.THIN);

        for (int i = 0, col = 0; i < items.length; i++) {
            String item = items[i];
            Cell cell = row.createCell(col++);
            cell.setCellValue(item);
            cell.setCellStyle(titlesCellStyle);
        }
        Cell cell = row.createCell(26);
        cell.setCellValue("Total Containers pricing");
        cell.setCellStyle(titlesCellStyle);
        line = br.readLine();
        for (int rows = 1; line != null; rows++) {
            row = sheet.createRow(rows);
            if (rows == 1) {

                cell = row.createCell(26);
                cell.setCellValue(containerService.calculateTotalPrice(containers, month, year));
                cell.setCellStyle(totalPriceCellStyle);
            }
            items = line.split(",");
            for (int i = 0, col = 0; i < 10; i++) {
                String item = items[i];
                cell = row.createCell(col++);
                cell.setCellValue(item);
                cell.setCellStyle(cellStyle);
            }

            cell = row.createCell(10);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(items[10]));
            cell.setCellStyle(cellStyle2);
            cell = row.createCell(11);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(items[11]));
            cell.setCellStyle(cellStyle2);
            cell = row.createCell(12);
            cell.setCellValue(Integer.parseInt(items[12]));
            cell.setCellStyle(cellStyle);
            for (int i = 13, col = 13; i < numberOfColumns; i++) {
                String item = items[i];
                cell = row.createCell(col++);
                cell.setCellValue(Double.parseDouble(item));
                cell.setCellStyle(pricesCellStyle);
            }

            line = br.readLine();
        }
        sheet.setDefaultColumnWidth(20);
        sheet.setAutoFilter(new CellRangeAddress(0, containers.size(), 0, numberOfColumns - 1));

        br.close();
        Path path = FileSystems.getDefault().getPath(inputFileName);
        Files.delete(path);

    }

    public void generateExcel2(SXSSFWorkbook workbook, String shippingLine, String month, String year) throws IOException, ParseException {
        String inputFileName = "input.csv";
        FileWriter csvWriter = new FileWriter(inputFileName);

        if(shippingLine.equals("HLC")) {
            csvWriter.append(ShippingLinesInvoicing.HLCSumMonthlyStorageDetails(month,year));
        }
        else if(shippingLine.equals("ARK")) {
            csvWriter.append(ShippingLinesInvoicing.ARKSumMonthlyStorageDetails(month,year));
        }
        else if(shippingLine.equals("YML")) {
            csvWriter.append(ShippingLinesInvoicing.YMLSumMonthlyStorageDetails(month,year));
        }
        else if(shippingLine.equals("ONE")) {
            csvWriter.append(ShippingLinesInvoicing.ONESumMonthlyStorageDetails(month,year));
        }
        else {
            csvWriter.append(ShippingLinesInvoicing.StandardSumMonthlyStorageDetails(shippingLine,month,year));
        }

        csvWriter.flush();
        csvWriter.close();

        //il a pointé sur br
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));

        //il a créé une feuille
        SXSSFSheet sheet = workbook.createSheet("INVOICE");

        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        Font font2= workbook.createFont();
        font2.setFontHeightInPoints((short)10);
        font2.setFontName("Arial");
        font2.setBold(true);
        font2.setItalic(false);
        cellStyle.setFont(font2);

        XSSFCellStyle  titlesCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor myColor = new XSSFColor(Color.decode("#FFEB9C"));
        titlesCellStyle.setFillForegroundColor(myColor);
        titlesCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font= workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.BROWN.getIndex());
        font.setBold(true);
        font.setItalic(false);
        titlesCellStyle.setFont(font);
        titlesCellStyle.setBorderBottom(BorderStyle.NONE);
        titlesCellStyle.setBorderTop(BorderStyle.THIN);
        titlesCellStyle.setBorderRight(BorderStyle.THIN);
        titlesCellStyle.setBorderLeft(BorderStyle.THIN);


        XSSFCellStyle  titlesCellStyle2 = (XSSFCellStyle) workbook.createCellStyle();
        myColor = new XSSFColor(Color.decode("#FFEB9C"));
        titlesCellStyle2.setFillForegroundColor(myColor);
        titlesCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font= workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.BROWN.getIndex());
        font.setBold(true);
        font.setItalic(false);
        titlesCellStyle2.setFont(font);
        titlesCellStyle2.setBorderBottom(BorderStyle.THIN);
        titlesCellStyle2.setBorderTop(BorderStyle.NONE);
        titlesCellStyle2.setBorderRight(BorderStyle.THIN);
        titlesCellStyle2.setBorderLeft(BorderStyle.THIN);


        XSSFCellStyle cellStyleTotalAmount = (XSSFCellStyle) workbook.createCellStyle();
        cellStyleTotalAmount.setBorderBottom(BorderStyle.THIN);
        cellStyleTotalAmount.setBorderTop(BorderStyle.THIN);
        cellStyleTotalAmount.setBorderRight(BorderStyle.THIN);
        cellStyleTotalAmount.setBorderLeft(BorderStyle.THIN);
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleTotalAmount.setDataFormat(createHelper.createDataFormat().getFormat("0.00€"));
        Font font3= workbook.createFont();
        font3.setFontHeightInPoints((short)10);
        font3.setFontName("Arial");
        font3.setBold(true);
        font3.setItalic(false);
        cellStyleTotalAmount.setFont(font2);





        String line = br.readLine();
        Row row = sheet.createRow(0);

        String[] items = line.split(";");
        line = br.readLine();
        for (int i = 0, col = 0; i < items.length; i++) {
            String item = items[i];
            Cell cell = row.createCell(col++);
            cell.setCellValue(item);
            cell.setCellStyle(titlesCellStyle);


        }
         row = sheet.createRow(1);
        for (int i = 0, col = 0; i < items.length; i++) {
            String item = "";
            Cell cell = row.createCell(col++);
            cell.setCellValue(item);
            cell.setCellStyle(titlesCellStyle2);


        }
        int size=0;
        for (int rows = 2; line != null; rows++) {
            row = sheet.createRow(rows);
            items = line.split(";");
            for (int i = 0, col = 0; i < 6; i++) {

                if(i==5){
                    String item = items[i];
                    Cell cell = row.createCell(col++);
                    if(!item.equals(" "))
                    cell.setCellValue(Double.parseDouble(item));
                    cell.setCellStyle(cellStyleTotalAmount);
                }
                else{
                    String item = items[i];
                    Cell cell = row.createCell(col++);
                    cell.setCellValue(item);
                    cell.setCellStyle(cellStyle);
                }
            }
            size++;
            line = br.readLine();
        }
        sheet.setDefaultColumnWidth(20);
        sheet.setColumnWidth(0,20000);
        sheet.setAutoFilter(new CellRangeAddress(0, size, 0, 6 - 1));

        br.close();
        Path path = FileSystems.getDefault().getPath(inputFileName);
        Files.delete(path);

    }


    public void generateEmptyContainersSheet(SXSSFWorkbook workbook, String month, String year, List<Container> containers,String shippingLine) throws ParseException {
        SXSSFSheet  sheet = workbook.createSheet("Empty Containers");
        XSSFCellStyle  titlesCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        titlesCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titlesCellStyle.setFillPattern(FillPatternType.DIAMONDS);
        titlesCellStyle.setBorderBottom(BorderStyle.THIN);
        titlesCellStyle.setBorderTop(BorderStyle.THIN);
        titlesCellStyle.setBorderRight(BorderStyle.THIN);
        titlesCellStyle.setBorderLeft(BorderStyle.THIN);
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(1);
        cell.setCellValue("Day");
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(2);
        cell.setCellValue("Number of Teus");
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(3);
        cell.setCellValue("Number of Teus to invoice");
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(4);
        cell.setCellValue("Price");
        cell.setCellStyle(titlesCellStyle);

        XSSFCellStyle  cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        XSSFCellStyle  priceCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        priceCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("0.00€"));
        priceCellStyle.setBorderBottom(BorderStyle.THIN);
        priceCellStyle.setBorderTop(BorderStyle.THIN);
        priceCellStyle.setBorderRight(BorderStyle.THIN);
        priceCellStyle.setBorderLeft(BorderStyle.THIN);

        int size = getNumberOfEmptyContainersTeus(month, year, containers).size();
        for (int i = 0; i < size; i++) {
            row = sheet.createRow(i + 2);
            cell = row.createCell(1);
            cell.setCellValue(i + 1);
            cell.setCellStyle(cellStyle);
            cell = row.createCell(2);
            cell.setCellValue(getNumberOfEmptyContainersTeus(month, year, containers).get(i));
            cell.setCellStyle(cellStyle);
            cell = row.createCell(3);
            cell.setCellValue(getNumberOfEmptyContainersTeus(month, year, containers).get(i) <= numberOfFreeTeus(shippingLine) ? 0 : getNumberOfEmptyContainersTeus(month, year, containers).get(i) - numberOfFreeTeus(shippingLine));
            cell.setCellStyle(cellStyle);
            cell = row.createCell(4);
            cell.setCellValue(containerService.getEmptyContainerPrices(month, year, shippingLine).get(i));
            cell.setCellStyle(priceCellStyle);

        }
        XSSFCellStyle  totalPriceCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        totalPriceCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("0.00€"));
        totalPriceCellStyle.setBorderBottom(BorderStyle.THIN);
        totalPriceCellStyle.setBorderTop(BorderStyle.THIN);
        totalPriceCellStyle.setBorderRight(BorderStyle.THIN);
        totalPriceCellStyle.setBorderLeft(BorderStyle.THIN);
        totalPriceCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        totalPriceCellStyle.setFillPattern(FillPatternType.DIAMONDS);

        row = sheet.createRow(34);
        cell = row.createCell(1);
        cell.setCellValue("Total");
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(2);
        int firstRow = 2;
        cell.setCellFormula(createCellSumFormula("C", firstRow, size + firstRow - 1));
        cell.setCellStyle(titlesCellStyle);
        cell =row.createCell(3);
        cell.setCellFormula(createCellSumFormula("D", firstRow, size + firstRow - 1));
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(4);
        cell.setCellFormula(createCellSumFormula("E", firstRow, size + firstRow - 1));
        cell.setCellStyle(totalPriceCellStyle);

        row = sheet.getRow(1);
        cell = row.createCell(6);
        cell.setCellValue("Number of free Teus per day ");
        cell.setCellStyle(titlesCellStyle);
        row = sheet.getRow(2);
        cell = row.createCell(6);
        cell.setCellValue(numberOfFreeTeus(shippingLine));
        cell.setCellStyle(cellStyle);

        row = sheet.getRow(4);
        cell = row.createCell(6);
        cell.setCellValue("Price of a non free teus ");
        cell.setCellStyle(titlesCellStyle);
        row = sheet.getRow(5);
        cell = row.createCell(6);
        cell.setCellValue(priceOfEmptyTeus(shippingLine));
        cell.setCellStyle(priceCellStyle);

        sheet.setDefaultColumnWidth(25);

    }


    public void generateRowAndCellDetails(Sheet sheet,int index,List<Integer> quantities,List<Interval> intervals,String title,String unit,CellStyle titlesCellStyle, CellStyle cellStyle,CellStyle priceCellStyle) {
        for(int i=0;i<quantities.size();i++) {
            Row row = sheet.createRow(i+index);
            Cell cell = row.createCell(0);
            cell.setCellValue(title+" "+rank(i)+" period");
            cell.setCellStyle(titlesCellStyle);
            cell = row.createCell(1);
            cell.setCellValue(unit);
            cell.setCellStyle(cellStyle);
            cell = row.createCell(2);
            cell.setCellValue(intervals.get(i).getPrice());
            cell.setCellStyle(priceCellStyle);
            cell = row.createCell(3);
            cell.setCellValue(quantities.get(i));
            cell.setCellStyle(cellStyle);
            cell = row.createCell(4);
            cell.setCellValue(DoubleRounder.round(intervals.get(i).getPrice()*quantities.get(i),2));
            cell.setCellStyle(priceCellStyle);

        }
    }
    public void generateARow(Sheet sheet,int index,String title,String unit,double unitPrice,int quantity,CellStyle titlesCellStyle,CellStyle cellStyle,CellStyle priceCellStyle) {
        Row row = sheet.createRow(index);
        Cell cell = row.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(titlesCellStyle);
        cell = row.createCell(1);
        cell.setCellValue(unit);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue(unitPrice);
        cell.setCellStyle(priceCellStyle);
        cell = row.createCell(3);
        cell.setCellValue(quantity);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(4);
        cell.setCellValue(unitPrice*quantity);
        cell.setCellStyle(priceCellStyle);
    }


    public ByteArrayInputStream generateHLCExcel(String month, String year) throws IOException, ParseException {

        String shippingLine = "HLC";
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<Container> hlcContainers = containerService.getByShippingLine("HLC");
        List<Container> fullTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",false);
        List<Container> fullReeferTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",true);

        List<Container> importContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",false);
        List<Container> exportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",false);
        List<Container> fullDirectContainers = new ArrayList<>();
        fullDirectContainers.addAll(exportContainers);
        fullDirectContainers.addAll(importContainers);
        importContainers.addAll(exportContainers);

        List<Container> reeferImportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",true);
        List<Container> reeferExportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",true);
        List<Container> fullReeferDirectContainers = new ArrayList<>();
        fullReeferDirectContainers.addAll(reeferImportContainers);
        fullReeferDirectContainers.addAll(reeferExportContainers);
        importContainers.addAll(exportContainers);

        generateExcel2(workbook, shippingLine, month, year);
        generateExcel(workbook, "Transshipment", fullTranshipmentContainers, hlcFullTransshipment(), 24, month, year);
        generateExcel(workbook, "Reefer Transshipment", fullReeferTranshipmentContainers, hlcFullTransshipment(), 24, month, year);
        generateExcel(workbook, "Direct", fullDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Reefer Direct", fullReeferDirectContainers, hlcFullDirect(), 23, month, year);
        generateEmptyContainersSheet(workbook, month, year, hlcContainers,"HLC");
        //generateSummarySheet(workbook,"HLC",month,year);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

    public ByteArrayInputStream generateARKExcel(String month, String year) throws IOException, ParseException {

        String shippingLine = "ARK";
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<Container> arkContainers = containerService.getByShippingLine(shippingLine);
        List<Container> fullTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",false);
        List<Container> fullReeferTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",true);

        List<Container> importContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",false);
        List<Container> exportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",false);
        List<Container> fullDirectContainers = new ArrayList<>();
        fullDirectContainers.addAll(exportContainers);
        fullDirectContainers.addAll(importContainers);
        importContainers.addAll(exportContainers);

        List<Container> reeferImportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",true);
        List<Container> reeferExportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",true);
        List<Container> fullReeferDirectContainers = new ArrayList<>();
        fullReeferDirectContainers.addAll(reeferImportContainers);
        fullReeferDirectContainers.addAll(reeferExportContainers);

        generateExcel2(workbook, shippingLine, month, year);
        generateExcel(workbook, "Transshipment", fullTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Reefer Transshipment", fullReeferTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Direct", fullDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Reefer Direct", fullReeferDirectContainers, hlcFullDirect(), 23, month, year);
        generateEmptyContainersSheet(workbook, month, year, arkContainers,"ARK");
        //generateSummarySheet(workbook,"ARK",month,year);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

    public ByteArrayInputStream generateYMLExcel(String month, String year) throws IOException, ParseException {

        String shippingLine = "YML";
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<Container> ymlContainers = containerService.getByShippingLine(shippingLine);
        List<Container> fullTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",false);
        List<Container> fullReeferTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",true);

        List<Container> importContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",false);
        List<Container> exportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",false);
        List<Container> fullDirectContainers = new ArrayList<>();
        fullDirectContainers.addAll(exportContainers);
        fullDirectContainers.addAll(importContainers);
        importContainers.addAll(exportContainers);

        List<Container> reeferImportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",true);
        List<Container> reeferExportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",true);
        List<Container> fullReeferDirectContainers = new ArrayList<>();
        fullReeferDirectContainers.addAll(reeferImportContainers);
        fullReeferDirectContainers.addAll(reeferExportContainers);

        generateExcel2(workbook, shippingLine, month, year);
        generateExcel(workbook, "Transshipment", fullTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Reefer Transshipment", fullReeferTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Direct", fullDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Reefer Direct", fullReeferDirectContainers, hlcFullDirect(), 23, month, year);
        generateEmptyContainersSheet(workbook, month, year, ymlContainers,"YML");
        //generateSummarySheet(workbook,"YML",month,year);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

    public ByteArrayInputStream generateONEExcel(String month, String year) throws IOException, ParseException {

        String shippingLine = "ONE";
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<Container> oneContainers = containerService.getByShippingLine(shippingLine);
        List<Container> fullTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",false);
        List<Container> fullReeferTranshipmentContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Transshipment",true);

        List<Container> importContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",false);
        List<Container> exportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",false);
        List<Container> fullDirectContainers = new ArrayList<>();
        fullDirectContainers.addAll(exportContainers);
        fullDirectContainers.addAll(importContainers);
        importContainers.addAll(exportContainers);

        List<Container> reeferImportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Import",true);
        List<Container> reeferExportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine, "Full", "Export",true);
        List<Container> fullReeferDirectContainers = new ArrayList<>();
        fullReeferDirectContainers.addAll(reeferImportContainers);
        fullReeferDirectContainers.addAll(reeferExportContainers);

        generateExcel2(workbook, shippingLine, month, year);
        generateExcel(workbook, "Transshipment", fullTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Reefeer Transshipment", fullReeferTranshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Direct", fullDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Reefer Direct", fullReeferDirectContainers, hlcFullDirect(), 23, month, year);
        generateEmptyContainersSheet(workbook, month, year, oneContainers,"ONE");
        //generateSummarySheet(workbook,"ONE",month,year);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

    public ByteArrayInputStream generateStandardExcel(String shippingLine,String month, String year) throws IOException, ParseException {

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<Container> transshipmentContainers = containerService.getContainerByShippingLineAndInvoiceCategory(shippingLine,"Transshipment");


        List<Container> importFullDirectContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Full","Import",false);
        List<Container> exportFullDirectContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Full","Export",false);
        importFullDirectContainers.addAll(exportFullDirectContainers);

        List<Container> importEmptyDirectContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Empty","Import",false);
        List<Container> exportEmptyDirectContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Empty","Export",false);
        importEmptyDirectContainers.addAll(exportEmptyDirectContainers);

        List<Container> fullReeferImportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Full","Import",true);
        List<Container> fullReeferExportContainers = containerService.getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(shippingLine,"Full","Export",true);
        fullReeferImportContainers.addAll(fullReeferExportContainers);

        generateExcel2(workbook, shippingLine, month, year);
        generateExcel(workbook, "Transshipment", transshipmentContainers, nonHLCFullTransshipment(), 26, month, year);
        generateExcel(workbook, "Full Direct", importFullDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Empty Direct", importEmptyDirectContainers, hlcFullDirect(), 23, month, year);
        generateExcel(workbook, "Full Reefer Direct", fullReeferImportContainers, hlcFullDirect(), 23, month, year);

        //generateSummarySheet(workbook,shippingLine,month,year);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

}
