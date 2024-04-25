package com.ta.csc.service;


import com.ta.csc.domain.Container;
import com.ta.csc.dto.ContainerDTO;
import com.ta.csc.dto.ContainerStorageDetailsDTO;
import com.ta.csc.dto.ShippingLineFreePoolDTO;
import com.ta.csc.exception.NotFoundException;
import com.ta.csc.repositroy.ContainerRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ta.csc.helper.Fixtures.getNumberOfEmptyContainersTeus;
import static com.ta.csc.helper.Fixtures.hasShippingLineFreePool;


@Service
public class ContainerService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ContainerRepository containerRepository;
    private final StandardContainerService standardContainerService;
    private final HLCContainerService hlcContainerService;
    private final ARKContainerService arkContainerService;
    private final YMLContainerService ymlContainerService;
    private final ONEContainerService oneContainerService;
    private final RestTemplate restTemplate;

    @Value("${host}")
    private String host;

    public ContainerService(ContainerRepository containerRepository, StandardContainerService standardContainerService, HLCContainerService hlcContainerService, ARKContainerService arkContainerService, YMLContainerService ymlContainerService, ONEContainerService oneContainerService, RestTemplate restTemplate) {
        this.containerRepository = containerRepository;
        this.standardContainerService = standardContainerService;
        this.hlcContainerService = hlcContainerService;
        this.arkContainerService = arkContainerService;
        this.ymlContainerService = ymlContainerService;
        this.oneContainerService = oneContainerService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void loadAllContainers(MultipartFile file,boolean transshipment,boolean exportContainer,boolean importContainer,boolean depotContainer,boolean fullContainer,boolean emptyContainer) throws IOException {
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        String filename = file.getOriginalFilename();
        assert filename != null;
        String[] fileSplite = filename.split("\\.");
        String extension = fileSplite[fileSplite.length-1];
        Workbook workbook = extension.equals("xls") ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int numofRows = sheet.getLastRowNum();
        for (int i = 1; i <= numofRows; i++) {
            Row row = sheet.getRow(i);
            Container container = new Container();
            container.setShippingLine(row.getCell(0).getStringCellValue());
            container.setContainerNumber(row.getCell(1).getStringCellValue());
            container.setLength(row.getCell(2).getStringCellValue());
            container.setType(row.getCell(3).getStringCellValue());
            container.setFullOrEmpty(row.getCell(4).getStringCellValue());
            if(row.getCell(5).getStringCellValue().replaceAll("\\s","").equals("Transhipment"))
                container.setInvoiceCategory("Transshipment");
            else
                container.setInvoiceCategory(row.getCell(5).getStringCellValue());
            container.setReef(row.getCell(6).getStringCellValue().replaceAll("\\s","").equalsIgnoreCase("Yes"));
            container.setImdg(row.getCell(7).getStringCellValue().replaceAll("\\s","").equalsIgnoreCase("Yes"));
            container.setOog(row.getCell(8).getStringCellValue().replaceAll("\\s","").equalsIgnoreCase("Yes"));
            container.setDmg(row.getCell(9).getStringCellValue().replaceAll("\\s","").equalsIgnoreCase("Yes"));
            container.setIncDate(row.getCell(11).getDateCellValue());
            container.setOutDate(row.getCell(12).getDateCellValue() == null || row.getCell(12).getDateCellValue().equals("") ? new Date() : row.getCell(12).getDateCellValue());
            container.setInvoiceStorageDuration((int) row.getCell(10).getNumericCellValue());
                if(container.getContainerNumber().replaceAll("\\s","").equals("TCNU1766655"))
               System.err.println("import : " + container.getInvoiceCategory().replaceAll("\\s","").equals("Import")); 
           
            
            if(( (container.getInvoiceCategory().replaceAll("\\s","").equals("Transshipment") && transshipment) ||
                  (container.getInvoiceCategory().replaceAll("\\s","").equals("Export") && exportContainer) ||
                  (container.getInvoiceCategory().replaceAll("\\s","").equals("Import") && importContainer) ||
                    (container.getInvoiceCategory().replaceAll("\\s","").equals("Depot") && depotContainer)
            ) &&
                    ( (container.getFullOrEmpty().replaceAll("\\s","").equals("Full") && fullContainer) || (container.getFullOrEmpty().replaceAll("\\s","").equals("Empty") && emptyContainer) )

                ){
                    containerRepository.save(container);
                    if (i % 50 == 0) {
                        entityManager.flush();
                    }
                }

        }
        entityManager.flush();

    }
    @Transactional
    public void loadContainersFromDb(String month,String year,boolean transshipment,boolean exportContainer,boolean importContainer,boolean depotContainer,boolean fullContainer,boolean emptyContainer) throws IOException {

       String url = host+"/api/v1/all?month="+month+"&year="+year+"&transshipment="+transshipment+"&exportContainer="+exportContainer+"&importContainer="+importContainer+"&depotContainer="+depotContainer+"&fullContainer="+fullContainer+"&emptyContainer="+emptyContainer;
        System.err.println(host+"/api/v1/all?month="+month+"&year="+year);
        ResponseEntity<ContainerDTO[]> responseEntity = restTemplate.getForEntity(url,ContainerDTO[].class);
        ContainerDTO[] containerDTOS = responseEntity.getBody();
        for(int i = 0; i< Objects.requireNonNull(containerDTOS).length; i++) {
            Container container = containerDTOS[i].toContainer();
            containerRepository.save(container);
            if (i % 50 == 0) {
                entityManager.flush();
            }
        }
        entityManager.flush();




        /*FileReader fr=new FileReader("C:\\Users\\fayssal.ourezzouq\\OneDrive - Tanger Alliance\\Bureau\\desktop\\storage reports\\August-2022\\EMPTY STORAGE_AUG.csv", StandardCharsets.ISO_8859_1);
        BufferedReader br=new BufferedReader(fr);
        String s="";
        int i=0;
        br.readLine();
        boolean stillinyard=false;
        while((s=br.readLine())!=null){
            String[] aa =s.split(";");


            //-----------------------------------------
            String[] aaDate;
            if(aa[11].equals(""))
                aa[11]="08/01/2022";
            else{
                 aaDate=aa[11].split("/");
                aa[11]=aaDate[1]+"/"+aaDate[0]+"/"+aaDate[2];
            }

            //-----------------------------------------

            if(aa[12].equals("")){
                aa[12]="08/31/2022";
                stillinyard=true;
            }
            else{
                 aaDate=aa[12].split("/");
                aa[12]=aaDate[1]+"/"+aaDate[0]+"/"+aaDate[2];
            }
            //-----------------------------------------



            Container container=new Container(
                    null,
                    aa[0],
                    aa[1],
                    aa[3],
                    aa[2],
                    aa[5],
                    aa[4],
                    aa[6].equals("Yes"),
                    aa[7].equals("Yes"),
                    aa[8].equals("Yes"),
                    aa[9].equals("Yes"),
                    (new Date(aa[11])),
                    (new Date(aa[12])),
                    Integer.parseInt(aa[10]),
                    stillinyard
                    );

            stillinyard=false;
                containerRepository.save(container);
                if (i % 50 == 0) {
                    entityManager.flush();
                }
                i++;




        }
        entityManager.flush();
       /* List<Container> list =containerRepository.findAll();
        FileWriter fw=new FileWriter("C:\\Users\\fayssal.ourezzouq\\Downloads\\Storage_Import-2021-11-01_10.00.40 FULL.csv");
        BufferedWriter bw=new BufferedWriter(fw);
        for(Container container : list){
            bw.write(container.getShippingLine()+";"
                    +container.getContainerNumber()+";"
                    +container.getLength()+";"
                    +container.getType()+";"
                    +container.getFullOrEmpty()+";"
                    +container.getInvoiceCategory()+";"
                    +container.isReef()+";"
                    +container.isImdg()+";"
                    +container.isOog()+";"
                    +container.isDmg()+";"
                    +container.getIncDate().getDate()+"/"+(container.getIncDate().getMonth()+1)+"/"+(container.getIncDate().getYear()+1900)+";"
                            +container.getOutDate().getDate()+"/"+(container.getOutDate().getMonth()+1)+"/"+(container.getOutDate().getYear()+1900)+";"
                    +container.getInvoiceStorageDuration()+";"
                    );
            bw.write("\n");
        }
        bw.close();*/
    }

    public List<String> getShippingLinesNames() {
        return containerRepository.gettAllShippingLines();
    }


    public double calculateHazardousContainerSurcharge(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateHazardousContainerSurcharge(container,month,year);
            case "ARK":
                return arkContainerService.calculateHazardousContainerSurcharge(container,month,year);
            case "YML":
                return ymlContainerService.calculateHazardousContainerSurcharge(container,month,year);
            case "ONE":
                return oneContainerService.calculateHazardousContainerSurcharge(container,month,year);
            default:
                return standardContainerService.calculateHazardousContainerSurcharge(container,month,year);
        }
    }

    public double calculateReeferConnectionSurcharge(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateReeferConnectionSurcharge(container,month,year);
            case "ARK":
                return arkContainerService.calculateReeferConnectionSurcharge(container,month,year);
            case "YML":
                return ymlContainerService.calculateReeferConnectionSurcharge(container,month,year);
            case "ONE":
                return oneContainerService.calculateReeferConnectionSurcharge(container,month,year);
            default:
                return standardContainerService.calculateReeferConnectionSurcharge(container,month,year);
        }
    }

    public double calculateOOGSurchargeContainer(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateOOGSurchargeContainer(container,month,year);
            case "ARK":
                return arkContainerService.calculateOOGSurchargeContainer(container,month,year);
            case "YML":
                return ymlContainerService.calculateOOGSurchargeContainer(container,month,year);
            case "ONE":
                return oneContainerService.calculateOOGSurchargeContainer(container,month,year);
            default:
                return standardContainerService.calculateOOGSurchargeContainer(container,month,year);
        }
    }

    public double calculateDMGSurchargeContainer(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateDMGSurchargeContainer(container,month,year);
            case "ARK":
                return arkContainerService.calculateDMGSurchargeContainer(container,month,year);
            case "YML":
                return ymlContainerService.calculateDMGSurchargeContainer(container,month,year);
            case "ONE":
                return oneContainerService.calculateDMGSurchargeContainer(container,month,year);
            default:
                return standardContainerService.calculateDMGSurchargeContainer(container,month,year);
        }
    }

    public double calculateTankSurchargeContainer(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateTankSurchargeContainer(container,month,year);
            case "ARK":
                return arkContainerService.calculateTankSurchargeContainer(container,month,year);
            case "YML":
                return ymlContainerService.calculateTankSurchargeContainer(container,month,year);
            case "ONE":
                return oneContainerService.calculateTankSurchargeContainer(container,month,year);
            default:
                return standardContainerService.calculateTankSurchargeContainer(container,month,year);
        }
    }

    public double calculateTotalSurchargePrice(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateTotalSurchargePrice(container,month,year);
            case "ARK":
                return arkContainerService.calculateTotalSurchargePrice(container,month,year);
            case "YML":
                return ymlContainerService.calculateTotalSurchargePrice(container,month,year);
            case "ONE":
                return oneContainerService.calculateTotalSurchargePrice(container,month,year);
            default:
                return standardContainerService.calculateTotalSurchargePrice(container,month,year);
        }
    }

    public double calculateContainerTotalStoragePricing(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateContainerTotalStoragePricing(container,month,year);
            case "ARK":
                return arkContainerService.calculateContainerTotalStoragePricing(container,month,year);
            case "YML":
                return ymlContainerService.calculateContainerTotalStoragePricing(container,month,year);
            case "ONE":
                return oneContainerService.calculateContainerTotalStoragePricing(container,month,year);
            default:
                return standardContainerService.calculateContainerTotalStoragePricing(container,month,year);
        }
    }

    public double calculateTotalPrice(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateTotalPrice(container,month,year);
            case "ARK":
                return arkContainerService.calculateTotalPrice(container,month,year);
            case "YML":
                return ymlContainerService.calculateTotalPrice(container,month,year);
            case "ONE":
                return oneContainerService.calculateTotalPrice(container,month,year);
            default:
                return standardContainerService.calculateTotalPrice(container,month,year);
        }
    }

    public double calculateTotalPrice(List<Container> containers,String month,String year) throws ParseException {
       double  sum = 0;
       for(Container container:containers) {
           sum+= calculateTotalPrice(container,month,year);
       }
       return DoubleRounder.round(sum,2);
    }

    public List<Double> calculateStorageOfEachInterval(Container container,String month,String year) throws ParseException {
        switch (container.getShippingLine()) {
            case "HLC":
                return hlcContainerService.calculateStorageOfEachInterval(container,month,year);
            case "ARK":
                return arkContainerService.calculateStorageOfEachInterval(container,month,year);
            case "YML":
                return ymlContainerService.calculateStorageOfEachInterval(container,month,year);
            case "ONE":
                return oneContainerService.calculateStorageOfEachInterval(container,month,year);
            default:
                return standardContainerService.calculateStorageOfEachInterval(container,month,year);
        }
    }

    public List<Double> getEmptyContainerPrices(String month,String year,String shippingLine) throws ParseException {
        switch (shippingLine) {
            case "HLC" : return hlcContainerService.getEmptyContainerPrices(month,year);
            case "ARK" : return arkContainerService.getEmptyContainerPrices(month,year);
            case "YML" : return ymlContainerService.getEmptyContainerPrices(month,year);
            case "ONE": return oneContainerService.getEmptyContainerPrices(month,year);
            default: return null;
        }
    }

    public List<ContainerStorageDetailsDTO> getContainersStorageDetails(String shippingLine, String month, String year) {
        List<Container> containers = getContainersByShippingLineAndFullOrEmpty(shippingLine);
        if(!hasShippingLineFreePool(shippingLine)) containers = getByShippingLine(shippingLine);
        return containers.stream().map( container -> {
            try {
                return getContainerStorageDetails(container,month,year);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }


    public ContainerStorageDetailsDTO getContainerStorageDetails(Container container, String month, String year) throws ParseException, IOException {
        double reeferSurcharge = calculateReeferConnectionSurcharge(container,month,year);
        double imdgSurcharge = calculateHazardousContainerSurcharge(container,month,year);
        double oogSurcharge = calculateOOGSurchargeContainer(container,month,year);
        double dmgSurcharge = calculateDMGSurchargeContainer(container,month,year);
        double tankSurcharge = calculateTankSurchargeContainer(container,month,year);
        double totalSurcharge = calculateTotalSurchargePrice(container,month,year);
        List<Double> storageOfEachIntervalInMonth = calculateStorageOfEachInterval(container,month,year);
        double totalStorageOfMonth = calculateTotalPrice(container,month,year);
        return  new ContainerStorageDetailsDTO(container.getShippingLine(),container.getContainerNumber(),container.getType(),container.getLength(),container.getInvoiceCategory(),
                container.getFullOrEmpty(),container.isReef(),container.isImdg(),container.isOog(),container.isDmg(),container.getIncDate(),container.getOutDate(),
                container.getInvoiceStorageDuration(),reeferSurcharge,imdgSurcharge,oogSurcharge,dmgSurcharge,tankSurcharge,totalSurcharge,storageOfEachIntervalInMonth,totalStorageOfMonth,container.isStillInYard());
    }

    public List<ShippingLineFreePoolDTO> getShippingLineFreePool(String shippingLine,String month,String year) throws ParseException {
        List<ShippingLineFreePoolDTO> shippingLineFreePoolDTOList = new ArrayList<>();
        List<Container> containers = getByShippingLine(shippingLine).stream().filter(container -> container.getFullOrEmpty().equals("Empty")).collect(Collectors.toList());
        List<Integer> getNumberOfTeus = getNumberOfEmptyContainersTeus(month,year,containers);
        List<Double> getEmptyContainersPrice = getEmptyContainerPrices(month,year,shippingLine);
        for (int i=0;i<getNumberOfTeus.size();i++) {
            ShippingLineFreePoolDTO shippingLineFreePoolDTO;
            if(i<5)
             shippingLineFreePoolDTO = new ShippingLineFreePoolDTO(i+1,getNumberOfTeus.get(i),0);
            else
             shippingLineFreePoolDTO = new ShippingLineFreePoolDTO(i+1,getNumberOfTeus.get(i),getEmptyContainersPrice.get(i));
            shippingLineFreePoolDTOList.add(shippingLineFreePoolDTO);
        }
        /*
        for (int i=0;i<getNumberOfTeus.size();i++) {
            ShippingLineFreePoolDTO shippingLineFreePoolDTO = new ShippingLineFreePoolDTO(i+1,getNumberOfTeus.get(i),getEmptyContainersPrice.get(i));
            shippingLineFreePoolDTOList.add(shippingLineFreePoolDTO);
        }*/
        return shippingLineFreePoolDTOList;
    }

    public double getHazardousPrice(String shippingLine) {
        switch (shippingLine) {
            case "HLC": return hlcContainerService.hazardousPrice;
            case "ARK": return arkContainerService.hazardousPrice;
            case "YML": return ymlContainerService.hazardousPrice;
            case "ONE": return oneContainerService.hazardousPrice;
            default: return standardContainerService.hazardousPrice;
        }
    }
    public double getTransshipmentReeferConnectionPrice(String shippingLine) {
        switch (shippingLine) {
            case "HLC": return hlcContainerService.transshipmentReeferConnectionPrice;
            case "ARK": return arkContainerService.transshipmentReeferConnectionPrice;
            case "YML": return ymlContainerService.transshipmentReeferConnectionPrice;
            case "ONE": return oneContainerService.transshipmentReeferConnectionPrice;
            default: return standardContainerService.getDirectReeferConnectionPrice(shippingLine);
        }
    }
    public double getDirectReeferConnectionPrice(String shippingLine) {
        switch (shippingLine) {
            case "HLC": return hlcContainerService.directReeferConnectionPrice;
            case "ARK": return arkContainerService.directReeferConnectionPrice;
            case "YML": return ymlContainerService.directReeferConnectionPrice;
            case "ONE": return oneContainerService.directReeferConnectionPrice;
            default: return standardContainerService.directReeferConnectionPrice;
        }
    }

    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    public List<Container> standardContainers() {
        return containerRepository.findAll().stream().filter(container -> !container.getShippingLine().equals("HLC") && !container.getShippingLine().equals("ARK")
        && !container.getShippingLine().equals("YML") && !container.getShippingLine().equals("ONE")).collect(Collectors.toList());
    }

    public List<Container> getContainersByShippingLineAndFullOrEmptyAndInvoiceCategory(String shippingLine, String fullOrEmpty, String invoiceCategroy,boolean reef) {
        return containerRepository.findByShippingLineAndFullOrEmptyAndInvoiceCategoryAndReef(shippingLine, fullOrEmpty, invoiceCategroy,reef);
    }

    public List<Container> getContainersByShippingLineAndFullOrEmpty(String shippingLine) {
        return containerRepository.findByShippingLineAndFullOrEmpty(shippingLine, "Full");
    }

    public List<Container> getContainerByShippingLineAndInvoiceCategory(String shippingLine,String invoiceCategroy) {
        return containerRepository.findbyShippingLineAndInvoiceCategory(shippingLine,invoiceCategroy);
    }


    public Container getByContainerNumber(String containerNumber) {
        return containerRepository.findByContainerNumber(containerNumber).orElseThrow(() -> new NotFoundException("Container not found with number: " + containerNumber));
    }

    public List<Container> getByShippingLine(String shippingLine) {
        return containerRepository.findByShippingLine(shippingLine);
    }

    public void deleteAllContainers() {
        containerRepository.deleteAll();
    }

    public boolean containersExist() {
        return containerRepository.findAll().size() != 0;
    }



}
