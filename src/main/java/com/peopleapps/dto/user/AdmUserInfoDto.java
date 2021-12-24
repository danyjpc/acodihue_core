package com.peopleapps.dto.user;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.UUID;

@JsonbPropertyOrder({
        "userId",
        "username"
})
public class AdmUserInfoDto {

    private AdmPersonUserInfoDto person;

    public AdmUserInfoDto() {
    }

    public AdmUserInfoDto(AdmPersonUserInfoDto person) {
        this.person = person;
    }

    public AdmPersonUserInfoDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonUserInfoDto person) {
        this.person = person;
    }

    public static class AdmPersonUserInfoDto{
        private UUID personKey;
        private String email;

        public AdmPersonUserInfoDto() {
        }

        public AdmPersonUserInfoDto(UUID personKey, String email) {
            this.personKey = personKey;
            this.email = email;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
