package com.peopleapps.dto.balanceSheet;

import com.peopleapps.dto.typology.AdmTypologyDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmBalanceSheetListDto {

    private Long balanceSheetId;
    private Long cuentaId;
    private String cuentaDescription;
    private BigDecimal monto;
    private Long creditId;

    public AdmBalanceSheetListDto() {
    }

    public AdmBalanceSheetListDto(Long balanceSheetId, Long cuentaId, String cuentaDescription, BigDecimal monto) {
        this.balanceSheetId = balanceSheetId;
        this.cuentaId = cuentaId;
        this.cuentaDescription = cuentaDescription;
        this.monto = monto;
    }

    public AdmBalanceSheetListDto(Long balanceSheetId, Long cuentaId, String cuentaDescription, BigDecimal monto, Long creditId) {
        this.balanceSheetId = balanceSheetId;
        this.cuentaId = cuentaId;
        this.cuentaDescription = cuentaDescription;
        this.monto = monto;
        this.creditId = creditId;
    }

    public Long getBalanceSheetId() {
        return balanceSheetId;
    }

    public void setBalanceSheetId(Long balanceSheetId) {
        this.balanceSheetId = balanceSheetId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getCuentaDescription() {
        return cuentaDescription;
    }

    public void setCuentaDescription(String cuentaDescription) {
        this.cuentaDescription = cuentaDescription;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }
}
