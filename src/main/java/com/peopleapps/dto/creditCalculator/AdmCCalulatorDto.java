package com.peopleapps.dto.creditCalculator;

public class AdmCCalulatorDto {
    public final Long calculatorId;
    public final Long noPeriod;
    public final Long noPayments;
    public final Long credit;
    public final Double interes;
    public final Double interesF;

    public AdmCCalulatorDto(Long calculatorId, Long noPeriod, Long noPayments, Long credit, Double interes, Double interesF) {
        this.calculatorId = calculatorId;
        this.noPeriod = noPeriod;
        this.noPayments = noPayments;
        this.credit = credit;
        this.interes = interes;
        this.interesF = interesF;
    }

    public Long getCalculatorId() {
        return calculatorId;
    }

    public Long getNoPeriod() {
        return noPeriod;
    }

    public Long getNoPayments() {
        return noPayments;
    }

    public Long getCredit() {
        return credit;
    }

    public Double getInteres() {
        return interes;
    }

    public Double getInteresF() {
        return interesF;
    }
}
