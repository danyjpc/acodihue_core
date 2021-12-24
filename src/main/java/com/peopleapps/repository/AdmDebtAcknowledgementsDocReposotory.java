package com.peopleapps.repository;

import com.peopleapps.dto.credit.AdmDebtAcknowledgementsDto;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository(forEntity = AdmDebtAcknowledgementsDto.class)
public abstract class AdmDebtAcknowledgementsDocReposotory extends AbstractEntityRepository<AdmDebtAcknowledgementsDto, Long> implements CriteriaSupport<AdmDebtAcknowledgementsDto> {

    @Inject
    EntityManager em;

    private StringBuilder getConsultaMaster(){
        StringBuilder query = new StringBuilder();

        query.append("select\n" +
                "       aa.address_id as addres_id,\n" +
                "       atco.description as country,\n" +
                "       atc.description as city,\n" +
                "       ats.description as state,\n" +
                "       aa.address_line as address_line,\n" +
                "       aa.extension as extension,\n" +
                "       aa.no_public as no_public,\n" +
                "       credit.credit_id as credit_id,\n" +
                "       credit.credit_key as credit_key,\n" +
                "       credit.estate_date as state_credit,\n" +
                "       credit.estate_date +  (ac.no_payments || ' month')::interval as date_end_credit,\n" +
                "       ac.no_payments as no_payments,\n" +
                "       ac.interest_rate as interest_rate,\n" +
                "       ac.interest_final as interest_final,\n" +
                "       ac.application_date as application_date,\n" +
                "       ac.no_period as no_period,\n" +
                "       ac.credit as credit,\n" +
                "       ac.date_created as date_created,\n" +
                "       ag.no_reference as no_reference,\n" +
                "       ag.name_farm as name_farm,\n" +
                "       ag.owner as owner,\n" +
                "       atdt.description as document_type,\n" +
                "       ap.name_complete as name_complete,\n" +
                "       ap.person_key as person_key,\n" +
                "       ap.birthday as birthday,\n" +
                "       atp.description as profession,\n" +
                "       atms.description as marital_status,\n" +
                "       ap.cui as cui,\n" +
                "       atg.description as genre,\n" +
                "       atmp.description as mayan_people,\n" +
                "       ap2.name_complete as partner_name,\n" +
                "       ap2.person_key as partner_person_key,\n" +
                "       ap2.birthday as partner_birthday,\n" +
                "       atpp.description as partner_profession,\n" +
                "       atmsp.description as partner_marital_status,\n" +
                "       ap2.cui as partner_cui,\n" +
                "       atgp.description as partner_genre,\n" +
                "       atmpp.description as partner_mayan_people\n" +
                "from adm_credit credit\n" +
                "inner join adm_organization_responsible aor on credit.organization_responsible_id = aor.organization_responsible_id\n" +
                "inner join adm_person ap on aor.person_id = ap.person_id\n" +
                "left join adm_partner_credit apc on credit.credit_id = apc.credit_id\n" +
                "left join adm_person ap2 on apc.person_id = ap2.person_id\n" +
                "left join adm_calculator ac on credit.calculator_id = ac.calculator_id\n" +
                "left join adm_guarantee ag on credit.credit_id = ag.credit_id\n" +
                "left join adm_address_account aaa on ag.address_account_id = aaa.address_account_id\n" +
                "left join adm_address aa on aaa.address_account_id = aa.address_account_id\n" +
                "left join adm_typology atc on aa.city = atc.typology_id\n" +
                "left join adm_typology ats on aa.state = ats.typology_id\n" +
                "left join adm_typology atco on aa.country = atco.typology_id\n" +
                "left join adm_typology atdt on ag.document_type = atdt.typology_id\n" +
                "left join adm_typology atp on ap.profession_id = atp.typology_id\n" +
                "left join adm_typology atg on ap.genre = atg.typology_id\n" +
                "left join adm_typology atms on ap.marital_status = atms.typology_id\n" +
                "left join adm_typology atmp on ap.mayan_people = atmp.typology_id\n" +
                "left join adm_typology atpp on ap2.profession_id = atpp.typology_id\n" +
                "left join adm_typology atgp on ap2.genre = atgp.typology_id\n" +
                "left join adm_typology atmsp on ap2.marital_status = atmsp.typology_id\n" +
                "left join adm_typology atmpp on ap2.mayan_people = atmpp.typology_id\n");

        return query;
    }

    public Optional<AdmDebtAcknowledgementsDto> getAcknowledgementsDoc(
            UUID creditKey)  {
        StringBuilder query = this.getConsultaMaster();

        query.append("where credit.credit_key='").append(creditKey).append("'");

        Stream<AdmDebtAcknowledgementsDto> q = em.createNativeQuery(query.toString(), "admDebtAcknowledgementsDto").getResultStream();
//        return q.map(item -> {
//            AdmDebtAcknowledgementsDto admDebtAcknowledgementsDto = new AdmDebtAcknowledgementsDto(
//                    item.getAddressId(),
//                    item.getCountry(),
//                    item.getCity(),
//                    item.getState(),
//                    item.getAddressLine(),
//                    item.getExtension(),
//                    item.getNoPublic(),
//                    item.getCreditId(),
//                    item.getCreditKey(),
//                    item.getStateCredit(),
//                    item.getDateEndCredit(),
//                    item.getNoPayments(),
//                    item.getInterestRate(),
//                    item.getInterestFinal(),
//                    item.getApplicationDate(),
//                    item.getNoPeriod(),
//                    item.getCredit(),
//                    item.getDateCreated(),
//                    item.getNoReference(),
//                    item.getNameFarm(),
//                    item.getOwner(),
//                    item.getDocumentType(),
//                    item.getDocumentDate(),
//                    item.getNotary(),
//                    item.getNameComplete(),
//                    item.getPersonKey(),
//                    item.getBirthday(),
//                    item.getProfession(),
//                    item.getMaritalStatus(),
//                    item.getCui(),
//                    item.getGenre(),
//                    item.getMayanPeople(),
//                    item.getPartnerName(),
//                    item.getPartnerPersonKey(),
//                    item.getPartnerBirthday(),
//                    item.getPartnerProfession(),
//                    item.getPartnerMaritalStatus(),
//                    item.getPartnerCui(),
//                    item.getPartnerGenre(),
//                    item.getPartnerMayanPeople(),
//                    item.getNameRepLegal(),
//                    item.getCuiRepLegal(),
//                    item.getBirthdayRepLegal(),
//                    item.getMaritalStatusRepLegal(),
//                    item.getProfessionRepLegal(),
//                    item.getAcreditationRepLegal(),
//                    item.getDateAcreditationRepLegal()
//            );
//
//            return admDebtAcknowledgementsDto;
//        }).collect(Collectors.toList());
        return q.findFirst();

    }
}
