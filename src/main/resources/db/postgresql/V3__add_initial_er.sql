
create sequence if not exists adm_person_sequence start with 1 increment by 1;
create table if not exists adm_person
(
    person_id               bigint       not null default nextval('adm_person_sequence'::regclass)  primary key,
    phone_account_id        bigint       not null default 0 references adm_phone_account (phone_account_id),
    document_account_id     bigint       not null default 0 references adm_document_account (document_account_id),
    address_account_id      bigint       not null default 0 references adm_address_account (address_account_id),
    beneficiary_account_id  bigint       not null default 0 references adm_beneficiary_account (beneficiary_account_id),
    first_name              varchar(100) not null default 'S/D',
    middle_name             varchar(100) not null default 'S/D',
    last_name               varchar(100) not null default 'S/D',
    partner_name            varchar(100) not null default 'S/D',
    married_name            varchar(100) not null default 'S/D',
    birthday                date         not null default '1900-01-01',
    email                   varchar(100) not null default '@',
    marital_status          bigint       not null default 160000 references adm_typology (typology_id),
    profession              varchar(100) not null default 'S/D',
    cui                     bigint       not null default 0,
    document_type           bigint       not null default 160000 references adm_typology (typology_id),
    document_order          bigint       not null default 160000 references adm_typology (typology_id),
    order_number            bigint       not null default 0,
    nit                     varchar(20)  not null default 'S/D',
    country_of_birth        bigint       not null default 160000 references adm_typology (typology_id),
    state_of_birth          bigint       not null default 160000 references adm_typology (typology_id),
    city_of_birth           bigint       not null default 160000 references adm_typology (typology_id),
    immigration_condition   bigint       not null default 160000 references adm_typology (typology_id),
    genre                   bigint       not null default 160000 references adm_typology (typology_id),
    passport                varchar(20)  not null default 'S/D',
    own_account             boolean      not null default false,
    own_account_description boolean      not null default false,
    mayan_people            bigint       not null default 160000 references adm_typology (typology_id),
    role                    bigint       not null default 160000 references adm_typology (typology_id),
    status                  bigint       not null default 160445 references adm_typology (typology_id),
    created_by              bigint       not null default 0,
    date_created            timestamp    not null default '1900-01-01 00:00:00',
    is_partner              boolean      not null default false,
    is_beneficiary          boolean      not null default false,
    membership_number       bigint       not null default 0
    );

comment on column adm_person.first_name is 'Primer Nombre';
comment on column adm_person.middle_name is 'Segundo Nombre';
comment on column adm_person.last_name is 'primer Apellido ';
comment on column adm_person.partner_name is 'Segundo Apellido';
comment on column adm_person.married_name is 'Apellido de casada';
comment on column adm_person.birthday is 'Fecha de Nacimiento';
comment on column adm_person.email is 'Correo electronico';
comment on column adm_person.marital_status is 'Estado Civil';
comment on column adm_person.profession is 'Profesion';
comment on column adm_person.cui is 'Dpi o CUI o para otro documento de identificacion unico';
comment on column adm_person.document_type is 'Tipo de documento (CEDULA, DPI, OTROS)';
comment on column adm_person.nit is 'Nit ';
comment on column adm_person.country_of_birth is 'Pais de nacimiento';
comment on column adm_person.state_of_birth is 'Departamento o estado de nacimiento';
comment on column adm_person.city_of_birth is 'Ciudad o municipio de departamento';
comment on column adm_person.immigration_condition is 'Condicion migratoria (Catalogo)';
comment on column adm_person.genre is 'Genero (Masculino o Femenino)';
comment on column adm_person.passport is 'Pasaporte';
comment on column adm_person.document_order is 'Numero de Orden (Cuando el tipo de documento es cedula)';
comment on column adm_person.order_number is ' Numero de documento (Cuando el tipo de documento es cedula)';
comment on column adm_person.own_account is 'El cliente actua en nombre propio';
comment on column adm_person.own_account_description is 'Si el cliente no actua en nombre propio especifique';
comment on column adm_person.mayan_people is 'PUEBLO (MAYA, XINCA, GARIFUNA, LADINO)';
comment on column adm_person.is_partner is 'este campo indica si es conyugue o la pareja si es casado';
comment on column adm_person.is_beneficiary is 'este campo indica si es beneficiario  para prestamos y/o cuentas/creditos';
comment on column adm_person.membership_number is 'Aqui se guarda el numero de asociado';


