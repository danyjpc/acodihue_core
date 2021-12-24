


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1389/
--Unify destiny and activity

--Agricola will be parent of Producion de Cafe
UPDATE adm_typology SET parent_typology_id = 160615 WHERE typology_id = 160619;

--Hija de ganaderia
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES
( 160677, 'Crianza y Reproduccion de Ganado', 160616, '%', '%', true, false);

--Hija de micro empresarial
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES
( 160678, 'Compra de Mercaderia', 160617, '%', '%', true, false);




