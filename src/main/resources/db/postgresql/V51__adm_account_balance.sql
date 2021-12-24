

--Comments for adm_account_allowed_movements
COMMENT ON COLUMN adm_account_allowed_movements.adm_account_allowed_movements_id IS 'ID del registro';
COMMENT ON COLUMN adm_account_allowed_movements.account_type IS 'Tipologia para tipo de cuenta, ahorro, plazo fijo, etc';
COMMENT ON COLUMN adm_account_allowed_movements.operation IS 'Tipologia para operacion, deposito, retiro, nota de credito';
COMMENT ON COLUMN adm_account_allowed_movements.operation_type IS 'Tipologia para tipo de operacion, Debe o Haber';
COMMENT ON COLUMN adm_account_allowed_movements.created_by IS 'Id de usuario que crea el registro';
COMMENT ON COLUMN adm_account_allowed_movements.date_created IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_account_allowed_movements.status IS 'Tipologia para el estado del registro';


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2108
--Tipologias para DEBE y HABER de las cuentas de ahorro y aportacion
--Cuenta aportacion
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160579, 160584, 160580, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160579, 160586, 160580, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160579, 160605, 160580, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160579, 160581, 160583, 1, now(), 160445);


--Cuenta ahorro corriente
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160605, 160580, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160582, 160583, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160581, 160583, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160679, 160583, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160680, 160583, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160681, 160583, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160682, 160580, 1, now(), 160445);
INSERT INTO adm_account_allowed_movements(account_type, operation, operation_type, created_by, date_created, status)VALUES(160578, 160611, 160583, 1, now(), 160445);


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2122
--Creating new table for balance sheet (estado patrimonial)
CREATE SEQUENCE IF NOT EXISTS adm_balance_sheet_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE adm_balance_sheet
(
    adm_balance_sheet_id BIGINT      DEFAULT
                                         nextval('adm_balance_sheet_sequence'::regclass)               NOT NULL,
    account_type                     BIGINT    DEFAULT 160000                                             NOT NULL
        CONSTRAINT adm_balance_sheet_account_type_fkey
            REFERENCES adm_typology,
    amount                           NUMERIC(17, 2)  DEFAULT 0                                             NOT NULL,
    credit_id                        BIGINT                                                                NOT NULL
        CONSTRAINT adm_balance_sheet_credit_id_fkey
            REFERENCES adm_credit,
    created_by                       BIGINT    DEFAULT 1                                                   NOT NULL
        CONSTRAINT aadm_balance_sheets_created_by_fkey
            REFERENCES adm_user,
    date_created                     TIMESTAMP
        DEFAULT '1900-01-01 00:00:00'::timestamp without time zone  NOT NULL,
    status                           BIGINT    DEFAULT 1604445                                           NOT NULL
        CONSTRAINT adm_balance_sheet_status_fkey
            REFERENCES adm_typology

);

--Comentarios para adm_balance_sheet
COMMENT ON COLUMN adm_balance_sheet.adm_balance_sheet_id IS 'ID del registro';
COMMENT ON COLUMN adm_balance_sheet.account_type         IS 'Tipologia para tipo de cuenta, Activo, Pasivo';
COMMENT ON COLUMN adm_balance_sheet.amount               IS 'Cantidad asociada a la cuenta';
COMMENT ON COLUMN adm_balance_sheet.credit_id            IS 'ID del credito al que se asocia la cuenta';
COMMENT ON COLUMN adm_balance_sheet.created_by           IS 'Id de usuario que crea el registro';
COMMENT ON COLUMN adm_balance_sheet.date_created         IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_balance_sheet.status               IS 'Tipologia para el estado del registro';



--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2123
--Ingreso de tipologias
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160683, 'Contabilidad', 100, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160684, 'Activo', 160683, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160685, 'Pasivo', 160683, '%', '%', true, false);

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2123
--Cuentas de Activo 160684
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160686, 'Efectivo', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160687, 'Depositos en Bancos o Cooperativoas', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160688, 'Cuentas por Cobrar', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160689, 'Productos a la Venta', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160690, 'Casas o Construcciones', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160691, 'Terrenos sin Cultivo', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160692, 'Terrenos con Cultivo', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160693, 'Inversiones en Acciones Banrural u otros', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160694, 'Vehiculos', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160695, 'Maquinaria, Equipo Agricola, Herramientas', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160696, 'Muebles, Aparatos y otros', 160684, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160697, 'Ganado (Animales)', 160684, '160683', '%', true, false);

--Cuntas de Pasivo 160685
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160698, 'Cuentas por Pagar a Corto Plazo', 160685, '160683', '%', true, false);
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable) VALUES( 160699, 'Cuentas por Pagar a Largo Plazo', 160685, '160683', '%', true, false);