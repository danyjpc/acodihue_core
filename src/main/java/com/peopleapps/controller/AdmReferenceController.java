package com.peopleapps.controller;

import com.peopleapps.dto.reference.AdmReferenceDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.model.AdmReference;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.repository.AdmReferenceRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/references/v1")
@Tag(name = "references")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmReferenceController {

    @Inject
    AdmReferenceRepository admReferenceRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("referenceId") @DefaultValue("0") Long referenceId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmReferenceDto> admReferenceList = admReferenceRepository.getAllReferencesByReferenceId(
                referenceId, desc);

        return Response.status(Response.Status.OK).entity(admReferenceList).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long referenceId
    ) {
        if (referenceId == null || referenceId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmReferenceDto> admReferenceList = admReferenceRepository.getAllReferencesByReferenceId(
                referenceId, true);
        if (admReferenceList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admReferenceList.get(0)).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmReference admReference) {
        try {
            admReferenceRepository.createReference(admReference);
            return CsnFunctions.setResponse(admReference.getReferenceId(),
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
    public Response edit(@PathParam("id") Long id, AdmReference admReference) {
        if (!id.equals(admReference.getReferenceId()) || admReference.getReferenceId() == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        AdmReference ref = this.admReferenceRepository.findBy(admReference.getReferenceId());
        if(ref == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        admReferenceRepository.editCustom(admReference);
        return Response.status(Response.Status.OK).build();
    }
}

