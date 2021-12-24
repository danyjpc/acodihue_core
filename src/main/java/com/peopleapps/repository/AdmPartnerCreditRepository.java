package com.peopleapps.repository;

import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.credit.AdmCreditListDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorDto;
import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.dto.partnerCredit.AdmPartnerCreditDto;
import com.peopleapps.dto.partnerCredit.AdmPartnerCreditListDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmPartnerCredit.class)
public abstract class AdmPartnerCreditRepository extends AbstractEntityRepository<AdmPartnerCredit, Long>
        implements CriteriaSupport<AdmPartnerCredit> {

    @Inject
    EntityManager em;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmReferenceAccountRepository admReferenceAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    public List<AdmPartnerCreditDto> getAllPartnerCredits(
            Long partnerCreditId,
            Boolean desc) {
        StringBuilder query = this.getBaseQuery();
        query.append("\nWHERE 1 = 1");

        if (partnerCreditId != null && partnerCreditId > 0)
            query.append(" AND partner_credit.partner_credit_id = ").append(partnerCreditId);

        if (desc != null && desc) {
            query.append("\nORDER BY partner_credit.partner_credit_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY partner_credit.partner_credit_id ASC");
        }


        Stream<AdmPartnerCreditListDto> q = em.createNativeQuery(query.toString(), "admPartnerCreditListDto").getResultStream();
        return q.map(item -> {
                    AdmPartnerCreditDto admPartnerCreditDto = new AdmPartnerCreditDto(
                            item.getPartnerCreditId(),
                            new AdmCreditDto(
                                    new AdmCreditCalculatorDto(
                                            item.getCalculatorId(),
                                            item.getNoPayments(),
                                            item.getInterestRate(),
                                            item.getCredit()
                                    ),
                                    item.getCreditKey()
                            ),
                            new AdmPersonDto(
                                    item.getPersonKey(),
                                    item.getFirstName(),
                                    item.getMiddleName(),
                                    item.getLastName(),
                                    item.getPartnerEmail(),
                                    item.getNameComplete(),
                                    item.getPartnerBirthday()
                            ),
                            new AdmUserInfoDto.AdmPersonUserInfoDto(
                                    item.getCreatedByPersonKey(),
                                    item.getCreatedByEmail()
                            ),
                            item.getDateCreated(),
                            new AdmTypologyDto(
                                    item.getStatusTypoId(),
                                    item.getStatusTypoDesc()
                            )
                    );
                    return admPartnerCreditDto;
                }
        ).collect(Collectors.toList());

    }

    private StringBuilder getBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    partner_credit.partner_credit_id,\n" +
                "    --credit\n" +
                "    credit.credit_id,\n" +
                "    credit_type_typo.typology_id       credit_type_id,\n" +
                "    credit_type_typo.description       credit_type_description,\n" +
                "    --calculator\n" +
                "    credit_calculator.calculator_id,\n" +
                "    credit_calculator.no_payments,\n" +
                "    credit_calculator.interest_rate,\n" +
                "    credit_calculator.credit,\n" +
                "    --person / conyuge / partner\n" +
                "    partner.person_key                partner_person_key,\n" +
                "    partner.first_name                partner_first_name,\n" +
                "    partner.middle_name               partner_middle_name,\n" +
                "    partner.last_name                 partner_last_name,\n" +
                "    partner.name_complete             partner_name_complete,\n" +
                "    --created by\n" +
                "    created_by_person.person_key      created_by_person_person_key,\n" +
                "    created_by_person.email           created_by_person_email,\n" +
                "    --partner_credit\n" +
                "    partner_credit.date_created,\n" +
                "    --status\n" +
                "    status_typo.typology_id           status_id,\n" +
                "    status_typo.description           status_description\n" +
                "    partner.birthday                  partner_birthday,\n" +
                "    partner.email                     partner_email\n" +
                "\n" +
                "FROM       adm_partner_credit partner_credit\n" +
                "--credit\n" +
                "INNER JOIN adm_credit         credit            ON partner_credit.credit_id  = credit.credit_id\n" +
                "--credit type\n" +
                "INNER JOIN adm_typology       credit_type_typo  ON credit.credit_type        = credit_type_typo.typology_id\n" +
                "--credit calculator\n" +
                "INNER JOIN adm_calculator     credit_calculator ON credit.calculator_id      = credit_calculator.calculator_id\n" +
                "--person\n" +
                "INNER JOIN adm_person         partner           ON partner_credit.person_id  = partner.person_id\n" +
                "--created by\n" +
                "INNER JOIN adm_person         created_by_person ON partner_credit.created_by = created_by_person.person_id\n" +
                "--status\n" +
                "INNER JOIN adm_typology       status_typo       ON partner_credit.status     = status_typo.typology_id");
        return query;
    }

    public Boolean findPartnerAlreadyOnCredit(AdmPartnerCredit admPartnerCredit) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    partnerCredit.partner_credit_id,\n" +
                "    partnerCredit.credit_id,\n" +
                "    partnerCredit.person_id\n" +
                "FROM adm_partner_credit partnerCredit\n" +
                "WHERE 1 = 1");
        query.append(" \nAND partnerCredit.credit_id = ").append(admPartnerCredit.getCredit().getCreditId());
        query.append(" \nAND partnerCredit.person_id = ").append(admPartnerCredit.getPerson().getPersonId());

        Stream<Object[]> q = em.createNativeQuery(query.toString()).getResultStream();
        if (q.count() > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
