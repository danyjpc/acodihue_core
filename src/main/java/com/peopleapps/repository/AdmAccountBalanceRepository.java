package com.peopleapps.repository;

import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceListDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmAccountBalance.class)
public abstract class AdmAccountBalanceRepository extends AbstractEntityRepository<AdmAccountBalance, Long>
        implements CriteriaSupport<AdmAccountBalance> {

    @Inject
    EntityManager em;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmAccountRepository admAccountRepository;

    @Inject
    Logger logger;

    public List<AdmAccountBalanceDto> getAllAccountBalances(
            Long accountBalanceId,
            Boolean desc
    ) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    accountBalance.account_balance_id,\n" +
                "    account.account_id,\n" +
                "    accountBalance.transaction_no,\n" +
                "    accountBalance.amount,\n" +
                "    transactionTypeTypo.typology_id      transaction_type_id,\n" +
                "    transactionTypeTypo.description      transaction_type_desc,\n" +
                "    createdByPerson.email                created_by_email,\n" +
                "    createdByPerson.person_key           created_by_person_key,\n" +
                "    accountBalance.date_created,\n" +
                "    statusTypo.typology_id               status_id,\n" +
                "    statusTypo.description               status_desc,\n" +
                "    accountBalance.annotation,\n" +
                "    transactionTypeParentTypo.typology_id      transaction_type_parent_id,\n" +
                "    transactionTypeParentTypo.description      transaction_type_parent_desc\n" +

                "FROM adm_account_balance accountBalance\n" +
                "INNER JOIN adm_account account  ON account.account_id = accountBalance.account_id\n" +
                "INNER JOIN adm_typology transactionTypeTypo  ON accountBalance.transaction_type = transactionTypeTypo.typology_id\n" +
                "INNER JOIN adm_user createdByUser  ON accountBalance.created_by = createdByUser.user_id\n" +
                "INNER JOIN adm_person createdByPerson  ON createdByUser.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo  ON accountBalance.status = statusTypo.typology_id\n" +
                "INNER JOIN adm_typology transactionTypeParentTypo  ON transactionTypeTypo.parent_typology_id = transactionTypeParentTypo.typology_id\n" +

                "WHERE 1 = 1 ");
        if (accountBalanceId != null && accountBalanceId > 0) {
            query.append("AND accountBalance.account_balance_id = ").append(accountBalanceId);
        }
        if (desc != null && desc) {
            query.append("\nORDER BY accountBalance.account_balance_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY accountBalance.account_balance_id ASC");
        }

        Stream<AdmAccountBalanceListDto> q = em.createNativeQuery(query.toString(), "admAccountBalanceListDto").getResultStream();
        return q.map(item -> {
            AdmAccountBalanceDto admAccountBalance = new AdmAccountBalanceDto(
                    item.getAccount_balance_id(),
                    item.getAccount_id(),
                    item.getTransaction_no(),
                    item.getAmount(),
                    new AdmAccountBalanceDto.AdmAccountBalanceTypologyDto(
                            item.getTransaction_type_id(),
                            item.getTransaction_type_desc(),
                            item.getTransaction_type_parent_id(),
                            item.getTransaction_type_parent_desc()
                    ),
                    new AdmAccountBalanceDto.AdmAccountBalancePersonDto(
                            item.getCreated_by_person_key(),
                            null,
                            null,
                            item.getCreated_by_email(),
                            null
                    ),
                    item.getDate_created(),
                    new AdmAccountBalanceDto.AdmAccountBalanceTypologyDto(
                            item.getStatus_id(),
                            item.getStatus_desc(),
                            null,
                            null
                    ),
                    item.getAnnotation()
            );
            return admAccountBalance;
        }).collect(Collectors.toList());
    }

    public List<AdmAccountBalanceInfoDto> getMovementsByAccount(
            UUID associateKey, Long accountId, Long statusId, Boolean desc,
            Long year, Long month, String startDate, String endDate) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    accountBalance.account_balance_id,\n" +
                "    account.account_id,\n" +
                "    accountBalance.transaction_no,\n" +
                "    accountBalance.amount,\n" +
                "    transactionTypeTypo.typology_id      transaction_type_id,\n" +
                "    transactionTypeTypo.description      transaction_type_desc,\n" +
                "    createdByPerson.email                created_by_email,\n" +
                "    createdByPerson.person_key           created_by_person_key,\n" +
                "    accountBalance.date_created,\n" +
                "    statusTypo.typology_id               status_id,\n" +
                "    statusTypo.description               status_desc,\n" +
                "    accountBalance.annotation,\n" +
                "    transactionTypeParentTypo.typology_id      transaction_type_parent_id,\n" +
                "    transactionTypeParentTypo.description      transaction_type_parent_desc,\n" +
                "    accountBalance.annotation\n" +

                "FROM adm_account_balance accountBalance\n" +
                "INNER JOIN adm_account account  ON account.account_id = accountBalance.account_id\n" +
                "INNER JOIN adm_typology transactionTypeTypo  ON accountBalance.transaction_type = transactionTypeTypo.typology_id\n" +
                "INNER JOIN adm_user createdByUser  ON accountBalance.created_by = createdByUser.user_id\n" +
                "INNER JOIN adm_person createdByPerson  ON createdByUser.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo  ON accountBalance.status = statusTypo.typology_id\n" +
                "INNER JOIN adm_typology transactionTypeParentTypo  ON transactionTypeTypo.parent_typology_id = transactionTypeParentTypo.typology_id\n" +
                "INNER JOIN adm_organization_responsible organizationResp ON account.organization_responsible_id = organizationResp.organization_responsible_id\n" +
                "INNER JOIN adm_person associatePerson ON organizationResp.person_id = associatePerson.person_id\n" +
                "WHERE 1 = 1 ");

        query.append("\nAND associatePerson.is_associate = TRUE");

        if (associateKey != null) {
            query.append("\nAND associatePerson.person_key = ").append("'").append(associateKey).append("'");
        }
        if (accountId != null && accountId > 0) {
            query.append("\nAND accountBalance.account_id = ").append(accountId);
        }
        if (statusId != null && statusId > 0) {
            query.append("\nAND accountBalance.status = ").append(statusId);
        } else {
            query.append("\nAND accountBalance.status = ").append(CsnConstants.STATUS_ACTIVE);
        }

        if (startDate.equals("") || endDate.equals("")) {
            if (month != null && month > 0) {
                query.append("\nAND EXTRACT(MONTH FROM accountBalance.date_created) = ").append(month);
            } else if (month == null || month == 0) {
                query.append("\nAND EXTRACT(MONTH FROM accountBalance.date_created) = EXTRACT(MONTH FROM NOW())");
            }

            if (year != null && year > 0) {
                query.append("\nAND EXTRACT(YEAR FROM accountBalance.date_created) = ").append(year);
            } else if (year == null || year == 0) {
                query.append("\nAND EXTRACT(YEAR FROM accountBalance.date_created) = EXTRACT(YEAR FROM NOW())");
            }
        }

        if(!startDate.equals("") && !endDate.equals("")){
            query.append("\nAND accountBalance.date_created BETWEEN '").append(startDate).append(" 00:00:00' AND '").append(endDate).append(" 23:59:59'");
        }




        if (desc != null && desc) {
            query.append("\nORDER BY accountBalance.account_balance_id ASC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY accountBalance.account_balance_id DESC");
        }

        Stream<AdmAccountBalanceListDto> q = em.createNativeQuery(query.toString(), "admAccountBalanceListDto").getResultStream();
        List<AdmAccountBalanceDto> listaDtos = this.getDtoList(q);
        //debit credit logic
        List<AdmAccountBalanceInfoDto> movements = new ArrayList<>();
        Double balanceFinal = 0.0;
        for (AdmAccountBalanceDto singleBalance : listaDtos) {



            AdmAccountBalanceInfoDto movement = new AdmAccountBalanceInfoDto();
            if (singleBalance.getTransactionType().getParentTypologyId().equals(CsnConstants.DEBIT_TYPOLOGY)) {
                //substract from balance
                balanceFinal -= singleBalance.getAmount();

                movement.setAccount_balance_id(singleBalance.getAccount_balance_id());
                movement.setAccount_id(singleBalance.getAccount_id());
                movement.setTransaction_no(singleBalance.getTransaction_no());
                movement.setDate_created(singleBalance.getDate_created());
                movement.setDebit(Math.round(singleBalance.getAmount() * 100.0) / 100.0);
                movement.setCredit(0.0);
                movement.setBalance(Math.round(balanceFinal * 100.0) / 100.0);
                movement.setAnnotation(singleBalance.getAnnotation());
                movement.setTransaction_type_id(singleBalance.getTransactionType().getTypologyId());
                movement.setTransaction_type_description(singleBalance.getTransactionType().getDescription());

                //Avoids adding empty objects for inscription fee
                movements.add(movement);


            } else if (singleBalance.getTransactionType().getParentTypologyId().equals(CsnConstants.CREDIT_TYPOLOGY)) {
                //add to balance
                balanceFinal += singleBalance.getAmount();

                movement.setAccount_balance_id(singleBalance.getAccount_balance_id());
                movement.setAccount_id(singleBalance.getAccount_id());
                movement.setTransaction_no(singleBalance.getTransaction_no());
                movement.setDate_created(singleBalance.getDate_created());
                movement.setDebit(0.0);
                movement.setCredit(Math.round(singleBalance.getAmount() * 100.0) / 100.0);
                movement.setBalance(Math.round(balanceFinal * 100.0) / 100.0);
                movement.setAnnotation(singleBalance.getAnnotation());
                movement.setTransaction_type_id(singleBalance.getTransactionType().getTypologyId());
                movement.setTransaction_type_description(singleBalance.getTransactionType().getDescription());

                //Avoids adding empty objects for inscription fee
                movements.add(movement);

            }

        }

        return movements;
    }

    private List<AdmAccountBalanceDto> getDtoList(Stream<AdmAccountBalanceListDto> q) {
        List<AdmAccountBalanceDto> admAccountBalanceDtos = q.map(item -> {
            AdmAccountBalanceDto admAccountBalance = new AdmAccountBalanceDto(
                    item.getAccount_balance_id(),
                    item.getAccount_id(),
                    item.getTransaction_no(),
                    item.getAmount(),
                    new AdmAccountBalanceDto.AdmAccountBalanceTypologyDto(
                            item.getTransaction_type_id(),
                            item.getTransaction_type_desc(),
                            item.getTransaction_type_parent_id(),
                            item.getTransaction_type_parent_desc()
                    ),
                    new AdmAccountBalanceDto.AdmAccountBalancePersonDto(
                            item.getCreated_by_person_key(),
                            null,
                            null,
                            item.getCreated_by_email(),
                            null
                    ),
                    item.getDate_created(),
                    new AdmAccountBalanceDto.AdmAccountBalanceTypologyDto(
                            item.getStatus_id(),
                            item.getStatus_desc(),
                            null,
                            null
                    ),
                    item.getAnnotation()
            );
            return admAccountBalance;
        }).collect(Collectors.toList());
        return admAccountBalanceDtos;
    }
}
