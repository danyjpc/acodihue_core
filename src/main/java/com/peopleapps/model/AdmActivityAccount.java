package com.peopleapps.model;

import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_activity_account")
@SequenceGenerator(
        name = "admActivityAccountSequence",
        sequenceName = "adm_activity_account_sequence",
        initialValue = 1,
        allocationSize = 1
)
@Schema(name = "AdmActivityAccount")
public class AdmActivityAccount {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admActivityAccountSequence")
    @Column(name = "activity_account_id")
    private Long activityAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmActivityAccount() {
    }

    public AdmActivityAccount(Long activityAccountId) {
        this.activityAccountId = activityAccountId;
    }

    public AdmActivityAccount(Long activityAccountId, LocalDateTime dateCreated) {
        this.activityAccountId = activityAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getActivityAccountId() {
        return activityAccountId;
    }

    public void setActivityAccountId(Long activityAccountId) {
        this.activityAccountId = activityAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmActivityAccount{" +
                "activityAccountId=" + activityAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}

