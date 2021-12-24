package com.peopleapps.repository;

import com.peopleapps.dto.address.AdmAddressDto;
import com.peopleapps.dto.address.AdmAddressListDto;
import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmAddress.class)
public abstract class AdmAddressRepository extends AbstractEntityRepository<AdmAddress, Long>
        implements CriteriaSupport<AdmAddress> {

    @Inject
    EntityManager em;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    Logger logger;

    public List<AdmAddress> getAllAddresses(
            Long addressId, Long addressAccountId, Boolean desc
    ) {
        StringBuilder query = this.getOriginalQuery();
        query.append("WHERE 1 = 1 ");

        if (addressId != null && addressId > 0) {
            query.append("AND address.address_id = ").append(addressId);
        }
        if (addressAccountId != null && addressAccountId > 0) {
            query.append("AND address.address_account_id = ").append(addressAccountId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY address.address_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY address.address_id ASC");
        }
        return getAddressList(query);
    }

    public AdmAddress getByAddressAccount(Long addressAccountId) {

        Criteria<AdmAddress, AdmAddress> query = criteria();

        query.join(AdmAddress_.addressAccount,
                where(AdmAddressAccount.class)
                        .eq(AdmAddressAccount_.addressAccountId, addressAccountId));

        return query.getAnyResult();

    }

    public void saveEdit(AdmAddress admAddress) {
        AdmAddress currentAddress = this.findBy(admAddress.getAddressId());

        admAddress.setCreatedBy(currentAddress.getCreatedBy());
        admAddress.setDateCreated(currentAddress.getDateCreated());
        admAddress.setAddressAccount(currentAddress.getAddressAccount());
        admAddress.setDocumentAcount(currentAddress.getDocumentAcount());

        //TODO check what properties can be changed

        if (admAddress.getAddressLine() == null) {
            admAddress.setAddressLine(currentAddress.getAddressLine());
        }

        if (admAddress.getAddressLine2() == null) {
            admAddress.setAddressLine2(currentAddress.getAddressLine2());
        }

        if (admAddress.getCountry() == null) {
            admAddress.setCountry(currentAddress.getCountry());
        }

        if (admAddress.getState() == null) {
            admAddress.setState(currentAddress.getState());
        }
        if (admAddress.getCity() == null) {
            admAddress.setCity(currentAddress.getCity());
        }
        if (admAddress.getZone() == null) {
            admAddress.setZone(currentAddress.getZone());
        }
        if (admAddress.getStatus() == null) {
            admAddress.setStatus(currentAddress.getStatus());
        }
        if (admAddress.getType() == null) {
            admAddress.setType(currentAddress.getType());
        }
        if (admAddress.getLeader() == null) {
            admAddress.setLeader(currentAddress.getLeader());
        }
        if (admAddress.getNoFarm() == null) {
            admAddress.setNoFarm(currentAddress.getNoFarm());
        }
        if (admAddress.getNoFolder() == null) {
            admAddress.setNoFolder(currentAddress.getNoFolder());
        }
        if (admAddress.getExtension() == null) {
            admAddress.setExtension(currentAddress.getExtension());
        }
        if (admAddress.getNoPublic() == null) {
            admAddress.setNoPublic(currentAddress.getNoPublic());
        }

        if (admAddress.getVillage() == null) {
            admAddress.setVillage(currentAddress.getVillage());
        }
        if (admAddress.getNoBook() == null) {
            admAddress.setNoBook(currentAddress.getNoBook());
        }

        logger.info(admAddress.toString());

        admAddress = this.save(admAddress);
        this.resetLeader(admAddress);
    }

    //Used to get all addresses by associate key
    public List<AdmAddress> getAllAddressesByAssociate(UUID personKey, Long addressId,
                                                       Long addressStatus, Boolean desc) {
        StringBuilder query = this.getOriginalQuery();

        query.append("JOIN adm_address_account adAccount ON adAccount.address_account_id = address.address_account_id\n");
        query.append("JOIN adm_person associate ON associate.address_account_id = adAccount.address_account_id\n");
        query.append("WHERE 1 = 1 ");

        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        if (addressId != null && addressId > 0) {
            query.append("\nAND address.address_id = ").append(addressId);
        }

        if (addressStatus != null && addressStatus > 0) {
            query.append("\nAND address.status_id = ").append(addressStatus);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY address.address_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY address.address_id ASC");
        }
        return getAddressList(query);
    }


    //generic method to transform native query to list of addresses using a DTO
    private List<AdmAddress> getAddressList(StringBuilder query) {
        Stream<AdmAddressListDto> q = em.createNativeQuery(query.toString(), "admAddressListDto").getResultStream();
        return q.map(item -> {
            AdmAddress admAddress = new AdmAddress(
                    item.getAddressId(),
                    new AdmAddressAccount(
                            item.getAddressAccountId()
                    ),
                    item.getAddressLine(),
                    item.getAddressLine2(),
                    new AdmTypology(
                            item.getCountryId(),
                            item.getCountryDescription()
                    ),
                    new AdmTypology(
                            item.getStateId(),
                            item.getStateDescription()
                    ),
                    new AdmTypology(
                            item.getCityId(),
                            item.getCityDescription()
                    ),
                    new AdmTypology(
                            item.getZoneId(),
                            item.getZoneDescription()
                    ),
                    new AdmTypology(
                            item.getStatusId(),
                            item.getStatusDescription()
                    ),
                    new AdmTypology(
                            item.getTypeId(),
                            item.getTypeDescription()
                    ),
                    new AdmUser(
                            new AdmPerson(
                                    item.getCreatedByPersonKey(),
                                    item.getCreatedByEmail()
                            )
                    ),
                    item.getDateCreated(),
                    item.getLeader(),
                    item.getNoFarm(),
                    item.getNoFolder(),
                    item.getExtension(),
                    item.getNoPublic(),
                    new AdmDocumentAccount(
                            item.getDocumentAccountId()
                    ),
                    new AdmTypology(
                            item.getVillageId(),
                            item.getVillageDescription()
                    ),
                    item.getNoBook()
            );
            return admAddress;
        }).collect(Collectors.toList());
    }

    //returns original query, to avoid duplicating lines, every other method expands on this original query
    private StringBuilder getOriginalQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "     address.address_id,\n" +
                "     address.address_account_id,\n" +
                "     address.address_line,\n" +
                "     address.address_line_2,\n" +
                "     countryTypo.typology_id                    country_id,\n" +
                "     countryTypo.description                    country_description,\n" +
                "     stateTypo.typology_id                      state_id,\n" +
                "     stateTypo.description                      state_description,\n" +
                "     cityTypo.typology_id                       city_id,\n" +
                "     cityTypo.description                       city_description,\n" +
                "     zoneTypo.typology_id                       zone_id,\n" +
                "     zoneTypo.description                       zone_description,\n" +
                "     statusTypo.typology_id                     status_id,\n" +
                "     statusTypo.description                     status_description,\n" +
                "     typeTypo.typology_id                       type_id,\n" +
                "     typeTypo.description                       type_description,\n" +
                "     createdByPerson.person_key                 created_by_person_key,\n" +
                "     createdByPerson.email                      created_by_email,\n" +
                "     address.date_created,\n" +
                "     address.leader,\n" +
                "     address.no_farm,\n" +
                "     address.no_folder,\n" +
                "     address.extension,\n" +
                "     address.no_public,\n" +
                "     address.document_account_id,\n" +
                "     villageTypo.typology_id                       village_id,\n" +
                "     villageTypo.description                       village_description,\n" +
                "     address.no_book\n" +
                "FROM adm_address address\n" +
                "JOIN adm_typology countryTypo ON address.country = countryTypo.typology_id\n" +
                "JOIN adm_typology stateTypo ON address.state = stateTypo.typology_id\n" +
                "JOIN adm_typology cityTypo ON address.city = cityTypo.typology_id\n" +
                "JOIN adm_typology zoneTypo ON address.zone = zoneTypo.typology_id\n" +
                "JOIN adm_typology statusTypo ON address.status_id  = statusTypo.typology_id\n" +
                "JOIN adm_typology typeTypo ON address.type = typeTypo.typology_id\n" +
                "JOIN adm_user createdBy ON address.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                "JOIN adm_typology villageTypo ON address.village = villageTypo.typology_id\n");

        return query;
    }

    public List<AdmAddress> getAllBeneficiaryAddressesByAssociate(UUID personKey, Long addressId, Long addressStatus) {
        StringBuilder query = this.getOriginalQuery();

        query.append("\nJOIN adm_address_account adAccount ON adAccount.address_account_id = address.address_account_id");
        query.append("\nJOIN adm_person benePerson ON benePerson.address_account_id = adAccount.address_account_id");
        query.append("\nJOIN adm_beneficiary beneBeneficiary ON benePerson.person_id = beneBeneficiary.beneficiary");
        query.append("\nJOIN adm_beneficiary_account beneAccount ON beneBeneficiary.beneficiary_account_id = beneAccount.beneficiary_account_id");
        query.append("\nJOIN adm_person associate ON beneAccount.beneficiary_account_id = associate.beneficiary_account_id");
        query.append("\nWHERE 1 = 1");
        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        query.append("\nAND benePerson.is_beneficiary = ").append(Boolean.TRUE);

        if (addressId != null && addressId > 0) {
            query.append("\nAND address.address_id = ").append(addressId);
        }

        if (addressStatus != null && addressStatus > 0) {
            query.append("\nAND address.status_id = ").append(addressStatus);
        }

        return getAddressList(query);
    }

    public List<AdmAddress> getAllPartnerAddressesByAssociate(UUID personKey, Long addressId, Long addressStatus) {

        StringBuilder query = this.getOriginalQuery();

        query.append("\nJOIN adm_address_account adAccount ON adAccount.address_account_id = address.address_account_id");
        query.append("\nJOIN adm_person partnerPerson ON partnerPerson.address_account_id = adAccount.address_account_id");
        query.append("\nJOIN adm_partner partPartner ON partnerPerson.person_id = partPartner.person");
        query.append("\nJOIN adm_person associate ON partPartner.partner = associate.person_id");
        query.append("\nWHERE 1 = 1");
        query.append("\nAND associate.person_key = ").append("'").append(personKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        query.append("\nAND partnerPerson.is_partner = ").append(Boolean.TRUE);

        if (addressId != null && addressId > 0) {
            query.append("\nAND address.address_id = ").append(addressId);
        }

        if (addressStatus != null && addressStatus > 0) {
            query.append("\nAND address.status_id = ").append(addressStatus);
        }

        return getAddressList(query);
    }

    //return credits by associate using a DTO
    public List<AdmAddressDto> getAllAddressesByAssociateDto(UUID associateKey, Long addressId,
                                                             Long addressStatus, Boolean desc,
                                                             Long addressAccountId) {
        StringBuilder query = getOriginalQuery();
        query.append("JOIN adm_address_account adAccount ON adAccount.address_account_id = address.address_account_id\n");
        query.append("JOIN adm_person associate ON associate.address_account_id = adAccount.address_account_id\n");
        query.append("WHERE 1 = 1 ");

        query.append("\nAND associate.person_key = ").append("'").append(associateKey).append("'");
        query.append("\nAND associate.is_associate = ").append(Boolean.TRUE);
        if (addressId != null && addressId > 0) {
            query.append("\nAND address.address_id = ").append(addressId);
        }

        if (addressStatus != null && addressStatus > 0) {
            query.append("\nAND address.status_id = ").append(addressStatus);
        }

        //getting addresses by credit
        if (addressAccountId != null && addressAccountId > 0) {
            query.append("\nAND address.address_account_id = ").append(addressAccountId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY address.address_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY address.address_id ASC");
        }
        return getAddressListDto(query);
    }

    //generic method to transform native query to list of addresses using a DTO
    private List<AdmAddressDto> getAddressListDto(StringBuilder query) {
        Stream<AdmAddressListDto> q = em.createNativeQuery(query.toString(), "admAddressListDto").getResultStream();
        return q.map(item -> {
            AdmAddressDto admAddressDtoList = new AdmAddressDto(
                    item.getAddressId(),
                    new AdmAddressAccountDto(
                            item.getAddressAccountId()
                    ),
                    item.getAddressLine(),
                    item.getAddressLine2(),
                    new AdmTypologyDto(
                            item.getCountryId(),
                            item.getCountryDescription()
                    ),
                    new AdmTypologyDto(
                            item.getStateId(),
                            item.getStateDescription()
                    ),
                    new AdmTypologyDto(
                            item.getCityId(),
                            item.getCityDescription()
                    ),
                    new AdmTypologyDto(
                            item.getZoneId(),
                            item.getZoneDescription()
                    ),
                    new AdmTypologyDto(
                            item.getStatusId(),
                            item.getStatusDescription()
                    ),
                    new AdmTypologyDto(
                            item.getTypeId(),
                            item.getTypeDescription()
                    ),

                    new AdmUserInfoDto.AdmPersonUserInfoDto(
                            item.getCreatedByPersonKey(),
                            item.getCreatedByEmail()
                    ),
                    item.getDateCreated(),
                    item.getLeader(),
                    item.getNoFarm(),
                    item.getNoFolder(),
                    item.getExtension(),
                    item.getNoPublic(),
                    new AdmDocumentAccountDto(
                            item.getDocumentAccountId()
                    ),
                    new AdmTypologyDto(
                            item.getVillageId(),
                            item.getVillageDescription()
                    ),
                    item.getNoBook()
            );
            return admAddressDtoList;
        }).collect(Collectors.toList());
    }

    public List<AdmAddressDto> getAllAddressesByAddressAccountDto(
            UUID creditKey, Long statusId,
            Boolean desc, Long addressAccountId,
            Long addressId) {
        StringBuilder query = getOriginalQuery();

        query.append("JOIN adm_address_account adAccount ON adAccount.address_account_id = address.address_account_id\n");
        query.append("JOIN adm_credit credit ON credit.address_account_id = adAccount.address_account_id\n");
        query.append("WHERE 1 = 1 ");

        query.append("\nAND credit.credit_key = ").append("'").append(creditKey).append("'");

        if (addressId != null && addressId > 0) {
            query.append("\nAND address.address_id = ").append(addressId);
        }

        if (statusId != null && statusId > 0) {
            query.append("\nAND address.status_id = ").append(statusId);
        }

        //getting addresses by credit
        if (addressAccountId != null && addressAccountId > 0) {
            query.append("\nAND address.address_account_id = ").append(addressAccountId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY address.address_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY address.address_id ASC");
        }
        return getAddressListDto(query);

    }

    //method that checks if address' property isLeader is TRUE, if so, changes all other addresses isLeader to FALSE
    public void resetLeader(AdmAddress admAddress) {
        //Apply logic only if property is TRUE
        if (admAddress.getLeader()) {
            Criteria<AdmAddress, AdmAddress> query = criteria();
            query.join(AdmAddress_.addressAccount,
                    where(AdmAddressAccount.class)
                            .eq(AdmAddressAccount_.addressAccountId, admAddress.getAddressAccount().getAddressAccountId()));

            List<AdmAddress> admAddresses = query.getResultList();
            for (AdmAddress item : admAddresses) {
                if (!item.getAddressId().equals(admAddress.getAddressId())) {
                    item.setLeader(Boolean.FALSE);
                } else {
                    item.setLeader(Boolean.TRUE);
                }
                this.save(item);
            }
        }
    }

    //method that gets leader address from a given address account
    public AdmAddress getLeaderAddress(Long admAddressAccountId) {
        //Apply logic only if property is TRUE

        Criteria<AdmAddress, AdmAddress> query = criteria();
        query.join(AdmAddress_.addressAccount,
                where(AdmAddressAccount.class)
                        .eq(AdmAddressAccount_.addressAccountId, admAddressAccountId))
                .eq(AdmAddress_.leader, Boolean.TRUE);


        List<AdmAddress> admAddressList = query.getResultList();
        return (admAddressList.size() > 0) ? admAddressList.get(0) : null;
    }
}
