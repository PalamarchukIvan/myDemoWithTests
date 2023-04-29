create table users(
	username varchar(50) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);



insert into authorities values ('user', 'USER');
insert into authorities values ('admin', 'ADMIN');

insert into users values('user', '$2a$10$oqhth.ORAVuXi5tFYYxmHuGCfmAFFWFzWAZDPPqNgfPM2vZgT6kMG', true);
insert into users values('admin', '$2a$10$oqhth.ORAVuXi5tFYYxmHuGCfmAFFWFzWAZDPPqNgfPM2vZgT6kMG', true);