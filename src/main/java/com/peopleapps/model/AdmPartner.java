package com.peopleapps.model;

import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.person.AdmPersonListDto;
import com.peopleapps.dto.credit.AdmCreditPartnerDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_partner")
@SequenceGenerator(
        name = "admPartnerSequence",
        sequenceName = "adm_partner_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admPartnerListDto",
        classes = @ConstructorResult(
                targetClass = AdmPartnerListDto.class,
                columns = {
                        @ColumnResult(name = "partner_id", type = Long.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "cui", type = Long.class),
                        @ColumnResult(name = "partner_person_key", type = UUID.class),
                        @ColumnResult(name = "partner_first_name", type = String.class),
                        @ColumnResult(name = "partner_last_name", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "no_boys", type = Long.class),
                        @ColumnResult(name = "no_girls", type = Long.class),
                        @ColumnResult(name = "leader", type = Boolean.class),
                        @ColumnResult(name = "age", type = Long.class),
                        @ColumnResult(name = "person_birthday", type = LocalDate.class),
                        @ColumnResult(name = "person_mail", type = String.class),
                        @ColumnResult(name = "person_name_complete", type = String.class),
                        @ColumnResult(name = "person_phone_id", type = Long.class),
                        @ColumnResult(name = "person_phone", type = Long.class),
                        @ColumnResult(name = "phone_status_id", type = Long.class),
                        @ColumnResult(name = "phone_status_desc", type = String.class),
                        @ColumnResult(name = "phone_type_id", type = Long.class),
                        @ColumnResult(name = "phone_type_desc", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = "admPartnerCreditLDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditPartnerDto.class,
                columns = {
                        @ColumnResult(name = "partner_credit_id", type = Long.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "person_birthday", type = LocalDate.class),
                        @ColumnResult(name = "person_mail", type = String.class),
                        @ColumnResult(name = "person_name_complete", type = String.class),
                        @ColumnResult(name = "person_phone_id", type = Long.class),
                        @ColumnResult(name = "person_phone", type = Long.class),
                        @ColumnResult(name = "phone_status_id", type = Long.class),
                        @ColumnResult(name = "phone_status_desc", type = String.class),
                        @ColumnResult(name = "phone_type_id", type = Long.class),
                        @ColumnResult(name = "phone_type_desc", type = String.class)
                }
        )
)

@Schema(name = "AdmPartner")
@JsonbPropertyOrder({"partnerId", "person", "partner", "createdBy",
        "dateCreated", "status", "noBoys", "noGirls",
        "leader", "age"})
public class AdmPartner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPartnerSequence")
    @NotNull
    @Column(name = "partner_id")
    private Long partnerId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person")
    @DefaultValue("0")
    private AdmPerson person;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner")
    @DefaultValue("0")
    private AdmPerson partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @NotNull
    @Column(name = "no_boys")
    private Long noBoys;

    @NotNull
    @Column(name = "no_girls")
    private Long noGirls;

    @Column(name = "leader")
    @DefaultValue("FALSE")
    private Boolean leader;

    @NotNull
    @Column(name = "age")
    private Long age;

    public AdmPartner() {
    }

    public  AdmPartner(@NotNull Long partnerId, @NotNull AdmPerson person, @NotNull AdmPerson partner, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status, @NotNull Long noBoys, @NotNull Long noGirls, Boolean leader, @NotNull Long age) {
        this.partnerId = partnerId;
        this.person = person;
        this.partner = partner;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.noBoys = noBoys;
        this.noGirls = noGirls;
        this.leader = leader;
        this.age = age;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public AdmPerson getPerson() {
        return person;
    }

    public void setPerson(AdmPerson person) {
        this.person = person;
    }

    public AdmPerson getPartner() {
        return partner;
    }

    public void setPartner(AdmPerson partner) {
        this.partner = partner;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public Long getNoBoys() {
        return noBoys;
    }

    public void setNoBoys(Long noBoys) {
        this.noBoys = noBoys;
    }

    public Long getNoGirls() {
        return noGirls;
    }

    public void setNoGirls(Long noGirls) {
        this.noGirls = noGirls;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
