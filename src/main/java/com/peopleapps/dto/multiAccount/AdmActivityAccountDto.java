package com.peopleapps.dto.multiAccount;

public class AdmActivityAccountDto {
    private Long activityAccountId;

    public AdmActivityAccountDto() {
    }

    public AdmActivityAccountDto(Long activityAccountId) {
        this.activityAccountId = activityAccountId;
    }

    public Long getActivityAccountId() {
        return activityAccountId;
    }

    public void setActivityAccountId(Long activityAccountId) {
        this.activityAccountId = activityAccountId;
    }
}
