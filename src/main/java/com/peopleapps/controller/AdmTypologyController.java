package com.peopleapps.controller;


import com.peopleapps.controller.util.Paginated;
import com.peopleapps.controller.util.ParamsPaginated;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.repository.AdmTypologyRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/typologies")
@Tag(name = "typologies ")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdmTypologyController {

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @APIResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = CsnConstants.MEDIA_TYPE,
                    schema = @Schema(
                            implementation = AdmTypology.class
                    )
            )
    )
    @Paginated
    public Response findAll(
            //@Valid @BeanParam ParamsPaginated paramsPaginated,
            @QueryParam("typologyId") @DefaultValue("100") Long typologyId,
            @QueryParam("parentTypologyId") Long parentTypologyId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmTypologyDto> admTypologyDtos =
                admTypologyRepository.getAllTypologies(typologyId, null, null, null, null, null, desc);
        return Response.status(Response.Status.OK).entity(admTypologyDtos).build();
    }

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @APIResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = CsnConstants.MEDIA_TYPE,
                    schema = @Schema(
                            implementation = AdmTypology.class
                    )
            )
    )
    @Path("/{id:[0-9][0-9]*}")
    public Response get(@PathParam("id") Long typologyId) {
        List<AdmTypologyDto> admTypologyDtos = admTypologyRepository.getAllTypologies(typologyId, null, null, null, null, null, null);
        if (admTypologyDtos.size() >= 1) {
            return Response.status(Response.Status.OK).entity(admTypologyDtos.get(0)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @APIResponse(
            responseCode = "200",
            description = "OK"
    )
    public Response create(
            @RequestBody(
                    description = "you can create new typologies ",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = AdmTypology.class
                            )
                    )
            ) AdmTypology admTypology) {
        try {

            //sets typology object from id
            if(admTypology.getValue3() == null){
                admTypology.setValue3("%");
            }
            admTypologyRepository.save(admTypology);
            return CsnFunctions.setResponse(admTypology.getTypologyId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @APIResponse(
            responseCode = "200",
            description = "OK"
    )
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id,
                           @RequestBody(
                                   description = "you can update only the columns that you want ",
                                   content = @Content(
                                           mediaType = "application/json",
                                           schema = @Schema(
                                                   implementation = AdmTypology.class
                                           )
                                   )
                           ) AdmTypology admTypology) {
        if (!id.equals(admTypology.getTypologyId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        admTypologyRepository.saveEdit(admTypology);
        return Response.ok().build();
    }
}
