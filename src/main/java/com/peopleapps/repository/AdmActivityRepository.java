package com.peopleapps.repository;

import com.peopleapps.dto.activity.AdmActivityListDto;
import com.peopleapps.model.*;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository(forEntity = AdmActivity.class)
public abstract class AdmActivityRepository
        extends AbstractEntityRepository<AdmActivity, Long>
        implements CriteriaSupport<AdmActivity> {


    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmActivity> getAll(
            Long activityAccountId,
            Long activity
    ) {
        StringBuilder query = new StringBuilder();
        query.append("select activity.activity_id       activity_id,\n" +
                "       activity.activity_account_id  activity_account_id,\n" +
                "       activity.destiny           destiny_id,\n" +
                "       a_destiny.description      destiny_desc,\n" +
                "       activity.activity_economic activity_economic_id,\n" +
                "       a_economic.description     activity_economic_desc,\n" +
                "       activity.is_apparel        is_apparel,\n" +
                "       activity.is_fiduciary      is_fiduciary,\n" +
                "       activity.area              area,\n" +
                "       activity.unit_measure      unit_measure_id,\n" +
                "       a_unit.description         unit_measure_desc,\n" +
                "       a_unit.value_1             unit_measure_value,\n" +
                "       activity.price             price,\n" +
                "       activity.earnings          earnings,\n" +
                "       activity.created_by        created_by,\n" +
                "       a_person.email             created_email,\n" +
                "       a_person.person_key        person_key,\n" +
                "       activity.date_created      date_created,\n" +
                "       activity.leader            leader,\n" +
                "       activity.status            status_id,\n" +
                "       a_status.description       status_desc,\n" +
                "       activity.quantity          quantity,\n" +
                "       activity.annotation        annotation\n" +
                "from adm_activity activity\n" +
                "         inner join adm_typology a_destiny on activity.destiny = a_destiny.typology_id\n" +
                "         inner join adm_typology a_economic on activity.activity_economic = a_economic.typology_id\n" +
                "         inner join adm_typology a_status on activity.status = a_status.typology_id\n" +
                "         inner join adm_typology a_unit on activity.unit_measure = a_unit.typology_id\n" +
                "         inner join adm_user a_user on activity.created_by = a_user.user_id\n" +
                "         inner join adm_person a_person on a_user.person_id = a_person.person_id\n" +
                "where activity.activity_account_id = ").append(activityAccountId).append("\n");

        if (activity != null)
            query.append(" and activity.activity_id= ").append(activity);


        Stream<AdmActivityListDto> q = em.createNativeQuery(query.toString(), "admActivityListDto").getResultStream();

        return q.map(item -> {
            AdmActivity activityItem = new AdmActivity(
                    item.activity_id,
                    new AdmActivityAccount(
                            item.activity_account_id
                    ),
                    new AdmTypology(
                            item.destiny_id,
                            item.destiny_desc
                    ),
                    new AdmTypology(
                            item.activity_economic_id,
                            item.activity_economic_desc
                    ),
                    item.is_apparel,
                    item.is_fiduciary,
                    item.area,
                    new AdmTypology(
                            item.unit_measure_id,
                            item.unit_measure_desc,
                            null,
                            item.unit_measure_value,
                            null,
                            null,
                            null
                    ),
                    item.price,
                    item.earnings,
                    new AdmTypology(
                            item.status_id,
                            item.status_desc
                    ),
                    new AdmUser(
                            new AdmPerson(
                                    item.person_key,
                                    item.created_email
                            )
                    ),
                    item.date_created,
                    item.quantity,
                    item.annotation
            );
            return activityItem;
        }).collect(Collectors.toList());
    }

    public void edit(AdmActivity admActivity) throws Exception {

        AdmActivity admActivity1 = this.findBy(admActivity.getActivityId());


        if (admActivity.getDestiny() != null)
            admActivity1.setDestiny(admActivity.getDestiny());

        if (admActivity.getActivityEconomic() != null)
            admActivity1.setActivityEconomic(admActivity.getActivityEconomic());

        if (admActivity.getIsApparel() != null)
            admActivity1.setIsApparel(admActivity.getIsApparel());

        if (admActivity.getIsFiduciary() != null)
            admActivity1.setIsFiduciary(admActivity.getIsFiduciary());

        if (admActivity.getArea() != null)
            admActivity1.setArea(admActivity.getArea());

        if (admActivity.getUnitMeasure() != null)
            admActivity1.setUnitMeasure(admActivity.getUnitMeasure());

        if (admActivity.getPrice() != null)
            admActivity1.setPrice(admActivity.getPrice());

        if (admActivity.getEarnings() != null)
            admActivity1.setEarnings(admActivity.getEarnings());

        if (admActivity.getStatus() != null)
            admActivity1.setStatus(admActivity.getStatus());

        if (admActivity.getQuantity() != null)
            admActivity1.setQuantity(admActivity.getQuantity());

        if (admActivity.getAnnotation() != null) {
            admActivity1.setAnnotation(admActivity.getAnnotation());
        }


        this.save(admActivity1);

    }
}
