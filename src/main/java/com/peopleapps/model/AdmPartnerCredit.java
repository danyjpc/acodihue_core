package com.peopleapps.model;


import com.peopleapps.dto.creditLine.AdmCreditLineListDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.partnerCredit.AdmPartnerCreditListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "adm_partner_credit")
@SequenceGenerator(
        name = "admPartnerCreditSequence",
        sequenceName = "adm_partner_credit_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admPartnerCreditListDto",
        classes = @ConstructorResult(
                targetClass = AdmPartnerCreditListDto.class,
                columns = {
                        @ColumnResult(name = "partner_credit_id", type = Long.class),
                        @ColumnResult(name = "credit_id", type = Long.class),
                        @ColumnResult(name = "credit_type_id", type = Long.class),
                        @ColumnResult(name = "credit_type_description", type = String.class),
                        @ColumnResult(name = "calculator_id", type = Long.class),
                        @ColumnResult(name = "no_payments", type = Long.class),
                        @ColumnResult(name = "interest_rate", type = BigDecimal.class),
                        @ColumnResult(name = "credit", type = BigDecimal.class),
                        @ColumnResult(name = "partner_person_key", type = UUID.class),
                        @ColumnResult(name = "partner_first_name", type = String.class),
                        @ColumnResult(name = "partner_middle_name", type = String.class),
                        @ColumnResult(name = "partner_last_name", type = String.class),
                        @ColumnResult(name = "partner_name_complete", type = String.class),
                        @ColumnResult(name = "created_by_person_person_key", type = UUID.class),
                        @ColumnResult(name = "created_by_person_email", type = String.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "partner_birthday", type = LocalDate.class),
                        @ColumnResult(name = "partner_email", type = String.class)
                }
        )
)

@Schema(name = "AdmPartnerCredit")
@JsonbPropertyOrder({"creditLineId", "organization", "description", "dateCreated", "status"})
public class AdmPartnerCredit implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPartnerCreditSequence") //JPA
    @Column(name = "partner_credit_id")
    private Long partnerCreditId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmCredit credit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmPerson person;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    public AdmPartnerCredit() {
    }

    public AdmPartnerCredit(@NotNull Long partnerCreditId, @NotNull AdmCredit credit, @NotNull AdmPerson person, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.partnerCreditId = partnerCreditId;
        this.credit = credit;
        this.person = person;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getPartnerCreditId() {
        return partnerCreditId;
    }

    public void setPartnerCreditId(Long partnerCreditId) {
        this.partnerCreditId = partnerCreditId;
    }

    public AdmCredit getCredit() {
        return credit;
    }

    public void setCredit(AdmCredit credit) {
        this.credit = credit;
    }

    public AdmPerson getPerson() {
        return person;
    }

    public void setPerson(AdmPerson person) {
        this.person = person;
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

    @Override
    public String toString() {
        return "AdmPartnerCredit{" +
                "partnerCreditId=" + partnerCreditId +
                ", credit=" + credit +
                ", person=" + person +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}