create sequence if not exists adm_user_sequence increment by 1 start with 1;
create table if not exists adm_user
(
    user_id      bigint       not null default nextval('adm_user_sequence'::regclass) primary key,
    person_id    bigint       not null default 0 references adm_person (person_id) unique,
    pwd          varchar(255) not null default 'abc12345',
    date_created timestamp    not null default '1900-01-01 00:00:00',
    role         bigint       not null default 160000 references adm_typology (typology_id),
    status       bigint       not null default 160445 references adm_typology (typology_id)
    );

comment on column adm_user.pwd is 'Contraseña';
comment on column adm_person.role is 'Roles (Catalogo)';
comment on column adm_person.status is 'Estado (Activo, Inactivo) (Catalogo)';

create sequence if not exists adm_phone_sequence start with 1 increment by 1;
create table if not exists adm_phone
(
    phone_id         bigint    not null default nextval('adm_phone_sequence'::regclass) primary key,
    phone_account_id bigint    not null default 0 references adm_phone_account (phone_account_id),
    phone            bigint    not null default 0,
    status           bigint    not null default 160445 references adm_typology (typology_id),
    type             bigint    not null default 160000 references adm_typology (typology_id),
    created_by       bigint    not null default 0 references adm_user (user_id),
    date_created     timestamp not null default '1900-01-01 00:00:00',
    leader           boolean   not null default false
    );

comment on column adm_phone.phone is 'Telefono';
comment on column adm_phone.type is 'Tipo de telefono (casa, trabajo, celular, personal, otros) (Catalogo) ';
comment on column adm_phone.status is 'Estado del telefono';


create sequence if not exists adm_address_sequence start with 1 increment by 1;
create table if not exists adm_address
(
    address_id          bigint       not null default nextval('adm_address_sequence'::regclass) primary key,
    address_account_id  bigint       not null default 0 references adm_address_account (address_account_id),
    address_line        varchar(255) not null default 'S/D',
    address_line_2      varchar(255) not null default 'S/D',
    country             bigint       not null default 160000 references adm_typology (typology_id),
    state               bigint       not null default 160000 references adm_typology (typology_id),
    city                bigint       not null default 160000 references adm_typology (typology_id),
    zone                bigint       not null default 160000 references adm_typology (typology_id),
    status_id           bigint       not null default 160445 references adm_typology (typology_id),
    type                bigint       not null default 160000 references adm_typology (typology_id),
    created_by          bigint       not null default 0 references adm_user (user_id),
    date_created        timestamp    not null default '1900-01-01 00:00:00',
    leader              boolean      not null default false,
    no_farm             bigint       not null default 0,
    no_folder           bigint       not null default 0,
    extension           bigint       not null default 0,
    no_public           varchar(20)  not null default 0,
    document_account_id bigint       not null default 0 references adm_document_account (document_account_id)
    );

comment on column adm_address.country is 'pais';
comment on column adm_address.state is 'departamento';
comment on column adm_address.city is 'municipio';
comment on column adm_address.zone is 'zona';
comment on column adm_address.address_line_2 is 'referencia colonia, sector, etc';
comment on column adm_address.address_line is 'direccion para aldea, cacerio';
comment on column adm_address.type is 'tipo de casa (Alquilada, Propia) ';
comment on column adm_address.no_farm is 'Finca No, sera solo usando cuando se llene la solicitud de credito';
comment on column adm_address.no_folder is 'No Folio,  sera solo usando cuando se llene la solicitud de credito';
comment on column adm_address.extension is 'Hectareas de terreno sembradas con cultivo de cafe en produccion (sera solo usando cuando se llene la solicitud de credito) ';
comment on column adm_address.no_public is 'Testimonio de escritura publica  No Ejm(344-2010) ';
comment on column adm_address.document_account_id is 'Cuando se quiera registrar los documentos del bien inmueble,se puede usar';



create sequence if not exists adm_document_sequence start with 1 increment by 1;
create table if not exists adm_document
(
    document_id          bigint    not null default nextval('adm_document_sequence'::regclass) primary key,
    document_account_id  bigint    not null default 0 references adm_document_account (document_account_id),
    path                 text      not null default 'S/D',
    created_by           bigint    not null default 0 references adm_user (user_id),
    date_created         timestamp not null default '1900-01-01 00:00:00',
    status               bigint    not null default 160445 references adm_typology (typology_id),
    leader               boolean   not null default false,
    document_credit_type bigint    not null default 160000 references adm_typology (typology_id)

    );

comment on column adm_document.path is 'Url del documento';
comment on column adm_document.leader is 'Se identifica si es principal o no';
comment on column adm_document.document_credit_type is 'Puede ser usado para guardar los tipos de documentos de solicitud de credito';


