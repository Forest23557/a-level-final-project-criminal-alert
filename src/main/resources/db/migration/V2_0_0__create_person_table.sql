CREATE TABLE IF NOT EXISTS "application_persons" (
"id" varchar(36) not null primary key,
"created" timestamp without time zone not null,
"updated" timestamp without time zone not null,
"name" varchar(255) not null,
"surname" varchar(255),
"email_address" varchar(255) not null,
"phone_number" varchar(20),
"person_status" varchar(255)
);