package com.peopleapps.dto.auth;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name ="AdmPasswordRecoveryDto")
public class AdmPasswordRecoveryDto {

    private String email;

    public AdmPasswordRecoveryDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