create sequence if not exists adm_partner_sequence start with 1 increment by 1;
create table if not exists adm_partner
(
    partner_id   bigint    not null default nextval('adm_partner_sequence'::regclass) primary key,
    person       bigint    not null default 0 references adm_person (person_id),
    partner      bigint    not null default 0 references adm_person (person_id),
    created_by   bigint    not null default 0 references adm_user (user_id),
    date_created timestamp not null default '1900-01-01 00:00:00',
    status       bigint    not null default 160445 references adm_typology (typology_id),
    no_boys      bigint    not null default 0,
    no_girls     bigint    not null default 0,
    leader       boolean   not null default false,
    age          bigint    not null default 0
    );

comment on column adm_partner.person is 'Persona o socio que se registra ';
comment on column adm_partner.partner is 'Es el conyuge o la pareja del asociado';
comment on column adm_partner.partner is 'Pareja o conyugue actual';
comment on column adm_partner.no_boys is 'Cantidad de hijos';
comment on column adm_partner.no_girls is 'Cantidad de hijas';
comment on column adm_partner.age is 'Edad cuando el asociado no conozca la fecha de nacimiento ';

create sequence if not exists adm_beneficiary_sequence start with 1 increment by 1;
create table if not exists adm_beneficiary
(
    beneficiary_id         bigint         not null default nextval('adm_beneficiary_sequence'::regclass) primary key,
    beneficiary_account_id bigint         not null default 0 references adm_beneficiary_account (beneficiary_account_id),
    beneficiary            bigint         not null default 0 references adm_person (person_id),
    created_by             bigint         not null default 0 references adm_user (user_id),
    date_created           timestamp      not null default '1900-01-01 00:00:00',
    status                 bigint         not null default 160445 references adm_typology (typology_id),
    kinship                bigint         not null default 160000 references adm_typology (typology_id),
    percent                decimal(17, 2) not null default 0,
    age                    bigint         not null default 0
    );

comment on column adm_beneficiary.beneficiary is 'Es el beneficiario del socio/prestamo/cuenta';
comment on column adm_beneficiary.percent is 'Son los porcentajes que el socio indica que recibira cada beneficiario';
comment on column adm_beneficiary.kinship is 'Es el parentesco familiar (catalogo)';
comment on column adm_beneficiary.age is 'Edad cuando el asociado no conozca la fecha de nacimiento';

create sequence if not exists adm_reference_sequence start with 1 increment by 1;
create table if not exists adm_reference
(
    reference_id         bigint    not null default nextval('adm_reference_sequence'::regclass) primary key,
    reference_account_id bigint    not null default 0 references adm_reference_account (reference_account_id),
    reference            bigint    not null default 0 references adm_person (person_id),
    created_by           bigint    not null default 0 references adm_user (user_id),
    date_created         timestamp not null default '1900-01-01 00:00:00',
    status               bigint    not null default 160445 references adm_typology (typology_id),
    kinship              bigint    not null default 160000 references adm_typology (typology_id),
    age                  bigint    not null default 0
    );

comment on column adm_reference.reference is 'Persona o referencia de la cuenta asociada';
comment on column adm_reference.kinship is 'Es el parentesco familiar (catalogo)';
comment on column adm_reference.age is 'Edad cuando el asociado no conozca la fecha de nacimiento';



create sequence if not exists adm_organization_sequence increment by 1 start with 160000;
create table if not exists adm_organization
(
    organization_id         bigint       not null default nextval('adm_organization_sequence'::regclass) primary key,
    organization_name       varchar(255) not null default 'S/D',
    organization_commercial varchar(255) not null default 'S/D',
    address_account_id      bigint       not null default 0 references adm_address_account (address_account_id),
    phone_account_id        bigint       not null default 0 references adm_phone_account (phone_account_id),
    document_account_id     bigint       not null default 0 references adm_document_account (document_account_id),
    sector                  bigint       not null default 160000 references adm_typology (typology_id),
    category                bigint       not null default 160000 references adm_typology (typology_id),
    date_created            timestamp    not null default '1900-01-01 00:00:00',
    organization_key        uuid         not null default '00000000-0000-0000-0000-000000000000',
    tax_code                varchar(15)  not null default '00000-0',
    is_agency               boolean      not null default false,
    is_society              boolean      not null default false
    );

