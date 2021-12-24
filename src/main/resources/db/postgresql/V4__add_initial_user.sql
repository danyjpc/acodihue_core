with phone_account as (
insert into adm_phone_account (date_created)
values (now())
    returning phone_account_id
    ),
    document_account as (
insert into adm_document_account (date_created)
values (now())
    returning document_account_id
    ),
    address_account as (
insert into adm_address_account (date_created)
values (now())
    returning address_account_id
    ),
    beneficiary_account as (
insert into adm_beneficiary_account (date_created)
values (now())
    returning beneficiary_account_id
    ),
    person_account as (insert
into adm_person (phone_account_id, document_account_id, address_account_id, beneficiary_account_id,
                 first_name, middle_name, last_name, partner_name, married_name, birthday, email,
                 marital_status, profession, cui, document_type, document_order, order_number, nit,
                 country_of_birth,
                 state_of_birth, city_of_birth, immigration_condition, genre, passport, own_account,
                 own_account_description, mayan_people, role, status, created_by, date_created, is_partner,
                 is_beneficiary, membership_number)
values ((select phone_account_id from phone_account),
    (select document_account_id from document_account),
    (select address_account_id from address_account),
    (select beneficiary_account_id from beneficiary_account),
    'admin', 'admin', 'admin', 'admin', 'admin', '1900-01-01', 'admin@mypeopleapps.com',
    160000, 'test', 0, 160000, 0, 0, '0', 160000, 160000, 160000, 160000, 160000,
    'S/D', false, false, 160000, 160000, 160445, 0, now(), false, false, 0)
    returning person_id
    )
insert
into adm_user(person_id, pwd, date_created, role, status)
values ((select person_id from person_account), 'admin', now(), 160534, 160445);

