package com.peopleapps.dto.auth;

import javax.validation.constraints.NotNull;

public class AdmCredentials {

    @NotNull
    private String email;

    @NotNull
    private String pwd;

    public AdmCredentials() {
    }

    public AdmCredentials(@NotNull String email, @NotNull String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
