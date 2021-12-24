package com.peopleapps.controller.associate.beneficiary;

import com.peopleapps.model.AdmAddress;
import com.peopleapps.repository.AdmAddressAccountRepository;
import com.peopleapps.repository.AdmAddressRepository;
import com.peopleapps.repository.AdmUserRepository;
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


@Path("/associate/beneficiary/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "addresses by associate")
public class AdmAssociateBeneficiaryAddressController {

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID personKey,
            @QueryParam("estado_direccion") @DefaultValue("0") Long addressStatus
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmAddress> admAddressList = admAddressRepository.getAllBeneficiaryAddressesByAssociate(
                personKey, null, addressStatus);

        return Response.status(Response.Status.OK).entity(admAddressList).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID personKey,
            @PathParam("id") @DefaultValue("0") Long addressId
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (addressId == null || addressId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmAddress> admAddressList = admAddressRepository.getAllBeneficiaryAddressesByAssociate(
                personKey, addressId, null);

        if (admAddressList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admAddressList.get(0)).build();

    }

}

