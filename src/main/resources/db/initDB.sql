drop table if exists developer_skill;
commit;
drop table if exists developers;
commit;
drop table if exists accounts;
commit;
drop table if exists skills;
commit;
drop table if exists projects;
commit;
drop table if exists customers;
commit;

create table if not exists customers
(
    id   binary(16) primary key,
    name varchar(255) not null default '',
    unique (name)
);
create table if not exists projects
(
    id          binary(16) primary key,
    name        varchar(255) not null default '',
    status      varchar(255) not null default '',
    customer_id binary(16),
    foreign key (customer_id) references customers (id)
        on update cascade
        on delete cascade,
    unique (name)
);
create table if not exists skills
(
    id   binary(16) primary key,
    name varchar(255) not null default '',
    unique (name)
);
create table if not exists accounts
(
    id     binary(16) primary key,
    name   varchar(255) not null default '',
    status varchar(255)          default '',
    unique (name)
);
create table if not exists developers
(
    id         binary(16) primary key,
    first_name varchar(255) default '',
    last_name  varchar(255) default '',
    account_id binary(16),
    project_id binary(16),
    foreign key (account_id) references accounts (id)
        on update cascade
        on delete cascade,
    foreign key (project_id) references projects (id)
        on update cascade
        on delete cascade
);
create table if not exists developer_skill
(
    developer_id binary(16) not null,
    skill_id     binary(16) not null,
    foreign key (developer_id) references developers (id)
        on update cascade
        on delete cascade,
    foreign key (skill_id) references skills (id)
        on update cascade
        on delete cascade,
    unique (developer_id, skill_id)
);
commit;
DELIMITER |
CREATE TRIGGER developers_ai
    BEFORE INSERT
    ON developers
    FOR EACH ROW
begin
    if (select a.name from accounts a where a.id = NEW.account_id) is null then
        INSERT INTO accounts (id) VALUES (NEW.account_id);
    end if;
end;
|
DELIMITER ;
# CREATE TRIGGER projects_ai
#     BEFORE UPDATE
#     ON projects
#     FOR EACH ROW
# begin
#     if (select a.name from accounts a where a.id = NEW.account_id) is null then
#         INSERT INTO accounts (id) VALUES (NEW.account_id);
#     end if;
end;
commit;