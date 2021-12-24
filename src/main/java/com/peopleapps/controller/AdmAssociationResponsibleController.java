package com.peopleapps.controller;

import com.peopleapps.dto.associationResponsible.AdmAssociationResponsibleDto;
import com.peopleapps.dto.credit.AdmCreditDto;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Path("/association_responsibles/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "association_responsible")
public class AdmAssociationResponsibleController {

    @Inject
    AdmAssociationResponsibleRepository admAssociationResponsibleRepository;

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("status") @DefaultValue("0") Long status
    ) {

        List<AdmAssociationResponsibleDto> admAssociationResponsibleDtos =
                admAssociationResponsibleRepository.getAllAssociationResponsibles(
                        null, null, desc, status, null);

        return Response.status(Response.Status.OK).entity(admAssociationResponsibleDtos).build();
    }
/*
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long associationResponsibleId
    ) {
        if (associationResponsibleId == null || associationResponsibleId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();


        List<AdmAssociationResponsibleDto> admAssociationResponsibleDtos =
                admAssociationResponsibleRepository.getAllAssociationResponsibles(
                        associationResponsibleId,null, null, null);

        if (admAssociationResponsibleDtos.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admAssociationResponsibleDtos.get(0)).build();

    }

 */

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID organizationKey
    ) {
        List<AdmAssociationResponsibleDto> admAssociationResponsibleDtos =
                admAssociationResponsibleRepository.getAllAssociationResponsibles(
                        null, organizationKey, null, null, null);

        if (admAssociationResponsibleDtos.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admAssociationResponsibleDtos).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmAssociationResponsibleDto admAssociationResponsibleDto) {
        try {
            if (admAssociationResponsibleDto.getOrganization() == null
                    || admAssociationResponsibleDto.getOrganization().getOrganizationKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Organization is mandatory");
            }

            if (admAssociationResponsibleDto.getPerson() == null
                    || admAssociationResponsibleDto.getPerson().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Associate is mandatory");
            }

            if (admAssociationResponsibleDto.getCreatedBy() == null
                    || admAssociationResponsibleDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            //association
            AdmOrganization organization = admOrganizationRepository.getByKey(admAssociationResponsibleDto.getOrganization().getOrganizationKey());
            if (organization == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Organization not found Error");
            }

            //person
            AdmPerson person = admPersonRepository.findByKey(admAssociationResponsibleDto.getPerson().getPersonKey());
            if (person == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found Error");
            }

            //check if associate is part of a given association
            AdmAssociationResponsible assoResponsible = admAssociationResponsibleRepository.findAssociationResponsible(organization, person);
            if (assoResponsible != null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Associate is already part of given association");
            }

            AdmUser createdByUser = admUserRepository.findByKey(admAssociationResponsibleDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmTypology status = null;
            if (admAssociationResponsibleDto.getStatus() != null
                    && admAssociationResponsibleDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(admAssociationResponsibleDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            AdmAssociationResponsible admAssociationResponsible = new AdmAssociationResponsible(
                    CsnConstants.ZERO,
                    organization,
                    person,
                    CsnFunctions.now(),
                    LocalDateTime.parse(CsnConstants.DATE_EMPTY, DateTimeFormatter.ofPattern(CsnConstants.DATE_FORMAT)),
                    admAssociationResponsibleDto.getAnnotation() == null
                            ? CsnConstants.DEFAULT_TEXT_SD : admAssociationResponsibleDto.getAnnotation(),
                    createdByUser,
                    CsnFunctions.now(),
                    status
            );


            admAssociationResponsibleRepository.save(admAssociationResponsible);
            return CsnFunctions.setResponse(admAssociationResponsible.getAssociationResponsibleId(),
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
    public Response edit(
            @PathParam("id") Long id,
            AdmAssociationResponsibleDto admAssociationResponsibleDto) {
        if (!id.equals(admAssociationResponsibleDto.getAssociationResponsibleId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        AdmAssociationResponsible currentAssoResp = this.admAssociationResponsibleRepository.findBy(id);
        if (currentAssoResp == null) {
            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Association Responsible not found");
        }

        //updating  fecha ingreso
        if (admAssociationResponsibleDto.getAdmissionDate() != null) {
            currentAssoResp.setAdmissionDate(admAssociationResponsibleDto.getAdmissionDate());
        }

        //updating discharge date
        if (admAssociationResponsibleDto.getDischargeDate() != null) {
            currentAssoResp.setDischargeDate(admAssociationResponsibleDto.getDischargeDate());
        }

        //updating annotation
        if (admAssociationResponsibleDto.getAnnotation() != null) {
            currentAssoResp.setAnnotation(admAssociationResponsibleDto.getAnnotation());
        }

        //updating status
        if (admAssociationResponsibleDto.getStatus() != null
                && admAssociationResponsibleDto.getStatus().getTypologyId() != null) {
            AdmTypology status = admTypologyRepository.findBy(admAssociationResponsibleDto.getStatus().getTypologyId());
            if (status == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect status");
            }

            currentAssoResp.setStatus(status);
        }

        admAssociationResponsibleRepository.save(currentAssoResp);
        return Response.status(Response.Status.OK).build();
    }

}
