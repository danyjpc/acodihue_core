


--UPDATES adm_organization default fees
UPDATE adm_organization SET entry_fee = 50.00 WHERE 1 = 1;
UPDATE adm_organization SET entry_contribution = 250.00 WHERE 1 = 1;

ALTER TABLE adm_organization ALTER entry_fee SET DEFAULT 50.00;
ALTER TABLE adm_organization ALTER entry_contribution SET DEFAULT 250.00;