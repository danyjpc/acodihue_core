package com.peopleapps.controller;

import com.peopleapps.dto.document.AdmDocumentDto;
import com.peopleapps.model.AdmDocument;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.repository.AdmDocumentRepository;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "documents")
public class AdmDocumentController {

    @Inject
    AdmDocumentRepository admDocumentRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("document_id") @DefaultValue("0") Long documentId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc) {

        try {
            List<AdmDocumentDto> admDocumentList = admDocumentRepository.getAllDocuments(
                    documentId, CsnConstants.ZERO, desc);
            return Response.status(Response.Status.OK).entity(admDocumentList).build();

        } catch (Exception ex) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error in query");
        }

    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long documentId
    ) {
        if (documentId == null || documentId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            List<AdmDocumentDto> admDocumentList = admDocumentRepository.getAllDocuments(CsnConstants.ZERO,
                    documentId, null);

            if (admDocumentList.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity(admDocumentList.get(0)).build();
        } catch (Exception ex) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error in query");
        }

    }


    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmDocument admDocument) {
        try {
            admDocumentRepository.createDocument(admDocument);
            return CsnFunctions.setResponse(admDocument.getDocumentId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("id") Long id, AdmDocument admDocument) {
        if (!id.equals(admDocument.getDocumentId())) return Response.status(Response.Status.NOT_FOUND).build();
        admDocumentRepository.saveEdit(admDocument);
        return Response.status(Response.Status.OK).build();
    }

}
