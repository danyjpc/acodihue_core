package com.peopleapps.controller;


import com.peopleapps.controller.util.ParamsPaginated;
import com.peopleapps.model.AdmPhrase;
import com.peopleapps.repository.AdmPhraseRepository;
import com.peopleapps.security.RolesEnum;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/phrases")
@Tag(name = "phrases")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdmPhraseController {

    @Inject
    AdmPhraseRepository admPhraseRepository;

    @Inject
    Logger logger;

    @GET
    public Response findAll(
            @Valid @BeanParam ParamsPaginated paramsPaginated,
            @QueryParam("author") @DefaultValue("%") String author,
            @QueryParam("phrase") @DefaultValue("%") String phrase) {

        try {
            return Response.ok().entity(admPhraseRepository.getAll()).build();
        } catch (Exception ex) {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public AdmPhrase get(@PathParam("id") Long id) {
        return admPhraseRepository.findBy(id);
    }

    @POST
    public Response create(@Valid AdmPhrase admPhrase) {
        admPhraseRepository.save(admPhrase);
        return Response.created(UriBuilder.fromResource(AdmPhraseController.class)
                .path(admPhrase.getPhraseId().toString()).build()).build();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id,
                           AdmPhrase admPhrase) {
        if (!id.equals(admPhrase.getPhraseId()))
            return Response.status(Response.Status.NOT_FOUND).build();
        admPhraseRepository.saveEdit(admPhrase);
        return Response.ok(admPhrase).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response delete(@PathParam("id") Long id) {
        AdmPhrase updatedEntity = admPhraseRepository.findBy(id);
        if (updatedEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        admPhraseRepository.attachAndRemove(updatedEntity);
        return Response.ok().build();
    }

}