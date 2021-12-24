package com.peopleapps.dto.address;

import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"addressId","addressAccount","addressLine","addressLine2","country",
        "state","city","zone","village","status","type",
        "created_by","dateCreated","leader", "noFarm","noFolder",
        "extension","noPublic","documentAccount","noBook"})
public class AdmAddressDto {
    private Long addressId;
    private AdmAddressAccountDto addressAccount;
    private String addressLine;
    private String addressLine2;
    private AdmTypologyDto country;
    private AdmTypologyDto state;
    private AdmTypologyDto city;
    private AdmTypologyDto zone;
    private AdmTypologyDto status;
    private AdmTypologyDto type;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private Boolean leader;
    private Long noFarm;
    private Long noFolder;
    private Long extension;
    private String noPublic;
    private AdmDocumentAccountDto documentAccount;
    //New properties should be added from this point on
    private AdmTypologyDto village;
    // New column no_book
    private String noBook;

    public AdmAddressDto() {
    }

    public AdmAddressDto(Long addressId, AdmAddressAccountDto addressAccount, String addressLine, String addressLine2, AdmTypologyDto country, AdmTypologyDto state, AdmTypologyDto city, AdmTypologyDto zone, AdmTypologyDto status, AdmTypologyDto type, AdmUserInfoDto.AdmPersonUserInfoDto createdBy, LocalDateTime dateCreated, Boolean leader, Long noFarm, Long noFolder, Long extension, String noPublic, AdmDocumentAccountDto documentAccount, AdmTypologyDto village, String noBook) {
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
        this.documentAccount = documentAccount;
        this.village = village;
        this.noBook = noBook;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public AdmAddressAccountDto getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccountDto addressAccount) {
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

    public AdmTypologyDto getCountry() {
        return country;
    }

    public void setCountry(AdmTypologyDto country) {
        this.country = country;
    }

    public AdmTypologyDto getState() {
        return state;
    }

    public void setState(AdmTypologyDto state) {
        this.state = state;
    }

    public AdmTypologyDto getCity() {
        return city;
    }

    public void setCity(AdmTypologyDto city) {
        this.city = city;
    }

    public AdmTypologyDto getZone() {
        return zone;
    }

    public void setZone(AdmTypologyDto zone) {
        this.zone = zone;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }

    public AdmTypologyDto getType() {
        return type;
    }

    public void setType(AdmTypologyDto type) {
        this.type = type;
    }

    public AdmUserInfoDto.AdmPersonUserInfoDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
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

    public AdmDocumentAccountDto getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccountDto documentAccount) {
        this.documentAccount = documentAccount;
    }

    public AdmTypologyDto getVillage() {
        return village;
    }

    public void setVillage(AdmTypologyDto village) {
        this.village = village;
    }

    public String getNoBook() {
        return noBook;
    }

    public void setNoBook(String noBook) {
        this.noBook = noBook;
    }

}
