package com.peopleapps.controller;


import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.model.AdmPerson;

import com.peopleapps.repository.AdmPersonRepository;
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
import java.util.UUID;

@Path("/persons/v1")
@Tag(name = "persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmPersonController {

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(@QueryParam("descending") @DefaultValue("true") Boolean desc) {

        List<AdmPersonDto> admPersonList = admPersonRepository.getAllPerson(
                null, desc);

        return Response.status(Response.Status.OK).entity(admPersonList).build();
    }

    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getByKey(
            @PathParam("key") UUID personKey
    ) {
        if (personKey == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPersonDto> admPersonList = admPersonRepository.getAllPerson(
                personKey, null);
        if (admPersonList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admPersonList.get(0)).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmPerson admPerson) {
        try {
            admPersonRepository.createPerson(admPerson);
            return CsnFunctions.setResponse(admPerson.getPersonKey(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("key") UUID personKey, AdmPerson admPerson) {
        if (!personKey.equals(admPerson.getPersonKey())) return Response.status(Response.Status.NOT_FOUND).build();
        AdmPerson person = admPersonRepository.editCustom(admPerson);

        return CsnFunctions.setResponse(person.getPersonKey(),
                Response.Status.OK, "Updated");
    }
}
