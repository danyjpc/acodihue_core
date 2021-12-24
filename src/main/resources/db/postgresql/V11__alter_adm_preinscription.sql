ALTER TABLE adm_preinscription ALTER COLUMN authorized_by DROP NOT NULL;
ALTER TABLE adm_preinscription ALTER COLUMN rejected_by DROP NOT NULL;

ALTER TABLE adm_preinscription ALTER COLUMN date_approved DROP NOT NULL;
ALTER TABLE adm_preinscription ALTER COLUMN date_rejected DROP NOT NULL;


