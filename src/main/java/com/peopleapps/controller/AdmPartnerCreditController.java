package com.peopleapps.controller;

import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.partnerCredit.AdmPartnerCreditDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
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

@Path("/partner_credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "partner_credits")
public class AdmPartnerCreditController {

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmPartnerCreditRepository admPartnerCreditRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("partnerCreditId") @DefaultValue("0") Long partnerCreditId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmPartnerCreditDto> admPartnerCreditDtos = admPartnerCreditRepository.getAllPartnerCredits(null, desc);

        return Response.status(Response.Status.OK).entity(admPartnerCreditDtos).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long partnerCreditId
    ) {
        if (partnerCreditId == null || partnerCreditId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPartnerCreditDto> admPartnerCreditDtos = admPartnerCreditRepository.getAllPartnerCredits(partnerCreditId, null);

        if (admPartnerCreditDtos.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admPartnerCreditDtos.get(0)).build();

    }


    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmPartnerCreditDto admPartnerCreditDto) {
        try {

            if (admPartnerCreditDto.getCreatedBy() == null || admPartnerCreditDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            if (admPartnerCreditDto.getCredit() == null || admPartnerCreditDto.getCredit().getCreditId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit is mandatory");
            }

            if (admPartnerCreditDto.getPerson() == null || admPartnerCreditDto.getPerson().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person is mandatory");
            }


            AdmTypology status = this.checkTypology(admPartnerCreditDto.getStatus());
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmUser createdByUser = admUserRepository.findByKey(admPartnerCreditDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            AdmCredit credit = admCreditRepository.findBy(admPartnerCreditDto.getCredit().getCreditId());
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
            }

            AdmPerson person = admPersonRepository.findByKey(admPartnerCreditDto.getPerson().getPersonKey());
            if (person == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");

            }

            AdmPartnerCredit partnerCredit = new AdmPartnerCredit(
                    CsnConstants.ZERO,
                    credit,
                    person,
                    createdByUser,
                    CsnFunctions.now(),
                    status
            );


            admPartnerCreditRepository.save(partnerCredit);
            return CsnFunctions.setResponse(partnerCredit.getPartnerCreditId(),
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
    public Response edit(@PathParam("id") Long id, AdmPartnerCreditDto admPartnerCreditDto) {
        if (!id.equals(admPartnerCreditDto.getPartnerCreditId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        AdmPartnerCredit currentPartnerCredit = this.admPartnerCreditRepository.findBy(id);
        if (currentPartnerCredit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Partner credit not found");
        }

        if (admPartnerCreditDto.getStatus() != null
                && admPartnerCreditDto.getStatus().getTypologyId() != null) {
            AdmTypology status = this.checkTypology(admPartnerCreditDto.getStatus());

            if (status != null) {
                currentPartnerCredit.setStatus(status);
            }
        }

        //updating person
        if (admPartnerCreditDto.getPerson() != null
                && admPartnerCreditDto.getPerson().getPersonKey() != null) {
            AdmPerson person = this.admPersonRepository.findByKey(admPartnerCreditDto.getPerson().getPersonKey());
            if (person != null) {
                currentPartnerCredit.setPerson(person);
            }
        }

        //updating credit
        if (admPartnerCreditDto.getCredit() != null
                && admPartnerCreditDto.getCredit().getCreditId() != null) {
            AdmCredit credit = this.admCreditRepository.findBy(admPartnerCreditDto.getCredit().getCreditId());
            if (credit != null) {
                currentPartnerCredit.setCredit(credit);
            }
        }
        admPartnerCreditRepository.save(currentPartnerCredit);
        return Response.status(Response.Status.OK).build();

    }

    //check if typology exists
    private AdmTypology checkTypology(AdmTypologyDto typologyDto) {

        AdmTypology result = null;
        //check if values are null
        if (typologyDto != null && typologyDto.getTypologyId() != null) {
            result = this.admTypologyRepository.findBy(typologyDto.getTypologyId());
        }
        return result;
    }

}
