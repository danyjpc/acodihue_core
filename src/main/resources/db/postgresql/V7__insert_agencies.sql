alter table adm_organization
    add column if not exists is_organization boolean default false;

alter table adm_organization
    add column if not exists status bigint not null default 160445;

alter table adm_organization
    add column if not exists entry_contribution decimal(17, 2) not null default 50.00;

alter table adm_organization
    add column if not exists entry_fee decimal(17, 2) not null default 50.00;

/*add comments*/
comment on column adm_organization.is_organization is 'esta condicicion indica si es una organizacion principal ejemplo acodihue las hijas serian las agencias';
comment on column adm_organization.status is 'este valor indica si es activo o inactivo';
comment on column adm_organization.entry_contribution is 'en este valor indica cual es el valor que la agencia tendra para el aporte inicial';
comment on column adm_organization.entry_fee is 'en este valor indica cual es el valor que la agencia tendra para la cuota de ingreso';
comment on column adm_person.name_complete is 'en este campo se guardara el nombre completo del cliente';
comment on column adm_address.village is 'en este campo se guardar la aldea , si proviene de un catalogo de tipologias';

/*add agencies*/

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
     organization_account as (
         insert into adm_organization (organization_name, organization_commercial, address_account_id,
                                       phone_account_id, document_account_id, sector, category, date_created,
                                       organization_key, tax_code, is_agency, is_society, is_organization, status,
                                       entry_contribution, entry_fee)
             values ('Acodihue',
                     'Cooperativa Huehueteca',
                     (select address_account_id from address_account_organization),
                     (select phone_account_id from phone_account_organization),
                     (select document_account_id from document_account_organization),
                     160000,
                     160000,
                     now(),
                     '3438d2ba-a56a-484c-bc2e-ea5b5dfcd488',
                     'S/D',
                     false,
                     false,
                     true,
                     160445, 50, 50)
             returning organization_id
     ),
     phone_account_agencia_1 as (
         insert into adm_phone_account (date_created) values (now())
             returning phone_account_id
     ),
     document_account_agencia_1 as (
         insert into adm_document_account (date_created) values (now())
             returning document_account_id
     ),
     address_account_agencia_1 as (
         insert into adm_address_account (date_created) values (now())
             returning address_account_id
     ),
     adm_organization_agencia_1 as (
         insert into adm_organization (organization_name, organization_commercial, address_account_id,
                                       phone_account_id, document_account_id, sector, category, date_created,
                                       organization_key, tax_code, is_agency, is_society, is_organization, status,
                                       entry_contribution, entry_fee)
             values ('Agencia Central',
                     'Agencia Central',
                     (select address_account_id from address_account_agencia_1),
                     (select phone_account_id from phone_account_agencia_1),
                     (select document_account_id from document_account_agencia_1),
                     160000,
                     160000,
                     now(),
                     '8feddd0a-9548-4086-a705-d6eaed027120',
                     'S/D',
                     true,
                     false,
                     false,
                     160445, 50, 50)
             returning organization_id
     ),
     adm_organization_sub_agencia_1 as (
         insert into adm_sub_organization (organization_id, organization_child, date_created, created_by)
             values ((select organization_id from organization_account),
                     (select organization_id from adm_organization_agencia_1),
                     now(),
                     1)
             returning organization_id
     ),
     phone_account_agencia_2 as (
         insert into adm_phone_account (date_created) values (now())
             returning phone_account_id
     ),
     document_account_agencia_2 as (
         insert into adm_document_account (date_created) values (now())
             returning document_account_id
     ),
     address_account_agencia_2 as (
         insert into adm_address_account (date_created) values (now())
             returning address_account_id
     ),
     adm_organization_agencia_2 as (
         insert into adm_organization (organization_name, organization_commercial, address_account_id,
                                       phone_account_id, document_account_id, sector, category, date_created,
                                       organization_key, tax_code, is_agency, is_society, is_organization, status,
                                       entry_contribution, entry_fee)
             values ('Agencia Todo Santos',
                     'Agencia Todo Santos',
                     (select address_account_id from address_account_agencia_2),
                     (select phone_account_id from phone_account_agencia_2),
                     (select document_account_id from document_account_agencia_2),
                     160000,
                     160000,
                     now(),
                     '58c70553-b5c6-4ee1-a535-b8989adb73fa',
                     'S/D',
                     true,
                     false,
                     false,
                     160445, 50, 50)
             returning organization_id
     ),
     adm_organization_sub_agencia_2 as (
         insert into adm_sub_organization (organization_id, organization_child, date_created, created_by)
             values ((select organization_id from organization_account),
                     (select organization_id from adm_organization_agencia_2),
                     now(),
                     1)
             returning organization_id
     ),
     phone_account_agencia_3 as (
         insert into adm_phone_account (date_created) values (now())
             returning phone_account_id
     ),
     document_account_agencia_3 as (
         insert into adm_document_account (date_created) values (now())
             returning document_account_id
     ),
     address_account_agencia_3 as (
         insert into adm_address_account (date_created) values (now())
             returning address_account_id
     ),
     adm_organization_agencia_3 as (
         insert
             into adm_organization (organization_name, organization_commercial, address_account_id,
                                    phone_account_id, document_account_id, sector, category, date_created,
                                    organization_key, tax_code, is_agency, is_society, is_organization, status,
                                    entry_contribution,
                                    entry_fee)
                 values ('Agencia San Miguel Soloma',
                         'Agencia San Miguel Soloma',
                         (select address_account_id from address_account_agencia_3),
                         (select phone_account_id from phone_account_agencia_3),
                         (select document_account_id from document_account_agencia_3),
                         160000,
                         160000,
                         now(),
                         '8fdcadbf-95f0-43a3-81ec-1149660b3441',
                         'S/D',
                         true,
                         false,
                         false,
                         160445, 50, 50)
                 returning organization_id
     )
