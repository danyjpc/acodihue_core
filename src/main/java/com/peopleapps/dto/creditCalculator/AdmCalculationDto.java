package com.peopleapps.dto.creditCalculator;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonbPropertyOrder({"header", "calculations"})
public class AdmCalculationDto {

    private AdmCreditCalculatorDto header;
    private List<AdmCreditCalculation> calculations;

    public AdmCalculationDto() {
    }

    public AdmCalculationDto(AdmCreditCalculatorDto header, List<AdmCreditCalculation> calculations) {
        this.header = header;
        this.calculations = calculations;
    }

    public AdmCreditCalculatorDto getHeader() {
        return header;
    }

    public void setHeader(AdmCreditCalculatorDto header) {
        this.header = header;
    }

    public List<AdmCreditCalculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<AdmCreditCalculation> calculations) {
        this.calculations = calculations;
    }


    @JsonbPropertyOrder({"noPago", "fecha", "pago", "capital",
            "capitalPagado", "interes", "interesPagado", "saldo"})
    public static class AdmCreditCalculation{
        private Long noPago;
        private LocalDate fecha;
        private BigDecimal pago;
        private BigDecimal capital;
        private BigDecimal capitalPagado;
        private BigDecimal interes;
        private BigDecimal interesPagado;
        private BigDecimal saldo;

        public AdmCreditCalculation() {
        }

        public AdmCreditCalculation(Long noPago, LocalDate fecha, BigDecimal pago, BigDecimal capital, BigDecimal capitalPagado, BigDecimal interes, BigDecimal interesPagado, BigDecimal saldo) {
            this.noPago = noPago;
            this.fecha = fecha;
            this.pago = pago;
            this.capital = capital;
            this.capitalPagado = capitalPagado;
            this.interes = interes;
            this.interesPagado = interesPagado;
            this.saldo = saldo;
        }

        public Long getNoPago() {
            return noPago;
        }

        public void setNoPago(Long noPago) {
            this.noPago = noPago;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public BigDecimal getPago() {
            return pago;
        }

        public void setPago(BigDecimal pago) {
            this.pago = pago;
        }

        public BigDecimal getCapital() {
            return capital;
        }

        public void setCapital(BigDecimal capital) {
            this.capital = capital;
        }

        public BigDecimal getCapitalPagado() {
            return capitalPagado;
        }

        public void setCapitalPagado(BigDecimal capitalPagado) {
            this.capitalPagado = capitalPagado;
        }

        public BigDecimal getInteres() {
            return interes;
        }

        public void setInteres(BigDecimal interes) {
            this.interes = interes;
        }

        public BigDecimal getInteresPagado() {
            return interesPagado;
        }

        public void setInteresPagado(BigDecimal interesPagado) {
            this.interesPagado = interesPagado;
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public void setSaldo(BigDecimal saldo) {
            this.saldo = saldo;
        }
    }
}
