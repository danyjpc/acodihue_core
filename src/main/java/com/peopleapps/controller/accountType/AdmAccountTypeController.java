package com.peopleapps.controller.accountType;

import com.peopleapps.dto.inputs.associate.AdmAssociateAddressDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.typology.AdmTypologyListDto;
import com.peopleapps.model.AdmAddress;
import com.peopleapps.model.AdmPerson;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Path("/account_types/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "account type crud")
public class AdmAccountTypeController {
    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    Logger logger;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("estado") @DefaultValue("0") Long accountStatus,
            @QueryParam("descending") @DefaultValue("true") Boolean desc

    ) {
        List<AdmTypologyDto> admAccountList = admTypologyRepository.getAllAccounts(
                accountStatus, desc, null);

        return Response.status(Response.Status.OK).entity(admAccountList).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getById(
            @PathParam("id") @DefaultValue("0") Long accountTypeId
    ) {

        if (accountTypeId == null || accountTypeId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<AdmTypologyDto> admAccountList = admTypologyRepository.getAllAccounts(
                null, null, accountTypeId);

        if (admAccountList.size() == 0)
            return Response.status(Response.Status.OK).entity(admAccountList).build();

        return Response.status(Response.Status.OK).entity(admAccountList).build();

    }

    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(AdmTypologyDto admTypologyDto) {
        try {
            if (admTypologyDto.getDescription() == null || admTypologyDto.getDescription().equals("")) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Description is necesary");

            }
            //we asume the number is incorrect
            boolean correctNumber = false;
            try {
                if (admTypologyDto.getValue3() != null) {
                    //try to convert string to number
                    BigDecimal number = new BigDecimal(admTypologyDto.getValue3());
                    //check if number is zero, correct
                    if (number.compareTo(new BigDecimal("0")) == 0) {
                        correctNumber = true;
                    }
                    //if number is less than 0 it is not correct
                    else if (number.compareTo(new BigDecimal("0")) == -1) {
                        correctNumber = false;
                    } else {
                        //number is correct
                        correctNumber = true;
                    }
                }
            } catch (Exception ex) {
                // if error, the number is incorrect
                correctNumber = false;
            }
            if (admTypologyDto.getValue3() == null
                    || !correctNumber) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Percentage is necesary or incorrect value");
            }

            AdmTypology newAccount = new AdmTypology(
                    CsnConstants.ZERO,
                    admTypologyDto.getDescription(),
                    new AdmTypology(CsnConstants.ACCOUNT_TYPE_TYPOLOGY),
                    admTypologyDto.getValue1() == null ? CsnConstants.DEFAULT_TEXT_SD : admTypologyDto.getValue1(),
                    admTypologyDto.getValue2() == null ? CsnConstants.DEFAULT_TEXT_SD : admTypologyDto.getValue2(),
                    Boolean.TRUE,
                    Boolean.TRUE,
                    admTypologyDto.getValue3() == null ? CsnConstants.DEFAULT_TEXT_SD : admTypologyDto.getValue3().toString()
            );


            newAccount = admTypologyRepository.save(newAccount);
            return CsnFunctions.setResponse(newAccount.getTypologyId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error, Try again");
        }
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response update(@PathParam("id") @DefaultValue("0") Long accountTypeId,
                           AdmTypologyDto admTypologyDto) {
        try {
            if (admTypologyDto.getTypologyId() == null || !admTypologyDto.getTypologyId().equals(accountTypeId)) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Incorrect ID");
            }

            AdmTypology currentAccount = this.admTypologyRepository.findBy(admTypologyDto.getTypologyId());
            if (currentAccount == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Account Type not found");
            }

            boolean correctNumber = false;
            try {
                if (admTypologyDto.getValue3() != null) {
                    //try to convert string to number
                    BigDecimal number = new BigDecimal(admTypologyDto.getValue3());
                    //check if number is zero, correct
                    if (number.compareTo(new BigDecimal("0")) == 0) {
                        correctNumber = true;
                    }
                    //if number is less than 0 it is not correct
                    else if (number.compareTo(new BigDecimal("0")) == -1) {
                        correctNumber = false;
                    } else {
                        //number is correct
                        correctNumber = true;
                    }
                }
            } catch (Exception ex) {
                // if error, the number is incorrect
                correctNumber = false;
            }
            if (!correctNumber) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Percentage is necesary or incorrect value");
            }

            if (admTypologyDto.getDescription() != null) {
                currentAccount.setDescription(admTypologyDto.getDescription());
            }

            if (admTypologyDto.getValue1() != null) {
                currentAccount.setValue1(admTypologyDto.getValue1());
            }

            if (admTypologyDto.getValue2() != null) {
                currentAccount.setValue2(admTypologyDto.getValue2());
            }

            if (admTypologyDto.getValue3() != null) {
                currentAccount.setValue3(admTypologyDto.getValue3().toString());
            }

            if (admTypologyDto.getAvailable() != null) {
                currentAccount.setAvailable(admTypologyDto.getAvailable());
            }

            if (admTypologyDto.getEditable() != null) {
                currentAccount.setEditable(admTypologyDto.getEditable());
            }

            currentAccount = admTypologyRepository.save(currentAccount);
            return CsnFunctions.setResponse(currentAccount.getTypologyId(),
                    Response.Status.OK, null);
        } catch (Exception ex) {
            ex.printStackTrace();

            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Error, Try again");
        }
    }
}

