package com.peopleapps.model;

import com.peopleapps.dto.inputs.agency.user.AdmAgencyResponsibleListDto;
import com.peopleapps.dto.organization.AdmAgencyListDto;
import com.peopleapps.dto.organization.AdmAssociationsListDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_organization")
@SequenceGenerator(
        name = "admOrganizationSequence",
        sequenceName = "adm_organization_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "admAgencyListDto",
                classes = @ConstructorResult(
                        targetClass = AdmAgencyListDto.class,
                        columns = {
                                @ColumnResult(name = "organization_id", type = Long.class),
                                @ColumnResult(name = "organization_name", type = String.class),
                                @ColumnResult(name = "organization_commercial", type = String.class),
                                @ColumnResult(name = "address_account_id", type = Long.class),
                                @ColumnResult(name = "phone_account_id", type = Long.class),
                                @ColumnResult(name = "document_account_id", type = Long.class),
                                @ColumnResult(name = "sector_id", type = Long.class),
                                @ColumnResult(name = "sector_description", type = String.class),
                                @ColumnResult(name = "category_id", type = Long.class),
                                @ColumnResult(name = "category_description", type = String.class),
                                @ColumnResult(name = "date_created", type = LocalDateTime.class),
                                @ColumnResult(name = "organization_key", type = UUID.class),
                                @ColumnResult(name = "tax_code", type = String.class),
                                @ColumnResult(name = "is_agency", type = Boolean.class),
                                @ColumnResult(name = "is_society", type = Boolean.class),
                                @ColumnResult(name = "is_organization", type = Boolean.class),
                                @ColumnResult(name = "entry_contribution", type = Double.class),
                                @ColumnResult(name = "entry_fee", type = Double.class),
                                @ColumnResult(name = "status_id", type = Long.class),
                                @ColumnResult(name = "status_desc", type = String.class),
                                @ColumnResult(name = "parent_organization_id", type = Long.class),
                                @ColumnResult(name = "parent_organization_name", type = String.class),
                                @ColumnResult(name = "parent_organization_key", type = UUID.class),
                                @ColumnResult(name = "created_user_id", type = Long.class),
                                @ColumnResult(name = "created_person_id", type = Long.class),
                                @ColumnResult(name = "created_person_first_name", type = String.class),
                                @ColumnResult(name = "created_person_last_name", type = String.class),
                                @ColumnResult(name = "created_person_key", type = UUID.class),
                                @ColumnResult(name = "phone_id", type = Long.class),
                                @ColumnResult(name = "phone", type = Long.class),
                                @ColumnResult(name = "phone_agency_type_id", type = Long.class),
                                @ColumnResult(name = "phone_agency_type_desc", type = String.class),
                                @ColumnResult(name = "address_id", type = Long.class),
                                @ColumnResult(name = "address", type = String.class),
                                @ColumnResult(name = "address_agency_type_id", type = Long.class),
                                @ColumnResult(name = "address_agency_type_desc", type = String.class),
                                @ColumnResult(name = "address_agency_country_id", type = Long.class),
                                @ColumnResult(name = "address_agency_country_desc", type = String.class),
                                @ColumnResult(name = "address_agency_state_id", type = Long.class),
                                @ColumnResult(name = "address_agency_state_desc", type = String.class),
                                @ColumnResult(name = "address_agency_city_id", type = Long.class),
                                @ColumnResult(name = "address_agency_city_desc", type = String.class),
                                @ColumnResult(name = "address_agency_zone_id", type = Long.class),
                                @ColumnResult(name = "address_agency_zone_desc", type = String.class),
                                @ColumnResult(name = "internal_code", type = String.class)
                        }
                )

        ),
        @SqlResultSetMapping(
                name = "admAssociationListDto",
                classes = @ConstructorResult(
                        targetClass = AdmAssociationsListDto.class,
                        columns = {
                                @ColumnResult(name = "organization_id", type = Long.class),
                                @ColumnResult(name = "organization_name", type = String.class),
                                @ColumnResult(name = "organization_commercial", type = String.class),
                                @ColumnResult(name = "address_account_id", type = Long.class),
                                @ColumnResult(name = "phone_account_id", type = Long.class),
                                @ColumnResult(name = "document_account_id", type = Long.class),
                                @ColumnResult(name = "sector_id", type = Long.class),
                                @ColumnResult(name = "sector_description", type = String.class),
                                @ColumnResult(name = "category_id", type = Long.class),
                                @ColumnResult(name = "category_description", type = String.class),
                                @ColumnResult(name = "date_created", type = LocalDateTime.class),
                                @ColumnResult(name = "organization_key", type = UUID.class),
                                @ColumnResult(name = "tax_code", type = String.class),
                                @ColumnResult(name = "is_agency", type = Boolean.class),
                                @ColumnResult(name = "is_society", type = Boolean.class),
                                @ColumnResult(name = "is_organization", type = Boolean.class),
                                @ColumnResult(name = "entry_contribution", type = Double.class),
                                @ColumnResult(name = "entry_fee", type = Double.class),
                                @ColumnResult(name = "is_association", type = Boolean.class),
                                @ColumnResult(name = "interest_rate", type = Double.class),
                                @ColumnResult(name = "status_id", type = Long.class),
                                @ColumnResult(name = "status_desc", type = String.class),
                                @ColumnResult(name = "created_user_id", type = Long.class),
                                @ColumnResult(name = "created_person_id", type = Long.class),
                                @ColumnResult(name = "created_person_first_name", type = String.class),
                                @ColumnResult(name = "created_person_last_name", type = String.class),
                                @ColumnResult(name = "created_person_key", type = UUID.class),
                                @ColumnResult(name = "phone_id", type = Long.class),
                                @ColumnResult(name = "phone", type = Long.class),
                                @ColumnResult(name = "phone_association_type_id", type = Long.class),
                                @ColumnResult(name = "phone_association_type_desc", type = String.class),
                                @ColumnResult(name = "address_id", type = Long.class),
                                @ColumnResult(name = "address", type = String.class),
                                @ColumnResult(name = "address_association_type_id", type = Long.class),
                                @ColumnResult(name = "address_association_type_desc", type = String.class),
                                @ColumnResult(name = "address_association_country_id", type = Long.class),
                                @ColumnResult(name = "address_association_country_desc", type = String.class),
                                @ColumnResult(name = "address_association_state_id", type = Long.class),
                                @ColumnResult(name = "address_association_state_desc", type = String.class),
                                @ColumnResult(name = "address_association_city_id", type = Long.class),
                                @ColumnResult(name = "address_association_city_desc", type = String.class),
                                @ColumnResult(name = "address_association_zone_id", type = Long.class),
                                @ColumnResult(name = "address_association_zone_desc", type = String.class),
                                @ColumnResult(name = "contact_name_complete", type = String.class),
                                @ColumnResult(name = "contact_person_key", type = UUID.class),
                                @ColumnResult(name = "internal_code", type = String.class)
                        }
                )
        ),
        @SqlResultSetMapping(
                name = "admAgencyResponsiblesDto",
                classes = @ConstructorResult(
                        targetClass = AdmAgencyResponsibleListDto.class,
                        columns = {
                                @ColumnResult(name ="organization_key", type = UUID.class),
                                @ColumnResult(name ="organization_name", type = String.class),
                                @ColumnResult(name ="agency_status_id", type = Long.class),
                                @ColumnResult(name ="agency_status_desc", type = String.class),
                                @ColumnResult(name ="person_key", type = UUID.class),
                                @ColumnResult(name ="person_name_complete", type = String.class),
                                @ColumnResult(name ="organization_responsible_is_responsible", type = Boolean.class),
                                @ColumnResult(name ="organization_responsible_status_id", type = Long.class),
                                @ColumnResult(name ="organization_responsible_status_desc", type = String.class),
                                @ColumnResult(name ="user_date_created", type = LocalDateTime.class),
                                @ColumnResult(name ="user_role_id", type = Long.class),
                                @ColumnResult(name ="user_role_desc", type = String.class),
                                @ColumnResult(name ="person_email", type = String.class),
                                @ColumnResult(name ="user_status_id", type = Long.class),
                                @ColumnResult(name ="user_status_desc", type = String.class)
                        }
                )
        )
})


