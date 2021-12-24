
-- https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2367/
-- Alterar tabla de guarantias para agregar nuevos campos

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS document_notary varchar(50) not null default 'S/D';

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS document_date timestamp not null default '1900-01-01 00:00:00';

COMMENT ON COLUMN adm_guarantee.document_notary IS 'es nombre del notario que facciono la escritura puesta en garantia';
COMMENT ON COLUMN adm_guarantee.document_date IS 'es la fecha de la escritura que queda en garantia';

insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160714, 'Pública', 160620, '%', '%', true, false),
       (160715, 'Municipal', 160620, '%', '%', true, false);

ALTER TABLE adm_partner_credit
    ADD COLUMN IF NOT EXISTS loader boolean not null default false;

COMMENT ON COLUMN adm_partner_credit.loader IS 'es una bandera para el fiador principal';