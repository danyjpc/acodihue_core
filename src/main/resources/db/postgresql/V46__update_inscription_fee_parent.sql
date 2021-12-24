


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1386/
--Add extra catalog and update existing inscription fee typology

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES
( 160676, 'Ingresos Cooperativa', 100, '%', '%', true, false);

UPDATE adm_typology SET parent_typology_id = 160676 WHERE typology_id = 160585;




