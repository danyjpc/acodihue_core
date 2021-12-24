


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1291/
--Update current typologies
UPDATE adm_typology SET description = 'DPI deudor' WHERE typology_id = 160573;
UPDATE adm_typology SET description = 'RTU deudor' WHERE typology_id = 160576;

--Add new typologies
INSERT INTO adm_typology(typology_id, description, parent_typology_id, value_1, value_2, available, editable)
VALUES
( 160653, 'Fotografias deudor'                      , 160572, '%', '%', true, false),
( 160654, 'Fotografias fiador'                      , 160572, '%', '%', true, false),
( 160655, 'RTU fiador'                              , 160572, '%', '%', true, false),
( 160656, 'DPI fiador'                              , 160572, '%', '%', true, false),
( 160657, 'Cheque voucher'                          , 160572, '%', '%', true, false),
( 160658, 'Recibo de agua'                          , 160572, '%', '%', true, false),
( 160659, 'Croquis de ubicacion de garantia'        , 160572, '%', '%', true, false),
( 160660, 'Escritura original'                       , 160572, '%', '%', true, false),
( 160661, 'Constancia laboral'                      , 160572, '%', '%', true, false),
( 160662, 'Certificacion de ingresos'               , 160572, '%', '%', true, false),
--
( 160663, 'Solicitud de credito firmada por el socio', 160572, '%', '%', true, false),
( 160664, 'Autorizacion COCODE'                      , 160572, '%', '%', true, false),
( 160665, 'Autorizacion alcalde auxiliar'            , 160572, '%', '%', true, false),
( 160666, 'Autorizacion alcalde municipal'           , 160572, '%', '%', true, false),
( 160667, 'Formularios ONU'                          , 160572, '%', '%', true, false),
( 160668, 'Formularios OFAC'                         , 160572, '%', '%', true, false),
( 160669, 'Formularios IVE'                          , 160572, '%', '%', true, false),
( 160670, 'Plan patrimonial'                         , 160572, '%', '%', true, false),
( 160671, 'Estado patrimonial'                       , 160572, '%', '%', true, false),
( 160672, 'Resolucion comite de credito'             , 160572, '%', '%', true, false),
( 160673, 'Resolucion consejo de administracion'     , 160572, '%', '%', true, false),
( 160674, 'Contrato mutuo'                           , 160572, '%', '%', true, false);


