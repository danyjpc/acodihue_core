package com.peopleapps.dto.user;


import java.time.LocalDate;
import java.util.UUID;

public class AdmRegistryUserListDto {
    private UUID person_key;
    private String first_name;
    private String last_name;
    private Long cui;
    private LocalDate birthday;
    private String email;
    private Long person_status_id;
    private String person_status_desc;
    private Long usuario_status_id;
    private String usuario_status_desc;
    private Long usuario_role_id;
    private String usuario_role_desc;
    private UUID created_by_person_key;
    private String created_by_first_name;
    private String created_by_last_name;
    private Long created_by_cui;
    private LocalDate created_by_birthday;
    private String created_by_email;
    private Long created_by_status_id;
    private String created_by_status_desc;
    private UUID organization_key;
    private String organization_name;

    public AdmRegistryUserListDto() {
    }

    public AdmRegistryUserListDto(UUID person_key, String first_name, String last_name, Long cui, LocalDate birthday, String email, Long person_status_id, String person_status_desc, Long usuario_status_id, String usuario_status_desc, Long usuario_role_id, String usuario_role_desc, UUID created_by_person_key, String created_by_first_name, String created_by_last_name, Long created_by_cui, LocalDate created_by_birthday, String created_by_email, Long created_by_status_id, String created_by_status_desc, UUID organization_key, String organization_name) {
        this.person_key = person_key;
        this.first_name = first_name;
        this.last_name = last_name;
        this.cui = cui;
        this.birthday = birthday;
        this.email = email;
        this.person_status_id = person_status_id;
        this.person_status_desc = person_status_desc;
        this.usuario_status_id = usuario_status_id;
        this.usuario_status_desc = usuario_status_desc;
        this.usuario_role_id = usuario_role_id;
        this.usuario_role_desc = usuario_role_desc;
        this.created_by_person_key = created_by_person_key;
        this.created_by_first_name = created_by_first_name;
        this.created_by_last_name = created_by_last_name;
        this.created_by_cui = created_by_cui;
        this.created_by_birthday = created_by_birthday;
        this.created_by_email = created_by_email;
        this.created_by_status_id = created_by_status_id;
        this.created_by_status_desc = created_by_status_desc;
        this.organization_key = organization_key;
        this.organization_name = organization_name;
    }

    public UUID getPerson_key() {
        return person_key;
    }

    public void setPerson_key(UUID person_key) {
        this.person_key = person_key;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Long getCui() {
        return cui;
    }

    public void setCui(Long cui) {
        this.cui = cui;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPerson_status_id() {
        return person_status_id;
    }

    public void setPerson_status_id(Long person_status_id) {
        this.person_status_id = person_status_id;
    }

    public String getPerson_status_desc() {
        return person_status_desc;
    }

    public void setPerson_status_desc(String person_status_desc) {
        this.person_status_desc = person_status_desc;
    }

    public Long getUsuario_status_id() {
        return usuario_status_id;
    }

    public void setUsuario_status_id(Long usuario_status_id) {
        this.usuario_status_id = usuario_status_id;
    }

    public String getUsuario_status_desc() {
        return usuario_status_desc;
    }

    public void setUsuario_status_desc(String usuario_status_desc) {
        this.usuario_status_desc = usuario_status_desc;
    }

    public Long getUsuario_role_id() {
        return usuario_role_id;
    }

    public void setUsuario_role_id(Long usuario_role_id) {
        this.usuario_role_id = usuario_role_id;
    }

    public String getUsuario_role_desc() {
        return usuario_role_desc;
    }

    public void setUsuario_role_desc(String usuario_role_desc) {
        this.usuario_role_desc = usuario_role_desc;
    }

    public UUID getCreated_by_person_key() {
        return created_by_person_key;
    }

    public void setCreated_by_person_key(UUID created_by_person_key) {
        this.created_by_person_key = created_by_person_key;
    }

    public String getCreated_by_first_name() {
        return created_by_first_name;
    }

    public void setCreated_by_first_name(String created_by_first_name) {
        this.created_by_first_name = created_by_first_name;
    }

    public String getCreated_by_last_name() {
        return created_by_last_name;
    }

    public void setCreated_by_last_name(String created_by_last_name) {
        this.created_by_last_name = created_by_last_name;
    }

    public Long getCreated_by_cui() {
        return created_by_cui;
    }

    public void setCreated_by_cui(Long created_by_cui) {
        this.created_by_cui = created_by_cui;
    }

    public LocalDate getCreated_by_birthday() {
        return created_by_birthday;
    }

    public void setCreated_by_birthday(LocalDate created_by_birthday) {
        this.created_by_birthday = created_by_birthday;
    }

    public String getCreated_by_email() {
        return created_by_email;
    }

    public void setCreated_by_email(String created_by_email) {
        this.created_by_email = created_by_email;
    }

    public Long getCreated_by_status_id() {
        return created_by_status_id;
    }

    public void setCreated_by_status_id(Long created_by_status_id) {
        this.created_by_status_id = created_by_status_id;
    }

    public String getCreated_by_status_desc() {
        return created_by_status_desc;
    }

    public void setCreated_by_status_desc(String created_by_status_desc) {
        this.created_by_status_desc = created_by_status_desc;
    }

    public UUID getOrganization_key() {
        return organization_key;
    }

    public void setOrganization_key(UUID organization_key) {
        this.organization_key = organization_key;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }
}