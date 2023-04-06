create table public.badges
(
    id            serial
        primary key,
    current_state varchar(255),
    first_name    varchar(255),
    is_private    boolean default false,
    key           varchar(255),
    last_name     varchar(255),
    position      varchar(255),
    previous_id   integer
        constraint fk_badge_badge
            references public.badges
);

create table public.employees
(
    id         serial
        primary key,
    country    varchar(255),
    email      varchar(255),
    gender     varchar(255),
    is_private boolean default false,
    name       varchar(255),
    password   varchar(255),
    phone      varchar(255),
    badge_id   integer
        constraint fk_employee_badge
            references public.badges
);

create table public.addresses
(
    id                 bigserial
        primary key,
    address_has_active boolean,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer
        constraint fk_address_employee
            references public.employees
);

create table public.photo
(
    id          serial
        primary key,
    bytes       bytea,
    format      varchar(255),
    is_private  boolean default false,
    name        varchar(255),
    upload_date date,
    url         varchar(255),
    employee_id integer
        constraint fk_photo_employee
            references public.employees
);

create table public.projects
(
    id         serial
        primary key,
    back_log   varchar(255),
    dead_line  date,
    is_private boolean,
    language   varchar(255),
    start_date date
);

create table public.projects_employees
(
    employee_id integer not null
        constraint fk_projects_employees_employee
            references public.employees,
    project_id  integer not null
        constraint fk_projects_employees_project
            references public.projects,
    is_active   boolean,
    primary key (employee_id, project_id)
);
