CREATE TABLE candidates (
    id VARCHAR(40) not null,
    photo varchar(255) default null,
    given_name varchar(50) not null,
    family_name varchar(50) not null,
    email varchar(255) not null,
    phone varchar(50) default null,
    job_title varchar(50) default null,
    created_at timestamp default current_timestamp,
    updated_at DATETIME default current_timestamp ON UPDATE current_timestamp,
    PRIMARY KEY (id)
);

