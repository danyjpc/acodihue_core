package com.peopleapps.controller.credit;

import com.peopleapps.dto.document.AdmDocumentDto;
import com.peopleapps.dto.inputs.associate.AdmAssociateDocumentDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.AdmCreditRepository;
import com.peopleapps.repository.AdmDocumentRepository;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.repository.AdmUserRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Path("/credits/v1")
@Tag(name = "document by credit")
@RequestScoped
public class AdmCreditDocumentController {

    @Inject
    AdmDocumentRepository admDocumentRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    Logger logger;


    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/documents")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getALl(@PathParam("key") UUID creditKey,
                           @QueryParam("descending") @DefaultValue("true") Boolean desc) {

        try {

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
            }


            List<AdmAssociateDocumentDto> documentDtos = admDocumentRepository.getAllDocuments(
                    CsnConstants.ZERO,
                    credit.getDocumentAccount().getDocumentAccountId(), desc

            ).stream().map(item -> {
                        AdmAssociateDocumentDto admAssociateDocumentDto = new AdmAssociateDocumentDto(
                                item.getDocumentId(),
                                item.getPath(),
                                new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                        item.getDocumentCreditType().getTypologyId(),
                                        item.getDocumentCreditType().getDescription()
                                ),
                                new AdmAssociateDocumentDto.AdmAssociateCreatedByDto(
                                        item.getCreatedBy().getPersonKey()
                                ),
                                item.getLeader(),
                                new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                        item.getStatus().getTypologyId(),
                                        item.getStatus().getDescription()
                                ),
                                item.getDocumentName(),
                                item.getDateCreated(),
                                null
                        );
                        return admAssociateDocumentDto;
                    }
            ).collect(Collectors.toList());

            //Setting relative path
            for (AdmAssociateDocumentDto dto : documentDtos) {
                StringBuilder strB = new StringBuilder();
                strB.append("acodihue-core/rest/credits/v1/").append(creditKey).append("/documents/");
                strB.append(dto.getDocumentId()).append("/pdf");
                dto.setRelativePath(strB.toString());
            }

            return Response.status(Response.Status.OK).entity(documentDtos).build();

        } catch (Exception ex) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error in query");
        }
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/documents/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getALl(@PathParam("key") UUID creditKey, @PathParam("id") Long id) {

        try {

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
            }


            return Response.status(Response.Status.OK).entity(admDocumentRepository.getAllDocuments(
                    id,
                    CsnConstants.ZERO, null

            ).stream().findFirst().map(
                    item -> {
                        AdmAssociateDocumentDto admassociate = new AdmAssociateDocumentDto(
                                item.getDocumentId(),
                                item.getPath(),
                                new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                        item.getDocumentCreditType().getTypologyId(),
                                        item.getDocumentCreditType().getDescription()
                                ),
                                new AdmAssociateDocumentDto.AdmAssociateCreatedByDto(
                                        item.getCreatedBy().getPersonKey()
                                ),
                                item.getLeader(),
                                new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                        item.getStatus().getTypologyId(),
                                        item.getStatus().getDescription()
                                ),
                                item.getDocumentName(),
                                item.getDateCreated(),
                                null
                        );
                        return admassociate;
                    }
            )).build();


        } catch (Exception ex) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error in query");
        }

    }

    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/documents/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("key") UUID creditKey,
                           @PathParam("id") @DefaultValue("0") Long id,
                           AdmAssociateDocumentDto documentDto) {


        if (!id.equals(documentDto.getDocumentId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        AdmCredit credit = admCreditRepository.findByKey(creditKey);
        if (credit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
        }


        admDocumentRepository.saveEdit(new AdmDocument(
                        documentDto.getDocumentId(),
                        new AdmDocumentAccount(
                                credit.getDocumentAccount().getDocumentAccountId()
                        ),
                        null,
                        null,
                        null,
                        new AdmTypology(
                                documentDto.getStatus().getTypologyId()
                        ),
                        documentDto.getLeader(),
                        new AdmTypology(
                                documentDto.getType().getTypologyId()
                        ),
                        documentDto.getDocumentName()
                )
        );

        return CsnFunctions.setResponse(documentDto.getDocumentId(),
                Response.Status.OK, null);

    }


    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/documents/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(
            @Context HttpServletRequest request,
            @QueryParam("name") String document_name,
            @QueryParam("type") Long document_type,
            @QueryParam("created_by") UUID created_by,
            @PathParam("key") UUID creditKey
    ) {
        try {

            Collection<Part> files = request.getParts();

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
            }

            if (credit != null) {
                logger.info("A----------");
                AdmUser createdBy = admUserRepository.findByKey(created_by);

                if (createdBy != null) {
                    logger.info("B----------");

                    for (Part file : files) {

                        InputStream fileContent = file.getInputStream();

                        String path = CsnFunctions.saveFile(
                                fileContent,
                                file.getSubmittedFileName(),
                                CsnConstants.DIRECTORY_ASSOCIATE_DOC,
                                creditKey.toString()
                        );

                        if (path != null) {

                            logger.info("C----------");
                            AdmDocument document = admDocumentRepository.createDocument(new AdmDocument(
                                            CsnConstants.ZERO,
                                            new AdmDocumentAccount(
                                                    credit.getDocumentAccount().getDocumentAccountId()
                                            ),
                                            path,
                                            createdBy,
                                            CsnFunctions.now(),
                                            new AdmTypology(
                                                    CsnConstants.STATUS_ACTIVE
                                            ),
                                            Boolean.FALSE,
                                            new AdmTypology(
                                                    document_type
                                            ),
                                            document_name
                                    )
                            );
                            return CsnFunctions.setResponse(document.getDocumentId(), Response.Status.CREATED, "success");
                        }

                    }
                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "No created");

                } else {
                    return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found");
                }
            } else {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/documents/{id:[0-9][0-9]*}/pdf")
    @Produces({"application/pdf"})
    public Response getFilePdf(
            @PathParam("key") UUID creditKey,
            @PathParam("id") @DefaultValue("0") Long id
    ) {
        try {
            if (id == null || id == 0)
                return Response.status(Response.Status.BAD_REQUEST).entity("please send id").build();

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
            }

            List<AdmDocumentDto> admDocumentDtos = admDocumentRepository.getAllDocuments(id, null, null);

            if (admDocumentDtos.size() > 0) {

                StringBuilder mainPath = new StringBuilder();
                mainPath.append(admDocumentDtos.get(0).getPath());

                try {

                    File file = new File(mainPath.toString());

                    logger.info("document name====" + file.getName());
                    logger.info(mainPath.toString());

                    //checking file extension
                    String[] pieces = file.getName().split("\\.", 2);
                    if (pieces.length > 1) {
                        logger.error(pieces[1]);
                        if (pieces[1].equals("jpg") ||
                                pieces[1].equals("png") ||
                                pieces[1].equals("jpeg")) {

                            return Response.ok(file, "image/jpg").header("Inline",
                                    "filename=\"" + file.getName() + "\"").build();
                        } else if (pieces[1].equals("pdf")) {
                            return Response.ok(file, "application/pdf").header("Inline",
                                    "filename=\"" + file.getName() + "\"").build();
                        }
                    }

                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect file extension");

                } catch (Exception ex) {
                    logger.info(ex.getMessage());
                    return Response.status(Response.Status.NOT_FOUND).build();
                }

            } else {

                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error in query");
        }

    }

}
