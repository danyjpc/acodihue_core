


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1136/
--Add field
ALTER TABLE adm_person ADD COLUMN linguistic_community_id BIGINT DEFAULT 160000 NOT NULL;

ALTER TABLE adm_person
ADD CONSTRAINT adm_person_linguistic_community_fkey
FOREIGN KEY(linguistic_community_id)
REFERENCES adm_typology(typology_id);

--Add catalogues for linguistic community
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES
( 160627, 'Comunidad lingüística', 100, '%', '%', true, false),
( 160628, 'Achi'                 , 160627, '%', '%', true, false),
( 160629, 'Akateko'              , 160627, '%', '%', true, false),
( 160630, 'Awakateko'            , 160627, '%', '%', true, false),
( 160631, 'Ch''orti'''           , 160627, '%', '%', true, false),
( 160632, 'Chalchiteko'          , 160627, '%', '%', true, false),
( 160633, 'Chuj'                 , 160627, '%', '%', true, false),
( 160634, 'Español'              , 160627, '%', '%', true, false),
( 160635, 'Garífuna'             , 160627, '%', '%', true, false),
( 160636, 'Itza'''               , 160627, '%', '%', true, false),
( 160637, 'Ixil'                 , 160627, '%', '%', true, false),
( 160638, 'Jakalteco (o Popti'')', 160627, '%', '%', true, false),
( 160639, 'K''iche'''            , 160627, '%', '%', true, false),
( 160640, 'Kaqchikel'            , 160627, '%', '%', true, false),
( 160641, 'Mam'                  , 160627, '%', '%', true, false),
( 160642, 'Mopan'                , 160627, '%', '%', true, false),
( 160643, 'Poqomam'              , 160627, '%', '%', true, false),
( 160644, 'Poqomchi'''           , 160627, '%', '%', true, false),
( 160645, 'Q''eqchi'''           , 160627, '%', '%', true, false),
( 160646, 'Q’anjob’al'           , 160627, '%', '%', true, false),
( 160647, 'Sakapulteko'          , 160627, '%', '%', true, false),
( 160648, 'Sipakapense'          , 160627, '%', '%', true, false),
( 160649, 'Tektiteko'            , 160627, '%', '%', true, false),
( 160650, 'Tz''utujil'           , 160627, '%', '%', true, false),
( 160651, 'Uspanteko'            , 160627, '%', '%', true, false),
( 160652, 'Xinca'                , 160627, '%', '%', true, false);



