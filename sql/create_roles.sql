create group employees;
create group admins;

create user def with password 'def';
grant employees to def;
create user root with password 'r';
grant admins to root;