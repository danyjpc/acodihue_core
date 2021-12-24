


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1464/
--Add field for storing account interest percentage

--Add value_3
ALTER TABLE adm_typology ADD COLUMN value_3 VARCHAR(100) DEFAULT '' NOT NULL;

--Updating for temporal interest value
--Cuenta Aportacion
UPDATE adm_typology SET value_3 = '19'
WHERE typology_id = 160579;

--Cuenta de Ahorro Corriente
UPDATE adm_typology SET value_3 = '20'
WHERE typology_id = 160578;

--Cuenta de Ahorro a Plazo Fijo
UPDATE adm_typology SET value_3 = '21'
WHERE typology_id = 160601;







