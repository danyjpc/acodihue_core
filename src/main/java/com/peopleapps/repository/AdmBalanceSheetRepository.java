package com.peopleapps.repository;

import com.peopleapps.dto.balanceSheet.AdmBalanceSheetDto;
import com.peopleapps.dto.balanceSheet.AdmBalanceSheetListDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.typology.AdmTypologyListDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmBalanceSheet.class)
public abstract class AdmBalanceSheetRepository extends AbstractEntityRepository<AdmBalanceSheet, Long>
        implements CriteriaSupport<AdmBalanceSheet> {

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

    public AdmBalanceSheetDto getBalanceSheetByCredit(
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
                "    balanceSheet.adm_balance_sheet_id,\n" +
                "    accountTypeTypo.typology_id   account_type_id,\n" +
                "    accountTypeTypo.description   account_type_description,\n" +
                "    balanceSheet.amount\n" +

                "FROM adm_balance_sheet balanceSheet\n" +
                "JOIN adm_typology accountTypeTypo\n" +
                "ON accountTypeTypo.typology_id = balanceSheet.account_type" +
                "\nWHERE 1 = 1 ");
        query.append("\nAND balanceSheet.credit_id = ").append(creditId);

        if (statusId > 0) {
            query.append("\nAND balanceSheet.status = ").append(statusId);
        } else if (statusId.equals(CsnConstants.STATUS_ACTIVE) || statusId.equals(CsnConstants.ZERO)) {
            query.append("\nAND balanceSheet.status = ").append(CsnConstants.STATUS_ACTIVE);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY accountTypeTypo.typology_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY accountTypeTypo.typology_id ASC");
        }

        //logger.error("-------------->>>>>>>>>>>>------------------- " + query.toString());

        //accountList contains balanceSheet for the given credit
        Stream<AdmBalanceSheetListDto> q = em.createNativeQuery(query.toString(), "admBalanceSheetListDto").getResultStream();
        List<AdmBalanceSheetDto.AdmBalanceSheetAccount> accountList = q.map(item -> {
            AdmBalanceSheetDto.AdmBalanceSheetAccount account = new AdmBalanceSheetDto.AdmBalanceSheetAccount(
                    item.getBalanceSheetId(),
                    item.getMonto(),
                    new AdmTypologyDto(
                            item.getCuentaId(),
                            item.getCuentaDescription()
                    )
            );
            return account;
        }).collect(Collectors.toList());

        //List that will be returned
        List<AdmBalanceSheetDto.AdmBalanceSheetAccount> responseAccountList = new ArrayList<>();

        //total accumulator
        BigDecimal total = new BigDecimal("0");

        //Object to return
        AdmBalanceSheetDto response = null;

        //if there is no balance sheet yet for the credit we return a zero amount and total for each account

        for (AdmTypologyDto catalogueItem : typologyCatalogue
        ) {
            responseAccountList.add(
                    new AdmBalanceSheetDto.AdmBalanceSheetAccount(
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
            response = new AdmBalanceSheetDto(
                    //accounts
                    responseAccountList,
                    //total
                    new BigDecimal("0")
            );
            return response;

        } else {
            //we check accountList against accountList to determine ammounts
            for (AdmBalanceSheetDto.AdmBalanceSheetAccount account : accountList
            ) {
                for (AdmBalanceSheetDto.AdmBalanceSheetAccount responseAccount : responseAccountList) {
                    //if ids match, change amount
                    if (account.getAccount().getTypologyId().equals(responseAccount.getAccount().getTypologyId())) {
                        //set Id
                        responseAccount.setBalanceSheetId(account.getBalanceSheetId());
                        //set amount
                        responseAccount.setAmount(account.getAmount());
                        total = total.add(account.getAmount());
                    }
                }

            }
            response = new AdmBalanceSheetDto(
                    responseAccountList,
                    total
            );
        }

        return response;
    }


    //method that gets balance sheet given a credit ID and account id
    public AdmBalanceSheet getBalanceSheet(Long creditId, Long accountTypeId) {

        Criteria<AdmBalanceSheet, AdmBalanceSheet> query = criteria();
        query.join(AdmBalanceSheet_.credit,
                where(AdmCredit.class)
                        .eq(AdmCredit_.creditId, creditId))
                .join(AdmBalanceSheet_.accountType,
                        where(AdmTypology.class)
                                .eq(AdmTypology_.typologyId, accountTypeId));

        List<AdmBalanceSheet> admBalanceSheetList = query.getResultList();
        return (admBalanceSheetList.size() > 0) ? admBalanceSheetList.get(0) : null;
    }

}
