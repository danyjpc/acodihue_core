package com.peopleapps.model;

import com.peopleapps.dto.user.AdmRegistryUserListDto;
import com.peopleapps.dto.user.AdmUserListDto;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_user")
@SequenceGenerator(
        name = "admUserSequence",
        sequenceName = "adm_user_sequence",
        allocationSize = 1,
        initialValue = 1
)


@SqlResultSetMapping(
        name = "admUserDto",
        classes = @ConstructorResult(
                targetClass = AdmUserListDto.class,
                columns = {
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "person_id", type = Long.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "role_id", type = Long.class),
                        @ColumnResult(name = "role_desc", type = String.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = "admRegistryUserListDto",
        classes = @ConstructorResult(
                targetClass = AdmRegistryUserListDto.class,
                columns = {
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "cui", type = Long.class),
                        @ColumnResult(name = "birthday", type = LocalDate.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "person_status_id", type = Long.class),
                        @ColumnResult(name = "person_status_desc", type = String.class),
                        @ColumnResult(name = "usuario_status_id", type = Long.class),
                        @ColumnResult(name = "usuario_status_desc", type = String.class),
                        @ColumnResult(name = "usuario_role_id", type = Long.class),
                        @ColumnResult(name = "usuario_role_desc", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "created_by_first_name", type = String.class),
                        @ColumnResult(name = "created_by_last_name", type = String.class),
                        @ColumnResult(name = "created_by_cui", type = Long.class),
                        @ColumnResult(name = "created_by_birthday", type = LocalDate.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_status_id", type = Long.class),
                        @ColumnResult(name = "created_by_status_desc", type = String.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                }
        )
)
@Schema(name = "AdmUser")
@JsonbPropertyOrder({"userId", "dateCreated", "person", "role", "status"})
@Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
public class AdmUser implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admUserSequence")
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmPerson person;

    @NotNull
    @Column(name = "pwd")
    @DefaultValue("%")
    @Size(max = 255)
    private String password;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology role;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @Transient
    private UUID personKey;

    public AdmUser() {
    }

    public AdmUser(UUID personKey) {
        this.personKey = personKey;
    }

    public AdmUser(@NotNull Long userId) {
        this.userId = userId;
    }

    public AdmUser(@NotNull AdmPerson person) {
        this.person = person;
    }

    public AdmUser(@NotNull Long userId, @NotNull AdmPerson person, @NotNull @Size(max = 255) String password, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology role, @NotNull AdmTypology status) {
        this.userId = userId;
        this.person = person;
        this.password = password;
        this.dateCreated = dateCreated;
        this.role = role;
        this.status = status;
    }

    public AdmUser(@NotNull Long userId, @NotNull AdmPerson person, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology role, @NotNull AdmTypology status) {
        this.userId = userId;
        this.person = person;
        this.dateCreated = dateCreated;
        this.role = role;
        this.status = status;
    }

    public AdmUser(@NotNull Long userId, @NotNull AdmPerson person) {
        this.userId = userId;
        this.person = person;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AdmPerson getPerson() {
        return person;
    }

    public void setPerson(AdmPerson person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypology getRole() {
        return role;
    }

    public void setRole(AdmTypology role) {
        this.role = role;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    @Override
    public String toString() {
        return "AdmUser{" +
                "userId=" + userId +
                ", person=" + person +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

}
