package com.peopleapps.controller.credit;

import com.peopleapps.dto.reference.AdmReferenceDto;
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
import java.util.Locale;
import java.util.UUID;

@Path("/credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "credit_references")
public class AdmCreditReferenceController {

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject

    AdmReferenceRepository admReferenceRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    Logger logger;

    //----------------CREDIT REFERENCE ENDPOINTS---------------------
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/references/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllReferencesByCredit(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        List<AdmReferenceDto> admReferenceList = admReferenceRepository.getReferencesByCredit(
                creditKey, desc, null);

        return Response.status(Response.Status.OK).entity(admReferenceList).build();
    }

    //Get reference by credit and by reference id
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/references/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllReferencesByCreditByReference(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @PathParam("id") @DefaultValue("0") Long referenceId
    ) {

        List<AdmReferenceDto> admReferenceList = admReferenceRepository.getReferencesByCredit(
                creditKey, desc, referenceId);

        if (admReferenceList.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(admReferenceList.get(0)).build();
    }

    //Persist reference (new Person)
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/references/new")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistCreditReferenceNewPerson(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            AdmReferenceDto referenceDto
    ) {

        try {
            if (referenceDto.getPerson() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person is mandatory");
            }

            if (referenceDto.getCreatedBy() == null || referenceDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmUser createdByUser = admUserRepository.findByKey(referenceDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            AdmTypology status = null;
            if (referenceDto.getStatus() != null && referenceDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(referenceDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //persist reference (on person table)
            AdmPerson referencePerson = new AdmPerson(
                    CsnConstants.ZERO,
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    admAddressAccountRepository.createAccount(),
                    admBeneficiaryAccountRepository.createAccount(),
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnFunctions.now().toLocalDate(),
                    referenceDto.getPerson().getEmail(),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    CsnConstants.DEFAULT_TEXT_SD,
                    admTypologyRepository.findBy(CsnConstants.DEFAULT_COUNTRY_ID),
                    admTypologyRepository.findBy(CsnConstants.DEFAULT_STATE_ID),
                    admTypologyRepository.findBy(CsnConstants.DEFAULT_CITY_ID),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    admTypologyRepository.findBy(CsnConstants.TYPOLOGY_EMPTY),
                    admTypologyRepository.findBy(CsnConstants.STATUS_ACTIVE),
                    createdByUser,
                    CsnFunctions.now(),
                    Boolean.FALSE,
                    Boolean.FALSE,
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    referenceDto.getPerson().getNameComplete() != null ? referenceDto.getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
            //persisting reference
            referencePerson = admPersonRepository.save(referencePerson);

            //Persist phone
            AdmTypology phoneType = null;
            if (referenceDto.getPhone().getType() != null) {
                phoneType = admTypologyRepository.findBy(referenceDto.getPhone().getType().getTypologyId());
            } else {
                phoneType = admTypologyRepository.findBy(CsnConstants.CELL_PHONE_ID);
            }
            AdmPhone referencePhone = new AdmPhone(
                    CsnConstants.ZERO,
                    referencePerson.getPhoneAccount(),
                    referenceDto.getPhone().getPhone(),
                    admTypologyRepository.findBy(CsnConstants.STATUS_ACTIVE),
                    phoneType,
                    referencePerson.getCreatedBy(),
                    CsnFunctions.now(),
                    Boolean.FALSE
            );
            referencePhone = admPhoneRepository.save(referencePhone);
            admPhoneRepository.resetLeader(referencePhone);

            //persisting reference on adm_reference table

            AdmReference reference = new AdmReference(
                    CsnConstants.ZERO,
                    credit.getReferenceAccount(),
                    referencePerson,
                    referencePerson.getCreatedBy(),
                    CsnFunctions.now(),
                    admTypologyRepository.findBy(referenceDto.getStatus().getTypologyId()),
                    admTypologyRepository.findBy(referenceDto.getKinship().getTypologyId()),
                    CsnConstants.ZERO
            );

            reference = admReferenceRepository.save(reference);

            return CsnFunctions.setResponse(reference.getReferenceId(),
                    Response.Status.CREATED, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //persist an existing reference
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/references/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistCreditReferenceExistingPerson(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            AdmReferenceDto referenceDto
    ) {

        try {
            if (referenceDto.getPerson() == null || referenceDto.getPerson().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person is mandatory");
            }

            if (referenceDto.getCreatedBy() == null || referenceDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmUser createdByUser = admUserRepository.findByKey(referenceDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            AdmTypology status = null;
            if (referenceDto.getStatus() != null && referenceDto.getStatus().getTypologyId() != null) {
                status = this.admTypologyRepository.findBy(referenceDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //persist reference (on person table)
            AdmPerson referencePerson = admPersonRepository.findByKey(referenceDto.getPerson().getPersonKey());
            if (referencePerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Person not found Error");
            }


            //check if reference is already on credit
            Boolean respuesta = admReferenceRepository.findReferenceAlreadyOnCredit(credit, referencePerson);
            if (respuesta) {
                //partner already on credit
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Reference already in this credit");
            }

            //TODO check phone usage
            //Persist phone
            if (referenceDto.getPhone() != null) {
                AdmTypology phoneType = null;
                if (referenceDto.getPhone().getType() != null) {
                    phoneType = admTypologyRepository.findBy(referenceDto.getPhone().getType().getTypologyId());
                } else {
                    phoneType = admTypologyRepository.findBy(CsnConstants.CELL_PHONE_ID);
                }
                AdmPhone referencePhone = new AdmPhone(
                        CsnConstants.ZERO,
                        referencePerson.getPhoneAccount(),
                        referenceDto.getPhone().getPhone(),
                        admTypologyRepository.findBy(CsnConstants.STATUS_ACTIVE),
                        phoneType,
                        referencePerson.getCreatedBy(),
                        CsnFunctions.now(),
                        Boolean.FALSE
                );
                referencePhone = admPhoneRepository.save(referencePhone);
            }

            //persisting reference on adm_reference table

            AdmReference reference = new AdmReference(
                    CsnConstants.ZERO,
                    credit.getReferenceAccount(),
                    referencePerson,
                    referencePerson.getCreatedBy(),
                    CsnFunctions.now(),
                    admTypologyRepository.findBy(referenceDto.getStatus().getTypologyId()),
                    admTypologyRepository.findBy(referenceDto.getKinship().getTypologyId()),
                    CsnConstants.ZERO
            );

            reference = admReferenceRepository.save(reference);

            return CsnFunctions.setResponse(reference.getReferenceId(),
                    Response.Status.CREATED, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //update an existing reference
    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/references/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response updateCreditReference(
            @PathParam("key") UUID creditKey,
            @PathParam("id") @DefaultValue("0") Long referenceId,
            AdmReferenceDto referenceDto
    ) {

        try {
            if (!referenceDto.getReferenceId().equals(referenceId)) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect ID");
            }
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Credit not found");
            }

            AdmReference currentReference = admReferenceRepository.findBy(referenceId);
            if (currentReference == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Reference not found");
            }

            //changes to reference person
            AdmPerson referencePerson = admPersonRepository.findByKey(referenceDto.getPerson().getPersonKey());
            if (referencePerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Reference person not found");
            }

            if (referenceDto.getPerson().getFirstName() != null) {
                referencePerson.setFirstName(referenceDto.getPerson().getFirstName());
            }

            if (referenceDto.getPerson().getLastName() != null) {
                referencePerson.setLastName(referenceDto.getPerson().getLastName());
            }

            if (referenceDto.getPerson().getNameComplete() != null) {
                referencePerson.setNameComplete(referenceDto.getPerson().getNameComplete());
            }

            //updating birthday
            if(referenceDto.getPerson().getBirthday() != null){
                referencePerson.setBirthday(referenceDto.getPerson().getBirthday());
            }

            admPersonRepository.save(referencePerson);

            //phone change
            if (referenceDto.getPhone() != null) {
                AdmPhone phone = admPhoneRepository.findBy(referenceDto.getPhone().getPhoneId());
                if (phone != null) {
                    if (referenceDto.getPhone().getPhone() != null) {
                        phone.setPhone(referenceDto.getPhone().getPhone());
                    }
                    if (referenceDto.getPhone().getType() != null) {
                        AdmTypology phoneType = admTypologyRepository.findBy(referenceDto.getPhone().getType().getTypologyId());
                        if (phoneType != null) {
                            phone.setType(phoneType);
                        }
                    }
                    //persist phone changes
                    admPhoneRepository.save(phone);
                }

            }

            //updating reference status
            if (referenceDto.getStatus() != null) {
                AdmTypology status = admTypologyRepository.findBy(referenceDto.getStatus().getTypologyId());
                if (status != null) {
                    currentReference.setStatus(status);
                }
            }

            //updating kinship
            if (referenceDto.getKinship() != null) {
                AdmTypology kinship = admTypologyRepository.findBy(referenceDto.getKinship().getTypologyId());
                if (kinship != null) {
                    currentReference.setStatus(kinship);
                }
            }

            //updating age
            if (referenceDto.getAge() != null) {
                currentReference.setAge(referenceDto.getAge());
            }

            //persisting changes
            admReferenceRepository.save(currentReference);

            return CsnFunctions.setResponse(currentReference.getReferenceId(),
                    Response.Status.OK, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //----------------CREDIT REFERENCE END OF ENDPOINTS---------------------
}
