
-- https://dev.azure.com/people-apps/Sistema%20Acodihue/_workitems/edit/2369
-- Alterar tabla de guarantias para agregar nuevos campos

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS ownership_rights varchar(300) not null default 'S/D';

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS no_rope double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS no_hectares double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS cost_per_square_meter double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS height_above_sea_level double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS value_of_permanent_crops double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS value_of_buildings double precision not null default 0;

ALTER TABLE adm_guarantee
    ADD COLUMN IF NOT EXISTS annotation_2 varchar(300) not null default 'S/D';

COMMENT ON COLUMN adm_guarantee.ownership_rights IS 'es un valor que sera usado en las garantias';
COMMENT ON COLUMN adm_guarantee.no_rope IS 'numero de cuerdas de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.no_hectares IS 'numero de hectareas de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.cost_per_square_meter IS 'costo de metros cuadrados de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.height_above_sea_level IS 'altura sobre el nivel del mar de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.value_of_permanent_crops IS 'valor de los cultivos permanentes de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.value_of_buildings IS 'valor de las construcciones de un terreno para las garantias';
COMMENT ON COLUMN adm_guarantee.annotation_2 IS 'anotaciones de un terreno para las garantias';