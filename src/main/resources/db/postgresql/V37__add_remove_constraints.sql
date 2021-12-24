alter table adm_guarantee
    drop constraint if exists adm_guarantee_state_fkey;

alter table adm_guarantee
    drop constraint if exists adm_guarantee_city_fkey;

alter table adm_guarantee
    drop constraint if exists adm_guarantee_evaluator_fkey;


alter table adm_guarantee
    drop column if exists no_farm;
alter table adm_guarantee
    drop column if exists no_register;
alter table adm_guarantee
    drop column if exists no_book;
alter table adm_guarantee
    drop column if exists no_record;

alter table adm_guarantee
    alter column evaluator drop not null;

