package com.peopleapps.dto.creditIncomeExpense;

import java.math.BigDecimal;

public class AdmCreditIncomeExpenseListDto {

    private Long creditIncomeExpenseId;
    private Long cuentaId;
    private String cuentaDescription;
    private BigDecimal monto;
    private Long creditId;

    public AdmCreditIncomeExpenseListDto() {
    }

    public AdmCreditIncomeExpenseListDto(Long creditIncomeExpenseId, Long cuentaId, String cuentaDescription, BigDecimal monto) {
        this.creditIncomeExpenseId = creditIncomeExpenseId;
        this.cuentaId = cuentaId;
        this.cuentaDescription = cuentaDescription;
        this.monto = monto;
    }

    public AdmCreditIncomeExpenseListDto(Long creditIncomeExpenseId, Long cuentaId, String cuentaDescription, BigDecimal monto, Long creditId) {
        this.creditIncomeExpenseId = creditIncomeExpenseId;
        this.cuentaId = cuentaId;
        this.cuentaDescription = cuentaDescription;
        this.monto = monto;
        this.creditId = creditId;
    }

    public Long getCreditIncomeExpenseId() {
        return creditIncomeExpenseId;
    }

    public void setCreditIncomeExpenseId(Long creditIncomeExpenseId) {
        this.creditIncomeExpenseId = creditIncomeExpenseId;
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
