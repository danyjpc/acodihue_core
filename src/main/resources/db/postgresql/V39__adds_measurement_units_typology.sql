

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1207/

INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES (160622, 'Unidades de medida', 100, '%', '%', true, false),
       (160623, 'Quintal'           , 160622, '%', '%', true, false),
       (160624, 'Libra'             , 160622, '%', '%', true, false),
       (160625, 'Kilogramo'         , 160622, '%', '%', true, false),
       (160626, 'Onza'              , 160622, '%', '%', true, false);