package com.peopleapps.repository;

import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Repository(forEntity = AdmSubOrganization.class)
public abstract class AdmSubOrganizationRepository extends AbstractEntityRepository<AdmSubOrganization, Long>
        implements CriteriaSupport<AdmSubOrganization> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;



    public void saveEdit(AdmSubOrganization admSubOrganization) {

        AdmSubOrganization currentSubOrganization = this.findBy(admSubOrganization.getSubOrganizationId());
        //Unable to edit these fields
        admSubOrganization.setSubOrganizationId(currentSubOrganization.getSubOrganizationId());
        admSubOrganization.setDateCreated(currentSubOrganization.getDateCreated());
        admSubOrganization.setCreatedBy(currentSubOrganization.getCreatedBy());

        //Editable fields
        if(admSubOrganization.getOrganization() == null){
            admSubOrganization.setOrganization(currentSubOrganization.getOrganization());
        }
        if(admSubOrganization.getOrganizationChild() == null){
            admSubOrganization.setOrganizationChild(currentSubOrganization.getOrganizationChild());
        }

        this.save(admSubOrganization);
    }

}