insert
into adm_sub_organization(organization_id, organization_child, date_created, created_by)
values ((select organization_id from organization_account),
        (select organization_id from adm_organization_agencia_3),
        now(),
        1);

/*add phone types*/
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160537, '', 100, 'Tipos de Telefono', '', true, true),
       (160538, '', 160537, 'Celular', '', true, true),
       (160539, '', 160537, 'Trabajo', '', true, true),
       (160540, '', 160537, 'Casa', '', true, true),
       (160541, '', 160537, 'Familiar', '', true, true),
       (160542, '', 160537, 'Amigo', '', true, true),
       (160543, '', 160537, 'Otros', '', true, true);

/*add addresses types*/
insert into adm_typology(typology_id, value_1, parent_typology_id, description, value_2, available, editable)
values (160544, '', 100, 'Tipos de Direccion', '', true, true),
       (160545, '', 160544, 'Casa', '', true, true),
       (160546, '', 160544, 'Trabajo', '', true, true),
       (160547, '', 160544, 'Oficina', '', true, true),
       (160548, '', 160544, 'Otros', '', true, true),
       (160549, '', 160544, 'Facturacion', '', true, true);

/*add etnias types*/
insert into adm_typology(typology_id, value_1, parent_typology_id, description, value_2, available, editable)
values (160550, '', 100, 'Catalogo de Pueblos', '', true, true),
       (160551, '', 160544, 'Maya', '', true, true),
       (160552, '', 160544, 'Xinca', '', true, true),
       (160553, '', 160544, 'Garifuna', '', true, true),
       (160554, '', 160544, 'Ladino', '', true, true);

/*add Garantias types*/
insert into adm_typology(typology_id, value_1, parent_typology_id, description, value_2, available, editable)
values (160555, '', 100, 'Catalogo de Garantias', '', true, true),
       (160556, '', 160544, 'Prendaria', '', true, true),
       (160557, '', 160544, 'Inmobiliaria', '', true, true),
       (160558, '', 160544, 'Prenda Hipotecaria', '', true, true),
       (160559, '', 160544, 'Fiduciaria', '', true, true);

