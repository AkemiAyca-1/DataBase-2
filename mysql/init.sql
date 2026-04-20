drop database if exists ToDoListEnglish;
create database if not exists ToDoListEnglish;
use ToDoListEnglish;

create table user
(
    id_user  int not null auto_increment primary key,
    name     varchar(140),
    password varchar(140),
    email    varchar(80)
);

create table user_roles
(
    id_role int auto_increment not null primary key,
    name    varchar(50)
);

create table roles_and_users
(
    id_roles_and_users int auto_increment primary key,
    id_role            int not null,
    id_user            int not null,
    foreign key (id_role) references user_roles (id_role),
    foreign key (id_user) references user (id_user)
);

create table category
(
    id_category int auto_increment not null primary key,
    name        varchar(50),
    id_user     int                not null,
    foreign key (id_user) references user (id_user)
);

create table workspace
(
    id_workspace int auto_increment not null primary key,
    name         varchar(100)
);

create table user_workspace
(
    id_user_workspace int auto_increment not null primary key,
    id_user           int                not null,
    id_workspace      int                not null,
    foreign key (id_user) references user (id_user),
    foreign key (id_workspace) references workspace (id_workspace)
);

create table task
(
    id_task           int auto_increment not null primary key,
    title             varchar(200),
    description       text,
    created_at        date,
    status            enum ('Pending', 'In Progress', 'Completed', 'Cancelled') default 'Pending',
    id_user_workspace int                not null,
    id_category       int                not null,
    foreign key (id_user_workspace) references user_workspace (id_user_workspace),
    foreign key (id_category) references category (id_category)
);

create table task_comment
(
    id_comment        int auto_increment not null primary key,
    title             varchar(100),
    comment           text,
    comment_date      date,
    id_task           int                not null,
    id_user_workspace int                not null,
    foreign key (id_task) references task (id_task),
    foreign key (id_user_workspace) references user_workspace (id_user_workspace)
);

create view task_dashboard as
select
    t.title,
    t.created_at,
    c.name as category_name,
    u.name as user_name
from task t
         join category c on t.id_category = c.id_category
         join user_workspace uw on t.id_user_workspace = uw.id_user_workspace
         join user u on uw.id_user = u.id_user;

create view comment_details
as
select u.name, t.title, c.comment
from task t
         join task_comment c on c.id_task = t.id_task
         join user_workspace uw on uw.id_user_workspace = c.id_user_workspace
         join user u on u.id_user = uw.id_user;

create view workspace_summary as
select w.name                        as workspace_name,
       count(t.id_task)              as total_tasks,
       sum(t.status = 'Pending')     as pending,
       sum(t.status = 'In Progress') as in_progress,
       sum(t.status = 'Completed')   as completed,
       sum(t.status = 'Cancelled')   as cancelled
from workspace w
         left join user_workspace uw on uw.id_workspace = w.id_workspace
         left join task t on t.id_user_workspace = uw.id_user_workspace
group by w.id_workspace, w.name;

delimiter $$
create procedure get_pending_tasks(in workspace_name varchar(150))
begin
    select u.name as user_name, count(t.id_task) as task_count
    from task t
             join user_workspace uw on uw.id_user_workspace = t.id_user_workspace
             join user u on u.id_user = uw.id_user
             join workspace w on w.id_workspace = uw.id_workspace
    where t.status != 'Completed'
      and w.name = workspace_name
    group by u.name;
end $$
delimiter ;

delimiter $$
create trigger before_task_insert
    before insert
    on task
    for each row
begin
    if new.title is null or trim(new.title) = '' then
        signal sqlstate '45000'
            set message_text = 'El título de la tarea no puede estar vacío.';
    end if;

    if new.created_at is null then
        set new.created_at = curdate();
    end if;
end $$
delimiter ;

create index idx_user_name on user (name);
create index idx_task_title on task (title);

insert into user (id_user, name, password, email)
values (1, 'system', 'system', 'system@system.com');

insert into category (id_category, name, id_user)
values (1, 'General', 1);

SELECT *
FROM category;
SELECT *
FROM workspace;
SELECT *
FROM task;
SELECT * FROM user;