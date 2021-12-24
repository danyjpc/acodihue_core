create sequence if not exists adm_address_account_sequence start with 1 increment by 1;
create table if not exists adm_address_account
(
    address_account_id bigint not null default nextval('adm_address_account_sequence') primary key,
    date_created       timestamp       default '1900-01-01 00:00:00'
);

create sequence if not exists adm_phone_account_sequence start with 1 increment by 1;
create table if not exists adm_phone_account
(
    phone_account_id bigint not null default nextval('adm_phone_account_sequence') primary key,
    date_created     timestamp       default '1900-01-01 00:00:00'
);

create sequence if not exists adm_document_account_sequence start with 1 increment by 1;
create table if not exists adm_document_account
(
    document_account_id bigint not null default nextval('adm_document_account_sequence') primary key,
    date_created        timestamp       default '1900-01-01 00:00:00'
);


create sequence if not exists adm_beneficiary_account_sequence start with 1 increment by 1;
create table if not exists adm_beneficiary_account
(
    beneficiary_account_id bigint not null default nextval('adm_beneficiary_account_sequence') primary key,
    date_created           timestamp       default '1900-01-01 00:00:00'
);


create sequence if not exists adm_reference_account_sequence start with 1 increment by 1;
create table if not exists adm_reference_account
(
    reference_account_id bigint not null default nextval('adm_reference_account_sequence') primary key,
    date_created         timestamp       default '1900-01-01 00:00:00'
);

create sequence if not exists adm_typology_sequence start with 170000 increment by 1;
create table if not exists adm_typology
(
    typology_id        bigint       not null default nextval('adm_typology_sequence'::regclass) primary key,
    description        varchar(200) not null default 0,
    parent_typology_id bigint                default 100,
    value_1            varchar(100) not null default '',
    value_2            varchar(100) not null default '',
    available          boolean      not null default false,
    editable           boolean      not null default false
);

comment on column adm_typology.description is 'Descripcion de la tipologia';
comment on column adm_typology.parent_typology_id is 'Relacion de tipologia padre con hijo';
comment on column adm_typology.value_1 is 'Valores que puedan ser usados para el front (color,codigos) ';
comment on column adm_typology.value_2 is 'Valores que puedan ser usados para el front (color,codigos) ';
comment on column adm_typology.available is 'Especifica si esta disponible para su uso o no';
comment on column adm_typology.editable is 'Especifica si es editable o no ';

insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (100, 'Global', null, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160000, 'Tipologia Vacia', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (0, 'Tipologia Vacia', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160010, 'Religion', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160011, 'Catolico', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160012, 'Ortodoxo', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160013, 'Cristiano', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160014, 'Mormon', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160015, 'Judio', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160016, 'Musulman', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160017, 'Budista', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160018, 'Hindu', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160019, 'Agnostico', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160020, 'Atheo', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160021, 'Otros', 160010, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160022, 'Genero', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160023, 'Masculino', 160022, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160024, 'Femenino', 160022, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160025, 'Otros', 160022, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160034, 'Tipo Sangre', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160035, 'O−', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160036, 'O+', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160037, 'A-', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160038, 'A+', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160039, 'B-', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160040, 'B+', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160041, 'AB-', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160042, 'AB+', 160034, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160052, 'Estado Civil', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160053, 'Soltero', 160052, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160054, 'Casado', 160052, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160059, 'Pais', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160060, 'Guatemala', 160059, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160061, 'Guatemala', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160062, 'El Progreso', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160063, 'Sacatepéquez', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160064, 'Chimaltenango', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160065, 'Escuintla', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160066, 'Santa Rosa', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160067, 'Sololá', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160068, 'Totonicapán', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160069, 'Quetzaltenango', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160070, 'Suchitepéquez', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160071, 'Retalhuleu', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160072, 'San Marcos', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160073, 'Huehuetenango', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160074, 'Quiché', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160075, 'Baja Verapaz', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160076, 'Alta Verapaz', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160077, 'Petén', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160078, 'Izabal', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160079, 'Zacapa', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160080, 'Chiquimula', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160081, 'Jalapa', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160082, 'Jutiapa', 160060, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160083, 'Guatemala', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160084, 'Santa Catarina Pinula', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160085, 'San José Pinula', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160086, 'San José del Golfo', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160087, 'Palencia', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160088, 'Chinautla', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160089, 'San Pedro Ayampuc', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160090, 'Mixco', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160091, 'San Pedro Sacatepéquez', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160092, 'San Juan Sacatepéquez', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160093, 'San Raymundo', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160094, 'Chuarrancho', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160095, 'Fraijanes', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160096, 'Amatitlán', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160097, 'Villa Nueva', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160098, 'Villa Canales', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160099, 'Petapa', 160061, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160100, 'Guastatoya', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160101, 'Morazán', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160102, 'San Agustín Acasaguastlán', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160103, 'San Cristóbal Acasaguastlán', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160104, 'El Jícaro', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160105, 'Sansare', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160106, 'Sanarate', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160107, 'San Antonio la Paz', 160062, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160108, 'Antigua Guatemala', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160109, 'Jocotenango', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160110, 'Pastores', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160111, 'Sumpango', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160112, 'Santo Domingo Xenacoj', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160113, 'Santiago Sacatepéquez', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160114, 'San Bartolomé Milpas Altas', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160115, 'San Lucas Sacatepéquez', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160116, 'Santa Lucía Milpas Altas', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160117, 'Magdalena Milpas Altas', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160118, 'Santa María de Jesús', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160119, 'Ciudad Vieja', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160120, 'San Miguel Dueñas', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160121, 'Alotenango', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160122, 'San Antonio Aguas Calientes', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160123, 'Santa Catarina Barahona', 160063, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160124, 'Chimaltenango', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160125, 'San José Poaquil', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160126, 'San Martín Jilotepeque', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160127, 'Comalapa', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160128, 'Santa Apolonia', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160129, 'Tecpán Guatemala', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160130, 'Patzún', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160131, 'Pochuta', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160132, 'Patzicía', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160133, 'Santa Cruz Balanyá', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160134, 'Acatenango', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160135, 'Yepocapa', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160136, 'San Andrés Itzapa', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160137, 'Parramos', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160138, 'Zaragoza', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160139, 'El Tejar', 160064, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160140, 'Escuintla', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160141, 'Santa Lucía Cotzumalguapa', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160142, 'La Democracia', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160143, 'Siquinalá', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160144, 'Masagua', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160145, 'Tiquisate', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160146, 'La Gomera', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160147, 'Guanagazapa', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160148, 'San José', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160149, 'Iztapa', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160150, 'Palín', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160151, 'San Vicente Pacaya', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160152, 'Nueva Concepción', 160065, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160153, 'Cuilapa', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160154, 'Barberena', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160155, 'Santa Rosa de Lima', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160156, 'Casillas', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160157, 'San Rafael las Flores', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160158, 'Oratorio', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160159, 'San Juan Tecuaco', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160160, 'Chiquimulilla', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160161, 'Taxisco', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160162, 'Santa María Ixhuatán', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160163, 'Guazacapán', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160164, 'Santa Cruz Naranjo', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160165, 'Pueblo Nuevo Viñas', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160166, 'Nueva Santa Rosa', 160066, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160167, 'Sololá', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160168, 'San José Chacayá', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160169, 'Santa María Visitación', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160170, 'Santa Lucía Utatlán', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160171, 'Nahualá', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160172, 'Santa Catarina Ixtahuacán', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160173, 'Santa Clara la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160174, 'Concepción', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160175, 'San Andrés Semetabaj', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160176, 'Panajachel', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160177, 'Santa Catarina Palopó', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160178, 'San Antonio Palopó', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160179, 'San Lucas Tolimán', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160180, 'Santa Cruz la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160181, 'San Pablo la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160182, 'San Marcos la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160183, 'San Juan la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160184, 'San Pedro la Laguna', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160185, 'Santiago Atitlán', 160067, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160186, 'Totonicapán', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160187, 'San Cristóbal Totonicapán', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160188, 'San Francisco el Alto', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160189, 'San Andrés Xecul', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160190, 'Momostenango', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160191, 'Santa María Chiquimula', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160192, 'Santa Lucía la Reforma', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160193, 'San Bartolo', 160068, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160194, 'Quetzaltenango', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160195, 'Salcajá', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160196, 'Olintepeque', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160197, 'San Carlos Sija', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160198, 'Sibilia', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160199, 'Cabricán', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160200, 'Cajolá', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160201, 'San Miguel Siguilá', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160202, 'Ostuncalco', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160203, 'San Mateo', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160204, 'Concepción Chiquirichapa', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160205, 'San Martín Sacatepéquez', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160206, 'Almolonga', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160207, 'Cantel', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160208, 'Huitán', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160209, 'Zunil', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160210, 'Colomba', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160211, 'San Francisco la Unión', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160212, 'El Palmar', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160213, 'Coatepeque', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160214, 'Génova', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160215, 'Flores Costa Cuca', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160216, 'La Esperanza', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160217, 'Palestina de los Altos', 160069, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160218, 'Mazatenango', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160219, 'Cuyotenango', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160220, 'San Francisco Zapotitlán', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160221, 'San Bernardino', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160222, 'San José el Idolo', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160223, 'Santo Domingo Suchitepéquez', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160224, 'San Lorenzo', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160225, 'Samayac', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160226, 'San Pablo Jocopilas', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160227, 'San Antonio Suchitepéquez', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160228, 'San Miguel Panán', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160229, 'San Gabriel', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160230, 'Chicacao', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160231, 'Patulul', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160232, 'Santa Bárbara', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160233, 'San Juan Bautista', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160234, 'Santo Tomás la Unión', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160235, 'Zunilito', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160236, 'Pueblo Nuevo', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160237, 'Río Bravo', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160238, 'San José La Máquina', 160070, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160239, 'Retalhuleu', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160240, 'San Sebastián', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160241, 'Santa Cruz Muluá', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160242, 'San Martín Zapotitlán', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160243, 'San Felipe', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160244, 'San Andrés Villa Seca', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160245, 'Champerico', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160246, 'Nuevo San Carlos', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160247, 'El Asintal', 160071, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160248, 'San Marcos', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160249, 'San Pedro Sacatepéquez', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160250, 'San Antonio Sacatepéquez', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160251, 'Comitancillo', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160252, 'San Miguel Ixtahuacán', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160253, 'Concepción Tutuapa', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160254, 'Tacaná', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160255, 'Sibinal', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160256, 'Tajumulco', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160257, 'Tejutla', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160258, 'San Rafael Pié de la Cuesta', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160259, 'Nuevo Progreso', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160260, 'El Tumbador', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160261, 'El Rodeo', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160262, 'Malacatán', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160263, 'Catarina', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160264, 'Ayutla', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160265, 'Ocós', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160266, 'San Pablo', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160267, 'El Quetzal', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160268, 'La Reforma', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160269, 'Pajapita', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160270, 'Ixchiguán', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160271, 'San José Ojetenán', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160272, 'San Cristóbal Cucho', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160273, 'Sipacapa', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160274, 'Esquipulas Palo Gordo', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160275, 'Río Blanco', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160276, 'San Lorenzo', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160277, 'La Blanca', 160072, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160278, 'Huehuetenango', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160279, 'Chiantla', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160280, 'Malacatancito', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160281, 'Cuilco', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160282, 'Nentón', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160283, 'San Pedro Necta', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160284, 'Jacaltenango', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160285, 'Soloma', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160286, 'Ixtahuacán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160287, 'Santa Bárbara', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160288, 'La Libertad', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160289, 'La Democracia', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160290, 'San Miguel Acatán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160291, 'San Rafael la Independencia', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160292, 'Todos Santos Cuchumatán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160293, 'San Juan Atitán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160294, 'Santa Eulalia', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160295, 'San Mateo Ixtatán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160296, 'Colotenango', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160297, 'San Sebastián Huehuetenango', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160298, 'Tectitán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160299, 'Concepción Huista', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160300, 'San Juan Ixcoy', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160301, 'San Antonio Huista', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160302, 'San Sebastián Coatán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160303, 'Barillas', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160304, 'Aguacatán', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160305, 'San Rafael Petzal', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160306, 'San Gaspar Ixchil', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160307, 'Santiago Chimaltenango', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160308, 'Santa Ana Huista', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160309, 'Unión Cantinil', 160073, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160310, 'Santa Cruz del Quiché', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160311, 'Chiché', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160312, 'Chinique', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160313, 'Zacualpa', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160314, 'Chajul', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160315, 'Chichicastenango', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160316, 'Patzité', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160317, 'San Antonio Ilotenango', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160318, 'San Pedro Jocopilas', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160319, 'Cunén', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160320, 'San Juan Cotzal', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160321, 'Joyabaj', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160322, 'Nebaj', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160323, 'San Andrés Sajcabajá', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160324, 'Uspantán', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160325, 'Sacapulas', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160326, 'San Bartolomé Jocotenango', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160327, 'Canillá', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160328, 'Chicamán', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160329, 'Ixcán', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160330, 'Pachalum', 160074, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160331, 'Salamá', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160332, 'San Miguel Chicaj', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160333, 'Rabinal', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160334, 'Cubulco', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160335, 'Granados', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160336, 'El Chol', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160337, 'San Jerónimo', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160338, 'Purulhá', 160075, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160339, 'Cobán', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160340, 'Santa Cruz Verapaz', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160341, 'San Cristóbal Verapaz', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160342, 'Tactic', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160343, 'Tamahú', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160344, 'Tucurú', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160345, 'Panzós', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160346, 'Senahú', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160347, 'San Pedro Carchá', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160348, 'San Juan Chamelco', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160349, 'Lanquín', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160350, 'Cahabón', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160351, 'Chisec', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160352, 'Chahal', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160353, 'Fray Bartolomé de las Casas', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160354, 'Santa Catalina la Tinta', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160355, 'Raxruhá', 160076, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160356, 'Flores', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160357, 'San José', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160358, 'San Benito', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160359, 'San Andrés', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160360, 'La Libertad', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160361, 'San Francisco', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160362, 'Santa Ana', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160363, 'Dolores', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160364, 'San Luis', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160365, 'Sayaxché', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160366, 'Melchor de Mencos', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160367, 'Poptún', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160368, 'Las Cruces', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160369, 'El Chal', 160077, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160370, 'Puerto Barrios', 160078, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160371, 'Livingston', 160078, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160372, 'El Estor', 160078, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160373, 'Morales', 160078, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160374, 'Los Amates', 160078, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160375, 'Zacapa', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160376, 'Estanzuela', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160377, 'Río Hondo', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160378, 'Gualán', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160379, 'Teculután', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160380, 'Usumatlán', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160381, 'Cabañas', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160382, 'San Diego', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160383, 'La Unión', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160384, 'Huité', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160385, 'San Jorge', 160079, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160386, 'Chiquimula', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160387, 'San José La Arada', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160388, 'San Juan Ermita', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160389, 'Jocotán', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160390, 'Camotán', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160391, 'Olopa', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160392, 'Esquipulas', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160393, 'Concepción Las Minas', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160394, 'Quetzaltepeque', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160395, 'San Jacinto', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160396, 'Ipala', 160080, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160397, 'Jalapa', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160398, 'San Pedro Pinula', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160399, 'San Luis Jilotepeque', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160400, 'San Manuel Chaparrón', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160401, 'San Carlos Alzatate', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160402, 'Monjas', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160403, 'Mataquescuintla', 160081, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160404, 'Jutiapa', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160405, 'El Progreso', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160406, 'Santa Catarina Mita', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160407, 'Agua Blanca', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160408, 'Asunción Mita', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160409, 'Yupiltepeque', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160410, 'Atescatempa', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160411, 'Jerez', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160412, 'El Adelanto', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160413, 'Zapotitlán', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160414, 'Comapa', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160415, 'Jalpatagua', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160416, 'Conguaco', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160417, 'Moyuta', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160418, 'Pasaco', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160419, 'San José Acatempa', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160420, 'Quesada', 160082, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160421, 'Zona', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160422, 'ZONA 1', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160423, 'ZONA 2', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160424, 'ZONA 3', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160425, 'ZONA 4', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160426, 'ZONA 5', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160427, 'ZONA 6', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160428, 'ZONA 7', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160429, 'ZONA 8', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160430, 'ZONA 9', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160431, 'ZONA 10', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160432, 'ZONA 11', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160433, 'ZONA 12', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160434, 'ZONA 13', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160435, 'ZONA 14', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160436, 'ZONA 15', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160437, 'ZONA 16', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160438, 'ZONA 17', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160439, 'ZONA 18', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160440, 'ZONA 19', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160441, 'ZONA 20', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160442, 'ZONA 21', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160443, 'Sin registrar', 160421, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160444, 'Estados', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160445, 'Activo', 160444, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160446, 'Suspendido', 160444, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160447, 'Inactivo', 160444, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160448, 'Profesiones', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160449, 'Comerciante', 160448, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160450, 'Estudiante', 160448, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160451, 'Graduado', 160448, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160452, 'Numeros de orden', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160453, 'A-01', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160454, 'A-02', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160455, 'A-03', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160456, 'A-04', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160457, 'A-05', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160458, 'A-06', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160459, 'A-07', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160460, 'A-08', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160461, 'A-09', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160462, 'A-10', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160463, 'A-11', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160464, 'A-12', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160465, 'A-13', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160466, 'A-14', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160467, 'A-15', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160468, 'A-16', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160469, 'A-17', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160470, 'A-18', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160471, 'A-19', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160472, 'A-20', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160473, 'A-21', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160474, 'A-22', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160475, 'A-23', 160452, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160476, 'Tipos Direccion', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160477, 'Casa', 160476, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160478, 'Trabajo', 160476, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160479, 'Oficina', 160476, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160480, 'Otros', 160476, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160490, 'Estados De cobro', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160491, 'Pendiente', 160490, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160492, 'En Proceso', 160490, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160499, 'Tipos de Contacto', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160500, 'Amigo', 160499, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160501, 'Familiar', 160499, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160502, 'Compañero', 160499, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160503, 'Conocido', 160499, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160511, 'Privada', 160510, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160512, 'Publica', 160510, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160513, 'Metodos de Pago', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160514, 'TARJETA', 160513, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160515, 'CHEQUE', 160513, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160516, 'EFECTIVO', 160513, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160517, 'PLANILLA', 160513, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160518, 'DEPOSITO', 160513, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160519, 'TIPO DE TARJETA', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160520, 'DEBITO', 160519, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160521, 'CREDITO', 160519, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160522, 'Tipo de Proveedor', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160526, 'Canal de Registro', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160527, 'Pagina Web', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160528, 'Facebook', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160529, 'Twitter', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160530, 'Linkedin', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160531, 'Whatsapp', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160532, 'Call Center', 160526, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160533, 'Roles', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160534, 'IT', 160533, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160535, 'Administrador', 160533, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160536, 'Usuario', 160533, '', '', true, false);
