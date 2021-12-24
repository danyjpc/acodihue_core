package com.peopleapps.repository;


import com.peopleapps.dto.globalSearch.AdmGlobalSearchDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchListDto;
import com.peopleapps.dto.inputs.associate.AdmAssociateListDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;

import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmBeneficiaryAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.multiAccount.AdmPhoneAccountDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.person.AdmPersonListDto;


import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;

import com.peopleapps.util.StringMatcher;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import javax.persistence.Query;
import javax.validation.*;

import org.slf4j.Logger;

import java.util.*;

import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmPerson.class)
public abstract class AdmPersonRepository extends AbstractEntityRepository<AdmPerson, Long>
        implements CriteriaSupport<AdmPerson> {

    @Inject
    EntityManager em;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmPhoneAccountRepository admPhoneAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmBeneficiaryAccountRepository admBeneficiaryAccountRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    Logger logger;

    public List<AdmPersonDto> getAllPerson(UUID personKey, Boolean desc) {
        StringBuilder query = this.getOriginalQuery();
        query.append("WHERE 1 = 1 \n");
        if (personKey != null)
            query.append(" and person.person_key = ").append("'").append(personKey).append("'");

        if (desc != null && desc) {
            query.append("\nORDER BY person.person_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY person.person_id ASC");
        }


        Stream<AdmPersonListDto> q = em.createNativeQuery(query.toString(), "admPersonListDto").getResultStream();
        return q.map(item -> {
                    AdmPersonDto admPerson = new AdmPersonDto(
                            item.getPersonKey(),
                            new AdmPhoneAccountDto(
                                    item.getPhoneAccountId()
                            ),
                            new AdmDocumentAccountDto(
                                    item.getDocumentAccountId()
                            ),
                            new AdmAddressAccountDto(
                                    item.getAddressAccountId()
                            ),
                            new AdmBeneficiaryAccountDto(
                                    item.getBeneficiaryAccountId()
                            ),
                            item.getFirstName(),
                            item.getMiddleName(),
                            item.getLastName(),
                            item.getPartnerName(),
                            item.getMarriedName(),
                            item.getBirthday(),
                            item.getEmail(),
                            new AdmTypologyDto(
                                    item.getMaritalStatusId(),
                                    item.getMaritalStatusDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getProfessionId(),
                                    item.getProfessionDesc()
                            ),
                            item.getCui(),
                            new AdmTypologyDto(
                                    item.getDocumentTypeId(),
                                    item.getDocumentTypeDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getDocumentOrderId(),
                                    item.getDocumentOrderDesc()
                            ),
                            item.getOrderNumber(),
                            item.getNit(),
                            new AdmTypologyDto(
                                    item.getCountryOfBirthId(),
                                    item.getCountryOfBirthDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getStateOfBirthId(),
                                    item.getStateOfBirthDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getCityOfBirthId(),
                                    item.getCountryOfBirthDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getImmigrationConditionId(),
                                    item.getImmigrationConditionDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getGenreId(),
                                    item.getGenreDesc()
                            ),
                            item.getPassport(),
                            item.getOwnAccount(),
                            item.getOwnAccountDescription(),
                            new AdmTypologyDto(
                                    item.getMayanPeopleId(),
                                    item.getMayanPeopleDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getRoleId(),
                                    item.getRoleDesc()
                            ),
                            new AdmTypologyDto(
                                    item.getStatusId(),
                                    item.getStatusDesc()
                            ),
                            new AdmUserInfoDto(
                                    new AdmUserInfoDto.AdmPersonUserInfoDto(
                                            item.getCreatedByPersonKey(),
                                            item.getCreatedByEmail()
                                    )
                            ),
                            item.getDateCreated(),
                            item.getPartner(),
                            item.getBeneficiary(),
                            CsnFunctions.getAccountNumberOrder(item.getMembershipNumber()),
                            item.getNameComplete(),
                            item.getAssociate(),
                            new AdmTypologyDto(
                                    item.getLinguisticCommunityId(),
                                    item.getLinguisticCommunityDesc()
                            )
                    );


                    return admPerson;
                }
        ).collect(Collectors.toList());

    }

    public AdmPerson createPerson(AdmPerson admPerson) {
        AdmPerson admPerson1 = this.createNewPerson(admPerson);
        return this.save(admPerson1);
    }

    protected AdmPerson createNewPerson(AdmPerson admPerson) {

        //Generating Key
        admPerson.setPersonKey(CsnFunctions.generateKey());
        AdmTypology emptyTypology = new AdmTypology();
        emptyTypology.setTypologyId(CsnConstants.TYPOLOGY_EMPTY);

        AdmTypology statusTypology = new AdmTypology();
        statusTypology.setTypologyId(CsnConstants.STATUS_ACTIVE);

        //TODO check default user when creating
        AdmUser createdBy = new AdmUser();
        createdBy.setUserId(CsnConstants.DEFAULT_USER_ID);

        admPerson.setDateCreated(CsnFunctions.now());

        if (admPerson.getPhoneAccount() == null || admPerson.getPhoneAccount().getPhoneAccountId() == 0) {
            admPerson.setPhoneAccount(admPhoneAccountRepository.createAccount());
        }
        if (admPerson.getDocumentAccount() == null || admPerson.getDocumentAccount().getDocumentAccountId() == 0) {
            admPerson.setDocumentAccount(admDocumentAccountRepository.createAccount());
        }
        if (admPerson.getAddressAccount() == null || admPerson.getAddressAccount().getAddressAccountId() == 0) {
            admPerson.setAddressAccount(admAddressAccountRepository.createAccount());
        }
        if (admPerson.getBeneficiaryAccount() == null || admPerson.getBeneficiaryAccount().getBeneficiaryAccountId() == 0) {
            admPerson.setBeneficiaryAccount(admBeneficiaryAccountRepository.createAccount());
        }
        if (admPerson.getFirstName() == null) {
            admPerson.setFirstName(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getMiddleName() == null) {
            admPerson.setMiddleName(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getLastName() == null) {
            admPerson.setLastName(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getPartnerName() == null) {
            admPerson.setPartnerName(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getMarriedName() == null) {
            admPerson.setMarriedName(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getBirthday() == null) {
            admPerson.setBirthday(CsnFunctions.now().toLocalDate());
        }
        if (admPerson.getEmail() == null) {
            admPerson.setEmail(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getMaritalStatus() == null) {
            admPerson.setMaritalStatus(emptyTypology);
        }
        if (admPerson.getProfession() == null) {
            admPerson.setProfession(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));
        }
        if (admPerson.getCui() == null) {
            admPerson.setCui(CsnConstants.ZERO);
        }
        if (admPerson.getDocumentType() == null) {
            admPerson.setDocumentType(emptyTypology);
        }
        if (admPerson.getDocumentOrder() == null) {
            admPerson.setDocumentOrder(emptyTypology);
        }
        if (admPerson.getOrderNumber() == null) {
            admPerson.setOrderNumber(CsnConstants.ZERO);
        }
        if (admPerson.getNit() == null) {
            admPerson.setNit(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getCountryOfBirth() == null) {
            admPerson.setCountryOfBirth(emptyTypology);
        }
        if (admPerson.getStateOfBirth() == null) {
            admPerson.setStateOfBirth(emptyTypology);
        }
        if (admPerson.getCityOfBirth() == null) {
            admPerson.setCityOfBirth(emptyTypology);
        }
        if (admPerson.getImmigrationCondition() == null) {
            admPerson.setImmigrationCondition(emptyTypology);
        }
        if (admPerson.getGenre() == null) {
            admPerson.setGenre(emptyTypology);
        }
        if (admPerson.getPassport() == null) {
            admPerson.setPassport(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getOwnAccount() == null) {
            admPerson.setOwnAccount(Boolean.FALSE);
        }
        if (admPerson.getOwnAccountDescription() == null) {
            admPerson.setOwnAccountDescription(Boolean.FALSE);
        }
        if (admPerson.getMayanPeople() == null) {
            admPerson.setMayanPeople(emptyTypology);
        }
        if (admPerson.getRole() == null) {
            admPerson.setRole(emptyTypology);
        }
        if (admPerson.getStatus() == null) {
            admPerson.setStatus(statusTypology);
        }
        if (admPerson.getCreatedBy() == null) {
            admPerson.setCreatedBy(createdBy);
        }
        if (admPerson.getIsPartner() == null) {
            admPerson.setIsPartner(Boolean.FALSE);
        }
        if (admPerson.getIsBeneficiary() == null) {
            admPerson.setIsBeneficiary(Boolean.FALSE);
        }
        if (admPerson.getMembershipNumber() == null) {
            admPerson.setMembershipNumber(CsnConstants.ZERO);
        }
        if (admPerson.getNameComplete() == null) {
            admPerson.setNameComplete(CsnConstants.DEFAULT_TEXT_SD);
        }
        if (admPerson.getIsAssociate() == null) {
            admPerson.setIsAssociate(Boolean.FALSE);
        }

        if (admPerson.getLinguisticCommunity() == null) {
            admPerson.setLinguisticCommunity(new AdmTypology(CsnConstants.TYPOLOGY_EMPTY));
        }


        return admPerson;

    }


    public AdmPerson editCustom(AdmPerson admPerson) {
        try {
            AdmPerson currentPerson = this.findByKey(admPerson.getPersonKey());

            admPerson.setPersonId(currentPerson.getPersonId());

            if (admPerson.getPhoneAccount() == null) {
                admPerson.setPhoneAccount(currentPerson.getPhoneAccount());
            }
            if (admPerson.getDocumentAccount() == null) {
                admPerson.setDocumentAccount(currentPerson.getDocumentAccount());
            }
            if (admPerson.getAddressAccount() == null) {
                admPerson.setAddressAccount(currentPerson.getAddressAccount());
            }
            if (admPerson.getBeneficiaryAccount() == null) {
                admPerson.setBeneficiaryAccount(currentPerson.getBeneficiaryAccount());
            }
            if (admPerson.getFirstName() == null) {
                admPerson.setFirstName(currentPerson.getFirstName().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getMiddleName() == null) {
                admPerson.setMiddleName(currentPerson.getMiddleName().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getLastName() == null) {
                admPerson.setLastName(currentPerson.getLastName().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getPartnerName() == null) {
                admPerson.setPartnerName(currentPerson.getPartnerName().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getMarriedName() == null) {
                admPerson.setMarriedName(currentPerson.getMarriedName().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getBirthday() == null) {
                admPerson.setBirthday(currentPerson.getBirthday());
            }
            if (admPerson.getEmail() == null) {
                admPerson.setEmail(currentPerson.getEmail());
            }
            if (admPerson.getMaritalStatus() == null) {
                admPerson.setMaritalStatus(currentPerson.getMaritalStatus());
            }
            if (admPerson.getProfession() == null) {
                admPerson.setProfession(currentPerson.getProfession());
            }
            if (admPerson.getCui() == null) {
                admPerson.setCui(currentPerson.getCui());
            }
            if (admPerson.getDocumentType() == null) {
                admPerson.setDocumentType(currentPerson.getDocumentType());
            }
            if (admPerson.getDocumentOrder() == null) {
                admPerson.setDocumentOrder(currentPerson.getDocumentOrder());
            }
            if (admPerson.getOrderNumber() == null) {
                admPerson.setOrderNumber(currentPerson.getOrderNumber());
            }
            if (admPerson.getNit() == null) {
                admPerson.setNit(currentPerson.getNit());
            }
            if (admPerson.getCountryOfBirth() == null) {
                admPerson.setCountryOfBirth(currentPerson.getCountryOfBirth());
            }
            if (admPerson.getStateOfBirth() == null) {
                admPerson.setStateOfBirth(currentPerson.getStateOfBirth());
            }
            if (admPerson.getCityOfBirth() == null) {
                admPerson.setCityOfBirth(currentPerson.getCityOfBirth());
            }
            if (admPerson.getImmigrationCondition() == null) {
                admPerson.setImmigrationCondition(currentPerson.getImmigrationCondition());
            }
            if (admPerson.getGenre() == null) {
                admPerson.setGenre(currentPerson.getGenre());
            }
            if (admPerson.getPassport() == null) {
                admPerson.setPassport(currentPerson.getPassport());
            }
            if (admPerson.getOwnAccount() == null) {
                admPerson.setOwnAccount(currentPerson.getOwnAccount());
            }
            if (admPerson.getOwnAccountDescription() == null) {
                admPerson.setOwnAccountDescription(currentPerson.getOwnAccountDescription());
            }
            if (admPerson.getMayanPeople() == null) {
                admPerson.setMayanPeople(currentPerson.getMayanPeople());
            }
            if (admPerson.getRole() == null) {
                admPerson.setRole(currentPerson.getRole());
            }
            if (admPerson.getStatus() == null) {
                admPerson.setStatus(currentPerson.getStatus());
            }
            if (admPerson.getCreatedBy() == null) {
                admPerson.setCreatedBy(currentPerson.getCreatedBy());
            }
            if (admPerson.getDateCreated() == null) {
                admPerson.setDateCreated(currentPerson.getDateCreated());
            }
            if (admPerson.getIsPartner() == null) {
                admPerson.setIsPartner(currentPerson.getIsPartner());
            }
            if (admPerson.getIsBeneficiary() == null) {
                admPerson.setIsBeneficiary(currentPerson.getIsBeneficiary());
            }
            if (admPerson.getMembershipNumber() == null) {
                admPerson.setMembershipNumber(currentPerson.getMembershipNumber());
            }
            if (admPerson.getNameComplete() == null) {
                admPerson.setNameComplete(currentPerson.getNameComplete().toUpperCase(new Locale("es", "ES")));
            }
            if (admPerson.getIsAssociate() == null) {
                admPerson.setIsAssociate(currentPerson.getIsAssociate());
            }
            if (admPerson.getLinguisticCommunity() == null) {
                admPerson.setLinguisticCommunity(currentPerson.getLinguisticCommunity());
            }

            logger.error("PRUEBA SALIDA " + admPerson.getLinguisticCommunity().getTypologyId());
            admPerson = this.save(admPerson);
        } catch (ConstraintViolationException e) {
            logger.error(Level.SEVERE + " Exception: ");
            e.getConstraintViolations().forEach(err -> logger.error("++++++++++++++++++++" + err.toString()));
        }
        return admPerson;
    }

    public AdmPerson findByCui(Long cui) {
        Criteria<AdmPerson, AdmPerson> query = criteria();
        query.eq(AdmPerson_.cui, cui);
        List<AdmPerson> admPersonList = query.getResultList();
        return (admPersonList.size() > 0) ? admPersonList.get(0) : null;
    }

    public AdmPerson findByNit(String nit) {
        Criteria<AdmPerson, AdmPerson> query = criteria();
        query.eq(AdmPerson_.nit, nit);
        List<AdmPerson> admPersonList = query.getResultList();
        return (admPersonList.size() > 0) ? admPersonList.get(0) : null;
    }

    public AdmPerson findByEmail(String email) {
        Criteria<AdmPerson, AdmPerson> query = criteria();
        query.eq(AdmPerson_.email, email);
        List<AdmPerson> admPersonList = query.getResultList();
        return (admPersonList.size() > 0) ? admPersonList.get(0) : null;
    }

    public AdmPerson findByKey(UUID key) {
        Criteria<AdmPerson, AdmPerson> query = criteria();
        query.eq(AdmPerson_.personKey, key);
        List<AdmPerson> admPersonList = query.getResultList();
        return (admPersonList.size() > 0) ? admPersonList.get(0) : null;
    }


    //Returns associates and organizations
    public List<AdmPreinscripcionDto> getAssociates(
            Long stateId,
            Long cityId,
            String date_ini,
            String date_end,
            String name,
            Long cui,
            Long membershipNumber,
            Boolean desc,
            UUID associateKey,
            UUID agencyKey) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    associate.person_key                  associate_person_key,\n" +
                "    associate.name_complete               associate_name_complete,\n" +
                "    associate.membership_number           associate_membership_number,\n" +
                "    associate.date_created                associate_date_created,\n" +
                "    organization.organization_key         organization_key,\n" +
                "    organization.organization_name        organization_name\n" +
                "FROM adm_person associate\n" +
                "INNER JOIN adm_organization_responsible   orgResp                    ON associate.person_id = orgResp.person_id\n" +
                "INNER JOIN adm_organization               organization               ON orgResp.organization_id = organization.organization_id\n" +
                "INNER JOIN adm_address_account            associateAddressAccount    ON associate.address_account_id = associateAddressAccount.address_account_id\n" +
                "INNER JOIN adm_address                    associateAddress           ON associateAddressAccount.address_account_id = associateAddress.address_account_id\n" +
                "WHERE 1 = 1\n" +
                "AND associate.is_associate = TRUE\n" +
                "AND associateAddress.address_id         IN (SELECT MAX(address_id)                  FROM adm_address                  WHERE address_account_id  = associate.address_account_id)\n" +
                "AND orgResp.organization_responsible_id IN (SELECT MAX(organization_responsible_id) FROM adm_organization_responsible WHERE person_id  = associate.person_id)\n");
        if (stateId != null && stateId > 0) {
            query.append("\nAND associateAddress.state = ").append(stateId);
        }
        if (cityId != null && cityId > 0) {
            query.append("\nAND associateAddress.city = ").append(cityId);
        }
        if (date_ini != null && date_end != null) {
            date_ini += " 00:00:00";
            date_end += " 23:59:59";
            query.append("\nAND associate.date_created BETWEEN '")
                    .append(date_ini).append("' AND '")
                    .append(date_end).append("'");
        }
        if (cui != null && cui > 0) {
            query.append("\nAND associate.cui = ").append(cui);
        }
        if (membershipNumber != null && membershipNumber > 0) {
            query.append("\nAND associate.membership_number = ").append(membershipNumber);
        }
        if (name != null && !name.equals("")) {

            String[] tokens = name.split(" ");
            System.out.println(tokens.length);
            StringBuilder cadena = new StringBuilder();
            //de manera dinamica se agregan los tokens a los criterios de busqueda sql
            //ILIKE busca de forma que ignora el case del token
            if (tokens.length > 0) {
                cadena.append("associate.name_complete ILIKE '%").append(tokens[0]).append("%'");
                if (tokens.length > 1) {
                    for (int i = 1; i < tokens.length; i++) {
                        cadena.append("\nOR associate.name_complete ILIKE '%").append(tokens[i]).append("%'");
                    }
                }
            }
            query.append("\nAND (").append(cadena).append(")");
        }
        //search by associate key
        if (associateKey != null) {
            query.append("\nAND associate.person_key = '").append(associateKey).append("'");
        }
        //search by agency
        if (agencyKey != null) {
            query.append("\nAND organization.organization_key = '").append(agencyKey).append("'");
        }

        if (desc != null && desc) {
            query.append("\nORDER BY associate.person_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY associate.person_id ASC");
        }


        Stream<AdmAssociateListDto> q = em.createNativeQuery(query.toString(), "admAssociateListDto").getResultStream();
        return q.map(item -> {
                    AdmPreinscripcionDto admPreinscripcionDto = new AdmPreinscripcionDto(
                            new AdmPreinscripcionDto.AdmPreinscripcionPersonDto(
                                    item.getAssociate_person_key(),
                                    item.getAssociate_name_complete(),
                                    CsnFunctions.getAccountNumberOrder(item.getAssociate_membership_number()),
                                    item.getAssociate_date_created()

                            ),
                            null,
                            null,
                            null,
                            new AdmPreinscripcionDto.AdmPreinscripcionOrganizationDto(
                                    item.getOrganization_key(),
                                    item.getOrganization_name()
                            ),
                            //extraordinary contribution
                            null

                    );
                    return admPreinscripcionDto;
                }
        ).collect(Collectors.toList());

    }


    //Returns associates and organizations
    public List<AdmPreinscripcionDto> getAssociatePreinscription(
            UUID associateKey) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    associate.person_key                  associate_person_key,\n" +
                "    associate.name_complete               associate_name_complete,\n" +
                "    associate.membership_number           associate_membership_number,\n" +
                "    associate.date_created                associate_date_created,\n" +
                "    organization.organization_key         organization_key,\n" +
                "    organization.organization_name        organization_name,\n" +
                "    associate.cui                         associate_cui,\n" +
                "    typo_profession.typology_id           profession_id,\n" +
                "    typo_profession.description           profession_desc,\n" +
                "    organization.entry_contribution       org_entry_contribution,\n" +
                "    organization.entry_fee                organization_entry_fee,\n" +
                "    associateAddress.address_line          asso_address_line,\n" +
                "    associatePhone.phone                  asso_phone\n" +


                "FROM adm_person associate\n" +
                "INNER JOIN adm_organization_responsible   orgResp                    ON associate.person_id = orgResp.person_id\n" +
                "INNER JOIN adm_organization               organization               ON orgResp.organization_id = organization.organization_id\n" +
                "INNER JOIN adm_address_account            associateAddressAccount    ON associate.address_account_id = associateAddressAccount.address_account_id\n" +
                "INNER JOIN adm_address                    associateAddress           ON associateAddressAccount.address_account_id = associateAddress.address_account_id\n" +
                "INNER JOIN adm_phone_account              associatePhoneAccount      ON associate.phone_account_id = associatePhoneAccount.phone_account_id\n" +
                "INNER JOIN adm_phone                      associatePhone             ON associatePhoneAccount.phone_account_id = associatePhone.phone_account_id\n" +
                "INNER JOIN adm_typology                   typo_profession            ON associate.profession_id = typo_profession.typology_id\n" +
                "WHERE 1 = 1\n" +
                "AND associate.is_associate = TRUE");
        if (associateKey != null) {
            query.append("\nAND associate.person_key = ").append("'").append(associateKey).append("'");
        }

        Stream<AdmAssociateListDto> q = em.createNativeQuery(query.toString(), "admAssociatePreinscriptionDto").getResultStream();
        return q.map(item -> {
                    AdmPreinscripcionDto admPreinscripcionDto = new AdmPreinscripcionDto(
                            new AdmPreinscripcionDto.AdmPreinscripcionPersonDto(
                                    item.getAssociate_person_key(),
                                    item.getAssociate_cui(),
                                    new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                            item.getProfession_id(),
                                            item.getProfession_desc()
                                    ),
                                    new AdmPreinscripcionDto.AdmPreinscripcionAddressDto(
                                            null,
                                            item.getAsso_address_line(),
                                            null,
                                            null,
                                            null,
                                            null,
                                            null
                                    ),
                                    new ArrayList<AdmPreinscripcionDto.AdmPreinscripcionPhoneDto>
                                            (Arrays.asList(new AdmPreinscripcionDto.AdmPreinscripcionPhoneDto(item.getAsso_phone()))),
                                    item.getAssociate_name_complete(),
                                    item.getAssociate_membership_number().toString(),
                                    item.getAssociate_date_created(),
                                    new AdmPreinscripcionDto.AdmPreinscripcionTypologyDto(
                                            item.getLinguistic_community_id(),
                                            item.getLinguistic_community_desc()
                                    )
                            ),
                            null,
                            null,
                            null,
                            new AdmPreinscripcionDto.AdmPreinscripcionOrganizationDto(
                                    item.getOrganization_key(),
                                    item.getOrganization_name(),
                                    item.getOrg_entry_contribution(),
                                    item.getOrganization_entry_fee()
                            ),
                            //extraordinary contribution
                            null


                    );
                    return admPreinscripcionDto;
                }
        ).collect(Collectors.toList());

    }

    //Returns query for string that contains query for all persons
    public StringBuilder getOriginalQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "     person.person_key,\n" +
                "     person.phone_account_id,\n" +
                "     person.document_account_id,\n" +
                "     person.address_account_id,\n" +
                "     person.beneficiary_account_id,\n" +
                "     person.first_name,\n" +
                "     person.middle_name,\n" +
                "     person.last_name,\n" +
                "     person.partner_name,\n" +
                "     person.married_name,\n" +
                "     person.birthday,\n" +
                "     person.email,\n" +
                "     typo_marital_status.typology_id        marital_status_id,\n" +
                "     typo_marital_status.description        marital_status_desc,\n");

        query.append(
                "     typo_profession.typology_id                       profession_id,\n" +
                        "     typo_profession.description                       profession_desc,\n" +
                        "     person.cui,\n" +
                        "     typo_document_type.typology_id         document_type_id,\n" +
                        "     typo_document_type.description         document_type_desc,\n" +
                        "     typo_document_order.typology_id        document_order_id,\n" +
                        "     typo_document_order.description        document_order_desc,\n" +
                        "     person.order_number,\n" +
                        "     person.nit,\n" +
                        "     typo_country_birth.typology_id             country_birth_id,\n" +
                        "     typo_country_birth.description             country_birth_desc,\n" +
                        "     typo_state_birth.typology_id               state_birth_id,\n" +
                        "     typo_state_birth.description               state_birth_desc,\n" +
                        "     typo_city_birth.typology_id                city_birth_id,\n" +
                        "     typo_city_birth.description                city_birth_desc,\n" +
                        "     typo_immigration_condition.typology_id     immigration_condition_id,\n" +
                        "     typo_immigration_condition.description     immigration_condition_desc,\n" +
                        "     typo_genre.typology_id                     genre_id,\n" +
                        "     typo_genre.description                     genre_desc,\n" +
                        "     person.passport,\n" +
                        "     person.own_account,\n" +
                        "     person.own_account_description,\n" +
                        "     typo_mayan_people.typology_id              mayan_people_id,\n" +
                        "     typo_mayan_people.description              mayan_people_desc,\n" +
                        "     typo_role.typology_id                      role_id,\n" +
                        "     typo_role.description                      role_desc,\n" +
                        "     typo_status.typology_id                    status_id,\n" +
                        "     typo_status.description                    status_desc,\n" +
                        "    createdByPerson.email                             created_by_email,\n" +
                        "    createdByPerson.person_key                        created_by_person_key,\n" +
                        "     person.date_created,\n" +
                        "     person.is_partner,\n" +
                        "     person.is_beneficiary,\n" +
                        "     person.membership_number,\n" +
                        "     person.name_complete,\n" +
                        "     person.is_associate,\n" +
                        "     typo_linguistic_community.typology_id           linguistic_community_id,\n" +
                        "     typo_linguistic_community.description           linguistic_community_desc\n" +
                        "FROM adm_person person\n" +
                        "INNER JOIN adm_typology typo_marital_status ON typo_marital_status.typology_id = person.marital_status\n");

        query.append(
                "INNER JOIN adm_typology typo_document_type ON typo_document_type.typology_id = person.document_type\n" +
                        "INNER JOIN adm_typology typo_document_order ON typo_document_order.typology_id = person.document_order\n" +
                        "INNER JOIN adm_typology typo_country_birth ON typo_country_birth.typology_id = person.country_of_birth\n" +
                        "INNER JOIN adm_typology typo_state_birth ON typo_state_birth.typology_id = person.state_of_birth\n" +
                        "INNER JOIN adm_typology typo_city_birth ON typo_city_birth.typology_id = person.city_of_birth\n" +
                        "INNER JOIN adm_typology typo_immigration_condition ON typo_immigration_condition.typology_id = person.immigration_condition\n" +
                        "INNER JOIN adm_typology typo_genre ON typo_genre.typology_id = person.genre\n" +
                        "INNER JOIN adm_typology typo_mayan_people ON typo_mayan_people.typology_id = person.mayan_people\n" +
                        "INNER JOIN adm_typology typo_role ON typo_role.typology_id = person.role\n" +
                        "INNER JOIN adm_typology typo_status ON typo_status.typology_id = person.status\n" +
                        "INNER JOIN adm_user createdBy ON person.created_by = createdBy.user_id\n" +
                        "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                        "INNER JOIN adm_typology typo_profession ON typo_profession.typology_id = person.profession_id\n" +
                        "INNER JOIN adm_typology typo_linguistic_community ON typo_linguistic_community.typology_id = person.linguistic_community_id\n");

        return query;
    }



    /* GLOBAL SEARCH METHODS */

    public AdmGlobalSearchDto globalSearch(String inputSearch) {
        StringBuilder query = getOriginalQuery();
        query.append("\nWHERE 1 = 1");

        Boolean filter = false;
        Boolean person = false;

        if (StringMatcher.isDpi(inputSearch)) {
            query.append("\nAND person.cui = ").append(inputSearch);
            filter = true;
            person = true;
        } else if (StringMatcher.isName(inputSearch) && (inputSearch.trim().length() > 0)) {
            String[] tokens = inputSearch.split(" ");
            query.append("\nAND  (1 = 1  ");
            for (String token : tokens) {
                if (!token.equals("")) {
                    query.append("AND ( person.first_name ILIKE '%").append(token.toUpperCase().trim()).append("%' \n");
                    query.append("OR  person.middle_name ILIKE '%").append(token.toUpperCase().trim()).append("%' \n");
                    query.append("OR  person.last_name ILIKE '%").append(token.toUpperCase().trim()).append("%' \n");
                    query.append("OR  person.married_name ILIKE '%").append(token.toUpperCase().trim()).append("%' \n");
                    query.append("OR  person.partner_name ILIKE '%").append(token.toUpperCase().trim()).append("%' \n");
                    query.append(")  \n ");
                }
            }
            query.append("OR  person.name_complete ILIKE '%").append(inputSearch.toUpperCase()).append("%' \n");

            query.append(")");

            filter = true;
            person = true;

        }
        if (StringMatcher.isNit(inputSearch)) {
            if (!filter) {
                query.append("\nAND person.nit = '").append(inputSearch).append("'").append("\n");
            } else {
                query.append("\nOR person.nit = '").append(inputSearch).append("'").append("\n");
            }

            filter = true;
            person = true;
        }
        if (StringMatcher.isAssociateNumber(inputSearch)) {
            Long membershipNumber = CsnFunctions.removeLeadingZeroes(inputSearch);
            if (!filter) {
                query.append("\nAND person.membership_number = ").append(inputSearch);
            } else {
                query.append("\nOR person.membership_number = ").append(inputSearch);
            }

            filter = true;
            person = true;
        }

        query.append("\nORDER BY person.date_created DESC \n");

        if (!filter) {
            query.append("\nLIMIT 0");
        } else {
            query.append("\nLIMIT 100 \n ");
        }

        String searchType = person ? "associate" : "to do";
        Stream<AdmGlobalSearchListDto> q = em.createNativeQuery(query.toString(), "admGlobalSearchPersonDto").getResultStream();
        List<AdmGlobalSearchDto.AdmGlobalSearchRecord> records = q.map(item -> {
            AdmGlobalSearchDto.AdmGlobalSearchRecord recs =
                    new AdmGlobalSearchDto.AdmGlobalSearchRecord(
                            new AdmPersonDto(
                                    item.getPersonKey(),
                                    item.getFirstName(),
                                    item.getMiddleName(),
                                    item.getLastName(),
                                    item.getPartnerName(),
                                    item.getMarriedName(),
                                    item.getEmail(),
                                    item.getCui(),
                                    item.getNit(),
                                    new AdmTypologyDto(
                                            item.getStatusId(),
                                            item.getStatusDesc()
                                    ),
                                    item.getDateCreated(),
                                    CsnFunctions.getAccountNumberOrder(item.getMembershipNumber()),
                                    item.getNameComplete(),
                                    new AdmTypologyDto(
                                            item.getLinguisticCommunityId(),
                                            item.getLinguisticCommunityDesc()
                                    )
                            )
                    );
            return recs;
        }).collect(Collectors.toList());

        return new AdmGlobalSearchDto(searchType, records);
        /*
        return q.map(item -> {
                    AdmGlobalSearchDto admGlobalSearchDto = new AdmGlobalSearchDto(
                            new AdmPersonDto(
                                    item.getPersonKey(),
                                    new AdmPhoneAccountDto(
                                            item.getPhoneAccountId()
                                    ),
                                    new AdmDocumentAccountDto(
                                            item.getDocumentAccountId()
                                    ),
                                    new AdmAddressAccountDto(
                                            item.getAddressAccountId()
                                    ),
                                    new AdmBeneficiaryAccountDto(
                                            item.getBeneficiaryAccountId()
                                    ),
                                    item.getFirstName(),
                                    item.getMiddleName(),
                                    item.getLastName(),
                                    item.getPartnerName(),
                                    item.getMarriedName(),
                                    item.getBirthday(),
                                    item.getEmail(),
                                    //marital status
                                    new AdmTypologyDto(
                                            item.getMaritalStatusId(),
                                            item.getMaritalStatusDesc()
                                    ),
                                    //profession
                                    new AdmTypologyDto(
                                            item.getProfessionId(),
                                            item.getProfessionDesc()
                                    ),
                                    item.getCui(),
                                    //document type
                                    new AdmTypologyDto(
                                            item.getDocumentTypeId(),
                                            item.getDocumentTypeDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getDocumentOrderId(),
                                            item.getDocumentOrderDesc()
                                    ),
                                    item.getOrderNumber(),
                                    item.getNit(),
                                    new AdmTypologyDto(
                                            item.getCountryOfBirthId(),
                                            item.getCountryOfBirthDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getStateOfBirthId(),
                                            item.getStateOfBirthDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getCityOfBirthId(),
                                            item.getCountryOfBirthDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getImmigrationConditionId(),
                                            item.getImmigrationConditionDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getGenreId(),
                                            item.getGenreDesc()
                                    ),
                                    item.getPassport(),
                                    item.getOwnAccount(),
                                    item.getOwnAccountDescription(),
                                    new AdmTypologyDto(
                                            item.getMayanPeopleId(),
                                            item.getMayanPeopleDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getRoleId(),
                                            item.getRoleDesc()
                                    ),
                                    new AdmTypologyDto(
                                            item.getStatusId(),
                                            item.getStatusDesc()
                                    ),
                                    new AdmUserInfoDto(
                                            new AdmUserInfoDto.AdmPersonUserInfoDto(
                                                    item.getCreatedByPersonKey(),
                                                    item.getCreatedByEmail()
                                            )
                                    ),
                                    item.getDateCreated(),
                                    item.getIsPartner(),
                                    item.getIsBeneficiary(),
                                    item.getMembershipNumber(),
                                    item.getNameComplete(),
                                    item.getIsAssociate()
                            )
                    );
                    return admGlobalSearchDto;
                }
        ).collect(Collectors.toList());*/
    }

    public Long generateMembershipNumber() {
        String query = "SELECT MAX(membership_number) + 1  AS membership_number FROM adm_person";

        Query q = em.createNativeQuery(query);
        List resultados = q.getResultList();

        if (resultados.size() > 0) {
            return Long.parseLong(resultados.get(0).toString());
        }
        return null;
    }

}
