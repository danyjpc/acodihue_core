
--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2126
--Ingreso credito
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160700, 'Ingresos de credito', 160683, '%', '%', true, false);

--Ingresos
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160702, 'Venta de productos o servicios', 160700, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160703, 'Sueldos', 160700, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160704, 'Jornales', 160700, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160705, 'Otros, venta de cafe', 160700, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160706, 'Efectivo', 160700, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160707, 'Prestamo', 160700, '%', '%', true, false);

--Egreso de credito
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160701, 'Egresos de credito', 160683, '%', '%', true, false);

--Egresos
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160708, 'Costos directos', 160701, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160709, 'Gastos familiares', 160701, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160710, 'Alquileres / Jornales', 160701, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160711, 'Arrendamiento', 160701, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160712, 'Amortizacion de prestamo', 160701, '%', '%', true, false);

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES( 160713, 'Servicios', 160701, '%', '%', true, false);



--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2153

--Creating new table for balance sheet (estado patrimonial)
CREATE SEQUENCE IF NOT EXISTS adm_credit_income_expense_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE adm_credit_income_expense
(
    credit_income_expense_id BIGINT      DEFAULT
                                         nextval('adm_credit_income_expense_sequence'::regclass)               NOT NULL,
    account_type                     BIGINT    DEFAULT 160000                                             NOT NULL
        CONSTRAINT adm_credit_income_expense_account_type_fkey
            REFERENCES adm_typology,
    amount                           NUMERIC(17, 2)  DEFAULT 0                                             NOT NULL,
    credit_id                        BIGINT                                                                NOT NULL
        CONSTRAINT adm_bcredit_income_expense_id_fkey
            REFERENCES adm_credit,
    created_by                       BIGINT    DEFAULT 1                                                   NOT NULL
        CONSTRAINT aadm_bcredit_income_expense_created_by_fkey
            REFERENCES adm_user,
    date_created                     TIMESTAMP
        DEFAULT '1900-01-01 00:00:00'::timestamp without time zone  NOT NULL,
    status                           BIGINT    DEFAULT 1604445                                           NOT NULL
        CONSTRAINT adm_bcredit_income_expense_status_fkey
            REFERENCES adm_typology

);

--Comentarios para adm_credit_income_expense
COMMENT ON COLUMN adm_credit_income_expense.credit_income_expense_id IS 'ID del registro';
COMMENT ON COLUMN adm_credit_income_expense.account_type         IS 'Tipologia para tipo de movimiento, Ingreso, Egreso';
COMMENT ON COLUMN adm_credit_income_expense.amount               IS 'Cantidad asociada a la cuenta';
COMMENT ON COLUMN adm_credit_income_expense.credit_id            IS 'ID del credito al que se asocia la cuenta';
COMMENT ON COLUMN adm_credit_income_expense.created_by           IS 'Id de usuario que crea el registro';
COMMENT ON COLUMN adm_credit_income_expense.date_created         IS 'Fecha de creacion del registro';
COMMENT ON COLUMN adm_credit_income_expense.status               IS 'Tipologia para el estado del registro';