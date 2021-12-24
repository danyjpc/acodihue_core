package com.peopleapps.repository;

import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceListDto;
import com.peopleapps.dto.associationResponsible.AdmAssociationResponsibleDto;
import com.peopleapps.dto.associationResponsible.AdmAssociationResponsibleListDto;
import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.dto.creditLine.AdmCreditLineListDto;
import com.peopleapps.dto.organization.AdmOrganizationDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
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

@Repository(forEntity = AdmAssociationResponsible.class)
public abstract class AdmAssociationResponsibleRepository extends AbstractEntityRepository<AdmAssociationResponsible, Long>
        implements CriteriaSupport<AdmAssociationResponsible> {

    @Inject
    EntityManager em;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;


    @Inject
    Logger logger;

    public List<AdmAssociationResponsibleDto> getAllAssociationResponsibles(
            Long associationResponsibleId,
            UUID organizationKey,
            Boolean desc,
            Long status,
            UUID associateKey
    ) {
        StringBuilder query = this.getBaseQuery();
        query.append("\nWHERE 1 = 1");

        if (associationResponsibleId != null && associationResponsibleId > 0) {
            query.append("\nAND asso_responsible.association_responsible_id = ").append(associationResponsibleId);
        }
        if (organizationKey != null && !organizationKey.equals("")) {
            query.append("\nAND organization.organization_key = '").append(organizationKey).append("'");
        }
        if (associateKey != null && !associateKey.equals("")) {
            query.append("\nAND person.person_key = '").append(associateKey).append("'");
        }
        if (associationResponsibleId == null || associationResponsibleId == 0) {
            //status
            if (status == null || status == 0) {
                query.append("\nAND asso_responsible.status = ").append(CsnConstants.STATUS_ACTIVE);
            } else if (status > 0) {
                query.append("\nAND asso_responsible.status = ").append(status);
            }
        }

        if (desc != null && desc) {
            query.append("\nORDER BY asso_responsible.association_responsible_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY asso_responsible.association_responsible_id ASC");
        }

        Stream<AdmAssociationResponsibleListDto> q = em.createNativeQuery(query.toString(), "admAssociationResponsibleListDto").getResultStream();
        return q.map(item ->
        {
            AdmAssociationResponsibleDto admAssociationResponsibleDto = new AdmAssociationResponsibleDto(
                    item.getAssociationResponsibleId(),
                    new AdmOrganizationDto(
                            item.getOrganizationKey(),
                            item.getOrganizationName(),
                            item.getOrganizationCommercial(),
                            //devolver valor con 2 decimales
                            CsnFunctions.redondearBigDecimal (new BigDecimal(item.getInterestRate().toString()), 2)
                    ),
                    new AdmPersonDto(
                            item.getPersonKey(),
                            item.getPersoNameComplete()
                    ),
                    item.getAdmissionDate(),
                    item.getDischargeDate(),
                    item.getAnnotation(),
                    new AdmUserInfoDto.AdmPersonUserInfoDto(
                            item.getCreatedByPersonKey(),
                            item.getCreatedByEmail()
                    ),
                    item.getDateCreated(),
                    new AdmTypologyDto(
                            item.getStatusId(),
                            item.getStatusDesc()
                    )
            );
            return admAssociationResponsibleDto;
        }).

                collect(Collectors.toList());
    }

    private StringBuilder getBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "        asso_responsible.association_responsible_id,\n" +
                "        asso_responsible.admission_date,\n" +
                "        asso_responsible.discharge_date,\n" +
                "        asso_responsible.annotation,\n" +
                "        asso_responsible.date_created,\n" +
                "        organization.organization_key,\n" +
                "        organization.organization_name,\n" +
                "        organization.organization_commercial,\n" +
                "        person.person_key                            associate_key,\n" +
                "        person.name_complete                         associate_name_complete,\n" +
                "        createdByPerson.person_key                   created_by_person_key,\n" +
                "        createdByPerson.email                        created_by_person_email,\n" +
                "        statusTypo.typology_id                       status_id,\n" +
                "        statusTypo.description                       status_description,\n" +
                "        organization.interest_rate\n" +
                "FROM adm_association_responsible asso_responsible\n" +
                "JOIN adm_organization            organization    ON asso_responsible.organization_id = organization.organization_id\n" +
                "JOIN adm_person                  person          ON asso_responsible.person_id       = person.person_id\n" +
                "JOIN adm_user                    createdByUser   ON asso_responsible.created_by      = createdByUser.user_id\n" +
                "JOIN adm_person                  createdByPerson ON createdByUser.person_id          = createdByPerson.person_id\n" +
                "JOIN adm_typology                statusTypo      ON asso_responsible.status          = statusTypo.typology_id");
        return query;
    }


    public AdmAssociationResponsible findAssociationResponsible(AdmOrganization organization, AdmPerson person) {

        Criteria<AdmAssociationResponsible, AdmAssociationResponsible> query = criteria();

        query.join(AdmAssociationResponsible_.organization,
                where(AdmOrganization.class)
                        .eq(AdmOrganization_.organizationId, organization.getOrganizationId()));

        query.join(AdmAssociationResponsible_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.personId, person.getPersonId()));

        List<AdmAssociationResponsible> admAssociationResponsibles = query.getResultList();

        return (admAssociationResponsibles.size() > 0) ? admAssociationResponsibles.get(0) : null;
    }


}
