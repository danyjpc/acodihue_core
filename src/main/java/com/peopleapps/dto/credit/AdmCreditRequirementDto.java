package com.peopleapps.dto.credit;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.util.List;


//Class to model from this image
//https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1291/
//every requirement may have sub requirements

@JsonbPropertyOrder({"number", "requirement", "percentageComplete"})
public class AdmCreditRequirementDto {
    private Long number;
    private List<CreditSubRequirement> subRequirements;
    private String nameDocument;
    private Boolean isDownload;
    private String pathDownload;
    private BigDecimal percentageComplete;

    public AdmCreditRequirementDto() {
    }

    public AdmCreditRequirementDto(Long number, List<CreditSubRequirement> subRequirements,String nameDocument, Boolean isDownload, String pathDownload, BigDecimal percentageComplete) {
        this.number = number;
        this.subRequirements = subRequirements;
        this.nameDocument = nameDocument;
        this.isDownload = isDownload;
        this.pathDownload = pathDownload;
        this.percentageComplete = percentageComplete;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public List<CreditSubRequirement> getSubRequirements() {
        return subRequirements;
    }

    public void setSubRequirements(List<CreditSubRequirement> subRequirements) {
        this.subRequirements = subRequirements;
    }

    public String getNameDocument() {
        return nameDocument;
    }

    public void setNameDocument(String nameDocument) {
        this.nameDocument = nameDocument;
    }

    public Boolean getDownload() {
        return isDownload;
    }

    public void setDownload(Boolean download) {
        isDownload = download;
    }

    public String getPathDownload() {
        return pathDownload;
    }

    public void setPathDownload(String pathDownload) {
        this.pathDownload = pathDownload;
    }

    public BigDecimal getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(BigDecimal percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    @JsonbPropertyOrder({"description", "percentageRepresented", "complete"})
    public static class CreditSubRequirement{
        private String description;
        private BigDecimal percentageRepresented;
        private Boolean complete;

        public CreditSubRequirement() {
        }

        public CreditSubRequirement(String description, BigDecimal percentageRepresented, Boolean complete) {
            this.description = description;
            this.percentageRepresented = percentageRepresented;
            this.complete = complete;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPercentageRepresented() {
            return percentageRepresented;
        }

        public void setPercentageRepresented(BigDecimal percentageRepresented) {
            this.percentageRepresented = percentageRepresented;
        }

        public Boolean getComplete() {
            return complete;
        }

        public void setComplete(Boolean complete) {
            this.complete = complete;
        }
    }
}

