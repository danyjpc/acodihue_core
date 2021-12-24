package com.peopleapps.repository;

import com.peopleapps.dto.phone.AdmPhoneListDto;
import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmPhone.class)
public abstract class AdmPhoneRepository extends AbstractEntityRepository<AdmPhone, Long>
        implements CriteriaSupport<AdmPhone> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmPhone> getAllPhones(
            Long phoneId,
            Long phoneAccountId,
            Boolean desc
    ) {
        StringBuilder query = this.getOriginalQuery();

        query.append("WHERE 1 = 1 ");
        if (phoneId != null && phoneId > 0) {
            query.append("AND phone.phone_id = ").append(phoneId);
        }
        if (phoneAccountId != null && phoneAccountId > 0) {
            query.append("AND phone.phone_account_id = ").append(phoneAccountId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY phone.phone_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY phone.phone_id ASC");
        }


        return this.getPhoneList(query);
    }

    public void saveEdit(AdmPhone admPhone) {
        AdmPhone currentPhone = this.findBy(admPhone.getPhoneId());

        //TODO CHECK WHAT PROPERTIES ARE ALLOWED TO BE UPDATED

        if (admPhone.getPhone() != null) {
            currentPhone.setPhone(admPhone.getPhone());
        }
        if (admPhone.getStatus() != null) {
            currentPhone.setStatus(admPhone.getStatus());
        }
        if (admPhone.getType() != null) {
            currentPhone.setType(admPhone.getType());
        }
        if (admPhone.getLeader() != null) {
            currentPhone.setLeader(admPhone.getLeader());
        }
        currentPhone = this.save(currentPhone);
        resetLeader(currentPhone);
    }

    //TODO document
    //Method allows to persist multiple phones
    public List<AdmPhone> saveList(List<AdmPhone> phoneList) {
        for (AdmPhone item : phoneList) {
            //if(item.getPhone() != 0){
                this.save(item);
            //}
        }
        return phoneList;
    }

    public List<AdmPhone> getAllPhonesByAssociate(UUID personKey, Long phoneId,
                                                  Long phoneStatus,
                                                  Boolean desc) {
        StringBuilder query = this.getOriginalQuery();

        query.append("INNER JOIN adm_phone_account phAccount ON phone.phone_account_id = phAccount.phone_account_id\n");
        query.append("INNER JOIN adm_person associate ON associate.phone_account_id = phAccount.phone_account_id\n");

        query.append("WHERE 1 = 1 ");

        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        if (phoneId != null && phoneId > 0) {
            query.append("\nAND phone.phone_id = ").append(phoneId);
        }
        if (phoneStatus != null && phoneStatus > 0) {
            query.append("\nAND phone.status = ").append(phoneStatus);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY phone.phone_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY phone.phone_id ASC");
        }


        return this.getPhoneList(query);
    }

    private List<AdmPhone> getPhoneList(StringBuilder query) {
        Stream<AdmPhoneListDto> q = em.createNativeQuery(query.toString(), "admPhoneListDto").getResultStream();
        return q.map(item -> {
            AdmPhone admPhone = new AdmPhone(
                    item.getPhoneId(),
                    new AdmPhoneAccount(
                            item.getPhoneAccountId()
                    ),
                    item.getPhone(),
                    new AdmTypology(
                            item.getStatusId(),
                            item.getStatusDesc()
                    ),
                    new AdmTypology(
                            item.getTypeId(),
                            item.getTypeDesc()
                    ),
                    new AdmUser(
                            new AdmPerson(
                                    item.getCreatedByPersonKey(),
                                    item.getCreatedByEmail()
                            )
                    ),
                    item.getDateCreated(),
                    item.getLeader()
            );
            return admPhone;
        }).collect(Collectors.toList());
    }

    //method that returns query, every method appends according to what it needs
    private StringBuilder getOriginalQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    phone.phone_id,\n" +
                "    phone.phone_account_id,\n" +
                "    phone.phone,\n" +
                "    statusTypo.typology_id       status_id,\n" +
                "    statusTypo.description       status_description,\n" +
                "    typeTypo.typology_id         type_id,\n" +
                "    typeTypo.description         type_description,\n" +
                "    createdByPerson.email                             created_by_email,\n" +
                "    createdByPerson.person_key                        created_by_person_key,\n" +
                "    phone.date_created,\n" +
                "    phone.leader\n" +
                "FROM adm_phone phone\n" +
                "INNER JOIN adm_typology statusTypo ON statusTypo.typology_id = phone.status\n" +
                "INNER JOIN adm_typology typeTypo ON typeTypo.typology_id = phone.type\n" +
                "INNER JOIN adm_user createdBy ON phone.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n");
        return query;
    }

    public List<AdmPhone> getAllBeneficiaryPhonesByAssociate(UUID personKey, Long phoneId, Long phoneStatus) {
        StringBuilder query = this.getOriginalQuery();

        query.append("\nJOIN adm_phone_account phAccount ON phAccount.phone_account_id = phone.phone_account_id");
        query.append("\nJOIN adm_person benePerson ON benePerson.phone_account_id = phAccount.phone_account_id");
        query.append("\nJOIN adm_beneficiary beneBeneficiary ON benePerson.person_id = beneBeneficiary.beneficiary");
        query.append("\nJOIN adm_beneficiary_account beneAccount ON beneBeneficiary.beneficiary_account_id = beneAccount.beneficiary_account_id");
        query.append("\nJOIN adm_person associate ON beneAccount.beneficiary_account_id = associate.beneficiary_account_id");
        query.append("\nWHERE 1 = 1");
        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        query.append("\nAND benePerson.is_beneficiary = ").append(Boolean.TRUE);

        if (phoneId != null && phoneId > 0) {
            query.append("\nAND phone.phone_id = ").append(phoneId);
        }
        if (phoneStatus != null && phoneStatus > 0) {
            query.append("\nAND phone.status = ").append(phoneStatus);
        }

        return this.getPhoneList(query);
    }

    public List<AdmPhone> getAllPartnerPhonesByAssociate(UUID personKey, Long phoneId, Long phoneStatus) {

        StringBuilder query = this.getOriginalQuery();

        query.append("\nJOIN adm_phone_account phAccount ON phAccount.phone_account_id = phone.phone_account_id");
        query.append("\nJOIN adm_person partnerPerson ON partnerPerson.phone_account_id = phAccount.phone_account_id");
        query.append("\nJOIN adm_partner partPartner ON partnerPerson.person_id = partPartner.person");
        query.append("\nJOIN adm_person associate ON partPartner.partner = associate.person_id");
        query.append("\nWHERE 1 = 1");
        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        query.append("\nAND partnerPerson.is_partner = ").append(Boolean.TRUE);

        if (phoneId != null && phoneId > 0) {
            query.append("\nAND phone.phone_id = ").append(phoneId);
        }
        if (phoneStatus != null && phoneStatus > 0) {
            query.append("\nAND phone.status = ").append(phoneStatus);
        }

        return this.getPhoneList(query);
    }

    //Returns phone by phoneAccount id
    public AdmPhone getByPhoneAccount(Long phoneAccountId) {
        Criteria<AdmPhone, AdmPhone> query = criteria();
        query.join(AdmPhone_.phoneAccount,
                where(AdmPhoneAccount.class)
                        .eq(AdmPhoneAccount_.phoneAccountId, phoneAccountId))
                .orderAsc(AdmPhone_.phoneId);

        List<AdmPhone> admPhoneList = query.getResultList();
        return (admPhoneList.size() > 0) ? admPhoneList.get(0) : null;
    }

    //method that checks if phone's property isLeader is TRUE, if so, changes all other phones isLeader to FALSE
    public void resetLeader(AdmPhone admPhone) {
        //logic is applied only if the property is TRUE
        if (admPhone.getLeader()) {
            Criteria<AdmPhone, AdmPhone> query = criteria();
            query.join(AdmPhone_.phoneAccount,
                    where(AdmPhoneAccount.class)
                            .eq(AdmPhoneAccount_.phoneAccountId, admPhone.getPhoneAccount().getPhoneAccountId()));

            List<AdmPhone> admPhoneList = query.getResultList();
            for (AdmPhone item : admPhoneList) {
                if (!item.getPhoneId().equals(admPhone.getPhoneId())) {
                    item.setLeader(Boolean.FALSE);
                } else {
                    item.setLeader(Boolean.TRUE);
                }
                this.save(item);
            }
        }
    }
}
