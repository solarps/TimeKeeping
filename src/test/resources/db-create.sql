drop table if exists users_has_activity;
drop table if exists users cascade;
drop table if exists user_role;
drop table if exists activities cascade;
drop table if exists activity_state cascade;
drop table if exists activity_category cascade;

create table if not exists user_role
(
    id    bigserial primary key,
    title varchar(30) not null unique
);

create table if not exists activity_category
(
    id    bigserial primary key,
    title varchar(30) not null unique
);

create table if not exists activities
(
    id          bigserial primary key,
    name        varchar(50),
    category_id serial,
    foreign key (category_id) references activity_category (id) on delete cascade
);

create table if not exists users
(
    id       bigserial primary key,
    role_id  bigserial,
    name     varchar(50),
    login    varchar(50),
    password varchar(255),
    FOREIGN KEY (role_id) REFERENCES user_role (id)
);

create table if not exists activity_state
(
    id    smallserial primary key,
    state varchar(10) not null unique
);

create table if not exists users_has_activity
(
    user_id     bigserial,
    activity_id bigserial,
    state_id    smallserial,
    spent_time  interval,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities (id),
    foreign key (state_id) references activity_state (id)
);

insert into user_role
values (default, 'ADMIN'),
       (default, 'USER');

insert into activity_state
values (default, 'FOLLOWED'),
       (default, 'WAITING'),
       (default, 'UNFOLLOWED');

