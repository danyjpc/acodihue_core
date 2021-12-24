package com.peopleapps.dto.accountTypeMovementAllowed;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;

@JsonbPropertyOrder({"admAccountAllowedMovementsId", "accountType", "movements"})
public class AdmAccountTypeMovementDto {

    private AdmTypologyDto accountType;
    private List<AdmAccountTypeMovementDto.AdmMovements> movements;


    public AdmAccountTypeMovementDto() {
    }

    public AdmAccountTypeMovementDto(AdmTypologyDto accountType, List<AdmMovements> movements) {
        this.accountType = accountType;
        this.movements = movements;
    }

    public AdmTypologyDto getAccountType() {
        return accountType;
    }

    public void setAccountType(AdmTypologyDto accountType) {
        this.accountType = accountType;
    }

    public List<AdmMovements> getMovements() {
        return movements;
    }

    public void setMovements(List<AdmMovements> movements) {
        this.movements = movements;
    }

    @JsonbPropertyOrder({"admAccountAllowedMovementsId", "operation", "operationType", "createdBy", "dateCreated","status"})
    public static class AdmMovements {
        private Long admAccountAllowedMovementsId;
        private AdmTypologyDto operation;
        private AdmTypologyDto operationType;
        private AdmUserInfoDto createdBy;
        @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
        private LocalDateTime dateCreated;
        private AdmTypologyDto status;

        public AdmMovements() {
        }

        public AdmMovements(Long admAccountAllowedMovementsId, AdmTypologyDto operation, AdmTypologyDto operationType, AdmUserInfoDto createdBy, LocalDateTime dateCreated, AdmTypologyDto status) {
            this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
            this.operation = operation;
            this.operationType = operationType;
            this.createdBy = createdBy;
            this.dateCreated = dateCreated;
            this.status = status;
        }

        public Long getAdmAccountAllowedMovementsId() {
            return admAccountAllowedMovementsId;
        }

        public void setAdmAccountAllowedMovementsId(Long admAccountAllowedMovementsId) {
            this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
        }

        public AdmTypologyDto getOperation() {
            return operation;
        }


        public void setOperation(AdmTypologyDto operation) {
            this.operation = operation;
        }

        public AdmTypologyDto getOperationType() {
            return operationType;
        }

        public void setOperationType(AdmTypologyDto operationType) {
            this.operationType = operationType;
        }

        public AdmUserInfoDto getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(AdmUserInfoDto createdBy) {
            this.createdBy = createdBy;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
        }

        public AdmTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmTypologyDto status) {
            this.status = status;
        }
    }
}
