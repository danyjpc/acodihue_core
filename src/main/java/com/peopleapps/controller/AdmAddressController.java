package com.peopleapps.controller;

import com.peopleapps.model.AdmAddress;
import com.peopleapps.model.AdmPartner;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmUser;
import com.peopleapps.repository.AdmAddressAccountRepository;
import com.peopleapps.repository.AdmAddressRepository;
import com.peopleapps.repository.AdmPartnerRepository;
import com.peopleapps.repository.AdmUserRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "addresses")
public class AdmAddressController {

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("addressId") @DefaultValue("0") Long addressId,
            @QueryParam("addressAccountId") @DefaultValue("0") Long addressAccountId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmAddress> admAddressList = admAddressRepository.getAllAddresses(
                null, addressAccountId, desc);

        return Response.status(Response.Status.OK).entity(admAddressList).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long addressId
    ) {
        if (addressId == null || addressId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmAddress> admAddressList = admAddressRepository.getAllAddresses(
                addressId, null, null);
        if (admAddressList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admAddressList.get(0)).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmAddress admAddress) {
        try {
            if (admAddress.getAddressAccount() == null
                    || admAddress.getAddressAccount().getAddressAccountId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Address Account Error");
            }

            if (admAddress.getDocumentAcount() == null
                    || admAddress.getDocumentAcount().getDocumentAccountId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Document Account Error");
            }

            String errores = this.validateTypologies(admAddress);
            if (errores != null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, errores + " Error");
            }

            AdmUser createdBy = this.admUserRepository.findByKey(admAddress.getCreatedBy().getPerson().getPersonKey());
            if (createdBy == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User Error");
            }

            admAddress.setCreatedBy(createdBy);

            if (admAddress.getZone() == null || admAddress.getZone().getTypologyId() == null) {
                admAddress.setZone(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));
            }
            if (admAddress.getVillage() == null || admAddress.getVillage().getTypologyId() == null) {
                admAddress.setZone(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));
            }


            admAddress.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));
            admAddress.setDateCreated(CsnFunctions.now());

            admAddress = admAddressRepository.save(admAddress);
            admAddressRepository.resetLeader(admAddress);
            return CsnFunctions.setResponse(admAddress.getAddressId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


    @PUT
    //@JWTTokenNeeded
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("id") Long id, AdmAddress admAddress) {
        if (!id.equals(admAddress.getAddressId()))
            return Response.status(Response.Status.NOT_FOUND).build();

        admAddressRepository.saveEdit(admAddress);
        return Response.status(Response.Status.OK).build();
    }


    private String validateTypologies(AdmAddress admAddress) {
        List<String> valores = new ArrayList<>();

        if (admAddress.getCountry() == null || admAddress.getCountry().getTypologyId() == null)
            valores.add("Country");

        if (admAddress.getState() == null || admAddress.getState().getTypologyId() == null)
            valores.add("State");

        if (admAddress.getCity() == null || admAddress.getCity().getTypologyId() == null)
            valores.add("City");

        //zone and village can be 160000

        if (admAddress.getStatus() == null || admAddress.getStatus().getTypologyId() == null)
            valores.add("Status");

        if (admAddress.getType() == null || admAddress.getType().getTypologyId() == null)
            valores.add("Type");

        if (admAddress.getCreatedBy() == null || admAddress.getCreatedBy().getPerson() == null
                || admAddress.getCreatedBy().getPerson().getPersonKey() == null)
            valores.add("User");

        String respuesta = null;
        if (valores.size() > 0) {
            //List to string and separating using ", "
            respuesta = valores.stream().map(Object::toString).collect(Collectors.joining(", "));
        }

        return respuesta;
    }


}
