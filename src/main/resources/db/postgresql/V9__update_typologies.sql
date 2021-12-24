ALTER TABLE adm_person
ADD COLUMN IF NOT EXISTS is_associate BOOLEAN DEFAULT FALSE NOT NULL;
comment on column adm_person.is_associate is 'este valor indica si la persona es un asociado';

--Faltaba este tipo de direccion
insert into adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
values
(160481, 'Facturacion', 160476, '', '', true, false);

--Tipos de telefono tenia la descripcion en value_1
UPDATE adm_typology
SET description = value_1, value_1 = ''
WHERE typology_id
IN (160537, 160538, 160539, 160540, 160541, 160542, 160543);

--Typologias Catalogos de pueblos tenian otra tipologia como padre
UPDATE adm_typology
SET parent_typology_id = 160550
WHERE typology_id
IN(160551,160552,160553,160554);

--Typologias Catalogos de garantias tenian otra tipologia como padre
UPDATE adm_typology
SET parent_typology_id = 160555
WHERE typology_id
IN(160556,160557,160558,160559);

--Tipologias para tipo de direccion duplicadas
DELETE FROM adm_typology
WHERE adm_typology.parent_typology_id = 160544;

DELETE FROM adm_typology
WHERE adm_typology.typology_id = 160544;