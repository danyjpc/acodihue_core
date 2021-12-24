package com.peopleapps.repository;

import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceListDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorListDto;
import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.dto.creditLine.AdmCreditLineListDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.creditCalculator.AdmCCalulatorDto;
import com.peopleapps.model.AdmCreditCalculator;
import com.peopleapps.model.AdmCreditLine;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmCreditCalculator.class)
public abstract class AdmCreditCalculatorRepository extends AbstractEntityRepository<AdmCreditCalculator, Long>
        implements CriteriaSupport<AdmCreditCalculator> {

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

    public List<AdmCreditCalculatorDto> getAllCreditCalculators(
            Long creditCalculatorId,
            UUID associateKey,
            Boolean desc,
            Long status,
            String startDate, String endDate
    ) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    calculator.calculator_id,\n" +
                "    associate.person_key                        associate_key,\n" +
                "    associate.first_name                        associate_first_name,\n" +
                "    associate.last_name                         associate_last_name,\n" +
                "    calculator.application_date,\n" +
                "    calculator.no_period,\n" +
                "    calculator.no_payments,\n" +
                "    calculator.interest_rate,\n" +
                "    calculator.interest_final,\n" +
                "    calculator.credit,\n" +
                "    calculator.date_created,\n" +
                "    createdByPerson.person_key                  created_by_person_key,\n" +
                "    createdByPerson.email                       created_by_email,\n" +
                "    statusTypo.typology_id                      status_id,\n" +
                "    statusTypo.description                      status_desc,\n" +
                "    organization.organization_key               organization_key,\n" +
                "    organization.organization_name              organization_name,\n" +
                "    organization.organization_commercial        organization_commercial,\n" +
                "    calculator.credit_line,\n" +
                "    creditLine.description                      credit_line_desc, \n" +
                "    associate.membership_number\n" +
                "FROM adm_calculator calculator\n" +
                "INNER JOIN adm_person       associate          ON calculator.person_id       = associate.person_id\n" +
                "INNER JOIN adm_user         createdByUser      ON calculator.created_by      = createdByUser.user_id\n" +
                "INNER JOIN adm_person       createdByPerson    ON createdByUser.person_id    = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology     statusTypo         ON calculator.status          = statusTypo.typology_id\n" +
                "INNER JOIN adm_credit_Line   creditLine        ON calculator.credit_line     = creditLine.credit_line_id\n" +
                "INNER JOIN adm_organization organization       ON creditLine.organization_id = organization.organization_id\n" +
                "WHERE 1 = 1 ");

        if (creditCalculatorId != null && creditCalculatorId > 0) {
            query.append("\nAND calculator.calculator_id = ").append(creditCalculatorId);
        }
        if (associateKey != null) {
            query.append("\nAND associate.person_key = ").append("'").append(associateKey).append("'");
        }

        //filters applied only when searching all not by id
        if (creditCalculatorId == null || creditCalculatorId == 0) {
            //status
            if (status == null || status == 0) {
                query.append("\nAND calculator.status = ").append(CsnConstants.STATUS_ACTIVE);
            } else if (status > 0) {
                query.append("\nAND calculator.status = ").append(status);
            }

            //date filter
            if (!startDate.equals("") && !endDate.equals("")) {
                query.append("\nAND calculator.date_created BETWEEN '").append(startDate).append(" 00:00:00' AND '").append(endDate).append(" 23:59:59'");
            }
        }

        if (desc != null && desc) {
            query.append("\nORDER BY calculator.calculator_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY calculator.calculator_id ASC");
        }

        Stream<AdmCreditCalculatorListDto> q = em.createNativeQuery(query.toString(), "admCreditCalculatorLineListDto").getResultStream();
        return q.map(item ->

        {
            AdmCreditCalculatorDto admCreditLineDto = new AdmCreditCalculatorDto(
                    item.calculatorId,
                    new AdmPersonDto(
                            item.personKey,
                            item.firstName,
                            item.lastName,
                            CsnFunctions.getAccountNumberOrder(item.membershipNumber)
                    ),
                    item.applicationDate,
                    item.noPeriod,
                    item.noPayments,
                    new BigDecimal(item.interestRate.toString()),
                    new BigDecimal(item.credit.toString()),
                    item.dateCreated,
                    new AdmPersonDto(
                            item.createdBypersonKey,
                            null,
                            null,
                            null,
                            item.createdByEmail,
                            null,
                            null
                    ),
                    new AdmTypologyDto(
                            item.statusId,
                            item.statusDesc
                    ),
                    new BigDecimal(item.interestFinal.toString()),
                    new AdmCreditLineDto(
                            item.creditLine,
                            new AdmCreditLineDto.AdmCreditLineOrganizationDto(
                                    item.organizationKey,
                                    item.organizationName,
                                    item.organizationCommercial
                            ),
                            item.creditLineDesc,
                            null,
                            null,
                            null
                    )
            );
            return admCreditLineDto;
        }).collect(Collectors.toList());
    }

    public Optional<AdmCCalulatorDto> getInfoCalulatorDto(Long creditId)throws Exception{
        StringBuilder query = new StringBuilder();
        query.append("select cal.calculator_id calculatorId,\n" +
                "cal.no_period noPeriod,\n" +
                "cal.no_payments noPayments,\n" +
                "cal.credit credit,\n" +
                "cal.interest_rate interes,\n" +
                "cal.interest_final interesF\n" +
                "from adm_credit credit \n" +
                "inner join adm_calculator cal on cal.calculator_id=credit.calculator_id \n" +
                "where cal.calculator_id='").append(creditId).append("'");
        Stream<AdmCCalulatorDto> q = em.createNativeQuery(query.toString(),"admCredCalculatorDto").getResultStream();
        return q.findFirst();
    }
}
