package com.peopleapps.dto.address;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAddressListDto {
    private Long addressId;
    private Long addressAccountId;
    private String addressLine;
    private String addressLine2;
    private Long countryId;
    private String countryDescription;
    private Long stateId;
    private String stateDescription;
    private Long cityId;
    private String cityDescription;
    private Long zoneId;
    private String zoneDescription;
    private Long statusId;
    private String statusDescription;
    private Long typeId;
    private String typeDescription;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Boolean leader;
    private Long noFarm;
    private Long noFolder;
    private Long extension;
    private String noPublic;
    private Long documentAccountId;
    //New properties should be added from this point on
    private Long villageId;
    private String villageDescription;
    // New column no_book
    private String noBook;


    public AdmAddressListDto(Long addressId, Long addressAccountId, String addressLine, String addressLine2, Long countryId, String countryDescription, Long stateId, String stateDescription, Long cityId, String cityDescription, Long zoneId, String zoneDescription, Long statusId, String statusDescription, Long typeId, String typeDescription, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Boolean leader, Long noFarm, Long noFolder, Long extension, String noPublic, Long documentAccountId, Long villageId, String villageDescription, String noBook) {
        this.addressId = addressId;
        this.addressAccountId = addressAccountId;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.countryId = countryId;
        this.countryDescription = countryDescription;
        this.stateId = stateId;
        this.stateDescription = stateDescription;
        this.cityId = cityId;
        this.cityDescription = cityDescription;
        this.zoneId = zoneId;
        this.zoneDescription = zoneDescription;
        this.statusId = statusId;
        this.statusDescription = statusDescription;
        this.typeId = typeId;
        this.typeDescription = typeDescription;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.leader = leader;
        this.noFarm = noFarm;
        this.noFolder = noFolder;
        this.extension = extension;
        this.noPublic = noPublic;
        this.documentAccountId = documentAccountId;
        this.villageId = villageId;
        this.villageDescription = villageDescription;
        this.noBook = noBook;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public UUID getCreatedByPersonKey() {
        return createdByPersonKey;
    }

    public void setCreatedByPersonKey(UUID createdByPersonKey) {
        this.createdByPersonKey = createdByPersonKey;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressAccountId() {
        return addressAccountId;
    }

    public void setAddressAccountId(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryDescription() {
        return countryDescription;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneDescription() {
        return zoneDescription;
    }

    public void setZoneDescription(String zoneDescription) {
        this.zoneDescription = zoneDescription;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
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

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageDescription() {
        return villageDescription;
    }

    public void setVillageDescription(String villageDescription) {
        this.villageDescription = villageDescription;
    }

    public String getNoBook() {
        return noBook;
    }

    public void setNoBook(String noBook) {
        this.noBook = noBook;
    }

}
