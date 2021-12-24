

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1209/

--Add quantity field to table
ALTER TABLE adm_activity
    ADD COLUMN quantity BIGINT NOT NULL DEFAULT 0;

--Add comment for new field
COMMENT ON COLUMN adm_activity.quantity IS 'Cantidad para la actividad';