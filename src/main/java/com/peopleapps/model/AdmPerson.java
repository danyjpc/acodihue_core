package com.peopleapps.model;

import com.peopleapps.dto.globalSearch.AdmGlobalSearchDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchListDto;
import com.peopleapps.dto.inputs.associate.AdmAssociateListDto;
import com.peopleapps.dto.person.AdmPersonListDto;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Convert;
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
@Table(name = "adm_person")
@SequenceGenerator(
        name = "admPersonSequence",
        sequenceName = "adm_person_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admPersonListDto",
        classes = @ConstructorResult(
                targetClass = AdmPersonListDto.class,
                columns = {
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "phone_account_id", type = Long.class),
                        @ColumnResult(name = "document_account_id", type = Long.class),
                        @ColumnResult(name = "address_account_id", type = Long.class),
                        @ColumnResult(name = "beneficiary_account_id", type = Long.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "middle_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "partner_name", type = String.class),
                        @ColumnResult(name = "married_name", type = String.class),
                        @ColumnResult(name = "birthday", type = LocalDate.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "marital_status_id", type = Long.class),
                        @ColumnResult(name = "marital_status_desc", type = String.class),
                        //TODO add if person.profession is changed to typology
                        @ColumnResult(name = "profession_id", type = Long.class),
                        @ColumnResult(name = "profession_desc", type = String.class),
                        //@ColumnResult(name = "profession", type = String.class),
                        @ColumnResult(name = "cui", type = Long.class),
                        @ColumnResult(name = "document_type_id", type = Long.class),
                        @ColumnResult(name = "document_type_desc", type = String.class),
                        @ColumnResult(name = "document_order_id", type = Long.class),
                        @ColumnResult(name = "document_order_desc", type = String.class),
                        @ColumnResult(name = "order_number", type = Long.class),
                        @ColumnResult(name = "nit", type = String.class),
                        @ColumnResult(name = "country_birth_id", type = Long.class),
                        @ColumnResult(name = "country_birth_desc", type = String.class),
                        @ColumnResult(name = "state_birth_id", type = Long.class),
                        @ColumnResult(name = "state_birth_desc", type = String.class),
                        @ColumnResult(name = "city_birth_id", type = Long.class),
                        @ColumnResult(name = "city_birth_desc", type = String.class),
                        @ColumnResult(name = "immigration_condition_id", type = Long.class),
                        @ColumnResult(name = "immigration_condition_desc", type = String.class),
                        @ColumnResult(name = "genre_id", type = Long.class),
                        @ColumnResult(name = "genre_desc", type = String.class),
                        @ColumnResult(name = "passport", type = String.class),
                        @ColumnResult(name = "own_account", type = Boolean.class),
                        @ColumnResult(name = "own_account_description", type = Boolean.class),
                        @ColumnResult(name = "mayan_people_id", type = Long.class),
                        @ColumnResult(name = "mayan_people_desc", type = String.class),
                        @ColumnResult(name = "role_id", type = Long.class),
                        @ColumnResult(name = "role_desc", type = String.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "is_partner", type = Boolean.class),
                        @ColumnResult(name = "is_beneficiary", type = Boolean.class),
                        @ColumnResult(name = "membership_number", type = Long.class),
                        //New column results should be added from this point on
                        @ColumnResult(name = "name_complete", type = String.class),
                        @ColumnResult(name = "is_associate", type = Boolean.class),
                        //linguistic community
                        @ColumnResult(name = "linguistic_community_id", type = Long.class),
                        @ColumnResult(name = "linguistic_community_desc", type = String.class),
                }
        )
)

