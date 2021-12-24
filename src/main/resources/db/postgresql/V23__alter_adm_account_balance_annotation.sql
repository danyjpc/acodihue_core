

--Add annotation field to table
ALTER TABLE adm_account_balance
ADD COLUMN annotation VARCHAR(250) NOT NULL DEFAULT 'S/D';

--Add comment for new field
COMMENT ON COLUMN adm_account_balance.annotation IS 'Concepto de la transaccion';