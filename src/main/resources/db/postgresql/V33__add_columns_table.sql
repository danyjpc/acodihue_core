alter table adm_credit
    drop column if exists dead_line;
alter table adm_credit
    drop column if exists interest_percentage;
alter table adm_credit
    drop column if exists dead_line_date;
alter table adm_credit
    drop column if exists payment_type;
alter table adm_credit
    drop column if exists credit_type;
alter table adm_credit
    drop column if exists mount;
alter table adm_credit
    drop column if exists partner_account_id;

alter table adm_credit
    add column if not exists credit_key uuid not null default '00000000-0000-0000-0000-000000000000';

create sequence if not exists adm_activity_sequence start with 1 increment by 1;

create table adm_activity
(
    activity_id       bigint         not null default nextval('adm_activity_sequence'),
    credit_id         bigint         not null default 0 references adm_credit (credit_id),
    destiny           bigint         not null default 160000 references adm_typology (typology_id),
    activity_economic bigint         not null default 160000 references adm_typology (typology_id),
    is_apparel        boolean        not null default false,
    is_fiduciary      boolean        not null default false,
    area              decimal(17, 2) not null default 0.00,
    unit_measure      bigint         not null default 160000 references adm_typology (typology_id),
    price             decimal(17, 2) not null default 0.00,
    earnings          decimal(17, 2) not null default 0.00,
    created_by        bigint         not null default 0 references adm_user (user_id),
    date_created      timestamp      not null default '1900-01-01 00:00:00',
    leader            boolean        not null default false
);

COMMENT ON COLUMN adm_activity.activity_id IS 'ID del registro';
COMMENT ON COLUMN adm_activity.credit_id IS 'ID del credito que se vincula';
COMMENT ON COLUMN adm_activity.activity_economic IS 'Proviene de un catalogo de actividades economicas';
COMMENT ON COLUMN adm_activity.is_apparel IS 'Si es Prendario o no';
COMMENT ON COLUMN adm_activity.is_fiduciary IS 'Si es fiduciario o no';
COMMENT ON COLUMN adm_activity.area IS 'Area de produccion';
COMMENT ON COLUMN adm_activity.unit_measure IS 'Unidad de Medida';
COMMENT ON COLUMN adm_activity.price IS 'Precio De inversion';
COMMENT ON COLUMN adm_activity.earnings IS 'Ganancias obtenidas';
COMMENT ON COLUMN adm_activity.created_by IS 'Quien crea la actividad';
COMMENT ON COLUMN adm_activity.created_by IS 'Fecha en la que se crea el registro';
COMMENT ON COLUMN adm_activity.leader IS 'Si es la actividad principal o no';

-- this columns change in ech credit
COMMENT ON COLUMN adm_credit.profession_id IS 'Profesion';
COMMENT ON COLUMN adm_credit.occupation_id IS 'Ocupacion';
COMMENT ON COLUMN adm_credit.own_house IS 'Si es casa propia';
