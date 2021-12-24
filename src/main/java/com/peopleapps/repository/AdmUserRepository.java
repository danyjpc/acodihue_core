package com.peopleapps.repository;


import com.peopleapps.dto.inputs.user.AdmUserRegistryDto;
import com.peopleapps.dto.user.AdmRegistryUserListDto;
import com.peopleapps.dto.user.AdmUserListDto;
import com.peopleapps.model.*;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmUser.class)
public abstract class AdmUserRepository extends AbstractEntityRepository<AdmUser, Long>
        implements CriteriaSupport<AdmUser> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmUser> getAllUsers(
            Long userId,
            String password,
            String email,
            Boolean desc
    ) throws Exception {

        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    usuario.user_id,\n" +
                "    usuario.person_id,\n" +
                "    person.first_name,\n" +
                "    person.last_name,\n" +
                "    person.email,\n" +
                "    person.person_key,\n" +
                "    usuario.date_created,\n" +
                "    role_typo.typology_id    role_id,\n" +
                "    role_typo.description    role_desc,\n" +
                "    status_typo.typology_id  status_id,\n" +
                "    status_typo.description  status_desc\n" +
                "FROM adm_user usuario\n" +
                "INNER JOIN adm_person person ON person.person_id = usuario.person_id\n" +
                "INNER JOIN adm_typology role_typo ON role_typo.typology_id = usuario.role\n" +
                "INNER JOIN adm_typology status_typo ON status_typo.typology_id = usuario.status\n" +
                "WHERE 1 = 1");

        if (userId != null && userId > 0)
            query.append(" and usuario.user_id ='").append(userId).append("' \n");

        if (password != null)
            query.append(" and usuario.pwd = '").append(password).append("'");

        if (email != null)
            query.append(" and person.email = '").append(email).append("'");

        if (desc != null && desc) {
            query.append("\nORDER BY user_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY user_id ASC");
        }

        Stream<AdmUserListDto> q = em.createNativeQuery(query.toString(), "admUserDto").getResultStream();

        return q.map(item -> {
            AdmUser user = new AdmUser(
                    item.getUserId(),
                    new AdmPerson(
                            item.getPersonId(),
                            item.getFirstName(),
                            item.getLastName(),
                            item.getEmail(),
                            item.getPersonKey()
                    ),
                    item.getDateCreated(),
                    new AdmTypology(
                            item.getRoleId(),
                            item.getRoleDesc()
                    ),
                    new AdmTypology(
                            item.getStatusId(),
                            item.getStatusDesc()
                    )
            );
            return user;
        }).collect(Collectors.toList());
    }


    public List<AdmUserRegistryDto> getAllUsersDTO(
            UUID personKey,
            String password,
            String email,
            Boolean desc
    ) throws Exception {

        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    person.person_key                                          person_key,\n" +
                "    CONCAT(person.first_name)                                  first_name,\n" +
                "    CONCAT(person.last_name)                                   last_name,\n" +
                "    person.cui                                                 cui,\n" +
                "    person.birthday                                            birthday,\n" +
                "    person.email                                               email,\n" +
                "    person_status_typo.typology_id                             person_status_id,\n" +
                "    person_status_typo.description                             person_status_desc,\n" +
                "\n" +
                "    usuario_status_typo.typology_id                            usuario_status_id,\n" +
                "    usuario_status_typo.description                            usuario_status_desc,\n" +
                "    usuario_role_typo.typology_id                              usuario_role_id,\n" +
                "    usuario_role_typo.description                              usuario_role_desc,\n" +
                "\n" +
                "    created_by_person.person_key                                             created_by_person_key,\n" +
                "    CONCAT(created_by_person.first_name, ' ', created_by_person.middle_name) created_by_first_name,\n" +
                "    CONCAT(created_by_person.last_name, ' ', created_by_person.partner_name) created_by_last_name,\n" +
                "    created_by_person.cui                                                    created_by_cui,\n" +
                "    created_by_person.birthday                                               created_by_birthday,\n" +
                "    created_by_person.email                                                  created_by_email,\n" +
                "    created_by_person_status_typo.typology_id                                created_by_status_id,\n" +
                "    created_by_person_status_typo.description                                created_by_status_desc\n" +
                "\n" +
                "FROM adm_person person\n" +
                "INNER JOIN adm_user usuario ON person.person_id = usuario.person_id\n" +
                "INNER JOIN adm_typology person_status_typo ON person_status_typo.typology_id = person.status\n" +
                "INNER JOIN adm_typology usuario_status_typo ON usuario_status_typo.typology_id = usuario.status\n" +
                "INNER JOIN adm_typology usuario_role_typo ON usuario_role_typo.typology_id = usuario.role\n" +
                "INNER JOIN adm_user created_by_user ON created_by_user.user_id = person.created_by\n" +
                "INNER JOIN adm_person created_by_person ON created_by_person.person_id = created_by_user.person_id\n" +
                "INNER JOIN adm_typology created_by_person_status_typo ON created_by_person_status_typo.typology_id = created_by_person.status\n" +
                "WHERE 1 = 1");

        if (personKey != null && !personKey.equals(""))
            query.append(" and person.person_key ='").append(personKey).append("' \n");

        if (password != null)
            query.append(" and usuario.pwd = '").append(password).append("' \n");

        if (email != null)
            query.append(" and person.email = '").append(email).append("' \n");

        if (desc != null && desc) {
            query.append("\nORDER BY usuario.user_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY usuario.user_id ASC");
        }
        Stream<AdmRegistryUserListDto> q = em.createNativeQuery(query.toString(), "admRegistryUserListDto").getResultStream();

        return q.map(item -> {
            AdmUserRegistryDto userResgistyDto = new AdmUserRegistryDto(
                    new AdmUserRegistryDto.AdmPersonRegistredDto(
                            item.getPerson_key(),
                            //if token[1] is equal to "S/D" ignore it and use only first token
                            item.getFirst_name(),
                            //(item.getFirst_name().split(" ")[1].equals(CsnConstants.DEFAULT_TEXT_SD)) ? item.getFirst_name().split(" ")[0] : item.getFirst_name(),
                            //if token[1] is equal to "S/D" ignore it and use only first token
                            item.getLast_name(),
                            //(item.getLast_name().split(" ")[1].equals(CsnConstants.DEFAULT_TEXT_SD)) ? item.getLast_name().split(" ")[0] : item.getLast_name(),
                            item.getCui(),
                            item.getBirthday(),
                            item.getEmail(),
                            new AdmUserRegistryDto.AdmUserRegistryTypologyDto(
                                    item.getPerson_status_id(),
                                    item.getPerson_status_desc()
                            )
                    ),

                    null, //password
                    new AdmUserRegistryDto.AdmUserRegistryTypologyDto(
                            item.getUsuario_role_id(),
                            item.getUsuario_role_desc()
                    ),
                    new AdmUserRegistryDto.AdmUserRegistryTypologyDto(
                            item.getUsuario_status_id(),
                            item.getUsuario_status_desc()
                    ),

                    new AdmUserRegistryDto.AdmPersonRegistredDto(
                            item.getCreated_by_person_key(),
                            item.getCreated_by_first_name(),
                            item.getCreated_by_last_name(),
                            item.getCreated_by_cui(),
                            item.getCreated_by_birthday(),
                            item.getCreated_by_email(),
                            new AdmUserRegistryDto.AdmUserRegistryTypologyDto(
                                    item.getCreated_by_status_id(),
                                    item.getCreated_by_status_desc()
                            )
                    )
            );
            return userResgistyDto;
        }).collect(Collectors.toList());
    }

    public void editUser(AdmUser admUser) {
        AdmUser currentUser = this.findBy(admUser.getUserId());

        if (currentUser != null) {
            //FIELDS THAT CAN NOT BE CHANGED
            admUser.setUserId(currentUser.getUserId());
            admUser.setPerson(currentUser.getPerson());
            admUser.setPassword(currentUser.getPassword());
            admUser.setDateCreated(currentUser.getDateCreated());

            //FIELDS THAT CAN BE UPDATED
            if (admUser.getRole() == null) {
                admUser.setRole(currentUser.getRole());
            }
            if (admUser.getStatus() == null) {
                admUser.setStatus(currentUser.getStatus());
            }
            this.save(admUser);

        }
    }


    public AdmUser findByKey(UUID key) {

        Criteria<AdmUser, AdmUser> query = criteria();
        query.join(AdmUser_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.personKey, key));
        return query.getAnyResult();
    }

    public AdmUser findUserByPersonId(Long personId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "    usuario.user_id\n" +
                "    FROM adm_user usuario" +
                "    WHERE 1 = 1");
        query.append(" and usuario.person_id ='").append(personId).append("' \n");

        Query q = em.createNativeQuery(query.toString());
        List<Object> rawResult = q.getResultList();
        AdmUser foundUser = new AdmUser();
        if (rawResult.isEmpty()) {
            foundUser = null;
            return foundUser;
        }
        for (Object result : rawResult) {
            System.out.println("USER ID **************** " + result);
            foundUser.setUserId(Long.parseLong(result.toString()));
        }

        foundUser = this.findBy(foundUser.getUserId());
        return foundUser;

    }

    public AdmUser findByCui(Long cui) {
        Criteria<AdmUser, AdmUser> query = criteria();
        query.join(AdmUser_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.cui, cui));
        return query.getAnyResult();
    }

    public AdmUser findByEmail(String email) {
        Criteria<AdmUser, AdmUser> query = criteria();
        query.join(AdmUser_.person,
                where(AdmPerson.class)
                        .eq(AdmPerson_.email, email));
        return query.getAnyResult();
    }
}
