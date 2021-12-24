package com.peopleapps.controller;

import com.peopleapps.dto.creditCalculator.AdmCalculationDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorDto;
import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Path("/creditCalculator/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "creditCalculator")
public class AdmCreditCalculatorController {

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmCreditLineRepository admCreditLineRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    public Response getAll(
            @PathParam("key") UUID associateKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("organization") @DefaultValue("0") Long organizationId,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("startDate") @DefaultValue("") String startDate,
            @QueryParam("endDate") @DefaultValue("") String endDate
    ) {

        List<AdmCreditCalculatorDto> admCreditCalculators = admCreditCalculatorRepository.getAllCreditCalculators(
                null, associateKey, desc, statusId, startDate, endDate);

        return Response.status(Response.Status.OK).entity(admCreditCalculators).build();
    }

    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/{id:[0-9][0-9]*}")
//    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long creditCalculatorId,
            @QueryParam("type") @DefaultValue("json") String responseType
    ) {
        if (creditCalculatorId == null || creditCalculatorId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmCreditCalculatorDto> admCreditCalculators = admCreditCalculatorRepository.getAllCreditCalculators(
                creditCalculatorId, associateKey, null, null, null, null);


        if (admCreditCalculators.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        AdmCalculationDto calculationDto = new AdmCalculationDto(
                admCreditCalculators.get(0),
                this.getCalculations(admCreditCalculators.get(0)).getCalculations()
        );


        if (responseType.equals("document")) {

            return printDocx(calculationDto);
        }


        return Response.status(Response.Status.OK).entity(calculationDto).build();

    }

    //GET para no asociados
    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getNonAssociate(
            AdmCreditCalculatorDto admCreditCalculatorDto,
            @QueryParam("type") @DefaultValue("json") String responseType
    ) {
        if (admCreditCalculatorDto.getCreditLine() == null
                || admCreditCalculatorDto.getCreditLine().getCreditLineId() == null
                || admCreditCalculatorDto.getCreditLine().getCreditLineId() == 0) {
            //setting default credit line 1
            admCreditCalculatorDto.setCreditLine(new AdmCreditLineDto(CsnConstants.CREDIT_LINE_AGRICOLA));
        }

        AdmCreditLine creditLine = admCreditLineRepository.findBy(admCreditCalculatorDto.getCreditLine().getCreditLineId());
        if (creditLine == null) {

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit Line not found Error");
        }
        //setting dto description for credit line
        admCreditCalculatorDto.getCreditLine().setDescription(creditLine.getDescription());
        //setting date created
        admCreditCalculatorDto.setDateCreated(CsnFunctions.now());

        AdmCalculationDto calculationDto = new AdmCalculationDto(
                admCreditCalculatorDto,
                this.getCalculations(admCreditCalculatorDto).getCalculations()
        );

        if (responseType.equals("document")) {
            return printDocx(calculationDto);
        }


        return Response.status(Response.Status.OK).entity(calculationDto).build();

    }

    //Para asociados
    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    public Response create(
            @PathParam("key") UUID associateKey,
            @QueryParam("type") @DefaultValue("json") String responseType,
            @QueryParam("save") @DefaultValue("false") Boolean saveRecord,
            AdmCreditCalculatorDto admCreditCalculatorDto) {
        try {
            if (associateKey == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Associate key is mandatory");
            }

            if (admCreditCalculatorDto.getCreatedBy() == null || admCreditCalculatorDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            if (admCreditCalculatorDto.getCreditLine() == null
                    || admCreditCalculatorDto.getCreditLine().getCreditLineId() == null
                    || admCreditCalculatorDto.getCreditLine().getCreditLineId() == 0) {

                //setting default credit line 1
                admCreditCalculatorDto.setCreditLine(new AdmCreditLineDto(CsnConstants.CREDIT_LINE_AGRICOLA));
            }

            AdmCreditLine creditLine = admCreditLineRepository.findBy(admCreditCalculatorDto.getCreditLine().getCreditLineId());
            if (creditLine == null) {

                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit Line not found Error");
            }
            //setting dto description for credit line
            admCreditCalculatorDto.getCreditLine().setDescription(creditLine.getDescription());

            logger.error("description 1: " +
                    admCreditCalculatorDto.getCreditLine().getDescription());

            AdmPerson associate = admPersonRepository.findByKey(associateKey);

            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(admCreditCalculatorDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            AdmTypology status = null;
            if (admCreditCalculatorDto.getStatus() != null && admCreditCalculatorDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(admCreditCalculatorDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //get calculations
            AdmCalculationDto calculationDto = new AdmCalculationDto(
                    admCreditCalculatorDto,
                    this.getCalculations(admCreditCalculatorDto).getCalculations()
            );


            //save record only if param is true
            if (saveRecord) {

                AdmCreditCalculator calculator = new AdmCreditCalculator(
                        CsnConstants.ZERO,
                        associate,
                        CsnFunctions.now(),
                        admCreditCalculatorDto.getNoPeriod(),
                        admCreditCalculatorDto.getNoPayments(),
                        admCreditCalculatorDto.getCredit().doubleValue(),
                        admCreditCalculatorDto.getInterestRate().doubleValue(),
                        admCreditCalculatorDto.getInterestFinal().doubleValue(),
                        createdByUser,
                        CsnFunctions.now(),
                        status,
                        creditLine
                );

                admCreditCalculatorRepository.save(calculator);
            }


            if (responseType.equals("document")) {
                return printDocx(calculationDto);
            }

            return Response.status(Response.Status.OK).entity(calculationDto).build();



            /*
            return CsnFunctions.setResponse(calculator.getCalculatorId(),
                    Response.Status.CREATED, null);

             */
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("id") Long id,
                         AdmCreditCalculatorDto admCreditCalculatorDto) {
        if (!id.equals(admCreditCalculatorDto.getCalculatorId())) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        AdmCreditCalculator currentCalculator = this.admCreditCalculatorRepository.findBy(id);
        if (currentCalculator == null) {
            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit Calculator not found");
        }

        //updating status
        if (admCreditCalculatorDto.getStatus() != null
                && admCreditCalculatorDto.getStatus().getTypologyId() != null) {
            AdmTypology status = admTypologyRepository.findBy(admCreditCalculatorDto.getStatus().getTypologyId());
            if (status == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect status");
            }

            currentCalculator.setStatus(status);
            //updating header status
            admCreditCalculatorDto.getStatus().setDescription(status.getDescription());
            admCreditCalculatorDto.getStatus().setTypologyId(status.getTypologyId());
        }

        //updating line
        if (admCreditCalculatorDto.getCreditLine() != null
                && admCreditCalculatorDto.getCreditLine().getCreditLineId() != null) {

            AdmCreditLine creditLine = admCreditLineRepository.findBy(admCreditCalculatorDto.getCreditLine().getCreditLineId());
            if (creditLine == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect line");
            }
            currentCalculator.setCreditLine(creditLine);

            //updating header credit line
            admCreditCalculatorDto.getCreditLine().setCreditLineId(creditLine.getCreditLineId());
            admCreditCalculatorDto.getCreditLine().setOrganization(new AdmCreditLineDto.AdmCreditLineOrganizationDto(
                    creditLine.getOrganization().getOrganizationKey(),
                    creditLine.getOrganization().getOrganizationName(),
                    creditLine.getOrganization().getOrganizationCommercial()));
        }

        //updating fecha aplicacion
        if (admCreditCalculatorDto.getApplicationDate() != null) {
            currentCalculator.setApplicationDate(admCreditCalculatorDto.getApplicationDate());
        }

        //updating periodos
        if (admCreditCalculatorDto.getNoPeriod() != null) {
            currentCalculator.setNoPeriod(admCreditCalculatorDto.getNoPeriod());
        }
        if (admCreditCalculatorDto.getNoPayments() != null) {
            currentCalculator.setNoPayments(admCreditCalculatorDto.getNoPayments());
        }

        if (admCreditCalculatorDto.getInterestRate() != null) {
            currentCalculator.setInterestRate(admCreditCalculatorDto.getInterestRate().doubleValue());
        }
        if (admCreditCalculatorDto.getCredit() != null) {
            currentCalculator.setCredit(admCreditCalculatorDto.getCredit().doubleValue());
        }


        //building response

        //get calculations
        AdmCalculationDto calculationDto = new AdmCalculationDto(
                admCreditCalculatorDto,
                this.getCalculations(admCreditCalculatorDto).getCalculations()
        );

        //setting final interest
        currentCalculator.setInterestFinal(calculationDto.getHeader().getInterestFinal().doubleValue());
        //updating in database
        admCreditCalculatorRepository.save(currentCalculator);
        return Response.status(Response.Status.OK).entity(calculationDto).build();
    }


    //calculations
    public AdmCalculationDto getCalculations(AdmCreditCalculatorDto calculatorValues) {
        //iterating for each payment
        //calculationDto for calculating with all decimals
        AdmCalculationDto calculationDto = new AdmCalculationDto();
        //resultDto for results with 2 decimals
        AdmCalculationDto resultDto = new AdmCalculationDto();

        calculationDto.setHeader(calculatorValues);
        resultDto.setHeader(calculatorValues);
        //calculations will be performed without rounding, for not affecting the final results
        List<AdmCalculationDto.AdmCreditCalculation> calculations = new ArrayList<>();
        //results will be rounded using 2 decimales
        List<AdmCalculationDto.AdmCreditCalculation> results = new ArrayList<>();

        calculationDto.setCalculations(calculations);
        resultDto.setCalculations(results);
        double mesesFuturo = (12 * (double) calculatorValues.getNoPeriod()) / (double) calculatorValues.getNoPayments();
        double mesesAumento = mesesFuturo;
        BigDecimal tempBigDecimal = new BigDecimal("0.00");

        //convertimos el interes a decimal
        calculatorValues.setInterestRate(calculatorValues.getInterestRate().divide(new BigDecimal("100"), CsnConstants.calculationScale, CsnConstants.calculationRoundMode));
        for (int i = 0; i < calculatorValues.getNoPayments(); i++) {
            //for calculations no truncating decimals
            AdmCalculationDto.AdmCreditCalculation calculation = new AdmCalculationDto.AdmCreditCalculation();
            //for results, decimals are truncated
            AdmCalculationDto.AdmCreditCalculation result = new AdmCalculationDto.AdmCreditCalculation();

            //setting payment number for calculation and result
            calculation.setNoPago((long) i + 1);
            result.setNoPago((long) i + 1);
            //calculating date
            //aproximamos extrayendo la parte decimal
            String mesesAumentoString = String.valueOf(mesesAumento);
            //indice del decimal
            int indiceDecimal = mesesAumentoString.indexOf(".");
            double parteEntera = Double.parseDouble(mesesAumentoString.substring(0, indiceDecimal));
            double parteDecimal = mesesAumento - parteEntera;


            LocalDate fechaPago = null;
            if (parteDecimal > 0.9) {
                //si es mayor a 0.9 por cuestiones de precision aproximamos hacia el siguiente numero
                fechaPago = calculatorValues.getApplicationDate().toLocalDate().plusMonths((long) Math.ceil(mesesAumento));
            } else {
                fechaPago = calculatorValues.getApplicationDate().toLocalDate().plusMonths((long) mesesAumento);
            }

            mesesAumento += mesesFuturo;


            //Si es sabado, restamos un dia
            //si es domingo, sumamos un dia
            LocalDate fechaEntreSemana = fechaPago.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    ? fechaPago.plusDays(-1) :
                    (fechaPago.getDayOfWeek().equals(DayOfWeek.SUNDAY) ? fechaPago.plusDays(1) : fechaPago);

            //setting date
            calculation.setFecha(fechaEntreSemana);
            result.setFecha(fechaEntreSemana);

            //calculating capital
            tempBigDecimal = calculatorValues.getCredit().divide(new BigDecimal(calculatorValues.getNoPayments()), CsnConstants.calculationScale, CsnConstants.calculationRoundMode);

            //setting capital
            calculation.setCapital(tempBigDecimal);
            result.setCapital(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));


            //calculating paid capital
            //initial payment
            if (i == 0) {
                //set paid capital
                calculation.setCapitalPagado(calculation.getCapital());
                result.setCapitalPagado(result.getCapital());
            } else {
                //accoumulating capital
                //capital pagado anterior + capital
                tempBigDecimal = calculationDto.getCalculations().get(i - 1).getCapitalPagado().add(calculation.getCapital());
                //setting paid capital
                calculation.setCapitalPagado(tempBigDecimal);
                result.setCapitalPagado(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));
            }

            //calculating interest
            //diferencia dias
            //diferente para el segundo pago
            Double diferenciaDias = 0.0;
            if (i == 0) {
                //Time portion may change results, that is why time portion is ignored
                diferenciaDias = (double) ChronoUnit.DAYS.between(calculatorValues.getApplicationDate().toLocalDate().atStartOfDay(), calculation.getFecha().atStartOfDay());
            } else {
                diferenciaDias = (double) ChronoUnit.DAYS.between(calculationDto.getCalculations().get(i - 1).getFecha(), calculation.getFecha().atStartOfDay());
            }

            BigDecimal interes = new BigDecimal("0.00");

            //interes calculado sobre el credito
            if (i == 0) {

                tempBigDecimal = (new BigDecimal(diferenciaDias.toString()).divide(new BigDecimal("360"), CsnConstants.calculationScale, CsnConstants.calculationRoundMode))
                        .multiply(new BigDecimal(calculatorValues.getInterestRate().toString()))
                        .multiply(new BigDecimal(calculatorValues.getCredit().toString()));


                interes = tempBigDecimal;

            } else {
                //interes calculado sobre saldo, el saldo esta en el objeto anterior en la lista
                BigDecimal saldo = calculationDto.getCalculations().get(i - 1).getSaldo();
                //redondeando a 3 decimales
                tempBigDecimal = (new BigDecimal(diferenciaDias).divide(new BigDecimal("360"), CsnConstants.calculationScale, CsnConstants.calculationRoundMode))
                        .multiply(new BigDecimal(calculatorValues.getInterestRate().toString()))
                        .multiply(saldo);

                interes = tempBigDecimal;

            }
            //setting interest
            calculation.setInteres(interes);
            result.setInteres(redondearBigDecimal(interes, CsnConstants.displayScale));
            //calculating interes pagado
            //interes pagada acummulates
            if (i == 0) {
                calculation.setInteresPagado(interes);
                result.setInteresPagado(redondearBigDecimal(interes, CsnConstants.displayScale));
            } else {
                BigDecimal interesPagadoAnterior = calculationDto.getCalculations().get(i - 1).getInteresPagado();
                //Acumulando el interes
                tempBigDecimal = interesPagadoAnterior.add(interes);
                calculation.setInteresPagado(tempBigDecimal);
                result.setInteresPagado(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));
            }

            //calculating saldo
            if (i == 0) {
                tempBigDecimal = new BigDecimal(calculatorValues.getCredit().toString()).subtract(calculation.getCapital());
                calculation.setSaldo(tempBigDecimal);
                result.setSaldo(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));
            } else {
                //restamos del saldo anterior el capital
                tempBigDecimal = calculationDto.getCalculations().get(i - 1).getSaldo().subtract(calculation.getCapital());
                calculation.setSaldo(tempBigDecimal);
                result.setSaldo(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));

            }

            //calculating payment
            tempBigDecimal = calculation.getCapital().add(calculation.getInteres());

            //setting payment
            calculation.setPago(tempBigDecimal);
            result.setPago(redondearBigDecimal(tempBigDecimal, CsnConstants.displayScale));

            //agregamos a la lista
            calculationDto.getCalculations().add(calculation);
            resultDto.getCalculations().add(result);
        }

        //calculating final interest
        AdmCalculationDto.AdmCreditCalculation maxInteresPagado = calculationDto.getCalculations()
                .stream()
                .max(Comparator.comparing(AdmCalculationDto.AdmCreditCalculation::getInteresPagado))
                .orElseThrow(NoSuchElementException::new);


        tempBigDecimal = (maxInteresPagado.getInteresPagado()
                .divide(new BigDecimal(calculatorValues.getCredit().toString()), CsnConstants.calculationScale, CsnConstants.calculationRoundMode))
                .divide(new BigDecimal(calculatorValues.getNoPeriod().toString()), CsnConstants.calculationScale, CsnConstants.calculationRoundMode);

        //convertimos a entero el interes y el interes final para la salida
        calculatorValues.setInterestFinal(this.redondearBigDecimal(tempBigDecimal
                .multiply(new BigDecimal("100")), CsnConstants.calculationScale)
                .setScale(CsnConstants.displayScale, CsnConstants.resultRoundMode));

        tempBigDecimal = calculatorValues.getInterestRate();
        //Convertimos el interes  a entero
        calculatorValues.setInterestRate(this.redondearBigDecimal(tempBigDecimal.multiply(new BigDecimal("100")), CsnConstants.calculationScale)
                .setScale(CsnConstants.displayScale, CsnConstants.resultRoundMode));

        return resultDto;
    }


    public BigDecimal redondearBigDecimal(BigDecimal numero, int decimales) {
        //decimales = CsnConstants.scale;
        if (decimales < 0) throw new IllegalArgumentException();
        BigDecimal redondeado = new BigDecimal(numero.toString()).setScale(decimales, CsnConstants.resultRoundMode);
        return redondeado;
    }

    public Response printDocx(AdmCalculationDto calculationDto) {
        try {

            XWPFDocument doc = new XWPFDocument(
                    OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.COTIZACION_PLANTILLA)
            );

                //text boxes replace
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                XmlCursor cursor = paragraph.getCTP().newCursor();
                cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

                List<XmlObject> ctrsintxtbx = new ArrayList<XmlObject>();

                while (cursor.hasNextSelection()) {
                    cursor.toNextSelection();
                    XmlObject obj = cursor.getObject();
                    ctrsintxtbx.add(obj);
                }

                //Replacing associate number inside text box
                for (XmlObject obj : ctrsintxtbx) {
                    CTR ctr = CTR.Factory.parse(obj.xmlText());
                    //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                    XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                    String text = bufferrun.getText(0);

                    if (text != null && text.contains("associate_no")) {
                        text = text.replace(text, "No aplica");
                        if (calculationDto.getHeader().getPerson() != null) {
                            if (calculationDto.getHeader().getPerson().getMembershipNumber() != null) {
                                text = text.replace(text, calculationDto.getHeader().getPerson().getMembershipNumber().toString());
                            }
                        }

                        bufferrun.setText(text, 0);
                    }

                    obj.set(bufferrun.getCTR());
                }
            }

            List<XWPFTable> tables = doc.getTables();

            for (XWPFTable t : tables) {
                for (XWPFTableRow row : t.getRows()) {

                    List<XWPFTableCell> cells = row.getTableCells();
                    int col = 0;
                    for (XWPFTableCell cell : cells) {

                        XWPFParagraph para = cell.getParagraphs().get(0);
                        List<XWPFRun> runs = para.getRuns();
                        for (XWPFRun r : runs) {
                            String text = r.getText(0);
                            if (text != null && text.contains("associate_name")) {
                                //if null change it to empty string
                                if (calculationDto.getHeader().getPerson().getLastName() == null) {
                                    calculationDto.getHeader().getPerson().setLastName("");
                                }
                                text = text.replace(text, calculationDto.getHeader().getPerson().getFirstName() + " " + calculationDto.getHeader().getPerson().getLastName());
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("associate_exp")) {
                                text = text.replace(text, "No aplica");
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("associate_address")) {
                                text = text.replace(text, "No aplica");
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("associate_line")) {
                                text = text.replace(text, calculationDto.getHeader().getCreditLine().getDescription());
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("calculate_date")) {
                                //Giving format to date
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CsnConstants.DATE_FORMAT);
                                text = text.replace(text, calculationDto.getHeader().getApplicationDate().format(formatter));
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("no_years")) {
                                text = text.replace(text, calculationDto.getHeader().getNoPeriod().toString());
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("no_payment")) {
                                text = text.replace(text, calculationDto.getHeader().getNoPayments().toString());

                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("calculate_credit")) {
                                //Giving format to amount
                                DecimalFormat df = new DecimalFormat("###,###,###.00");
                                text = text.replace(text, "Q."+df.format(calculationDto.getHeader().getCredit()));
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("sum_interest")) {
                                text = text.replace(text, calculationDto.getHeader().getInterestRate().toString() + "%");
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("interest_final")) {
                                text = text.replace(text, calculationDto.getHeader().getInterestFinal().toString() + "%");
                                r.setText(text, 0);
                            }
                        }

                    }
                }
            }

            XWPFTable tab = doc.createTable();
            tab.setCellMargins(50, 15, 10, 0);

            tab.setWidth(-1);

            XWPFTableRow row = tab.getRow(0); // First row
            centerTextBold(row.getCell(0), "Nº de pago", true);
            centerTextBold(row.addNewTableCell(), "Fecha", true);
            centerTextBold(row.addNewTableCell(), "Pago", true);
            centerTextBold(row.addNewTableCell(), "Capital", true);
            centerTextBold(row.addNewTableCell(), "Cap. Pagado", true);
            centerTextBold(row.addNewTableCell(), "Interés", true);
            centerTextBold(row.addNewTableCell(), "Int. Pagado", true);
            centerTextBold(row.addNewTableCell(), "Saldo", true);


            for (AdmCalculationDto.AdmCreditCalculation item : calculationDto.getCalculations()) {
                row = tab.createRow(); // Second Row
                centerTextBold(row.getCell(0), item.getNoPago().toString(), false);
                centerTextBold(row.getCell(1), item.getFecha().toString(), false);
                centerTextBold(row.getCell(2), item.getPago().toString(), false);
                centerTextBold(row.getCell(3), item.getCapital().toString(), false);
                centerTextBold(row.getCell(4), item.getCapitalPagado().toString(), false);
                centerTextBold(row.getCell(5), item.getInteres().toString(), false);
                centerTextBold(row.getCell(6), item.getInteresPagado().toString(), false);
                centerTextBold(row.getCell(7), item.getSaldo().toString(), false);

            }


            File rootDirectory = new File(Paths.get("").toAbsolutePath() + CsnConstants.ROOT_DIRECTORY);

            if (!rootDirectory.exists())
                rootDirectory.mkdir();


            File calculator_directory = new File(rootDirectory.getAbsolutePath() + "/cotizacion/");
            if (!calculator_directory.exists())
                calculator_directory.mkdir();


            String personKey = calculationDto.getHeader().getPerson().getPersonKey() == null ? "cotizacion_temporal" : calculationDto.getHeader().getPerson().getPersonKey().toString();
            File directory_quote = new File(calculator_directory + "/" + personKey + "/");
            if (!directory_quote.exists())
                directory_quote.mkdir();



            String name_complete = calculationDto.getHeader().getPerson().getFirstName() + "_" + calculationDto.getHeader().getPerson().getLastName();
            //if char is not replaced, it causes an error, where the program thinks we are referencing a directory (folder) that
            //does not exist e.g. John S/D
            String validNameComplete = name_complete.replace('/', '-');
            String outputPath = directory_quote + "/" + validNameComplete + ".docx";


            doc.write(new FileOutputStream(outputPath));

            logger.info("Created quote =========" + outputPath);

            File file = new File(outputPath);
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename=\"" + name_complete + "_cotizacion-.docx\"");

            return response.build();
        } catch (Exception ex) {
            ex.printStackTrace();

            logger.warn(ex.getMessage());

            logger.error(ex.getMessage());

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }

    }

    private void centerTextBold(XWPFTableCell cell, String text, Boolean bold) {
        XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tempRun = tempParagraph.createRun();
        tempRun.setText(text);
        tempRun.setBold(bold);
    }

}

