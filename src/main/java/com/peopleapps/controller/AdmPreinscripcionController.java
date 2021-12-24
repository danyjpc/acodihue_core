package com.peopleapps.controller;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.AdmUser;

import java.time.LocalDateTime;

import com.peopleapps.model.AdmBeneficiaryAccount;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmPerson;

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
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("/preinscripciones")
@Tag(name = "preinscripciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmPreinscripcionController {

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmPartnerRepository admPartnerRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmBeneficiaryRepository admBeneficiaryRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    AdmPreinscriptionRepository admPreinscriptionRepository;

    @Inject
    AdmAccountRepository admAccountRepository;

    @Inject
    AdmAccountBalanceRepository admAccountBalanceRepository;


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
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("agency") UUID agencyKey

    ) {
        List<AdmPreinscripcionDto> admPreinscripcionDtos = admPersonRepository.getAssociates(stateId, cityId, dateIni, dateEnd, name, cui, membershipNumber, desc, null, agencyKey);
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

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmPreinscripcionDto admPreinscripcionDto) {
        try {

            // new person to persist
            AdmPerson associate = null;
            AdmPerson associatePartner = null;
            AdmPerson associateBeneficiary = null;
            AdmPreinscription associatePreInscription = null;
            AdmOrganization organization = null;
            AdmAccount admSavingsAccount = null;
            AdmAccount admContributionAccount = null;


            //Check if CUI is alredy in use
            if (admPreinscripcionDto.getAssociate() != null) {
                associate = this.admPersonRepository.findByCui(admPreinscripcionDto.getAssociate().getCui());
                if (associate != null) {
                    return CsnFunctions.setResponse(associate.getPersonKey(), Response.Status.BAD_REQUEST, "El CUI ya esta ingresado");
                }

                //check if nit is valid
                if (admPreinscripcionDto.getAssociate().getNit() != null) {
                    associate = this.admPersonRepository.findByNit(admPreinscripcionDto.getAssociate().getNit());
                    if (associate != null) {
                        return CsnFunctions.setResponse(associate.getPersonKey(), Response.Status.BAD_REQUEST, "El NIT ya esta ingresado");
                    }
                }
            } else {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect information");
            }

            //Check if organization is sent of json payload
            if (admPreinscripcionDto.getOrganization() != null) {
                organization = this.admOrganizationRepository.getByKey(admPreinscripcionDto.getOrganization().getOrganizationKey());
                if (organization == null) {
                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect organization");
                }
            } else {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Missing organization");
            }

            //check if phone numbers are valid for associate (different to 0)
            if (admPreinscripcionDto.getAssociate().getPhones() != null) {
                if (admPreinscripcionDto.getAssociate().getPhones().size() > 0) {
                    for (AdmPreinscripcionDto.AdmPreinscripcionPhoneDto phone : admPreinscripcionDto.getAssociate().getPhones()
                    ) {
                        if (phone.getPhone() == 0) {
                            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone must be different from 0");
                        }

                    }
                } else {
                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone is mandatory");
                }

            } else {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone is mandatory");
            }

            //Check if partner has values
            if (admPreinscripcionDto.getPartner() != null) {
                associatePartner = new AdmPerson();
            }

            //Check if beneficiary has values
            if (admPreinscripcionDto.getBeneficiary() != null) {
                associateBeneficiary = new AdmPerson();
            }

            //setting accounts for associate
            associate = this.setPersonProperties(null, PreRegRolesEnum.ASOCIADO, admPreinscripcionDto);

            //setting accounts for partner
            if (associatePartner != null) {
                associatePartner = this.setPersonProperties(null, PreRegRolesEnum.CONYUGE, admPreinscripcionDto);
            }

            //setting accounts for beneficiary
            if (associateBeneficiary != null) {
                associateBeneficiary = this.setPersonProperties(associate, PreRegRolesEnum.BENEFICIARIO, admPreinscripcionDto);
            }


            //////////

            //persisting person

            associate = admPersonRepository.save(associate);
            //persisting partner
            if (associatePartner != null) {
                associatePartner = admPersonRepository.save(associatePartner);
            }

            //persisting beneficiary
            if (associateBeneficiary != null) {
                associateBeneficiary = admPersonRepository.save(associateBeneficiary);
            }

            //Persisting new associate phones
            //setting associate cell phone
            List<AdmPhone> associatePhones = this.setPhoneListProperties(admPreinscripcionDto, associate, PreRegRolesEnum.ASOCIADO);
            associatePhones = this.admPhoneRepository.saveList(associatePhones);

            //Persisting new partner phones
            if (associatePartner != null) {
                List<AdmPhone> partnerPhones = this.setPhoneListProperties(admPreinscripcionDto, associatePartner, PreRegRolesEnum.CONYUGE);
                partnerPhones = this.admPhoneRepository.saveList(partnerPhones);
            }

            //Persisting new beneficiary phones
            if (associateBeneficiary != null) {
                //TODO check if beneficiary will have extra phones
                List<AdmPhone> beneficiaryPhones = this.setPhoneListProperties(admPreinscripcionDto, associateBeneficiary, PreRegRolesEnum.BENEFICIARIO);
                beneficiaryPhones = this.admPhoneRepository.saveList(beneficiaryPhones);
            }

            //Persisting associate address
            AdmAddress associateAddress = this.setAddressProperties(admPreinscripcionDto, associate);
            associateAddress = this.admAddressRepository.save(associateAddress);
            admAddressRepository.resetLeader(associateAddress);


            //Persisting partner
            if (associatePartner != null) {
                AdmPartner partner = new AdmPartner(
                        null,
                        associate,
                        associatePartner,
                        associatePartner.getCreatedBy(),
                        CsnFunctions.now(),
                        new AdmTypology(CsnConstants.STATUS_ACTIVE),
                        CsnConstants.ZERO,
                        CsnConstants.ZERO,
                        Boolean.TRUE,
                        CsnConstants.ZERO
                );

                partner = admPartnerRepository.save(partner);
            }

            //Persisting beneficiary
            if (associateBeneficiary != null) {
                AdmBeneficiary beneficiary = new AdmBeneficiary(
                        null,
                        associateBeneficiary.getBeneficiaryAccount(),
                        associateBeneficiary,
                        associateBeneficiary.getCreatedBy(),
                        LocalDateTime.now(),
                        new AdmTypology(CsnConstants.STATUS_ACTIVE),
                        new AdmTypology(admPreinscripcionDto.getBeneficiary().getKinship().getTypologyId()),
                        CsnConstants.ZERO_DOUBLE,
                        CsnConstants.ZERO
                );

                beneficiary = admBeneficiaryRepository.save(beneficiary);
            }

            //Creating organization responsible object
            AdmOrganizationResponsible organizationResponsible =
                    new AdmOrganizationResponsible(
                            null,
                            organization,
                            associate,
                            CsnFunctions.now(),
                            Boolean.FALSE,
                            new AdmTypology(
                                    CsnConstants.STATUS_ACTIVE
                            )
                    );
            //Persisting organizationResponsible
            organizationResponsible = this.admOrganizationResponsibleRepository.save(organizationResponsible);

            //Creating new preinscription
            AdmPreinscription admPreinscription = new AdmPreinscription(
                    null,
                    organizationResponsible,
                    associate.getMembershipNumber(),
                    new AdmTypology(
                            CsnConstants.STATUS_ACTIVE
                    ),
                    new AdmTypology(
                            CsnConstants.PREINSCRIPTION_PENDING
                    ),
                    null,
                    associate.getCreatedBy(),
                    null,
                    null,
                    null,
                    CsnFunctions.now()
            );

            //persisting preinscripcion
            admPreinscription = this.admPreinscriptionRepository.save(admPreinscription);

            //persisting account
            //TODO check default account, generate num account, account order, account type
            admSavingsAccount = new AdmAccount(
                    0L,
                    organizationResponsible,
                    new AdmTypology(CsnConstants.REGULAR_SAVINGS_ACCOUNT),
                    //num account
                    associate.getMembershipNumber(),
                    //order
                    CsnConstants.SAVINGS_ORDER,
                    associate.getCreatedBy(),
                    CsnFunctions.now(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE)
            );

            admSavingsAccount = admAccountRepository.save(admSavingsAccount);

            admContributionAccount = new AdmAccount(
                    0L,
                    organizationResponsible,
                    new AdmTypology(CsnConstants.CONTRIBUTION_ACCOUNT),
                    //num account
                    associate.getMembershipNumber(),
                    //order
                    CsnConstants.CONTRIBUTION_ORDER,
                    associate.getCreatedBy(),
                    CsnFunctions.now(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE)
            );

            admContributionAccount = admAccountRepository.save(admContributionAccount);


            /*
            Logic for account movements

            //persisting balance accounts
            //persisting incription fee
            //TODO generate transaction number
            AdmAccountBalance inscriptionFee = new AdmAccountBalance(
                    0L,
                    admContributionAccount,
                    CsnFunctions.now().toEpochSecond(ZoneOffset.UTC) + new Random().nextInt(1000),
                    CsnConstants.INSCRIPTION_AMOUNT,
                    new AdmTypology(CsnConstants.INSCRIPTION_FEE),
                    associate.getCreatedBy(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    CsnFunctions.now(),
                    CsnConstants.INSCRIPTION_FEE_ANNOTATION
            );
            inscriptionFee = admAccountBalanceRepository.save(inscriptionFee);

            //initial fee logic
            //persisting initial fee
            //TODO generate transaction number
            AdmAccountBalance initialFee = new AdmAccountBalance(
                    0L,
                    admContributionAccount,
                    CsnFunctions.now().toEpochSecond(ZoneOffset.UTC) + new Random().nextInt(1000),
                    CsnConstants.CONTRIBUTION_FEE,
                    new AdmTypology(CsnConstants.INITIAL_FEE),
                    associate.getCreatedBy(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    CsnFunctions.now(),
                    CsnConstants.INITIAL_FEE_ANNOTATION
            );
            initialFee = admAccountBalanceRepository.save(initialFee);

            //extraordinary contribution logic
            //persisting extraordinary contribution
            //TODO generate transaction number
            AdmAccountBalance extraordinaryContribution = null;
            if (admPreinscripcionDto.getExtraContribution() != null && admPreinscripcionDto.getExtraContribution() > 0) {
                extraordinaryContribution = new AdmAccountBalance(
                        0L,
                        admContributionAccount,
                        CsnFunctions.now().toEpochSecond(ZoneOffset.UTC) + new Random().nextInt(1000),
                        admPreinscripcionDto.getExtraContribution(),
                        new AdmTypology(CsnConstants.EXTRAORDINARY_CONTRIBUTION),
                        associate.getCreatedBy(),
                        new AdmTypology(CsnConstants.STATUS_ACTIVE),
                        CsnFunctions.now(),
                        CsnConstants.EXTRAORDINARY_CONTRIBUTION_ANNOTATION
                );
                extraordinaryContribution = admAccountBalanceRepository.save(extraordinaryContribution);
            }
             Logic for account movements
        */

            //responses
            List<CustomPreinscriptionResponse> responses = new ArrayList<>();
            CustomPreinscriptionResponse custResp = new CustomPreinscriptionResponse(associate.getPersonKey().toString(),
                    "Associate created " + associate.getPersonId());
            responses.add(custResp);

            custResp = new CustomPreinscriptionResponse(organizationResponsible.getOrganizationResponsibleId(),
                    "Organization responsible created");
            responses.add(custResp);

            custResp = new CustomPreinscriptionResponse(admPreinscription.getPreinsciptionId(),
                    "Preinscripcion created");
            responses.add(custResp);


            if (associatePartner != null) {
                custResp = new CustomPreinscriptionResponse(associatePartner.getPersonKey().toString(),
                        "Partner created " + associatePartner.getPersonId());
                responses.add(custResp);
            }

            if (associateBeneficiary != null) {
                custResp = new CustomPreinscriptionResponse(associateBeneficiary.getPersonKey().toString(),
                        "Beneficiary created " + associateBeneficiary.getPersonId());
                responses.add(custResp);
            }

           /* custResp = new CustomPreinscriptionResponse(admContributionAccount.getAccountId(),
                    "Contribution Account created " + admContributionAccount.getAccountId());
            responses.add(custResp);

            custResp = new CustomPreinscriptionResponse(admSavingsAccount.getAccountId(),
                    "Savings Account created " + admSavingsAccount.getAccountId());
            responses.add(custResp); */

            return Response.ok(responses).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }

    }

    //method that sets address properties for new associates
    //takes dto to extrat info
    //takes person to get address account
    private AdmAddress setAddressProperties(AdmPreinscripcionDto admPreinscripcionDto, AdmPerson associate) {
        AdmAddress associateAddress = new AdmAddress(
                null,
                associate.getAddressAccount(),
                admPreinscripcionDto.getAssociate().getAddress().getAddressLine(),
                CsnConstants.DEFAULT_TEXT_SD,
                new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                //depto
                admPreinscripcionDto.getAssociate().getAddress().getState() != null
                        ? new AdmTypology(admPreinscripcionDto.getAssociate().getAddress().getState().getTypologyId())
                        : new AdmTypology(CsnConstants.DEFAULT_STATE_ID),
                //municipio
                admPreinscripcionDto.getAssociate().getAddress().getCity() != null
                        ? new AdmTypology(admPreinscripcionDto.getAssociate().getAddress().getCity().getTypologyId())
                        : new AdmTypology(CsnConstants.DEFAULT_CITY_ID),
                //zona
                admPreinscripcionDto.getAssociate().getAddress().getZone() != null
                        ? new AdmTypology(admPreinscripcionDto.getAssociate().getAddress().getZone().getTypologyId())
                        : new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                new AdmTypology(CsnConstants.STATUS_ACTIVE),
                //TODO check type
                new AdmTypology(CsnConstants.ADDRESS_TYPE_HOME),
                this.admUserRepository.findByKey(admPreinscripcionDto.getCreatedBy().getPersonKey()),
                CsnFunctions.now(),
                //isLeader
                Boolean.TRUE,
                CsnConstants.ZERO,
                CsnConstants.ZERO,
                CsnConstants.ZERO,
                CsnConstants.DEFAULT_TEXT_SD,
                //TODO document account is not shared
                associate.getDocumentAccount(),
                admPreinscripcionDto.getAssociate().getAddress().getVillage() != null
                        ? new AdmTypology(admPreinscripcionDto.getAssociate().getAddress().getVillage().getTypologyId())
                        : new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                CsnConstants.DEFAULT_TEXT_SD
        );

        return associateAddress;
    }

    //method that sets phone properties
    //takes dtoPreinscription to extrac data
    //person that will own the phone
    //phone which properties will be set
    private List<AdmPhone> setPhoneListProperties(AdmPreinscripcionDto admPreinscripcionDto, AdmPerson person, PreRegRolesEnum role) {

        List<AdmPhone> admPhoneList = new ArrayList<>();
        if (role.equals(PreRegRolesEnum.ASOCIADO)) {
            for (AdmPreinscripcionDto.AdmPreinscripcionPhoneDto item : admPreinscripcionDto.getAssociate().getPhones()) {
                AdmPhone phone = this.setSinglePhoneProperties(item, person);
                admPhoneList.add(phone);
            }
            //first phone would be leader
            if (admPhoneList.size() > 0) {
                admPhoneList.get(0).setLeader(Boolean.TRUE);
            }
        } else if (role.equals(PreRegRolesEnum.BENEFICIARIO)) {
            for (AdmPreinscripcionDto.AdmPreinscripcionPhoneDto item : admPreinscripcionDto.getBeneficiary().getPerson().getPhones()) {
                AdmPhone phone = this.setSinglePhoneProperties(item, person);
                admPhoneList.add(phone);
            }
            //first phone would be leader
            if (admPhoneList.size() > 0) {
                admPhoneList.get(0).setLeader(Boolean.TRUE);
            }
        } else if (role.equals(PreRegRolesEnum.CONYUGE)) {
            for (AdmPreinscripcionDto.AdmPreinscripcionPhoneDto item : admPreinscripcionDto.getPartner().getPhones()) {
                AdmPhone phone = this.setSinglePhoneProperties(item, person);
                admPhoneList.add(phone);
            }
            //first phone would be leader
            if (admPhoneList.size() > 0) {
                admPhoneList.get(0).setLeader(Boolean.TRUE);
            }
        }

        return admPhoneList;
    }


    private AdmPhone setSinglePhoneProperties(AdmPreinscripcionDto.AdmPreinscripcionPhoneDto item, AdmPerson person) {
        AdmPhone phone = new AdmPhone(
                null,
                person.getPhoneAccount(),
                item.getPhone(),
                new AdmTypology(item.getStatus().getTypologyId()),
                new AdmTypology(item.getType().getTypologyId()),
                person.getCreatedBy(),
                CsnFunctions.now(),
                Boolean.FALSE
        );
        return phone;
    }

    private AdmPerson setPersonProperties(AdmPerson associate, PreRegRolesEnum role, AdmPreinscripcionDto admPreinscripcionDto) {
        logger.error("" + role.getRole());
        //creating new phone account
        AdmPhoneAccount personPhoneAccount = this.admPhoneAccountRepository.createAccount();
        //Creating new document account
        AdmDocumentAccount personDocumentAccount = this.admDocumentAccountRepository.createAccount();
        //Creating new address account
        AdmAddressAccount personAddressAccout = this.admAddressAccountRepository.createAccount();
        //Creating new beneficiary accout
        //If person is a beneficiary, we have to use the associate beneficiary account
        AdmBeneficiaryAccount personBeneficiaryAccount = new AdmBeneficiaryAccount();
        if (!role.equals(PreRegRolesEnum.BENEFICIARIO)) {
            personBeneficiaryAccount = this.admBeneficiaryAccountRepository.createAccount();

        } else {
            personBeneficiaryAccount = associate.getBeneficiaryAccount();
        }
        //Person object to return
        AdmPerson person = null;
        if (role.equals(PreRegRolesEnum.ASOCIADO)) {
            logger.error("" + role.getRole());

            person = new AdmPerson(
                    null,
                    personPhoneAccount,
                    personDocumentAccount,
                    personAddressAccout,
                    personBeneficiaryAccount,
                    admPreinscripcionDto.getAssociate().getFirstName() != null
                            ? admPreinscripcionDto.getAssociate().getFirstName().toUpperCase(new Locale("es", "ES"))
                            : CsnConstants.DEFAULT_TEXT_SD,
                    admPreinscripcionDto.getAssociate().getMiddleName() != null
                            ? admPreinscripcionDto.getAssociate().getMiddleName().toUpperCase(new Locale("es", "ES"))
                            : CsnConstants.DEFAULT_TEXT_SD,
                    admPreinscripcionDto.getAssociate().getLastName() != null
                            ? admPreinscripcionDto.getAssociate().getLastName().toUpperCase(new Locale("es", "ES"))
                            : CsnConstants.DEFAULT_TEXT_SD,
                    admPreinscripcionDto.getAssociate().getPartnerName() != null
                            ? admPreinscripcionDto.getAssociate().getPartnerName().toUpperCase(new Locale("es", "ES"))
                            : CsnConstants.DEFAULT_TEXT_SD,
                    admPreinscripcionDto.getAssociate().getMarriedName() != null
                            ? admPreinscripcionDto.getAssociate().getMarriedName().toUpperCase(new Locale("es", "ES"))
                            : CsnConstants.DEFAULT_TEXT_SD,
                    admPreinscripcionDto.getAssociate().getBirthday(),
                    admPreinscripcionDto.getAssociate().getEmail(),
                    new AdmTypology(admPreinscripcionDto.getAssociate().getMaritalStatus().getTypologyId()),
                    new AdmTypology(admPreinscripcionDto.getAssociate().getProfession().getTypologyId()),
                    admPreinscripcionDto.getAssociate().getCui(),
                    //TODO check default document type
                    //set document type
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    //set document order
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    //order number
                    CsnConstants.ZERO,
                    //nit
                    admPreinscripcionDto.getAssociate().getNit(),
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    new AdmTypology(CsnConstants.DEFAULT_STATE_ID),
                    new AdmTypology(CsnConstants.DEFAULT_CITY_ID),
                    //TODO immigration condition
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(admPreinscripcionDto.getAssociate().getGenre().getTypologyId()),
                    CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(admPreinscripcionDto.getAssociate().getMayanPeople().getTypologyId()),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(admPreinscripcionDto.getAssociate().getStatus().getTypologyId()),
                    //Setting created by
                    this.admUserRepository.findByKey(admPreinscripcionDto.getCreatedBy().getPersonKey()),
                    CsnFunctions.now(),
                    Boolean.FALSE,
                    Boolean.FALSE,
                    //membership number
                    admPersonRepository.generateMembershipNumber(),
                    //person key
                    CsnFunctions.generateKey(),
                    getNameComplete(admPreinscripcionDto),
                    Boolean.TRUE,
                    admPreinscripcionDto.getAssociate().getLinguisticCommunity() != null ? new AdmTypology(admPreinscripcionDto.getAssociate().getLinguisticCommunity().getTypologyId()) : new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
        } else {
            logger.error("" + role.getRole());
            person = new AdmPerson(
                    null,
                    personPhoneAccount,
                    personDocumentAccount,
                    personAddressAccout,
                    personBeneficiaryAccount,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    //Setting birthday LocalDate
                    LocalDate.parse(CsnConstants.ONLY_DATE_EMPTY, DateTimeFormatter.ofPattern(CsnConstants.ONLY_DATE_FORMAT)),
                    CsnConstants.DEFAULT_TEXT_SD,
                    //set marital status
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    //TODO check default document type
                    //set document type
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    //nit
                    CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    new AdmTypology(CsnConstants.DEFAULT_STATE_ID),
                    new AdmTypology(CsnConstants.DEFAULT_CITY_ID),
                    //TODO immigration condition
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    //role
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    //status
                    (role.equals(PreRegRolesEnum.CONYUGE)) ?
                            new AdmTypology(admPreinscripcionDto.getPartner().getStatus().getTypologyId()) :
                            new AdmTypology(admPreinscripcionDto.getBeneficiary().getPerson().getStatus().getTypologyId()),
                    //Setting created by
                    this.admUserRepository.findByKey(admPreinscripcionDto.getCreatedBy().getPersonKey()),
                    CsnFunctions.now(),
                    //isPartner
                    (role.equals(PreRegRolesEnum.CONYUGE)) ?
                            Boolean.TRUE : Boolean.FALSE,
                    (role.equals(PreRegRolesEnum.BENEFICIARIO)) ?
                            Boolean.TRUE : Boolean.FALSE,
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    //nameComplete
                    (role.equals(PreRegRolesEnum.CONYUGE))
                            ? admPreinscripcionDto.getPartner().getNameComplete().toUpperCase(new Locale("es", "ES"))
                            : admPreinscripcionDto.getBeneficiary().getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")),
                    //isAssociate
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
        }
        return person;
    }

    private String getNameComplete(AdmPreinscripcionDto admPreinscripcionDto) {
        String nameComplete = admPreinscripcionDto.getAssociate().getNameComplete();
        StringBuilder returnName = new StringBuilder();
        if (nameComplete != null) {
            returnName.append(nameComplete);
        } else {
            //appending name parts
            //first name
            returnName.append(admPreinscripcionDto.getAssociate().getFirstName());
            //middle name
            if (admPreinscripcionDto.getAssociate().getMiddleName() != null && !admPreinscripcionDto.getAssociate().getMiddleName().equals("S/D")) {
                returnName.append(" ").append(admPreinscripcionDto.getAssociate().getMiddleName());
            }
            //last name
            returnName.append(" ").append(admPreinscripcionDto.getAssociate().getLastName());
            //partner name
            if (!admPreinscripcionDto.getAssociate().getPartnerName().equals("S/D")) {
                returnName.append(" ").append(admPreinscripcionDto.getAssociate().getPartnerName());
            }
        }
        //logger.error("Name complete ----> "+returnName.toString());
        return returnName.toString().toUpperCase(new Locale("es", "ES"));
    }


    public static class CustomPreinscriptionResponse {
        private String personKey;
        private Long id;
        private String message;


        public CustomPreinscriptionResponse(Long id, String message) {
            this.id = id;
            this.message = message;
        }

        public CustomPreinscriptionResponse(String personKey, String message) {
            this.personKey = personKey;
            this.message = message;
        }

        public String getPersonKey() {
            return personKey;
        }

        public void setPersonKey(String personKey) {
            this.personKey = personKey;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}

