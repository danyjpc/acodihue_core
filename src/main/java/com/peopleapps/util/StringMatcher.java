package com.peopleapps.util;


import java.util.UUID;

public class StringMatcher {

    // to search only data with dpi
    public static Boolean isDpi(String s) {
        return s.matches("^[0-9]{13}+$");
    }

    //to search only data with name or another string
    public static Boolean isName(String s) {
        return s.matches("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$");
    }

    // to search only demdand Number
    public static Boolean isNit(String s) {

        //return s.matches("^[\\d]+$");
        return s.matches("^[\\d]+-?[\\d]+");
    }

    //Search by account number
    public static Boolean isAccountNumber(String s){
        return s.matches("^[0-9]{1,6}+-[0-9]{2}$");
    }

    public static Boolean isTarjetaDeudaCuenta(String s) {

        return s.matches("^[\\d]+");
    }

    public static Boolean isPhoneValid(String s) {

        return s.matches("^[\\d]{8}+");
    }

    public static Boolean isNumeric(String s) {

        return s.matches("^[\\d]+");
    }

    public static Boolean isDate(String s) {

        return s.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    public static Boolean isEmail(String s) {

        return s.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    }

    public static boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // to search only data with nit Company
    public static Boolean isNitCompany(String s) {
        return s.matches("^[0-9]{13}+$");
    }

    // to search only data with employer Number
    public static Boolean isEmployerNumber(String s) {
        return s.matches("^[0-9]{13}+$");
    }

    // to search only data only with cedula and order number
    public static Boolean isCedulaWithOrder(String s) {

        return s.matches("^[\\w]+-[\\d]+\\s+[\\d]+$");
    }

    // to search only data with isCedula Or Dpi number
    public static Boolean isCedulaOrDpiNumber(String s) {
        return s.matches("^[\\d]+$");
    }

    // to search only demdand Number
    public static Boolean isDemandNumber(String s) {

        return s.matches("^[\\d]+-[\\d]+-[\\d]+$");
    }

    //search strings for credit internal number AAAA-####-####
    public static Boolean isCreditInternalCode(String s) {

        return s.matches("^[^\n ][a-zA-Z]{0,4}+-{0,1}[0-9]{0,4}+-{0,1}[0-9]{0,4}+$");
    }

    //search strings for associate number ######
    public static Boolean isAssociateNumber(String s) {

        return s.matches("^[0-9]{1,6}+$");
    }

}
