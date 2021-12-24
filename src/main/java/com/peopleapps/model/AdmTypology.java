package com.peopleapps.model;


import com.peopleapps.dto.typology.AdmTypologyListDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//mapping from nativeQuery result
//Column names are provided, respecting AdmTypologyListDto constructor parameter order
@SqlResultSetMapping(
        name = "admTypologyListDto",
        classes = @ConstructorResult(
                targetClass = AdmTypologyListDto.class,
                columns = {
                        @ColumnResult(name = "typology_id", type = Long.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "value_1", type = String.class),
                        @ColumnResult(name = "value_2", type = String.class),
                        @ColumnResult(name = "available", type = Boolean.class),
                        @ColumnResult(name = "editable", type = Boolean.class),
                        @ColumnResult(name = "parent_typology_id", type = Long.class),
                        @ColumnResult(name = "parent_typology_description", type = String.class),
                        @ColumnResult(name = "value_3", type = String.class),
                }
        )
)
@Entity
@Table(name = "adm_typology")
@SequenceGenerator(
        name = "admTypologySequence",
        sequenceName = "adm_typology_sequence",
        initialValue = 3,
        allocationSize = 1
)
@Schema(name = "AdmTypology")
@JsonbPropertyOrder({"typologyId", "description", "parentTypology", "value1",
        "value2", "available", "editable", "admChildTypologies"})
public class AdmTypology implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admTypologySequence")
    @Column(name = "typology_id")
    private Long typologyId;

    @Column(name = "description")
    @NotNull
    @Size(max = 200)
    @DefaultValue("%")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_typology_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology parentTypology;

    @Column(name = "value_1")
    @NotNull
    @Size(max = 100)
    private String value1;

    @Column(name = "value_2")
    @NotNull
    @Size(max = 100)
    private String value2;

    @Column(name = "available")
    @NotNull
    @DefaultValue("FALSE")
    private Boolean available;

    @Column(name = "editable")
    @NotNull
    @DefaultValue("FALSE")
    private Boolean editable;

    @Column(name = "value_3")
    @NotNull
    @Size(max = 100)
    private String value3;

    public AdmTypology() {
    }

    public AdmTypology(Long typologyId) {
        this.typologyId = typologyId;
    }

    public AdmTypology(Long typologyId, @NotNull @Size(max = 200) String description) {
        this.typologyId = typologyId;
        this.description = description;
    }

    public AdmTypology(Long typologyId, @NotNull @Size(max = 200) String description, AdmTypology parentTypology, @NotNull @Size(max = 100) String value1, @NotNull @Size(max = 100) String value2, @NotNull Boolean available, @NotNull Boolean editable) {
        this.typologyId = typologyId;
        this.description = description;
        this.parentTypology = parentTypology;
        this.value1 = value1;
        this.value2 = value2;
        this.available = available;
        this.editable = editable;
    }

    public AdmTypology(Long typologyId, @NotNull @Size(max = 200) String description, AdmTypology parentTypology, @NotNull @Size(max = 100) String value1, @NotNull @Size(max = 100) String value2, @NotNull Boolean available, @NotNull Boolean editable, @NotNull @Size(max = 100) String value3) {
        this.typologyId = typologyId;
        this.description = description;
        this.parentTypology = parentTypology;
        this.value1 = value1;
        this.value2 = value2;
        this.available = available;
        this.editable = editable;
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

    public AdmTypology getParentTypology() {
        return parentTypology;
    }

    public void setParentTypology(AdmTypology parentTypology) {
        this.parentTypology = parentTypology;
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

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    @Override
    public String toString() {
        return "AdmTypology{" +
                "typologyId=" + typologyId +
                ", description='" + description + '\'' +
                ", parentTypology=" + parentTypology +
                ", value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", available=" + available +
                ", editable=" + editable +
                ", value3='" + value3 + '\'' +
                '}';
    }
}

