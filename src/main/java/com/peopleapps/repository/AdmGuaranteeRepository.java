package com.peopleapps.repository;

import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.guarantee.AdmGuaranteeListDto;
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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmGuarantee.class)
public abstract class AdmGuaranteeRepository extends AbstractEntityRepository<AdmGuarantee, Long>
        implements CriteriaSupport<AdmGuarantee> {

    @Inject
    Logger logger;

    @Inject
    EntityManager em;

    public Long getMax() {
        Criteria<AdmGuarantee, AdmGuarantee> query = criteria();
        query.eq(AdmGuarantee_.noYear, CsnFunctions.getYear());
        List<AdmGuarantee> list = query.getResultList();

        if (list.size() > 1)
            return list.stream().max(Comparator.comparing(AdmGuarantee::getNoReference)).get().getNoReference() + 1;

        return CsnConstants.DEFAULT_GUARANTEE_INITIAL;
    }

    public List<ResponseJson> checkModel(AdmGuarantee admGuarantee) {

        List<ResponseJson> responseJsons = new ArrayList<>();
        try {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<AdmGuarantee>> violations = validator.validate(admGuarantee);
            for (ConstraintViolation<AdmGuarantee> violation : violations) {
                responseJsons.add(new ResponseJson(
                        "FAIL",
                        violation.getPropertyPath().toString(),
                        0L,
                        violation.getMessage(),
                        ""));

            }
            return responseJsons;
        } catch (Exception ex) {
            logger.error("ERRROR=================CHECK" + ex.toString());
        }
        return responseJsons;
    }

    //method that returns base sql query
    private StringBuilder getBaseQuery() {
        StringBuilder query = new StringBuilder();
        query.append("select " +
                "       guarantee.guarantee_id         guarantee_id,\n" +
                "       guarantee.credit_id            credit_id,\n" +
                "       guarantee.no_year              no_year,\n" +
                "       guarantee.no_reference         no_reference,\n" +
                "       guarantee.name_farm            name_farm,\n" +
                "       guarantee.owner as             owner,\n" +
                "       address.address_id             address_id,\n" +
                "       address.address_line           address_line,\n" +
                "       address.no_public              no_public,\n" +
                "       address.no_farm                no_farm,\n" +
                "       address.no_folder              no_folder,\n" +
                "       address.extension              no_book,\n" +
                "       address.document_account_id    document_account_id,\n" +
                "       adm_typology_state.typology_id address_state_id,\n" +
                "       adm_typology_state.description address_state_desc,\n" +
                "       adm_typology_city.typology_id  address_city_id,\n" +
                "       adm_typology_city.description  address_city_desc,\n" +
                "       guarantee.address_account_id   address_account_id,\n" +
                "       guarantee.document_type        document_type_id,\n" +
                "       a_document_type.description    document_type_desc,\n" +
                "       guarantee.testimony            testimony,\n" +
                "       guarantee.rope_value           rope_value,\n" +
                "       guarantee.area_meters          area_meters,\n" +
                "       guarantee.msnm                 msnm,\n" +
                "       guarantee.topography           topography,\n" +
                "       guarantee.hydrography          hydrography,\n" +
                "       guarantee.soil_quality         soil_quality,\n" +
                "       guarantee.plan_cover           plan_cover,\n" +
                "       guarantee.cultivate_detail     cultivate_detail,\n" +
                "       guarantee.farm_neighbor        farm_neighbor,\n" +
                "       guarantee.risk_class_form      risk_class_form,\n" +
                "       guarantee.irrigation_extension irrigation_extension,\n" +
                "       guarantee.building_detail      building_detail,\n" +
                "       guarantee.annotation           annotation,\n" +
                "       guarantee.north_origin         north_origin,\n" +
                "       guarantee.south_origin         south_origin,\n" +
                "       guarantee.orient_origin        orient_origin,\n" +
                "       guarantee.west_origin          west_origin,\n" +
                "       guarantee.communication_routes communication_routes,\n" +
                "       guarantee.state                state,\n" +
                "       guarantee.city                 city,\n" +
                "       guarantee.to_city              to_city,\n" +
                "       guarantee.evaluator            evaluator,\n" +
                "       adm_evaluator.name_complete    evaluator_name,\n" +
                "       adm_evaluator.email            evaluator_email,\n" +
                "       adm_evaluator.person_key       evaluator_key,\n" +
                "       guarantee.date_created         date_created,\n" +
                "       guarantee.created_by           created_by,\n" +
                "       adm_person_creator.email       created_email,\n" +
                "       adm_person_creator.person_key  created_key,\n" +
                "       guarantee.status               status_id,\n" +
                "       a_status.description           status_desc,\n"+
                "       address.no_book                no_book,\n"+
                "       guarantee.ownership_rights     ownership_rights,\n"+
                "       guarantee.no_rope              no_rope,\n"+
                "       guarantee.no_hectares          no_hectares,\n"+
                "       guarantee.cost_per_square_meter cost_per_square_meter,\n"+
                "       guarantee.height_above_sea_level height_above_sea_level,\n"+
                "       guarantee.value_of_permanent_crops value_of_permanent_crops,\n"+
                "       guarantee.value_of_buildings   value_of_buildings,\n"+
                "       guarantee.annotation_2         annotation_2\n");
        return query;
    }

    //method that returns a List<AdmGuarantee> from a Stream
    private List<AdmGuarantee> getStreamResult(Stream<AdmGuaranteeListDto> q) {
        return q.map(item -> {

            AdmGuarantee itemGuarantee = new AdmGuarantee(
                    item.guarantee_id,
                    new AdmCredit(
                            item.credit_id
                    ),
                    item.no_year,
                    item.no_reference,
                    item.name_farm,
                    item.owner,
                    new AdmAddressAccount(
                            item.address_account_id
                    ),
                    new AdmTypology(
                            item.document_type_id,
                            item.document_type_desc
                    ),
                    item.testimony,
                    item.rope_value,
                    item.area_meters,
                    item.msnm,
                    item.topography,
                    item.hydrography,
                    item.soil_quality,
                    item.plan_cover,
                    item.cultivate_detail,
                    item.farm_neighbor,
                    item.risk_class_form,
                    item.irrigation_extension,
                    item.building_detail,
                    item.annotation,
                    item.north_origin,
                    item.south_origin,
                    item.orient_origin,
                    item.west_origin,
                    item.communication_routes,
                    item.state,
                    item.city,
                    item.to_city,
                    new AdmPerson(
                            item.evaluator_email,
                            item.evaluator_key,
                            item.evaluator_name
                    ),
                    item.date_created,
                    new AdmUser(
                            item.created_key
                    ),
                    new AdmTypology(
                            item.status_id,
                            item.status_desc
                    ),
                    new AdmAddress(
                            item.address_id,
                            new AdmAddressAccount(
                                    item.address_account_id
                            ),
                            item.address_line,
                            null,
                            null,
                            new AdmTypology(
                                    item.address_state_id,
                                    item.address_state_desc
                            ),
                            new AdmTypology(
                                    item.address_city_id,
                                    item.address_state_desc
                            ),
                            null,
                            null,
                            null,
                            null,
                            null,
                            Boolean.TRUE,
                            item.no_farm,
                            item.no_folder,
                            null,
                            item.no_public,
                            new AdmDocumentAccount(
                                    item.document_account_id
                            ),
                            null,
                            item.no_book
                    ),
                    item.ownership_rights,
                    item.no_rope,
                    item.no_hectares,
                    item.cost_per_square_meter,
                    item.height_above_sea_level,
                    item.value_of_permanent_crops,
                    item.value_of_buildings,
                    item.annotation_2
            );
            return itemGuarantee;


        }).collect(Collectors.toList());
    }

    public List<AdmGuarantee> getAll(
            Long credit,
            Long guarantee
    ) throws Exception {

        StringBuilder query = getBaseQuery();
        query.append(

                "   from adm_guarantee guarantee\n" +
                        "         inner join adm_typology a_document_type on guarantee.document_type = a_document_type.typology_id\n" +
                        "         inner join adm_typology a_status on a_status.typology_id = guarantee.status\n" +
                        "         left join adm_person adm_evaluator on adm_evaluator.person_id = guarantee.evaluator\n" +
                        "         inner join adm_user adm_creator on adm_creator.user_id = guarantee.created_by\n" +
                        "         inner join adm_person adm_person_creator on adm_person_creator.person_id = adm_creator.person_id\n" +
                        "         inner join adm_address address on address.address_account_id = guarantee.address_account_id\n" +
                        "         inner join adm_typology adm_typology_state on address.state = adm_typology_state.typology_id\n" +
                        "         inner join adm_typology adm_typology_city on address.city = adm_typology_city.typology_id\n" +
                        "   where guarantee.credit_id =  ").append(credit).append("\n");

        if (guarantee > 0)
            query.append(" and  guarantee.guarantee_id = ").append(guarantee).append("\n");

        Stream<AdmGuaranteeListDto> q = em.createNativeQuery(query.toString(), "admGuaranteeListDto").getResultStream();

        return getStreamResult(q);
    }


    public void edit(AdmGuarantee admGuarantee) throws Exception {

        AdmGuarantee admGuarantee1 = this.findBy(admGuarantee.getGuaranteeId());


        if (admGuarantee.getCredit() != null)
            admGuarantee1.setCredit(admGuarantee.getCredit());

        if (admGuarantee.getNoYear() != null)
            admGuarantee1.setNoYear(admGuarantee.getNoYear());

        if (admGuarantee.getNoReference() != null)
            admGuarantee1.setNoReference(admGuarantee.getNoReference());

        if (admGuarantee.getNameFarm() != null)
            admGuarantee1.setNameFarm(admGuarantee.getNameFarm());

        if (admGuarantee.getOwner() != null)
            admGuarantee1.setOwner(admGuarantee.getOwner());

        if (admGuarantee.getDocumentType() != null)
            admGuarantee1.setDocumentType(admGuarantee.getDocumentType());

        if (admGuarantee.getTestimony() != null)
            admGuarantee1.setTestimony(admGuarantee.getTestimony());

        if (admGuarantee.getRopeValue() != null)
            admGuarantee1.setRopeValue(admGuarantee.getRopeValue());

        if (admGuarantee.getAreaMeters() != null)
            admGuarantee1.setAreaMeters(admGuarantee.getAreaMeters());

        if (admGuarantee.getMsnm() != null)
            admGuarantee1.setMsnm(admGuarantee.getMsnm());

        if (admGuarantee.getTopography() != null)
            admGuarantee1.setTopography(admGuarantee.getTopography());

        if (admGuarantee.getHydrography() != null)
            admGuarantee1.setHydrography(admGuarantee.getHydrography());

        if (admGuarantee.getSoilQuality() != null)
            admGuarantee1.setSoilQuality(admGuarantee.getSoilQuality());

        if (admGuarantee.getPlanCover() != null)
            admGuarantee1.setPlanCover(admGuarantee.getPlanCover());

        if (admGuarantee.getCultivateDetail() != null)
            admGuarantee1.setCultivateDetail(admGuarantee.getCultivateDetail());

        if (admGuarantee.getFarmNeighbor() != null)
            admGuarantee1.setFarmNeighbor(admGuarantee.getFarmNeighbor());

        if (admGuarantee.getRiskClassForm() != null)
            admGuarantee1.setRiskClassForm(admGuarantee.getRiskClassForm());

        if (admGuarantee.getIrrigationExtension() != null)
            admGuarantee1.setIrrigationExtension(admGuarantee.getIrrigationExtension());

        if (admGuarantee.getBuildingDetail() != null)
            admGuarantee1.setBuildingDetail(admGuarantee.getBuildingDetail());

        if (admGuarantee.getAnnotation() != null)
            admGuarantee1.setAnnotation(admGuarantee.getAnnotation());

        if (admGuarantee.getNorthOrigin() != null)
            admGuarantee1.setNorthOrigin(admGuarantee.getNorthOrigin());

        if (admGuarantee.getSouthOrigin() != null)
            admGuarantee1.setSouthOrigin(admGuarantee.getSouthOrigin());

        if (admGuarantee.getOrientOrigin() != null)
            admGuarantee1.setOrientOrigin(admGuarantee.getOrientOrigin());

        if (admGuarantee.getWestOrigin() != null)
            admGuarantee1.setWestOrigin(admGuarantee.getWestOrigin());

        if (admGuarantee.getCommunicationRoutes() != null)
            admGuarantee1.setCommunicationRoutes(admGuarantee.getCommunicationRoutes());

        if (admGuarantee.getState() != null)
            admGuarantee1.setState(admGuarantee.getState());

        if (admGuarantee.getCity() != null)
            admGuarantee1.setCity(admGuarantee.getCity());

        if (admGuarantee.getToCity() != null)
            admGuarantee1.setToCity(admGuarantee.getToCity());

        if (admGuarantee.getEvaluator() != null)
            admGuarantee1.setEvaluator(admGuarantee.getEvaluator());

        if (admGuarantee.getStatus() != null)
            admGuarantee1.setStatus(admGuarantee.getStatus());

       if (admGuarantee.getOwnershipRights() != null)
            admGuarantee1.setOwnershipRights(admGuarantee.getOwnershipRights());

      if (admGuarantee.getNoRope() != null)
            admGuarantee1.setNoRope(admGuarantee.getNoRope());

      if (admGuarantee.getNoHectares() != null)
            admGuarantee1.setNoHectares(admGuarantee.getNoHectares());

      if (admGuarantee.getCostPerSquareMeter() != null)
            admGuarantee1.setCostPerSquareMeter(admGuarantee.getCostPerSquareMeter());

      if (admGuarantee.getHeightAboveSeaLevel() != null)
            admGuarantee1.setHeightAboveSeaLevel(admGuarantee.getHeightAboveSeaLevel());

      if (admGuarantee.getValueOfPermanentCrops() != null)
            admGuarantee1.setValueOfPermanentCrops(admGuarantee.getValueOfPermanentCrops());

      if (admGuarantee.getValueOfBuildings() != null)
            admGuarantee1.setValueOfBuildings(admGuarantee.getValueOfBuildings());

      if (admGuarantee.getAnnotation2() != null)
            admGuarantee1.setAnnotation2(admGuarantee.getAnnotation2());

        this.save(admGuarantee1);

    }

    public List<AdmGuarantee> getAllGuaranteesByAssociate(Long personId) {

        StringBuilder query = getBaseQuery();
        query.append(

                "   from adm_guarantee guarantee\n" +
                        "         inner join adm_typology a_document_type on guarantee.document_type = a_document_type.typology_id\n" +
                        "         inner join adm_typology a_status on a_status.typology_id = guarantee.status\n" +
                        "         left join adm_person adm_evaluator on adm_evaluator.person_id = guarantee.evaluator\n" +
                        "         inner join adm_user adm_creator on adm_creator.user_id = guarantee.created_by\n" +
                        "         inner join adm_person adm_person_creator on adm_person_creator.person_id = adm_creator.person_id\n" +
                        "         inner join adm_address address on address.address_account_id = guarantee.address_account_id\n" +
                        "         inner join adm_typology adm_typology_state on address.state = adm_typology_state.typology_id\n" +
                        "         inner join adm_typology adm_typology_city on address.city = adm_typology_city.typology_id\n" +
                        "         JOIN adm_credit credit ON credit.credit_id = guarantee.credit_id\n" +
                        "         JOIN adm_calculator calculator ON calculator.calculator_id = credit.calculator_id \n" +
                        "         JOIN adm_person associate ON associate.person_id = calculator.person_id\n" +
                "   where associate.person_id =  ").append(personId).append("\n");


        Stream<AdmGuaranteeListDto> q = em.createNativeQuery(query.toString(), "admGuaranteeListDto").getResultStream();

        return getStreamResult(q);
    }
}
