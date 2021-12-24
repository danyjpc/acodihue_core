package com.peopleapps.model;


import com.peopleapps.dto.address.AdmAddressListDto;
import com.peopleapps.dto.credit.AdmCreditListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_address")
@SequenceGenerator(
        name = "admAddressSequence",
        sequenceName = "adm_address_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admAddressListDto",
        classes = @ConstructorResult(
                targetClass = AdmAddressListDto.class,
                columns = {
                        @ColumnResult(name = "address_id", type = Long.class),
                        @ColumnResult(name = "address_account_id", type = Long.class),
                        @ColumnResult(name = "address_line", type = String.class),
                        @ColumnResult(name = "address_line_2", type = String.class),
                        @ColumnResult(name = "country_id", type = Long.class),
                        @ColumnResult(name = "country_description", type = String.class),
                        @ColumnResult(name = "state_id", type = Long.class),
                        @ColumnResult(name = "state_description", type = String.class),
                        @ColumnResult(name = "city_id", type = Long.class),
                        @ColumnResult(name = "city_description", type = String.class),
                        @ColumnResult(name = "zone_id", type = Long.class),
                        @ColumnResult(name = "zone_description", type = String.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "type_id", type = Long.class),
                        @ColumnResult(name = "type_description", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "leader", type = Boolean.class),
                        @ColumnResult(name = "no_farm", type = Long.class),
                        @ColumnResult(name = "no_folder", type = Long.class),
                        @ColumnResult(name = "extension", type = Long.class),
                        @ColumnResult(name = "no_public", type = String.class),
                        @ColumnResult(name = "document_account_id", type = Long.class),
                        //New column results should be added from this point on
                        @ColumnResult(name = "village_id", type = Long.class),
                        @ColumnResult(name = "village_description", type = String.class),
                        // New Column no_book
                        @ColumnResult(name = "no_book", type = String.class),
                }
        )
)
@Schema(name = "AdmAddress")
@JsonbPropertyOrder({"addressId","addressAccount","addressLine","addressLine2","country",
        "state","city","zone","village","status","type",
        "created_by","dateCreated","leader","noFarm","noFolder",
        "extension","noPublic","documentAcount","noBook"})
public class AdmAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAddressSequence")
    @NotNull
    @Column(name = "address_id")
    private Long addressId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_account_id")
    @DefaultValue("0")
    private AdmAddressAccount addressAccount;

    @Column(name = "address_line")
    @NotNull
    @Size(max = 255)
    @DefaultValue("%")
    private String addressLine;

    @Column(name = "address_line_2")
    @NotNull
    @Size(max = 255)
    @DefaultValue("%")
    private String addressLine2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology zone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
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

    @NotNull
    @Column(name = "no_farm")
    private Long noFarm;

    @NotNull
    @Column(name = "no_folder")
    private Long noFolder;

    @NotNull
    @Column(name = "extension")
    private Long extension;

    @Column(name = "no_public")
    @NotNull
    @Size(max = 20)
    private String noPublic;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_account_id")
    @DefaultValue("0")
    private AdmDocumentAccount documentAcount;

    //New properties should be added from this point on
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology village;

    // New column no_book
    @NotNull
    @Size(max = 10)
    @Column(name = "no_book")
    private String noBook;


    public AdmAddress() {
    }

    public AdmAddress(@NotNull Long addressId, @NotNull AdmAddressAccount addressAccount, @NotNull @Size(max = 255) String addressLine, @NotNull @Size(max = 255) String addressLine2, @NotNull AdmTypology country, @NotNull AdmTypology state, @NotNull AdmTypology city, @NotNull AdmTypology zone, @NotNull AdmTypology status, @NotNull AdmTypology type, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, Boolean leader, @NotNull Long noFarm, @NotNull Long noFolder, @NotNull Long extension, @NotNull @Size(max = 20) String noPublic, @NotNull AdmDocumentAccount documentAcount, @NotNull AdmTypology village, @NotNull @Size(max = 10) String noBook) {
        this.addressId = addressId;
        this.addressAccount = addressAccount;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zone = zone;
        this.status = status;
        this.type = type;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.leader = leader;
        this.noFarm = noFarm;
        this.noFolder = noFolder;
        this.extension = extension;
        this.noPublic = noPublic;
        this.documentAcount = documentAcount;
        this.village = village;
        this.noBook = noBook;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public AdmAddressAccount getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccount addressAccount) {
        this.addressAccount = addressAccount;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public AdmTypology getCountry() {
        return country;
    }

    public void setCountry(AdmTypology country) {
        this.country = country;
    }

    public AdmTypology getState() {
        return state;
    }

    public void setState(AdmTypology state) {
        this.state = state;
    }

    public AdmTypology getCity() {
        return city;
    }

    public void setCity(AdmTypology city) {
        this.city = city;
    }

    public AdmTypology getZone() {
        return zone;
    }

    public void setZone(AdmTypology zone) {
        this.zone = zone;
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

    public Long getNoFarm() {
        return noFarm;
    }

    public void setNoFarm(Long noFarm) {
        this.noFarm = noFarm;
    }

    public Long getNoFolder() {
        return noFolder;
    }

    public void setNoFolder(Long noFolder) {
        this.noFolder = noFolder;
    }

    public Long getExtension() {
        return extension;
    }

    public void setExtension(Long extension) {
        this.extension = extension;
    }

    public String getNoPublic() {
        return noPublic;
    }

    public void setNoPublic(String noPublic) {
        this.noPublic = noPublic;
    }

    public AdmDocumentAccount getDocumentAcount() {
        return documentAcount;
    }

    public void setDocumentAcount(AdmDocumentAccount documentAcount) {
        this.documentAcount = documentAcount;
    }

    public AdmTypology getVillage() {
        return village;
    }

    public void setVillage(AdmTypology village) {
        this.village = village;
    }

    public String getNoBook() {
        return noBook;
    }

    public void setNoBook(String noBook) {
        this.noBook = noBook;
    }

    @Override
    public String toString() {
        return "AdmAddress{" +
                "addressId=" + addressId +
                ", addressAccount=" + addressAccount +
                ", addressLine='" + addressLine + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", country=" + country +
                ", state=" + state +
                ", city=" + city +
                ", zone=" + zone +
                ", status=" + status +
                ", type=" + type +
//                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", leader=" + leader +
                ", noFarm=" + noFarm +
                ", noFolder=" + noFolder +
                ", extension=" + extension +
                ", noPublic='" + noPublic + '\'' +
                ", documentAcount=" + documentAcount +
                ", village=" + village +
                ", noBook=" + noBook +
                '}';
    }
}
