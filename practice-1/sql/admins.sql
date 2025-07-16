create table admins(
id int AUTO_INCREMENT primary key,
last_name varchar(255) not null,
first_name varchar(255) not null,
email varchar(255) not null unique,
password varchar(255) not null,
current_sign_in_at timestamp null,
created_at timestamp not null default current_timestamp,
updated_at timestamp not null default current_timestamp
);