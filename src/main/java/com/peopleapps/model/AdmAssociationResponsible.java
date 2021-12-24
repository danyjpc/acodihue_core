package com.peopleapps.model;


import com.peopleapps.dto.associationResponsible.AdmAssociationResponsibleListDto;
import com.peopleapps.dto.creditLine.AdmCreditLineListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "adm_association_responsible")
@SequenceGenerator(
        name = "admAssociationResponsibleSequence",
        sequenceName = "adm_association_responsible_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admAssociationResponsibleListDto",
        classes = @ConstructorResult(
                targetClass = AdmAssociationResponsibleListDto.class,
                columns = {
                        @ColumnResult(name = "association_responsible_id", type = Long.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "associate_key", type = UUID.class),
                        @ColumnResult(name = "associate_name_complete", type = String.class),
                        @ColumnResult(name = "admission_date", type = LocalDateTime.class),
                        @ColumnResult(name = "discharge_date", type = LocalDateTime.class),
                        @ColumnResult(name = "annotation", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "created_by_person_email", type = String.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "interest_rate", type = Double.class)
                }
        )
)
@Schema(name = "AdmAssociationResponsible")
@JsonbPropertyOrder({"associationResponsibleId", "organization", "person", "admissionDate",
         "dischargeDate", "annotation", "createdBy", "dateCreated", "status"})
public class AdmAssociationResponsible implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAssociationResponsibleSequence") //JPA
    @Column(name = "association_responsible_id")
    private Long associationResponsibleId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmOrganization organization;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmPerson person;

    @NotNull
    @Column(name = "admission_date")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime admissionDate;

    @NotNull
    @Column(name = "discharge_date")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dischargeDate;

    @NotNull
    @Column(name = "annotation")
    private String annotation;

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

    public AdmAssociationResponsible() {
    }

    public AdmAssociationResponsible(@NotNull Long associationResponsibleId, @NotNull AdmOrganization organization, @NotNull AdmPerson person, @NotNull LocalDateTime admissionDate, @NotNull LocalDateTime dischargeDate, @NotNull String annotation, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.associationResponsibleId = associationResponsibleId;
        this.organization = organization;
        this.person = person;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.annotation = annotation;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getAssociationResponsibleId() {
        return associationResponsibleId;
    }

    public void setAssociationResponsibleId(Long associationResponsibleId) {
        this.associationResponsibleId = associationResponsibleId;
    }

    public AdmOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(AdmOrganization organization) {
        this.organization = organization;
    }

    public AdmPerson getPerson() {
        return person;
    }

    public void setPerson(AdmPerson person) {
        this.person = person;
    }

    public LocalDateTime getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDateTime getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
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
        return "AdmAssociationResponsible{" +
                "associationResponsibleId=" + associationResponsibleId +
                ", organization=" + organization +
                ", person=" + person +
                ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate +
                ", annotation='" + annotation + '\'' +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}

