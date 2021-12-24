package com.peopleapps.controller.credit;

import com.peopleapps.dto.address.AdmAddressDto;
import com.peopleapps.model.AdmAddress;
import com.peopleapps.model.AdmCredit;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmUser;
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
import java.util.UUID;


@Path("/credits/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "credit_addresses")
public class AdmCreditAddressController {
    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmAddressRepository admAddressRepository;


    @Inject
    Logger logger;


    //----------------CREDIT ADDRESS ENDPOINTS------------------------------

    //Get addresses for associate
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/current")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAddressesByAssociate(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        AdmCredit credit = admCreditRepository.findByKey(creditKey);
        if (credit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
        }

        logger.error(credit.getCalculator().getPerson().getPersonKey().toString());

        List<AdmAddressDto> admReferenceList = admAddressRepository.getAllAddressesByAssociateDto(
                credit.getCalculator().getPerson().getPersonKey(),
                null, null, desc, null);

        return Response.status(Response.Status.OK).entity(admReferenceList).build();
    }

    //Get addresses for credit
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAddressesByCredit(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("status") @DefaultValue("0") Long statusId
    ) {
        //get associate key
        AdmCredit credit = admCreditRepository.findByKey(creditKey);
        if (credit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
        }

        logger.error(credit.getCalculator().getPerson().getPersonKey().toString());

        List<AdmAddressDto> admReferenceList = admAddressRepository.getAllAddressesByAddressAccountDto(
                creditKey, statusId, desc,
                credit.getAddressAccount().getAddressAccountId(), null);

        return Response.status(Response.Status.OK).entity(admReferenceList).build();
    }


    //Get addresses for credit by id
    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAddressesByCreditById(
            @PathParam("key") UUID creditKey,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @PathParam("id") @DefaultValue("0") Long addressId
    ) {
        //get associate key
        AdmCredit credit = admCreditRepository.findByKey(creditKey);
        if (credit == null) {
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found");
        }

        logger.error(credit.getCalculator().getPerson().getPersonKey().toString());

        List<AdmAddressDto> admAddressDtoList = admAddressRepository.getAllAddressesByAddressAccountDto(
                creditKey, null, desc,
                credit.getAddressAccount().getAddressAccountId(), addressId);

        if (admAddressDtoList.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(admAddressDtoList.get(0)).build();
    }

    //Persist new credit address
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/new")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistNewCreditAddress(
            @PathParam("key") UUID creditKey,
            AdmAddressDto addressDto
    ) {
        try {

            AdmCredit credit = this.admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
            }

            if (addressDto.getCreatedBy() == null || addressDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(addressDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            if (addressDto.getState() == null || addressDto.getCity() == null
                    || addressDto.getVillage() == null || addressDto.getZone() == null
                    || addressDto.getAddressLine() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incomplete data");
            }
            AdmTypology state = this.admTypologyRepository.findBy(addressDto.getState().getTypologyId());
            if (state == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect state");
            }

            AdmTypology city = this.admTypologyRepository.findBy(addressDto.getCity().getTypologyId());
            if (city == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect city");
            }
            AdmTypology village = this.admTypologyRepository.findBy(addressDto.getVillage().getTypologyId());
            if (village == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect village");
            }
            AdmTypology zone = this.admTypologyRepository.findBy(addressDto.getZone().getTypologyId());
            if (zone == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Inorrect zone");
            }

            AdmTypology status = null;
            if (addressDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(addressDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmAddress address = new AdmAddress(
                    0L,
                    credit.getAddressAccount(),
                    addressDto.getAddressLine(),
                    CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    state,
                    city,
                    zone,
                    status,
                    new AdmTypology(CsnConstants.ADDRESS_TYPE_HOME), //address type
                    createdByUser,
                    CsnFunctions.now(),
                    addressDto.getLeader() != null && addressDto.getLeader(),
                    CsnConstants.ZERO,
                    CsnConstants.ZERO,
                    CsnConstants.ZERO,
                    CsnConstants.DEFAULT_TEXT_SD,
                    credit.getDocumentAccount(),
                    village,
                    CsnConstants.DEFAULT_TEXT_SD
            );
            address = admAddressRepository.save(address);
            admAddressRepository.resetLeader(address);
            return CsnFunctions.setResponse(address.getAddressId(),
                    Response.Status.CREATED, null);
        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //Persist an existing address to a credit
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistCreditAddress(
            @PathParam("key") UUID creditKey,
            AdmAddressDto addressDto
    ) {
        try {
            AdmCredit credit = this.admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
            }

            if (addressDto.getCreatedBy() == null || addressDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found Error");
            }

            AdmAddress address = admAddressRepository.findBy(addressDto.getAddressId());
            if (address == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Address not found Error");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(addressDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found Error");
            }

            AdmTypology status = null;
            if (addressDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(addressDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //Setting address values
            address.setAddressId(CsnConstants.ZERO);
            address.setAddressAccount(credit.getAddressAccount());
            address.setDocumentAcount(credit.getDocumentAccount());
            address.setCreatedBy(createdByUser);
            address.setDateCreated(CsnFunctions.now());
            address.setStatus(status);

            //persisting address
            address = admAddressRepository.save(address);
            admAddressRepository.resetLeader(address);

            return CsnFunctions.setResponse(address.getAddressId(),
                    Response.Status.CREATED, null);
        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    //Persist an existing address to a credit
    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/addresses/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response updateCreditAddress(
            @PathParam("key") UUID creditKey,
            @PathParam("id") @DefaultValue("0") Long addressId,
            AdmAddressDto addressDto
    ) {
        try {

            if (addressDto.getAddressId() == null || addressDto.getAddressId() != addressId) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect ID");
            }
            AdmCredit credit = this.admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credit not found Error");
            }

            AdmAddress currentAddress = admAddressRepository.findBy(addressDto.getAddressId());
            if (currentAddress == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Address not found Error");
            }


            //updating status
            AdmTypology status = null;
            if (addressDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(addressDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //updating departamento (state)
            if (addressDto.getState() != null && addressDto.getState().getTypologyId() != null) {
                AdmTypology state = admTypologyRepository.findBy(addressDto.getState().getTypologyId());
                if (state != null) {
                    currentAddress.setState(state);
                }
            }

            //updating municipio (city)
            if (addressDto.getCity() != null && addressDto.getCity().getTypologyId() != null) {
                AdmTypology city = admTypologyRepository.findBy(addressDto.getCity().getTypologyId());
                if (city != null) {
                    currentAddress.setCity(city);
                }
            }

            //updating zone
            if (addressDto.getZone() != null && addressDto.getZone().getTypologyId() != null) {
                AdmTypology zone = admTypologyRepository.findBy(addressDto.getZone().getTypologyId());
                if (zone != null) {
                    currentAddress.setZone(zone);
                }
            }

            //updating aldea (village)
            if (addressDto.getVillage() != null && addressDto.getVillage().getTypologyId() != null) {
                AdmTypology village = admTypologyRepository.findBy(addressDto.getVillage().getTypologyId());
                if (village != null) {
                    currentAddress.setVillage(village);
                }
            }

            //updating leader
            if (addressDto.getLeader() != null) {
                currentAddress.setLeader(addressDto.getLeader());
            }

            //updating address line
            if (addressDto.getAddressLine() != null
                    && !addressDto.getAddressLine().equals("")
                    && !addressDto.getAddressLine().equals(" ")) {
                currentAddress.setAddressLine(addressDto.getAddressLine());
            }

            currentAddress = admAddressRepository.save(currentAddress);
            admAddressRepository.resetLeader(currentAddress);

            return CsnFunctions.setResponse(currentAddress.getAddressId(),
                    Response.Status.OK, null);
        } catch (
                Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }
}
