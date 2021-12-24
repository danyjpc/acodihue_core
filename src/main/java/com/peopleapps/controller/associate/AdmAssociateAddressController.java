package com.peopleapps.controller.associate;

import com.peopleapps.dto.inputs.associate.AdmAssociateAddressDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.model.AdmAddress;
import com.peopleapps.model.AdmPerson;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Path("/associate/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "addresses by associate")
public class AdmAssociateAddressController {

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/address/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID personKey,
            @QueryParam("estado_direccion") @DefaultValue("0") Long addressStatus,
            @QueryParam("descending") @DefaultValue("true") Boolean desc

    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmAddress> admAddressList = admAddressRepository.getAllAddressesByAssociate(
                personKey, null, addressStatus, desc);

        return Response.status(Response.Status.OK).entity(admAddressList).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/address/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID personKey,
            @PathParam("id") @DefaultValue("0") Long addressId
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (addressId == null || addressId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmAddress> admAddressList = admAddressRepository.getAllAddressesByAssociate(
                personKey, addressId, null, null);
        if (admAddressList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admAddressList.get(0)).build();

    }

    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/address/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(@PathParam("key") UUID personKey,
                           AdmAssociateAddressDto addressDto) {
        try {

            AdmPerson associate = this.admPersonRepository.findByKey(personKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            if (addressDto.getCreatedBy() == null || addressDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(addressDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            if (addressDto.getState() == null || addressDto.getCity() == null
                    || addressDto.getVillage() == null || addressDto.getZone() == null
                    || addressDto.getAddressLine() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incomplete data");
            }
            AdmTypology state = this.admTypologyRepository.findBy(addressDto.getState().getTypologyId());
            if (state == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect state");
            }

            AdmTypology city = this.admTypologyRepository.findBy(addressDto.getCity().getTypologyId());
            if (city == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect city");
            }
            AdmTypology village = this.admTypologyRepository.findBy(addressDto.getVillage().getTypologyId());
            if (village == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect village");
            }
            AdmTypology zone = this.admTypologyRepository.findBy(addressDto.getZone().getTypologyId());
            if (zone == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect zone");
            }

            AdmTypology status = null;
            if (addressDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(addressDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmAddress address = new AdmAddress(
                    0L,
                    associate.getAddressAccount(),
                    addressDto.getAddressLine(),
                    CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    state,
                    city,
                    zone,
                    status,
                    new AdmTypology(CsnConstants.ADDRESS_TYPE_HOME), //address type
                    createdByUser,
                    CsnFunctions.now(),
                    addressDto.getLeader() != null && addressDto.getLeader(),
                    CsnConstants.ZERO,
                    CsnConstants.ZERO,
                    CsnConstants.ZERO,
                    CsnConstants.DEFAULT_TEXT_SD,
                    associate.getDocumentAccount(),
                    village,
                    CsnConstants.DEFAULT_TEXT_SD
            );
            address = admAddressRepository.save(address);
            admAddressRepository.resetLeader(address);
            return CsnFunctions.setResponse(address.getAddressId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/address/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response update(@PathParam("key") UUID personKey,
                           @PathParam("id") @DefaultValue("0") Long addressId,
                           AdmAssociateAddressDto addressDto) {
        try {
            if (addressDto.getAddressId() == null || !addressDto.getAddressId().equals(addressId)) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect ID");
            }
            AdmPerson associate = this.admPersonRepository.findByKey(personKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmAddress currentAddress = this.admAddressRepository.findBy(addressDto.getAddressId());
            if (currentAddress == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Address not found");
            }

            if (addressDto.getState() != null || addressDto.getState().getTypologyId() != null) {
                AdmTypology state = this.admTypologyRepository.findBy(addressDto.getState().getTypologyId());
                currentAddress.setState(state);
            }

            if (addressDto.getCity() != null || addressDto.getCity().getTypologyId() != null) {
                AdmTypology city = this.admTypologyRepository.findBy(addressDto.getCity().getTypologyId());
                currentAddress.setCity(city);
            }
            if (addressDto.getVillage() != null || addressDto.getVillage().getTypologyId() != null) {
                AdmTypology village = this.admTypologyRepository.findBy(addressDto.getVillage().getTypologyId());
                currentAddress.setVillage(village);
            }
            if (addressDto.getZone() != null || addressDto.getZone().getTypologyId() != null) {
                AdmTypology zone = this.admTypologyRepository.findBy(addressDto.getZone().getTypologyId());
                currentAddress.setZone(zone);
            }

            if (addressDto.getAddressLine() != null) {
                currentAddress.setAddressLine(addressDto.getAddressLine());
            }

            AdmTypology status = null;
            if (addressDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(addressDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }
            currentAddress.setStatus(status);

            if (addressDto.getLeader() != null) {
                currentAddress.setLeader(addressDto.getLeader());
            }


            currentAddress = admAddressRepository.save(currentAddress);
            admAddressRepository.resetLeader(currentAddress);
            return CsnFunctions.setResponse(currentAddress.getAddressId(),
                    Response.Status.OK, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

}

