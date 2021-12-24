package com.peopleapps.controller;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.balanceSheet.AdmBalanceSheetDto;
import com.peopleapps.dto.credit.*;
import com.peopleapps.dto.creditCalculator.AdmCalculationDto;
import com.peopleapps.dto.creditCalculator.AdmCCalulatorDto;
import com.peopleapps.dto.creditIncomeExpense.AdmCreditIncomeExpenseDto;
import com.peopleapps.dto.inputs.partner.AdmPartnerDto;
import com.peopleapps.dto.reference.AdmReferenceDto;
import com.peopleapps.dto.balanceSheet.AdmBalanceSheetDto;
import com.peopleapps.model.AdmAddress;
import com.peopleapps.model.AdmGuarantee;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.model.AdmPhone;
import com.peopleapps.repository.*;
import com.peopleapps.dto.person.AdmPersonDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.format.DateTimeFormatter;

import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import com.peopleapps.util.PathDocsCredit;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.List;


@Path("credit/v1/")
@RequestScoped
public class AdmDocCreditRequestController {
    @Inject
    ResponseJson responseJson;

    @Inject
    AdmReferenceRepository admReferenceRepository;

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    AdmRequestCreditRepository admRequestCreditRepository;

    @Inject
    AdmGuaranteeRepository admGuaranteeRepository;

    @Inject
    AdmCreditIncomeExpenseRepository admCreditIncomeExpenseRepository;

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmBalanceSheetRepository admBalanceSheetRepository;

    @Inject
    AdmDebtAcknowledgementsDocReposotory admDebtAcknowledgementsDocReposotory;

    @Inject
    Logger logger;

