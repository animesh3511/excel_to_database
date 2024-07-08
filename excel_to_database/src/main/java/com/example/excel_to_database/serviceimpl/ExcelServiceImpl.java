package com.example.excel_to_database.serviceimpl;

import com.example.excel_to_database.model.Excel;
import com.example.excel_to_database.repository.ExcelRepository;
import com.example.excel_to_database.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;

    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);


    @Override
    public Object saveDataFromExcel(InputStream inputStream) throws IOException {

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        DataFormatter dataFormatter = new DataFormatter();
        List<Excel> list = new ArrayList<>();


        while (rowIterator.hasNext())
        {

          Row row = rowIterator.next();
            Excel excel = new Excel();

          String companyId = dataFormatter.formatCellValue(row.getCell(0));

         excel.setCompanyId(Long.valueOf(companyId));

         excel.setCompanyName(dataFormatter.formatCellValue(row.getCell(1)));

         excel.setDescription(dataFormatter.formatCellValue(row.getCell(2)));

         excel.setWebsite(dataFormatter.formatCellValue(row.getCell(3)));

         excel.setContact(dataFormatter.formatCellValue(row.getCell(4)));

        excel.setDate(dataFormatter.formatCellValue(row.getCell(5)));

        excel.setPrice(dataFormatter.formatCellValue(row.getCell(6)));

        excel.setPercentage(dataFormatter.formatCellValue(row.getCell(7)));


        list.add(excel);


        }

        excelRepository.saveAll(list);

        logger.info("data saved from excel sheet to database");
        return "Data Saved Succesfully";

    }

    @Override
    public Object isExcelFile(MultipartFile file) {

      String contentType = file.getContentType();

     if (contentType!=null && contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
     {

      return true;
     }

     else
     {

       return false;
     }



    }

    @Override
    public Object exportDataToExcel(HttpServletResponse response) throws IOException {

        List<Excel> list = excelRepository.findAll();
        Workbook workbook = new XSSFWorkbook();

       Sheet sheet = workbook.createSheet("new excel file");

       Row headerRow = sheet.createRow(0);

       headerRow.createCell(0).setCellValue("Company_id");
       headerRow.createCell(1).setCellValue("Company_name");
       headerRow.createCell(2).setCellValue("Description");
       headerRow.createCell(3).setCellValue("Website");
       headerRow.createCell(4).setCellValue("Contact");
       headerRow.createCell(5).setCellValue("Date");
       headerRow.createCell(6).setCellValue("Price");
       headerRow.createCell(7).setCellValue("Percentage");

       int rowNum = 1;

     for (Excel excel : list)
     {

      Row row  = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(excel.getCompanyId());
      row.createCell(1).setCellValue(excel.getCompanyName());
      row.createCell(2).setCellValue(excel.getDescription());
      row.createCell(3).setCellValue(excel.getWebsite());
      row.createCell(4).setCellValue(excel.getContact());
      row.createCell(5).setCellValue(excel.getDate());
      row.createCell(6).setCellValue(excel.getPrice());
      row.createCell(7).setCellValue(excel.getPercentage());

     }

     response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
     response.setHeader("Content-Disposition","attachment; filename=excel_new.xlsx");

    ServletOutputStream outputStream = response.getOutputStream();

    workbook.write(outputStream);
    workbook.close();
    outputStream.close();

    return "excel sheet download";


    }


}
