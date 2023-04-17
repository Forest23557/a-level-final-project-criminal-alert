CREATE TABLE IF NOT EXISTS "user_messages" (
"id" varchar(36) not null primary key,
"created" timestamp without time zone not null,
"updated" timestamp without time zone not null,
"user_id" varchar(36),
"crime_type" varchar(255),
"body" text,
"message_status" varchar(255) not null,
"subject" text,
"to_email" varchar(255),
foreign key ("user_id")
references "application_users"("id")
on delete cascade
on update cascade
);