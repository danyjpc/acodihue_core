package com.peopleapps.controller;


import com.peopleapps.dto.inputs.associations.AdmAssociationsDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("/associations/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdmAssociationsController {

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAssociations(
            @QueryParam("status") @DefaultValue("0") Long status,
            @QueryParam("state") @DefaultValue("0") Long state,
            @QueryParam("city") @DefaultValue("0") Long city,
            @QueryParam("descending") @DefaultValue("0") Boolean desc
    ) {

        List<AdmAssociationsDto> admOrganizations = admOrganizationRepository.getAllAssociations(
                null,
                status,
                state,
                city,
                desc
        );

        return Response.status(Response.Status.OK).entity(admOrganizations).build();

    }

    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAssociationByKey(
            @PathParam("key") UUID key
    ) {

        List<AdmAssociationsDto> admOrganizations = admOrganizationRepository.getAllAssociations(
                key,
                null,
                null,
                null,
                null
        );

        if (admOrganizations.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(admOrganizations.get(0)).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response createAssociation(AdmAssociationsDto associationsDto) {

        try {
            AdmUser admCreatedBy = admUserRepository.findByKey(associationsDto.getCreatedBy().getPersonKey());

            if (admCreatedBy == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Usuario no encontrado");

            AdmOrganization association = new AdmOrganization(
                    CsnConstants.ZERO,
                    associationsDto.getAssociation().getOrganizationName(),
                    "S/D",
                    admAddressAccountRepository.createAccount(),
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnFunctions.now(),
                    CsnFunctions.generateKey(),
                    "S/D",
                    Boolean.FALSE,
                    Boolean.TRUE,
                    Boolean.FALSE,
                    new AdmTypology(
                            (associationsDto.getStatus() != null ? associationsDto.getStatus().getTypologyId() : CsnConstants.STATUS_ACTIVE)
                    ),
                    0.00,
                    0.00,

                    associationsDto.getAssociation().getInterestRate(),
                    Boolean.TRUE,
                    //null,
                    //admCreatedBy,
                    associationsDto.getInternalCode() == null ? "C" : associationsDto.getInternalCode()
            );

            association = admOrganizationRepository.save(association);

            if (associationsDto.getContactPhone() != null) {
                AdmPhone phone = admPhoneRepository.save(new AdmPhone(
                        ((associationsDto.getContactPhone().getPhoneId() != null && associationsDto.getContactPhone().getPhoneId() > 0) ? associationsDto.getContactPhone().getPhoneId() : CsnConstants.ZERO),
                        association.getPhoneAccount(),
                        associationsDto.getContactPhone().getPhone(),
                        new AdmTypology(
                                CsnConstants.STATUS_ACTIVE
                        ),
                        new AdmTypology(
                                (associationsDto.getContactPhone().getType() != null ? associationsDto.getContactPhone().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
                        ),
                        admCreatedBy,
                        CsnFunctions.now(),
                        false
                ));
                admPhoneRepository.resetLeader(phone);
            }

            if (associationsDto.getContactAddress() != null) {
                AdmAddress address = admAddressRepository.save(
                        new AdmAddress(
                                ((associationsDto.getContactAddress().getAddressId() != null && associationsDto.getContactAddress().getAddressId() > 0) ? associationsDto.getContactAddress().getAddressId() : CsnConstants.ZERO),
                                association.getAddressAccount(),
                                associationsDto.getContactAddress().getAddressLine(),
                                "S/D",
                                new AdmTypology(
                                        CsnConstants.DEFAULT_COUNTRY_ID
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getState() != null ? associationsDto.getContactAddress().getState().getTypologyId() : CsnConstants.DEFAULT_STATE_ID)
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getCity() != null ? associationsDto.getContactAddress().getCity().getTypologyId() : CsnConstants.DEFAULT_CITY_ID)
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getZone() != null ? associationsDto.getContactAddress().getZone().getTypologyId() : CsnConstants.TYPOLOGY_EMPTY)
                                ),
                                new AdmTypology(
                                        CsnConstants.STATUS_ACTIVE
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getType() != null ? associationsDto.getContactAddress().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
                                ),
                                admCreatedBy,
                                CsnFunctions.now(),
                                false,
                                0L,
                                0L,
                                0L,
                                "S/D",
                                admDocumentAccountRepository.createAccount(),
                                new AdmTypology(
                                        CsnConstants.TYPOLOGY_EMPTY
                                ),
                                "S/D"
                        )
                );
                admAddressRepository.resetLeader(address);
            }

            AdmPerson contact = new AdmPerson(
                    CsnConstants.ZERO,
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    admAddressAccountRepository.createAccount(),
                    admBeneficiaryAccountRepository.createAccount(),
                    "S/D",
                    "S/D",
                    "S/D",
                    "S/D",
                    "S/D",
                    LocalDate.now(),
                    "S/D",
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    CsnConstants.ZERO,
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    CsnConstants.ZERO,
                    "S/D",
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    "S/D",
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    ),
                    new AdmTypology(
                            CsnConstants.STATUS_ACTIVE
                    ),
                    admCreatedBy,
                    CsnFunctions.now(),
                    Boolean.FALSE,
                    Boolean.FALSE,
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    associationsDto.getContact().getNameComplete(),
                    Boolean.FALSE,
                    new AdmTypology(
                            CsnConstants.TYPOLOGY_EMPTY
                    )

            );

            contact = admPersonRepository.save(contact);
            logger.error(contact.getPersonId().toString());
            logger.error(association.getOrganizationId().toString());

            admOrganizationResponsibleRepository.save(
                    new AdmOrganizationResponsible(
                            CsnConstants.ZERO,
                            association,
                            contact,
                            CsnFunctions.now(),
                            false,
                            new AdmTypology(
                                    CsnConstants.STATUS_ACTIVE
                            )
                    )
            );

            return CsnFunctions.setResponse(association.getOrganizationKey(),
                    Response.Status.CREATED, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response editAssociation(@PathParam("key") UUID key, @Valid AdmAssociationsDto associationsDto) {

        try {

            if (!key.equals(associationsDto.getAssociation().getOrganizationKey())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            AdmUser admCreatedBy = admUserRepository.findByKey(associationsDto.getCreatedBy().getPersonKey());

            if (admCreatedBy == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Usuario no encontrado");

            AdmOrganization association = admOrganizationRepository.getByKey(associationsDto.getAssociation().getOrganizationKey());


            if (association == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Associacion no encontrada");

            if (associationsDto.getAssociation().getOrganizationName() != null)
                association.setOrganizationName(associationsDto.getAssociation().getOrganizationName());

            if (associationsDto.getAssociation().getInterestRate() != null)
                association.setInterestRate(associationsDto.getAssociation().getInterestRate());

            if (associationsDto.getStatus() != null)
                association.setStatus(new AdmTypology(associationsDto.getStatus().getTypologyId()));

            admOrganizationRepository.save(association);

            if (associationsDto.getContactPhone() != null) {
                AdmPhone phone = admPhoneRepository.save(new AdmPhone(
                        ((associationsDto.getContactPhone().getPhoneId() != null && associationsDto.getContactPhone().getPhoneId() > 0) ? associationsDto.getContactPhone().getPhoneId() : CsnConstants.ZERO),
                        association.getPhoneAccount(),
                        associationsDto.getContactPhone().getPhone(),
                        new AdmTypology(
                                CsnConstants.STATUS_ACTIVE
                        ),
                        new AdmTypology(
                                (associationsDto.getContactPhone().getType() != null ? associationsDto.getContactPhone().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
                        ),
                        admCreatedBy,
                        CsnFunctions.now(),
                        false
                ));
                admPhoneRepository.resetLeader(phone);
            }

            if (associationsDto.getContactAddress() != null) {
                AdmAddress cotactAddress = admAddressRepository.save(
                        new AdmAddress(
                                ((associationsDto.getContactAddress().getAddressId() != null && associationsDto.getContactAddress().getAddressId() > 0) ? associationsDto.getContactAddress().getAddressId() : CsnConstants.ZERO),
                                association.getAddressAccount(),
                                associationsDto.getContactAddress().getAddressLine(),
                                "S/D",
                                new AdmTypology(
                                        CsnConstants.DEFAULT_COUNTRY_ID
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getState() != null ? associationsDto.getContactAddress().getState().getTypologyId() : CsnConstants.DEFAULT_STATE_ID)
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getCity() != null ? associationsDto.getContactAddress().getCity().getTypologyId() : CsnConstants.DEFAULT_CITY_ID)
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getZone() != null ? associationsDto.getContactAddress().getZone().getTypologyId() : CsnConstants.TYPOLOGY_EMPTY)
                                ),
                                new AdmTypology(
                                        CsnConstants.STATUS_ACTIVE
                                ),
                                new AdmTypology(
                                        (associationsDto.getContactAddress().getType() != null ? associationsDto.getContactAddress().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
                                ),
                                admCreatedBy,
                                CsnFunctions.now(),
                                false,
                                0L,
                                0L,
                                0L,
                                "S/D",
                                admDocumentAccountRepository.createAccount(),
                                new AdmTypology(
                                        CsnConstants.TYPOLOGY_EMPTY
                                ),
                                "S/D"
                        )
                );
                admAddressRepository.resetLeader(cotactAddress);
            }

            if (associationsDto.getContact() != null) {
                AdmPerson personContact = admPersonRepository.findByKey(associationsDto.getContact().getPersonKey());

                if (personContact != null) {
                    if (associationsDto.getContact().getNameComplete() != null)
                        personContact.setNameComplete(associationsDto.getContact().getNameComplete());
                    admPersonRepository.save(personContact);
                }
            }

            return CsnFunctions.setResponse(association.getOrganizationKey(),
                    Response.Status.OK, null);

        } catch (Exception ex) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }

    }

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    @Path("/name_search")
    public Response getByName(
            @QueryParam("is_associatiation") @DefaultValue("0") Boolean isAssociation,
            @QueryParam("is_society") @DefaultValue("0") Boolean isSociety,
            @QueryParam("is_agency") @DefaultValue("0") Boolean isAgency,

            @QueryParam("organization_name") @DefaultValue("") String organizationName,
            @QueryParam("commercial_name") @DefaultValue("") String organizationCommercial
    ) {

        List<AdmAssociationsDto> admOrganizations = admOrganizationRepository.getAssociationByName(
                isAssociation,
                isSociety,
                isAgency,
                organizationName,
                organizationCommercial
        );

        return Response.status(Response.Status.OK).entity(admOrganizations).build();

    }

}
