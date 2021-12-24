package com.peopleapps.controller;

import com.peopleapps.dto.inputs.partner.AdmPartnerDto;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Path("/associate/v1")
@Tag(name = "partners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmPartnerController {

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partner/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(@PathParam("key") UUID associateKey,
                           @QueryParam("status") @DefaultValue("0") Long statusId,
                           @QueryParam("descending") @DefaultValue("true") Boolean desc) {

        AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
        if(associate != null){
            List<AdmPartnerDto> admPartnerList = admPartnerRepository.getAllPartners(
                    associate.getPersonId(), associateKey, statusId, desc);

            //Stream que devuelve solo los partners filtrados en base al UUID del asociado
            //la lista admPartnerList devuelve multiples del mismo partner si tienen multiples telefonos
            List<AdmPartnerDto> uniquePartners = admPartnerList.stream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparingLong(AdmPartnerDto::getPartnerId))), ArrayList::new));

            return Response.status(Response.Status.OK).entity(uniquePartners).build();
        }

        return CsnFunctions.setResponse(0L, Response.Status.OK, "Empty");
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partner/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long partnerId
    ) {
        if (partnerId == null || partnerId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<AdmPartnerDto> admPartnerList = admPartnerRepository.getAllPartners(
                partnerId, associateKey, null, null);
        if (admPartnerList.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<AdmPartnerDto> uniquePartners = admPartnerList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparingLong(AdmPartnerDto::getPartnerId))), ArrayList::new));
        return Response.status(Response.Status.OK).entity(uniquePartners.get(0)).build();

    }

    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partner/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(@PathParam("key") UUID associateKey,
                           AdmPartnerDto partnerDto) {

        return admPartnerRepository.createPartner(partnerDto, associateKey);

    }

    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partner/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("key") UUID associateKey,
                         @PathParam("id") Long partnerId,
                         AdmPartnerDto partnerDto) {
        try {

            if (partnerDto.getPartnerId() == null || partnerDto.getPartnerId() != partnerId) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect ID");
            }

            AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            AdmPartner currentPartner = this.admPartnerRepository.findBy(partnerDto.getPartnerId());
            if (currentPartner == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Partner not found");
            }

            AdmPerson currentPartnerPerson = this.admPersonRepository.findByKey(currentPartner.getPartner().getPersonKey());
            if (partnerDto.getPerson().getFirstName() != null) {
                currentPartnerPerson.setFirstName(partnerDto.getPerson().getFirstName());
            }
            if (partnerDto.getPerson().getLastName() != null) {
                currentPartnerPerson.setLastName(partnerDto.getPerson().getLastName());
            }
            if (partnerDto.getPerson().getNameComplete() != null) {
                currentPartnerPerson.setNameComplete(partnerDto.getPerson().getNameComplete());
            }
            if (partnerDto.getPerson().getBirthday() != null) {
                currentPartnerPerson.setBirthday(partnerDto.getPerson().getBirthday());
            }
            if (partnerDto.getPerson().getEmail() != null) {
                currentPartnerPerson.setEmail(partnerDto.getPerson().getEmail());
            }
            if (partnerDto.getNoBoys() != null) {
                currentPartner.setNoBoys(partnerDto.getNoBoys());
            }
            if (partnerDto.getNoGirls() != null) {
                currentPartner.setNoGirls(partnerDto.getNoGirls());
            }
            if(partnerDto.getIsLeader() != null){
                currentPartner.setLeader(partnerDto.getIsLeader());
            }

            AdmPhone currentPhone = this.admPhoneRepository.getByPhoneAccount(currentPartnerPerson.getPhoneAccount().getPhoneAccountId());
            if (currentPhone != null) {
                currentPhone.setPhone(partnerDto.getPhone().getPhone());
                if (partnerDto.getPhone().getStatus() != null) {
                    AdmTypology phoneStatus = this.admTypologyRepository.findBy(partnerDto.getPhone().getStatus().getTypologyId());
                    currentPhone.setStatus(phoneStatus);
                }
                if (partnerDto.getPhone().getType() != null) {
                    AdmTypology phoneType = this.admTypologyRepository.findBy(partnerDto.getPhone().getType().getTypologyId());
                    currentPhone.setType(phoneType);
                }
                currentPhone = admPhoneRepository.save(currentPhone);
                admPhoneRepository.resetLeader(currentPhone);
            }else{
                AdmPhone newPhone = new AdmPhone(
                        0l,
                        currentPartnerPerson.getPhoneAccount(),
                        partnerDto.getPhone().getPhone(),
                        partnerDto.getPhone().getStatus() != null ? new AdmTypology(partnerDto.getPhone().getStatus().getTypologyId()) :
                                new AdmTypology(CsnConstants.STATUS_ACTIVE),
                        partnerDto.getPhone().getType() != null ? new AdmTypology(partnerDto.getPhone().getType().getTypologyId()) :
                                new AdmTypology(CsnConstants.HOME_PHONE_ID),

                        currentPartnerPerson.getCreatedBy(),
                        CsnFunctions.now(),
                        Boolean.FALSE
                );
                newPhone =admPhoneRepository.save(newPhone);
                admPhoneRepository.resetLeader(newPhone);
            }
            if (partnerDto.getStatus() != null) {
                AdmTypology status = this.admTypologyRepository.findBy(partnerDto.getStatus().getTypologyId());
                if (status != null) {
                    currentPartner.setStatus(status);
                }
            }

            //updating changes
            admPersonRepository.save(currentPartnerPerson);
            currentPartner = admPartnerRepository.save(currentPartner);
            admPartnerRepository.resetPartnerLeader(currentPartner);

            return CsnFunctions.setResponse(currentPartner.getPartnerId(),
                    Response.Status.OK, "Values updated");
        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


}
