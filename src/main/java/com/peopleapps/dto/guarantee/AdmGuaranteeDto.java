package com.peopleapps.dto.guarantee;

import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;

public class AdmGuaranteeDto {

    private Long guaranteeId;
    private AdmCredit credit;
    private Long noYear;
    private Long noReference;
    private String nameFarm;
    private String owner;
    private AdmAddressAccount addressAccount;
    private AdmTypology documentType;
    private Long testimony;
    private Double repeValue;
    private Double areaMeters;
    private Double msnm;
    private String topography;
    private String hydrography;
    private String soilQuality;
    private String planCover;
    private String cultivateDetail;
    private String farmNeighbor;
    private String riskClassForm;
    private String irrigationExtension;
    private String buildingDetail;
    private String annotation;
    private String northOrigin;
    private String southOrigin;
    private String orientOrigin;
    private String westOrigin;
    private String communicationRoutes;
    private Long state;
    private Long city;
    private String toCity;
    private AdmPerson evaluator;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmUser createdBy;
    private AdmTypology status;
    private AdmAddress address;

    public AdmGuaranteeDto() {
    }

    public AdmGuaranteeDto(Long guaranteeId, AdmCredit credit, Long noYear, Long noReference, String nameFarm, String owner, AdmAddressAccount addressAccount, AdmTypology documentType, Long testimony, Double repeValue, Double areaMeters, Double msnm, String topography, String hydrography, String soilQuality, String planCover, String cultivateDetail, String farmNeighbor, String riskClassForm, String irrigationExtension, String buildingDetail, String annotation, String northOrigin, String southOrigin, String orientOrigin, String westOrigin, String communicationRoutes, Long state, Long city, String toCity, AdmPerson evaluator, LocalDateTime dateCreated, AdmUser createdBy, AdmTypology status, AdmAddress address) {
        this.guaranteeId = guaranteeId;
        this.credit = credit;
        this.noYear = noYear;
        this.noReference = noReference;
        this.nameFarm = nameFarm;
        this.owner = owner;
        this.addressAccount = addressAccount;
        this.documentType = documentType;
        this.testimony = testimony;
        this.repeValue = repeValue;
        this.areaMeters = areaMeters;
        this.msnm = msnm;
        this.topography = topography;
        this.hydrography = hydrography;
        this.soilQuality = soilQuality;
        this.planCover = planCover;
        this.cultivateDetail = cultivateDetail;
        this.farmNeighbor = farmNeighbor;
        this.riskClassForm = riskClassForm;
        this.irrigationExtension = irrigationExtension;
        this.buildingDetail = buildingDetail;
        this.annotation = annotation;
        this.northOrigin = northOrigin;
        this.southOrigin = southOrigin;
        this.orientOrigin = orientOrigin;
        this.westOrigin = westOrigin;
        this.communicationRoutes = communicationRoutes;
        this.state = state;
        this.city = city;
        this.toCity = toCity;
        this.evaluator = evaluator;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.status = status;
        this.address = address;
    }

    public Long getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(Long guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public AdmCredit getCredit() {
        return credit;
    }

    public void setCredit(AdmCredit credit) {
        this.credit = credit;
    }

    public Long getNoYear() {
        return noYear;
    }

    public void setNoYear(Long noYear) {
        this.noYear = noYear;
    }

    public Long getNoReference() {
        return noReference;
    }

    public void setNoReference(Long noReference) {
        this.noReference = noReference;
    }

    public String getNameFarm() {
        return nameFarm;
    }

    public void setNameFarm(String nameFarm) {
        this.nameFarm = nameFarm;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public AdmAddressAccount getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccount addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmTypology getDocumentType() {
        return documentType;
    }

    public void setDocumentType(AdmTypology documentType) {
        this.documentType = documentType;
    }

    public Long getTestimony() {
        return testimony;
    }

    public void setTestimony(Long testimony) {
        this.testimony = testimony;
    }

    public Double getRepeValue() {
        return repeValue;
    }

    public void setRepeValue(Double repeValue) {
        this.repeValue = repeValue;
    }

    public Double getAreaMeters() {
        return areaMeters;
    }

    public void setAreaMeters(Double areaMeters) {
        this.areaMeters = areaMeters;
    }

    public Double getMsnm() {
        return msnm;
    }

    public void setMsnm(Double msnm) {
        this.msnm = msnm;
    }

    public String getTopography() {
        return topography;
    }

    public void setTopography(String topography) {
        this.topography = topography;
    }

    public String getHydrography() {
        return hydrography;
    }

    public void setHydrography(String hydrography) {
        this.hydrography = hydrography;
    }

    public String getSoilQuality() {
        return soilQuality;
    }

    public void setSoilQuality(String soilQuality) {
        this.soilQuality = soilQuality;
    }

    public String getPlanCover() {
        return planCover;
    }

    public void setPlanCover(String planCover) {
        this.planCover = planCover;
    }

    public String getCultivateDetail() {
        return cultivateDetail;
    }

    public void setCultivateDetail(String cultivateDetail) {
        this.cultivateDetail = cultivateDetail;
    }

    public String getFarmNeighbor() {
        return farmNeighbor;
    }

    public void setFarmNeighbor(String farmNeighbor) {
        this.farmNeighbor = farmNeighbor;
    }

    public String getRiskClassForm() {
        return riskClassForm;
    }

    public void setRiskClassForm(String riskClassForm) {
        this.riskClassForm = riskClassForm;
    }

    public String getIrrigationExtension() {
        return irrigationExtension;
    }

    public void setIrrigationExtension(String irrigationExtension) {
        this.irrigationExtension = irrigationExtension;
    }

    public String getBuildingDetail() {
        return buildingDetail;
    }

    public void setBuildingDetail(String buildingDetail) {
        this.buildingDetail = buildingDetail;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getNorthOrigin() {
        return northOrigin;
    }

    public void setNorthOrigin(String northOrigin) {
        this.northOrigin = northOrigin;
    }

    public String getSouthOrigin() {
        return southOrigin;
    }

    public void setSouthOrigin(String southOrigin) {
        this.southOrigin = southOrigin;
    }

    public String getOrientOrigin() {
        return orientOrigin;
    }

    public void setOrientOrigin(String orientOrigin) {
        this.orientOrigin = orientOrigin;
    }

    public String getWestOrigin() {
        return westOrigin;
    }

    public void setWestOrigin(String westOrigin) {
        this.westOrigin = westOrigin;
    }

    public String getCommunicationRoutes() {
        return communicationRoutes;
    }

    public void setCommunicationRoutes(String communicationRoutes) {
        this.communicationRoutes = communicationRoutes;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public AdmPerson getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(AdmPerson evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public AdmAddress getAddress() {
        return address;
    }

    public void setAddress(AdmAddress address) {
        this.address = address;
    }
}
