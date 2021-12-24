package com.peopleapps.controller.credit;

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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Path("/credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "credit_partner")
public class AdmCreditPartnerController {

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmPartnerCreditRepository admPartnerCreditRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

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

    //--------------Beginning of Partner endpoints------------------------

    //Get all partners by credit key
    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partners")
    public Response getPartnersByCreditKey(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmPartnerDto> admPartnerList = admPartnerRepository.getPartnersByCreditKey(creditKey, desc, null);

        //todo remove repeated partners (appear multiple times because of multiple phones)
        //Stream que devuelve solo los partners filtrados en base al partner id
        //la lista admPartnerList devuelve multiples del mismo partner si tienen multiples telefonos
        List<AdmPartnerDto> uniquePartners = admPartnerList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparingLong(AdmPartnerDto::getPartnerId))), ArrayList::new));


        return Response.status(Response.Status.OK).entity(uniquePartners).build();
    }

    //Get credit partner by id
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partners/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getPartnerById(
            @PathParam("key") UUID creditKey,
            @PathParam("id") @DefaultValue("0") Long partnerId
    ) {
        if (partnerId == null || partnerId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmPartnerDto> admPartnerList = admPartnerRepository.getPartnersByCreditKey(creditKey, null, partnerId);
        if (admPartnerList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admPartnerList.get(0)).build();

    }

    //Add new partner to a credit (persist on adm_partner and adm_partner_credit)
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partners/new")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response addNewPartnerToCredit(
            @PathParam("key") UUID creditKey,
            AdmPartnerDto partnerDto
    ) {
        try {
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            AdmCreditCalculator calculator = admCreditCalculatorRepository.findBy(credit.getCalculator().getCalculatorId());
            if (calculator == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Calculator not found");
            }

            AdmPerson associate = admPersonRepository.findByKey(calculator.getPerson().getPersonKey());
            //Create person fiador
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            if (partnerDto.getCreatedBy() == null || partnerDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }
            if (partnerDto.getPhone() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone is mandatory");
            }

            AdmPerson createdByPerson = admPersonRepository.findByKey(partnerDto.getCreatedBy().getPersonKey());
            if (createdByPerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(createdByPerson.getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmTypology status = null;
            if (partnerDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(partnerDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }
            AdmTypology maritalStatus = this.admTypologyRepository.findBy(partnerDto.getPerson().getMaritalStatus().getTypologyId());
            AdmTypology profession = this.admTypologyRepository.findBy(partnerDto.getPerson().getProfession().getTypologyId());

            //fiador person
            AdmPerson personfiador = new AdmPerson(
                    0L,
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    admAddressAccountRepository.createAccount(),
                    admBeneficiaryAccountRepository.createAccount(),
                    partnerDto.getPerson().getFirstName() != null ? partnerDto.getPerson().getFirstName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    partnerDto.getPerson().getLastName() != null ? partnerDto.getPerson().getLastName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    partnerDto.getPerson().getBirthday() != null ? partnerDto.getPerson().getBirthday() : LocalDate.parse(CsnConstants.ONLY_DATE_EMPTY),
                    partnerDto.getPerson().getEmail() != null ? partnerDto.getPerson().getEmail() : CsnConstants.DEFAULT_TEXT_SD,
                    maritalStatus != null ? maritalStatus : new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    profession != null ? profession : new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    partnerDto.getPerson().getCui(),
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
                    Boolean.TRUE, //is partner
                    Boolean.FALSE,
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    partnerDto.getPerson().getNameComplete() != null ? partnerDto.getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
            //persisting partner person
            admPersonRepository.save(personfiador);

            AdmPhone newPhone = new AdmPhone(
                    0l,
                    personfiador.getPhoneAccount(),
                    partnerDto.getPhone().getPhone(),
                    partnerDto.getPhone().getStatus() != null ? new AdmTypology(partnerDto.getPhone().getStatus().getTypologyId()) :
                            new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    partnerDto.getPhone().getType() != null ? new AdmTypology(partnerDto.getPhone().getType().getTypologyId()) :
                            new AdmTypology(CsnConstants.HOME_PHONE_ID),

                    personfiador.getCreatedBy(),
                    CsnFunctions.now(),
                    Boolean.FALSE
            );
            newPhone =admPhoneRepository.save(newPhone);
            admPhoneRepository.resetLeader(newPhone);

            AdmPartnerCredit partnerCredit = new AdmPartnerCredit(
                    CsnConstants.ZERO,
                    credit,
                    personfiador,
                    personfiador.getCreatedBy(),
                    CsnFunctions.now(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE)
            );
            //persist partnerCredit
            admPartnerCreditRepository.save(partnerCredit);

            return CsnFunctions.setResponse(partnerCredit.getPartnerCreditId(),
                    Response.Status.CREATED, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //Add existing partner (conyuge) to a credit (persist on adm_partner_credit)
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partners")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response addPartnerToCredit(
            @PathParam("key") UUID creditKey,
            AdmPartnerDto partnerDto
    ) {
        try {
            if (partnerDto.getPerson() == null || partnerDto.getPerson().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person is mandatory");
            }

            if (partnerDto.getCreatedBy() == null || partnerDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmUser createdByUser = admUserRepository.findByKey(partnerDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmPerson partner = this.admPersonRepository.findByKey(partnerDto.getPerson().getPersonKey());
            if (partner == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Partner not found Error");
            }

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            AdmCreditCalculator calculator = admCreditCalculatorRepository.findBy(credit.getCalculator().getCalculatorId());
            if (calculator == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Calculator not found");
            }

            AdmTypology status = null;
            if (partnerDto.getStatus() != null && partnerDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(partnerDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            AdmPartnerCredit partnerCredit = new AdmPartnerCredit(
                    CsnConstants.ZERO,
                    credit,
                    partner,
                    createdByUser,
                    CsnFunctions.now(),
                    status
            );
            //check if partner already exists on credit
            Boolean respuesta = admPartnerCreditRepository.findPartnerAlreadyOnCredit(partnerCredit);

            if (respuesta) {
                //partner already on credit
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Partner already in this credit");
            }

            //persist partnerCredit
            admPartnerCreditRepository.save(partnerCredit);

            return CsnFunctions.setResponse(partnerCredit.getPartnerCreditId(),
                    Response.Status.CREATED, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //Update partner from credit
    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/partners/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("key") UUID creditKey,
                         @PathParam("id") Long partnerId,
                         AdmPartnerDto partnerDto) {
        try {

            if (partnerDto.getPartnerId() == null || partnerDto.getPartnerId() != partnerId) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect ID");
            }

            //find credit
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            //find associate by credit and id
            AdmPerson associate = this.admPersonRepository.findByKey(credit.getCalculator().getPerson().getPersonKey());
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            AdmPartner currentPartner = this.admPartnerRepository.findBy(partnerDto.getPartnerId());
            if (currentPartner == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Partner not found");
            }

            AdmPerson currentPartnerPerson = this.admPersonRepository.findByKey(partnerDto.getPerson().getPersonKey());
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
            }
            if (partnerDto.getStatus() != null) {
                AdmTypology status = this.admTypologyRepository.findBy(partnerDto.getStatus().getTypologyId());
                if (status != null) {
                    currentPartner.setStatus(status);
                }
            }

            //updating changes
            admPersonRepository.save(currentPartnerPerson);
            admPartnerRepository.save(currentPartner);
            currentPhone = admPhoneRepository.save(currentPhone);
            admPhoneRepository.resetLeader(currentPhone);
            return CsnFunctions.setResponse(currentPartner.getPartnerId(),
                    Response.Status.OK, "Values updated");
        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //--------------End of partner endpoints------------------------
}
