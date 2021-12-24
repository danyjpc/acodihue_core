package com.peopleapps.model;

import com.peopleapps.dto.beneficiary.AdmBeneficiaryListDto;
import com.peopleapps.dto.credit.AdmCreditDocDto;
import com.peopleapps.dto.credit.AdmCreditListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_credit")
@SequenceGenerator(
        name = "admCreditSequence",
        sequenceName = "adm_credit_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admCreditListDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditListDto.class,
                columns = {
                        @ColumnResult(name = "credit_id", type = Long.class),
                        @ColumnResult(name = "no_year", type = Long.class),
                        @ColumnResult(name = "no_reference", type = Long.class),
                        @ColumnResult(name = "address_account_id", type = Long.class),
                        @ColumnResult(name = "reference_account_id", type = Long.class),
                        @ColumnResult(name = "document_account_id", type = Long.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "activity_account_id", type = Long.class),
                        @ColumnResult(name = "internal_code", type = String.class),

                        @ColumnResult(name = "estate_date", type = LocalDateTime.class),
                        @ColumnResult(name = "status_operated_id", type = Long.class),
                        @ColumnResult(name = "status_operated_description", type = String.class),
                        @ColumnResult(name = "operated_by_email", type = String.class),
                        @ColumnResult(name = "operated_by_person_key", type = UUID.class),
                        @ColumnResult(name = "annotation", type = String.class),

                        @ColumnResult(name = "organization_responsible_id", type = Long.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "associate_person_key", type = UUID.class),
                        @ColumnResult(name = "associate_first_name", type = String.class),
                        @ColumnResult(name = "associate_last_name", type = String.class),
                        @ColumnResult(name = "associate_name_complete", type = String.class),

                        //profession, occupation,
                        @ColumnResult(name = "profession_id", type = Long.class),
                        @ColumnResult(name = "profession_description", type = String.class),
                        @ColumnResult(name = "occupation_id", type = Long.class),
                        @ColumnResult(name = "occupation_description", type = String.class),
                        @ColumnResult(name = "own_house", type = Boolean.class),
                        @ColumnResult(name = "calculator_id", type = Long.class),
                        @ColumnResult(name = "credit_key", type = UUID.class),
                }
        )
)

@SqlResultSetMapping(
        name = "admCreditDocDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditDocDto.class,
                columns = {
                        @ColumnResult(name="creditId", type = Long.class),
                        @ColumnResult(name="internal_code", type = String.class),
                        @ColumnResult(name= "calculator_id", type = Long.class),
                        @ColumnResult(name = "associateKey", type = UUID.class),
                        @ColumnResult(name="person_id", type = Long.class),
                        @ColumnResult(name = "name_complete", type = String.class),
                        @ColumnResult(name="cui", type = Long.class),
                        @ColumnResult(name="nit", type = String.class),
                        @ColumnResult(name = "birthday", type = LocalDate.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name="no_associate", type = Long.class),
                        @ColumnResult(name = "own_house", type = Boolean.class),
                        @ColumnResult(name="marital_status", type = String.class),
                        @ColumnResult(name="phone", type = Long.class),
                        @ColumnResult(name="address", type = String.class),
                        @ColumnResult(name="statedp", type = String.class),
                        @ColumnResult(name="city", type = String.class),
                        @ColumnResult(name="amount", type = Double.class),
                        @ColumnResult(name="no_payments", type = Long.class),
                        @ColumnResult(name="interest_rate", type = Double.class),
                        @ColumnResult(name="interest_final", type = Double.class),
                        @ColumnResult(name="destiny", type = String.class),
                        @ColumnResult(name="fiduciary", type = Boolean.class),
                }
        )
)

@Schema(name = "AdmCredit")
@JsonbPropertyOrder({"creditId", "noYear", "noReference", "addressAccount",
        "referenceAccount", "documentAccount", "createdBy", "dateCreated", "status"})
