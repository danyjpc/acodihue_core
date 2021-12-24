package com.peopleapps.controller.associate.beneficiary;

import com.peopleapps.model.AdmPhone;
import com.peopleapps.repository.AdmAddressRepository;
import com.peopleapps.repository.AdmPhoneRepository;
import com.peopleapps.security.RolesEnum;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


@Path("/associate/beneficiary/phones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "addresses by associate")
public class AdmAssociateBeneficiaryPhoneController {


    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID personKey,
            @QueryParam("estado_telefono") @DefaultValue("0") Long phoneStatus
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPhone> admPhoneList = admPhoneRepository.getAllBeneficiaryPhonesByAssociate(
                personKey, null, phoneStatus);

        return Response.status(Response.Status.OK).entity(admPhoneList).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID personKey,
            @PathParam("id") @DefaultValue("0") Long phoneId
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (phoneId == null || phoneId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPhone> admPhoneList = admPhoneRepository.getAllBeneficiaryPhonesByAssociate(
                personKey, phoneId, null);

        if (admPhoneList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admPhoneList.get(0)).build();

    }

}

