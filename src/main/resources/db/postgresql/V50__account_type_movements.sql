


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2108
--Create new table for account type and movements allowed

--Creating new typologies for new movements
--Haber
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160679, 160580, 'Nota de débito, pago de interes','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160680, 160580, 'Nota de débito, pago de mora','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160681, 160580, 'Nota de débito, pago a capital','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160682, 160580, 'Nota de débito, pago de impuesto','S');
--updating nota de credito to haber
UPDATE adm_typology SET parent_typology_id = 160583 WHERE typology_id = 160582;



--Creating new table for account movements
CREATE SEQUENCE IF NOT EXISTS adm_account_allowed_movements_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE adm_account_allowed_movements
(
    adm_account_allowed_movements_id BIGINT    DEFAULT nextval('adm_account_allowed_movements_sequence'::regclass) NOT NULL,
    account_type                     BIGINT    DEFAULT 160000                                                      NOT NULL
        CONSTRAINT adm_account_allowed_movements_account_type_fkey
            REFERENCES adm_typology,
    operation                        BIGINT    DEFAULT 160000                                                      NOT NULL
        CONSTRAINT adm_account_allowed_movements_operation_fkey
            REFERENCES adm_typology,
    operation_type                   BIGINT    DEFAULT 160000                                                      NOT NULL
        CONSTRAINT adm_account_allowed_movements_operation_type_fkey
            REFERENCES adm_typology,
    created_by                       BIGINT    DEFAULT 1                                                           NOT NULL
        CONSTRAINT adm_account_allowed_movements_created_by_fkey
            REFERENCES adm_user,
    date_created                     TIMESTAMP DEFAULT '1900-01-01 00:00:00'::timestamp without time zone          NOT NULL,
    status                           BIGINT    DEFAULT 160445                                                     NOT NULL
        CONSTRAINT adm_account_allowed_movements_status_fkey
            REFERENCES adm_typology
);






