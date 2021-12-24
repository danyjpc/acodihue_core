
--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1106/
CREATE SEQUENCE IF NOT EXISTS adm_partner_credit_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS adm_partner_credit
(
    partner_credit_id BIGINT    NOT NULL PRIMARY KEY DEFAULT NEXTVAL('adm_partner_credit_sequence'),
    credit_id         BIGINT    NOT NULL REFERENCES adm_credit(credit_id),
    person_id         BIGINT    NOT NULL REFERENCES adm_person(person_id),
    date_created      TIMESTAMP NOT NULL DEFAULT '1900-01-01 00:00:00',
    created_by        BIGINT    NOT NULL REFERENCES adm_user (user_id),
    status            BIGINT    NOT NULL DEFAULT 160445 REFERENCES adm_typology(typology_id)
);

COMMENT ON COLUMN adm_partner_credit.partner_credit_id IS 'ID del registro';
COMMENT ON COLUMN adm_partner_credit.credit_id         IS 'ID del credito que se vincula';
COMMENT ON COLUMN adm_partner_credit.person_id         IS 'ID de la persona (conyuge/partner) que se vincula al credito';
COMMENT ON COLUMN adm_partner_credit.date_created      IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_partner_credit.created_by        IS 'ID del Usuario que creo el registro';
COMMENT ON COLUMN adm_partner_credit.status            IS 'Estado interno del registro (alta / baja)';