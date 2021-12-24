package com.peopleapps.controller;

import com.peopleapps.dto.guarantee.AdmGuaranteeDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmGuaranteeController {

    @Inject
    AdmGuaranteeRepository admGuaranteeRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmAddressRepository admAddressRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    Logger logger;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/guarantee")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID key
    ) {

        try {

            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(admCredit.getCreditId(), 0L);

            return Response.status(Response.Status.OK).entity(admGuaranteeList).build();

        } catch (Exception ex) {

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error en la consulta");
        }
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/guarantee/{id}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID key,
            @PathParam("id") @DefaultValue("0") Long id
    ) {
        try {
            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAll(admCredit.getCreditId(), id);

            if (admGuaranteeList.size() > 0)
                return Response.status(Response.Status.OK).entity(admGuaranteeList.get(0)).build();


            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "No encontrado");

        } catch (Exception ex) {

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error en la consulta");
        }
    }


    //T1460
    //https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1460/
    //Metodo que obtiene garantias asociadas a otros creditos del asociado

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/guarantee/associate")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getGuaranteesByAssociate(
            @PathParam("key") UUID key
    ) {
        try {
            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            List<AdmGuarantee> admGuaranteeList = admGuaranteeRepository.getAllGuaranteesByAssociate(admCredit.getCalculator().getPerson().getPersonId());


            return Response.status(Response.Status.OK).entity(admGuaranteeList).build();


        } catch (Exception ex) {

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error en la consulta");
        }
    }


    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/guarantee")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(
            AdmGuarantee admGuarantee,
            @PathParam("key") UUID key
    ) {

        try {

            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");

            AdmUser admUser = admUserRepository.findByKey(admGuarantee.getCreatedBy().getPersonKey());

            if (admUser == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "usuario no encontrado");

            admGuarantee.setAddressAccount(admAddressAccountRepository.createAccount());



            /*check evaluator*/
            AdmPerson evaluator = new AdmPerson();
//            logger.info(admGuarantee.getEvaluator().toString());
            if (admGuarantee.getEvaluator() != null) {
                logger.info("entra aqui-------------------------------------------");
                if (admGuarantee.getEvaluator().getPersonKey() != null)
                    evaluator = admPersonRepository.findByKey(admGuarantee.getEvaluator().getPersonKey());

                if (evaluator == null)
                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "evaluador no encontrado");

                admGuarantee.setEvaluator(evaluator);
                logger.info("entra aqui B-------------------------------------------");
            } else {
                admGuarantee.setEvaluator(null);
                logger.info("entra aqui C-------------------------------------------");
            }

            admGuarantee.setGuaranteeId(CsnConstants.ZERO);
            admGuarantee.setNoYear(CsnFunctions.getYear());
            admGuarantee.setNoReference(admGuaranteeRepository.getMax());
            admGuarantee.setCredit(admCredit);
            admGuarantee.setDateCreated(CsnFunctions.now());
            admGuarantee.setCreatedBy(admUser);

            admGuaranteeRepository.checkModel(admGuarantee);

            admGuaranteeRepository.save(admGuarantee);

            /*Create Address*/
            admGuarantee.getAddress().setAddressId(CsnConstants.ZERO);
            admGuarantee.getAddress().setCountry(new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID));
            admGuarantee.getAddress().setDocumentAcount(admDocumentAccountRepository.createAccount());
            admGuarantee.getAddress().setAddressAccount(admGuarantee.getAddressAccount());

            if (admGuarantee.getAddress().getState() == null)
                admGuarantee.getAddress().setState(new AdmTypology(CsnConstants.DEFAULT_STATE_ID));

            if (admGuarantee.getAddress().getCity() == null)
                admGuarantee.getAddress().setCity(new AdmTypology(CsnConstants.DEFAULT_CITY_ID));

            if (admGuarantee.getAddress().getVillage() == null)
                admGuarantee.getAddress().setVillage(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));

            if (admGuarantee.getAddress().getZone() == null)
                admGuarantee.getAddress().setZone(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));

            if (admGuarantee.getAddress().getType() == null)
                admGuarantee.getAddress().setType(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));


            if (admGuarantee.getAddress().getStatus() == null)
                admGuarantee.getAddress().setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));

            if (admGuarantee.getAddress().getLeader() == null)
                admGuarantee.getAddress().setLeader(Boolean.TRUE);

            if (admGuarantee.getAddress().getNoFarm() == null)
                admGuarantee.getAddress().setNoFarm(CsnConstants.ZERO);

            if (admGuarantee.getAddress().getExtension() == null)
                admGuarantee.getAddress().setExtension(CsnConstants.ZERO);

            if (admGuarantee.getAddress().getNoFolder() == null)
                admGuarantee.getAddress().setNoFolder(CsnConstants.ZERO);

            if (admGuarantee.getAddress().getNoPublic() == null)
                admGuarantee.getAddress().setNoPublic(CsnConstants.DEFAULT_TEXT_SD);

            if (admGuarantee.getAddress().getAddressLine() == null)
                admGuarantee.getAddress().setAddressLine(CsnConstants.DEFAULT_TEXT_SD);

            if (admGuarantee.getAddress().getAddressLine2() == null)
                admGuarantee.getAddress().setAddressLine2(CsnConstants.DEFAULT_TEXT_SD);

            admGuarantee.getAddress().setDateCreated(CsnFunctions.now());
            admGuarantee.getAddress().setCreatedBy(admGuarantee.getCreatedBy());

            admGuarantee.setAddress(admAddressRepository.save(admGuarantee.getAddress()));
            admAddressRepository.resetLeader(admGuarantee.getAddress());


            return CsnFunctions.setResponse(admGuarantee.getGuaranteeId(), Response.Status.CREATED, "Garantia Creada");

        } catch (final JsonParsingException ex) {

            logger.error(ex.getMessage(), ex);

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "error al insertar");

        } catch (Exception ex) {

//            logger.info(admGuarantee.toString());
            logger.info(ex.getMessage());

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "error al insertar");
        }
    }

    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/guarantee/{id}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(
            AdmGuarantee admGuarantee,
            @PathParam("key") UUID key,
            @PathParam("id") @DefaultValue("0") Long id
    ) {

        try {

            if (!id.equals(admGuarantee.getGuaranteeId()))
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Garantia no coincide");


            AdmCredit admCredit = admCreditRepository.findByKey(key);

            if (admCredit == null)
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");


            AdmGuarantee admGuarantee1 = admGuaranteeRepository.findBy(admGuarantee.getGuaranteeId());

            /*check evaluator*/
            AdmPerson evaluator = new AdmPerson();
            if (admGuarantee.getEvaluator() != null) {
                evaluator = admPersonRepository.findByKey(admGuarantee.getEvaluator().getPersonKey());

                if (evaluator == null)
                    return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "evaluador no encontrado");

                admGuarantee.setEvaluator(evaluator);
            }

            /*update guarantee*/
            admGuaranteeRepository.edit(admGuarantee);

            /*update Address*/

            AdmAddress address = admAddressRepository.getByAddressAccount(admGuarantee1.getAddressAccount().getAddressAccountId());
            admGuarantee.getAddress().setAddressId(address.getAddressId());

            admAddressRepository.saveEdit(admGuarantee.getAddress());

            return CsnFunctions.setResponse(admGuarantee.getGuaranteeId(), Response.Status.CREATED, "Actividad Creada");

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "error al insertar");
        }
    }

}
