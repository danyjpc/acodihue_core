package com.peopleapps.controller;

import com.peopleapps.model.*;
import com.peopleapps.repository.AdmActivityRepository;
import com.peopleapps.repository.AdmAddressRepository;
import com.peopleapps.repository.AdmCreditRepository;
import com.peopleapps.repository.AdmUserRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/credits/v1/")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
@Tag(name = "activities")
public class AdmActivityController {

    @Inject
    AdmActivityRepository admActivityRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;


    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/activity")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID key
    ) {

        AdmCredit admCredit = admCreditRepository.findByKey(key);

        if (admCredit == null)
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

        List<AdmActivity> admActivties = admActivityRepository.getAll(admCredit.getActivityAccount().getActivityAccountId(), null);

        return Response.status(Response.Status.OK).entity(admActivties).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/activity/{id}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID key,
            @PathParam("id") @DefaultValue("0") Long id
    ) {
        AdmCredit admCredit = admCreditRepository.findByKey(key);

        if (admCredit == null)
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

        List<AdmActivity> admActivties = admActivityRepository.getAll(admCredit.getActivityAccount().getActivityAccountId(), id);

        if (admActivties.size() > 0)
            return Response.status(Response.Status.OK).entity(admActivties.get(0)).build();

        return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "No Encontrado");
    }

    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/activity")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(
            @RequestBody AdmActivity admActivity,
            @PathParam("key") UUID key
    ) {

        try {

            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            AdmUser admUser = admUserRepository.findByKey(admActivity.getCreatedBy().getPersonKey());


            if (admUser == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "usuario no encontrado");

            admActivity.setActivityAccount(admCredit.getActivityAccount());
            admActivity.setDateCreated(CsnFunctions.now());
            admActivity.setCreatedBy(admUser);

            admActivityRepository.save(admActivity);

            return CsnFunctions.setResponse(admActivity.getActivityId(), Response.Status.CREATED, "Actividad Creada");

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "error al insertar");
        }
    }


    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/activity/{id}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(
            @RequestBody AdmActivity admActivity,
            @PathParam("key") UUID key,
            @PathParam("id") @DefaultValue("0") Long id
    ) {

        try {

            if (!id.equals(admActivity.getActivityId()))
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Actividad no coincide");

            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            admActivityRepository.edit(admActivity);

            return CsnFunctions.setResponse(id, Response.Status.OK, "actividad actualizada");

        } catch (Exception ex) {

            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "error al actualizar");
        }
    }

}
