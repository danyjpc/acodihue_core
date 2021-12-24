

--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1034/
ALTER TABLE adm_account ADD CONSTRAINT unique_account_number_order UNIQUE(num_account, num_account_order);


