package com.peopleapps.model;


import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_phone_account")
@SequenceGenerator(
        name = "admPhoneAccountSequence",
        sequenceName = "adm_phone_account_sequence",
        initialValue = 1,
        allocationSize = 1
)
@Schema(name = "AdmPhoneAccount")
public class AdmPhoneAccount implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPhoneAccountSequence")
    @Column(name = "phone_account_id")
    private Long phoneAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmPhoneAccount() {
    }

    public AdmPhoneAccount(@NotNull Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }

    public AdmPhoneAccount(@NotNull Long phoneAccountId, @NotNull LocalDateTime dateCreated) {
        this.phoneAccountId = phoneAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getPhoneAccountId() {
        return phoneAccountId;
    }

    public void setPhoneAccountId(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmPhoneAccount{" +
                "phoneAccountId=" + phoneAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
