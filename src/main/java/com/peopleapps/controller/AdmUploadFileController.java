package com.peopleapps.controller;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.inputs.beneficiary.AdmBeneficiaryDto;
import com.peopleapps.dto.inputs.partner.AdmPartnerDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.repository.AdmBeneficiaryRepository;
import com.peopleapps.repository.AdmPartnerRepository;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.repository.AdmPreinscriptionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.peopleapps.util.CsnConstants;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;

@Path("associate/v1/")
@RequestScoped
public class AdmUploadFileController {
    @Inject
    ResponseJson responseJson;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmBeneficiaryRepository admBeneficiaryRepository;

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/files/archivo_preinscripcion")
    public Response getArchivoPreinscripcionAsociado(@PathParam("key") UUID associateKey) {
        try {
            List<AdmPreinscripcionDto> associatePreInscription = admPersonRepository.getAssociatePreinscription(associateKey);
            if (associatePreInscription != null) {
                if (associatePreInscription.size() > 0) {
                    List<AdmBeneficiaryDto> admBeneficiaryDtoList = admBeneficiaryRepository.getAllBeneficiaries(
                            null, associatePreInscription.get(0).getAssociate().getPersonKey(), null, null);

                    if (admBeneficiaryDtoList != null) {
                        if (admBeneficiaryDtoList.size() > 0) {
                            associatePreInscription.get(0).setBeneficiary(
                                    new AdmPreinscripcionDto.AdmPreinscripcionBeneficiaryDto(
                                            new AdmPreinscripcionDto.AdmPreinscripcionPersonDto(
                                                    admBeneficiaryDtoList.get(0).getPerson().getNameComplete()),
                                            null
                                    ));
                        }
                    }

                    List<AdmPartnerDto> admPartnerDtoList = admPartnerRepository.getAllPartners(
                            null, associatePreInscription.get(0).getAssociate().getPersonKey(), null, null);
                    if (admPartnerDtoList != null) {
                        if (admPartnerDtoList.size() > 0) {
                            associatePreInscription.get(0).setPartner(
                                    new AdmPreinscripcionDto.AdmPreinscripcionPersonDto(
                                            admPartnerDtoList.get(0).getPerson().getNameComplete())
                            );
                        }
                    }


                    logger.warn("Asociados encontrados: " + associatePreInscription.size());
                    XWPFDocument doc = new XWPFDocument(
                            OPCPackage.open(Paths.get("/opt/payara/") + CsnConstants.PREINSCRIPCION_PLANTILLA)
                    );

                    /*
                    List<XWPFHeader> headers = doc.getHeaderList();
                    for (XWPFHeader header : headers) {
                        for (XWPFParagraph p : header.getParagraphs()) {
                            List<XWPFRun> runs = p.getRuns();
                            if (runs != null) {
                                for (XWPFRun r : runs) {
                                    String text = r.getText(0);
                                    logger.warn(text);

                                    if (text != null && text.contains("associate_cui")) {
                                        text = text.replace("associate_cui", associatePreInscription.get(0).getAssociate().getCui().toString());
                                        r.setText(text, 0);
                                    }
                                }
                            }
                        }
                    }
                    logger.info("Parte a");
                    logger.error("CUI " + associatePreInscription.get(0).getAssociate().getCui().toString());
                    for (XWPFParagraph p : doc.getParagraphs()) {
                        List<XWPFRun> runs = p.getRuns();
                        if (runs != null) {
                            for (XWPFRun r : runs) {
                                String text = r.getText(0);
                                logger.warn(text);

                                if (text != null && text.contains("associate_cui")) {
                                    text = text.replace("associate_cui", associatePreInscription.get(0).getAssociate().getCui().toString());
                                    r.setText(text, 0);
                                }


                            }
                        }
                    }
                 */
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

                            //associate membership number
                            if (text != null && text.contains("associate_no")) {
                                text = text.replace("associate_no", associatePreInscription.get(0).getAssociate().getMembershipNumber().toString());
                                bufferrun.setText(text, 0);
                            }

                            //associate place
                            if (text != null && text.contains("place_desc")) {
                                text = text.replace("place_desc", associatePreInscription.get(0).getOrganization().getOrganizationName());
                                bufferrun.setText(text, 0);
                            }

                            //associate date
                            if (text != null && text.contains("date_created")) {
                                text = text.replace("date_created", associatePreInscription.get(0).getAssociate().getDateCreated().toLocalDate().toString());
                                bufferrun.setText(text, 0);
                            }

                            //associate name
                            if (text != null && text.contains("associate_name")) {
                                text = text.replace("associate_name", associatePreInscription.get(0).getAssociate().getNameComplete());
                                bufferrun.setText(text, 0);
                            }

                            //associate cui
                            if (text != null && text.contains("associate_cui")) {
                                text = text.replace("associate_cui", associatePreInscription.get(0).getAssociate().getCui().toString());
                                bufferrun.setText(text, 0);
                            }

                            //associate address
                            if (text != null && text.contains("associate_address")) {
                                String dummyText = "";
                                if (associatePreInscription.get(0).getAssociate().getAddress() != null) {
                                    dummyText = associatePreInscription.get(0).getAssociate().getAddress().getAddressLine();
                                }
                                text = text.replace("associate_address", dummyText);
                                bufferrun.setText(text, 0);
                            }

                            //associate phone
                            if (text != null && text.contains("associate_phone")) {
                                String dummyText = "";
                                if (associatePreInscription.get(0).getAssociate().getPhones().get(0) != null) {
                                    dummyText = associatePreInscription.get(0).getAssociate().getPhones().get(0).getPhone().toString();
                                }
                                text = text.replace("associate_phone", dummyText);
                                bufferrun.setText(text, 0);
                            }

                            //associate profession
                            if (text != null && text.contains("associate_profession_desc")) {
                                text = text.replace("associate_profession_desc", associatePreInscription.get(0).getAssociate().getProfession().getDescription());
                                bufferrun.setText(text, 0);
                            }

                            //associate partner name
                            if (text != null && text.contains("partner_name")) {
                                String dummyText = "";
                                if (associatePreInscription.get(0).getPartner() != null) {
                                    dummyText = associatePreInscription.get(0).getPartner().getNameComplete();
                                }
                                text = text.replace("partner_name", dummyText);
                                bufferrun.setText(text, 0);
                            }

                            //associate beneficiary name
                            if (text != null && text.contains("beneficiary_name")) {
                                String dummyText = "";
                                if (associatePreInscription.get(0).getBeneficiary() != null) {
                                    dummyText = associatePreInscription.get(0).getBeneficiary().getPerson().getNameComplete();
                                }
                                text = text.replace("beneficiary_name", dummyText);
                                bufferrun.setText(text, 0);
                            }


                            //associate entry contribution
                            if (text != null && text.contains("entry_contribution")) {
                                text = text.replace("Q.entry_contribution", "Q." + String.format("%.2f", associatePreInscription.get(0).getOrganization().getEntryContribution()));
                                bufferrun.setText(text, 0);
                            }

                            //associate entry fee
                            if (text != null && text.contains("entry_fee")) {
                                text = text.replace("Q.entry_fee", "Q." + String.format("%.2f", associatePreInscription.get(0).getOrganization().getEntryFee()));
                                bufferrun.setText(text, 0);
                            }

                            obj.set(bufferrun.getCTR());
                        }

                    }


                    File rootDirectory = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);

                    if (!rootDirectory.exists()) {
                        rootDirectory.mkdir();
                    }

                    //logger.warn("root " + rootDirectory.getAbsolutePath() + " write? " + rootDirectory.canWrite());
                    File directory_preinscriptions = new File(rootDirectory.getAbsolutePath() + "/preinscripciones/");
                    if (!directory_preinscriptions.exists()) {
                        directory_preinscriptions.mkdir();
                    }
                    //logger.warn("directory_pre " + directory_preinscriptions.getAbsolutePath() + " write? " + directory_preinscriptions.canWrite());

                    File directory_associate_cui = new File(directory_preinscriptions + "/" + associatePreInscription.get(0).getAssociate().getCui() + "/");
                    if (!directory_associate_cui.exists()) {
                        directory_associate_cui.mkdir();
                    }
                    //logger.warn("direct associate " + directory_associate_cui.getAbsolutePath() + " write? " + directory_associate_cui.canWrite());

                    String outputPath = directory_associate_cui + "/" + associatePreInscription.get(0).getAssociate().getPersonKey().toString() + "-Asociado_Preinscripcion.docx";

                    doc.write(new FileOutputStream(outputPath));

                    logger.info("Path: -----> " + outputPath);

                    File file = new File(outputPath);
                    Response.ResponseBuilder response = Response.ok((Object) file);
                    response.header("Content-Disposition", "attachment; filename =\"" + associatePreInscription.get(0).getAssociate().getPersonKey().toString() + "-Asociado_Preinscripcion.docx\"");
                    return response.build();
                }
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseJson(
                    "FAIL",
                    "CUI not found",
                    0L,
                    "CUI not found",
                    "0"
            )).build();

        } catch (Exception ex) {

            logger.error(ex.getMessage());
            ex.printStackTrace();

            return Response.serverError().build();
        }
    }
}

