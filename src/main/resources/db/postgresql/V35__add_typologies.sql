insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160614, 'Destinos', 100, '%', '%', true, false),
       (160615, 'Agricola', 160614, '%', '%', true, false),
       (160616, 'Ganadero', 160614, '%', '%', true, false),
       (160617, 'MicroEmpresarial', 160614, '%', '%', true, false);



insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160618, 'Actividad Economica', 100, '%', '%', true, false),
       (160619, 'Produccion de Cafe', 160618, '%', '%', true, false);


insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160620, 'Tipo de Escritura', 100, '%', '%', true, false),
       (160621, 'Registrada', 160620, '%', '%', true, false);



create sequence if not exists adm_guarantee_sequence increment by 1 start with 1;

create table if not exists adm_guarantee
(
    guarantee_id         bigint         not null default nextval('adm_guarantee_sequence') primary key,
    credit_id            bigint         not null default 0 references adm_credit (credit_id),
    no_year              bigint         not null default 0,
    no_reference         bigint         not null default 0,
    name_farm            varchar(500)   not null default '%',
    owner                varchar(100)   not null default '%',
    address_account_id   bigint         not null default 0 references adm_address_account (address_account_id),
    document_type        bigint         not null default 160000 references adm_typology (typology_id),
    testimony            bigint         not null default 0,
    no_record            bigint         not null default 0,
    no_farm              bigint         not null default 0,
    no_register          bigint         not null default 0,
    no_book              bigint         not null default 0,
    rope_value           decimal(17, 2) not null default 0,
    area_meters          decimal(17, 2) not null default 0,
    msnm                 decimal(17, 2) not null default 0,
    topography           text           not null default '%',
    hydrography          text           not null default '%',
    soil_quality         text           not null default '%',
    plan_cover           text           not null default '%',
    cultivate_detail     text           not null default '%',
    farm_neighbor        text           not null default '%',
    risk_class_form      text           not null default '%',
    irrigation_extension text           not null default '%',
    building_detail      text           not null default '%',
    annotation           text           not null default '%',
    north_origin         text           not null default '%',
    south_origin         text           not null default '%',
    orient_origin        text           not null default '%',
    west_origin          text           not null default '%',
    communication_routes text           not null default '%',
    state                bigint         not null default 160000 references adm_typology (typology_id),
    city                 bigint         not null default 160000 references adm_typology (typology_id),
    to_city              text           not null default '%',
    evaluator            bigint         not null default 0 references adm_person (person_id),
    date_created         timestamp      not null default '1900-01-01 00:00:00',
    created_by           bigint         not null default 0 references adm_user (user_id),
    status               bigint         not null default 160445 references adm_typology (typology_id)
);

COMMENT ON COLUMN adm_guarantee.guarantee_id IS 'ID del registro';
COMMENT ON COLUMN adm_guarantee.no_year IS 'Año en la que se crea la garantia';
COMMENT ON COLUMN adm_guarantee.no_reference IS 'Numero de referencia de la garantia';
COMMENT ON COLUMN adm_guarantee.name_farm IS 'Nombre de la finca';
COMMENT ON COLUMN adm_guarantee.owner IS 'Propietario';
COMMENT ON COLUMN adm_guarantee.address_account_id IS 'Direccion de la finca';
COMMENT ON COLUMN adm_guarantee.document_type IS 'Tipo de Escritura';
COMMENT ON COLUMN adm_guarantee.testimony IS 'Testimonio';
COMMENT ON COLUMN adm_guarantee.no_record IS 'No de Escritura';
COMMENT ON COLUMN adm_guarantee.no_farm IS 'No de Finca';
COMMENT ON COLUMN adm_guarantee.no_register IS 'No de folio';
COMMENT ON COLUMN adm_guarantee.no_book IS 'No de libro';
COMMENT ON COLUMN adm_guarantee.rope_value IS 'Valor de la cuerda';
COMMENT ON COLUMN adm_guarantee.area_meters IS 'Area en metros';
COMMENT ON COLUMN adm_guarantee.msnm IS 'msnm';
COMMENT ON COLUMN adm_guarantee.topography IS 'Topografia';
COMMENT ON COLUMN adm_guarantee.hydrography IS 'hidrografia';
COMMENT ON COLUMN adm_guarantee.soil_quality IS 'calidad del suelo';
COMMENT ON COLUMN adm_guarantee.plan_cover IS 'Cubierta Vegetal';
COMMENT ON COLUMN adm_guarantee.cultivate_detail IS 'Detalles de los cultivos';
COMMENT ON COLUMN adm_guarantee.farm_neighbor IS 'Explotacion de las fincas vecinas';
COMMENT ON COLUMN adm_guarantee.risk_class_form IS 'Riesgo de Clase y Forma';
COMMENT ON COLUMN adm_guarantee.irrigation_extension IS 'Extension del riego';
COMMENT ON COLUMN adm_guarantee.building_detail IS 'Detalle de las construcciones';
COMMENT ON COLUMN adm_guarantee.annotation IS 'Anotaciones Generales';
COMMENT ON COLUMN adm_guarantee.north_origin IS 'Norte';
COMMENT ON COLUMN adm_guarantee.south_origin IS 'SUR';
COMMENT ON COLUMN adm_guarantee.orient_origin IS 'ORIENTE';
COMMENT ON COLUMN adm_guarantee.west_origin IS 'PONIENTE';
COMMENT ON COLUMN adm_guarantee.communication_routes IS 'Vias de comunicacion';
COMMENT ON COLUMN adm_guarantee.state IS 'Cabezera departamental';
COMMENT ON COLUMN adm_guarantee.city IS 'Cabezera Municipal';
COMMENT ON COLUMN adm_guarantee.to_city IS 'a Guatemala o A la ciudad';
COMMENT ON COLUMN adm_guarantee.evaluator IS 'Evaluador';

alter table adm_activity
    add column if not exists status bigint not null default 1604445 references adm_typology (typology_id);