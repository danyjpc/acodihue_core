package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.AdmAccountBalanceRepository;
import com.peopleapps.repository.AdmAccountRepository;
import com.peopleapps.repository.AdmTypologyRepository;
import com.peopleapps.repository.AdmUserRepository;
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
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

//@Path("/account_balances")
@Tag(name = "accountBalance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmAccountBalanceController {

    @Inject
    AdmAccountBalanceRepository admAccountBalanceRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmAccountRepository admAccountRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    Logger logger;


    @Inject
    ResponseJson responseJson;

    /*
    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @QueryParam("id") @DefaultValue("0") Long accountBalanceId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {
            List<AdmAccountBalanceDto> admAccountBalances = admAccountBalanceRepository.getAllAccountBalances(
                    accountBalanceId, desc
            );

            return Response.status(Response.Status.OK).entity(admAccountBalances).build();

        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAccountBalanceById(
            @PathParam("id") @DefaultValue("0") Long accountBalanceId
    ) {

        if (accountBalanceId == null || accountBalanceId == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            List<AdmAccountBalanceDto> admAccounts = admAccountBalanceRepository.getAllAccountBalances(
                    accountBalanceId, null
            );

            if (admAccounts.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();


            return Response.status(Response.Status.OK).entity(admAccounts.get(0)).build();
        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }


    @POST
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response createAccount(AdmAccountBalanceDto admAccountBalanceDto) {
        try {

            AdmAccount account = this.admAccountRepository.findBy(admAccountBalanceDto.getAccount_id());
            if (account == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Account not found");
            }
            if (admAccountBalanceDto.getCreatedBy() == null || admAccountBalanceDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(admAccountBalanceDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found");
            }

            AdmTypology transactionType = admTypologyRepository.findBy(admAccountBalanceDto.getTransactionType().getTypologyId());
            if (transactionType == null) {

                transactionType = new AdmTypology(CsnConstants.REGULAR_SAVINGS_ACCOUNT);
            }

            AdmTypology status = admTypologyRepository.findBy(admAccountBalanceDto.getStatus().getTypologyId());
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            //TODO generate transaction number
            AdmAccountBalance newAccountBalance = new AdmAccountBalance(
                    0L,
                    account,
                    CsnFunctions.now().toEpochSecond(ZoneOffset.UTC) + new Random().nextInt(1000),
                    admAccountBalanceDto.getAmount(),
                    transactionType,
                    createdByUser,
                    status,
                    CsnFunctions.now(),
                    admAccountBalanceDto.getAnnotation()
            );

            admAccountBalanceRepository.save(newAccountBalance);
            return CsnFunctions.setResponse(newAccountBalance.getAccountBalanceId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("id") Long id, AdmAccountBalanceDto admAccountBalanceDto) {

        try {
            if (!admAccountBalanceDto.getAccount_balance_id().equals(id)) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "ID not found");
            }

            AdmAccountBalance currentAccountBalance = admAccountBalanceRepository.findBy(admAccountBalanceDto.getAccount_balance_id());
            if (currentAccountBalance == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Account not found");
            }

            if (admAccountBalanceDto.getAccount_id() != null) {
                AdmAccount account = admAccountRepository.findBy(admAccountBalanceDto.getAccount_id());
                if (account != null) {
                    currentAccountBalance.setAccount(account);
                }
            }

            if (admAccountBalanceDto.getAmount() != null) {
                currentAccountBalance.setAmount(admAccountBalanceDto.getAmount());
            }

            if (admAccountBalanceDto.getTransactionType() != null) {
                AdmTypology transactionType = admTypologyRepository.findBy(admAccountBalanceDto.getTransactionType().getTypologyId());
                if (transactionType != null) {
                    currentAccountBalance.setTransactionType(transactionType);
                }
            }

            if (admAccountBalanceDto.getStatus() != null) {
                AdmTypology status = admTypologyRepository.findBy(admAccountBalanceDto.getStatus().getTypologyId());
                if (status != null) {
                    currentAccountBalance.setStatus(status);
                }
            }

            admAccountBalanceRepository.save(currentAccountBalance);
            return CsnFunctions.setResponse(currentAccountBalance.getAccountBalanceId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

     */

}
