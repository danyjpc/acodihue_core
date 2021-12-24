alter table adm_person
    add column if not exists person_key uuid default '00000000-0000-0000-0000-000000000000';

update adm_person
set person_key='3b252cf1-aa7f-47b0-8ed2-1be9353a64da'
where email = 'admin@mypeopleapps.com'