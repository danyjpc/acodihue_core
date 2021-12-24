package com.peopleapps.model;

import com.peopleapps.dto.phone.AdmPhoneListDto;
import com.peopleapps.dto.subOrganization.AdmSubOrganizationListDto;
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
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_sub_organization")
@SequenceGenerator(
        name = "admSubOrganizationSequence",
        sequenceName = "adm_sub_organization_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admSubOrganizationListDto",
        classes = @ConstructorResult(
                targetClass = AdmSubOrganizationListDto.class,
                columns = {
                        @ColumnResult(name = "sub_organization_id", type = Long.class),
                        @ColumnResult(name = "organization_id", type = Long.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial_name", type = String.class),
                        @ColumnResult(name = "organization_child_id", type = Long.class),
                        @ColumnResult(name = "organization_child_name", type = String.class),
                        @ColumnResult(name = "organization_child_commercial_name", type = String.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "created_by_id", type = Long.class)
                }
        )
)
@Schema(name = "AdmSubOrganization")
@JsonbPropertyOrder({"subOrganizationId", "organization", "organizationChild", "dateCreated",
        "createdBy"})
public class AdmSubOrganization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admSubOrganizationSequence")
    @NotNull
    @Column(name = "sub_organization_id")
    private Long subOrganizationId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @DefaultValue("0")
    private AdmOrganization organization;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_child")
    @DefaultValue("0")
    private AdmOrganization organizationChild;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    public AdmSubOrganization() {
    }

    public AdmSubOrganization(@NotNull Long subOrganizationId, @NotNull AdmOrganization organization, @NotNull AdmOrganization organizationChild, @NotNull LocalDateTime dateCreated, @NotNull AdmUser createdBy) {
        this.subOrganizationId = subOrganizationId;
        this.organization = organization;
        this.organizationChild = organizationChild;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
    }

    public Long getSubOrganizationId() {
        return subOrganizationId;
    }

    public void setSubOrganizationId(Long subOrganizationId) {
        this.subOrganizationId = subOrganizationId;
    }

    public AdmOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(AdmOrganization organization) {
        this.organization = organization;
    }

    public AdmOrganization getOrganizationChild() {
        return organizationChild;
    }

    public void setOrganizationChild(AdmOrganization organizationChild) {
        this.organizationChild = organizationChild;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "AdmSubOrganization{" +
                "subOrganizationId=" + subOrganizationId +
                ", organization=" + organization +
                ", organizationChild=" + organizationChild +
                ", dateCreated=" + dateCreated +
                ", createdBy=" + createdBy +
                '}';
    }
}