@Schema(name = "AdmOrganization")
@Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
@JsonbPropertyOrder({"organizationId", "organizationName", "organizationCommercial", "addressAccount",
        "phoneAccount", "documentAccount", "sector", "category", "dateCreated",
        "organizationKey", "taxCode", "isAgency", "isSociety", "isOrganization", "status"
        , "entryContribution", "entryFee", "parent", "createdBy"})
public class AdmOrganization implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "admOrganizationSequence")
    @Column(name = "organization_id")
    private Long organizationId;

    @NotNull
    @Column(name = "organization_name")
    @DefaultValue("%")
    @Size(max = 255)
    private String organizationName;

    @NotNull
    @Column(name = "organization_commercial")
    @DefaultValue("%")
    @Size(max = 255)
    private String organizationCommercial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_account_id")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    @DefaultValue("0")
    private AdmAddressAccount addressAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_account_id")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    @DefaultValue("0")
    private AdmPhoneAccount phoneAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_account_id")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    @DefaultValue("0")
    private AdmDocumentAccount documentAccount;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sector")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology sector;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology category;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @Column(name = "organization_key")
    @Convert("uuidConverter")
    private UUID organizationKey;

    @NotNull
    @Size(max = 15)
    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "is_agency")
    @NotNull
    private Boolean isAgency;

    @Column(name = "is_society")
    @NotNull
    private Boolean isSociety;

    @Column(name = "is_organization")
    @NotNull
    private Boolean isOrganization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @Column(name = "entry_contribution")
    @NotNull
    private Double entryContribution;

    @Column(name = "entry_fee")
    @NotNull
    private Double entryFee;

    @Column(name = "interest_rate")
    @NotNull
    private Double interestRate;

    @Column(name = "is_association")
    @NotNull
    private Boolean isAssociation;

    @Transient
    private AdmOrganization parent;

    @Transient
    private AdmUser createdBy;

    @NotNull
    @Column(name = "internal_code")
    @DefaultValue("S/D")
    private String internalCode;

    public AdmOrganization() {

    }

    public AdmOrganization(@NotNull Long organizationId, @NotNull @Size(max = 255) String organizationName) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    public AdmOrganization(@NotNull Long organizationId, @NotNull @Size(max = 255) String organizationName, UUID organizationKey) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationKey = organizationKey;
    }


    public AdmOrganization(@NotNull Long organizationId, @NotNull @Size(max = 255) String organizationName, @NotNull @Size(max = 255) String organizationCommercial, @NotNull AdmAddressAccount addressAccount, @NotNull AdmPhoneAccount phoneAccount, @NotNull AdmDocumentAccount documentAccount, @NotNull AdmTypology sector, @NotNull AdmTypology category, @NotNull LocalDateTime dateCreated, UUID organizationKey, @NotNull @Size(max = 15) String taxCode, @NotNull Boolean isAgency, @NotNull Boolean isSociety, @NotNull Boolean isOrganization, @NotNull AdmTypology status, @NotNull Double entryContribution, @NotNull Double entryFee, @NotNull Double interestRate, @NotNull Boolean isAssociation, AdmOrganization parent, AdmUser createdBy, @NotNull String internalCode) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.addressAccount = addressAccount;
        this.phoneAccount = phoneAccount;
        this.documentAccount = documentAccount;
        this.sector = sector;
        this.category = category;
        this.dateCreated = dateCreated;
        this.organizationKey = organizationKey;
        this.taxCode = taxCode;
        this.isAgency = isAgency;
        this.isSociety = isSociety;
        this.isOrganization = isOrganization;
        this.status = status;
        this.entryContribution = entryContribution;
        this.entryFee = entryFee;
        this.interestRate = interestRate;
        this.isAssociation = isAssociation;
        this.parent = parent;
        this.createdBy = createdBy;
        this.internalCode = internalCode;
    }

    public AdmOrganization(@NotNull Long organizationId, @NotNull @Size(max = 255) String organizationName, @NotNull @Size(max = 255) String organizationCommercial, @NotNull AdmAddressAccount addressAccount, @NotNull AdmPhoneAccount phoneAccount, @NotNull AdmDocumentAccount documentAccount, @NotNull AdmTypology sector, @NotNull AdmTypology category, @NotNull LocalDateTime dateCreated, UUID organizationKey, @NotNull @Size(max = 15) String taxCode, @NotNull Boolean isAgency, @NotNull Boolean isSociety, @NotNull Boolean isOrganization, @NotNull AdmTypology status, @NotNull Double entryContribution, @NotNull Double entryFee, @NotNull Double interestRate, @NotNull Boolean isAssociation, @NotNull String internalCode) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.addressAccount = addressAccount;
        this.phoneAccount = phoneAccount;
        this.documentAccount = documentAccount;
        this.sector = sector;
        this.category = category;
        this.dateCreated = dateCreated;
        this.organizationKey = organizationKey;
        this.taxCode = taxCode;
        this.isAgency = isAgency;
        this.isSociety = isSociety;
        this.isOrganization = isOrganization;
        this.status = status;
        this.entryContribution = entryContribution;
        this.entryFee = entryFee;
        this.interestRate = interestRate;
        this.isAssociation = isAssociation;
        this.internalCode = internalCode;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCommercial() {
        return organizationCommercial;
    }

    public void setOrganizationCommercial(String organizationCommercial) {
        this.organizationCommercial = organizationCommercial;
    }

    public AdmAddressAccount getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccount addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmPhoneAccount getPhoneAccount() {
        return phoneAccount;
    }

    public void setPhoneAccount(AdmPhoneAccount phoneAccount) {
        this.phoneAccount = phoneAccount;
    }

    public AdmDocumentAccount getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccount documentAccount) {
        this.documentAccount = documentAccount;
    }

    public AdmTypology getSector() {
        return sector;
    }

    public void setSector(AdmTypology sector) {
        this.sector = sector;
    }

    public AdmTypology getCategory() {
        return category;
    }

    public void setCategory(AdmTypology category) {
        this.category = category;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UUID getOrganizationKey() {
        return organizationKey;
    }

    public void setOrganizationKey(UUID organizationKey) {
        this.organizationKey = organizationKey;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Boolean getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(Boolean agency) {
        isAgency = agency;
    }

    public Boolean getIsSociety() {
        return isSociety;
    }

    public void setIsSociety(Boolean society) {
        isSociety = society;
    }

    public Boolean getIsOrganization() {
        return isOrganization;
    }

    public void setIsOrganization(Boolean organization) {
        isOrganization = organization;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public Double getEntryContribution() {
        return entryContribution;
    }

    public void setEntryContribution(Double entryContribution) {
        this.entryContribution = entryContribution;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public AdmOrganization getParent() {
        return parent;
    }

    public void setParent(AdmOrganization parent) {
        this.parent = parent;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getIsAssociation() {
        return isAssociation;
    }

    public void setIsAssociation(Boolean association) {
        isAssociation = association;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    @Override
    public String toString() {
        return "AdmOrganization{" +
                "organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", organizationCommercial='" + organizationCommercial + '\'' +
                ", addressAccount=" + addressAccount +
                ", phoneAccount=" + phoneAccount +
                ", documentAccount=" + documentAccount +
                ", sector=" + sector +
                ", category=" + category +
                ", dateCreated=" + dateCreated +
                ", organizationKey=" + organizationKey +
                ", taxCode='" + taxCode + '\'' +
                ", isAgency=" + isAgency +
                ", isSociety=" + isSociety +
                ", isOrganization=" + isOrganization +
                ", status=" + status +
                ", entryContribution=" + entryContribution +
                ", entryFee=" + entryFee +
                ", interestRate=" + interestRate +
                ", isAssociation=" + isAssociation +
                ", parent=" + parent +
                ", createdBy=" + createdBy +
                ", internalCode='" + internalCode + '\'' +
                '}';
    }
}
