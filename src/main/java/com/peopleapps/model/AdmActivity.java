package com.peopleapps.model;

import com.peopleapps.dto.activity.AdmActivityListDto;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.enterprise.util.Nonbinding;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_activity", schema = "public")
@SequenceGenerator(
        name = "admActivitySequence",
        sequenceName = "adm_activity_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admActivityListDto",
        classes = @ConstructorResult(
                targetClass = AdmActivityListDto.class,
                columns = {
                        @ColumnResult(name = "activity_id", type = Long.class),
                        @ColumnResult(name = "activity_account_id", type = Long.class),
                        @ColumnResult(name = "destiny_id", type = Long.class),
                        @ColumnResult(name = "destiny_desc", type = String.class),
                        @ColumnResult(name = "activity_economic_id", type = Long.class),
                        @ColumnResult(name = "activity_economic_desc", type = String.class),
                        @ColumnResult(name = "is_apparel", type = Boolean.class),
                        @ColumnResult(name = "is_fiduciary", type = Boolean.class),
                        @ColumnResult(name = "area", type = Double.class),
                        @ColumnResult(name = "unit_measure_id", type = Long.class),
                        @ColumnResult(name = "unit_measure_desc", type = String.class),
                        @ColumnResult(name = "unit_measure_value", type = String.class),
                        @ColumnResult(name = "price", type = Double.class),
                        @ColumnResult(name = "earnings", type = Double.class),
                        @ColumnResult(name = "created_by", type = Long.class),
                        @ColumnResult(name = "created_email", type = String.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "leader", type = Boolean.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "quantity", type = Long.class),
                        @ColumnResult(name = "annotation", type = String.class),
                }
        )
)
@Schema(name = "AdmActivity")
@JsonbPropertyOrder({
        "activityId", "credit", "destiny", "activityEconomic", "isApparel", "isFiduciary", "area",
        "unitMeasure", "price", "earnings", "status", "createdBy", "dateCreated"
})
@Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
public class AdmActivity implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admActivitySequence") //JP
    @Column(name = "activity_id")
    private Long activityId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmActivityAccount activityAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destiny")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology destiny;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_economic")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology activityEconomic;

    @NotNull
    @Column(name = "is_apparel")
    private Boolean isApparel;

    @NotNull
    @Column(name = "is_fiduciary")
    private Boolean isFiduciary;

    @NotNull
    @Column(name = "area")
    private Double area;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_measure")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology unitMeasure;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "earnings")
    private Double earnings;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @NotNull
    @Column(name = "quantity")
    private Long quantity;

    @NotNull
    @Column(name = "annotation")
    private String annotation;


    public AdmActivity() {
    }

    public AdmActivity(@NotNull Long activityId, @NotNull AdmActivityAccount activityAccount, @NotNull AdmTypology destiny, @NotNull AdmTypology activityEconomic, @NotNull Boolean isApparel, @NotNull Boolean isFiduciary, @NotNull Double area, @NotNull AdmTypology unitMeasure, @NotNull Double price, @NotNull Double earnings, @NotNull AdmTypology status, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull Long quantity, @NotNull String annotation) {
        this.activityId = activityId;
        this.activityAccount = activityAccount;
        this.destiny = destiny;
        this.activityEconomic = activityEconomic;
        this.isApparel = isApparel;
        this.isFiduciary = isFiduciary;
        this.area = area;
        this.unitMeasure = unitMeasure;
        this.price = price;
        this.earnings = earnings;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.quantity = quantity;
        this.annotation = annotation;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public AdmActivityAccount getActivityAccount() {
        return activityAccount;
    }

    public void setActivityAccount(AdmActivityAccount activityAccount) {
        this.activityAccount = activityAccount;
    }

    public AdmTypology getDestiny() {
        return destiny;
    }

    public void setDestiny(AdmTypology destiny) {
        this.destiny = destiny;
    }

    public AdmTypology getActivityEconomic() {
        return activityEconomic;
    }

    public void setActivityEconomic(AdmTypology activityEconomic) {
        this.activityEconomic = activityEconomic;
    }

    public Boolean getIsApparel() {
        return isApparel;
    }

    public void setIsApparel(Boolean apparel) {
        isApparel = apparel;
    }

    public Boolean getIsFiduciary() {
        return isFiduciary;
    }

    public void setIsFiduciary(Boolean fiduciary) {
        isFiduciary = fiduciary;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public AdmTypology getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(AdmTypology unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "AdmActivity{" +
                "activityId=" + activityId +
                ", activityAccount=" + activityAccount +
                ", destiny=" + destiny +
                ", activityEconomic=" + activityEconomic +
                ", isApparel=" + isApparel +
                ", isFiduciary=" + isFiduciary +
                ", area=" + area +
                ", unitMeasure=" + unitMeasure +
                ", price=" + price +
                ", earnings=" + earnings +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", quantity=" + quantity +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