comment on column adm_organization.organization_name is 'nombre de la empresa';
comment on column adm_organization.organization_name is 'nombre comercial de la empresa';
comment on column adm_organization.tax_code is 'nit de la empresa ';
comment on column adm_organization.sector is 'Sector empresarial';
comment on column adm_organization.is_agency is 'Se usara para identificar si es una agencia';
comment on column adm_organization.is_society is 'Se usara para identificar si es un comite de creditos';

/*
Esta tabla se usara para guardar las relaciones entre carteras y agencias
La relacion entre commites y grupos
*/
create sequence if not exists adm_sub_organization_sequence increment by 1 start with 160000;
create table if not exists adm_sub_organization
(
    sub_organization_id bigint    not null default nextval('adm_sub_organization_sequence'::regclass) primary  key ,
    organization_id     bigint    not null default 0 references adm_organization (organization_id),
    organization_child  bigint    not null default 0 references adm_organization (organization_id),
    date_created        timestamp not null default '1900-01-01 00:00:00',
    created_by          bigint    not null default 0 references adm_user (user_id)
    );

create sequence if not exists adm_organization_responsible_sequence increment by 1 start with 1;
create table if not exists adm_organization_responsible
(
    organization_responsible_id bigint    not null default nextval('adm_organization_responsible_sequence') primary key,
    organization_id             bigint    not null default 0 references adm_organization (organization_id),
    person_id                   bigint    not null default 0 references adm_person (person_id),
    date_created                timestamp not null default '1900-01-01 00:00:00'
    );


comment on column adm_organization_responsible.person_id is 'persona asociada o registrada';
comment on column adm_organization_responsible.organization_id is 'cooperativa a la que pertenece';

create sequence if not exists adm_account_balance_sequence start with 1 increment by 1;
create table if not exists adm_account_balance
(
    account_bank_id             bigint         not null default nextval('adm_account_balance_sequence'::regclass),
    organization_responsible_id bigint         not null default 0 references adm_organization_responsible (organization_responsible_id),
    account_type                bigint         not null default 160000 references adm_typology (typology_id),
    num_account                 bigint         not null default 0,
    num_account_order           bigint         not null default 0,
    created_by                  bigint         not null default 0 references adm_user (user_id),
    date_created                timestamp      not null default '1900-01-01 00:00:00',
    status                      bigint         not null default 160445 references adm_typology (typology_id),
    balance_initial             decimal(17, 2) not null default 0
    );

comment on column adm_account_balance.account_type is 'Tipo de cuenta ahorro o credito (Catalogo)';
comment on column adm_account_balance.num_account is 'Numero de cuenta los primeros digitos antes del guion';
comment on column adm_account_balance.num_account_order is 'Numero de cuenta ultimos digitos despues del guion';

create sequence if not exists adm_credit_sequence start with 1 increment by 1;
create table if not exists adm_credit
(
    credit_id            bigint    not null default nextval('adm_credit_sequence'::regclass) primary key ,
    no_year              bigint    not null default 2021,
    no_reference         bigint    not null default 0,
    property             bigint    not null default 0 references adm_address_account (address_account_id),
    reference_account_id bigint    not null default 0 references adm_reference_account (reference_account_id),
    document_account_id  bigint    not null default 0 references adm_document_account (document_account_id),
    created_by           bigint    not null default 0 references adm_user (user_id),
    date_created         timestamp not null default '1900-01-01 00:00:00',
    status               bigint    not null default 160445 references adm_typology (typology_id)
    );
/*tispo de documento de credito
  *Contratos Mutuos y garantia del credito
  *Cheque voucher
  *Solicitud firmada por el socio
  *DPi del Deudor/Fiador
  *NIT
  *Constancia de Residencia
  *Copia de Recibo de luz
  *Copia de recibo de Agua
  *Documentos de Cocodes
  *Documentos de Alcaldes y Municipales
  *Formularios ONU-OFAC-IVE
  *Plan y estado patrimonial
  *Fotografias del deudor/fiador
  *Croquis de ubicacion de las garantias
  *Avaluo del Bien inmueble
  *Escritura Original
  *Constancia Laboral
  *Certificacion de ingresos
  */
comment on column adm_credit.no_year is 'no de credito 077-2020 donde 2020 es el año ';
comment on column adm_credit.no_reference is 'Es el correlativo del credito';
comment on column adm_credit.property is 'Es el bien inmuble que se va a hipotecar';
comment on column adm_credit.reference_account_id is 'Cuenta para agregar las referencias al credito';
comment on column adm_credit.document_account_id is 'Documentos importantes para el credito, revisar el catalogo de tipos de documento de credito';