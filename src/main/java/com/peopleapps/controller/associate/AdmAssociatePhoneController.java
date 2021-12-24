package com.peopleapps.controller.associate;

import com.peopleapps.dto.inputs.associate.AdmAssociateAddressDto;
import com.peopleapps.dto.inputs.associate.AdmAssociatePhoneDto;
import com.peopleapps.model.*;
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
public class AdmAssociatePhoneController {

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    Logger logger;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/phone")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID personKey,
            @QueryParam("estado_telefono") @DefaultValue("0") Long phoneStatus,
            @QueryParam("descending") @DefaultValue("true") Boolean desc

    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPhone> admPhoneList = admPhoneRepository.getAllPhonesByAssociate(
                personKey, null, phoneStatus, desc);

        return Response.status(Response.Status.OK).entity(admPhoneList).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/phone/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID personKey,
            @PathParam("id") @DefaultValue("0") Long phoneId
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (phoneId == null || phoneId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPhone> admPhoneList = admPhoneRepository.getAllPhonesByAssociate(
                personKey, phoneId, null, null);

        if (admPhoneList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admPhoneList.get(0)).build();

    }

    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/phone/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(@PathParam("key") UUID personKey,
                           AdmAssociatePhoneDto phoneDto) {
        try {

            AdmPerson associate = this.admPersonRepository.findByKey(personKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            if (phoneDto.getCreatedBy() == null || phoneDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(phoneDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            if (phoneDto.getPhone() == null || phoneDto.getType() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incomplete data");
            }
            AdmTypology type = this.admTypologyRepository.findBy(phoneDto.getType().getTypologyId());
            if (type == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect state");
            }


            AdmTypology status = null;
            if (phoneDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(phoneDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            AdmPhone phone = new AdmPhone(
                    0L,
                    associate.getPhoneAccount(),
                    phoneDto.getPhone(),
                    status,
                    type,
                    createdByUser,
                    CsnFunctions.now(),
                    phoneDto.getLeader() != null && phoneDto.getLeader()
            );

            phone = admPhoneRepository.save(phone);
            admPhoneRepository.resetLeader(phone);
            return CsnFunctions.setResponse(phone.getPhoneId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/phone/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response update(@PathParam("key") UUID personKey,
                           @PathParam("id") @DefaultValue("0") Long phoneId,
                           AdmAssociatePhoneDto phoneDto) {
        try {
            if (phoneDto.getPhoneId() == null || !phoneDto.getPhoneId().equals(phoneId)) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect ID");
            }
            AdmPerson associate = this.admPersonRepository.findByKey(personKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmPhone currentPhone = this.admPhoneRepository.findBy(phoneDto.getPhoneId());
            if (currentPhone == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Address not found");
            }

            if (phoneDto.getType() != null && phoneDto.getType().getTypologyId() != null) {
                AdmTypology type = this.admTypologyRepository.findBy(phoneDto.getType().getTypologyId());
                currentPhone.setType(type);
            }

            if (phoneDto.getPhone() != null) {
                currentPhone.setPhone(phoneDto.getPhone());
            }

            AdmTypology status = null;
            if (phoneDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(phoneDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }
            currentPhone.setStatus(status);

            if (phoneDto.getLeader() != null) {
                currentPhone.setLeader(phoneDto.getLeader());
            }

            currentPhone = admPhoneRepository.save(currentPhone);
            admPhoneRepository.resetLeader(currentPhone);
            return CsnFunctions.setResponse(currentPhone.getPhoneId(),
                    Response.Status.OK, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

}

