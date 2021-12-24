

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/995/
ALTER TABLE adm_credit ADD COLUMN partner_account_id BIGINT DEFAULT 0;
COMMENT ON COLUMN adm_credit.partner_account_id IS 'Esta columna enlazara a los expedientes con los fiadores/conyugues';

ALTER TABLE adm_credit RENAME COLUMN property TO address_account_id;

ALTER TABLE adm_credit ADD COLUMN activity_account_id BIGINT DEFAULT 0;
COMMENT ON COLUMN adm_credit.activity_account_id IS 'columna enlazara a los expedientes con las actividades';

ALTER TABLE adm_credit ADD COLUMN internal_code VARCHAR(4) DEFAULT 'C';
COMMENT ON COLUMN adm_credit.internal_code IS 'esta columna sera para guardar el codigo de la agencia, "C-0001-20212"';

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/997/

ALTER TABLE adm_credit ADD COLUMN  dead_line  BIGINT DEFAULT 160000 REFERENCES adm_typology(typology_id);
COMMENT ON COLUMN adm_credit.dead_line IS 'plazo viene de un catalogo 6,12,24,36,48 meses ';

ALTER TABLE adm_credit ADD COLUMN  interest_percentage  DECIMAL(17,2) DEFAULT  0.00;
COMMENT ON COLUMN adm_credit.interest_percentage IS 'porcentaje de interes';

ALTER TABLE adm_credit ADD COLUMN  dead_line_date  TIMESTAMP  NOT NULL DEFAULT '1900-01-01 00:00';
COMMENT ON COLUMN adm_credit.dead_line_date IS 'fecha en la que vence el credito';

ALTER TABLE adm_credit ADD COLUMN  payment_type  BIGINT NOT NULL  DEFAULT 160000 REFERENCES adm_typology(typology_id);
COMMENT ON COLUMN adm_credit.payment_type IS 'fecha en la que vence el credito';

ALTER TABLE adm_credit ADD COLUMN  credit_type  BIGINT NOT NULL  DEFAULT 160000 REFERENCES adm_typology(typology_id);
COMMENT ON COLUMN adm_credit.credit_type IS 'Tipo de credito , viene de un catalogo ';

ALTER TABLE adm_credit ADD COLUMN  mount  DECIMAL(17,2) DEFAULT 0.00;
COMMENT ON COLUMN adm_credit.mount IS 'Monto del credito solicitado';

ALTER TABLE adm_credit ADD COLUMN  estate_date  TIMESTAMP  NOT NULL DEFAULT '1900-01-01 00:00';
COMMENT ON COLUMN adm_credit.estate_date IS 'Fecha del estado patrimonial';

ALTER TABLE adm_credit ADD COLUMN  status_operated  BIGINT NOT NULL  DEFAULT 160000 REFERENCES  adm_typology(typology_id);
COMMENT ON COLUMN adm_credit.status_operated IS 'Estado de operacion (aprobado, rechazado, pendiente), viene de catalogo, inicia en pendiente';

ALTER TABLE adm_credit ADD COLUMN  operated_by   BIGINT NOT NULL  DEFAULT 160000 REFERENCES adm_user(user_id) ;
COMMENT ON COLUMN adm_credit.operated_by IS 'Es la persona que aprueba o rechaza el credito, puede ser gerente o adminIStrador';

ALTER TABLE adm_credit ADD COLUMN  annotation   VARCHAR(250) NOT NULL  DEFAULT 'S/D';
COMMENT ON COLUMN adm_credit.annotation IS 'motivo del rechazo y/ comentarios adicionales ';

ALTER TABLE adm_credit ADD COLUMN  organization_responsible_id   BIGINT  NOT NULL REFERENCES adm_organization_responsible(organization_responsible_id);
COMMENT ON COLUMN adm_credit.organization_responsible_id IS 'organizacion responsable del asociado';
