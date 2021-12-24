package com.peopleapps.security;

public enum RolesEnum {
    COORDINADOR(Constants.COORDINADOR_VALUE),
    OPERARIO(Constants.OPERARIO_VALUE),
    ADMINISTRADOR(Constants.ADMINISTRADOR_VALUE),
    IT(Constants.IT_VALUE);

    private String role;

    public String getRole() {
        return this.role;
    }

    RolesEnum(String role) {
        this.role = role;
    }

    public static class Constants {
        public static final String COORDINADOR_VALUE = "Coordinador";
        public static final String OPERARIO_VALUE = "Operario";
        public static final String IT_VALUE = "IT";
        public static final String ADMINISTRADOR_VALUE = "Admnistrador";
    }
}
