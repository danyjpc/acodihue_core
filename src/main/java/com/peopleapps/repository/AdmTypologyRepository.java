package com.peopleapps.repository;

import com.peopleapps.dto.address.AdmAddressListDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.typology.AdmTypologyListDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmTypology.class)
public abstract class AdmTypologyRepository extends AbstractEntityRepository<AdmTypology, Long>
        implements CriteriaSupport<AdmTypology> {

    @Inject
    EntityManager em;

    public List<AdmTypologyDto> getAllTypologies(
            Long typologyId,
            Long start,
            Long max,
            Boolean asc,
            String columnOrder,
            String name,
            Boolean desc
    ) {
        if (typologyId == null) {
            typologyId = CsnConstants.TYPOLOGY_GENERAL;
        }
        StringBuilder query = new StringBuilder();
        query.append("SELECT typology.typology_id,\n" +
                "       typology.description,\n" +
                "       typology.parent_typology_id,\n" +
                "       typology.value_1,\n" +
                "       typology.value_2,\n" +
                "       typology.available,\n" +
                "       typology.editable,\n" +
                "       parent.description    parent_typology_description,\n" +
                "       typology.value_3\n" +
                "FROM adm_typology typology\n" +
                "INNER JOIN adm_typology parent\n" +
                "ON typology.parent_typology_id = parent.typology_id\n" +
                "WHERE 1 = 1");
        if (typologyId > 0 && !typologyId.equals(CsnConstants.TYPOLOGY_GENERAL)) {
            query.append(" AND typology.typology_id = ").append(typologyId);
        }
        //When the typology id is 100 a different query needs to be made
        else if (typologyId == CsnConstants.TYPOLOGY_GENERAL) {
            query = new StringBuilder();
            query.append("SELECT typology.typology_id,\n" +
                    "       typology.description,\n" +
                    "       typology.parent_typology_id,\n" +
                    "       typology.value_1,\n" +
                    "       typology.value_2,\n" +
                    "       typology.available,\n" +
                    "       typology.editable,\n" +
                    "       typology.value_3\n" +
                    "FROM adm_typology typology\n" +
                    "WHERE 1 = 1");
            query.append(" AND typology.typology_id = ").append(CsnConstants.TYPOLOGY_GENERAL);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY typology.typology_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY typology.typology_id ASC");
        }


        System.out.println("RepositoryQuery---> " + query.toString());
        Query q = em.createNativeQuery(query.toString(), "admTypologyListDto");
        List<AdmTypologyListDto> admTypologyListDtos = q.getResultList();
        List<AdmTypologyDto> admTypologyDtos = new ArrayList<>();
        for (AdmTypologyListDto item : admTypologyListDtos) {
            //Querying child typologies
            List<AdmTypologyDto> childTypologies = this.getChildTypologies(typologyId);

            AdmTypologyDto parentTypology = new AdmTypologyDto();
            parentTypology.setTypologyId(item.getParentTypologyId());
            parentTypology.setDescription(item.getParentTypologyDescription());

            AdmTypologyDto typologyDto = new AdmTypologyDto();
            typologyDto.setTypologyId(item.getTypologyId());
            typologyDto.setDescription(item.getDescription());
            typologyDto.setValue1(item.getValue1());
            typologyDto.setValue2(item.getValue2());
            typologyDto.setValue3(item.getValue3());
            typologyDto.setAvailable(item.getAvailable());
            typologyDto.setEditable(item.getEditable());
            typologyDto.setParentTypology(parentTypology);
            typologyDto.setChildTypologies(childTypologies);

            admTypologyDtos.add(typologyDto);
        }
        return admTypologyDtos;

    }

    public List<AdmTypologyDto> getChildTypologies(Long parentTypologyId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT typology.typology_id,\n" +
                "       typology.description,\n" +
                "       typology.parent_typology_id,\n" +
                "       typology.value_1,\n" +
                "       typology.value_2,\n" +
                "       typology.available,\n" +
                "       typology.editable,\n" +
                "       parent.description    parent_typology_description\n" +
                "FROM adm_typology typology\n" +
                "INNER JOIN adm_typology parent\n" +
                "ON typology.parent_typology_id = parent.typology_id\n" +
                "WHERE 1 = 1");
        query.append(" AND typology.parent_typology_id = ").append(parentTypologyId);
        query.append(" ORDER BY typology_id");

        Query q = em.createNativeQuery(query.toString(), "admTypologyListDto");
        List<AdmTypologyListDto> admChildTypologiesListDto = q.getResultList();
        List<AdmTypologyDto> admChildTypologiesDto = new ArrayList<>();
        for (AdmTypologyListDto item : admChildTypologiesListDto) {
            AdmTypologyDto parentTypology = new AdmTypologyDto();
            //TODO Adds childs to childs
            List<AdmTypologyDto> childTypologies = this.getChildTypologies(item.getTypologyId());
            parentTypology.setTypologyId(item.getParentTypologyId());
            parentTypology.setDescription(item.getParentTypologyDescription());

            AdmTypologyDto typologyDto = new AdmTypologyDto();
            typologyDto.setTypologyId(item.getTypologyId());
            typologyDto.setDescription(item.getDescription());
            typologyDto.setValue1(item.getValue1());
            typologyDto.setValue2(item.getValue2());
            typologyDto.setValue3(item.getValue3());
            typologyDto.setAvailable(item.getAvailable());
            typologyDto.setEditable(item.getEditable());
            typologyDto.setParentTypology(parentTypology);
            typologyDto.setChildTypologies(childTypologies);

            admChildTypologiesDto.add(typologyDto);
        }
        return admChildTypologiesDto;
    }

    public void saveEdit(AdmTypology updAdmTypology) {
        AdmTypology curAdmTypology = this.findBy(updAdmTypology.getTypologyId());

        if(updAdmTypology.getDescription() == null){
            updAdmTypology.setDescription(curAdmTypology.getDescription());
        }
        if(updAdmTypology.getValue1() == null){
            updAdmTypology.setValue1(curAdmTypology.getValue1());
        }
        if(updAdmTypology.getValue2() == null){
            updAdmTypology.setValue2(curAdmTypology.getValue2());
        }
        if(updAdmTypology.getValue3() == null){
            updAdmTypology.setValue3(curAdmTypology.getValue3());
        }
        if(updAdmTypology.getAvailable() == null){
            updAdmTypology.setAvailable(curAdmTypology.getAvailable());
        }
        if(updAdmTypology.getEditable() == null){
            updAdmTypology.setEditable(curAdmTypology.getEditable());
        }
        if(updAdmTypology.getParentTypology() == null){
            updAdmTypology.setParentTypology(curAdmTypology.getParentTypology());
        }

        this.save(updAdmTypology);

    }


    //Return all account typologies
    //Used to get all addresses by associate key
    public List<AdmTypologyDto> getAllAccounts(Long accountStatus, Boolean desc, Long accountTypeId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT typology.typology_id,\n" +
                "       typology.description,\n" +
                "       typology.parent_typology_id,\n" +
                "       typology.value_1,\n" +
                "       typology.value_2,\n" +
                "       typology.value_3\n" +
                "FROM adm_typology typology\n" +
                "WHERE 1 = 1");
        query.append(" AND typology.parent_typology_id = ").append(CsnConstants.ACCOUNT_TYPE_TYPOLOGY);

        if (accountStatus != null && accountStatus > 0) {
            query.append("\nAND typology.status_id = ").append(accountStatus);
        }
        if(accountTypeId != null && accountTypeId > 0){
            query.append("\nAND typology.typology_id = ").append(accountTypeId);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY typology.typology_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY typology.typology_id ASC");
        }

        Stream<AdmTypologyListDto> q = em.createNativeQuery(query.toString(), "admTypologyListDto").getResultStream();
        return q.map(item -> {
            AdmTypologyDto accountTypeDto = new AdmTypologyDto(
                    item.getTypologyId(),
                    item.getDescription(),
                    item.getValue1(),
                    item.getValue2(),
                    item.getValue3()

            );
            return accountTypeDto;
        }).collect(Collectors.toList());
    }

}
