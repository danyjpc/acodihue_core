

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/978

--Renombar la tabla adm_account_balance para guardar unicamente los datos de las cuentas,
ALTER TABLE adm_account_balance
    RENAME TO adm_account;

ALTER TABLE adm_account
    ADD PRIMARY KEY(account_bank_id);
--Se debe renombar la columna primary key

ALTER TABLE adm_account
    RENAME COLUMN account_bank_id to account_id;

--Eliminar el campo  balance initial
ALTER TABLE adm_account
DROP COLUMN balance_initial;

--Rename sequence
ALTER SEQUENCE adm_account_balance_sequence RENAME TO adm_account_sequence;

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/979
--Esta tabla Guardara la informacion de los aportes y retiros de las cuentas

--Crear sequenciador
CREATE SEQUENCE IF NOT EXISTS adm_account_balance_sequence START WITH 1 INCREMENT BY 1;
--crear tabla
CREATE TABLE IF NOT EXISTS adm_account_balance
(
    account_balance_id bigint default nextval('adm_account_balance_sequence') not null primary key ,
    account_id         bigint not null references adm_account(account_id),
    transaction_no     bigint not null unique,
    amount             decimal(17,2) not null default 0,
    transaction_type   bigint not null default 160000 references adm_typology(typology_id),
    created_by         bigint not null default 1 references adm_user(user_id),
    status             bigint not null default 160445 references adm_typology(typology_id),
    date_created       timestamp default '1900-01-01 00:00:00'::timestamp without time zone not null
    );
COMMENT ON COLUMN adm_account_balance.account_balance_id IS 'es la llave principal';
COMMENT ON COLUMN adm_account_balance.account_id IS 'es la llave foranea de la tabla adm_account';
COMMENT ON COLUMN adm_account_balance.transaction_no IS 'es el numero de transaccion que se debe generar cuando se haga un aporte o un retiro';
COMMENT ON COLUMN adm_account_balance.transaction_type IS 'es el tipo de transaccion ver la tarea de catalogos';
COMMENT ON COLUMN adm_account_balance.amount IS 'es el montO por el cual se esta operando la transaccion, recordemos que el saldo final sera calculado dependiendo del tipo de operacion o transaccion.';
--campos de auditoria
COMMENT ON COLUMN adm_account_balance.created_by IS 'es la persona que realiza la operacion';
COMMENT ON COLUMN adm_account_balance.status IS 'es el estado de la transaccion';
COMMENT ON COLUMN adm_account_balance.date_created IS 'es la fecha hora que se crea la transaccion';

