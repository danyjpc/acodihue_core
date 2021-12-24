package com.peopleapps.controller.util;


import javax.validation.constraints.Max;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class ParamsPaginated {

    @QueryParam("start")
    private Long start_position;

    @QueryParam("max")
    @Max(100)
    private Long max_result;

    @QueryParam("cliId")
    private Long cliId;

    @QueryParam("ownId")
    private Long ownId;

    @QueryParam("asc")
    @DefaultValue("true")
    private Boolean asc;

    @QueryParam("column_order")
//    @DefaultValue("name_complete")
    private String columnOrder;

    public Long getStart_position() {
        return start_position;
    }

    public void setStart_position(Long start_position) {
        this.start_position = start_position;
    }

    public Long getMax_result() {
        return max_result;
    }

    public void setMax_result(Long max_result) {
        this.max_result = max_result;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }

    public String getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(String columnOrder) {
        this.columnOrder = columnOrder;
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public Long getOwnId() {
        return ownId;
    }

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
    }
}
