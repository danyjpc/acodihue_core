package com.peopleapps.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmUserListDto {

    private Long userId;
    private Long personId;
    private String firstName;
    private String lastName;
    private String email;
    private UUID personKey;
    private LocalDateTime dateCreated;
    private Long roleId;
    private String roleDesc;
    private Long statusId;
    private String statusDesc;

    public AdmUserListDto(Long userId, Long personId, String firstName, String lastName, String email, UUID personKey, LocalDateTime dateCreated, Long roleId, String roleDesc, Long statusId, String statusDesc) {
        this.userId = userId;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personKey = personKey;
        this.dateCreated = dateCreated;
        this.roleId = roleId;
        this.roleDesc = roleDesc;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }
}
