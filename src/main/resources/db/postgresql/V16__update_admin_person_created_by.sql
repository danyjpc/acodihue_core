


--Updates admin person createdBy field to 1, actually the value is 0 and is
--causing some issues when trying to persist using that personKey as that record
--will not be returned because of joins
UPDATE adm_person SET created_by = 1
WHERE person_key = '3b252cf1-aa7f-47b0-8ed2-1be9353a64da'
AND person_id = 1;