package com.peopleapps.repository;

import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchAccountListDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchListDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnFunctions;
import com.peopleapps.util.StringMatcher;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmAccount.class)
public abstract class AdmAccountRepository extends AbstractEntityRepository<AdmAccount, Long>
        implements CriteriaSupport<AdmAccount> {

    @Inject
    EntityManager em;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    public List<AdmAccountDto> getAllAccounts(
            UUID associateKey,
            Long accountId,
            Boolean desc
    ) {
        StringBuilder query = getOriginalQuery();

        query.append("\nWHERE 1 = 1 ");

        query.append("\n AND associatePerson.person_key = ").append("'").append(associateKey).append("'");
        query.append("\n AND associatePerson.is_associate = TRUE");


        if (accountId != null && accountId > 0) {
            query.append("\nAND account.account_id = ").append(accountId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY account.account_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY account.account_id ASC");
        }


        Stream<AdmAccountListDto> q = em.createNativeQuery(query.toString(), "admAccountListDto").getResultStream();
        List<AdmAccountDto> admAccountDtos = q.map(item -> {
            AdmAccountDto admAccountDto = new AdmAccountDto(
                    new AdmAccountDto.AdmPersonAccountInfo(
                            item.getAccountId(),
                            new AdmAccountDto.AdmAccountOrganizationResponsibleDto(
                                    item.getOrganizationResponsibleId(),
                                    new AdmAccountDto.AdmAccountOrganizationDto(
                                            item.getOrganizationKey(),
                                            item.getOrganizationName(),
                                            null
                                    ),
                                    new AdmAccountDto.AdmAccountPersonDto(
                                            item.getPersonKey(),
                                            item.getFirstName(),
                                            item.getLastName(),
                                            null,
                                            null
                                    )
                            ),
                            //account type
                            new AdmAccountDto.AdmAccountTypologyDto(
                                    item.getAccountTypeId(),
                                    item.getAccountTypeDesc(),
                                    null,
                                    item.getAccountTypeValue2()
                            ),
                            new AdmAccountDto.AdmAccountPersonDto(
                                    item.getCreatedByPersonKey(),
                                    null,
                                    null,
                                    item.getCreatedByEmail(),
                                    null
                            ),
                            item.getNumAccount().toString(),
                            item.getNumAccountOrder(),
                            item.getDateCreated(),
                            //status
                            new AdmAccountDto.AdmAccountTypologyDto(
                                    item.getStatusId(),
                                    item.getStatusDesc(),
                                    null,
                                    null
                            )
                    ),
                    //TODO calculate balance
                    //item.getBalance()
                    item.getBalance()
            );
            return admAccountDto;
        }).collect(Collectors.toList());

        //Formatting account number
        for (AdmAccountDto dto : admAccountDtos) {
            Long currentNumber = Long.parseLong(dto.getPersonAccount().getNumAccount());
            Long currentOrder = dto.getPersonAccount().getNumAccountOrder();
            String formato = CsnFunctions.getAccountNumberOrder(currentNumber);
            dto.getPersonAccount().setNumAccount(CsnFunctions.appendOrder(formato, currentOrder));
        }

        return admAccountDtos;
    }

    public void saveEdit(AdmAccount admAccount) {
        AdmAccount currentAccount = this.findBy(admAccount.getAccountId());

        //TODO check what properties can be updated

        //admAccount.setAccountId();
        //admAccount.setOrganizationResponsible();
        if (admAccount.getAccountType() != null) {
            AdmTypology accountType = admTypologyRepository.findBy(admAccount.getAccountType().getTypologyId());
            if (accountType != null) {
                currentAccount.setAccountType(accountType);
            }

        }
        if (admAccount.getNumAccount() != null) {
            currentAccount.setNumAccount(admAccount.getNumAccount());
        }
        if (admAccount.getNumAccountOrder() != null) {
            currentAccount.setNumAccountOrder(admAccount.getNumAccountOrder());
        }
        if (admAccount.getCreatedBy() != null) {
            AdmUser createdBy = admUserRepository.findByKey(admAccount.getCreatedBy().getPerson().getPersonKey());
            if (createdBy != null) {
                currentAccount.setCreatedBy(createdBy);
            }
        }

        if (admAccount.getStatus() != null) {
            AdmTypology status = admTypologyRepository.findBy(admAccount.getStatus().getTypologyId());
            if (status != null) {
                currentAccount.setStatus(admAccount.getStatus());
            }
        }
        this.save(currentAccount);

    }

    public Boolean findByAccountNumberOrder(Long accountNumber, Long accountOrder) {
        StringBuilder query = new StringBuilder();
        //TODO add typology for received
        query.append("SELECT account_id FROM adm_account WHERE num_account = ")
                .append(accountNumber)
                .append("\nAND num_account_order = ").append(accountOrder);

        Query q = em.createNativeQuery(query.toString());
        List<Object[]> resultados = q.getResultList();
        if (resultados.size() > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public AdmGlobalSearchDto globalSearch(String inputSearch) {
        StringBuilder query = getOriginalQuery();
        query.append("\nWHERE 1 = 1");

        Boolean applyFilter = false;
        Boolean isAccount = false;

        if (StringMatcher.isAccountNumber(inputSearch)) {
            String[] tokens = inputSearch.split("-");
            //query.append("\nAND  (1 = 1  ");
            if (tokens.length == 2) {

                Long account = Long.parseLong(tokens[0]);
                Long order = Long.parseLong(tokens[1]);

                query.append("\nAND account.num_account = ").append(account);
                query.append("\nAND account.num_account_order = ").append(order);
            }

            applyFilter = true;
            isAccount = true;

        }
        query.append("\nORDER BY account.date_created DESC \n");

        if (!applyFilter) {
            query.append("\nLIMIT 0");
        } else {
            query.append("\nLIMIT 100 \n ");
        }

        String searchType = isAccount ? "account" : "to do";
        Stream<AdmGlobalSearchAccountListDto> q = em.createNativeQuery(query.toString(), "admGlobalSearchAccountDto").getResultStream();
        List<AdmGlobalSearchDto.AdmGlobalSearchRecord> records = q.map(item -> {
            AdmGlobalSearchDto.AdmGlobalSearchRecord recs =
                    new AdmGlobalSearchDto.AdmGlobalSearchRecord(
                            new AdmAccountDto(
                                    new AdmAccountDto.AdmPersonAccountInfo(
                                            item.getAccountId(),
                                            new AdmAccountDto.AdmAccountOrganizationResponsibleDto(
                                                    item.getOrganizationResponsibleId(),
                                                    new AdmAccountDto.AdmAccountOrganizationDto(
                                                            item.getOrganizationKey(),
                                                            item.getOrganizationName(),
                                                            item.getOrganizationCommercial()
                                                    ),
                                                    new AdmAccountDto.AdmAccountPersonDto(
                                                            item.getPersonKey(),
                                                            item.getFirstName(),
                                                            item.getLastName(),
                                                            null,
                                                            null
                                                    )
                                            ),
                                            //account type
                                            new AdmAccountDto.AdmAccountTypologyDto(
                                                    item.getAccountTypeId(),
                                                    item.getAccountTypeDesc(),
                                                    null,
                                                    item.getAccountTypeValue2()
                                            ),
                                            //created by
                                            new AdmAccountDto.AdmAccountPersonDto(
                                                    item.getCreatedByPersonKey(),
                                                    null,
                                                    null,
                                                    item.getCreatedByEmail(),
                                                    null
                                            ),
                                            //account number with leading zeroes
                                            CsnFunctions.appendOrder(CsnFunctions.getAccountNumberOrder(item.getNumAccount()), item.getNumAccountOrder()),
                                            item.getNumAccountOrder(),
                                            item.getDateCreated(),
                                            //status
                                            new AdmAccountDto.AdmAccountTypologyDto(
                                                    item.getStatusId(),
                                                    item.getStatusDesc(),
                                                    null,
                                                    null
                                            )
                                    ),
                                    0.0
                            ));
            return recs;
        }).collect(Collectors.toList());


        return new AdmGlobalSearchDto(searchType, records);
    }

    public StringBuilder getOriginalQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    account.account_id,\n" +
                "    account.organization_responsible_id,\n" +
                "    organization.organization_key,\n" +
                "    organization.organization_name,\n" +
                "    organization.organization_commercial,\n" +
                "    associatePerson.person_key                    person_key,\n" +
                "    associatePerson.first_name                   first_name,\n" +
                "    associatePerson.last_name                    last_name,\n" +
                "    typeTypo.typology_id                account_type_id,\n" +
                "    typeTypo.description                account_type_desc,\n" +
                "    account.num_account,\n" +
                "    account.num_account_order,\n" +
                "    createdByPerson.email                             created_by_email,\n" +
                "    createdByPerson.person_key                        created_by_person_key,\n" +
                "    account.date_created,\n" +
                "    statusTypo.typology_id              status_id,\n" +
                "    statusTypo.description              status_desc,\n" +
                "    account.account_id                  balance,\n" +
                "    typeTypo.value_2                    account_type_value_2\n" +
                "FROM adm_account account\n" +
                "INNER JOIN adm_organization_responsible responsible ON account.organization_responsible_id = responsible.organization_responsible_id\n" +
                "INNER JOIN adm_organization organization ON responsible.organization_id = organization.organization_id\n" +
                "INNER JOIN adm_person associatePerson ON responsible.person_id = associatePerson.person_id\n" +
                "INNER JOIN adm_typology typeTypo ON account.account_type = typeTypo.typology_id\n" +
                "INNER JOIN adm_user createdBy ON account.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo ON account.status = statusTypo.typology_id\n");


        return query;
    }
}
