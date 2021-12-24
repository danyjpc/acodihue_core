package com.peopleapps.controller.util;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.UUID;

@JsonbPropertyOrder({
        "code", "msg", "id", "cause", "rowNumber"
})
public class ResponseJson {


    private String code;
    private String msg;
    private Long id;
    private String cause;
    private String rowNumber;
    private UUID key;

    public ResponseJson() {

    }

    public ResponseJson(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseJson(String code, String msg, Long id, String cause, String rowNumber) {
        this.code = code;
        this.msg = msg;
        this.id = id;
        this.cause = cause;
        this.rowNumber = rowNumber;
    }

    public ResponseJson(String code, String msg, Long id, String cause, String rowNumber, UUID key) {
        this.code = code;
        this.msg = msg;
        this.id = id;
        this.cause = cause;
        this.rowNumber = rowNumber;
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }
}