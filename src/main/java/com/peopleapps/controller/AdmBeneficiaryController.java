package com.peopleapps.controller;

import com.peopleapps.dto.inputs.beneficiary.AdmBeneficiaryDto;
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
import java.util.*;
import java.util.stream.Collectors;

@Path("/associate/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "beneficiaries")
public class AdmBeneficiaryController {

    @Inject
    AdmBeneficiaryRepository admBeneficiaryRepository;

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
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/beneficiary/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID associateKey,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc) {

        List<AdmBeneficiaryDto> admBeneficiaryList = admBeneficiaryRepository.getAllBeneficiaries(
                null, associateKey, statusId, desc);

        //Stream que devuelve solo los partners filtrados en base al partner id
        //la lista admPartnerList devuelve multiples del mismo partner si tienen multiples telefonos
        List<AdmBeneficiaryDto> uniqueBeneficiaries= admBeneficiaryList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        ()-> new TreeSet<>(Comparator.comparingLong(AdmBeneficiaryDto::getBeneficiaryId))), ArrayList::new));

        return Response.status(Response.Status.OK).entity(uniqueBeneficiaries).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/beneficiary/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long beneficiaryId
    ) {
        if (beneficiaryId == null || beneficiaryId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmBeneficiaryDto> admBeneficiaryList = admBeneficiaryRepository.getAllBeneficiaries(
                beneficiaryId, associateKey, null, null);
        if (admBeneficiaryList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        List<AdmBeneficiaryDto> uniqueBeneficiaries= admBeneficiaryList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        ()-> new TreeSet<>(Comparator.comparingLong(AdmBeneficiaryDto::getBeneficiaryId))), ArrayList::new));

        return Response.status(Response.Status.OK).entity(uniqueBeneficiaries.get(0)).build();

    }

    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/beneficiary")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(@PathParam("key") UUID associateKey,
                           AdmBeneficiaryDto beneficiaryDto) {
        try {
            AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            if (beneficiaryDto.getCreatedBy() == null || beneficiaryDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }
            if (beneficiaryDto.getKinship() == null || beneficiaryDto.getKinship().getTypologyId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Kinship is mandatory");
            }
            if (beneficiaryDto.getPhone() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone is mandatory");
            }
            if (beneficiaryDto.getPerson().getNameComplete() == null
                    || beneficiaryDto.getPerson().getBirthday() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incomplete information");
            }

            AdmPerson createdByPerson = admPersonRepository.findByKey(beneficiaryDto.getCreatedBy().getPersonKey());
            if (createdByPerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(createdByPerson.getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmTypology status = null;
            if (beneficiaryDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(beneficiaryDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            AdmTypology kinship = null;
            if (beneficiaryDto.getKinship() != null) {
                kinship = this.admTypologyRepository.findBy(beneficiaryDto.getKinship().getTypologyId());
            }
            if (kinship == null) {
                kinship = new AdmTypology(CsnConstants.TYPOLOGY_EMPTY);
            }

            //beneficiary person
            AdmPerson personBeneficiary = new AdmPerson(
                    0L,
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    admAddressAccountRepository.createAccount(),
                    associate.getBeneficiaryAccount(),
                    beneficiaryDto.getPerson().getFirstName() != null ? beneficiaryDto.getPerson().getFirstName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    beneficiaryDto.getPerson().getLastName() != null ? beneficiaryDto.getPerson().getLastName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    beneficiaryDto.getPerson().getBirthday(),
                    beneficiaryDto.getPerson().getEmail(),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    status,
                    createdByUser,
                    CsnFunctions.now(),
                    Boolean.FALSE,
                    Boolean.TRUE, //Is beneficiary
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    beneficiaryDto.getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")),
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
            //persisting beneficiary person
            admPersonRepository.save(personBeneficiary);

            //beneficiary to persist
            AdmBeneficiary newBeneficiary = new AdmBeneficiary(
                    0L,
                    personBeneficiary.getBeneficiaryAccount(),
                    personBeneficiary,
                    personBeneficiary.getCreatedBy(),
                    CsnFunctions.now(),
                    status,
                    kinship,
                    beneficiaryDto.getPercent() == null ? CsnConstants.ZERO_DOUBLE : beneficiaryDto.getPercent(),
                    CsnConstants.ZERO
                    //age: beneficiaryDto.getAge()
            );

            admBeneficiaryRepository.save(newBeneficiary);

            AdmPhone newPhone = new AdmPhone(
                    0l,
                    personBeneficiary.getPhoneAccount(),
                    beneficiaryDto.getPhone().getPhone(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    new AdmTypology(CsnConstants.HOME_PHONE_ID),
                    personBeneficiary.getCreatedBy(),
                    CsnFunctions.now(),
                    Boolean.FALSE
            );
            newPhone = admPhoneRepository.save(newPhone);
            admPhoneRepository.resetLeader(newPhone);


            return CsnFunctions.setResponse(newBeneficiary.getBeneficiaryId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/beneficiary/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response update(@PathParam("key") UUID associateKey,
                           @PathParam("id") Long beneficiaryId,
                           AdmBeneficiaryDto beneficiaryDto) {
        try {
            if (beneficiaryDto.getBeneficiaryId() == null || beneficiaryDto.getBeneficiaryId() != beneficiaryId) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect ID");
            }
            AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            AdmBeneficiary currentBeneficiary = this.admBeneficiaryRepository.findBy(beneficiaryDto.getBeneficiaryId());
            if (currentBeneficiary == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Beneficiary not found");
            }
            AdmPerson currentBeneficiaryPerson = this.admPersonRepository.findByKey(currentBeneficiary.getBeneficiary().getPersonKey());
            if (beneficiaryDto.getPerson().getFirstName() != null) {
                currentBeneficiaryPerson.setFirstName(beneficiaryDto.getPerson().getFirstName());
            }
            if (beneficiaryDto.getPerson().getLastName() != null) {
                currentBeneficiaryPerson.setLastName(beneficiaryDto.getPerson().getLastName());
            }
            if (beneficiaryDto.getPerson().getNameComplete() != null) {
                currentBeneficiaryPerson.setNameComplete(beneficiaryDto.getPerson().getNameComplete());
            }
            if (beneficiaryDto.getPerson().getBirthday() != null) {
                currentBeneficiaryPerson.setBirthday(beneficiaryDto.getPerson().getBirthday());
            }
            if (beneficiaryDto.getAge() != null) {
                currentBeneficiary.setAge(beneficiaryDto.getAge());
            }
            if (beneficiaryDto.getPercent() != null) {
                currentBeneficiary.setPercent(beneficiaryDto.getPercent());
            }

            //Check if has a phone, if doesn´t has create one
            AdmPhone currentPhone = this.admPhoneRepository.getByPhoneAccount(currentBeneficiaryPerson.getPhoneAccount().getPhoneAccountId());
                if (currentPhone != null) {
                    currentPhone.setPhone(beneficiaryDto.getPhone().getPhone());
                    if(beneficiaryDto.getPhone().getStatus() != null){
                        AdmTypology phoneStatus = this.admTypologyRepository.findBy(beneficiaryDto.getPhone().getStatus().getTypologyId());
                        currentPhone.setStatus(phoneStatus);
                    }
                    if(beneficiaryDto.getPhone().getType() != null){
                        AdmTypology phoneType = this.admTypologyRepository.findBy(beneficiaryDto.getPhone().getType().getTypologyId());
                        currentPhone.setType(phoneType);
                    }
                    currentPhone = admPhoneRepository.save(currentPhone);
                    admPhoneRepository.resetLeader(currentPhone);
                }else{
                    AdmPhone newPhone = new AdmPhone(
                        0l,
                        currentBeneficiaryPerson.getPhoneAccount(),
                        beneficiaryDto.getPhone().getPhone(),
                        new AdmTypology(CsnConstants.STATUS_ACTIVE),
                        new AdmTypology(CsnConstants.HOME_PHONE_ID),
                        currentBeneficiaryPerson.getCreatedBy(),
                        CsnFunctions.now(),
                        Boolean.FALSE
                    );
                    newPhone = admPhoneRepository.save(newPhone);
                    admPhoneRepository.resetLeader(newPhone);
                }

            if (beneficiaryDto.getPerson().getEmail() != null) {
                currentBeneficiaryPerson.setEmail(beneficiaryDto.getPerson().getEmail());
            }

            if (beneficiaryDto.getKinship() != null && beneficiaryDto.getKinship().getTypologyId() != null) {
                AdmTypology kinship = this.admTypologyRepository.findBy(beneficiaryDto.getKinship().getTypologyId());
                if (kinship != null) {
                    currentBeneficiary.setKinship(kinship);
                }
            }

            if (beneficiaryDto.getStatus() != null) {
                AdmTypology status = this.admTypologyRepository.findBy(beneficiaryDto.getStatus().getTypologyId());
                if (status != null) {
                    currentBeneficiary.setStatus(status);
                }
            }

            //updating changes
            admPersonRepository.save(currentBeneficiaryPerson);
            admBeneficiaryRepository.save(currentBeneficiary);


            return CsnFunctions.setResponse(currentBeneficiary.getBeneficiaryId(),
                    Response.Status.OK, "Values updated");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

}
