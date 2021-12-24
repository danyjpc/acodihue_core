


--Updating from Cuenta Corriente to Cuenta Aportacion
UPDATE adm_typology SET description = 'Cuenta Aportacion'
WHERE typology_id = 160579;

--Updating account colors
-- ahorro yellow
UPDATE adm_typology SET value_2 = '#FEB602'
WHERE typology_id = 160578 AND description = 'Cuenta de Ahorro';

--aportacion green
UPDATE adm_typology SET value_2 = '#37AC04'
WHERE typology_id = 160579 AND description = 'Cuenta Aportacion';

--Updating internal account code
--ahorro code 11
UPDATE adm_typology SET value_1 = '11'
WHERE typology_id = 160578 AND description = 'Cuenta de Ahorro';

--aportacion code 10
UPDATE adm_typology SET value_1 = '10'
WHERE typology_id = 160579 AND description = 'Cuenta Aportacion';

--Altering organization, adding internal code
ALTER TABLE adm_organization ADD COLUMN
    internal_code VARCHAR(4) NOT NULL DEFAULT 'C';

--Updating agencies internal code
--Central 'C'
UPDATE adm_organization SET internal_code = 'C'
WHERE organization_name = 'Agencia Central'
  AND organization_commercial = 'Agencia Central'
  AND organization_key = '8feddd0a-9548-4086-a705-d6eaed027120';

--Todo Santos 'TS'

--updating internal code
UPDATE adm_organization SET internal_code = 'TS'
WHERE organization_name = 'Agencia Todo Santos'
  AND organization_commercial = 'Agencia Todo Santos'
  AND organization_key = '58c70553-b5c6-4ee1-a535-b8989adb73fa';

--Soloma 'S'
UPDATE adm_organization SET internal_code = 'S'
WHERE organization_name = 'Agencia San Miguel Soloma'
  AND organization_commercial = 'Agencia San Miguel Soloma'
  AND organization_key = '8fdcadbf-95f0-43a3-81ec-1149660b3441';