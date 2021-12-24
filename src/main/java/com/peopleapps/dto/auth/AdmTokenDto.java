package com.peopleapps.dto.auth;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"personKey", "expire","token"})
public class AdmTokenDto {
    @JsonbProperty("person_key")
    private String personKey;
    private Long expire;
    private String token;

    public AdmTokenDto() {
    }

    public String getPersonKey() {
        return personKey;
    }

    public void setPersonKey(String personKey) {
        this.personKey = personKey;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
