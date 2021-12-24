package com.peopleapps.controller;

import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.AdmCreditLineRepository;
import com.peopleapps.repository.AdmOrganizationRepository;
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

@Path("/creditLines/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "creditLines")
public class AdmCreditLineController {

    @Inject
    AdmCreditLineRepository admCreditLineRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("organization") @DefaultValue("0") Long organizationId,
            @QueryParam("status") @DefaultValue("0") Long statusId
    ) {

        List<AdmCreditLineDto> admCreditLineDtos = admCreditLineRepository.getAllCreditLines(
                null, organizationId, desc, statusId);

        return Response.status(Response.Status.OK).entity(admCreditLineDtos).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long creditLineId
    ) {
        if (creditLineId == null || creditLineId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmCreditLineDto> admCreditLineDtos = admCreditLineRepository.getAllCreditLines(
                creditLineId, null, null, null);

        if (admCreditLineDtos.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admCreditLineDtos.get(0)).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmCreditLineDto admCreditLineDto) {
        try {
            if (admCreditLineDto.getOrganization() == null
                    || admCreditLineDto.getOrganization().getOrganizationKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Organization is mandatory");
            }

            if (admCreditLineDto.getCreatedBy() == null || admCreditLineDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmOrganization organization = admOrganizationRepository.getByKey(admCreditLineDto.getOrganization().getOrganizationKey());
            if (organization == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Organization not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(admCreditLineDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmTypology status = null;
            if (admCreditLineDto.getStatus() != null && admCreditLineDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(admCreditLineDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            AdmCreditLine creditLine = new AdmCreditLine(
                    CsnConstants.ZERO,
                    organization,
                    admCreditLineDto.getDescription() == null ? CsnConstants.DEFAULT_TEXT_SD : admCreditLineDto.getDescription(),
                    createdByUser,
                    CsnFunctions.now(),
                    status
            );

            admCreditLineRepository.save(creditLine);
            return CsnFunctions.setResponse(creditLine.getCreditLineId(),
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
    public Response edit(@PathParam("id") Long id,
                         AdmCreditLineDto admCreditLineDto) {
        if (!id.equals(admCreditLineDto.getCreditLineId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        AdmCreditLine currentCreditLine = this.admCreditLineRepository.findBy(id);
        if (currentCreditLine == null) {
            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit Line not found");
        }

        if (admCreditLineDto.getStatus() != null
                && admCreditLineDto.getStatus().getTypologyId() != null) {
            AdmTypology status = admTypologyRepository.findBy(admCreditLineDto.getStatus().getTypologyId());

            currentCreditLine.setStatus(status);
        }

        if (admCreditLineDto.getDescription() != null) {
            currentCreditLine.setDescription(admCreditLineDto.getDescription());
        }
        admCreditLineRepository.save(currentCreditLine);
        return Response.status(Response.Status.OK).build();
    }

}
