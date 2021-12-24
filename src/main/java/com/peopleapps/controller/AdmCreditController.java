package com.peopleapps.controller;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.address.AdmAddressDto;
import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.credit.AdmCreditRequirementDto;
import com.peopleapps.dto.inputs.partner.AdmPartnerDto;
import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.dto.reference.AdmReferenceDto;
import com.peopleapps.dto.reference.AdmReferenceListDto;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "credits")
public class AdmCreditController {

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmReferenceAccountRepository admReferenceAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmPartnerCreditRepository admPartnerCreditRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject

    AdmReferenceRepository admReferenceRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;


    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmActivityAccountRepository admActivityAccountRepository;

    @Inject
    Logger logger;

    //Get all credits
    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("date_ini") String dateIni,
            @QueryParam("date_end") String dateEnd,
            @QueryParam("status_operated") @DefaultValue("0") Long statusOperated,
            @QueryParam("agency") UUID agencyKey
    ) {

        List<AdmCreditDto> admCreditList = admCreditRepository.getAllCredits(
                null, desc, null, dateIni, dateEnd, statusOperated, agencyKey);

        return Response.status(Response.Status.OK).entity(admCreditList).build();
    }

    //Get credit by credit key
    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID creditKey
    ) {
        if (creditKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmCreditDto> admCreditList = admCreditRepository.getAllCredits(
                creditKey, null, null, null, null, null, null);
        if (admCreditList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admCreditList.get(0)).build();

    }

    //Get credits by associate key
    @GET
    @Path("/associates/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getByAssociateKey(
            @PathParam("key") UUID associateKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        if (associateKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmCreditDto> admCreditList = admCreditRepository.getAllCredits(
                null, desc, associateKey, null, null, null, null);
        if (admCreditList.size() == 0)
            return Response.status(Response.Status.OK).entity(admCreditList).build();

        return Response.status(Response.Status.OK).entity(admCreditList).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmCreditDto admCreditDto) {
        try {

            if (admCreditDto.getCreatedBy() == null || admCreditDto.getCreatedBy().getPersonKey() == null) {
                return this.ownRequestResponse("User is mandatory", Response.Status.BAD_REQUEST);
            }

            if (admCreditDto.getOperatedBy() == null || admCreditDto.getOperatedBy().getPersonKey() == null) {
                return this.ownRequestResponse("User that operated by is mandatory", Response.Status.BAD_REQUEST);
            }

            if (admCreditDto.getOrganization() == null) {
                return this.ownRequestResponse("Organization is mandatory", Response.Status.BAD_REQUEST);
            }

            if (admCreditDto.getOrganization().getOrganizationKey() == null) {
                return this.ownRequestResponse("Organization key is mandatory", Response.Status.BAD_REQUEST);
            }

            AdmTypology status = this.checkTypology(admCreditDto.getStatus());
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmTypology statusOperated = this.checkTypology(admCreditDto.getStatusOperated());
            if (statusOperated == null) {
                return this.ownRequestResponse("Status operated is mandatory", Response.Status.BAD_REQUEST);
            }

            AdmTypology profession = this.checkTypology(admCreditDto.getProfession());
            if (profession == null) {
                return this.ownRequestResponse("Profession is mandatory", Response.Status.BAD_REQUEST);
            }

            AdmTypology occupation = this.checkTypology(admCreditDto.getOccupation());
            if (occupation == null) {
                return this.ownRequestResponse("Occupation is mandatory", Response.Status.BAD_REQUEST);
            }

            AdmUser createdByUser = admUserRepository.findByKey(admCreditDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return this.ownRequestResponse("User not found Error", Response.Status.NOT_FOUND);
            }

            AdmUser operatedByUser = admUserRepository.findByKey(admCreditDto.getOperatedBy().getPersonKey());
            if (operatedByUser == null) {
                return this.ownRequestResponse("Operated by user not found Error", Response.Status.NOT_FOUND);
            }

            AdmOrganization organization = admOrganizationRepository.getByKey(admCreditDto.getOrganization().getOrganizationKey());
            if (organization == null) {
                return this.ownRequestResponse("Organization not found Error", Response.Status.NOT_FOUND);
            }

            /*
            AdmOrganizationResponsible organizationResponsible = admOrganizationResponsibleRepository.findBy(admCreditDto.getOrganizationResponsible().getOrganizationResponsibleId());
            if (organizationResponsible == null) {
                return this.ownRequestResponse("Organization responsible not found Error", Response.Status.NOT_FOUND);
            }

             */

            AdmCreditCalculator creditCalculator = admCreditCalculatorRepository.findBy(admCreditDto.getCalculator().getCalculatorId());
            if (creditCalculator == null) {
                return this.ownRequestResponse("Calculator not found Error", Response.Status.NOT_FOUND);
            }

            AdmAddressAccount addressAccount = admAddressAccountRepository.createAccount();
            AdmReferenceAccount referenceAccount = admReferenceAccountRepository.createAccount();
            AdmDocumentAccount documentAccount = admDocumentAccountRepository.createAccount();

            //creating new organization responsible
            AdmOrganizationResponsible organizationResponsible = new AdmOrganizationResponsible(
                    CsnConstants.ZERO,
                    organization,
                    creditCalculator.getPerson(),
                    CsnFunctions.now(),
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.STATUS_ACTIVE)
            );

            //persisting organization responsible
            organizationResponsible = admOrganizationResponsibleRepository.save(organizationResponsible);
            //generating internal code
            List<String> lastCreditCode = admCreditRepository.getLastCreditIdAndInternalCode();
            String internalCode = "";
            if (lastCreditCode == null) {
                //generates with code 0001
                internalCode = CsnFunctions.generateCreditInternalCode(organization.getInternalCode(), null);
            } else {
                internalCode = CsnFunctions.generateCreditInternalCode(organization.getInternalCode(), lastCreditCode.get(1));
            }


            AdmCredit credit = new AdmCredit(
                    CsnConstants.ZERO,
                    //todo no year
                    CsnFunctions.getYear(),
                    //todo reference no
                    2021L,
                    addressAccount,
                    referenceAccount,
                    documentAccount,
                    createdByUser,
                    CsnFunctions.now(),
                    status,
                    //todo activity account
                    admActivityAccountRepository.createAccount(),
                    //todo internal code
                    internalCode,
                    admCreditDto.getEstateDate(),
                    statusOperated,
                    operatedByUser,
                    admCreditDto.getAnnotation() == null ? CsnConstants.DEFAULT_TEXT_SD : admCreditDto.getAnnotation(),
                    organizationResponsible,
                    creditCalculator,
                    profession,
                    occupation,
                    admCreditDto.getOwnHouse() == null ? Boolean.FALSE : admCreditDto.getOwnHouse(),
                    CsnFunctions.generateKey()
            );
            admCreditRepository.save(credit);
            //persisting default address (leader address of associate)
            AdmAddress creditAddress = admAddressRepository.getLeaderAddress(creditCalculator.getPerson().getAddressAccount().getAddressAccountId());
            if (creditAddress == null) {
                return this.ownRequestResponse("Main address not found Error", Response.Status.NOT_FOUND);
            }
            logger.error("credit address account id: " + creditCalculator.getPerson().getAddressAccount().getAddressAccountId());
            logger.error("address id: " + creditAddress.getAddressId());
            //Changing address, address account
            creditAddress.setAddressAccount(credit.getAddressAccount());
            creditAddress.setAddressId(CsnConstants.ZERO);
            //persisting address
            creditAddress = admAddressRepository.save(creditAddress);
            admAddressRepository.resetLeader(creditAddress);

            return CsnFunctions.setResponse(credit.getCreditId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(
            @PathParam("key") UUID creditKey,
            AdmCreditDto admCreditDto) {
        if (!creditKey.equals(admCreditDto.getCreditKey())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        AdmCredit currentCredit = admCreditRepository.findByKey(creditKey);
        if (currentCredit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
        }

        //status
        AdmTypology status = this.checkTypology(admCreditDto.getStatus());
        if (status != null) {
            currentCredit.setStatus(status);
        }

        //estateDate fecha avaluo
        if (admCreditDto.getEstateDate() != null) {
            currentCredit.setEstateDate(admCreditDto.getEstateDate());
        }

        //credit operated by
        if (admCreditDto.getOperatedBy() != null && admCreditDto.getOperatedBy().getPersonKey() != null) {
            AdmUser operatedBy = admUserRepository.findByKey(admCreditDto.getOperatedBy().getPersonKey());
            if (operatedBy != null) {
                currentCredit.setOperatedBy(operatedBy);
            }
        }

        //credit status
        if (admCreditDto.getStatusOperated() != null && admCreditDto.getStatusOperated().getTypologyId() != null) {
            AdmTypology statusOperated = admTypologyRepository.findBy(admCreditDto.getStatusOperated().getTypologyId());
            if (statusOperated != null) {
                currentCredit.setStatusOperated(statusOperated);
            }
        }

        //annotation
        if (admCreditDto.getAnnotation() != null) {
            currentCredit.setAnnotation(admCreditDto.getAnnotation());
        }

        //ownHouse
        if (admCreditDto.getOwnHouse() != null) {
            currentCredit.setOwnHouse(admCreditDto.getOwnHouse());
        }


        admCreditRepository.save(currentCredit);
        return Response.status(Response.Status.OK).build();
    }

    private Response ownRequestResponse(String message, Response.Status resp) {
        return CsnFunctions.setResponse(0L, resp, message);
    }

    private AdmTypology checkTypology(AdmCreditDto.AdmTypologyCreditDto typologyDto) {

        AdmTypology result = null;
        //check if values are null
        if (typologyDto != null && typologyDto.getTypologyId() != null) {
            result = this.admTypologyRepository.findBy(typologyDto.getTypologyId());
        }
        return result;
    }


    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/requirements")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getRequirementsList(
            @PathParam("key") UUID creditKey
    ) {

        AdmCredit currentCredit = admCreditRepository.findByKey(creditKey);
        if (currentCredit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
        }
        List<AdmCreditRequirementDto> admRequirementDto = admCreditRepository.getCreditRequirementsList(
                currentCredit);

        return Response.status(Response.Status.OK).entity(admRequirementDto).build();
    }


    //Method for downloading credit requirement in docx
    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/requirements/files/archivo_requerimientos_credito")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getArchivoRequirementoCredito(
            @PathParam("key") UUID creditKey
    ) {
        try {

            AdmCredit currentCredit = admCreditRepository.findByKey(creditKey);
            if (currentCredit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
            }
            List<AdmCreditRequirementDto> admRequirementDto = admCreditRepository.getCreditRequirementsList(
                    currentCredit);

            logger.warn("Requerimientos encontrados: " + admRequirementDto.size());
            XWPFDocument doc = new XWPFDocument(
                    OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.CREDITO_REQUERIMIENTO_PLANTILLA)
            );

            //Mapa con valores de la persona
            AdmPerson persona = currentCredit.getCalculator().getPerson();
            Map<String, String> valoresPersona = new HashMap<>();
            valoresPersona.put("associate_no", CsnFunctions.getAccountNumberOrder(persona.getMembershipNumber()));
            valoresPersona.put("associate_cui", persona.getCui().toString());
            valoresPersona.put("associate_email", persona.getEmail());
            valoresPersona.put("associate_name", persona.getFirstName() + ", " + persona.getLastName());
            //formatting date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaFormato = persona.getDateCreated().format(formatter);
            valoresPersona.put("date_created", fechaFormato);

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

                for (XmlObject obj : ctrsintxtbx) {
                    CTR ctr = CTR.Factory.parse(obj.xmlText());
                    //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                    XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                    String text = bufferrun.getText(0);

                    //compare text to every map key
                    for(Map.Entry<String, String> entrada: valoresPersona.entrySet()){
                        if (text != null && text.contains(entrada.getKey())) {
                            text = text.replace(entrada.getKey(), entrada.getValue());
                            bufferrun.setText(text, 0);
                        }
                    }
                    obj.set(bufferrun.getCTR());
                }

            }

            //mapa que contiene el texto a buscar y el texto nuevo a usarse
            Map<String, String> valores = new HashMap<>();
            for (int i = 0; i < 13; i++) {
                valores.put("req_" + (i + 1) + "_percent", admRequirementDto.get(i).getPercentageComplete().toString());
            }
            //Obteniendo la tabla para cambiar valores
            //List<XWPFTable> tables = doc.getTables();
            XWPFTable table = doc.getTableArray(0);
            for (XWPFTableRow fila : table.getRows()) {
                //celdas
                List<XWPFTableCell> celdas = fila.getTableCells();
                for (XWPFTableCell celda : celdas) {
                    for (Map.Entry<String, String> entrada : valores.entrySet()) {
                        if (celda.getText().equals(entrada.getKey())){
                            XWPFParagraph newPara = new XWPFParagraph(celda.getCTTc().addNewP(), celda);
                            XWPFRun r1 = newPara.createRun();
                            r1.setBold(true);
                            if(Double.parseDouble(entrada.getValue()) < 99.99){
                                //set to red color
                                r1.setColor("f23513");
                            }else{
                                //hex color for green
                                r1.setColor("76d442");
                            }
                            r1.setFontSize(15);
                            r1.setText(entrada.getValue() + "%");
                            //center paragraph content
                            newPara.setAlignment(ParagraphAlignment.CENTER);
                            celda.removeParagraph(0);
                            celda.setParagraph(newPara);

                        }
                    }
                }
            }


            File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

            if (!rootDirectory.exists()) {
                rootDirectory.mkdir();
            }

            //logger.warn("root " + rootDirectory.getAbsolutePath() + " write? " + rootDirectory.canWrite());
            File directory_preinscriptions = new File(rootDirectory.getAbsolutePath() + "/creditos/");
            if (!directory_preinscriptions.exists()) {
                directory_preinscriptions.mkdir();
            }
            //logger.warn("directory_pre " + directory_preinscriptions.getAbsolutePath() + " write? " + directory_preinscriptions.canWrite());

            File directory_associate_cui = new File(directory_preinscriptions + "/" + currentCredit.getCreditKey() + "/");
            if (!directory_associate_cui.exists()) {
                directory_associate_cui.mkdir();
            }
            //logger.warn("direct associate " + directory_associate_cui.getAbsolutePath() + " write? " + directory_associate_cui.canWrite());

            String outputPath = directory_associate_cui + "/" + currentCredit.getCreditKey().toString() + "-Requerimientos_credito.docx";

            doc.write(new FileOutputStream(outputPath));

            logger.info("Path: -----> " + outputPath);

            File file = new File(outputPath);
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename =\"" + currentCredit.getCreditKey().toString() + "-Requerimientos_credito.docx\"");
            return response.build();

        } catch (
                Exception ex) {

            logger.error(ex.getMessage());
            ex.printStackTrace();

            return Response.serverError().build();
        }
    }
}




