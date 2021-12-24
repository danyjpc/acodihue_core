package com.peopleapps.controller.associate;

import com.peopleapps.dto.associationResponsible.AdmAssociationResponsibleDto;
import com.peopleapps.dto.inputs.associate.AdmAssociatePhoneDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.model.AdmPhone;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmUser;
import com.peopleapps.repository.*;
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
import java.util.UUID;


@Path("/associate/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "phones by associate")
public class AdmAssociateAssociationController {

    @Inject
    AdmAssociationResponsibleRepository admAssociationResponsibleRepository;

    @Inject
    Logger logger;



    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/associations")
    public Response getAll(
            @PathParam("key") UUID associateKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("status") @DefaultValue("0") Long status
    ) {

        List<AdmAssociationResponsibleDto> admAssociationResponsibleDtos =
                admAssociationResponsibleRepository.getAllAssociationResponsibles(
                        null, null, desc, status, associateKey);

        return Response.status(Response.Status.OK).entity(admAssociationResponsibleDtos).build();
    }
}

