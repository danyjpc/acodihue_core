


TRUNCATE adm_calculator;

ALTER TABLE adm_calculator
ADD COLUMN credit_line INTEGER NOT NULL REFERENCES adm_credit_line(credit_line_id);