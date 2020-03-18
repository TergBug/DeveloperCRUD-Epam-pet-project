use uboafn2j5p8ew05f;
drop table if exists security_authorities;
commit;
drop table if exists security_users;
commit;
create table if not exists security_users
(
    username varchar(255) not null primary key,
    password varchar(255) not null,
    enabled  tinyint      not null default 1
);
create table if not exists security_authorities
(
    username  varchar(255) not null references security_users (username),
    authority varchar(255) not null,
    unique (username, authority)
);
commit;