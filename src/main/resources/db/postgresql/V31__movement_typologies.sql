

--Updating typology catalog
---Haber
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160602, 160580, 'Pago de Intereses','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160603, 160580, 'Concesion Credito','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160604, 160580, 'Abono a Capital','N');

--Debe
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160605, 160583, 'Deposito','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160606, 160583, 'Amortizacion','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160607, 160583, 'Abono a Capital','N');

INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160608, 160583, 'Intereses sobre Credito','N');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160609, 160583, 'Avaluo / Inspeccion','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160610, 160583, 'Otros ingresos,','S');


INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160611, 160583, 'Nota de debito','N');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160612, 160583, 'Cuota Administrativa','S');
INSERT INTO adm_typology(typology_id, parent_typology_id, description, value_2) VALUES(160613, 160583, 'Pago de Mora','N');

--Nota de credito
UPDATE adm_typology SET value_2 = 'N' WHERE typology_id = 160582;
--Retiro
UPDATE adm_typology SET value_2 = 'S' WHERE typology_id = 160581;
--Cuota inicial
UPDATE adm_typology SET value_2 = 'S' WHERE typology_id = 160584;
--Cuota Inscripcion
UPDATE adm_typology SET value_2 = 'S' WHERE typology_id = 160585;
--Aporte extraordinario
UPDATE adm_typology SET value_2 = 'S' WHERE typology_id = 160586;

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1108/
--agregar relacion con calculator

--converting credit calculator_id into primary key
ALTER TABLE adm_calculator ADD PRIMARY KEY(calculator_id);

--Add relation to adm_calculator
ALTER TABLE adm_credit ADD COLUMN IF NOT EXISTS calculator_id BIGINT REFERENCES adm_calculator(calculator_id);

--Remove interest_percentage
ALTER TABLE adm_credit DROP COLUMN IF EXISTS interest_percentage;

--Remove mount
ALTER TABLE adm_credit DROP COLUMN IF EXISTS mount;

--Remove partner_account_id
ALTER TABLE adm_credit DROP COLUMN IF EXISTS partner_account_id;

--Profesion
ALTER TABLE adm_credit ADD COLUMN IF NOT EXISTS profession_id BIGINT NOT NULL DEFAULT 160000 REFERENCES adm_typology(typology_id);

--Ocupacion
ALTER TABLE adm_credit ADD COLUMN IF NOT EXISTS occupation_id BIGINT NOT NULL DEFAULT 160000 REFERENCES adm_typology(typology_id);

--Casa propia
ALTER TABLE adm_credit ADD COLUMN IF NOT EXISTS own_house BOOLEAN NOT NULL DEFAULT FALSE;


