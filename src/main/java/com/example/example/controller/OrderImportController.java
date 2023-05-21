package com.example.example.controller;

import com.example.example.config.WebConstants;
import com.example.example.service.importer.ExcelImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("import")
@RequiredArgsConstructor
public class OrderImportController {


    private final ExcelImportService excelImportService;

    @PostMapping(value = "/xlsx", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void importViaExcel(@RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String vendorCode,
                               @RequestPart("file") MultipartFile file) throws IOException {
        this.excelImportService.importFile(vendorCode, file);
    }

}
