package com.peopleapps.model;

import com.peopleapps.util.CsnConstants;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_preinscription")
@SequenceGenerator(
        name = "admPreinscriptionSequence",
        sequenceName = "adm_preinscription_sequence",
        initialValue = 1,
        allocationSize = 1
)
public class AdmPreinscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPreinscriptionSequence")
    @Column(name = "preinscription_id")
    private Long preinsciptionId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_responsible_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmOrganizationResponsible organizationResponsible;

    @NotNull
    @Column(name = "membership_number")
    private Long membershipNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membership_status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology membershipStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorized_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser authorizedBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rejected_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser rejectedBy;

    @Column(name = "date_approved")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateApproved;

    @Column(name = "date_rejected")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateRejected;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmPreinscription() {
    }

    public AdmPreinscription(Long preinsciptionId, @NotNull AdmOrganizationResponsible organizationResponsible, @NotNull Long membershipNumber, @NotNull AdmTypology status, @NotNull AdmTypology membershipStatus, AdmUser authorizedBy, @NotNull AdmUser createdBy, AdmUser rejectedBy, LocalDateTime dateApproved, LocalDateTime dateRejected, @NotNull LocalDateTime dateCreated) {
        this.preinsciptionId = preinsciptionId;
        this.organizationResponsible = organizationResponsible;
        this.membershipNumber = membershipNumber;
        this.status = status;
        this.membershipStatus = membershipStatus;
        this.authorizedBy = authorizedBy;
        this.createdBy = createdBy;
        this.rejectedBy = rejectedBy;
        this.dateApproved = dateApproved;
        this.dateRejected = dateRejected;
        this.dateCreated = dateCreated;
    }

    public Long getPreinsciptionId() {
        return preinsciptionId;
    }

    public void setPreinsciptionId(Long preinsciptionId) {
        this.preinsciptionId = preinsciptionId;
    }

    public AdmOrganizationResponsible getOrganizationResponsible() {
        return organizationResponsible;
    }

    public void setOrganizationResponsible(AdmOrganizationResponsible organizationResponsible) {
        this.organizationResponsible = organizationResponsible;
    }

    public Long getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(Long membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public AdmTypology getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(AdmTypology membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public AdmUser getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(AdmUser authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public AdmUser getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(AdmUser rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public LocalDateTime getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(LocalDateTime dateApproved) {
        this.dateApproved = dateApproved;
    }

    public LocalDateTime getDateRejected() {
        return dateRejected;
    }

    public void setDateRejected(LocalDateTime dateRejected) {
        this.dateRejected = dateRejected;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
