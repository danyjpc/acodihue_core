package com.peopleapps.model;


import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchAccountListDto;
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
@Table(name = "adm_account")
@SequenceGenerator(
        name = "admAccountSequence",
        sequenceName = "adm_account_sequence",
        initialValue = 3,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admAccountListDto",
        classes = @ConstructorResult(
                targetClass = AdmAccountListDto.class,
                columns = {
                        @ColumnResult(name = "account_id", type = Long.class),
                        @ColumnResult(name = "organization_responsible_id", type = Long.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_desc", type = String.class),
                        @ColumnResult(name = "num_account", type = Long.class),
                        @ColumnResult(name = "num_account_order", type = Long.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "balance", type = Double.class),
                        @ColumnResult(name = "account_type_value_2", type = String.class),
                }
        )
)
@SqlResultSetMapping(
        name = "admGlobalSearchAccountDto",
        classes = @ConstructorResult(
                targetClass = AdmGlobalSearchAccountListDto.class,
                columns = {
                        @ColumnResult(name = "account_id", type = Long.class),
                        @ColumnResult(name = "organization_responsible_id", type = Long.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_desc", type = String.class),
                        @ColumnResult(name = "num_account", type = Long.class),
                        @ColumnResult(name = "num_account_order", type = Long.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "balance", type = Double.class),
                        @ColumnResult(name = "account_type_value_2", type = String.class),
                }
        )
)
@Schema(name = "AdmAccount")
@JsonbPropertyOrder({"accountId", "organizationResponsibleId", "accountType", "numAccount",
        "numAccountOrder", "createdBy", "dateCreated", "status"})
public class AdmAccount implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAccountSequence") //JPA
    @Column(name = "account_id")
    private Long accountId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_responsible_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmOrganizationResponsible organizationResponsible;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology accountType;

    @NotNull
    @Column(name = "num_account")
    private Long numAccount;

    @NotNull
    @Column(name = "num_account_order")
    private Long numAccountOrder;

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

    public AdmAccount() {
    }

    public AdmAccount(@NotNull Long accountId, @NotNull AdmOrganizationResponsible organizationResponsible, @NotNull AdmTypology accountType, @NotNull Long numAccount, @NotNull Long numAccountOrder, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.accountId = accountId;
        this.organizationResponsible = organizationResponsible;
        this.accountType = accountType;
        this.numAccount = numAccount;
        this.numAccountOrder = numAccountOrder;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public AdmOrganizationResponsible getOrganizationResponsible() {
        return organizationResponsible;
    }

    public void setOrganizationResponsible(AdmOrganizationResponsible organizationResponsible) {
        this.organizationResponsible = organizationResponsible;
    }

    public AdmTypology getAccountType() {
        return accountType;
    }

    public void setAccountType(AdmTypology accountType) {
        this.accountType = accountType;
    }

    public Long getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(Long numAccount) {
        this.numAccount = numAccount;
    }

    public Long getNumAccountOrder() {
        return numAccountOrder;
    }

    public void setNumAccountOrder(Long numAccountOrder) {
        this.numAccountOrder = numAccountOrder;
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
        return "AdmAccount{" +
                "accountId=" + accountId +
                ", organizationResponsible=" + organizationResponsible +
                ", accountType=" + accountType +
                ", numAccount=" + numAccount +
                ", numAccountOrder=" + numAccountOrder +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
