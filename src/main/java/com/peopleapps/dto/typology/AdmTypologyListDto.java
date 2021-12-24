package com.peopleapps.dto.typology;


import java.util.List;

public class AdmTypologyListDto {
    private Long typologyId;
    private String description;
    private String value1;
    private String value2;
    private Boolean available;
    private Boolean editable;
    private Long parentTypologyId;
    private String parentTypologyDescription;
    private String value3;

    public AdmTypologyListDto(Long typologyId, String description, String value1, String value2, Boolean available, Boolean editable, Long parentTypologyId, String parentTypologyDescription) {
        this.typologyId = typologyId;
        this.description = description;
        this.value1 = value1;
        this.value2 = value2;
        this.available = available;
        this.editable = editable;
        this.parentTypologyId = parentTypologyId;
        this.parentTypologyDescription = parentTypologyDescription;
    }

    public AdmTypologyListDto(Long typologyId, String description, String value1, String value2, Boolean available, Boolean editable, Long parentTypologyId, String parentTypologyDescription, String value3) {
        this.typologyId = typologyId;
        this.description = description;
        this.value1 = value1;
        this.value2 = value2;
        this.available = available;
        this.editable = editable;
        this.parentTypologyId = parentTypologyId;
        this.parentTypologyDescription = parentTypologyDescription;
        this.value3 = value3;
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

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Long getParentTypologyId() {
        return parentTypologyId;
    }

    public void setParentTypologyId(Long parentTypologyId) {
        this.parentTypologyId = parentTypologyId;
    }

    public String getParentTypologyDescription() {
        return parentTypologyDescription;
    }

    public void setParentTypologyDescription(String parentTypologyDescription) {
        this.parentTypologyDescription = parentTypologyDescription;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }
}
