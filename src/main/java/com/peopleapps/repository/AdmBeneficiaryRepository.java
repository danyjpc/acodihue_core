package com.peopleapps.repository;

import com.peopleapps.dto.beneficiary.AdmBeneficiaryListDto;
import com.peopleapps.dto.inputs.beneficiary.AdmBeneficiaryDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
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
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmBeneficiary.class)
public abstract class AdmBeneficiaryRepository extends AbstractEntityRepository<AdmBeneficiary, Long>
        implements CriteriaSupport<AdmBeneficiary> {

    @Inject
    EntityManager em;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    public List<AdmBeneficiaryDto> getAllBeneficiaries(Long beneficiaryId, UUID associateKey, Long statusId, Boolean desc) {
        StringBuilder query = this.returnBaseQuery();

        if (associateKey != null) {
            query.append("\nINNER JOIN adm_beneficiary_account  beneAccount ON beneficiary.beneficiary_account_id = beneAccount.beneficiary_account_id ");
            query.append("\nINNER JOIN adm_person associate ON beneAccount.beneficiary_account_id = associate.beneficiary_account_id");
        }
        query.append("\nWHERE 1 = 1");
        if (associateKey != null) {
            query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
            query.append("\nAND associate.person_key = ").append("'").append(associateKey).append("'");
        }
        if (statusId != null && statusId > 0) {
            query.append("\nAND beneficiary.status = ").append(statusId);
        }
        if (beneficiaryId != null && beneficiaryId > 0)
            query.append(" AND beneficiary.beneficiary_id = ").append(beneficiaryId);

        query.append("\nAND person.is_beneficiary = ").append(Boolean.TRUE);

        if (desc != null && desc) {
            query.append("\nORDER BY beneficiary.beneficiary_id DESC, phone.phone_id");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY beneficiary.beneficiary_id ASC, phone.phone_id");
        }


        Stream<AdmBeneficiaryListDto> q = em.createNativeQuery(query.toString(), "admBeneficiaryListDto").getResultStream();
        return q.map(item -> {
                    AdmBeneficiaryDto admBeneficiary = new AdmBeneficiaryDto(
                            item.getBeneficiaryId(),
                            new AdmBeneficiaryDto.AdmBeneficiaryPersonDto(
                                    item.getPersonBeneficiaryKey(),
                                    item.getPersonBeneficiaryFirstName(),
                                    item.getPersonBeneficiaryLastName(),
                                    item.getBirthday(),
                                    item.getEmail(),
                                    item.getPersonBeneficiaryNameComplete()
                            ),
                            new AdmBeneficiaryDto.AdmBeneficiaryTypologyDto(
                                    item.getKinshipId(),
                                    item.getKinshipDesc()
                            ),
                            new AdmBeneficiaryDto.AdmBeneficiaryTypologyDto(
                                    item.getStatusId(),
                                    item.getStatusDesc()
                            ),
                            new AdmBeneficiaryDto.AdmBeneficiaryPhoneDto(
                                    item.getPhone()
                            ),
                            item.getDateCreated(),
                            item.getPercent(),
                            item.getAge(),
                            new AdmBeneficiaryDto.AdmBeneficiaryPersonDto(
                                    item.getCreatedByPersonKey(),
                                    null,
                                    null,
                                    null,
                                    item.getCreatedByEmail(),
                                    null),
                            new AdmBeneficiaryAccount(
                                    item.getBeneficiaryAccountId()
                            ));

                    return admBeneficiary;
                }
        ).collect(Collectors.toList());

    }

    public AdmBeneficiary createBeneficiary(AdmBeneficiary admBeneficiary) {
        AdmBeneficiary admBeneficiary1 = this.createNewBeneficiary(admBeneficiary);
        return this.save(admBeneficiary1);
    }

    protected AdmBeneficiary createNewBeneficiary(AdmBeneficiary admBeneficiary) {

        //Setting person
        AdmPerson beneficiary = admPersonRepository.createPerson(admBeneficiary.getBeneficiary());
        admBeneficiary.setBeneficiary(beneficiary);

        if (admBeneficiary.getBeneficiaryAccount() == null
                || admBeneficiary.getBeneficiaryAccount().getBeneficiaryAccountId() == 0) {
            admBeneficiary.setBeneficiaryAccount(admBeneficiaryAccountRepository.createAccount());

        }

        if (admBeneficiary.getCreatedBy() != null) {
            AdmUser createdBy = this.admUserRepository.findByKey(admBeneficiary.getCreatedBy().getPerson().getPersonKey());
            admBeneficiary.setCreatedBy(createdBy);
        }


        admBeneficiary.setDateCreated(CsnFunctions.now());
        admBeneficiary.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));
        if (admBeneficiary.getPercent() == null) {
            admBeneficiary.setPercent(CsnConstants.ZERO_DOUBLE);
        }
        if (admBeneficiary.getAge() == null) {
            admBeneficiary.setAge(CsnConstants.ZERO);
        }
        return admBeneficiary;

    }

    public void editCustom(AdmBeneficiary admBeneficiary) throws Exception {
        AdmBeneficiary currentBeneficiary = this.findBy(admBeneficiary.getBeneficiaryId());
        if (currentBeneficiary == null)
            throw new Exception("beneficiary not found");

        AdmBeneficiaryAccount benAccount = this.admBeneficiaryAccountRepository
                .findBy(admBeneficiary.getBeneficiaryAccount().getBeneficiaryAccountId());

        if (benAccount == null)
            throw new Exception("beneficiary account not found");

        AdmPerson personBeneficiary = this.admPersonRepository.findBy(admBeneficiary.getBeneficiary().getPersonId());

        if (personBeneficiary == null)
            throw new Exception("beneficiary person not found");

        admBeneficiary.setCreatedBy(currentBeneficiary.getCreatedBy());
        admBeneficiary.setDateCreated(currentBeneficiary.getDateCreated());

        if (admBeneficiary.getBeneficiaryAccount() == null) {
            admBeneficiary.setBeneficiaryAccount(currentBeneficiary.getBeneficiaryAccount());
        }
        //TODO CHECK IF BENEFICIARY CAN BE UPDATED
        if (admBeneficiary.getBeneficiary() == null) {
            admBeneficiary.setBeneficiary(currentBeneficiary.getBeneficiary());
        }

        if (admBeneficiary.getStatus() == null) {
            admBeneficiary.setStatus(currentBeneficiary.getStatus());
        }
        if (admBeneficiary.getKinship() == null) {
            admBeneficiary.setKinship(currentBeneficiary.getKinship());
        }
        if (admBeneficiary.getPercent() == null) {
            admBeneficiary.setPercent(currentBeneficiary.getPercent());
        }
        if (admBeneficiary.getAge() == null) {
            admBeneficiary.setAge(currentBeneficiary.getAge());
        }

        this.save(admBeneficiary);
    }

    //Method that returns base sql query
    private StringBuilder returnBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    beneficiary.beneficiary_id,\n" +
                "    beneficiary.beneficiary_account_id,\n" +
                "    person.person_key                        person_beneficiary_key,\n" +
                "    person.first_name                       person_beneficiary_first_name,\n" +
                "    person.last_name                        person_beneficiary_last_name,\n" +
                "    createdByPerson.email                             created_by_email,\n" +
                "    createdByPerson.person_key                        created_by_person_key,\n" +
                "    beneficiary.date_created,\n" +
                "    statusTypo.typology_id                  status_id,\n" +
                "    statusTypo.description                  status_desc,\n" +
                "    kinshipTypo.typology_id                  kinship_id,\n" +
                "    kinshipTypo.description                kinship_desc,\n" +
                "    beneficiary.percent,\n" +
                "    beneficiary.age,\n" +
                "    person.name_complete                   person_name_complete\n," +
                "    phone.phone_id                         phone_id,\n" +
                "    phone.phone                            phone,\n" +
                "    person.birthday                        person_birthday,\n" +
                "    person.email                           person_email\n" +
                "FROM adm_beneficiary beneficiary\n" +
                "INNER JOIN adm_person person ON beneficiary.beneficiary = person.person_id\n" +
                "INNER JOIN adm_user createdBy ON beneficiary.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo ON beneficiary.status = statusTypo.typology_id\n" +
                "INNER JOIN adm_typology kinshipTypo ON beneficiary.kinship = kinshipTypo.typology_id\n" +
                "LEFT JOIN adm_phone phone ON person.phone_account_id = phone.phone_account_id");
        return query;
    }
}
