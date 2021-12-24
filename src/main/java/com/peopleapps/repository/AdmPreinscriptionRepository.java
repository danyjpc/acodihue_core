package com.peopleapps.repository;

import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Repository(forEntity = AdmPreinscription.class)
public abstract class AdmPreinscriptionRepository extends AbstractEntityRepository<AdmPreinscription, Long>
        implements CriteriaSupport<AdmPreinscription> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

}
