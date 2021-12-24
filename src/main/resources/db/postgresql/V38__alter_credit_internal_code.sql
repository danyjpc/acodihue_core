

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1168/
--current field size is 4, updating to 14
--4 for agency code, 4 for numeration, 4 for year, 2 for separator '-'
--ABCD-00001-2021

ALTER TABLE adm_credit
ALTER COLUMN internal_code TYPE VARCHAR(14);