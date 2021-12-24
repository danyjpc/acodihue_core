package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.inputs.agency.AdmAgencyDto;

import com.peopleapps.dto.inputs.agency.AdmAgencyUserControlDto;
import com.peopleapps.dto.organizationResponsible.AdmOrganizationResponsibleListDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/agencies/v1")
@Tag(name = "agencies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmAgenciesController {

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmSubOrganizationRepository admSubOrganizationRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    Logger logger;

    @Inject
    ResponseJson responseJson;


    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAgencies(
            @QueryParam("parent_key") UUID parent_key,
            @QueryParam("status") @DefaultValue("0") Long status,
            @QueryParam("sector") @DefaultValue("0") Long sector,
            @QueryParam("category") @DefaultValue("0") Long category,
            @QueryParam("state") @DefaultValue("0") Long state,
            @QueryParam("city") @DefaultValue("0") Long city,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {

            List<AdmAgencyDto> admOrganizations = admOrganizationRepository.getAllAgencies(
                    null,
                    CsnConstants.ACODIHUE_KEY,
                    status,
                    sector,
                    category,
                    state,
                    city,
                    desc
            );

            return Response.status(Response.Status.OK).entity(admOrganizations).build();

        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAgencyByKey(
            @PathParam("key") UUID key
    ) {
        try {

            List<AdmAgencyDto> admOrganizations = admOrganizationRepository.getAllAgencies(
                    key,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (admOrganizations.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity(admOrganizations.get(0)).build();

        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }


    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response createOrganizationAgency(@Valid AdmAgencyDto agencyDto) {
        try {

            AdmUser admCreatedBy = admUserRepository.findByKey(agencyDto.getCreatedBy().getPersonKey());

            if (admCreatedBy == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Usuario no encontrado");

            AdmOrganization agency = new AdmOrganization(
                    CsnConstants.ZERO,
                    agencyDto.getOrganizationName(),
                    "S/D",
                    admAddressAccountRepository.createAccount(),
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnFunctions.now(),
                    CsnFunctions.generateKey(),
                    "S/D",
                    Boolean.TRUE,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(
                            (agencyDto.getStatus() != null ? agencyDto.getStatus().getTypologyId() : CsnConstants.STATUS_ACTIVE)
                    ),
                    agencyDto.getEntryContribution(),
                    agencyDto.getEntryFee(),
                    00.00,
                    Boolean.FALSE,
                    null,
                    admCreatedBy,
                    agencyDto.getInternalCode() == null ? "C" : agencyDto.getInternalCode()
            );

            admOrganizationRepository.save(agency);

            /*add phones*/

            if (agencyDto.getAgencyPhone() != null) {
                AdmPhone phone = admPhoneRepository.save(new AdmPhone(
                        ((agencyDto.getAgencyPhone().getPhoneId() != null && agencyDto.getAgencyPhone().getPhoneId() > 0) ? agencyDto.getAgencyPhone().getPhoneId() : CsnConstants.ZERO),
                        agency.getPhoneAccount(),
                        (agencyDto.getAgencyPhone().getPhone() != null) ? agencyDto.getAgencyPhone().getPhone() : CsnConstants.ZERO,
                        new AdmTypology(
                                CsnConstants.STATUS_ACTIVE
                        ),
                        new AdmTypology(
                                (agencyDto.getAgencyPhone().getType() != null ? agencyDto.getAgencyPhone().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
                        ),
                        admCreatedBy,
                        CsnFunctions.now(),
                        false
                ));
                admPhoneRepository.resetLeader(phone);
            }

            /*add addresses*/
            if (agencyDto.getAgencyAddress() != null) {
                AdmAddress address = admAddressRepository.save(
                        new AdmAddress(
                                ((agencyDto.getAgencyAddress().getAddressId() != null && agencyDto.getAgencyAddress().getAddressId() > 0) ? agencyDto.getAgencyAddress().getAddressId() : CsnConstants.ZERO),
                                agency.getAddressAccount(),
                                agencyDto.getAgencyAddress().getAddressLine(),
                                "S/D",
                                new AdmTypology(
                                        CsnConstants.DEFAULT_COUNTRY_ID
                                ),
                                new AdmTypology(
                                        (agencyDto.getAgencyAddress().getState() != null ? agencyDto.getAgencyAddress().getState().getTypologyId() : CsnConstants.DEFAULT_STATE_ID)
                                ),
                                new AdmTypology(
                                        (agencyDto.getAgencyAddress().getCity() != null ? agencyDto.getAgencyAddress().getCity().getTypologyId() : CsnConstants.DEFAULT_CITY_ID)
                                ),
                                new AdmTypology(
                                        (agencyDto.getAgencyAddress().getZone() != null ? agencyDto.getAgencyAddress().getZone().getTypologyId() : CsnConstants.TYPOLOGY_EMPTY)
                                ),
                                new AdmTypology(
                                        CsnConstants.STATUS_ACTIVE
                                ),
                                new AdmTypology(
                                        (agencyDto.getAgencyAddress().getType() != null ? agencyDto.getAgencyAddress().getType().getTypologyId() : CsnConstants.CELL_PHONE_ID)
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


            /*generate sub organization id persist */

            admSubOrganizationRepository.save(
                    new AdmSubOrganization(
                            CsnConstants.ZERO,
                            admOrganizationRepository.getByKey(CsnConstants.ACODIHUE_KEY),
                            agency,
                            CsnFunctions.now(),
                            admCreatedBy
                    )
            );

            return CsnFunctions.setResponse(agency.getOrganizationKey(),
                    Response.Status.CREATED, null);

        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }

    }

    @POST
    @Path("/users")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response addUserToAgency(@Valid AdmAgencyUserControlDto admAgencyUserControlDto) {

        try {
            AdmPerson person = this.admPersonRepository.findByKey(admAgencyUserControlDto.getUser().getPersonKey());
            AdmOrganization organization = this.admOrganizationRepository.getByKey(admAgencyUserControlDto.getOrganization().getOrganizationKey());
            if (organization == null || person == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Invalid key");
            }
            AdmOrganizationResponsible admOrganizationResponsible = this.admOrganizationResponsibleRepository.findResponsible(organization, person);
            if (admOrganizationResponsible == null) {
                admOrganizationResponsible = new AdmOrganizationResponsible();
                admOrganizationResponsible.setOrganization(organization);
                admOrganizationResponsible.setPerson(person);
                admOrganizationResponsible.setDateCreated(CsnFunctions.now());
                admOrganizationResponsible.setIsResponsible(admAgencyUserControlDto.getIsResponsible());
                admOrganizationResponsible.setStatus(new AdmTypology(admAgencyUserControlDto.getStatus().getTypologyId()));

                if (admOrganizationResponsible.getIsResponsible() == Boolean.TRUE) {
                    //get organization responsibles and update their isResponsible field
                    List<AdmOrganizationResponsible> admOrganizationResponsibleList = this.admOrganizationResponsibleRepository.findOrganizationResponsibleByOrganization(organization);
                    if (admOrganizationResponsibleList != null) {
                        logger.error("Has responsibles");
                        for (AdmOrganizationResponsible item : admOrganizationResponsibleList) {
                            item.setIsResponsible(Boolean.FALSE);
                            this.admOrganizationResponsibleRepository.save(item);
                        }
                    }
                }
                //persist new record after updating existing records so the property will not get updated
                this.admOrganizationResponsibleRepository.save(admOrganizationResponsible);
            } else {

                //if record exists and status is inactive, change it to active as task 673 describes
                if (admOrganizationResponsible.getStatus().getTypologyId().equals(CsnConstants.STATUS_INACTIVE)) {

                    admOrganizationResponsible.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));

                } else {
                    admOrganizationResponsible.setStatus(new AdmTypology(admAgencyUserControlDto.getStatus().getTypologyId()));
                }
                admOrganizationResponsible.setIsResponsible(admAgencyUserControlDto.getIsResponsible());

                if (admOrganizationResponsible.getIsResponsible() == Boolean.TRUE) {
                    //get organization responsibles and update their isResponsible field
                    List<AdmOrganizationResponsible> admOrganizationResponsibleList = this.admOrganizationResponsibleRepository.findOrganizationResponsibleByOrganization(organization);
                    if (admOrganizationResponsibleList != null) {
                        for (AdmOrganizationResponsible item : admOrganizationResponsibleList) {

                            item.setIsResponsible(Boolean.FALSE);

                            this.admOrganizationResponsibleRepository.save(item);
                        }
                    }
                }
                //persist new record after updating existing records so the property will not get updated
                this.admOrganizationResponsibleRepository.save(admOrganizationResponsible);

            }
            return CsnFunctions.setResponse(admOrganizationResponsible.getOrganizationResponsibleId(), Response.Status.OK, "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/users")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response updateAgencyUser(@Valid AdmAgencyUserControlDto admAgencyUserControlDto) {

        try {
            AdmPerson person = this.admPersonRepository.findByKey(admAgencyUserControlDto.getUser().getPersonKey());
            AdmOrganization organization = this.admOrganizationRepository.getByKey(admAgencyUserControlDto.getOrganization().getOrganizationKey());
            if (organization == null || person == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Invalid key");
            }
            AdmOrganizationResponsible admOrganizationResponsible = this.admOrganizationResponsibleRepository.findResponsible(organization, person);
            if (admOrganizationResponsible == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Organization responsible not found");
            } else {
                admOrganizationResponsible.setStatus(new AdmTypology(admAgencyUserControlDto.getStatus().getTypologyId()));
                this.admOrganizationResponsibleRepository.save(admOrganizationResponsible);
            }
            return CsnFunctions.setResponse(admOrganizationResponsible.getOrganizationResponsibleId(), Response.Status.OK, "");

        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, ex.getMessage());
        }

    }

    @PUT
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("key") UUID key, @Valid AdmAgencyDto agencyDto) {

        try {

            if (!key.equals(agencyDto.getOrganizationKey())) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            AdmUser admCreatedBy = admUserRepository.findByKey(agencyDto.getCreatedBy().getPersonKey());

            if (admCreatedBy == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Usuario no encontrado");


            AdmOrganization agency = admOrganizationRepository.getByKey(agencyDto.getOrganizationKey());

            if (agency == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Agencia no encontrada");

            if (agencyDto.getOrganizationName() != null)
                agency.setOrganizationName(agencyDto.getOrganizationName());

            if (agencyDto.getEntryContribution() != null)
                agency.setEntryContribution(agencyDto.getEntryContribution());

            if (agencyDto.getEntryFee() != null)
                agency.setEntryFee(agencyDto.getEntryFee());

            if (agencyDto.getStatus() != null)
                agency.setStatus(new AdmTypology(agencyDto.getStatus().getTypologyId()));

            admOrganizationRepository.save(agency);

            /*add phones*/

            if (agencyDto.getAgencyPhone().getPhone() != null) {
                if (agencyDto.getAgencyPhone().getPhoneId() != null && agencyDto.getAgencyPhone().getPhoneId() > 0) {
                    AdmPhone phone1 = admPhoneRepository.findBy(agencyDto.getAgencyPhone().getPhoneId());

                    if (agencyDto.getAgencyPhone().getPhone() != null)
                        phone1.setPhone(agencyDto.getAgencyPhone().getPhone());

                    if (agencyDto.getAgencyPhone().getPhone() != null)
                        phone1.setPhone(agencyDto.getAgencyPhone().getPhone());

                    phone1 = admPhoneRepository.save(phone1);
                    admPhoneRepository.resetLeader(phone1);
                }
            }


            if (agencyDto.getAgencyAddress() != null) {
                if (agencyDto.getAgencyAddress().getAddressId() != null && agencyDto.getAgencyAddress().getAddressId() > 0) {
                    AdmAddress address1 = admAddressRepository.findBy(agencyDto.getAgencyAddress().getAddressId());

                    if (agencyDto.getAgencyAddress().getAddressLine() != null)
                        address1.setAddressLine(agencyDto.getAgencyAddress().getAddressLine());

                    if (agencyDto.getAgencyAddress().getType() != null)
                        address1.setType(new AdmTypology(agencyDto.getAgencyAddress().getType().getTypologyId()));

                    if (agencyDto.getAgencyAddress().getState() != null)
                        address1.setState(new AdmTypology(agencyDto.getAgencyAddress().getState().getTypologyId()));

                    if (agencyDto.getAgencyAddress().getCity() != null)
                        address1.setCity(new AdmTypology(agencyDto.getAgencyAddress().getCity().getTypologyId()));

                    if (agencyDto.getAgencyAddress().getZone() != null)
                        address1.setZone(new AdmTypology(agencyDto.getAgencyAddress().getZone().getTypologyId()));

                    address1 = admAddressRepository.save(address1);
                    admAddressRepository.resetLeader(address1);
                }
            }

            agency = admOrganizationRepository.save(agency);
            return CsnFunctions.setResponse(agency.getOrganizationKey(),
                    Response.Status.OK, "Success!!");


        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @GET
    @Path("/users")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAgencies(
            @QueryParam("departamento") @DefaultValue("0") Long departamento,
            @QueryParam("municipio") @DefaultValue("0") Long municipio,
            @QueryParam("status") @DefaultValue("0") Long status,
            @QueryParam("descending") @DefaultValue("0") Boolean desc
    ) {
        try {

            List<AdmAgencyUserControlDto> admAgencyUserControlDtos = admOrganizationRepository.getAllAgenciesAndResponsibles(
                    null,
                    departamento,
                    municipio,
                    status,
                    desc
            );

            return Response.status(Response.Status.OK).entity(admAgencyUserControlDtos).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @GET
    @Path("/users/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getUsersByAgency(
            @PathParam("key") UUID key
    ) {
        try {

            List<AdmAgencyUserControlDto> admAgencyUserControlDtos = admOrganizationRepository.getAllAgenciesAndResponsibles(
                    key,
                    null,
                    null,
                    null,
                    null
            );

            if (admAgencyUserControlDtos.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity(admAgencyUserControlDtos).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

}
