


--adds unique contraints for person_key and organization_key
ALTER TABLE adm_person ADD CONSTRAINT person_key_unique UNIQUE(person_key);
ALTER TABLE adm_organization ADD CONSTRAINT organization_key_unique UNIQUE(organization_key);