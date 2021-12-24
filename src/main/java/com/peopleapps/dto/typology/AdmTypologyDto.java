package com.peopleapps.dto.typology;


import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.util.List;

@JsonbPropertyOrder({"typologyId", "description", "value1", "value2", "available",
        "editable", "parentTypology", "childTypologies"})
public class AdmTypologyDto {
    private Long typologyId;
    private String description;
    private String value1;
    private String value2;
    private Boolean available;
    private Boolean editable;
    private AdmTypologyDto parentTypology;
    private List<AdmTypologyDto> childTypologies;
    private String value3;

    public AdmTypologyDto() {
    }

    public AdmTypologyDto(Long typologyId, String description) {
        this.typologyId = typologyId;
        this.description = description;
    }

    public AdmTypologyDto(Long typologyId, String description, String value1, String value2) {
        this.typologyId = typologyId;
        this.description = description;
        this.value1 = value1;
        this.value2 = value2;
    }

    public AdmTypologyDto(Long typologyId, String description, String value1, String value2, String value3) {
        this.typologyId = typologyId;
        this.description = description;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public AdmTypologyDto(Long typologyId, String description, String value1, String value2, Boolean available, Boolean editable, AdmTypologyDto parentTypology) {
        this.typologyId = typologyId;
        this.description = description;
        this.value1 = value1;
        this.value2 = value2;
        this.available = available;
        this.editable = editable;
        this.parentTypology = parentTypology;
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

    public AdmTypologyDto getParentTypology() {
        return parentTypology;
    }

    public void setParentTypology(AdmTypologyDto parentTypology) {
        this.parentTypology = parentTypology;
    }

    public List<AdmTypologyDto> getChildTypologies() {
        return childTypologies;
    }

    public void setChildTypologies(List<AdmTypologyDto> childTypologies) {
        this.childTypologies = childTypologies;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }
}
