create TABLE if not exists tasks(
   id serial primary key,
   name varchar not null,
   description varchar not null,
   created timestamp,
   done boolean
);