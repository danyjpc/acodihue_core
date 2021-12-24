


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1362/
--Add annotation for credit activity
ALTER TABLE adm_activity
ADD COLUMN annotation TEXT NOT NULL DEFAULT '%';


