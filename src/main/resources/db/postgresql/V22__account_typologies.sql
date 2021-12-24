

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/980

--Catalogos de tipo de cuenta

--Tipos de cuenta
INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160577, 100, 'Tipo de cuenta');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160578, 160577, 'Cuenta de Ahorro');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160579, 160577, 'Cuenta Corriente');

--Transacciones debe
INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160580, 100, 'Tipo Transaccion DEBE');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160581, 160580, 'Retiro');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160582, 160580, 'Nota de Credito');

--Transacciones haber
INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160583, 100, 'Tipo Transaccion HABER');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160584, 160583, 'Cuota Inicial');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160585, 160583, 'Cuota Inscripcion');

INSERT INTO adm_typology(typology_id, parent_typology_id, description)
VALUES(160586, 160583, 'Aporte Extraordinario');


