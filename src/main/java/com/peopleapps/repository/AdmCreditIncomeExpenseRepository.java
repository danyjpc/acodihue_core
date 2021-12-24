package com.peopleapps.repository;

import com.peopleapps.dto.creditIncomeExpense.AdmCreditIncomeExpenseDto;
import com.peopleapps.dto.creditIncomeExpense.AdmCreditIncomeExpenseListDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmCreditIncomeExpense.class)
public abstract class AdmCreditIncomeExpenseRepository extends AbstractEntityRepository<AdmCreditIncomeExpense, Long>
        implements CriteriaSupport<AdmCreditIncomeExpense> {

    @Inject
    EntityManager em;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmCreditRepository admCreditRepository;

    @Inject
    Logger logger;

    public AdmCreditIncomeExpenseDto getCreditIncomeExpenseById(
            Long creditId,
            //activo (asset)/ pasivo (liability)
            Long accountTypeId,
            Long statusId,
            Boolean desc
    ) {

        //get asset / liability catalogue
        List<AdmTypologyDto> typologyCatalogue = admTypologyRepository.getChildTypologies(accountTypeId);

        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    creditIncomeExpense.credit_income_expense_id,\n" +
                "    accountTypeTypo.typology_id   account_type_id,\n" +
                "    accountTypeTypo.description   account_type_description,\n" +
                "    creditIncomeExpense.amount\n" +

                "FROM adm_credit_income_expense creditIncomeExpense\n" +
                "JOIN adm_typology accountTypeTypo\n" +
                "ON accountTypeTypo.typology_id = creditIncomeExpense.account_type" +
                "\nWHERE 1 = 1 ");
        query.append("\nAND creditIncomeExpense.credit_id = ").append(creditId);

        if (statusId > 0) {
            query.append("\nAND creditIncomeExpense.status = ").append(statusId);
        } else if (statusId.equals(CsnConstants.STATUS_ACTIVE) || statusId.equals(CsnConstants.ZERO)) {
            query.append("\nAND creditIncomeExpense.status = ").append(CsnConstants.STATUS_ACTIVE);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY accountTypeTypo.typology_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY accountTypeTypo.typology_id ASC");
        }

        //logger.error("-------------->>>>>>>>>>>>------------------- " + query.toString());

        //accountList contains credit income expense for the given credit
        Stream<AdmCreditIncomeExpenseListDto> q = em.createNativeQuery(query.toString(), "admCreditIncomeExpenseListDto").getResultStream();
        List<AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount> accountList = q.map(item -> {
            AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount account = new AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount(
                    item.getCreditIncomeExpenseId(),
                    item.getMonto(),
                    new AdmTypologyDto(
                            item.getCuentaId(),
                            item.getCuentaDescription()
                    )
            );
            return account;
        }).collect(Collectors.toList());

        //List that will be returned
        List<AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount> responseAccountList = new ArrayList<>();

        //total accumulator
        BigDecimal totalMonthly = new BigDecimal("0");

        BigDecimal totalYearly = new BigDecimal("0");

        //Object to return
        AdmCreditIncomeExpenseDto response = null;

        //if there is no credit income expense yet for the credit we return a zero amount and total for each account

        for (AdmTypologyDto catalogueItem : typologyCatalogue
        ) {
            responseAccountList.add(
                    new AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount(
                            CsnConstants.ZERO,
                            new BigDecimal("0"),
                            new AdmTypologyDto(
                                    catalogueItem.getTypologyId(),
                                    catalogueItem.getDescription()
                            )
                    )
            );
        }
        if (accountList.size() == 0) {

            //setting final response
            response = new AdmCreditIncomeExpenseDto(
                    //accounts
                    responseAccountList,
                    //total yearly
                    new BigDecimal("0"),
                    //total monthly
                    new BigDecimal("0")
            );
            return response;

        } else {
            //we check accountList against accountList to determine ammounts
            for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount account : accountList
            ) {
                for (AdmCreditIncomeExpenseDto.AdmCreditIncomeExpenseAccount responseAccount : responseAccountList) {
                    //if ids match, change amount
                    if (account.getAccount().getTypologyId().equals(responseAccount.getAccount().getTypologyId())) {
                        //set Id
                        responseAccount.setCreditIncomeExpenseId(account.getCreditIncomeExpenseId());
                        //set amount
                        responseAccount.setAmount(account.getAmount());
                        totalMonthly = totalMonthly.add(account.getAmount());

                    }
                }

            }
            response = new AdmCreditIncomeExpenseDto(
                    responseAccountList,
                    //year total
                    totalMonthly.multiply(new BigDecimal("12")),
                    //monthly total
                    totalMonthly
            );
        }

        return response;
    }


    //method that gets balance credit income expense given a credit ID and account id
    public AdmCreditIncomeExpense getCreditIncomeExpense(Long creditId, Long accountTypeId) {

        Criteria<AdmCreditIncomeExpense, AdmCreditIncomeExpense> query = criteria();
        query.join(AdmCreditIncomeExpense_.credit,
                where(AdmCredit.class)
                        .eq(AdmCredit_.creditId, creditId))
                .join(AdmCreditIncomeExpense_.accountType,
                        where(AdmTypology.class)
                                .eq(AdmTypology_.typologyId, accountTypeId));

        List<AdmCreditIncomeExpense> admCreditIncomeExpenses = query.getResultList();
        return (admCreditIncomeExpenses.size() > 0) ? admCreditIncomeExpenses.get(0) : null;
    }

}
