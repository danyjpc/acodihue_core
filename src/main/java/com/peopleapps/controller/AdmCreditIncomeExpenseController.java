package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.balanceSheet.AdmBalanceSheetDto;
import com.peopleapps.dto.creditIncomeExpense.AdmCreditIncomeExpenseDto;
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
import java.math.BigDecimal;
import java.util.UUID;

@Path("/credits/v1")
@Tag(name = "creditIncomeExpense")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmCreditIncomeExpenseController {

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmCreditIncomeExpenseRepository admCreditIncomeExpenseRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    Logger logger;

    @Inject
    ResponseJson responseJson;

    //get credit incomes for income (ingreso)
    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/credit_income_expense/income")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getCreditIncome(
            @PathParam("key") UUID creditKey,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {
            //check if credit key is valid
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");
            }

            AdmCreditIncomeExpenseDto admBalanceSheetDto = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                    credit.getCreditId(),
                    //ingresos
                    CsnConstants.INCOME_TYPE,
                    statusId,
                    desc
            );

            return Response.status(Response.Status.OK).entity(admBalanceSheetDto).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    //get credit incomes for expense (gasto)
    @GET
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/credit_income_expense/expense")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getCreditExpense(
            @PathParam("key") UUID creditKey,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {
            //check if credit key is valid
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "credito no encontrado");
            }

            AdmCreditIncomeExpenseDto admBalanceSheetDto = admCreditIncomeExpenseRepository.getCreditIncomeExpenseById(
                    credit.getCreditId(),
                    //ingresos
                    CsnConstants.EXPENSE_TYPE,
                    statusId,
                    desc
            );

            return Response.status(Response.Status.OK).entity(admBalanceSheetDto).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }


    @POST
    @Path("/{key:[A-Za-z0-9-][A-Za-z0-9-]*}/credit_income_expense/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistBalanceSheet(
            @PathParam("key") UUID creditKey,
            AdmCreditIncomeExpenseDto creditIncomeExpenseDto
    ) {
        try {
            //check if credit key is valid
            AdmCredit credit = admCreditRepository.findByKey(creditKey);
            if (credit == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Credito no encontrado");
            }

            //default user
            AdmUser createdBy = admUserRepository.findBy(CsnConstants.DEFAULT_USER_ID);
            if (createdBy == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Usuario no encontrado");
            }

            //Active status
            AdmTypology activeStatus = admTypologyRepository.findBy(CsnConstants.STATUS_ACTIVE);
            for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount account : creditIncomeExpenseDto.getAccounts()) {

                //check if credit already has that account assigned, then we update the amount

                AdmCreditIncomeExpense creditIncomeExpense = admCreditIncomeExpenseRepository.getCreditIncomeExpense(credit.getCreditId(), account.getAccount().getTypologyId());
                if (creditIncomeExpense != null) {
                    //update amount by adding values
                    //ignore negative values
                    //BegDecimal.compareTo returns:
                    // 0 if equal
                    //1 if greater than
                    //-1 if less than
                    if(account.getAmount().compareTo(new BigDecimal("0")) > 0){
                        creditIncomeExpense.setAmount(account.getAmount());
                    }

                }
                //we persist a new object
                else {
                    creditIncomeExpense = new AdmCreditIncomeExpense(
                            //Id
                            CsnConstants.ZERO,
                            //account type
                            admTypologyRepository.findBy(account.getAccount().getTypologyId()),
                            //amount
                            account.getAmount(),
                            //credit
                            credit,
                            createdBy,
                            //Date created
                            CsnFunctions.now(),
                            activeStatus
                    );
                }

                //save only if amount is greater than 0
                if(creditIncomeExpense.getAmount().compareTo(new BigDecimal("0")) == 1){
                    admCreditIncomeExpenseRepository.save(creditIncomeExpense);
                }

            }

            return Response.status(Response.Status.CREATED).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en al crear registro");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

}