public class AdmCredit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admCreditSequence")
    @NotNull
    @Column(name = "credit_id")
    private Long creditId;

    @NotNull
    @Column(name = "no_year")
    private Long noYear;

    @NotNull
    @Column(name = "no_reference")
    private Long noReference;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmAddressAccount addressAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reference_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmReferenceAccount referenceAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmDocumentAccount documentAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    //TODO check future relationships

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmActivityAccount activityAccount;

    @NotNull
    @Column(name = "internal_code")
    private String internalCode;

    @Column(name = "estate_date")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime estateDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_operated")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology statusOperated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operated_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser operatedBy;

    @NotNull
    @Column(name = "annotation")
    private String annotation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_responsible_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmOrganizationResponsible organizationResponsible;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calculator_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmCreditCalculator calculator;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profession_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology profession;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "occupation_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology occupation;

    @NotNull
    @Column(name = "own_house")
    private Boolean ownHouse;

    @Column(name = "credit_key")
    @Convert("uuidConverter")
    private UUID creditKey;


    public AdmCredit() {
    }

    public AdmCredit(Long creditId) {
        this.creditId = creditId;
    }

    public AdmCredit(Long creditId, Long noYear, Long noReference, AdmAddressAccount addressAccount, AdmReferenceAccount referenceAccount, AdmDocumentAccount documentAccount, AdmUser createdBy, LocalDateTime dateCreated, AdmTypology status, AdmActivityAccount activityAccount, String internalCode, LocalDateTime estateDate, AdmTypology statusOperated, AdmUser operatedBy, String annotation, AdmOrganizationResponsible organizationResponsible, AdmCreditCalculator calculator, AdmTypology profession, AdmTypology occupation, Boolean ownHouse, UUID creditKey) {
        this.creditId = creditId;
        this.noYear = noYear;
        this.noReference = noReference;
        this.addressAccount = addressAccount;
        this.referenceAccount = referenceAccount;
        this.documentAccount = documentAccount;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.activityAccount = activityAccount;
        this.internalCode = internalCode;
        this.estateDate = estateDate;
        this.statusOperated = statusOperated;
        this.operatedBy = operatedBy;
        this.annotation = annotation;
        this.organizationResponsible = organizationResponsible;
        this.calculator = calculator;
        this.profession = profession;
        this.occupation = occupation;
        this.ownHouse = ownHouse;
        this.creditKey = creditKey;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getNoYear() {
        return noYear;
    }

    public void setNoYear(Long noYear) {
        this.noYear = noYear;
    }

    public Long getNoReference() {
        return noReference;
    }

    public void setNoReference(Long noReference) {
        this.noReference = noReference;
    }

    public AdmAddressAccount getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccount addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmReferenceAccount getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(AdmReferenceAccount referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public AdmDocumentAccount getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccount documentAccount) {
        this.documentAccount = documentAccount;
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

    public AdmActivityAccount getActivityAccount() {
        return activityAccount;
    }

    public void setActivityAccount(AdmActivityAccount activityAccount) {
        this.activityAccount = activityAccount;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public LocalDateTime getEstateDate() {
        return estateDate;
    }

    public void setEstateDate(LocalDateTime estateDate) {
        this.estateDate = estateDate;
    }

    public AdmTypology getStatusOperated() {
        return statusOperated;
    }

    public void setStatusOperated(AdmTypology statusOperated) {
        this.statusOperated = statusOperated;
    }

    public AdmUser getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(AdmUser operatedBy) {
        this.operatedBy = operatedBy;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public AdmOrganizationResponsible getOrganizationResponsible() {
        return organizationResponsible;
    }

    public void setOrganizationResponsible(AdmOrganizationResponsible organizationResponsible) {
        this.organizationResponsible = organizationResponsible;
    }

    public AdmCreditCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(AdmCreditCalculator calculator) {
        this.calculator = calculator;
    }

    public AdmTypology getProfession() {
        return profession;
    }

    public void setProfession(AdmTypology profession) {
        this.profession = profession;
    }

    public AdmTypology getOccupation() {
        return occupation;
    }

    public void setOccupation(AdmTypology occupation) {
        this.occupation = occupation;
    }

    public Boolean getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(Boolean ownHouse) {
        this.ownHouse = ownHouse;
    }

    public UUID getCreditKey() {
        return creditKey;
    }

    public void setCreditKey(UUID creditKey) {
        this.creditKey = creditKey;
    }
}
