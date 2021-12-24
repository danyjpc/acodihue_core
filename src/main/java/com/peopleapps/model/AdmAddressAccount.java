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
@Table(name = "adm_address_account")
@SequenceGenerator(
        name = "admAddressAccountSequence",
        sequenceName = "adm_address_account_sequence",
        initialValue = 1,
        allocationSize = 1
)
@Schema(name = "AdmAddressAccount")
public class AdmAddressAccount implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAddressAccountSequence")
    @Column(name = "address_account_id")
    private Long addressAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;


    public AdmAddressAccount() {
    }

    public AdmAddressAccount(@NotNull Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }

    public AdmAddressAccount(@NotNull Long addressAccountId, @NotNull LocalDateTime dateCreated) {
        this.addressAccountId = addressAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getAddressAccountId() {
        return addressAccountId;
    }

    public void setAddressAccountId(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmAddressAccount{" +
                "addressAccountId=" + addressAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
