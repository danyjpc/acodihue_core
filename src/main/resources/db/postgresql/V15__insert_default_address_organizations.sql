

--Agencia central default address
WITH organization1_address_account AS(
    SELECT address_account_id FROM adm_organization
    WHERE  organization_key = '8feddd0a-9548-4086-a705-d6eaed027120'
),
     organization1_document_account AS (
         SELECT document_account_id
         FROM adm_organization
         WHERE organization_key = '8feddd0a-9548-4086-a705-d6eaed027120'
     )
insert into adm_address (address_account_id, address_line, address_line_2, country,
                         state, city, zone, status_id,
                         type, created_by, date_created, leader,
                         no_farm, no_folder, extension, no_public,
                         document_account_id, village)
    values ((select address_account_id from organization1_address_account), 'D1', 'S/D', 160060,
    160073, 160278, 160421, 160445,
    160478, 1, now(), false,
    0, 0, 0, 'S/D',
    (select document_account_id from organization1_document_account), 160000);

--Agencia todos santos default address
WITH organization2_address_account AS(
    SELECT address_account_id FROM adm_organization
    WHERE  organization_key = '58c70553-b5c6-4ee1-a535-b8989adb73fa'
),
     organization2_document_account AS (
         SELECT document_account_id
         FROM adm_organization
         WHERE organization_key = '58c70553-b5c6-4ee1-a535-b8989adb73fa'
     )
insert into adm_address (address_account_id, address_line, address_line_2, country,
                         state, city, zone, status_id,
                         type, created_by, date_created, leader,
                         no_farm, no_folder, extension, no_public,
                         document_account_id, village)
    values ((select address_account_id from organization2_address_account), 'D2', 'S/D', 160060,
    160073, 160292, 160421, 160445,
    160478, 1, now(), false,
    0, 0, 0, 'S/D',
    (select document_account_id from organization2_document_account), 160000);

--Agencia Soloma default address
WITH organization3_address_account AS(
    SELECT address_account_id FROM adm_organization
    WHERE  organization_key = '8fdcadbf-95f0-43a3-81ec-1149660b3441'
),
     organization3_document_account AS (
         SELECT document_account_id
         FROM adm_organization
         WHERE organization_key = '8fdcadbf-95f0-43a3-81ec-1149660b3441'
     )
insert into adm_address (address_account_id, address_line, address_line_2, country,
                         state, city, zone, status_id,
                         type, created_by, date_created, leader,
                         no_farm, no_folder, extension, no_public,
                         document_account_id, village)
    values ((select address_account_id from organization3_address_account), 'D3', 'S/D', 160060,
    160073, 160285, 160421, 160445,
    160478, 1, now(), false,
    0, 0, 0, 'S/D',
    (select document_account_id from organization3_document_account), 160000);