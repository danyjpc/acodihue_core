package com.peopleapps.repository;

import com.peopleapps.dto.phrase.AdmPhraseDto;
import com.peopleapps.model.AdmPhrase;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmPhrase.class)
public abstract class AdmPhraseRepository extends AbstractEntityRepository<AdmPhrase, Long> implements CriteriaSupport<AdmPhrase> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmPhrase> getAll() throws Exception {

        StringBuilder query = new StringBuilder();
        query.append("select phrase_id," +
                "author,phrase\n" +
                "from adm_phrase");

        Stream<AdmPhraseDto> q = em.createNativeQuery(query.toString(), "admPhraseDto").getResultStream();

        return q.map(item -> {
            AdmPhrase admPhrase = new AdmPhrase(
                    item.phrase_id,
                    item.author,
                    item.phrase
            );

            return admPhrase;
        }).collect(Collectors.toList());
    }

    public void saveEdit(AdmPhrase newAdmPhrase) {
        AdmPhrase currentAdmPhrase = this.findBy(newAdmPhrase.getPhraseId());

        if (newAdmPhrase.getPhrase() != null)
            currentAdmPhrase.setPhrase(newAdmPhrase.getPhrase());

        if (newAdmPhrase.getAuthor() != null)
            currentAdmPhrase.setAuthor(newAdmPhrase.getAuthor());

        this.save(currentAdmPhrase);
    }

}