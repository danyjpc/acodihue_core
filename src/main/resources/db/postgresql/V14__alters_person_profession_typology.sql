

ALTER TABLE adm_person DROP COLUMN IF EXISTS profession;
ALTER TABLE adm_person
ADD COLUMN IF NOT EXISTS profession_id BIGINT REFERENCES adm_typology(typology_id) DEFAULT 160000;