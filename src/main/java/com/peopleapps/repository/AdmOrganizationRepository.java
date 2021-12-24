package com.peopleapps.repository;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.inputs.agency.AdmAgencyDto;
import com.peopleapps.dto.inputs.agency.AdmAgencyUserControlDto;
import com.peopleapps.dto.inputs.agency.user.AdmAgencyResponsibleListDto;
import com.peopleapps.dto.inputs.associations.AdmAssociationsDto;
import com.peopleapps.dto.organization.AdmAgencyListDto;
import com.peopleapps.dto.organization.AdmAssociationsListDto;
import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmOrganization.class)
public abstract class AdmOrganizationRepository extends AbstractEntityRepository<AdmOrganization, Long>
        implements CriteriaSupport<AdmOrganization> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;


    public List<ResponseJson> checkModel(AdmOrganization admOrganization) {

        List<ResponseJson> responseJsons = new ArrayList<>();
        try {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<AdmOrganization>> violations = validator.validate(admOrganization);

            return violations.stream().map(item -> {

                logger.info(item.getMessage());

                ResponseJson res = new ResponseJson("FAIL",
                        item.getPropertyPath().toString(),
                        0L,
                        item.getMessage(),
                        "");
                return res;

            }).collect(Collectors.toList());


        } catch (Exception ex) {

            logger.error("ERRROR=================CHECK" + ex.toString());
        }
        return responseJsons;
    }


    public List<AdmAssociationsDto> getAllAssociations(
            UUID association_key,
            Long status,
            Long state,
            Long city,
            Boolean desc
    ) {

        StringBuilder query = getBaseAssociationQuery();

        query.append("WHERE 1 = 1\n");
        query.append("\nAND association.is_association = TRUE ");

        if (association_key != null) {
            query.append(" AND association.organization_key = '").append(association_key).append("'");
        }

        if (status != null && status > 0) {
            query.append(" AND adm_status.typology_id = ").append(status);
        }

        if (city != null && city > 0) {
            query.append(" AND address_association.typology_id  = ").append(city);
        }

        if (state != null && state > 0) {
            query.append(" AND address_association.typology_id  = ").append(state);
        }
        if (desc != null && desc) {
            query.append("\nORDER BY association.organization_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY association.organization_id ASC");
        }

        return this.getAssociationList(query);
    }

    public List<AdmAgencyDto> getAllAgencies(
            UUID agency_organization_key,
            UUID parent_organization_key,
            Long status,
            Long sector,
            Long category,
            Long state,
            Long city,
            Boolean desc
    ) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("SELECT agency.organization_id,\n" +
                "       agency.organization_name,\n" +
                "       agency.organization_commercial,\n" +
                "       agency.address_account_id,\n" +
                "       agency.phone_account_id,\n" +
                "       agency.document_account_id,\n" +
                "       sector_typo.typology_id            sector_id,\n" +
                "       sector_typo.description            sector_description,\n" +
                "       cat_typo.typology_id               category_id,\n" +
                "       cat_typo.description               category_description,\n" +
                "       agency.date_created,\n" +
                "       agency.organization_key,\n" +
                "       agency.tax_code,\n" +
                "       agency.is_agency,\n" +
                "       agency.is_society,\n" +
                "       agency.is_organization,\n" +
                "       agency.entry_contribution,\n" +
                "       agency.entry_fee,\n" +
                "       adm_status.typology_id             status_id,\n" +
                "       adm_status.description             status_desc,\n" +
                "       parent.organization_id             parent_organization_id,\n" +
                "       parent.organization_name           parent_organization_name,\n" +
                "       parent.organization_key            parent_organization_key,\n" +
                "       created_user.user_id               created_user_id,\n" +
                "       created_person.person_id           created_person_id,\n" +
                "       created_person.first_name          created_person_first_name,\n" +
                "       created_person.last_name           created_person_last_name,\n" +
                "       created_person.person_key          created_person_key,\n" +
                "       phone_agency.phone_id              phone_id,\n" +
                "       phone_agency.phone                 phone,\n" +
                "       phone_agency_type.typology_id      phone_agency_type_id,\n" +
                "       phone_agency_type.description      phone_agency_type_desc,\n" +
                "       address_agency.address_id          address_id,\n" +
                "       address_agency.address_line        address,\n" +
                "       address_agency_type.typology_id    address_agency_type_id,\n" +
                "       address_agency_type.description    address_agency_type_desc,\n" +
                "       address_agency_country.typology_id address_agency_country_id,\n" +
                "       address_agency_country.description address_agency_country_desc,\n" +
                "       address_agency_state.typology_id   address_agency_state_id,\n" +
                "       address_agency_state.description   address_agency_state_desc,\n" +
                "       address_agency_city.typology_id    address_agency_city_id,\n" +
                "       address_agency_city.description    address_agency_city_desc,\n" +
                "       address_agency_zone.typology_id    address_agency_zone_id,\n" +
                "       address_agency_zone.description    address_agency_zone_desc,\n" +
                "       agency.internal_code\n" +
                "FROM adm_organization agency\n" +
                "         INNER JOIN adm_typology sector_typo ON agency.sector = sector_typo.typology_id\n" +
                "         INNER JOIN adm_typology cat_typo ON agency.category = cat_typo.typology_id\n" +
                "         INNER JOIN adm_typology adm_status ON agency.status = adm_status.typology_id\n" +
                "         INNER JOIN adm_sub_organization sub_organization\n" +
                "                    on agency.organization_id = sub_organization.organization_child\n" +
                "         INNER JOIN adm_organization parent on sub_organization.organization_id = parent.organization_id\n" +
                "         INNER JOIN adm_user created_user on created_user.user_id = sub_organization.created_by\n" +
                "         INNER JOIN adm_person created_person on created_user.person_id = created_person.person_id\n" +
                "         LEFT JOIN adm_address address_agency on agency.address_account_id = address_agency.address_account_id\n" +
                "         LEFT JOIN adm_phone phone_agency on agency.phone_account_id = phone_agency.phone_account_id\n" +
                "         LEFT JOIN adm_typology phone_agency_type on phone_agency_type.typology_id = phone_agency.type\n" +
                "         LEFT JOIN adm_typology address_agency_type on address_agency_type.typology_id = address_agency.type\n" +
                "         LEFT JOIN adm_typology address_agency_country on address_agency_country.typology_id = address_agency.country\n" +
                "         LEFT JOIN adm_typology address_agency_state on address_agency_state.typology_id = address_agency.state\n" +
                "         LEFT JOIN adm_typology address_agency_city on address_agency_city.typology_id = address_agency.city\n" +
                "         LEFT JOIN adm_typology address_agency_zone on address_agency_zone.typology_id = address_agency.zone\n" +
                "WHERE 1 = 1\n" +
                "  and agency.is_agency = true\n" +
                "  AND parent.organization_key = '3438d2ba-a56a-484c-bc2e-ea5b5dfcd488'");

        if (agency_organization_key != null) {
            query.append(" AND agency.organization_key = '").append(agency_organization_key).append("'");
        }

        if (parent_organization_key != null) {
            query.append(" AND parent.organization_key = '").append(parent_organization_key).append("'");
        }

        if (status != null && status > 0) {
            query.append(" AND adm_status.typology_id = ").append(status);
        }

        if (sector != null && sector > 0) {
            query.append(" AND sector_typo.typology_id  = ").append(sector);
        }

        if (category != null && category > 0) {
            query.append(" AND cat_typo.typology_id  = ").append(category);
        }

        if (city != null && city > 0) {
            query.append(" AND address_agency_city.typology_id  = ").append(city);
        }

        if (state != null && state > 0) {
            query.append(" AND address_agency_state.typology_id  = ").append(state);
        }
        if (desc != null && desc) {
            query.append("\nORDER BY organization_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY organization_id ASC");
        }

        Stream<AdmAgencyListDto> q = em.createNativeQuery(query.toString(), "admAgencyListDto").getResultStream();
        return q.map(item -> {
            AdmAgencyDto agency = new AdmAgencyDto(
                    item.organization_key,
                    item.organization_name,
                    item.entry_contribution,
                    item.entry_fee,
                    new AdmAgencyDto.AdmAgencyPhoneDto(
                            item.phone_id,
                            item.phone,
                            new AdmAgencyDto.AdmAgencyTypologyDto(
                                    item.phone_agency_type_id,
                                    item.phone_agency_type_desc
                            )
                    ),
                    new AdmAgencyDto.AdmAgencyAddressDto(
                            item.address_id,
                            item.address,
                            new AdmAgencyDto.AdmAgencyTypologyDto(
                                    item.address_agency_state_id,
                                    item.address_agency_state_desc
                            ),
                            new AdmAgencyDto.AdmAgencyTypologyDto(
                                    item.address_agency_city_id,
                                    item.address_agency_city_desc
                            ),
                            new AdmAgencyDto.AdmAgencyTypologyDto(
                                    item.address_agency_zone_id,
                                    item.address_agency_zone_desc
                            ),
                            new AdmAgencyDto.AdmAgencyTypologyDto(
                                    item.address_agency_type_id,
                                    item.address_agency_type_desc
                            )
                    ),

                    new AdmAgencyDto.AdmAgencyCreatedPersonDto(
                            item.created_person_key
                    ),
                    new AdmAgencyDto.AdmAgencyTypologyDto(
                            item.status_id,
                            item.status_desc
                    ),
                    item.internal_code
            );
            return agency;
        }).collect(Collectors.toList());
    }

    public AdmOrganization getByKey(UUID key) {
        Criteria<AdmOrganization, AdmOrganization> query = criteria();
        query.eq(AdmOrganization_.organizationKey, key);
        return query.getAnyResult();
    }

    public void saveEdit(AdmOrganization admOrganization) {
        AdmOrganization currentOrganization = this.findBy(admOrganization.getOrganizationId());


        if (admOrganization.getOrganizationName() != null) {
            currentOrganization.setOrganizationName(admOrganization.getOrganizationName());
        }
        if (admOrganization.getOrganizationCommercial() != null) {
            currentOrganization.setOrganizationCommercial(admOrganization.getOrganizationCommercial());
        }

        if (admOrganization.getSector() != null) {
            currentOrganization.setSector(admOrganization.getSector());
        }
        if (admOrganization.getCategory() != null) {
            currentOrganization.setCategory(admOrganization.getCategory());
        }
        if (admOrganization.getTaxCode() != null) {
            currentOrganization.setTaxCode(admOrganization.getTaxCode());
        }
        if (admOrganization.getIsAgency() != null) {
            currentOrganization.setIsAgency(admOrganization.getIsAgency());
        }
        if (admOrganization.getIsSociety() != null) {
            currentOrganization.setIsSociety(admOrganization.getIsSociety());
        }

        if (admOrganization.getIsOrganization() != null) {
            currentOrganization.setIsSociety(admOrganization.getIsOrganization());
        }

        if (admOrganization.getEntryContribution() != null) {
            currentOrganization.setEntryContribution(admOrganization.getEntryContribution());
        }

        if (admOrganization.getEntryFee() != null) {
            currentOrganization.setEntryFee(admOrganization.getEntryFee());
        }

        if (admOrganization.getStatus() != null) {
            currentOrganization.setStatus(admOrganization.getStatus());
        }

        this.save(currentOrganization);
    }

    public List<AdmAgencyUserControlDto> getAllAgenciesAndResponsibles(
            UUID agency_organization_key,
            Long departamento,
            Long municipio,
            Long status,
            Boolean desc)
            throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "     agency.organization_key                   organization_key,\n" +
                "     agency.organization_name                  organization_name,\n" +
                "     agencyStatusTypo.typology_id              agency_status_id,\n" +
                "     agencyStatusTypo.description              agency_status_desc,\n" +
                "     person.person_key                         person_key,\n" +
                "     person.name_complete                      person_name_complete,\n" +
                "     orgResp.is_responsable                    organization_responsible_is_responsible,\n" +
                "     orgRespStatusTypo.typology_id             organization_responsible_status_id,\n" +
                "     orgRespStatusTypo.description             organization_responsible_status_desc,\n" +
                "     personUser.date_created                   user_date_created,\n" +
                "     userRoleTypo.typology_id                  user_role_id,\n" +
                "     userRoleTypo.description                  user_role_desc,\n" +
                "     person.email                              person_email,\n" +
                "     userStatusTypo.typology_id                user_status_id,\n" +
                "     userStatusTypo.description                user_status_desc\n" +
                " FROM adm_organization agency\n" +
                " INNER JOIN adm_typology agencyStatusTypo ON agency.status = agencyStatusTypo.typology_id\n" +
                " INNER JOIN adm_organization_responsible orgResp ON agency.organization_id = orgResp.organization_id\n" +
                " INNER JOIN adm_person person ON person.person_id = orgResp.person_id\n" +
                " INNER JOIN adm_address_account agency_address_account ON agency.address_account_id = agency_address_account.address_account_id\n" +
                " INNER JOIN adm_address agency_address ON agency_address_account.address_account_id = agency_address.address_account_id\n" +
                " INNER JOIN adm_typology orgRespStatusTypo ON orgResp.status = orgRespStatusTypo.typology_id\n" +
                " INNER JOIN adm_user personUser ON orgResp.person_id = personUser.person_id\n" +
                " INNER JOIN adm_typology userRoleTypo ON personUser.role = userRoleTypo.typology_id\n" +
                " INNER JOIN adm_typology userStatusTypo ON personUser.status = userStatusTypo.typology_id\n" +
                " WHERE 1 = 1\n" +
                " AND (agency.organization_id = orgResp.organization_id AND person.person_id = orgResp.person_id)");

        if (agency_organization_key == null) {
            query.append(" AND orgResp.is_responsable = TRUE");
        }
        if (agency_organization_key != null) {
            query.append(" AND agency.organization_key = '").append(agency_organization_key).append("'");
        }
        if (departamento != null && departamento > 0) {
            query.append(" AND agency_address.state = ").append(departamento);
        }
        if (municipio != null && municipio > 0) {
            query.append(" AND agency_address.city = ").append(municipio);
        }
        if (status != null && status > 0) {
            query.append(" AND agency.status = ").append(status);
        }
        if (desc != null && desc) {
            query.append("\nORDER BY agency.organization_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY agency.organization_id ASC");
        }

        //TODO FIX ERROR WHEN TRYING TO CAST
        Stream<AdmAgencyResponsibleListDto> q = em.createNativeQuery(query.toString(), "admAgencyResponsiblesDto").getResultStream();
        return q.map(item -> {
                    AdmAgencyUserControlDto result = new AdmAgencyUserControlDto(
                            new AdmAgencyUserControlDto.AdmAgencyUserControlCreatedUserDto(
                                    item.getPerson_key(),
                                    item.getPerson_name_complete(),
                                    item.getPerson_email(),
                                    item.getUser_date_created(),
                                    new AdmAgencyUserControlDto.AdmAgencyUserControlTypologyDto(
                                            item.getUser_role_id(),
                                            item.getUser_role_desc()
                                    ),
                                    new AdmAgencyUserControlDto.AdmAgencyUserControlTypologyDto(
                                            item.getUser_status_id(),
                                            item.getUser_status_desc()
                                    )

                            ),
                            new AdmAgencyUserControlDto.AdmAgencyUserControlOrganizationDto(
                                    item.getOrganization_key(),
                                    item.getOrganization_name(),
                                    new AdmAgencyUserControlDto.AdmAgencyUserControlTypologyDto(
                                            item.getAgency_status_id(),
                                            item.getAgency_status_desc()
                                    )
                            ),
                            item.getOrganization_responsible_is_responsible(),
                            new AdmAgencyUserControlDto.AdmAgencyUserControlTypologyDto(
                                    item.getOrganization_responsible_status_id(),
                                    item.getOrganization_responsible_status_desc()
                            )
                    );
                    return result;
                }
        ).collect(Collectors.toList());
    }

    private StringBuilder getBaseAssociationQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT " +
                "       association.organization_id,\n" +
                "       association.organization_name,\n" +
                "       association.organization_commercial,\n" +
                "       association.address_account_id,\n" +
                "       association.phone_account_id,\n" +
                "       association.document_account_id,\n" +
                "       sector_typo.typology_id                 sector_id,\n" +
                "       sector_typo.description                 sector_description,\n" +
                "       cat_typo.typology_id                    category_id,\n" +
                "       cat_typo.description                    category_description,\n" +
                "       association.date_created,\n" +
                "       association.organization_key,\n" +
                "       association.tax_code,\n" +
                "       association.is_agency,\n" +
                "       association.is_society,\n" +
                "       association.is_organization,\n" +
                "       association.entry_contribution,\n" +
                "       association.entry_fee,\n" +
                "       association.interest_rate,\n" +
                "       association.is_association,\n" +
                "       adm_status.typology_id                  status_id,\n" +
                "       adm_status.description                  status_desc,\n" +

                //TODO check for organization created by that is actually not on the table organization
               "       created_user.user_id                    created_user_id,\n" +
                "       created_person.person_id                created_person_id,\n" +
                "       created_person.first_name               created_person_first_name,\n" +
                "       created_person.last_name                created_person_last_name,\n" +
                "       created_person.person_key               created_person_key,\n" +
                "       phone_association.phone_id              phone_id,\n" +
                "       phone_association.phone                 phone,\n" +
                "       phone_association_type.typology_id      phone_association_type_id,\n" +
                "       phone_association_type.description      phone_association_type_desc,\n" +
                "       address_association.address_id          address_id,\n" +
                "       address_association.address_line        address,\n" +
                "       address_association_type.typology_id    address_association_type_id,\n" +
                "       address_association_type.description    address_association_type_desc,\n" +
                "       address_association_country.typology_id address_association_country_id,\n" +
                "       address_association_country.description address_association_country_desc,\n" +
                "       address_association_state.typology_id   address_association_state_id,\n" +
                "       address_association_state.description   address_association_state_desc,\n" +
                "       address_association_city.typology_id    address_association_city_id,\n" +
                "       address_association_city.description    address_association_city_desc,\n" +
                "       address_association_zone.typology_id    address_association_zone_id,\n" +
                "       address_association_zone.description    address_association_zone_desc,\n" +
                "       adm_contact.name_complete               contact_name_complete, \n" +
                "       adm_contact.person_key                  contact_person_key,\n" +
                //end of todo

                "       association.internal_code\n" +
                "FROM adm_organization association\n" +
                "         INNER JOIN adm_typology sector_typo                 ON association.sector                      = sector_typo.typology_id\n" +
                "         INNER JOIN adm_typology cat_typo                    ON association.category                    = cat_typo.typology_id\n" +
                "         INNER JOIN adm_typology adm_status                  ON association.status                      = adm_status.typology_id\n" +

                //todo check joins for future use
                "         INNER JOIN adm_organization_responsible responsible ON association.organization_id             = responsible.organization_id\n" +
                "         INNER JOIN adm_person adm_contact                   ON adm_contact.person_id                   = responsible.person_id\n" +
                "         INNER JOIN adm_user created_user                    ON created_user.user_id                    = adm_contact.created_by\n" +
                "         INNER JOIN adm_person created_person                ON created_user.person_id                  = created_person.person_id\n" +
                "         INNER JOIN adm_address address_association          ON association.address_account_id          = address_association.address_account_id\n" +
                "         INNER JOIN adm_phone phone_association              ON association.phone_account_id            = phone_association.phone_account_id\n" +
                "         INNER JOIN adm_typology phone_association_type      ON phone_association_type.typology_id      = phone_association.type\n" +
                "         INNER JOIN adm_typology address_association_type    ON address_association_type.typology_id    = address_association.type\n" +
                "         INNER JOIN adm_typology address_association_country ON address_association_country.typology_id = address_association.country\n" +
                "         INNER JOIN adm_typology address_association_state   ON address_association_state.typology_id   = address_association.state\n" +
                "         INNER JOIN adm_typology address_association_city    ON address_association_city.typology_id    = address_association.city\n" +
                "         INNER JOIN adm_typology address_association_zone    ON address_association_zone.typology_id    = address_association.zone\n");
        return query;

    }

    private List<AdmAssociationsDto> getAssociationList(StringBuilder query) {
        Stream<AdmAssociationsListDto> q = em.createNativeQuery(query.toString(), "admAssociationListDto").getResultStream();
        return q.map(item -> {
            AdmAssociationsDto association = new AdmAssociationsDto(
                    new AdmAssociationsDto.AdmAssociationOrganizationDto(
                            item.organization_key,
                            item.organization_name,
                            item.interest_rate
                    ),
                    new AdmAssociationsDto.AdmAssociationPersonDto(
                            item.contact_person_key,
                            item.contact_name_complete
                    ),
                    new AdmAssociationsDto.AdmAssociationsPhoneDto(
                            item.phone_id,
                            item.phone,
                            new AdmAssociationsDto.AdmAssociationsTypologyDto(
                                    item.phone_association_type_id,
                                    item.phone_association_type_desc
                            )
                    ),

                    new AdmAssociationsDto.AdmAssociationsAddressDto(
                            item.address_id,
                            item.address,
                            new AdmAssociationsDto.AdmAssociationsTypologyDto(
                                    item.address_association_state_id,
                                    item.address_association_state_desc
                            ),
                            new AdmAssociationsDto.AdmAssociationsTypologyDto(
                                    item.address_association_city_id,
                                    item.address_association_city_desc
                            ),
                            new AdmAssociationsDto.AdmAssociationsTypologyDto(
                                    item.address_association_zone_id,
                                    item.address_association_zone_desc
                            ),
                            new AdmAssociationsDto.AdmAssociationsTypologyDto(
                                    item.address_association_type_id,
                                    item.address_association_type_desc
                            )
                    ),

                    new AdmAssociationsDto.AdmAssociationUserPersonDto(
                            item.created_person_key
                    ),
                    new AdmAssociationsDto.AdmAssociationsTypologyDto(
                            item.status_id,
                            item.status_desc
                    ),
                    item.internal_code

            );
            return association;
        }).collect(Collectors.toList());
    }

    public List<AdmAssociationsDto> getAssociationByName(
            Boolean isAssociation,
            Boolean isSociety,
            Boolean isAgency,
            String organizationName,
            String organizationCommercial) {

        StringBuilder query = this.getBaseAssociationQuery();

        query.append("\nWHERE 1 = 1");
        if (isAssociation) {
            query.append("\nAND association.is_association = TRUE");
        }
        if (isSociety) {
            query.append("\nAND association.is_society = TRUE");
        }
        if (isAgency) {
            query.append("\nAND association.is_agency = TRUE");
        }
        if (!organizationName.equals("")) {
            query.append("\nAND association.organization_name ILIKE('%").append(organizationName).append("%')");
        }
        if (!organizationCommercial.equals("")) {
            query.append("\nAND association.organization_commercial ILIKE('%").append(organizationCommercial).append("%')");
        }
        query.append("\nORDER BY association.organization_id ASC");

        return this.getAssociationList(query);

    }
}
