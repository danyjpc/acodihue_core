package com.peopleapps.model;



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
@Table(name = "adm_credit_line")
@SequenceGenerator(
        name = "admCreditLineSequence",
        sequenceName = "adm_credit_line_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admCreditLineListDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditLineListDto.class,
                columns = {
                        @ColumnResult(name = "credit_line_id", type = Long.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class)
                }
        )
)
@Schema(name = "AdmCreditLine")
@JsonbPropertyOrder({"creditLineId", "organization", "description", "dateCreated", "status"})
public class AdmCreditLine implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admCreditLineSequence") //JPA
    @Column(name = "credit_line_id")
    private Long creditLineId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmOrganization organization;

    @NotNull
    @Column(name = "description")
    private String description;

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

    public AdmCreditLine() {
    }

    public AdmCreditLine(@NotNull Long creditLineId, @NotNull AdmOrganization organization, @NotNull String description, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.creditLineId = creditLineId;
        this.organization = organization;
        this.description = description;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getCreditLineId() {
        return creditLineId;
    }

    public void setCreditLineId(Long creditLineId) {
        this.creditLineId = creditLineId;
    }

    public AdmOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(AdmOrganization organization) {
        this.organization = organization;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AdmCreditLine{" +
                "creditLineId=" + creditLineId +
                ", organization=" + organization +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}

