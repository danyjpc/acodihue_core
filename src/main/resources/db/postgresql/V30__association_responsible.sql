

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1083
CREATE SEQUENCE IF NOT EXISTS adm_association_responsible_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS adm_association_responsible
(
    association_responsible_id    BIGINT       NOT NULL PRIMARY KEY DEFAULT NEXTVAL('adm_association_responsible_sequence'),
    organization_id               BIGINT       NOT NULL REFERENCES adm_organization(organization_id),
    person_id                     BIGINT       NOT NULL REFERENCES adm_person(person_id),
    admission_date                TIMESTAMP    NOT NULL DEFAULT '1900-01-01 00:00:00',
    discharge_date                TIMESTAMP    NOT NULL DEFAULT '1900-01-01 00:00:00',
    annotation                    VARCHAR(250) NOT NULL DEFAULT 'S/D',
    date_created                  TIMESTAMP    NOT NULL DEFAULT '1900-01-01 00:00:00',
    created_by                    BIGINT       NOT NULL REFERENCES adm_user (user_id),
    status                        BIGINT       NOT NULL DEFAULT 160445 REFERENCES adm_typology(typology_id)
);
COMMENT ON COLUMN adm_association_responsible.association_responsible_id IS 'ID de la tabla';
COMMENT ON COLUMN adm_association_responsible.organization_id            IS 'Asociacion a la que se hace referencia';
COMMENT ON COLUMN adm_association_responsible.person_id                  IS 'Persona a la que hace referencia';
COMMENT ON COLUMN adm_association_responsible.admission_date             IS 'Fecha de ingreso a la asociacion';
COMMENT ON COLUMN adm_association_responsible.discharge_date             IS 'Fecha de egreso de la asociacion';
COMMENT ON COLUMN adm_association_responsible.annotation                 IS 'Comentario sobre el registro';
COMMENT ON COLUMN adm_association_responsible.date_created               IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_association_responsible.created_by                 IS 'ID del Usuario que creo el registro';
COMMENT ON COLUMN adm_association_responsible.status                     IS 'Estado interno del registro (alta / baja)';