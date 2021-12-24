package com.peopleapps.model;


import com.peopleapps.dto.phone.AdmPhoneListDto;
import com.peopleapps.dto.user.AdmUserListDto;
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
import java.util.UUID;


@Entity
@Table(name = "adm_phone")
@SequenceGenerator(
        name = "admPhoneSequence",
        sequenceName = "adm_phone_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admPhoneListDto",
        classes = @ConstructorResult(
                targetClass = AdmPhoneListDto.class,
                columns = {
                        @ColumnResult(name = "phone_id", type = Long.class),
                        @ColumnResult(name = "phone_account_id", type = Long.class),
                        @ColumnResult(name = "phone", type = Long.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "type_id", type = Long.class),
                        @ColumnResult(name = "type_description", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "leader", type = Boolean.class)
                }
        )
)
@Schema(name = "AdmPhone")
@JsonbPropertyOrder({"phoneId", "phoneAccount", "phone", "status",
        "type", "createdBy", "dateCreated", "leader"})
public class AdmPhone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPhoneSequence")
    @NotNull
    @Column(name = "phone_id")
    private Long phoneId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_account_id")
    @DefaultValue("0")
    private AdmPhoneAccount phoneAccount;

    @NotNull
    @Column(name = "phone")
    private Long phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @Column(name = "leader")
    @DefaultValue("FALSE")
    private Boolean leader;

    public AdmPhone() {
    }

    public AdmPhone(@NotNull Long phoneId, @NotNull AdmPhoneAccount phoneAccount, @NotNull Long phone, @NotNull AdmTypology status, @NotNull AdmTypology type, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, Boolean leader) {
        this.phoneId = phoneId;
        this.phoneAccount = phoneAccount;
        this.phone = phone;
        this.status = status;
        this.type = type;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.leader = leader;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public AdmPhoneAccount getPhoneAccount() {
        return phoneAccount;
    }

    public void setPhoneAccount(AdmPhoneAccount phoneAccount) {
        this.phoneAccount = phoneAccount;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public AdmTypology getType() {
        return type;
    }

    public void setType(AdmTypology type) {
        this.type = type;
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

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "AdmPhone{" +
                "phoneId=" + phoneId +
                ", phoneAccount=" + phoneAccount +
                ", phone=" + phone +
                ", status=" + status +
                ", type=" + type +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", leader=" + leader +
                '}';
    }
}
