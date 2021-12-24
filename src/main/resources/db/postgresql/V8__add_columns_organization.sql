alter table adm_organization
    add column if not exists is_association boolean default false;
alter table adm_organization
    add column if not exists interest_rate decimal(17, 2) not null default 0.00;

comment on column adm_organization.is_association is 'esta condicion  indica is la organizacion que se persiste es una agencia';
comment on column adm_organization.interest_rate is 'este valor indica la tasa de interes para sus beneficiarios';