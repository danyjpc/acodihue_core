


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1037/

CREATE SEQUENCE IF NOT EXISTS adm_credit_line_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS adm_credit_line
(
    credit_line_id    BIGINT       NOT NULL PRIMARY KEY DEFAULT NEXTVAL('adm_credit_line_sequence'),
    organization_id   BIGINT       NOT NULL DEFAULT 0 REFERENCES adm_organization(organization_id),
    description       VARCHAR(50)  NOT NULL DEFAULT 'S/D',
    date_created      TIMESTAMP    NOT NULL DEFAULT '1900-01-01 00:00:00',
    created_by        BIGINT       NOT NULL DEFAULT 0 REFERENCES adm_user (user_id),
    status            BIGINT       NOT NULL DEFAULT 160445 REFERENCES adm_typology(typology_id)
);
COMMENT ON COLUMN adm_credit_line.credit_line_id  IS 'ID de la tabla';
COMMENT ON COLUMN adm_credit_line.organization_id IS 'Organizacion a la que pertenece la linea de credito';
COMMENT ON COLUMN adm_credit_line.description     IS 'Descripcion de la linea de credito';
COMMENT ON COLUMN adm_credit_line.date_created    IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_credit_line.created_by      IS 'ID del Usuario que creo el registro';
COMMENT ON COLUMN adm_credit_line.status          IS 'Estado interno del registro (alta / baja)';




--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1039/
CREATE SEQUENCE IF NOT EXISTS adm_calculator_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS adm_calculator
(
    calculator_id    BIGINT         NOT NULL DEFAULT NEXTVAL('adm_calculator_sequence'),
    person_id        BIGINT         NOT NULL DEFAULT 0 REFERENCES adm_person(person_id),
    application_date TIMESTAMP      NOT NULL DEFAULT '1900-01-01 00:00:00',
    no_period        BIGINT         NOT NULL DEFAULT 0,
    no_payments      BIGINT         NOT NULL DEFAULT 0,
    credit           NUMERIC(17, 2) NOT NULL DEFAULT 0,
    interest_rate    NUMERIC(17, 2) NOT NULL DEFAULT 0,
    interest_final   NUMERIC(17, 2) NOT NULL DEFAULT 0,
    date_created     TIMESTAMP      NOT NULL DEFAULT '1900-01-01 00:00:00',
    created_by       BIGINT         NOT NULL DEFAULT 0 REFERENCES adm_user (user_id),
    status            BIGINT       NOT NULL DEFAULT 160445 REFERENCES adm_typology(typology_id)
);

COMMENT ON COLUMN adm_calculator.calculator_id    IS 'ID de la tabla';
COMMENT ON COLUMN adm_calculator.person_id        IS 'ID del solicitante';
COMMENT ON COLUMN adm_calculator.application_date IS 'Fecha de aplicacion del calculo';
COMMENT ON COLUMN adm_calculator.no_period        IS 'Numero de periodo';
COMMENT ON COLUMN adm_calculator.no_payments      IS 'Numero de pagos';
COMMENT ON COLUMN adm_calculator.credit           IS 'Monto del credito solicitado';
COMMENT ON COLUMN adm_calculator.interest_rate    IS 'Tasa de interes';
COMMENT ON COLUMN adm_calculator.interest_final   IS 'Interes final';
COMMENT ON COLUMN adm_calculator.date_created     IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_calculator.created_by       IS 'ID del Usuario que creo el registro';
COMMENT ON COLUMN adm_credit_line.status          IS 'Estado interno del registro (alta / baja)';

--removing adm_account account_number account_order
ALTER TABLE adm_account DROP CONSTRAINT unique_account_number_order;

--Updating account types
UPDATE adm_typology SET description = 'Cuenta de Ahorro Corriente' WHERE typology_id = 160578;

--New account type
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2)
VALUES(160601 ,'Cuenta de Ahorro a Plazo Fijo',160577,11,'#FEB602');