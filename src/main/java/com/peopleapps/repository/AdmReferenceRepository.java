package com.peopleapps.repository;

import com.peopleapps.dto.multiAccount.AdmPhoneAccountDto;
import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.phone.AdmPhoneDto;
import com.peopleapps.dto.reference.AdmReferenceDto;
import com.peopleapps.dto.reference.AdmReferenceListDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmReference.class)
public abstract class AdmReferenceRepository extends AbstractEntityRepository<AdmReference, Long>
        implements CriteriaSupport<AdmReference> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    //return references by associate
    public List<AdmReferenceDto> getAllReferencesByReferenceId(Long referenceId, Boolean desc) {
        StringBuilder query = this.getBaseQuery();
        query.append("\nWHERE 1 = 1");
        if (referenceId != null && referenceId > 0)
            query.append("\nAND reference.reference_id = ").append(referenceId);

        if (desc != null && desc) {
            query.append("\nORDER BY reference.reference_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY reference.reference_id ASC");
        }

        List<AdmReferenceDto> referenciasDtoList = this.queryReferences(query);

        return referenciasDtoList;
    }

    //return references by credit
    public List<AdmReferenceDto> getReferencesByCredit(UUID creditKey, Boolean desc, Long referenceId) {
        StringBuilder query = this.getBaseQuery();
        query.append("\nJOIN adm_credit       credit          ON reference.reference_account_id = credit.reference_account_id");
        query.append("\nWHERE 1 = 1");

        //filter by creditKey
        if (creditKey != null) {
            query.append("\nAND credit.credit_key = '").append(creditKey).append("'");
        }

        //filter by reference id
        if (referenceId != null && referenceId > 0) {
            query.append("\nAND reference.reference_id = ").append(referenceId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY reference.reference_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY reference.reference_id ASC");
        }

        List<AdmReferenceDto> referenciasDtoList = this.queryReferences(query);

        return referenciasDtoList;

    }

    //extracted method so it can be reused when querying references by credit.
    private List<AdmReferenceDto> queryReferences(StringBuilder query) {
        Stream<AdmReferenceListDto> q = em.createNativeQuery(query.toString(), "admReferenceListDto").getResultStream();
        return q.map(item -> {
                    AdmReferenceDto admReferenceDto = new AdmReferenceDto(
                            item.getReferenceId(),
                            new AdmReferenceAccountDto(
                                    item.getReferenceAccountId()
                            ),
                            new AdmPersonDto(
                                    item.getReferencePersonKey(),
                                    item.getReferenceFirstName(),
                                    null,
                                    item.getReferenceLastName(),
                                    item.getReferenceEmail(),
                                    item.getReferenceNameComplete(),
                                    item.getReferenceBirthday()
                            ),
                            new AdmUserInfoDto.AdmPersonUserInfoDto(
                                    item.getCreatedByPersonKey(),
                                    item.getCreatedByEmail()
                            ),
                            item.getDateCreated(),
                            new AdmTypologyDto(
                                    item.getStatusTypologyId(),
                                    item.getStatusDescription()
                            ),
                            new AdmTypologyDto(
                                    item.getKinshipTypologyId(),
                                    item.getKinshipDescription()
                            ),
                            item.getAge(),
                            new AdmPhoneDto(
                                    item.getPhoneId(),
                                    new AdmPhoneAccountDto(
                                            item.getPhoneAccountId()
                                    ),
                                    item.getPhone(),
                                    null,
                                    new AdmTypologyDto(
                                            item.getPhoneTypeTypologyId(),
                                            item.getPhoneTypeDescription()
                                    ),
                                    null,
                                    null,
                                    null
                            )
                    );
                    return admReferenceDto;
                }
        ).collect(Collectors.toList());
    }

    public void createReference(AdmReference admReference) {

        //TODO check default values
        admReference.setDateCreated(CsnFunctions.now());
        admReference.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));

        this.save(admReference);

    }

    public void editCustom(AdmReference admReference) {
        AdmReference currentReference = this.findBy(admReference.getReferenceId());
        //TODO check values that can be edited

        if (admReference.getStatus() != null) {
            currentReference.setStatus(admReference.getStatus());

        }
        if (admReference.getKinship() != null) {
            currentReference.setKinship(admReference.getKinship());
        }
        if (admReference.getAge() != null) {
            currentReference.setAge(admReference.getAge());
        }
        this.save(currentReference);

    }

    private StringBuilder getBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "      reference.reference_id,\n" +
                "      reference.reference_account_id,\n" +
                "      person.person_key                   reference_person_key,\n" +
                "      person.first_name                   reference_first_name,\n" +
                "      person.last_name                    reference_last_name,\n" +
                "      person.name_complete                reference_name_complete,\n" +
                "      createdByPerson.email               created_by_email,\n" +
                "      createdByPerson.person_key          created_by_person_key,\n" +
                "      reference.date_created,\n" +
                "      statusTypo.typology_id              status_typology_id,\n" +
                "      statusTypo.description              status_description,\n" +
                "      kinshipTypo.typology_id             kinship_typology_id,\n" +
                "      kinshipTypo.description             kinship_description,\n" +
                "      reference.age,\n" +
                "      phoneAccount.phone_account_id       phone_account_id,\n" +
                "      phone.phone_id                      phone_id,\n" +
                "      phone.phone                         phone,\n" +
                "      phoneTypeTypo.typology_id           phone_type_typology_id,\n" +
                "      phoneTypeTypo.description           phone_type_description,\n" +
                "      person.email                        person_email,\n" +
                "      person.birthday                     person_birthday\n" +

                "FROM adm_reference     reference\n" +
                "JOIN adm_person        person          ON reference.reference            = person.person_id\n" +
                "INNER JOIN adm_user    createdBy       ON reference.created_by           = createdBy.user_id\n" +
                "INNER JOIN adm_person  createdByPerson ON createdBy.person_id            = createdByPerson.person_id\n" +
                "JOIN adm_typology      statusTypo      ON reference.status               = statusTypo.typology_id\n" +
                "JOIN adm_typology      kinshipTypo     ON reference.kinship              = kinshipTypo.typology_id\n" +
                "JOIN adm_phone_account phoneAccount    ON person.phone_account_id        = phoneAccount.phone_account_id\n" +
                "JOIN adm_phone         phone           ON phoneAccount.phone_account_id  = phone.phone_account_id\n" +
                "JOIN adm_typology      phoneTypeTypo   ON phone.type                     = phoneTypeTypo.typology_id\n");

        return query;
    }


    public Boolean findReferenceAlreadyOnCredit(AdmCredit credit, AdmPerson referencePerson) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    reference.reference_id,\n" +
                "    reference.reference_account_id,\n" +
                "    reference.reference\n" +
                "FROM adm_reference reference\n" +
                "WHERE 1 = 1");
        query.append(" \nAND reference.reference_account_id = ").append(credit.getReferenceAccount().getReferenceAccountId());
        query.append(" \nAND reference.reference = ").append(referencePerson.getPersonId());

        Stream<Object[]> q = em.createNativeQuery(query.toString()).getResultStream();


        if (q.count() > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