@SqlResultSetMapping(
        name = "admAssociateListDto",
        classes = @ConstructorResult(
                targetClass = AdmAssociateListDto.class,
                columns = {
                        @ColumnResult(name = "associate_person_key", type = UUID.class),
                        @ColumnResult(name = "associate_name_complete", type = String.class),
                        @ColumnResult(name = "associate_membership_number", type = Long.class),
                        @ColumnResult(name = "associate_date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = "admAssociatePreinscriptionDto",
        classes = @ConstructorResult(
                targetClass = AdmAssociateListDto.class,
                columns = {
                        @ColumnResult(name = "associate_person_key", type = UUID.class),
                        @ColumnResult(name = "associate_name_complete", type = String.class),
                        @ColumnResult(name = "associate_membership_number", type = Long.class),
                        @ColumnResult(name = "associate_date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),

                        @ColumnResult(name = "associate_cui", type = Long.class),
                        @ColumnResult(name = "profession_id", type = Long.class),
                        @ColumnResult(name = "profession_desc", type = String.class),
                        @ColumnResult(name = "org_entry_contribution", type = Double.class),
                        @ColumnResult(name = "organization_entry_fee", type = Double.class),
                        @ColumnResult(name = "asso_address_line", type = String.class),
                        @ColumnResult(name = "asso_phone", type = Long.class),

                        //linguistic community
                        @ColumnResult(name = "linguistic_community_id", type = Long.class),
                        @ColumnResult(name = "linguistic_community_desc", type = String.class),
                }
        )
)

//Global Search
@SqlResultSetMapping(
        name = "admGlobalSearchPersonDto",
        classes = @ConstructorResult(
                targetClass = AdmGlobalSearchListDto.class,
                columns = {
                        @ColumnResult(name = "person_key", type = UUID.class),
                        //@ColumnResult(name = "phone_account_id", type = Long.class),
                        //@ColumnResult(name = "document_account_id", type = Long.class),
                        //@ColumnResult(name = "address_account_id", type = Long.class),
                        // @ColumnResult(name = "beneficiary_account_id", type = Long.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "middle_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "partner_name", type = String.class),
                        @ColumnResult(name = "married_name", type = String.class),
                        //@ColumnResult(name = "birthday", type = LocalDate.class),
                        @ColumnResult(name = "email", type = String.class),
                        //@ColumnResult(name = "marital_status_id", type = Long.class),
                        //@ColumnResult(name = "marital_status_desc", type = String.class),
                        //TODO add if person.profession is changed to typology
                        // @ColumnResult(name = "profession_id", type = Long.class),
                        //@ColumnResult(name = "profession_desc", type = String.class),
                        @ColumnResult(name = "cui", type = Long.class),
                        //@ColumnResult(name = "document_type_id", type = Long.class),
                        //@ColumnResult(name = "document_type_desc", type = String.class),
                        //@ColumnResult(name = "document_order_id", type = Long.class),
                        //@ColumnResult(name = "document_order_desc", type = String.class),
                        //@ColumnResult(name = "order_number", type = Long.class),
                        @ColumnResult(name = "nit", type = String.class),
                        //@ColumnResult(name = "country_birth_id", type = Long.class),
                        //@ColumnResult(name = "country_birth_desc", type = String.class),
                        //@ColumnResult(name = "state_birth_id", type = Long.class),
                        //@ColumnResult(name = "state_birth_desc", type = String.class),
                        //@ColumnResult(name = "city_birth_id", type = Long.class),
                        //@ColumnResult(name = "city_birth_desc", type = String.class),
                        //@ColumnResult(name = "immigration_condition_id", type = Long.class),
                        //@ColumnResult(name = "immigration_condition_desc", type = String.class),
                        // @ColumnResult(name = "genre_id", type = Long.class),
                        //@ColumnResult(name = "genre_desc", type = String.class),
                        // @ColumnResult(name = "passport", type = String.class),
                        //@ColumnResult(name = "own_account", type = Boolean.class),
                        //@ColumnResult(name = "own_account_description", type = Boolean.class),
                        //@ColumnResult(name = "mayan_people_id", type = Long.class),
                        //@ColumnResult(name = "mayan_people_desc", type = String.class),
                        //@ColumnResult(name = "role_id", type = Long.class),
                        //@ColumnResult(name = "role_desc", type = String.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        // @ColumnResult(name = "created_by_email", type = String.class),
                        //@ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        // @ColumnResult(name = "is_partner", type = Boolean.class),
                        // @ColumnResult(name = "is_beneficiary", type = Boolean.class),
                        @ColumnResult(name = "membership_number", type = Long.class),
                        //New column results should be added from this point on
                        @ColumnResult(name = "name_complete", type = String.class),
                        //@ColumnResult(name = "is_associate", type = Boolean.class),
                        @ColumnResult(name = "linguistic_community_id", type = Long.class),
                        @ColumnResult(name = "linguistic_community_desc", type = String.class)
                }
        )
)


@Schema(name = "AdmPerson")
@JsonbPropertyOrder({"personId", "phoneAccount", "documentAccount", "addressAccount",
        "beneficiaryAccount", "firstName", "middleName", "lastName",
        "partnerName", "marriedName", "name_complete", "birthday", "email",
        "maritalStatus", "profession", "cui", "documentType",
        "documentOrder", "orderNumber", "nit", "countryOfBirth",
        "stateOfBirth", "cityOfBirth", "immigrationCondition", "genre",
        "passport", "ownAccount", "ownAccountDescription", "mayanPeople",
        "role", "status", "createdBy", "dateCreated",
        "isPartner", "isBeneficiary", "membershipNumber", "personKey"})
@Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
public class AdmPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPersonSequence")
    @Column(name = "person_id")
    private Long personId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phone_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmPhoneAccount phoneAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmDocumentAccount documentAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmAddressAccount addressAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beneficiary_account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmBeneficiaryAccount beneficiaryAccount;

    @NotNull
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 100)
    @Column(name = "partner_name")
    private String partnerName;

    @Size(max = 100)
    @Column(name = "married_name")
    private String marriedName;

    @NotNull
    @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
    @Column(name = "birthday")
    private LocalDate birthday;

    @Size(max = 100)
    @Column(name = "email")
    private String email;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marital_status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology maritalStatus;

    //TODO remove if converted to typology
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profession_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology profession;

    @NotNull
    @Column(name = "cui")
    private Long cui;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology documentType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_order")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology documentOrder;

    @NotNull
    @Column(name = "order_number")
    private Long orderNumber;

    @NotNull
    @Size(max = 20)
    @Column(name = "nit")
    private String nit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_of_birth")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology countryOfBirth;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_of_birth")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology stateOfBirth;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_of_birth")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology cityOfBirth;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "immigration_condition")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology immigrationCondition;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology genre;

    @NotNull
    @Size(max = 20)
    @Column(name = "passport")
    private String passport;

    @NotNull
    @Column(name = "own_account")
    @DefaultValue("FALSE")
    private Boolean ownAccount;

    @NotNull
    @Column(name = "own_account_description")
    @DefaultValue("FALSE")
    private Boolean ownAccountDescription;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mayan_people")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology mayanPeople;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.OUTER)
    private AdmUser createdBy;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @Column(name = "is_partner")
    private Boolean isPartner;

    @NotNull
    @Column(name = "is_beneficiary")
    private Boolean isBeneficiary;

    @NotNull
    @Column(name = "membership_number")
    private Long membershipNumber;

    @NotNull
    @Column(name = "person_key")
    @Convert("uuidConverter")
    private UUID personKey;

    //New properties should be added from this point on, so the constructor is not changed
    @NotNull
    @Size(max = 500)
    @Column(name = "name_complete")
    private String nameComplete;

    @NotNull
    @Column(name = "is_associate")
    private Boolean isAssociate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "linguistic_community_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology linguisticCommunity;

    public AdmPerson() {
    }

    public AdmPerson(Long personId) {
        this.personId = personId;
    }

    public AdmPerson(@NotNull UUID personKey) {
        this.personKey = personKey;
    }

    public AdmPerson(Long personId, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AdmPerson(@NotNull UUID personKey, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personKey = personKey;
    }

    public AdmPerson(@NotNull UUID personKey, @Size(max = 100) String email) {
        this.email = email;
        this.personKey = personKey;
    }


    public AdmPerson(Long personId, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String lastName, @NotNull @Size(max = 500) String nameComplete) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nameComplete = nameComplete;
    }

    public AdmPerson(UUID personKey, @NotNull AdmPhoneAccount phoneAccount, @NotNull AdmDocumentAccount documentAccount, @NotNull AdmAddressAccount addressAccount, @NotNull AdmBeneficiaryAccount beneficiaryAccount, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String middleName, @NotNull @Size(max = 100) String lastName, @Size(max = 100) String partnerName, @Size(max = 100) String marriedName, @NotNull LocalDate birthday, @Size(max = 100) String email, @NotNull AdmTypology maritalStatus, @NotNull AdmTypology profession, @NotNull Long cui, @NotNull AdmTypology documentType, @NotNull AdmTypology documentOrder, @NotNull Long orderNumber, @NotNull @Size(max = 20) String nit, @NotNull AdmTypology countryOfBirth, @NotNull AdmTypology stateOfBirth, @NotNull AdmTypology cityOfBirth, @NotNull AdmTypology immigrationCondition, @NotNull AdmTypology genre, @NotNull @Size(max = 20) String passport, @NotNull Boolean ownAccount, @NotNull Boolean ownAccountDescription, @NotNull AdmTypology mayanPeople, @NotNull AdmTypology role, @NotNull AdmTypology status, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull Boolean isPartner, @NotNull Boolean isBeneficiary, @NotNull Long membershipNumber, @NotNull @Size(max = 500) String nameComplete, @NotNull Boolean isAssociate, @NotNull AdmTypology linguisticCommunity) {
        this.personKey = personKey;
        this.phoneAccount = phoneAccount;
        this.documentAccount = documentAccount;
        this.addressAccount = addressAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.birthday = birthday;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.profession = profession;
        this.cui = cui;
        this.documentType = documentType;
        this.documentOrder = documentOrder;
        this.orderNumber = orderNumber;
        this.nit = nit;
        this.countryOfBirth = countryOfBirth;
        this.stateOfBirth = stateOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.immigrationCondition = immigrationCondition;
        this.genre = genre;
        this.passport = passport;
        this.ownAccount = ownAccount;
        this.ownAccountDescription = ownAccountDescription;
        this.mayanPeople = mayanPeople;
        this.role = role;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.isPartner = isPartner;
        this.isBeneficiary = isBeneficiary;
        this.membershipNumber = membershipNumber;
        this.nameComplete = nameComplete;
        this.isAssociate = isAssociate;
        this.linguisticCommunity = linguisticCommunity;
    }

    public AdmPerson(Long personId, @NotNull AdmPhoneAccount phoneAccount, @NotNull AdmDocumentAccount documentAccount, @NotNull AdmAddressAccount addressAccount, @NotNull AdmBeneficiaryAccount beneficiaryAccount, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String middleName, @NotNull @Size(max = 100) String lastName, @Size(max = 100) String partnerName, @Size(max = 100) String marriedName, @NotNull LocalDate birthday, @Size(max = 100) String email, @NotNull AdmTypology maritalStatus, @NotNull AdmTypology profession, @NotNull Long cui, @NotNull AdmTypology documentType, @NotNull AdmTypology documentOrder, @NotNull Long orderNumber, @NotNull @Size(max = 20) String nit, @NotNull AdmTypology countryOfBirth, @NotNull AdmTypology stateOfBirth, @NotNull AdmTypology cityOfBirth, @NotNull AdmTypology immigrationCondition, @NotNull AdmTypology genre, @NotNull @Size(max = 20) String passport, @NotNull Boolean ownAccount, @NotNull Boolean ownAccountDescription, @NotNull AdmTypology mayanPeople, @NotNull AdmTypology role, @NotNull AdmTypology status, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull Boolean isPartner, @NotNull Boolean isBeneficiary, @NotNull Long membershipNumber, @NotNull UUID personKey, @NotNull @Size(max = 500) String nameComplete, @NotNull Boolean isAssociate, @NotNull AdmTypology linguisticCommunity ) {
        this.personId = personId;
        this.phoneAccount = phoneAccount;
        this.documentAccount = documentAccount;
        this.addressAccount = addressAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.birthday = birthday;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.profession = profession;
        this.cui = cui;
        this.documentType = documentType;
        this.documentOrder = documentOrder;
        this.orderNumber = orderNumber;
        this.nit = nit;
        this.countryOfBirth = countryOfBirth;
        this.stateOfBirth = stateOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.immigrationCondition = immigrationCondition;
        this.genre = genre;
        this.passport = passport;
        this.ownAccount = ownAccount;
        this.ownAccountDescription = ownAccountDescription;
        this.mayanPeople = mayanPeople;
        this.role = role;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.isPartner = isPartner;
        this.isBeneficiary = isBeneficiary;
        this.membershipNumber = membershipNumber;
        this.personKey = personKey;
        this.nameComplete = nameComplete;
        this.isAssociate = isAssociate;
        this.linguisticCommunity = linguisticCommunity;
    }

    public AdmPerson(Long personId, @NotNull @Size(max = 100) String firstName, @NotNull @Size(max = 100) String lastName, @Size(max = 100) String email, @NotNull UUID personKey) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personKey = personKey;
    }

    public AdmPerson(String email, UUID personKey, String nameComplete) {
        this.email = email;
        this.personKey = personKey;
        this.nameComplete = nameComplete;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public AdmPhoneAccount getPhoneAccount() {
        return phoneAccount;
    }

    public void setPhoneAccount(AdmPhoneAccount phoneAccount) {
        this.phoneAccount = phoneAccount;
    }

    public AdmDocumentAccount getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccount documentAccount) {
        this.documentAccount = documentAccount;
    }

    public AdmAddressAccount getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccount addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmBeneficiaryAccount getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(AdmBeneficiaryAccount beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMarriedName() {
        return marriedName;
    }

    public void setMarriedName(String marriedName) {
        this.marriedName = marriedName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdmTypology getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(AdmTypology maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public AdmTypology getProfession() {
        return profession;
    }

    public void setProfession(AdmTypology profession) {
        this.profession = profession;
    }

    public Boolean getPartner() {
        return isPartner;
    }

    public void setPartner(Boolean partner) {
        isPartner = partner;
    }

    public Boolean getBeneficiary() {
        return isBeneficiary;
    }

    public void setBeneficiary(Boolean beneficiary) {
        isBeneficiary = beneficiary;
    }

    public Boolean getAssociate() {
        return isAssociate;
    }

    public void setAssociate(Boolean associate) {
        isAssociate = associate;
    }

    public Long getCui() {
        return cui;
    }

    public void setCui(Long cui) {
        this.cui = cui;
    }

    public AdmTypology getDocumentType() {
        return documentType;
    }

    public void setDocumentType(AdmTypology documentType) {
        this.documentType = documentType;
    }

    public AdmTypology getDocumentOrder() {
        return documentOrder;
    }

    public void setDocumentOrder(AdmTypology documentOrder) {
        this.documentOrder = documentOrder;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public AdmTypology getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(AdmTypology countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public AdmTypology getStateOfBirth() {
        return stateOfBirth;
    }

    public void setStateOfBirth(AdmTypology stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public AdmTypology getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(AdmTypology cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public AdmTypology getImmigrationCondition() {
        return immigrationCondition;
    }

    public void setImmigrationCondition(AdmTypology immigrationCondition) {
        this.immigrationCondition = immigrationCondition;
    }

    public AdmTypology getGenre() {
        return genre;
    }

    public void setGenre(AdmTypology genre) {
        this.genre = genre;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Boolean getOwnAccount() {
        return ownAccount;
    }

    public void setOwnAccount(Boolean ownAccount) {
        this.ownAccount = ownAccount;
    }

    public Boolean getOwnAccountDescription() {
        return ownAccountDescription;
    }

    public void setOwnAccountDescription(Boolean ownAccountDescription) {
        this.ownAccountDescription = ownAccountDescription;
    }

    public AdmTypology getMayanPeople() {
        return mayanPeople;
    }

    public void setMayanPeople(AdmTypology mayanPeople) {
        this.mayanPeople = mayanPeople;
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

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Boolean partner) {
        isPartner = partner;
    }

    public Boolean getIsBeneficiary() {
        return isBeneficiary;
    }

    public void setIsBeneficiary(Boolean beneficiary) {
        isBeneficiary = beneficiary;
    }

    public Long getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(Long membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public Boolean getIsAssociate() {
        return isAssociate;
    }

    public void setIsAssociate(Boolean associate) {
        isAssociate = associate;
    }

    public AdmTypology getLinguisticCommunity() {
        return linguisticCommunity;
    }

    public void setLinguisticCommunity(AdmTypology linguisticCommunity) {
        this.linguisticCommunity = linguisticCommunity;
    }

    @Override
    public String toString() {
        return "AdmPerson{" +
                "personId=" + personId +
                ", phoneAccount=" + phoneAccount +
                ", documentAccount=" + documentAccount +
                ", addressAccount=" + addressAccount +
                ", beneficiaryAccount=" + beneficiaryAccount +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", partnerName='" + partnerName + '\'' +
                ", marriedName='" + marriedName + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", profession='" + profession + '\'' +
                ", cui=" + cui +
                ", documentType=" + documentType +
                ", documentOrder=" + documentOrder +
                ", orderNumber=" + orderNumber +
                ", nit='" + nit + '\'' +
                ", countryOfBirth=" + countryOfBirth +
                ", stateOfBirth=" + stateOfBirth +
                ", cityOfBirth=" + cityOfBirth +
                ", immigrationCondition=" + immigrationCondition +
                ", genre=" + genre +
                ", passport='" + passport + '\'' +
                ", ownAccount=" + ownAccount +
                ", ownAccountDescription=" + ownAccountDescription +
                ", mayanPeople=" + mayanPeople +
                ", role=" + role +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", isPartner=" + isPartner +
                ", isBeneficiary=" + isBeneficiary +
                ", membershipNumber=" + membershipNumber +
                ", personKey=" + personKey +
                ", nameComplete='" + nameComplete + '\'' +
                ", isAssociate=" + isAssociate +
                '}';
    }
}
