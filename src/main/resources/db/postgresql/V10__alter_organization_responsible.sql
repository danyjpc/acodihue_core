ALTER TABLE adm_organization_responsible
ADD COLUMN IF NOT EXISTS is_responsable BOOLEAN DEFAULT FALSE NOT NULL;

ALTER TABLE adm_organization_responsible
ADD COLUMN IF NOT EXISTS  status bigint not null default 160445 references adm_typology (typology_id);

--Estados de preinscripcion
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160560, 'Estado de Preinscripcion', 100, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160561, 'Pendiente', 160560, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160562, 'Autorizado', 160560, '', '', true, false);
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values (160563, 'En Proceso', 160560, '', '', true, false);


CREATE SEQUENCE IF NOT EXISTS adm_preinscription_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS adm_preinscription(
    preinscription_id               BIGINT NOT NULL DEFAULT NEXTVAL('adm_preinscription_sequence'::regclass) PRIMARY KEY,
    organization_responsible_id     BIGINT NOT NULL DEFAULT 0 REFERENCES adm_organization_responsible(organization_responsible_id),
    membership_number               BIGINT NOT NULL DEFAULT 0,
    status                          BIGINT NOT NULL DEFAULT 160445 REFERENCES adm_typology(typology_id),
    membership_status               BIGINT NOT NULL DEFAULT 160561 REFERENCES adm_typology(typology_id),
    authorized_by                   BIGINT NOT NULL DEFAULT 0,
    created_by                      BIGINT NOT NULL DEFAULT 0,
    rejected_by                     BIGINT NOT NULL DEFAULT 0,
    date_approved                   TIMESTAMP NOT NULL DEFAULT '1900-01-01 00:00:00',
    date_rejected                   TIMESTAMP NOT NULL DEFAULT '1900-01-01 00:00:00',
    date_created                    TIMESTAMP NOT NULL DEFAULT '1900-01-01 00:00:00'
);

comment ON COLUMN adm_preinscription.membership_number      is 'numero de asociado';
comment ON COLUMN adm_preinscription.status                 is 'estado interno del registro, activo, inactivo';
comment ON COLUMN adm_preinscription.membership_status      is 'estado de la preinscripcion, pendiente, autorizado, en proceso';
comment ON COLUMN adm_preinscription.authorized_by          is 'usuario que autorizo la preinscripcion';
comment ON COLUMN adm_preinscription.created_by             is 'usuario que creo la preinscripcion';
comment ON COLUMN adm_preinscription.rejected_by            is 'usuario que rechazo la preinscripcion';
comment ON COLUMN adm_preinscription.date_approved          is 'fecha de aprobacion de la preinscripcion';
comment ON COLUMN adm_preinscription.date_rejected          is 'fecha en que se rechazo la preinscripcion';
comment ON COLUMN adm_preinscription.date_created           is 'fecha en la que se creo el registro';

