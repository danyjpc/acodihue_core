


--https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1360/
--Add default credit lines for central agency
TRUNCATE TABLE adm_credit_line CASCADE;
INSERT INTO adm_credit_line(credit_line_id, organization_id, description, created_by)
VALUES
(1, 160003, 'Agricola', 1),
(2, 160003, 'Construcción', 1);


