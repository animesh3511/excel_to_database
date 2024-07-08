package com.example.excel_to_database.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface ExcelService {


    Object saveDataFromExcel(InputStream inputStream) throws IOException;

    Object isExcelFile(MultipartFile file);

    Object exportDataToExcel(HttpServletResponse response) throws IOException;
}
