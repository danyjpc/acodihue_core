package com.peopleapps.controller;

import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import com.peopleapps.util.PreRegRolesEnum;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Path("/associate/v1")
@Tag(name = "associate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmAssociateController {

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    Logger logger;


    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAssociates(
            @QueryParam("departamento") @DefaultValue("0") Long stateId,
            @QueryParam("municipio") @DefaultValue("0") Long cityId,
            @QueryParam("date_ini") String dateIni,
            @QueryParam("date_end") String dateEnd,
            @QueryParam("name") String name,
            @QueryParam("cui") Long cui,
            @QueryParam("membership_number") Long membershipNumber,
            @QueryParam("descending") @DefaultValue("true") Boolean desc

    ) {
        List<AdmPreinscripcionDto> admPreinscripcionDtos = admPersonRepository.getAssociates(stateId, cityId, dateIni, dateEnd, name, cui, membershipNumber, desc, null, null);
        if (admPreinscripcionDtos.size() >= 1) {
            return Response.status(Response.Status.OK).entity(admPreinscripcionDtos).build();
        }
        return Response.status(Response.Status.OK).entity(admPreinscripcionDtos).build();
    }

    //return single associate by key
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getByAssociateKey(
            @PathParam("key") UUID associateKey

    ) {
        List<AdmPreinscripcionDto> admPreinscripcionDtos = admPersonRepository.getAssociates(
                null, null, null, null, null,
                null, null, null, associateKey, null);
        if (admPreinscripcionDtos.size() >= 1) {
            return Response.status(Response.Status.OK).entity(admPreinscripcionDtos.get(0)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

