package com.peopleapps.util;

//ENUM for Pre registration process
public enum PreRegRolesEnum {

    ASOCIADO(Constants.ASOCIADO),
    BENEFICIARIO(Constants.BENEFICIARIO),
    CONYUGE(Constants.CONYUGE);


    private String role;

    public String getRole() {
        return this.role;
    }

    PreRegRolesEnum(String role) {
        this.role = role;
    }

    public static class Constants {
        public static final String ASOCIADO = "Asociado";
        public static final String BENEFICIARIO = "Beneficiario";
        public static final String CONYUGE = "Conyuge";

    }

}
