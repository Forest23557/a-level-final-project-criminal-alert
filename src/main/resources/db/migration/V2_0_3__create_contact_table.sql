CREATE TABLE IF NOT EXISTS "user_contacts" (
"id" varchar(36) not null primary key,
"user_id" varchar(36),
"relation_type" varchar(255),
foreign key ("id")
references "application_persons"("id")
on delete cascade
on update cascade,
foreign key ("user_id")
references "application_users"("id")
on delete cascade
on update cascade
);