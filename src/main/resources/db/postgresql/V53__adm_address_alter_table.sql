
--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2353
-- agregar columna no_book a tabla address
ALTER TABLE adm_address
    ADD COLUMN IF NOT EXISTS no_book varchar(10) not null default 'S/D';

COMMENT ON COLUMN adm_address.no_book IS 'Libro No, sera solo usando cuando se llene la solicitud de credito';

-- https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/2356
-- Crear tabla para almacenar configuraciones golbales

CREATE TABLE IF NOT EXISTS adm_general_config
(
    config_id    SERIAL       NOT NULL PRIMARY KEY,
    config_name  VARCHAR(200) NOT NULL DEFAULT '%',
    config_value TEXT         NOT NULL DEFAULT '%',
    created_by   BIGINT       NOT NULL REFERENCES adm_user(user_id),
    date_created TIMESTAMP    NOT NULL DEFAULT '1900-01-01 00:00:00'::timestamp without time zone,
    status_id    INTEGER      NOT NULL DEFAULT 160445  REFERENCES adm_typology(typology_id)
);

COMMENT ON COLUMN adm_general_config.config_id IS 'es la llave principal';
COMMENT ON COLUMN adm_general_config.config_name IS 'es el nombre de la configuración';
COMMENT ON COLUMN adm_general_config.config_value IS 'es el valor de la configuración';
COMMENT ON COLUMN adm_general_config.created_by IS 'es la persona que realizo la operación';
COMMENT ON COLUMN adm_general_config.date_created IS 'es la fecha hora que se crea la transaccion';
COMMENT ON COLUMN adm_general_config.status_id IS 'es el estado de la transaccion';