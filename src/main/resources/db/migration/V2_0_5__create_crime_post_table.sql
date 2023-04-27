CREATE TABLE IF NOT EXISTS "crime_posts" (
"id" varchar(36) not null primary key,
"created" timestamp without time zone not null,
"updated" timestamp without time zone not null,
"address_id" varchar(36),
"crime_type" varchar(255),
"body" text,
"header" text,
"post_status" varchar(255) not null,
foreign key ("address_id")
references "user_addresses"("id")
on delete set null
on update cascade
);