    @GET
    @Path("files/path_doc")
    public Response getPathDocumnetsCredit(){
        try{
            List<AdmPathDocsCredit> listPath = PathDocsCredit.getPaths();
            return Response.status(Response.Status.OK).entity(listPath).build();
        }catch (Exception ex){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/solicitud_credito")
    public Response getDocSolicitudCredito(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            logger.warn("Destini " + infoDoc.get().getDestiny());
            if (infoDoc.isPresent()) {

                List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(infoDoc.get().creditId, 0L);

                //Conyuge
                List<AdmPartnerDto> partnerList = admPartnerRepository.getAllPartners(
                        infoDoc.get().getPerson_id(), infoDoc.get().getAssociateKey(), 0L, Boolean.TRUE);

                //Fiadores
                //List<AdmPartnerDto> partnerCreditList = admPartnerRepository.getPartnersByCreditKey(creditKey, Boolean.TRUE, null);
                //Referencias
                List<AdmReferenceDto> admReferenceList = admReferenceRepository.getReferencesByCredit(
                        creditKey, Boolean.TRUE, null);
                if (admReferenceList == null){
                    return this.ownRequestResponse("Informacion de referencias no encontrada", Response.Status.NOT_FOUND);
                }

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.SOLICITUD_CREDITO)
                );

                //change text inside each paragrap

                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {
                        int index = 0;
                        for (XWPFRun run: runs){

                            String document_text  = run.getText(0);

                            //lugar
                            if (document_text.contains("place_request")) {
                                document_text = document_text.replace("place_request",  "Huehuetenango");
                                run.setText(document_text,0);
                            }

                            //date
                            if (document_text.contains("date_request")) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                                document_text = document_text.replace("date_request",  dtf.format(LocalDateTime.now()));
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  infoDoc.get().getInternal_code());
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("amount")) {
                                document_text = document_text.replace("amount",  infoDoc.get().getAmount().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_period")) {
                                document_text = document_text.replace("no_period",  infoDoc.get().getNo_payments().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("credit_destination")) {
                                document_text = document_text.replace("credit_destination",  infoDoc.get().getDestiny());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("type_p")) {
                                String tipoPrendario= infoDoc.get().getFiduciary()?  "PRENDARIO" : "FIDUCIARIO";
                                document_text = document_text.replace("type_p",  tipoPrendario.toUpperCase());
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("table_position")) {
                                runs.get(index).setText(document_text.replace("table_position",  ""), 0);
                                XmlCursor cursor = parrafo.getCTP().newCursor();
                                //  Insert the table at the specified cursor position
                                XWPFTable tab = doc.insertNewTbl(cursor);
                                //tab.setCellMargins(20, 30, 20, 30);

                                tab.setWidth(-1);

                                XWPFTableRow row = tab.getRow(0); // First row
                                centerTextBold(row.getCell(0), "Nombre completo", true);
                                centerTextBold(row.addNewTableCell(), "Número", true);

                                for (AdmReferenceDto item : admReferenceList ) {
                                    row = tab.createRow(); // Second Row
                                    centerTextBold(row.getCell(0), item.getPerson().getNameComplete(), false);
                                    centerTextBold(row.getCell(1), item.getPhone().getPhone().toString(), false);

                                }
                            }

                            if (document_text.contains("associate_no")) {
                                document_text = document_text.replace("associate_no",  infoDoc.get().getNo_associate().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("associate_name")) {
                                document_text = document_text.replace("associate_name",  infoDoc.get().getName_complete());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("update_date")) {
                                document_text = document_text.replace("update_date",  "06/11/2021");
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("info_inmueble")) {
                                if (admGuaranteeList.size()!=0) {
                                    document_text = document_text.replace("info_inmueble", this.infoInmueble(admGuaranteeList.get(0)));
                                    run.setText(document_text, 0);
                                }else{
                                    document_text = document_text.replace("info_inmueble", "INFORMACION DEL LA GARANTIA AUN NO INGRESADA");
                                    run.setText(document_text, 0);
                                }
                            }

                            index++;
                        }
                    }
                }

                List<XWPFTable> tables = doc.getTables();
                for (XWPFTable table: tables) {
                    for  (XWPFTableRow row: table.getRows()) {
                        List<XWPFTableCell> cells =  row.getTableCells();
                        int col = 0;

                        for (XWPFTableCell cell: cells) {
                            XWPFParagraph parrafo  = cell.getParagraphs().get(0);
                            List<XWPFRun> runs =  parrafo.getRuns();

                            for (XWPFRun run: runs) {
                                String text = run.getText(0) ;

                                //replaces
                                if (text.contains("associate_name")) {
                                    text = text.replace("associate_name", infoDoc.get().getName_complete());
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_cui")) {
                                    text = text.replace("associate_cui", infoDoc.get().getCui().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_age")) {
                                    LocalDate birthdate = infoDoc.get().getBirthday();
                                    int edad = this.calculateAge(birthdate);

                                    text = text.replace("associate_age",  edad+"");
                                    run.setText(text,0);
                                }
                                if (text.contains("m_s")) {
                                    text = text.replace("m_s", infoDoc.get().getMarital_status());
                                    run.setText(text,0);
                                }
                                if (text.contains("a_nit")) {
                                    text = text.replace("a_nit", infoDoc.get().getNit());
                                    run.setText(text,0);
                                }
                                if (text.contains("partner_name")) {
                                    if (partnerList.size() != 0) {
                                        text = text.replace("partner_name", partnerList.get(0).getPerson().getNameComplete());
                                        run.setText(text, 0);
                                    }else {
                                        text = text.replace("partner_name", "S/D");
                                        run.setText(text, 0);
                                    }
                                }
                                if (text.contains("partner_cui")) {
                                    text = text.replace("partner_cui", "S/D");
                                    run.setText(text,0);
                                }
                                if (text.contains("no_boys")) {
                                    if (partnerList.size() != 0) {
                                        text = text.replace("no_boys", partnerList.get(0).getNoBoys().toString());
                                        run.setText(text, 0);
                                    }else {
                                        text = text.replace("no_boys", "S/D");
                                        run.setText(text, 0);
                                    }

                                }
                                if (text.contains("no_girls")) {
                                    if (partnerList.size() != 0) {
                                        text = text.replace("no_girls", partnerList.get(0).getNoGirls().toString());
                                        run.setText(text, 0);
                                    }
                                    else {
                                        text = text.replace("no_girls", "S/D");
                                        run.setText(text, 0);
                                    }
                                }
                                if (text.contains("p_phone")) {
                                    text = text.replace("p_phone", infoDoc.get().getPhone().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("address_full")) {
                                    text = text.replace("address_full", infoDoc.get().getAddress());
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_city")) {
                                    text = text.replace("associate_city", infoDoc.get().getCity());
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_state")) {
                                    text = text.replace("associate_state", infoDoc.get().getStatedp());
                                    run.setText(text,0);
                                }
                                if (text.contains("s_phone")) {
                                    text = text.replace("s_phone", "No tiene");
                                    run.setText(text,0);
                                }
                                if (text.contains("resi_enc")) {
                                    String tipoCasa= infoDoc.get().getOwn_house()?  "PROPIA" : "ALQUILADA";
                                    text = text.replace("resi_enc", tipoCasa);
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_email")) {
                                    text = text.replace("associate_email", infoDoc.get().getEmail());
                                    run.setText(text,0);
                                }

                                /*Inmueble
                                if (text.contains("own_name")) {
                                    text = text.replace("own_name", admGuaranteeList.get(0).getOwner());
                                    run.setText(text,0);
                                }
                                if (text.contains("property_caserio")) {
                                    text = text.replace("property_caserio", admGuaranteeList.get(0).getAddress().getAddressLine());
                                    run.setText(text,0);
                                }
                                if (text.contains("property_city")) {
                                    text = text.replace("property_city", admGuaranteeList.get(0).getAddress().getCity().getDescription());
                                    run.setText(text,0);
                                }
                                if (text.contains("n_f")) {
                                    text = text.replace("n_f", admGuaranteeList.get(0).getAddress().getNoFarm().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_invoice")) {
                                    text = text.replace("no_invoice", admGuaranteeList.get(0).getAddress().getNoFolder().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_book")) {
                                    text = text.replace("no_book", "Principal 7");
                                    run.setText(text,0);
                                }
                                if (text.contains("property_state")) {
                                    text = text.replace("property_state", admGuaranteeList.get(0).getAddress().getState().getDescription());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_tep")) {
                                    text = text.replace("no_tep", admGuaranteeList.get(0).getAddress().getNoPublic());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_er")) {
                                    text = text.replace("no_er", admGuaranteeList.get(0).getAddress().getExtension().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("f_d")) {
                                    text = text.replace("f_d", admGuaranteeList.get(0).getAnnotation());
                                    run.setText(text,0);
                                }*/
                            }
                            col++;
                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/solicitud_credito/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                //String outputPath = directory_associate_cui + "/" + infoDoc.get().getPersonKey().toString() + "-Solicitud_Credito.docx";
                String outputPath = directory_associate_cui + "/" + infoDoc.get().getNo_associate().toString() + "-Solicitud_Credito.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                //response.header("Content-Disposition", "attachment; filename =\"" + associate.getPersonKey().toString() + "-Solicitud_Credito.docx\"");
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getNo_associate().toString() + "-Solicitud_Credito.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }
    //Usado para crear una tabla de 2 columnnas
    private void centerTextBold(XWPFTableCell cell, String text, Boolean bold) {
        XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        //Quita el espacio despues del parafo
        tempParagraph.setSpacingAfter(0);
        //Ancho de cada celda
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5000));
        XWPFRun tempRun = tempParagraph.createRun();
        tempRun.setText(text);
        tempRun.setBold(bold);
    }

    public int calculateAge(LocalDate birthDate) {
        LocalDate currentDate =LocalDate.now();

        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    //Documento de expediente de credito
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/expediente_credito")
    public Response getDocExpedienteCredito(@PathParam("key") UUID creditKey){
        try{

            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {
                logger.warn("Person id " + infoDoc.get().getPerson_id());

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.EXPENDIENTE_CREDITO)
                );

                //change text inside each paragrap
                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {

                        for (XWPFRun run: runs){
                            String document_text  = run.getText(0);

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  infoDoc.get().getInternal_code());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("associate_name")) {
                                document_text = document_text.replace("associate_name",  infoDoc.get().getName_complete());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_associate")) {
                                document_text = document_text.replace("no_associate",  infoDoc.get().getNo_associate().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("place_request")) {
                                document_text = document_text.replace("place_request",  "Huehuetenango");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("date_request")) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                                document_text = document_text.replace("date_request",  dtf.format(LocalDateTime.now()));
                                run.setText(document_text,0);
                            }

                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/expediente_credito/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-Expediente_Credito.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-Expediente_Credito.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/ingresos_egresos_credito")
    public Response getDocIngresosEgresos(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {

                AdmCreditIncomeExpenseDto admIncome = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                        infoDoc.get().getCreditId(), CsnConstants.INCOME_TYPE, 0L, Boolean.TRUE);

                AdmCreditIncomeExpenseDto admExpenses = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                        infoDoc.get().getCreditId(), CsnConstants.EXPENSE_TYPE , 0L, Boolean.TRUE);
                BigDecimal diferencia = (admIncome.getTotalByYear()).subtract(admExpenses.getTotalByYear()) ;

                if (admIncome == null || admExpenses == null){
                    return this.ownRequestResponse("Income Expenses no found", Response.Status.NOT_FOUND);
                }

                Optional<AdmCCalulatorDto> infoCalculator = this.admCreditCalculatorRepository.getInfoCalulatorDto(
                        infoDoc.get().getCalculator_id());

                if (infoCalculator.isPresent()){
                    logger.warn("Credit amoun " + infoCalculator.get().getCredit());

                    Double monto = infoCalculator.get().getCredit() * (infoCalculator.get().getInteres()/100);
                    Double diferenciaNeta = diferencia.doubleValue() - monto;
                    Double rentabilidad = diferenciaNeta / admExpenses.getTotalByYear().doubleValue();
                    XWPFDocument doc = new XWPFDocument(
                            OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.INGRESOS_EGRESOS_PLANTILLA));

                    //change text inside each paragrap
                    for (XWPFParagraph parrafo: doc.getParagraphs()){
                        List<XWPFRun> runs = parrafo.getRuns();
                        if (runs != null) {
                            int index = 0;
                            for (XWPFRun run: runs){
                                String document_text  = run.getText(0);

                                if (document_text.contains("table_position")) {
                                    runs.get(index).setText(document_text.replace("table_position",  ""), 0);
                                    XmlCursor cursor = parrafo.getCTP().newCursor();
                                    //  Insert the table at the specified cursor position
                                    XWPFTable tab = doc.insertNewTbl(cursor);
                                    tab.setWidth(-1);

                                    XWPFTableRow row = tab.getRow(0); // First row
                                    centerTextBold2(row.getCell(0), "INGRESOS", true);
                                    centerTextBold2(row.addNewTableCell(), "MENSUALES EN Q.", true);
                                    centerTextBold2(row.addNewTableCell(), "ANUALES EN Q.", true);

                                    for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount item : admIncome.getAccounts() ) {
                                        row = tab.createRow(); // Second Row
                                        centerTextBold(row.getCell(0), item.getAccount().getDescription(), false);
                                        centerTextBold(row.getCell(1), item.getAmount().toString(), false);
                                        centerTextBold(row.getCell(2), (item.getAmount().multiply(new BigDecimal("12"))).toString(), false);
                                    }
                                    row = tab.createRow(); // Total Row
                                    centerTextBold(row.getCell(0), "TOTAL DE INGRESOS", true);
                                    centerTextBold(row.getCell(1), admIncome.getTotalByMonth().toString(), true);
                                    centerTextBold(row.getCell(2), admIncome.getTotalByYear().toString(), true);
                                }
                                if (document_text.contains("toble_position")) {
                                    runs.get(index).setText(document_text.replace("toble_position",  ""), 0);
                                    XmlCursor cursor = parrafo.getCTP().newCursor();
                                    //  Insert the table at the specified cursor position
                                    XWPFTable tab = doc.insertNewTbl(cursor);
                                    tab.setWidth(-1);

                                    XWPFTableRow row = tab.getRow(0); // First row
                                    centerTextBold2(row.getCell(0), "EGRESOS", true);
                                    centerTextBold2(row.addNewTableCell(), "MENSUALES EN Q.", true);
                                    centerTextBold2(row.addNewTableCell(), "ANUALES EN Q.", true);

                                    for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount item : admExpenses.getAccounts() ) {
                                        row = tab.createRow(); // Second Row
                                        centerTextBold(row.getCell(0), item.getAccount().getDescription(), false);
                                        centerTextBold(row.getCell(1), item.getAmount().toString(), false);
                                        centerTextBold(row.getCell(2), (item.getAmount().multiply(new BigDecimal("12"))).toString(), false);
                                    }
                                    row = tab.createRow(); // Total Row
                                    centerTextBold(row.getCell(0), "TOTAL DE EGRESOS", true);
                                    centerTextBold(row.getCell(1), admExpenses.getTotalByMonth().toString(), true);
                                    centerTextBold(row.getCell(2), admExpenses.getTotalByYear().toString(), true);
                                }
                                if (document_text.contains("associate_name")) {
                                    document_text = document_text.replace("associate_name",  infoDoc.get().getName_complete());
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("partner_name")) {
                                    document_text = document_text.replace("partner_name",  "Mariana Perez");
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("place_request")) {
                                    document_text = document_text.replace("place_request",  "Huehuetenango");
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("date_request")) {
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                                    document_text = document_text.replace("date_request",  dtf.format(LocalDateTime.now()));
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("address_line")) {
                                    document_text = document_text.replace("address_line",  infoDoc.get().getAddress());
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("city_req")) {
                                    document_text = document_text.replace("city_req",  infoDoc.get().getCity());
                                    run.setText(document_text,0);
                                }
                                if (document_text.contains("state_req")) {
                                    document_text = document_text.replace("state_req",  infoDoc.get().getStatedp());
                                    run.setText(document_text,0);
                                }
                                index ++;
                            }
                        }
                    }

                    List<XWPFTable> tables = doc.getTables();
                    for (XWPFTable table: tables) {
                        for  (XWPFTableRow row: table.getRows()) {
                            List<XWPFTableCell> cells =  row.getTableCells();
                            for (XWPFTableCell cell: cells) {
                                XWPFParagraph parrafo  = cell.getParagraphs().get(0);
                                List<XWPFRun> runs =  parrafo.getRuns();

                                for (XWPFRun run: runs) {
                                    String text = run.getText(0) ;

                                    //replaces
                                    if (text.contains("dif_ei")) {
                                        text = text.replace("dif_ei", diferencia.toString());
                                        run.setText(text,0);
                                    }
                                    if (text.contains("mot_pre")) {

                                        text = text.replace("mot_pre", monto.toString());
                                        run.setText(text,0);
                                    }
                                    if (text.contains("dif_net")) {
                                        text = text.replace("dif_net", diferenciaNeta.toString());
                                        run.setText(text,0);
                                    }
                                    if (text.contains("rent_b")) {
                                        text = text.replace("rent_b", rentabilidad.toString() +"%");
                                        run.setText(text,0);
                                    }
                                }
                            }
                        }
                    }

                    File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                    if (!rootDirectory.exists()) {
                        rootDirectory.mkdir();
                    }

                    File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/ingresos-egresos/");
                    if (!directory_credit_request.exists()) {
                        directory_credit_request.mkdir();
                    }

                    File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                    if (!directory_associate_cui.exists()) {
                        directory_associate_cui.mkdir();
                    }

                    String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-Ingresos-Egresos.docx";
                    doc.write(new FileOutputStream(outputPath));

                    File file = new File(outputPath);
                    Response.ResponseBuilder response = Response.ok((Object) file);
                    response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-Ingresos-Egresos.docx\"");

                    return response.build();


                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                            "FAIL",
                            "Credit calculator not found",
                            0L,
                            "Credit calculator not found",
                            "0"
                    )).build();
                }

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    //Usado para crear una tabla de 3 columnas
    private void centerTextBold2(XWPFTableCell cell, String text, Boolean bold) {
        XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        //Quita el espacio despues del parafo
        tempParagraph.setSpacingAfter(0);
        //Ancho de cada celda
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3000));
        XWPFRun tempRun = tempParagraph.createRun();
        tempRun.setText(text);
        tempRun.setBold(bold);
    }


    private Response ownRequestResponse(String message, Response.Status resp) {
        return CsnFunctions.setResponse(0L, resp, message);
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/estado_patrimonial")
    public Response getDocEstadoPatrimonail(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {
                //Garantias
                List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(infoDoc.get().creditId, 0L);
                //Conyuge
                List<AdmPartnerDto> partnerList = admPartnerRepository.getAllPartners(
                        infoDoc.get().getPerson_id(), infoDoc.get().getAssociateKey(), 0L, Boolean.TRUE);

                //activos
                AdmBalanceSheetDto admActivos = admBalanceSheetRepository.getBalanceSheetByCredit(
                        infoDoc.get().getCreditId(), CsnConstants.ASSET_TYPE, 0L, Boolean.TRUE);
                //pasivos
                AdmBalanceSheetDto admPasivos = admBalanceSheetRepository.getBalanceSheetByCredit(
                        infoDoc.get().getCreditId(), CsnConstants.LIABILITY_TYPE, 0L, Boolean.TRUE);

                //Ingresos y egresos
                AdmCreditIncomeExpenseDto admIncome = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                        infoDoc.get().getCreditId(), CsnConstants.INCOME_TYPE, 0L, Boolean.TRUE);

                AdmCreditIncomeExpenseDto admExpenses = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                        infoDoc.get().getCreditId(), CsnConstants.EXPENSE_TYPE , 0L, Boolean.TRUE);
                BigDecimal diferencia = (admIncome.getTotalByYear()).subtract(admExpenses.getTotalByYear()) ;


                //Calculos para datos de ingresos y egresos
                Double monto = infoDoc.get().getAmount() * (infoDoc.get().getInterest_rate()/100);
                Double diferenciaNeta = diferencia.doubleValue() - monto;
                Double rentabilidad = diferenciaNeta / admExpenses.getTotalByYear().doubleValue();

                LocalDate birthdate = infoDoc.get().getBirthday();
                int edad = this.calculateAge(birthdate);

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.ESTADO_PATRIMONIAL_PLANTILLA));

                //change text inside each paragrap
                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {
                        int index=0;
                        for (XWPFRun run: runs){
                            String document_text  = run.getText(0);

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  infoDoc.get().getInternal_code());
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("date_request")) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                                document_text = document_text.replace("date_request",  dtf.format(LocalDateTime.now()));
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("amount")) {
                                document_text = document_text.replace("amount",  "80000");
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("info_associate")) {
                                if(partnerList.size() !=0) {
                                    document_text = document_text.replace("info_associate", this.infoAssociateG(infoDoc.get(), edad, partnerList.get(0)));
                                    run.setText(document_text, 0);
                                }else{
                                    document_text = document_text.replace("info_associate", this.infoAssociateG2(infoDoc.get(), edad));
                                    run.setText(document_text, 0);
                                }
                            }
                            if (document_text.contains("info_activo")) {
                                runs.get(index).setText(document_text.replace("info_activo",  ""), 0);
                                XmlCursor cursor = parrafo.getCTP().newCursor();
                                this.createTbl2C(cursor, doc,"Activo", admActivos);

                            }
                            if (document_text.contains("info_pasivo")) {
                                runs.get(index).setText(document_text.replace("info_pasivo",  ""), 0);
                                XmlCursor cursor = parrafo.getCTP().newCursor();
                                this.createTbl2C(cursor, doc,"Pasivo", admPasivos);
                            }
                            if (document_text.contains("info_inmueble")) {
                                if(admGuaranteeList.size()!=0){
                                    document_text = document_text.replace("info_inmueble",  this.infoInmueble(admGuaranteeList.get(0)));
                                    run.setText(document_text,0);
                                }else{
                                    document_text = document_text.replace("info_inmueble",  "INFORMACION DE LA GARANTIA AUN NO INGRESADA");
                                    run.setText(document_text,0);
                                }

                            }

                            //datos de ingresos y egresos
                            if (document_text.contains("table_position")) {
                                runs.get(index).setText(document_text.replace("table_position",  ""), 0);
                                XmlCursor cursor = parrafo.getCTP().newCursor();
                                //  Insert the table at the specified cursor position
                                XWPFTable tab = doc.insertNewTbl(cursor);
                                tab.setWidth(-1);

                                XWPFTableRow row = tab.getRow(0); // First row
                                centerTextBold2(row.getCell(0), "INGRESOS", true);
                                centerTextBold2(row.addNewTableCell(), "MENSUALES EN Q.", true);
                                centerTextBold2(row.addNewTableCell(), "ANUALES EN Q.", true);

                                for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount item : admIncome.getAccounts() ) {
                                    row = tab.createRow(); // Second Row
                                    centerTextBold(row.getCell(0), item.getAccount().getDescription(), false);
                                    centerTextBold(row.getCell(1), item.getAmount().toString(), false);
                                    centerTextBold(row.getCell(2), (item.getAmount().multiply(new BigDecimal("12"))).toString(), false);
                                }
                                row = tab.createRow(); // Total Row
                                centerTextBold(row.getCell(0), "TOTAL DE INGRESOS", true);
                                centerTextBold(row.getCell(1), admIncome.getTotalByMonth().toString(), true);
                                centerTextBold(row.getCell(2), admIncome.getTotalByYear().toString(), true);
                            }
                            if (document_text.contains("toble_position")) {
                                runs.get(index).setText(document_text.replace("toble_position",  ""), 0);
                                XmlCursor cursor = parrafo.getCTP().newCursor();
                                //  Insert the table at the specified cursor position
                                XWPFTable tab = doc.insertNewTbl(cursor);
                                tab.setWidth(-1);

                                XWPFTableRow row = tab.getRow(0); // First row
                                centerTextBold2(row.getCell(0), "EGRESOS", true);
                                centerTextBold2(row.addNewTableCell(), "MENSUALES EN Q.", true);
                                centerTextBold2(row.addNewTableCell(), "ANUALES EN Q.", true);

                                for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount item : admExpenses.getAccounts() ) {
                                    row = tab.createRow(); // Second Row
                                    centerTextBold(row.getCell(0), item.getAccount().getDescription(), false);
                                    centerTextBold(row.getCell(1), item.getAmount().toString(), false);
                                    centerTextBold(row.getCell(2), (item.getAmount().multiply(new BigDecimal("12"))).toString(), false);
                                }
                                row = tab.createRow(); // Total Row
                                centerTextBold(row.getCell(0), "TOTAL DE EGRESOS", true);
                                centerTextBold(row.getCell(1), admExpenses.getTotalByMonth().toString(), true);
                                centerTextBold(row.getCell(2), admExpenses.getTotalByYear().toString(), true);
                            }

                            if (document_text.contains("associate_name")) {
                                document_text = document_text.replace("associate_name",  infoDoc.get().getName_complete());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("partner_name")) {
                                document_text = document_text.replace("partner_name",  "Mariana Perez");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("place_request")) {
                                document_text = document_text.replace("place_request",  "Huehuetenango");
                                run.setText(document_text,0);
                            }

                            if (document_text.contains("address_line")) {
                                document_text = document_text.replace("address_line",  infoDoc.get().getAddress());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("city_req")) {
                                document_text = document_text.replace("city_req",  infoDoc.get().getCity());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("state_req")) {
                                document_text = document_text.replace("state_req",  infoDoc.get().getStatedp());
                                run.setText(document_text,0);
                            }

                            index++;
                        }
                    }
                }

                List<XWPFTable> tables = doc.getTables();
                for (XWPFTable table: tables) {
                    for  (XWPFTableRow row: table.getRows()) {
                        List<XWPFTableCell> cells =  row.getTableCells();
                        for (XWPFTableCell cell: cells) {
                            XWPFParagraph parrafo  = cell.getParagraphs().get(0);
                            List<XWPFRun> runs =  parrafo.getRuns();

                            for (XWPFRun run: runs) {
                                String text = run.getText(0) ;

                                //replaces
                                if (text.contains("dif_ei")) {
                                    text = text.replace("dif_ei", diferencia.toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("mot_pre")) {

                                    text = text.replace("mot_pre", monto.toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("dif_net")) {
                                    text = text.replace("dif_net", diferenciaNeta.toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("rent_b")) {
                                    text = text.replace("rent_b", rentabilidad.toString() +"%");
                                    run.setText(text,0);
                                }
                            }
                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/estado_patrimonial/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-Estado-Patrimonial.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-Estado-Patrimonial.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }
    //Create table for 2 columns for Estado patrimonial
    private void createTbl2C(XmlCursor cursor, XWPFDocument doc, String column1, AdmBalanceSheetDto data) {
        //  Insert the table at the specified cursor position
        XWPFTable tab = doc.insertNewTbl(cursor);
        tab.setWidth(-1);

        XWPFTableRow row = tab.getRow(0); // First row
        centerTextBold2(row.getCell(0), column1, true);
        centerTextBold2(row.addNewTableCell(), "VALOR EN Q.", true);


        for (AdmBalanceSheetDto.AdmBalanceSheetAccount item : data.getAccounts() ) {
            row = tab.createRow(); // Second Row
            centerTextBold(row.getCell(0), item.getAccount().getDescription(), false);
            centerTextBold(row.getCell(1), item.getAmount().toString(), false);

        }
        row = tab.createRow(); // Total Row
        centerTextBold(row.getCell(0), "TOTAL", true);
        centerTextBold(row.getCell(1), data.getTotal().toString(), true);
    }

    //Documento de resolucion de comite de credito
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/resolucion_cc")
    public Response getDocResolucionCC(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {
                List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(infoDoc.get().creditId, 0L);
                //Conyuge
                List<AdmPartnerDto> partnerList = admPartnerRepository.getAllPartners(
                        infoDoc.get().getPerson_id(), infoDoc.get().getAssociateKey(), 0L, Boolean.TRUE);

                //edad
                LocalDate birthdate = infoDoc.get().getBirthday();
                int edad = this.calculateAge(birthdate);

                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int year  = localDate.getYear();
                int month = localDate.getMonthValue();
                int day   = localDate.getDayOfMonth();

                String infoBienInmueble="";
                String infoAssociate="";
                if(admGuaranteeList.size()!=0){infoBienInmueble = this.infoInmueble(admGuaranteeList.get(0));
                }else{infoBienInmueble="INFORMACION DE LA GARANTIA AUN NO INGRESADA";}

                if(partnerList.size()!=0){infoAssociate = this.infoAssociate(infoDoc.get(), edad, partnerList.get(0));
                }else{infoAssociate = this.infoAssociate(infoDoc.get(), edad);}


                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.RESOLUCION_COMITE_CREDITO)
                );

                //change text inside each paragrap
                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {
                        int index = 0;
                        for (XWPFRun run: runs){

                            String document_text  = run.getText(0);

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  infoDoc.get().internal_code);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("info_associate")) {
                                document_text = document_text.replace("info_associate",  infoAssociate);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("info_inmueble")) {
                                document_text = document_text.replace("info_inmueble",  infoBienInmueble);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("amount")) {
                                document_text = document_text.replace("amount",  infoDoc.get().getAmount().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_per")) {
                                document_text = document_text.replace("no_per",  infoDoc.get().getNo_payments().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_interes")) {
                                document_text = document_text.replace("no_interes",  infoDoc.get().getInterest_rate().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("name_associate")) {
                                document_text = document_text.replace("name_associate",  infoDoc.get().getName_complete());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_day")) {
                                document_text = document_text.replace("no_day",  day+"");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_mon")) {
                                document_text = document_text.replace("no_mon",  month+"");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_year")) {
                                document_text = document_text.replace("no_year",  year+"");
                                run.setText(document_text,0);
                            }
                            index++;
                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/resolucion_comite_credito/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-Resolucion_Comite_Credito.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-Resolucion_Comite_Credito.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    //Documento de resolucion de consejo administrativo
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/resolucion_consejo_adm")
    public Response getDocResolucionConsejoAdministrativo(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {
                List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(infoDoc.get().creditId, 0L);
                //Conyuge
                List<AdmPartnerDto> partnerList = admPartnerRepository.getAllPartners(
                        infoDoc.get().getPerson_id(), infoDoc.get().getAssociateKey(), 0L, Boolean.TRUE);

                //edad
                LocalDate birthdate = infoDoc.get().getBirthday();
                int edad = this.calculateAge(birthdate);

                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int year  = localDate.getYear();
                int month = localDate.getMonthValue();
                int day   = localDate.getDayOfMonth();

                String infoBienInmueble="";
                String infoAssociate="";
                if(admGuaranteeList.size()!=0){infoBienInmueble = this.infoInmueble(admGuaranteeList.get(0));
                }else{infoBienInmueble="INFORMACION DE LA GARANTIA AUN NO INGRESADA";}

                if(partnerList.size()!=0){infoAssociate = this.infoAssociate(infoDoc.get(), edad, partnerList.get(0));
                }else{infoAssociate = this.infoAssociate(infoDoc.get(), edad);}

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.RESOLUCION_RL_PLANTILLA)
                );

                //change text inside each paragrap
                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {
                        int index = 0;
                        for (XWPFRun run: runs){

                            String document_text  = run.getText(0);

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  infoDoc.get().internal_code);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("info_associate")) {
                                document_text = document_text.replace("info_associate",  infoAssociate);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("info_inmueble")) {
                                document_text = document_text.replace("info_inmueble",  infoBienInmueble);
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("amount")) {
                                document_text = document_text.replace("amount",  infoDoc.get().getAmount().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_per")) {
                                document_text = document_text.replace("no_per",  infoDoc.get().getNo_payments().toString());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_interes")) {
                                document_text = document_text.replace("no_interes",  infoDoc.get().getInterest_rate().toString()+"%");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("name_associate")) {
                                document_text = document_text.replace("name_associate",  infoDoc.get().getName_complete());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_day")) {
                                document_text = document_text.replace("no_day",  day+"");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_mon")) {
                                document_text = document_text.replace("no_mon",  month+"");
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("no_year")) {
                                document_text = document_text.replace("no_year",  year+"");
                                run.setText(document_text,0);
                            }
                            index++;
                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/resolucion_consejo_administrativo/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-Resolucion_Consejo_Administrativo.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-Resolucion_Consejo_Administrativo.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    //Obtiene informacion del associado y detalles del partner, para uso general en varias plantillas
    public String infoAssociateG(AdmCreditDocDto infoA, int edad, AdmPartnerDto partner ){
        String infoAssociate = "NOMBRE DEL SOLICITANTE "+infoA.getName_complete()+ ", NO. NIT "+ infoA.getNit()+", EDAD "+edad+" AÑOS, " +
                "ESTADO CIVIL "+infoA.getMarital_status()+ ", NO. DE DPI "+infoA.getCui()+
                ", NOMBRE DE LA ESPOSA O FIADOR "+partner.getPerson().getNameComplete()+", " +
                "NO. DE DPI "+partner.getPerson().getCui()+", HIJOS "+partner.getNoBoys()+", HIJAS "+partner.getNoGirls()+
                ", TELEFONO "+infoA.getPhone()+", DIRECCIÓN "+infoA.getAddress()+", MUNICIPIO "+infoA.getCity()+
                ", DEPARTAMENTO "+infoA.getStatedp()+
                ", DESTINO "+infoA.getDestiny();
        return infoAssociate.toUpperCase();
    }
    //Obtiene informacion del associado y partner NULL, para uso general en varias plantillas
    public String infoAssociateG2(AdmCreditDocDto infoA, int edad){
        String infoAssociate = "NOMBRE DEL SOLICITANTE "+infoA.getName_complete()+ ", NO. NIT "+ infoA.getNit()+", EDAD "+edad+" AÑOS, " +
                "ESTADO CIVIL "+infoA.getMarital_status()+ ", NO. DE DPI "+infoA.getCui()+
                ", NOMBRE DE LA ESPOSA O FIADOR S/D, " +
                "NO. DE DPI S/D, HIJOS S/D, HIJAS S/D"+
                ", TELEFONO "+infoA.getPhone()+", DIRECCIÓN "+infoA.getAddress()+", MUNICIPIO "+infoA.getCity()+
                ", DEPARTAMENTO "+infoA.getStatedp()+
                ", DESTINO "+infoA.getDestiny();
        return infoAssociate.toUpperCase();
    }

    //Obtiene informacion del associado y detalles del partner, para uso de ciertas plantillas
    public String infoAssociate(AdmCreditDocDto infoA, int edad, AdmPartnerDto partner ){
        String infoAssociate = "NOMBRE DEL SOLICITANTE "+infoA.getName_complete()+ ", EDAD "+edad+" AÑOS, " +
                "ESTADO CIVIL "+infoA.getMarital_status()+ ", NO. DE DPI "+infoA.getCui()+
                ", NOMBRE DE LA ESPOSA O FIADOR "+partner.getPerson().getNameComplete()+", " +
                "NO. DE DPI "+partner.getPerson().getCui()+", DIRECCIÓN "+infoA.getAddress()+", MUNICIPIO "+infoA.getCity()+
                ", DEPARTAMENTO "+infoA.getStatedp()+
                ", DESTINO "+infoA.getDestiny()+", FINANCIAMIENTO AUTORIZADO "+infoA.getAmount();
        return infoAssociate.toUpperCase();
    }
    ////Obtiene informacion del associado y  partner NULL, para uso de ciertas plantillas
    public String infoAssociate(AdmCreditDocDto infoA, int edad ){
        String infoAssociate = "NOMBRE DEL SOLICITANTE "+infoA.getName_complete()+ ", EDAD "+edad+" AÑOS, " +
                "ESTADO CIVIL "+infoA.getMarital_status()+ ", NO. DE DPI "+infoA.getCui()+
                ", NOMBRE DE LA ESPOSA O FIADOR S/D, " +
                "NO. DE DPI S/D, DIRECCIÓN "+infoA.getAddress()+", MUNICIPIO "+infoA.getCity()+
                ", DEPARTAMENTO "+infoA.getStatedp()+
                ", DESTINO "+infoA.getDestiny()+", FINANCIAMIENTO AUTORIZADO "+infoA.getAmount();
        return infoAssociate.toUpperCase();
    }

    public String infoInmueble(AdmGuarantee guarante){
        String infoBienInmueble= "NOMBRE DEL PROPIETARIO "+guarante.getOwner()+", " +
                "DIRECCION "+guarante.getAddress().getAddressLine()+", "+
                "MUNICIPIO "+guarante.getAddress().getCity().getDescription() +", "+" FINCA NO. "+guarante.getAddress().getNoFarm()+", "+
                "FOLIO "+guarante.getAddress().getNoFolder()+", LIBRO No APLICA"+
                ", DEPARTAMENTO "+guarante.getAddress().getState().getDescription()+", TESTIMONIO ESCRITURA PUBLICA NO. "+guarante.getAddress().getNoPublic()+
                ", EXTENSION REGISTRADA "+guarante.getAddress().getExtension()+", "+guarante.getAnnotation();
        return infoBienInmueble.toUpperCase();
    }

    //Documento de autoavaluo
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/autoavaluo")
    public Response getDocAutoAvaluo(@PathParam("key") UUID creditKey){
        try{
            Optional<AdmCreditDocDto> infoDoc = this.admRequestCreditRepository.getInfoRequestCreditDoc(creditKey);
            if (infoDoc.isPresent()) {
                List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(infoDoc.get().creditId, 0L);

                if (admGuaranteeList.size() ==0){
                    return this.ownRequestResponse("Datos de la garantia no encontrados", Response.Status.NOT_FOUND);
                }

                Double costo_tierra = admGuaranteeList.get(0).getAreaMeters() * admGuaranteeList.get(0).getCostPerSquareMeter();
                Double total_avaluo = costo_tierra+admGuaranteeList.get(0).getValueOfPermanentCrops()+admGuaranteeList.get(0).getValueOfBuildings();

                logger.warn("InfoInmueble " );

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.AUTOAVALUO_PLANTILLA)
                );

                //change text inside each paragrap
                for (XWPFParagraph parrafo: doc.getParagraphs()){
                    List<XWPFRun> runs = parrafo.getRuns();
                    if (runs != null) {
                        int index = 0;
                        for (XWPFRun run: runs){

                            String document_text  = run.getText(0);

                            if (document_text.contains("no_request")) {
                                document_text = document_text.replace("no_request",  "NUMERO: "+infoDoc.get().getInternal_code());
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("date_f")) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                document_text = document_text.replace("date_f",  "FECHA: "+dtf.format(ZonedDateTime.now(ZoneId.of("GMT-6"))));
                                run.setText(document_text,0);
                            }
                            if (document_text.contains("info_avaluo")) {
                                document_text = document_text.replace("info_avaluo",  "hola");
                                run.setText(document_text,0);
                            }


                            index++;
                        }
                    }
                }

                List<XWPFTable> tables = doc.getTables();
                for (XWPFTable table: tables) {
                    for  (XWPFTableRow row: table.getRows()) {
                        List<XWPFTableCell> cells =  row.getTableCells();
                        int col = 0;

                        for (XWPFTableCell cell: cells) {
                            XWPFParagraph parrafo  = cell.getParagraphs().get(0);
                            List<XWPFRun> runs =  parrafo.getRuns();

                            for (XWPFRun run: runs) {
                                String text = run.getText(0) ;

                                //replaces
                                if (text.contains("associate_name")) {
                                    text = text.replace("associate_name", infoDoc.get().getName_complete());
                                    run.setText(text,0);
                                }

                                if (text.contains("address_full")) {
                                    text = text.replace("address_full", infoDoc.get().getAddress());
                                    run.setText(text,0);
                                }
                                if (text.contains("associate_city")) {
                                    text = text.replace("associate_city", infoDoc.get().getCity());
                                    run.setText(text,0);
                                }

                                if (text.contains("name_farm")) {
                                    text = text.replace("name_farm", admGuaranteeList.get(0).getNameFarm());
                                    run.setText(text,0);
                                }

                                if (text.contains("name_ownf")) {
                                    text = text.replace("name_ownf", admGuaranteeList.get(0).getOwner());
                                    run.setText(text,0);
                                }
                                if (text.contains("address_farm")) {
                                    text = text.replace("address_farm", admGuaranteeList.get(0).getAddress().getAddressLine());
                                    run.setText(text,0);
                                }
                                if (text.contains("city_farm")) {
                                    text = text.replace("city_farm", admGuaranteeList.get(0).getAddress().getCity().getDescription());
                                    run.setText(text,0);
                                }

                                if (text.contains("sta_farm")) {
                                    text = text.replace("sta_farm", admGuaranteeList.get(0).getAddress().getState().getDescription());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_farm")) {
                                    text = text.replace("no_farm", admGuaranteeList.get(0).getAddress().getNoFarm().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_fol")) {
                                    text = text.replace("no_fol", admGuaranteeList.get(0).getAddress().getNoFolder().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_book")) {
                                    text = text.replace("no_book", admGuaranteeList.get(0).getAddress().getNoBook());
                                    run.setText(text,0);
                                }

                                if (text.contains("own_far")) {
                                    text = text.replace("own_far", admGuaranteeList.get(0).getAddress().getNoFarm().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("annotation1")) {
                                    text = text.replace("annotation1", admGuaranteeList.get(0).getAnnotation());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_rope")) {
                                    text = text.replace("no_rope", admGuaranteeList.get(0).getNoRope().toString());
                                    run.setText(text,0);
                                }

                                if (text.contains("no_hecta")) {
                                    text = text.replace("no_hecta", admGuaranteeList.get(0).getNoHectares().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("commun_chan")) {
                                    text = text.replace("commun_chan", admGuaranteeList.get(0).getCommunicationRoutes());
                                    run.setText(text,0);
                                }

                                if (text.contains("to_guate")) {
                                    text = text.replace("to_guate", admGuaranteeList.get(0).getCity().toString());
                                    run.setText(text,0);
                                }

                                if (text.contains("to_sta")) {
                                    text = text.replace("to_sta", admGuaranteeList.get(0).getState().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("to_city")) {
                                    text = text.replace("to_city", admGuaranteeList.get(0).getCity().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("north_origin")) {
                                    text = text.replace("north_origin", admGuaranteeList.get(0).getNorthOrigin());
                                    run.setText(text,0);
                                }

                                if (text.contains("ortient_origin")) {
                                    text = text.replace("ortient_origin", admGuaranteeList.get(0).getOrientOrigin());
                                    run.setText(text,0);
                                }
                                if (text.contains("south_origin")) {
                                    text = text.replace("south_origin", admGuaranteeList.get(0).getSouthOrigin());
                                    run.setText(text,0);
                                }
                                if (text.contains("west_origin")) {
                                    text = text.replace("west_origin", admGuaranteeList.get(0).getWestOrigin());
                                    run.setText(text,0);
                                }

                                if (text.contains("no_area")) {
                                    text = text.replace("no_area", admGuaranteeList.get(0).getAreaMeters().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("no_rop")) {
                                    text = text.replace("no_rop", admGuaranteeList.get(0).getNoRope().toString());
                                    run.setText(text,0);
                                }

                                if (text.contains("cost_rope")) {
                                    text = text.replace("cost_rope", admGuaranteeList.get(0).getRopeValue().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("cost_area")) {
                                    text = text.replace("cost_area", admGuaranteeList.get(0).getCostPerSquareMeter().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("height_sea")) {
                                    text = text.replace("height_sea", admGuaranteeList.get(0).getHeightAboveSeaLevel().toString());
                                    run.setText(text,0);
                                }

                                if (text.contains("info_topog")) {
                                    text = text.replace("info_topog", admGuaranteeList.get(0).getTopography());
                                    run.setText(text,0);
                                }
                                if (text.contains("info_hydrography")) {
                                    text = text.replace("info_hydrography", admGuaranteeList.get(0).getHydrography());
                                    run.setText(text,0);
                                }

                                if (text.contains("soil_quality")) {
                                    text = text.replace("soil_quality", admGuaranteeList.get(0).getSoilQuality());
                                    run.setText(text,0);
                                }

                                if (text.contains("plan_cover")) {
                                    text = text.replace("plan_cover", admGuaranteeList.get(0).getPlanCover());
                                    run.setText(text,0);
                                }
                                if (text.contains("cultivate_det")) {
                                    text = text.replace("cultivate_det", admGuaranteeList.get(0).getCultivateDetail());
                                    run.setText(text,0);
                                }

                                if (text.contains("farm_nei")) {
                                    text = text.replace("farm_nei", admGuaranteeList.get(0).getFarmNeighbor());
                                    run.setText(text,0);
                                }
                                if (text.contains("risk_class")) {
                                    text = text.replace("risk_class", admGuaranteeList.get(0).getRiskClassForm());
                                    run.setText(text,0);
                                }

                                if (text.contains("building_details")) {
                                    text = text.replace("building_details", admGuaranteeList.get(0).getBuildingDetail());
                                    run.setText(text,0);
                                }
                                if (text.contains("annotation2")) {
                                    text = text.replace("annotation2", admGuaranteeList.get(0).getAnnotation2());
                                    run.setText(text,0);
                                }

                                if (text.contains("cost_soil")) {
                                    text = text.replace("cost_soil", costo_tierra.toString());
                                    run.setText(text,0);
                                }

                                if (text.contains("value_culti")) {
                                    text = text.replace("value_culti", admGuaranteeList.get(0).getValueOfPermanentCrops().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("value_building")) {
                                    text = text.replace("value_building", admGuaranteeList.get(0).getValueOfBuildings().toString());
                                    run.setText(text,0);
                                }
                                if (text.contains("total_avaluo")) {
                                    text = text.replace("total_avaluo", total_avaluo.toString());
                                    run.setText(text,0);
                                }
                            }
                            col++;
                        }
                    }
                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/auto_avaluo/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + infoDoc.get().getCui() + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + infoDoc.get().getAssociateKey().toString() + "-AutoAvaluo.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + infoDoc.get().getAssociateKey().toString() + "-AutoAvaluo.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    public void infoAutoAvaluo(AdmCreditDocDto infPerson, AdmGuarantee infG , XWPFRun run){
        String infassociate = "NOMBRE DEL SOLICITANTE "+infPerson.getName_complete()+ ", Direccion "+infPerson.getAddress()+
                ", MUNICIPIO "+infPerson.getCity()+", DEPARTAMENTO "+infPerson.getStatedp()+".";
        String infguarantee= "NOMBRE DE LA FINCA "+infG.getNameFarm()+", PROPIETARIO "+infG.getOwner()+
                ", LOCALIZACION "+infG.getAddress().getAddressLine()+ ", MUNICIPIO "+infG.getAddress().getCity().getDescription()+
                ", DEPARTAMENTO "+infG.getAddress().getState().getDescription()+", NO. ESCRITURA "+infG.getAddress().getNoPublic()+
                ", FOLIO "+infG.getAddress().getNoFolder()+", LIBRO "+infG.getAddress().getNoBook()+
                ", DERECHOS DE POSECION"+"0.325"+", "+infG.getAnnotation()+
                ", EXTENSION REGISTRADA "+" "+", HECTAREAS"+".\n";
        run.setText(infassociate+" "+infguarantee);
        run.addBreak();
        run.setText(infguarantee);

                /*"VIAS DE COMUNICACION\n"+infG.getCommunicationRoutes()+".\n"+
                "DISTANCIA EN KILOMETROS"+infG.getToCity()+ " A GUATEMALA "+infG.getCity()+
                " CABECERA DEPTAL "+infG.getState()+ " A LA CABECERA MUNICIPAL. \n"+
                "COLINDANCIAS ACTUALES: \n"+
                "NORTE: "+ infG.getNorthOrigin()+"\n"+
                "ORIENTE: "+infG.getOrientOrigin()+"\n"+
                "SUR: "+ infG.getSouthOrigin()+"\n"+
                "PONIENTE: "+infG.getSouthOrigin()+"\n"+
                "EN TOTAL SON "+infG.getAreaMeters()+" METROS CUADRADOS EQUIVALENTES A: "+""+"CUERDAS.\n"+
                "EL COSTO DE LA CUERDA EN ESTE TERRITORIO ES APROXIMADAMENTE DE: "+"\n"+
                "EL COSTO DEL METRO CUADRADO EN ESTE TERRITORIO ES APROXIMADAMENTE DE: "+ "\n"+
                "ALTURA SOBRE EL NIVEL DEL MAR "+ "METROS. \n"+
                "TOPOGRAFIA \n"+infG.getTopography() +".\n"+
                "CALIDAD DE LOS SUELOS\n"+infG.getSoilQuality()+".\n"+

                "CUBIERTA DIGITAL\n"+infG.getPlanCover()+".\n"+
                "DETALLES Y ESTADO DE LOS CULTIVOS PERMANENTES\n"+infG.getCultivateDetail()+".\n"+
                "CLASE DE EXPLOTACION DE LAS FINCAS VECINAS\n"+infG.getFarmNeighbor()+".\n"+
                "RIESGOS CLASES Y FORMAS\n"+infG.getRiskClassForm()+".\n"+
                "EXTENSION REGABLE\n"+infG.getIrrigationExtension()+".\n"+
                "DETALLES DE LAS CONTRUCCIONES\n"+infG.getBuildingDetail()+".\n"+
                "OBSERVACIONES\n"+infG.getAnnotation()+".\n\n";*/

        //return infassociate+"\t\t"+infguarantee;
    }

    public String infoAvaluo(){

        return "hola";
    }

    //Documento de Reconocimiento de Deuda
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/reconocimiento_deuda")
    public Response getDocReconocimientoDeuda(@PathParam("key") UUID creditKey){
        try{

            Optional<AdmDebtAcknowledgementsDto> infoDoc = this.admDebtAcknowledgementsDocReposotory.getAcknowledgementsDoc(creditKey);
            if (infoDoc.isPresent()) {
                //logger.warn("Person id " + infoDoc.get().getCreditKey());

                XWPFDocument doc = new XWPFDocument(
                        OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.RECONOCIMIENTO_DEUDA_UNO_NO_FIRMA)
                );

                //change text inside each paragrap
//                for (XWPFParagraph parrafo: doc.getParagraphs()){
//                    List<XWPFRun> runs = parrafo.getRuns();
//                    if (runs != null) {
//
//                        for (XWPFRun run: runs){
//                            String document_text  = run.getText(0);
//
//                            if (document_text.contains("no_request")) {
//                                document_text = document_text.replace("no_request",  infoDoc.get().getInternal_code());
//                                run.setText(document_text,0);
//                            }
//                            if (document_text.contains("associate_name")) {
//                                document_text = document_text.replace("associate_name",  infoDoc.get().getName_complete());
//                                run.setText(document_text,0);
//                            }
//                            if (document_text.contains("no_associate")) {
//                                document_text = document_text.replace("no_associate",  infoDoc.get().getNo_associate().toString());
//                                run.setText(document_text,0);
//                            }
//                            if (document_text.contains("place_request")) {
//                                document_text = document_text.replace("place_request",  "Huehuetenango");
//                                run.setText(document_text,0);
//                            }
//                            if (document_text.contains("date_request")) {
//                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//
//                                document_text = document_text.replace("date_request",  dtf.format(LocalDateTime.now()));
//                                run.setText(document_text,0);
//                            }
//
//                        }
//                    }
//                }

                File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdir();
                }

                File directory_credit_request = new File(rootDirectory.getAbsolutePath() + "/reconocimiento_deuda/");
                if (!directory_credit_request.exists()) {
                    directory_credit_request.mkdir();
                }

                File directory_associate_cui = new File(directory_credit_request + "/" + "5454545" + "/");
                if (!directory_associate_cui.exists()) {
                    directory_associate_cui.mkdir();
                }

                String outputPath = directory_associate_cui + "/" + "5454545" + "-Reconocimiento_Deuda.docx";
                doc.write(new FileOutputStream(outputPath));

                File file = new File(outputPath);
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition", "attachment; filename =\"" + "5454545"+ "-Reconocimiento_Deuda.docx\"");

                return response.build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "Credit not found",
                    0L,
                    "Credit not found",
                    "0"
            )).build();

        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }
}
