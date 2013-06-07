# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint not null,
  text                      TEXT,
  post_id                   bigint,
  author_id                 bigint,
  created_at                timestamp not null,
  constraint pk_comment primary key (id))
;

create table post (
  id                        bigint not null,
  text                      TEXT,
  topic_id                  bigint,
  author_id                 bigint,
  attached_image_path       varchar(255),
  created_at                timestamp,
  constraint pk_post primary key (id))
;

create table section (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_section primary key (id))
;

create table topic (
  id                        bigint not null,
  name                      varchar(255),
  section_id                bigint,
  author_id                 bigint,
  crated_at                 timestamp,
  constraint pk_topic primary key (id))
;

create table users (
  id                        bigint not null,
  full_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  role                      integer,
  registred_at              timestamp not null,
  logins_count              integer not null,
  last_login                timestamp,
  token                     varchar(255),
  service_link              varchar(255),
  constraint ck_users_role check (role in (0,1,2,3)),
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id))
;


create table post_users (
  post_id                        bigint not null,
  users_id                       bigint not null,
  constraint pk_post_users primary key (post_id, users_id))
;
create sequence comment_seq;

create sequence post_seq;

create sequence section_seq;

create sequence topic_seq;

create sequence users_seq;

alter table comment add constraint fk_comment_post_1 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (post_id);
alter table comment add constraint fk_comment_author_2 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_comment_author_2 on comment (author_id);
alter table post add constraint fk_post_topic_3 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_post_topic_3 on post (topic_id);
alter table post add constraint fk_post_author_4 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_post_author_4 on post (author_id);
alter table topic add constraint fk_topic_section_5 foreign key (section_id) references section (id) on delete restrict on update restrict;
create index ix_topic_section_5 on topic (section_id);
alter table topic add constraint fk_topic_author_6 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_topic_author_6 on topic (author_id);



alter table post_users add constraint fk_post_users_post_01 foreign key (post_id) references post (id) on delete restrict on update restrict;

alter table post_users add constraint fk_post_users_users_02 foreign key (users_id) references users (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists post;

drop table if exists post_users;

drop table if exists section;

drop table if exists topic;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

drop sequence if exists section_seq;

drop sequence if exists topic_seq;

drop sequence if exists users_seq;

