package com.peopleapps.model;

import com.peopleapps.dto.organizationResponsible.AdmOrganizationResponsibleListDto;
import com.peopleapps.dto.person.AdmPersonListDto;
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
@Table(name = "adm_organization_responsible")
@SequenceGenerator(
        name = "admOrganizationResponsibleSequence",
        sequenceName = "adm_organization_responsible_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admOrganizationResponsibleListDto",
        classes = @ConstructorResult(
                targetClass = AdmOrganizationResponsibleListDto.class,
                columns = {
                        @ColumnResult(name = "organization_responsible_id", type = Long.class),
                        @ColumnResult(name = "organization_id", type = Long.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "person_id", type = Long.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "is_responsible", type = Boolean.class)
                }
        )
)
@Schema(name = "AdmOrganizationResponsible")
@JsonbPropertyOrder({"organizationResponsibleId", "organization", "person", "dateCreated"})
public class AdmOrganizationResponsible implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admOrganizationResponsibleSequence")
    @Column(name = "organization_responsible_id")
    private Long organizationResponsibleId;

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
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @DefaultValue(CsnConstants.DATE_EMPTY)
    private LocalDateTime dateCreated;

    //New properties should be added from this point on, so the constructor is not changed
    @NotNull
    @Column(name="is_responsable")
    private Boolean isResponsible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    public AdmOrganizationResponsible() {
    }

    public AdmOrganizationResponsible(@NotNull Long organizationResponsibleId) {
        this.organizationResponsibleId = organizationResponsibleId;
    }

    public AdmOrganizationResponsible(@NotNull Long organizationResponsibleId, @NotNull AdmOrganization organization, @NotNull AdmPerson person) {
        this.organizationResponsibleId = organizationResponsibleId;
        this.organization = organization;
        this.person = person;
    }


    public AdmOrganizationResponsible(@NotNull Long organizationResponsibleId, @NotNull AdmOrganization organization, @NotNull AdmPerson person, @NotNull LocalDateTime dateCreated) {
        this.organizationResponsibleId = organizationResponsibleId;
        this.organization = organization;
        this.person = person;
        this.dateCreated = dateCreated;
    }

    public AdmOrganizationResponsible(@NotNull Long organizationResponsibleId, @NotNull AdmOrganization organization, @NotNull AdmPerson person, @NotNull LocalDateTime dateCreated, @NotNull Boolean isResponsible, @NotNull AdmTypology status) {
        this.organizationResponsibleId = organizationResponsibleId;
        this.organization = organization;
        this.person = person;
        this.dateCreated = dateCreated;
        this.isResponsible = isResponsible;
        this.status = status;
    }

    public Long getOrganizationResponsibleId() {
        return organizationResponsibleId;
    }

    public void setOrganizationResponsibleId(Long organizationResponsibleId) {
        this.organizationResponsibleId = organizationResponsibleId;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsResponsible() {
        return isResponsible;
    }

    public void setIsResponsible(Boolean responsible) {
        isResponsible = responsible;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdmOrganizationResponsible{" +
                "organizationResponsibleId=" + organizationResponsibleId +
                ", organization=" + organization +
                ", person=" + person +
                ", dateCreated=" + dateCreated +
                ", isResponsible=" + isResponsible +
                ", status=" + status +
                '}';
    }
}
