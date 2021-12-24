with phone_account_organization as (
    insert into adm_phone_account (date_created) values (now())
        returning phone_account_id
),
     document_account_organization as (
         insert into adm_document_account (date_created) values (now())
             returning document_account_id
     ),
     address_account_organization as (
         insert into adm_address_account (date_created) values (now())
             returning address_account_id
     ),
     organization as (
         insert into adm_organization (organization_name, organization_commercial, address_account_id,
                                       phone_account_id, document_account_id, sector, category, date_created,
                                       organization_key, tax_code, is_agency, is_society, is_organization, status,
                                       entry_contribution, entry_fee, is_association)
             values ('Cooperativa Union Huehueteca',
                     'Acodihue Cooperativa Union Huehueteca',
                     (select address_account_id from address_account_organization),
                     (select phone_account_id from phone_account_organization),
                     (select document_account_id from document_account_organization),
                     160000,
                     160000,
                     now(),
                     'b4595d5d-d317-415f-8c80-6dbdb5cacd4c',
                     'S/D',
                     false,
                     false,
                     false,
                     160445, 50, 50,
                     true)
             returning organization_id
     ),
     organization_address AS (
         insert into adm_address (address_account_id, address_line, address_line_2, country,
                                  state, city, zone, status_id,
                                  type, created_by, date_created, leader,
                                  no_farm, no_folder, extension, no_public,
                                  document_account_id, village)
             values ((select address_account_id from address_account_organization), 'Sector 2, Cambote', 'S/D', 160060,
                     160073, 160278, 160432, 160445,
                     160478, 1, now(), false,
                     0, 0, 0, 'S/D',
                     (select document_account_id from document_account_organization), 160000)
             returning address_id
     ),
     phone_organization1 AS (
         insert into adm_phone (phone_account_id, phone, status,
                                type, created_by, date_created, leader)
             values ((select phone_account_id from phone_account_organization), 79344413, 160445,
                     160538, 1, now(), false)
             returning phone_id
     ),
     organization_responsible AS (
         insert into adm_organization_responsible (organization_id, person_id,
                                                   date_created, is_responsable, status)
             values ((select organization_id from organization), 1, now(), false, 160445)
     )
SELECT organization_id
from organization;

