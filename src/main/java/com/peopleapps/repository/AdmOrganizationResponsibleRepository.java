package com.peopleapps.repository;

import com.peopleapps.dto.organizationResponsible.AdmOrganizationResponsibleListDto;
import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmOrganizationResponsible.class)
public abstract class AdmOrganizationResponsibleRepository extends AbstractEntityRepository<AdmOrganizationResponsible, Long>
        implements CriteriaSupport<AdmOrganizationResponsible> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmOrganizationResponsible> getAllOrganizationResponsibles(
            Long organizationResponsibleId
    ) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    orgResp.organization_responsible_id,\n" +
                "    organization.organization_id             organization_id,\n" +
                "    organization.organization_name           organization_name,\n" +
                "    organization.organization_commercial     organization_commercial,\n" +
                "    person.person_id            person_id,\n" +
                "    person.first_name           first_name,\n" +
                "    person.last_name            last_name,\n" +
                "    orgResp.date_created\n" +
                "FROM adm_organization_responsible orgResp\n" +
                "INNER JOIN adm_organization organization ON orgResp.organization_id = organization.organization_id\n" +
                "INNER JOIN adm_person person ON orgResp.person_id = person.person_id\n" +
                "WHERE 1 = 1");
        if (organizationResponsibleId != null && organizationResponsibleId > 0) {
            query.append(" AND orgResp.organization_responsible_id = ").append(organizationResponsibleId);
        }


        Stream<AdmOrganizationResponsibleListDto> q = em.createNativeQuery(query.toString(), "admOrganizationResponsibleListDto").getResultStream();
        return q.map(item -> {
            AdmOrganizationResponsible admOrganizationResponsible = new AdmOrganizationResponsible(
                    item.getOrganizationResponsibleId(),
                    new AdmOrganization(
                            item.getOrganizationId(),
                            item.getOrganizationName()
                    ),
                    new AdmPerson(
                            item.getPersonId(),
                            item.getPersonName(),
                            item.getPersonLastName()
                    ),
                    item.getDateCreated()
            );
            return admOrganizationResponsible;
        }).collect(Collectors.toList());
    }

    public Boolean findIsResponsibleAlready(AdmOrganizationResponsible admOrganizationResponsible) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    orgResp.organization_responsible_id,\n" +
                "    orgResp.organization_id,\n" +
                "    orgResp.person_id\n" +
                "FROM adm_organization_responsible orgResp\n" +
                "WHERE 1 = 1");
        query.append(" \nAND orgResp.organization_id = ").append(admOrganizationResponsible.getOrganization().getOrganizationId());
        query.append(" \nAND orgResp.person_id = ").append(admOrganizationResponsible.getPerson().getPersonId());

        Stream<Object[]> q = em.createNativeQuery(query.toString()).getResultStream();
        if (q.count() > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void saveEdit(AdmOrganizationResponsible admOrganizationResponsible) throws Exception {
        AdmOrganizationResponsible currentOrgResponsible = this.findBy(admOrganizationResponsible.getOrganizationResponsibleId());
        admOrganizationResponsible.setDateCreated(currentOrgResponsible.getDateCreated());
        //TODO check values that can be edited
        Boolean isResponsibleAlready = Boolean.TRUE;
        if (admOrganizationResponsible.getPerson() != null && admOrganizationResponsible.getOrganization() != null) {
            isResponsibleAlready = this.findIsResponsibleAlready(admOrganizationResponsible);
        }
        if (isResponsibleAlready) {
            throw new Exception("Already assigned as responsible for this organization");
        }
        this.save(admOrganizationResponsible);
    }

    public AdmOrganizationResponsible findResponsible(AdmOrganization organization, AdmPerson person) {

        Criteria<AdmOrganizationResponsible, AdmOrganizationResponsible> query = criteria();

        query.join(AdmOrganizationResponsible_.organization,
                where(AdmOrganization.class)
                        .eq(AdmOrganization_.organizationId, organization.getOrganizationId()));

        query.join(AdmOrganizationResponsible_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.personId, person.getPersonId()));

        List<AdmOrganizationResponsible> admOrganizationResponsibles = query.getResultList();

        return (admOrganizationResponsibles.size() > 0) ? admOrganizationResponsibles.get(0) : null;
    }

    public List<AdmOrganizationResponsible> findOrganizationResponsibleByOrganization(AdmOrganization organization) {

        Criteria<AdmOrganizationResponsible, AdmOrganizationResponsible> query = criteria();

        query.join(AdmOrganizationResponsible_.organization,
                where(AdmOrganization.class)
                        .eq(AdmOrganization_.organizationId, organization.getOrganizationId()));

        List<AdmOrganizationResponsible> admOrganizationResponsibles = new ArrayList<>();
        admOrganizationResponsibles = query.getResultList();

        return (admOrganizationResponsibles.size() > 0) ? admOrganizationResponsibles : null;
    }

}
