drop table if exists users_has_activity;
drop table if exists users;
drop table if exists user_role;
drop table if exists activities;
drop table if exists activity_category;

create table if not exists user_role
(
    id    serial primary key,
    title varchar(30) not null unique
);

create table if not exists activity_category
(
    id    serial primary key,
    title varchar(30) not null unique
);

create table if not exists activity
(
    id          serial primary key,
    name        varchar(50),
    category_id integer,
    foreign key (category_id) references activity_category (id)
);

create table if not exists users
(
    id       serial primary key,
    role_id  integer,
    name     varchar(50),
    login    varchar(50),
    password varchar(255),
    FOREIGN KEY (role_id) REFERENCES user_role (id)
);

create table if not exists usersHasActivity
(
    user_id     integer,
    activity_id integer,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities (id)
);

insert into user_role
values (default, 'ADMIN'),
       (default, 'USER');

insert into users
values (default, 1, 'admin', 'admin',
        'd+sYTsy7/EPLsyaYCsD8QKxSfrJQhU4hk7N14rc2A7I=$pFeKrhPXvX8xyVpQ6hhw8ACiAgdooCGneWUx0Ivb1QY=');

insert into activity_category
values (default, 'Sport'),
       (default, 'Study');