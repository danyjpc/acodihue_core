package com.peopleapps.dto.inputs.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "AdmUserRegistryDto")
@JsonbPropertyOrder({
        "person", "role", "status", "password", "createtBy"
})
public class AdmUserRegistryDto implements Serializable {
    private AdmPersonRegistredDto person;
    private String password;
    private AdmUserRegistryTypologyDto role;
    private AdmUserRegistryTypologyDto status;
    private AdmPersonRegistredDto createdBy;

    public AdmUserRegistryDto() {
    }

    public AdmUserRegistryDto(AdmPersonRegistredDto person, String password, AdmUserRegistryTypologyDto role, AdmUserRegistryTypologyDto status, AdmPersonRegistredDto createdBy) {
        this.person = person;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdBy = createdBy;
    }

    public AdmPersonRegistredDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonRegistredDto person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdmUserRegistryTypologyDto getRole() {
        return role;
    }

    public void setRole(AdmUserRegistryTypologyDto role) {
        this.role = role;
    }

    public AdmUserRegistryTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmUserRegistryTypologyDto status) {
        this.status = status;
    }

    public AdmPersonRegistredDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmPersonRegistredDto createdBy) {
        this.createdBy = createdBy;
    }

    @JsonbPropertyOrder({
            "personKey", "name", "lastName", "cui", "birthday",
            "email", "status"
    })
    public static class AdmPersonRegistredDto implements Serializable{
        private UUID personKey;
        private String firstName;
        private String lastName;
        private Long cui;
        private LocalDate birthday;
        private String email;
        private AdmUserRegistryTypologyDto status;

        public AdmPersonRegistredDto() {
        }

        public AdmPersonRegistredDto(UUID personKey, String firstName, String lastName, Long cui, LocalDate birthday, String email, AdmUserRegistryTypologyDto status) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cui = cui;
            this.birthday = birthday;
            this.email = email;
            this.status = status;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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

        public AdmUserRegistryTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmUserRegistryTypologyDto status) {
            this.status = status;
        }
    }


    public static class AdmUserRegistryTypologyDto implements Serializable {
        private Long typologyId;
        private String description;

        public AdmUserRegistryTypologyDto() {
        }


        public AdmUserRegistryTypologyDto(Long typologyId, String description) {
            this.typologyId = typologyId;
            this.description = description;
        }

        public Long getTypologyId() {
            return typologyId;
        }

        public void setTypologyId(Long typologyId) {
            this.typologyId = typologyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
