package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.model.AdmPhone;
import com.peopleapps.model.AdmPhoneAccount;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmUser;
import com.peopleapps.repository.AdmPhoneAccountRepository;
import com.peopleapps.repository.AdmPhoneRepository;
import com.peopleapps.repository.AdmTypologyRepository;
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
import java.util.List;

@Path("/phones")
@Tag(name = "phones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmPhoneController {

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;


    @Inject
    Logger logger;


    @Inject
    ResponseJson responseJson;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("accountId") @DefaultValue("0") Long phoneAccountId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc

    ) {
        try {
            List<AdmPhone> admPhones = admPhoneRepository.getAllPhones(
                    0L, phoneAccountId, desc
            );

            return Response.status(Response.Status.OK).entity(admPhones).build();

        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getPhoneById(
            @PathParam("id") @DefaultValue("0") Long phoneId
    ) {

        if (phoneId == null || phoneId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            List<AdmPhone> admPhones = admPhoneRepository.getAllPhones(
                    phoneId, 0L, null
            );

            if (admPhones.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();


            return Response.status(Response.Status.OK).entity(admPhones.get(0)).build();
        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response createPhone(AdmPhone admPhone) {
        try {
            if (admPhone.getPhoneAccount() == null || admPhone.getStatus() == null
                    || admPhone.getType() == null || admPhone.getCreatedBy().getPerson() == null
                    || admPhone.getCreatedBy().getPerson().getPersonKey() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            AdmPhoneAccount phoneAccount = this.admPhoneAccountRepository.findBy(admPhone.getPhoneAccount().getPhoneAccountId());
            if (phoneAccount == null) {
                return CsnFunctions.setResponse(admPhone.getPhoneAccount().getPhoneAccountId(),
                        Response.Status.BAD_REQUEST, "Incorrect phone account");
            }

            AdmTypology phoneType = this.admTypologyRepository.findBy(admPhone.getType().getTypologyId());
            if (phoneType == null) {
                return CsnFunctions.setResponse(admPhone.getType().getTypologyId(),
                        Response.Status.BAD_REQUEST, "Incorrect type");
            }

            AdmTypology phoneStatus = this.admTypologyRepository.findBy(admPhone.getType().getTypologyId());
            if (phoneStatus == null) {
                //for incorrect status, default status is provided (active)
                phoneStatus = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmUser createdBy = this.admUserRepository.findByKey(admPhone.getCreatedBy().getPerson().getPersonKey());
            if (createdBy == null) {
                return CsnFunctions.setResponse(0L,
                        Response.Status.BAD_REQUEST, "Incorrect user");
            }

            admPhone.setPhoneAccount(phoneAccount);
            admPhone.setStatus(phoneStatus);
            admPhone.setType(phoneType);
            admPhone.setCreatedBy(createdBy);
            admPhone.setDateCreated(CsnFunctions.now());

            admPhone = admPhoneRepository.save(admPhone);
            admPhoneRepository.resetLeader(admPhone);
            return CsnFunctions.setResponse(admPhone.getPhoneId(),
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
    public Response edit(@PathParam("id") Long id, AdmPhone admPhone) {
        if (!id.equals(admPhone.getPhoneId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        AdmPhone phone = this.admPhoneRepository.findBy(admPhone.getPhoneId());
        if (phone == null) {
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, "Phone ID not found");
        }
        admPhoneRepository.saveEdit(admPhone);
        return Response.status(Response.Status.OK).build();
    }
}
