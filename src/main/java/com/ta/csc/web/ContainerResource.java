package com.ta.csc.web;


import com.ta.csc.domain.Container;
import com.ta.csc.dto.ContainerStorageDetailsDTO;
import com.ta.csc.dto.ShippingLineFreePoolDTO;
import com.ta.csc.dto.ShippingLinesInvoicing;
import com.ta.csc.service.ContainerService;
import com.ta.csc.service.excelGenerator.ExcelGeneratorService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ContainerResource {

    private final ContainerService containerService;
    private final ExcelGeneratorService excelGeneratorService;

    public ContainerResource(ContainerService containerService, ExcelGeneratorService excelGeneratorService) {
        this.containerService = containerService;
        this.excelGeneratorService = excelGeneratorService;
    }

    @PostMapping("/loadContainers")
    public void loadContainerList(@RequestBody MultipartFile file,@RequestParam("transshipment") boolean transshipment,@RequestParam("exportContainer") boolean exportContainer,@RequestParam("importContainer") boolean importContainer,@RequestParam("depotContainer") boolean depotContainer,@RequestParam("fullContainer") boolean fullContainer,@RequestParam("emptyContainer") boolean emptyContainer) throws IOException {
        System.err.println(file.getOriginalFilename());
        containerService.loadAllContainers(file,transshipment,exportContainer,importContainer,depotContainer,fullContainer,emptyContainer);
    }


    @PostMapping("/loadFromDb")
    public void loadFromDb(@RequestParam("month") String month,@RequestParam("year") String year,@RequestParam("transshipment") boolean transshipment,@RequestParam("exportContainer") boolean exportContainer,@RequestParam("importContainer") boolean importContainer,@RequestParam("depotContainer") boolean depotContainer,@RequestParam("fullContainer") boolean fullContainer,@RequestParam("emptyContainer") boolean emptyContainer) throws IOException {
        System.err.println("fullContainer :"+fullContainer);
        System.err.println("emptyContainer :"+emptyContainer);
        containerService.loadContainersFromDb(month,year,transshipment,exportContainer,importContainer,depotContainer,fullContainer,emptyContainer);
    }


    @GetMapping("/containers")
    public List<Container> getAllContainers() {
        return containerService.getAllContainers();
    }
    @GetMapping("/container")
    public Container getByContainerNumber(@RequestParam("containerNumber")String containerNumber) {
        return  containerService.getByContainerNumber(containerNumber);
    }
    @GetMapping("/containers/shippingLine")
    public List<Container> getContainersByShippingLine(@RequestParam("shippingLine") String shippingLine) {
        return containerService.getByShippingLine(shippingLine);
    }
    @DeleteMapping("/containers")
    public void deleteAllContainers() {
        containerService.deleteAllContainers();
    }

    @GetMapping("/containers/exist")
    public boolean containersExist() {
        return containerService.containersExist();
    }

    @GetMapping("/shippingLines")
    public List<String> getShippingLines() {
        return containerService.getShippingLinesNames();
    }

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> generateExcel(@RequestParam("shippingLine") String shippingLine,@RequestParam("month") String month,@RequestParam("year") String year) throws IOException, ParseException {
        ByteArrayInputStream in;
       switch (shippingLine) {
           case "ONE" : in = excelGeneratorService.generateONEExcel(month, year);
           break;
           case "YML": in = excelGeneratorService.generateYMLExcel(month, year);
           break;
           case "ARK": in = excelGeneratorService.generateARKExcel(month, year);
           break;
           case "HLC": in = excelGeneratorService.generateHLCExcel(month, year);
           break;
           default: in = excelGeneratorService.generateStandardExcel(shippingLine,month, year);
           break;
       }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Storage_"+shippingLine+"_"+month+"_"+year+".xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
    @GetMapping("/containers/details")
    public List<ContainerStorageDetailsDTO> containerStorageDetails(@RequestParam("shippingLine") String shippingLine, @RequestParam("month") String month, @RequestParam("year") String year) throws ParseException, IOException {
        System.out.println("shippingLine :"+shippingLine+" | month :"+month+" | year :"+year);
        List<ContainerStorageDetailsDTO> shippingLinesInvoicingList =containerService.getContainersStorageDetails(shippingLine, month, year);
        ShippingLinesInvoicing.shippingLinesInvoicingList = shippingLinesInvoicingList;

        return shippingLinesInvoicingList;
    }

    @GetMapping("/containers/empty")
    public List<ShippingLineFreePoolDTO> emptyContainerStorageDetails(@RequestParam("shippingLine") String shippingLine, @RequestParam("month") String month, @RequestParam("year") String year) throws ParseException {
        return containerService.getShippingLineFreePool(shippingLine,month,year);
    }




}
