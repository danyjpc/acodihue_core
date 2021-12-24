package com.peopleapps.controller;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchDto;
import com.peopleapps.repository.AdmAccountBalanceRepository;
import com.peopleapps.repository.AdmAccountRepository;
import com.peopleapps.repository.AdmCreditRepository;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.StringMatcher;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("global-search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmGlobalSearchController {

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmAccountRepository admAccountRepository;

    @Inject
    AdmAccountBalanceRepository admAccountBalanceRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @GET
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response globalSearch(@QueryParam("search") @DefaultValue("%") String inputSearch) {
        try {
            Boolean isPerson = false;
            Boolean isAccount = false;
            Boolean isCreditInternalNumber = false;

            AdmGlobalSearchDto globalSearchResults = new AdmGlobalSearchDto();
            //checking if isPerson
            if (StringMatcher.isDpi(inputSearch) || (StringMatcher.isName(inputSearch) && (inputSearch.trim().length() > 0))
                    || StringMatcher.isNit(inputSearch) || StringMatcher.isAssociateNumber(inputSearch)) {
                isPerson = true;
            }

            //checking if is account
            if (StringMatcher.isAccountNumber(inputSearch)) {
                isAccount = true;
            }

            //checking if is credit internal number
            if (StringMatcher.isCreditInternalCode(inputSearch)) {
                isCreditInternalNumber = true;
            }

            if (isPerson) {
                globalSearchResults = admPersonRepository.globalSearch(inputSearch);
            }

            //if isAccount and records is null or empty
            if (isAccount && (globalSearchResults.getRecords() == null || globalSearchResults.getRecords().size() == 0)) {
                globalSearchResults = admAccountRepository.globalSearch(inputSearch);

                for (AdmGlobalSearchDto.AdmGlobalSearchRecord account : globalSearchResults.getRecords()) {
                    //setting balance
                    List<AdmAccountBalanceInfoDto> movements = admAccountBalanceRepository.getMovementsByAccount(
                            account.getAccount().getPersonAccount().getOrganizationResponsible().getPerson().getPersonKey(),
                            account.getAccount().getPersonAccount().getAccount_id(),
                            CsnConstants.STATUS_ACTIVE,
                            Boolean.TRUE,
                            0L,
                            0L,
                            //TODO hacer cortes de saldo
                            "1900-01-01",
                            "9999-01-01"
                    );
                    Double balance = 0.00;
                    if (movements.size() > 0) {
                        balance = movements.get(movements.size() - 1).getBalance();
                    }
                    account.getAccount().setBalance(balance);
                }
            }

            //if isCreditInternalNumber and records is null or empty
            if (isCreditInternalNumber && (globalSearchResults.getRecords() == null || globalSearchResults.getRecords().size() == 0)) {
                globalSearchResults = admCreditRepository.globalSearch(inputSearch);
            }


            if (globalSearchResults.getRecords().size() > 0) {
                return Response.status(Response.Status.OK).entity(globalSearchResults).build();
            }
            return Response.status(Response.Status.OK).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new
                    ResponseJson("FAIL", "Error en la consulta")
            ).build();
        }

    }
}
