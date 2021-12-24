package com.peopleapps.model;

import com.peopleapps.dto.guarantee.AdmGuaranteeListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_guarantee", schema = "public")
@SequenceGenerator(
        name = "admGuaranteeSequence",
        sequenceName = "adm_guarantee_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admGuaranteeListDto",
        classes = @ConstructorResult(
                targetClass = AdmGuaranteeListDto.class,
                columns = {
                        @ColumnResult(name = "guarantee_id", type = Long.class),
                        @ColumnResult(name = "credit_id", type = Long.class),
                        @ColumnResult(name = "no_year", type = Long.class),
                        @ColumnResult(name = "no_reference", type = Long.class),
                        @ColumnResult(name = "name_farm", type = String.class),
                        @ColumnResult(name = "owner", type = String.class),
                        @ColumnResult(name = "address_id", type = Long.class),
                        @ColumnResult(name = "address_line", type = String.class),
                        @ColumnResult(name = "no_public", type = String.class),
                        @ColumnResult(name = "no_farm", type = Long.class),
                        @ColumnResult(name = "no_folder", type = Long.class),
                        @ColumnResult(name = "document_account_id", type = Long.class),
                        @ColumnResult(name = "address_state_id", type = Long.class),
                        @ColumnResult(name = "address_state_desc", type = String.class),
                        @ColumnResult(name = "address_city_id", type = Long.class),
                        @ColumnResult(name = "address_city_desc", type = String.class),
                        @ColumnResult(name = "address_account_id", type = Long.class),
                        @ColumnResult(name = "document_type_id", type = Long.class),
                        @ColumnResult(name = "document_type_desc", type = String.class),
                        @ColumnResult(name = "testimony", type = Long.class),
                        @ColumnResult(name = "rope_value", type = Double.class),
                        @ColumnResult(name = "area_meters", type = Double.class),
                        @ColumnResult(name = "msnm", type = Double.class),
                        @ColumnResult(name = "topography", type = String.class),
                        @ColumnResult(name = "hydrography", type = String.class),
                        @ColumnResult(name = "soil_quality", type = String.class),
                        @ColumnResult(name = "plan_cover", type = String.class),
                        @ColumnResult(name = "cultivate_detail", type = String.class),
                        @ColumnResult(name = "farm_neighbor", type = String.class),
                        @ColumnResult(name = "risk_class_form", type = String.class),
                        @ColumnResult(name = "irrigation_extension", type = String.class),
                        @ColumnResult(name = "building_detail", type = String.class),
                        @ColumnResult(name = "annotation", type = String.class),
                        @ColumnResult(name = "north_origin", type = String.class),
                        @ColumnResult(name = "south_origin", type = String.class),
                        @ColumnResult(name = "orient_origin", type = String.class),
                        @ColumnResult(name = "west_origin", type = String.class),
                        @ColumnResult(name = "communication_routes", type = String.class),
                        @ColumnResult(name = "state", type = Long.class),
                        @ColumnResult(name = "city", type = Long.class),
                        @ColumnResult(name = "to_city", type = String.class),
                        @ColumnResult(name = "evaluator", type = Long.class),
                        @ColumnResult(name = "evaluator_name", type = String.class),
                        @ColumnResult(name = "evaluator_email", type = String.class),
                        @ColumnResult(name = "evaluator_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "created_by", type = Long.class),
                        @ColumnResult(name = "created_email", type = String.class),
                        @ColumnResult(name = "created_key", type = UUID.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        // new column no_book
                        @ColumnResult(name = "no_book", type = String.class),
                        // new columns
                        @ColumnResult(name = "ownership_rights", type = String.class),
                        @ColumnResult(name = "no_rope", type = Double.class),
                        @ColumnResult(name = "no_hectares", type = Double.class),
                        @ColumnResult(name = "cost_per_square_meter", type = Double.class),
                        @ColumnResult(name = "height_above_sea_level", type = Double.class),
                        @ColumnResult(name = "value_of_permanent_crops", type = Double.class),
                        @ColumnResult(name = "value_of_buildings", type = Double.class),
                        @ColumnResult(name = "annotation_2", type = String.class),

                }
        )
)

@JsonbPropertyOrder({
        "guaranteeId", "credit", "noYear", "noReference", "nameFarm",
        "owner", "addressAccount", "documentType", "testimony", "repeValue", "areaMeters",
        "msnm", "topography", "hydrography", "soilQuality", "planCover", "cultivateDetail", "farmNeighbor",
        "riskClassForm", "irrigationExtension", "buildingDetail", "annotation", "northOrigin", "southOrigin",
        "orientOrigin", "westOrigin", "communicationRoutes", "state", "city", "toCity", "evaluator", "dateCreated",
        "createdBy", "status", "address","ownershipRights","noRope","noHectares","costPerSquareMeter",
        "heightAboveSeaLevel","valueOfPermanentCrops","valueOfBuildings","annotation2"
})
public class AdmGuarantee implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admGuaranteeSequence") //JPA
    @Column(name = "guarantee_id")
    @NotNull
    private Long guaranteeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "credit_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmCredit credit;

    @NotNull
    @Column(name = "no_year")
    private Long noYear;

    @NotNull
    @Column(name = "no_reference")
    private Long noReference;

    @NotNull
    @Column(name = "name_farm")
    @Size(max = 500)
    private String nameFarm;

    @NotNull
    @Column(name = "owner")
    @Size(max = 500)
    private String owner;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmAddressAccount addressAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology documentType;

    @NotNull
    @Column(name = "testimony")
    private Long testimony;


    @NotNull
    @Column(name = "rope_value")
    private Double ropeValue;

    @NotNull
    @Column(name = "area_meters")
    private Double areaMeters;

    @NotNull
    @Column(name = "msnm")
    private Double msnm;

    @NotNull
    @Column(name = "topography")
    private String topography;

    @NotNull
    @Column(name = "hydrography")
    private String hydrography;

    @NotNull
    @Column(name = "soil_quality")
    private String soilQuality;

    @NotNull
    @Column(name = "plan_cover")
    private String planCover;

    @NotNull
    @Column(name = "cultivate_detail")
    private String cultivateDetail;

    @NotNull
    @Column(name = "farm_neighbor")
    private String farmNeighbor;

    @NotNull
    @Column(name = "risk_class_form")
    private String riskClassForm;

    @NotNull
    @Column(name = "irrigation_extension")
    private String irrigationExtension;

    @NotNull
    @Column(name = "building_detail")
    private String buildingDetail;


    @NotNull
    @Column(name = "annotation")
    private String annotation;

    @NotNull
    @Column(name = "north_origin")
    private String northOrigin;

    @NotNull
    @Column(name = "south_origin")
    private String southOrigin;

    @NotNull
    @Column(name = "orient_origin")
    private String orientOrigin;

    @NotNull
    @Column(name = "west_origin")
    private String westOrigin;

    @NotNull
    @Column(name = "communication_routes")
    private String communicationRoutes;

    @NotNull
    @Column(name = "state")
    private Long state;


    @NotNull
    @Column(name = "city")
    private Long city;

    @NotNull
    @Column(name = "to_city")
    private String toCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluator")
    @JoinFetch(value = JoinFetchType.OUTER)
    private AdmPerson evaluator;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;


    @Transient
    private AdmAddress address;

    @NotNull
    @Column(name = "ownership_rights")
    private String ownershipRights;

    @NotNull
    @Column(name = "no_rope")
    private Double noRope;

    @NotNull
    @Column(name = "no_hectares")
    private Double noHectares;

    @NotNull
    @Column(name = "cost_per_square_meter")
    private Double costPerSquareMeter;

    @NotNull
    @Column(name ="height_above_sea_level")
    private Double heightAboveSeaLevel;

    @NotNull
    @Column(name = "value_of_permanent_crops")
    private Double valueOfPermanentCrops;

    @NotNull
    @Column(name = "value_of_buildings")
    private Double valueOfBuildings;

    @NotNull
    @Column(name = "annotation_2")
    private String annotation2;

    public AdmGuarantee() {
    }

    public AdmGuarantee(Long guaranteeId, AdmCredit credit, Long noYear, Long noReference, String nameFarm, String owner, AdmAddressAccount addressAccount, AdmTypology documentType, Long testimony, Double ropeValue, Double areaMeters, Double msnm, String topography, String hydrography, String soilQuality, String planCover, String cultivateDetail, String farmNeighbor, String riskClassForm, String irrigationExtension, String buildingDetail, String annotation, String northOrigin, String southOrigin, String orientOrigin, String westOrigin, String communicationRoutes, Long state, Long city, String toCity, AdmPerson evaluator, LocalDateTime dateCreated, AdmUser createdBy, AdmTypology status, AdmAddress address, String ownershipRights, Double noRope, Double noHectares, Double costPerSquareMeter, Double heightAboveSeaLevel, Double valueOfPermanentCrops, Double valueOfBuildings, String annotation2) {
        this.guaranteeId = guaranteeId;
        this.credit = credit;
        this.noYear = noYear;
        this.noReference = noReference;
        this.nameFarm = nameFarm;
        this.owner = owner;
        this.addressAccount = addressAccount;
        this.documentType = documentType;
        this.testimony = testimony;
        this.ropeValue = ropeValue;
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
        this.ownershipRights = ownershipRights;
        this.noRope = noRope;
        this.noHectares = noHectares;
        this.costPerSquareMeter = costPerSquareMeter;
        this.heightAboveSeaLevel = heightAboveSeaLevel;
        this.valueOfPermanentCrops = valueOfPermanentCrops;
        this.valueOfBuildings = valueOfBuildings;
        this.annotation2 = annotation2;
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


    public Double getRopeValue() {
        return ropeValue;
    }

    public void setRopeValue(Double repeValue) {
        this.ropeValue = repeValue;
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

    public String getOwnershipRights() {
        return ownershipRights;
    }

    public void setOwnershipRights(String ownershipRights) {
        this.ownershipRights = ownershipRights;
    }

    public Double getNoRope() {
        return noRope;
    }

    public void setNoRope(Double noRope) {
        this.noRope = noRope;
    }

    public Double getNoHectares() {
        return noHectares;
    }

    public void setNoHectares(Double noHectares) {
        this.noHectares = noHectares;
    }

    public Double getCostPerSquareMeter() {
        return costPerSquareMeter;
    }

    public void setCostPerSquareMeter(Double costPerSquareMeter) {
        this.costPerSquareMeter = costPerSquareMeter;
    }

    public Double getHeightAboveSeaLevel() {
        return heightAboveSeaLevel;
    }

    public void setHeightAboveSeaLevel(Double heightAboveSeaLevel) {
        this.heightAboveSeaLevel = heightAboveSeaLevel;
    }

    public Double getValueOfPermanentCrops() {
        return valueOfPermanentCrops;
    }

    public void setValueOfPermanentCrops(Double valueOfPermanentCrops) {
        this.valueOfPermanentCrops = valueOfPermanentCrops;
    }

    public Double getValueOfBuildings() {
        return valueOfBuildings;
    }

    public void setValueOfBuildings(Double valueOfBuildings) {
        this.valueOfBuildings = valueOfBuildings;
    }

    public String getAnnotation2() {
        return annotation2;
    }

    public void setAnnotation2(String annotation2) {
        this.annotation2 = annotation2;
    }

    @Override
    public String toString() {
        return "AdmGuarantee{" +
                "guaranteeId=" + guaranteeId +
                ", noYear=" + noYear +
                ", noReference=" + noReference +
                ", nameFarm='" + nameFarm + '\'' +
                ", owner='" + owner + '\'' +
                ", addressAccount=" + addressAccount +
                ", documentType=" + documentType +
                ", testimony=" + testimony +
                ", repeValue=" + ropeValue +
                ", areaMeters=" + areaMeters +
                ", msnm=" + msnm +
                ", topography='" + topography + '\'' +
                ", hydrography='" + hydrography + '\'' +
                ", soilQuality='" + soilQuality + '\'' +
                ", planCover='" + planCover + '\'' +
                ", cultivateDetail='" + cultivateDetail + '\'' +
                ", farmNeighbor='" + farmNeighbor + '\'' +
                ", riskClassForm='" + riskClassForm + '\'' +
                ", irrigationExtension='" + irrigationExtension + '\'' +
                ", buildingDetail='" + buildingDetail + '\'' +
                ", annotation='" + annotation + '\'' +
                ", northOrigin='" + northOrigin + '\'' +
                ", southOrigin='" + southOrigin + '\'' +
                ", orientOrigin='" + orientOrigin + '\'' +
                ", westOrigin='" + westOrigin + '\'' +
                ", communicationRoutes='" + communicationRoutes + '\'' +
                ", state=" + state +
                ", city=" + city +
                ", toCity='" + toCity + '\'' +
                ", evaluator=" + evaluator +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", ownershipRights=" + ownershipRights +
                ", noRope=" + noRope +
                ", noHectares=" + noHectares +
                ", costPerSquareMeter=" + costPerSquareMeter +
                ", heightAboveSeaLevel=" + heightAboveSeaLevel +
                ", valueOfPermanentCrops=" + valueOfPermanentCrops +
                ", valueOfBuildings=" + valueOfBuildings +
                ", annotation2=" + annotation2 +
                '}';
    }
}
