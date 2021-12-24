package com.peopleapps.controller;

import com.peopleapps.dto.inputs.user.AdmUserRegistryDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Path("/users/v1")
@Tag(name = "users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmUserController {

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmOrganizationRepository admOrganizationRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(@QueryParam("descending") @DefaultValue("true") Boolean desc) {

        try {
            List<AdmUserRegistryDto> admUserList = admUserRepository.getAllUsersDTO(
                    null, null, null, desc);

            return Response.status(Response.Status.OK).entity(admUserList).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("key") UUID key
    ) {
        if (key == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            List<AdmUserRegistryDto> admUserList = admUserRepository.getAllUsersDTO(key, null, null, null);

            if (admUserList.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity(admUserList.get(0)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(@Valid AdmUserRegistryDto admUserRegistryDto) {
        try {


            if (admUserRegistryDto == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect Values");
            }

            Boolean personCreated = Boolean.FALSE;
            Boolean userCreated = Boolean.FALSE;


            AdmPerson person = null;
            //person already exists

            //person does not exist
            person = new AdmPerson();
            person = this.setPersonProperties(person, admUserRegistryDto);
            //before persisting person check if mail is in use
            AdmUser user = this.admUserRepository.findByEmail(person.getEmail());
            if (user != null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "email already in use");
            } else {
                //create and persist user
                user = new AdmUser();
                //persist person
                person = this.admPersonRepository.save(person);
                personCreated = Boolean.TRUE;
                user = this.setUserProperties(person, admUserRegistryDto);
                user = this.admUserRepository.save(user);
                userCreated = Boolean.TRUE;

                return CsnFunctions.setResponse(person.getPersonKey(), Response.Status.CREATED, "Person / User");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response update(@PathParam("key") UUID personKey, AdmUserRegistryDto admUserRegistryDto) {
        try {
            if (!admUserRegistryDto.getPerson().getPersonKey().equals(personKey)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            //check if person exists
            AdmPerson person = this.admPersonRepository.findByKey(admUserRegistryDto.getPerson().getPersonKey());
            if (person == null) {
                return CsnFunctions.setResponse(personKey, Response.Status.BAD_REQUEST, "Person not found");
            }
            //check if user exists
            AdmUser user = this.admUserRepository.findByKey(admUserRegistryDto.getPerson().getPersonKey());
            if (user == null) {
                return CsnFunctions.setResponse(personKey, Response.Status.BAD_REQUEST, "User not found");
            }

            //editing person properties

            if (admUserRegistryDto.getPerson().getFirstName() != null) {
                person.setFirstName(admUserRegistryDto.getPerson().getFirstName());
            }
            if (admUserRegistryDto.getPerson().getLastName() != null) {
                person.setLastName(admUserRegistryDto.getPerson().getLastName());
            }
            if (admUserRegistryDto.getPerson().getBirthday() != null) {
                person.setBirthday(admUserRegistryDto.getPerson().getBirthday());
            }
            if (admUserRegistryDto.getPerson().getStatus() != null) {
                AdmTypology personStatus = this.admTypologyRepository.findBy(admUserRegistryDto.getPerson().getStatus().getTypologyId());
                person.setStatus(personStatus);
            }

            //persisting person changes
            this.admPersonRepository.save(person);
            //editing user properties

            if (admUserRegistryDto.getPassword() != null) {
                user.setPassword(admUserRegistryDto.getPassword());
            }
            if (admUserRegistryDto.getRole() != null) {
                AdmTypology userRole = this.admTypologyRepository.findBy(admUserRegistryDto.getRole().getTypologyId());
                user.setRole(userRole);
            }
            if (admUserRegistryDto.getStatus() != null) {
                AdmTypology userStatus = this.admTypologyRepository.findBy(admUserRegistryDto.getStatus().getTypologyId());
                user.setStatus(userStatus);
            }
            //persisting user changes
            this.admUserRepository.save(user);

            return CsnFunctions.setResponse(admUserRegistryDto.getPerson().getPersonKey(), Response.Status.OK, "Updated");

        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error updating user");
        }
    }

    private AdmPerson setPersonProperties(AdmPerson person, AdmUserRegistryDto admUserRegistryDto) {
        AdmPhoneAccount phoneAccount = this.admPhoneAccountRepository.createAccount();
        AdmDocumentAccount documentAccount = this.admDocumentAccountRepository.createAccount();
        AdmAddressAccount addressAccount = this.admAddressAccountRepository.createAccount();
        AdmBeneficiaryAccount beneficiaryAccount = this.admBeneficiaryAccountRepository.createAccount();
        AdmUser createdBy = this.admUserRepository.findByKey(admUserRegistryDto.getCreatedBy().getPersonKey());

        if (admUserRegistryDto.getPerson().getFirstName() != null) {
            person.setFirstName(admUserRegistryDto.getPerson().getFirstName());
        }
        if (admUserRegistryDto.getPerson().getLastName() != null) {
            person.setLastName(admUserRegistryDto.getPerson().getLastName());
        }


        AdmPerson returnPerson = new AdmPerson(
                null,
                phoneAccount,
                documentAccount,
                addressAccount,
                beneficiaryAccount,
                person.getFirstName().toUpperCase(new Locale("es", "ES")),
                CsnConstants.DEFAULT_TEXT_SD,
                person.getLastName().toUpperCase(new Locale("es", "ES")),
                CsnConstants.DEFAULT_TEXT_SD,
                CsnConstants.DEFAULT_TEXT_SD,
                admUserRegistryDto.getPerson().getBirthday(),
                admUserRegistryDto.getPerson().getEmail(),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                admUserRegistryDto.getPerson().getCui(),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                CsnConstants.ZERO,
                CsnConstants.DEFAULT_TEXT_SD,
                new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                new AdmTypology(CsnConstants.DEFAULT_STATE_ID),
                new AdmTypology(CsnConstants.DEFAULT_CITY_ID),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                CsnConstants.DEFAULT_TEXT_SD,
                Boolean.FALSE,
                Boolean.FALSE,
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                new AdmTypology(admUserRegistryDto.getRole().getTypologyId()),
                new AdmTypology(admUserRegistryDto.getPerson().getStatus().getTypologyId()),
                createdBy,
                CsnFunctions.now(),
                Boolean.FALSE,
                Boolean.FALSE,
                CsnConstants.ZERO,
                CsnFunctions.generateKey(),
                admUserRegistryDto.getPerson().getFirstName().toUpperCase(new Locale("es", "ES"))
                        + " " + admUserRegistryDto.getPerson().getLastName().toUpperCase(new Locale("es", "ES")),
                Boolean.FALSE,
                new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
        );

        person = returnPerson;
        return person;
    }

    private AdmUser setUserProperties(AdmPerson person, AdmUserRegistryDto admUserRegistryDto) {
        AdmUser returnUser = new AdmUser(
                null,
                person,
                admUserRegistryDto.getPassword(),
                CsnFunctions.now(),
                new AdmTypology(admUserRegistryDto.getRole().getTypologyId()),
                new AdmTypology(admUserRegistryDto.getStatus().getTypologyId())
        );
        return returnUser;
    }


}
