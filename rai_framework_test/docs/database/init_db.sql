/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011/10/21 15:36:05                          */
/*==============================================================*/


drop table if exists CODER;

drop table if exists ORGANIZATION;

drop table if exists PERSON;

drop table if exists PERSON_MOVE_LOG;

/*==============================================================*/
/* Table: CODER                                                 */
/*==============================================================*/
create table CODER
(
   ID                   varchar(50) not null,
   VERSION              int not null,
   NEXTINDEX            int not null,
   TABLE_NAME           varchar(50) not null,
   primary key (ID)
);

alter table CODER comment '主键生成表';

/*==============================================================*/
/* Table: ORGANIZATION                                          */
/*==============================================================*/
create table ORGANIZATION
(
   ID                   int not null,
   NAME                 varchar(200) not null comment '名称',
   PARENT_ID            varchar(100),
   IDX                  numeric(8,0) not null default 0 comment '排序',
   RANK                 numeric(8,0) not null default 1 comment '层次',
   primary key (ID)
);

alter table ORGANIZATION comment '组织机构';

/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
create table PERSON
(
   ID                   int not null auto_increment,
   NAME                 varchar(100) not null,
   SEX                  numeric(8,0) not null,
   AGE                  numeric(8,0),
   ORG_ID               varchar(100),
   primary key (ID)
);

alter table PERSON comment '人员';

/*==============================================================*/
/* Table: PERSON_MOVE_LOG                                       */
/*==============================================================*/
create table PERSON_MOVE_LOG
(
   ID                   int not null auto_increment,
   PERSON_ID            numeric(8,0) not null,
   OLD_ORG_ID           varchar(100) not null,
   NEW_ORG_ID           varchar(100) not null,
   MOVE_TIME            date not null,
   primary key (ID)
);

alter table PERSON_MOVE_LOG comment '人员调动记录';

alter table PERSON add constraint FK_REFERENCE_1 foreign key (ORG_ID)
      references ORGANIZATION (ID) on delete restrict on update restrict;

alter table PERSON_MOVE_LOG add constraint FK_REFERENCE_2 foreign key (PERSON_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table PERSON_MOVE_LOG add constraint FK_REFERENCE_3 foreign key (NEW_ORG_ID)
      references ORGANIZATION (ID) on delete restrict on update restrict;

