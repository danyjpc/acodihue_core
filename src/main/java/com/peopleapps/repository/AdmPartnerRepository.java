package com.peopleapps.repository;

import com.peopleapps.dto.inputs.partner.AdmPartnerDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.person.AdmPersonListDto;
import com.peopleapps.dto.credit.AdmCreditPartnerDto;
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
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmPartner.class)
public abstract class AdmPartnerRepository extends AbstractEntityRepository<AdmPartner, Long>
        implements CriteriaSupport<AdmPartner> {

    @Inject
    EntityManager em;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPhoneRepository admPhoneRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    Logger logger;

    public List<AdmPartnerDto> getAllPartners(Long associateId, UUID associateKey, Long statusId, Boolean desc) {
        StringBuilder query = new StringBuilder();


        query.append("Select par.partner_id,\n" +
                "per.person_key            person_key,\n" +
                "per.first_name           first_name,\n" +
                "per.last_name            last_name,\n" +
                "per.cui                  cui,\n"+
                "par.date_created,\n" +
                "statusTypo.typology_id      status_id,\n" +
                "statusTypo.description      status_desc,\n" +
                "par.no_boys,\n" +
                "par.no_girls,\n" +
                "par.leader,\n" +
                "par.age,\n" +
                "per.birthday                       person_birthday,\n" +
                "per.email                           person_mail,\n" +
                "per.name_complete                   person_name_complete,\n" +
                "phone.phone_id                        person_phone_id,\n" +
                "phone.phone                           person_phone,\n" +
                "phoneStatusTypo.typology_id           phone_status_id,\n" +
                "phoneStatusTypo.description           phone_status_desc,\n" +
                "phoneTypeTypo.typology_id             phone_type_id,\n" +
                "phoneTypeTypo.description             phone_type_desc\n" +
                "from adm_person per inner join adm_partner par on per.person_id = par.partner\n" +
                "INNER JOIN adm_typology statusTypo ON par.status = statusTypo.typology_id\n" +
                "LEFT  JOIN adm_phone phone ON per.phone_account_id = phone.phone_account_id\n" +
                "LEFT JOIN adm_typology phoneStatusTypo ON phone.status = phoneStatusTypo.typology_id\n" +
                "LEFT JOIN adm_typology phoneTypeTypo ON phone.type = phoneTypeTypo.typology_id\n"+
                "WHERE person = ").append(associateId);
                query.append("\n AND phone.leader = true");

        /*if (associateKey != null) {
            query.append("\nINNER JOIN adm_person associate ON partner.partner = associate.person_id");
        }
        query.append("\nWHERE 1 = 1");
        if (associateKey != null) {
            query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
            query.append("\nAND associate.person_key = ").append("'").append(associateKey).append("'");
        }

        if (statusId != null && statusId > 0) {
            query.append("\nAND partner.status = ").append(statusId);
        }

        if (partnerId != null && partnerId > 0) {
            query.append("\nAND partner.partner_id = ").append(partnerId);
        }

        //query.append("\nAND person.is_partner = ").append(Boolean.TRUE);

        if (desc != null && desc) {
            query.append("\nORDER BY partner.partner_id DESC, phone.phone_id");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY partner.partner_id ASC, phone.phone_id");
        }*/

        Stream<AdmPartnerListDto> q = em.createNativeQuery(query.toString(), "admPartnerListDto").getResultStream();
        return q.map(item -> {
                    AdmPartnerDto admPartner = new AdmPartnerDto(
                            item.getPartnerId(),
                            new AdmPartnerDto.AdmPartnerPersonDto(
                                    item.getPersonKey(),
                                    item.getPersonFirstName(),
                                    item.getPersonLastName(),
                                    item.getCui(),
                                    item.getPersonBirthday(),
                                    item.getPersonMail(),
                                    item.getPersonNameComplete()
                            ),
                            new AdmPartnerDto.AdmPartnerTypologyDto(
                                    item.getStatusId(),
                                    item.getStatusDesc()
                            ),
                            new AdmPartnerDto.AdmPartnerPhoneDto(
                                    item.getPersonPhoneId(),
                                    item.getPersonPhone(),
                                    new AdmPartnerDto.AdmPartnerTypologyDto(
                                            item.getPhoneTypeId(),
                                            item.getPhoneTypeDesc()
                                    ),
                                    new AdmPartnerDto.AdmPartnerTypologyDto(
                                            item.getPhoneStatusId(),
                                            item.getPhoneStatusDesc()
                                    )
                            ),
                            item.getDateCreated(),
                            item.getNoBoys(),
                            item.getNoGirls(),
                            new AdmPartnerDto.AdmPartnerPersonDto(
                                    item.getCreatedByPersonKey(),
                                    null,
                                    null,
                                    null,
                                    null,
                                    item.getCreatedByEmail(),
                                    null
                            ),
                            null,
                            item.getLeader()
                    );
                    return admPartner;
                }
        ).collect(Collectors.toList());

    }

    private StringBuilder returnBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    partner.partner_id,\n" +
                "    person.person_key            person_key,\n" +
                "    person.first_name           first_name,\n" +
                "    person.last_name            last_name,\n" +
                "    partnerPerson.person_key     partner_person_key,\n" +
                "    partnerPerson.first_name    partner_first_name,\n" +
                "    partnerPerson.last_name     partner_last_name,\n" +
                "    createdByPerson.email                             created_by_email,\n" +
                "    createdByPerson.person_key                        created_by_person_key,\n" +
                "    partner.date_created,\n" +
                "    statusTypo.typology_id      status_id,\n" +
                "    statusTypo.description      status_desc,\n" +
                "    partner.no_boys,\n" +
                "    partner.no_girls,\n" +
                "    partner.leader,\n" +
                "    partner.age,\n" +
                "    person.birthday                       person_birthday,\n" +
                "    person.email                           person_mail,\n" +
                "    person.name_complete                   person_name_complete,\n" +
                "    phone.phone_id                        person_phone_id,\n" +
                "    phone.phone                           person_phone,\n" +
                "    phoneStatusTypo.typology_id           phone_status_id,\n" +
                "    phoneStatusTypo.description           phone_status_desc,\n" +
                "    phoneTypeTypo.typology_id             phone_type_id,\n" +
                "    phoneTypeTypo.description             phone_type_desc\n" +
                "FROM adm_partner partner\n" +
                "INNER JOIN adm_person person ON partner.person = person.person_id\n" +
                "INNER JOIN adm_person partnerPerson ON partner.partner = partnerPerson.person_id\n" +
                "INNER JOIN adm_user createdBy ON partner.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo ON partner.status = statusTypo.typology_id\n" +
                "LEFT  JOIN adm_phone phone ON person.phone_account_id = phone.phone_account_id\n" +
                "LEFT JOIN adm_typology phoneStatusTypo ON phone.status = phoneStatusTypo.typology_id\n" +
                "LEFT JOIN adm_typology phoneTypeTypo ON phone.type = phoneTypeTypo.typology_id\n");
        return query;
    }

    protected AdmPartner createNewPartner(AdmPartner admPartner) {

        //Setting person
        AdmPerson person = admPersonRepository.findBy(admPartner.getPerson().getPersonId());
        admPartner.setPerson(person);

        //TODO check how to create partner
        //Setting partner
        AdmPerson partner = admPersonRepository.createPerson(admPartner.getPartner());
        admPartner.setPartner(partner);

        admPartner.setDateCreated(CsnFunctions.now());

        admPartner.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));

        if (admPartner.getNoBoys() == null) {
            admPartner.setNoBoys(CsnConstants.ZERO);
        }
        if (admPartner.getNoGirls() == null) {
            admPartner.setNoGirls(CsnConstants.ZERO);
        }
        if (admPartner.getLeader() == null) {
            admPartner.setLeader(Boolean.FALSE);
        }
        return admPartner;

    }

    public void editCustom(AdmPartner admPartner) {
        AdmPartner currentPartner = this.findBy(admPartner.getPartnerId());

        admPartner.setPartnerId(currentPartner.getPartnerId());
        admPartner.setCreatedBy(currentPartner.getCreatedBy());
        admPartner.setDateCreated(currentPartner.getDateCreated());

        if (admPartner.getPerson() == null) {
            admPartner.setPerson(currentPartner.getPerson());
        }
        if (admPartner.getPartner() == null) {
            admPartner.setPartner(currentPartner.getPartner());
        }
        if (admPartner.getStatus() == null) {
            admPartner.setStatus(currentPartner.getStatus());
        }
        if (admPartner.getNoBoys() == null) {
            admPartner.setNoBoys(currentPartner.getNoBoys());
        }
        if (admPartner.getNoGirls() == null) {
            admPartner.setNoGirls(currentPartner.getNoGirls());
        }
        if (admPartner.getLeader() == null) {
            admPartner.setLeader(currentPartner.getLeader());
        }
        if (admPartner.getAge() == null) {
            admPartner.setAge(currentPartner.getAge());
        }

        this.save(admPartner);

    }


    //Credit partner methods.

    public List<AdmPartnerDto> getPartnersByCreditKey(UUID creditKey, Boolean desc, Long partnerId) {

        StringBuilder query = new StringBuilder();
        query.append("Select par.partner_credit_id partner_credit_id,\n" +
                "per.person_key            person_key,\n" +
                "per.first_name           first_name,\n" +
                "per.last_name            last_name,\n" +
                "par.date_created       date_created,\n" +
                "statusTypo.typology_id      status_id,\n" +
                "statusTypo.description      status_desc,\n" +
                "per.birthday                       person_birthday,\n" +
                "per.email                           person_mail,\n" +
                "per.name_complete                   person_name_complete,\n" +
                "phone.phone_id                        person_phone_id,\n" +
                "phone.phone                           person_phone,\n" +
                "phoneStatusTypo.typology_id           phone_status_id,\n" +
                "phoneStatusTypo.description           phone_status_desc,\n" +
                "phoneTypeTypo.typology_id             phone_type_id,\n" +
                "phoneTypeTypo.description             phone_type_desc\n" +
                "from adm_credit credito \n" +
                "inner join adm_partner_credit par on credito.credit_id = par.credit_id\n" +
                "Inner join adm_person per on per.person_id = par.person_id\n" +
                "INNER JOIN adm_typology statusTypo ON par.status = statusTypo.typology_id\n" +
                "LEFT  JOIN adm_phone phone ON per.phone_account_id = phone.phone_account_id\n" +
                "LEFT JOIN adm_typology phoneStatusTypo ON phone.status = phoneStatusTypo.typology_id\n" +
                "LEFT JOIN adm_typology phoneTypeTypo ON phone.type = phoneTypeTypo.typology_id\n" +
                "WHERE credito.credit_key = '").append(creditKey).append("'");

        if (partnerId != null && partnerId > 0) {
            query.append("AND par.partner_credit_id = ").append(partnerId).append("\n");
        }

        Stream<AdmCreditPartnerDto> q = em.createNativeQuery(query.toString(), "admPartnerCreditLDto").getResultStream();
        return q.map(item -> {
                    AdmPartnerDto admPartner = new AdmPartnerDto(
                            item.getPartner_credit_id(),
                            new AdmPartnerDto.AdmPartnerPersonDto(
                                    item.getPerson_key(),
                                    item.getFirst_name(),
                                    item.getLast_name(),
                                    item.getPerson_birthday(),
                                    item.getPerson_mail(),
                                    item.getPerson_name_complete()
                            ),
                            new AdmPartnerDto.AdmPartnerTypologyDto(
                                    item.getStatus_id(),
                                    item.getStatus_desc()
                            ),
                            new AdmPartnerDto.AdmPartnerPhoneDto(
                                    item.getPerson_phone_id(),
                                    item.getPerson_phone(),
                                    new AdmPartnerDto.AdmPartnerTypologyDto(
                                            item.getPhone_type_id(),
                                            item.getPhone_type_desc()
                                    ),
                                    new AdmPartnerDto.AdmPartnerTypologyDto(
                                            item.getPhone_status_id(),
                                            item.getPhone_status_desc()
                                    )
                            ),
                            item.getDate_created()
                    );
                    return admPartner;
                }
        ).collect(Collectors.toList());
    }

    public Response createPartner(AdmPartnerDto partnerDto, UUID associateKey) {

        try {
            AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            if (partnerDto.getCreatedBy() == null || partnerDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }
            if (partnerDto.getPhone() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Phone is mandatory");
            }

            AdmPerson createdByPerson = admPersonRepository.findByKey(partnerDto.getCreatedBy().getPersonKey());
            if (createdByPerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(createdByPerson.getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmTypology status = null;
            if (partnerDto.getStatus() != null) {
                status = this.admTypologyRepository.findBy(partnerDto.getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }

            //partner person
            AdmPerson personPartner = new AdmPerson(
                    0L,
                    admPhoneAccountRepository.createAccount(),
                    admDocumentAccountRepository.createAccount(),
                    admAddressAccountRepository.createAccount(),
                    admBeneficiaryAccountRepository.createAccount(),
                    partnerDto.getPerson().getFirstName() != null ? partnerDto.getPerson().getFirstName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    partnerDto.getPerson().getLastName() != null ? partnerDto.getPerson().getLastName().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    CsnConstants.DEFAULT_TEXT_SD,
                    partnerDto.getPerson().getBirthday() != null ? partnerDto.getPerson().getBirthday() : LocalDate.parse(CsnConstants.ONLY_DATE_EMPTY),
                    partnerDto.getPerson().getEmail() != null ? partnerDto.getPerson().getEmail() : CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.ZERO,
                    CsnConstants.DEFAULT_TEXT_SD,
                    new AdmTypology(CsnConstants.DEFAULT_COUNTRY_ID),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY),
                    status,
                    createdByUser,
                    CsnFunctions.now(),
                    Boolean.TRUE, //is partner
                    Boolean.FALSE,
                    CsnConstants.ZERO,
                    CsnFunctions.generateKey(),
                    partnerDto.getPerson().getNameComplete() != null ? partnerDto.getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")) : CsnConstants.DEFAULT_TEXT_SD,
                    Boolean.FALSE,
                    new AdmTypology(CsnConstants.TYPOLOGY_EMPTY)
            );
            //persisting partner person
            admPersonRepository.save(personPartner);

            //partner to persist
            AdmPartner newPartner = new AdmPartner(
                    0L,
                    associate,
                    personPartner,
                    createdByUser,
                    CsnFunctions.now(),
                    new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    partnerDto.getNoBoys() != null ? partnerDto.getNoBoys() : CsnConstants.ZERO,
                    partnerDto.getNoGirls() != null ? partnerDto.getNoGirls() : CsnConstants.ZERO,
                    partnerDto.getIsLeader() != null ? partnerDto.getIsLeader() : Boolean.FALSE, // leader
                    CsnConstants.ZERO //age
            );
            this.save(newPartner);
            this.resetPartnerLeader(newPartner);

            AdmPhone newPhone = new AdmPhone(
                    0l,
                    personPartner.getPhoneAccount(),
                    partnerDto.getPhone().getPhone(),
                    partnerDto.getPhone().getStatus() != null ? new AdmTypology(partnerDto.getPhone().getStatus().getTypologyId()) :
                            new AdmTypology(CsnConstants.STATUS_ACTIVE),
                    partnerDto.getPhone().getType() != null ? new AdmTypology(partnerDto.getPhone().getType().getTypologyId()) :
                            new AdmTypology(CsnConstants.HOME_PHONE_ID),

                    personPartner.getCreatedBy(),
                    CsnFunctions.now(),
                    Boolean.FALSE
            );
            newPhone =admPhoneRepository.save(newPhone);
            admPhoneRepository.resetLeader(newPhone);

            return CsnFunctions.setResponse(newPartner.getPartnerId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    public AdmPartner findByKey(UUID key) {

        Criteria<AdmPartner, AdmPartner> query = criteria();
        query.join(AdmPartner_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.personKey, key));
        return query.getAnyResult();
    }

    //method that checks if partner's property isLeader is TRUE, if so, changes all other partners isLeader to FALSE
    public void resetPartnerLeader(AdmPartner admPartner) {

        //logic is applied only if the property is TRUE
        if (admPartner.getLeader()) {
            Criteria<AdmPartner, AdmPartner> query = criteria();
            query.join(AdmPartner_.partner,
                    where(AdmPerson.class)
                            .eq(AdmPerson_.personId, admPartner.getPartner().getPersonId()));

            List<AdmPartner> admPartnerList = query.getResultList();
            for (AdmPartner item : admPartnerList) {
                if (!item.getPartnerId().equals(admPartner.getPartnerId())) {
                    item.setLeader(Boolean.FALSE);
                } else {
                    item.setLeader(Boolean.TRUE);
                }
                this.save(item);
            }
        }
    }
}
