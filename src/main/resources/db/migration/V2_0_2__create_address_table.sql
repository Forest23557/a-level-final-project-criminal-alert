CREATE TABLE IF NOT EXISTS "user_addresses" (
"id" varchar(36) not null primary key,
"created" timestamp without time zone not null,
"updated" timestamp without time zone not null,
"user_id" varchar(36),
"address_line" text,
"address_status" varchar(255) not null,
"city_or_town" varchar(255) not null,
"country" varchar(255) not null,
"district" varchar(255),
"region" varchar(255) not null,
foreign key ("user_id")
references "application_users"("id")
on delete set null
on update cascade
);