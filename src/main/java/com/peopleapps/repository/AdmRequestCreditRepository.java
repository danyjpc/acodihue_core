package com.peopleapps.repository;


import com.peopleapps.model.AdmCredit;
import com.peopleapps.dto.credit.AdmCreditDocDto;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import  java.util.Optional;

@Repository(forEntity = AdmCredit.class)
public abstract class  AdmRequestCreditRepository extends AbstractEntityRepository<AdmCredit, Long> implements CriteriaSupport<AdmCredit> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public Optional<AdmCreditDocDto> getInfoRequestCreditDoc(UUID creditKey) throws Exception {

        StringBuilder query = new StringBuilder();
        query.append("Select credit.credit_id creditId, \n" +
                "credit.internal_code internal_code,\n" +
                "credit.calculator_id calculator_id,\n" +
                "per.person_key associateKey,\n" +
                "per.person_id person_id,\n" +
                "per.name_complete name_complete,\n" +
                "per.cui cui,\n" +
                "per.nit nit, \n" +
                "per.birthday birthday,\n" +
                "per.email email,\n" +
                "per.membership_number no_associate, \n" +
                "credit.own_house own_house,\n" +
                "ms.description marital_status,\n" +
                "coalesce(phone.phone,0) phone,\n" +
                "direccion.address_line address,\n" +
                "departamento.description statedp,\n" +
                "municipio.description city,\n" +
                "calculador.credit amount,\n" +
                "calculador.no_payments no_payments,\n" +
                "calculador.interest_rate interest_rate,\n" +
                "calculador.interest_final interest_final,\n" +
                "coalesce( destiny.description,'S/D') destiny,\n" +
                "coalesce (actividad.is_fiduciary, false) fiduciary\n" +
                "from adm_credit credit\n" +
                "inner join adm_organization_responsible org on credit.organization_responsible_id=org.organization_responsible_id\n" +
                "left join adm_person per on per.person_id = org.person_id\n" +
                "left join adm_typology ms on per.marital_status = ms.typology_id\n" +
                "left join adm_phone_account pa on per.phone_account_id = pa.phone_account_id\n" +
                "left join adm_phone phone on phone.phone_account_id = pa.phone_account_id\n" +
                "left join adm_address_account ac on per.address_account_id = ac.address_account_id\n" +
                "left join adm_address direccion on direccion.address_account_id = ac.address_account_id\n" +
                "left join adm_typology departamento on direccion.state = departamento.typology_id\n" +
                "left join adm_typology municipio on direccion.city = municipio.typology_id\n" +
                "left join adm_calculator calculador on credit.calculator_id = calculador.calculator_id\n" +
                "left join adm_activity actividad on actividad.activity_account_id = credit.activity_account_id\n" +
                "left join adm_typology destiny on destiny.typology_id = actividad.destiny\n" +
                "where credit_key='").append(creditKey).append("'");
        query.append("\nAND phone.leader =true and direccion.leader");


        Stream<AdmCreditDocDto> q = em.createNativeQuery(query.toString(), "admCreditDocDto").getResultStream();

        /*return q.map(item -> {
            AdmCreditDocDto dto = new AdmCreditDocDto(
                    item.name_complete,
                    item.cui,
                    item.nit,
                    item.birthday,
                    item.email,
                    item.no_associate,
                    item.own_house,
                    item.marital_status,
                    item.phone,
                    item.address,
                    item.statedp,
                    item.city,
                    item.amount,
                    item.no_payments
            );

            return dto;
        }).collect(Collectors.toList());*/
        return q.findFirst();
    }
}
