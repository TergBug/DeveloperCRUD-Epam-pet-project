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
    name varchar(255) not null,
    unique (name)
);
create table if not exists projects
(
    id          binary(16) primary key,
    name        varchar(255) not null,
    status      varchar(255) not null,
    customer_id binary(16),
    foreign key (customer_id) references customers (id)
        on update cascade
        on delete cascade,
    unique (name)
);
create table if not exists skills
(
    id   binary(16) primary key,
    name varchar(255) not null,
    unique (name)
);
create table if not exists accounts
(
    id     binary(16) primary key,
    name   varchar(255) not null,
    status varchar(255),
    unique (name)
);
create table if not exists developers
(
    id         binary(16) primary key,
    first_name varchar(255),
    last_name  varchar(255),
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
-- Create hex functions --
CREATE ALIAS if not exists hex FOR "org.mycode.testutil.EncryptionFunctionsToH2DB.hex";
CREATE ALIAS if not exists unhex FOR "org.mycode.testutil.EncryptionFunctionsToH2DB.unhex";