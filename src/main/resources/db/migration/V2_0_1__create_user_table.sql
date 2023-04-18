CREATE TABLE IF NOT EXISTS "application_users" (
"id" varchar(36) not null primary key,
"username" varchar(255) not null,
"password" text not null,
"gender" varchar(255),
"role" varchar(255) not null,
"date_of_birth" timestamp without time zone,
"age" integer,
"rating" double precision,
foreign key ("id")
references "application_persons"("id")
on delete cascade
on update cascade
);