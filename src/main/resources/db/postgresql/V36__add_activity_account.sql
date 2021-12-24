create sequence if not exists adm_activity_account_sequence increment by 1 start with 1;

create table if not exists adm_activity_account
(
    activity_account_id bigint    not null default nextval('adm_activity_account_sequence') primary key,
    date_created        timestamp not null default '1900-01-01 00:00:00'
);

alter table adm_activity
    drop column credit_id;

alter table adm_activity
    add column if not exists activity_account_id bigint default 0 references adm_activity_account (activity_account_id);