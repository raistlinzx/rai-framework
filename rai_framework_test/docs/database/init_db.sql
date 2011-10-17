/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2011/2/24 15:27:11                           */
/*==============================================================*/


alter table PERSON
   drop constraint FK_PERSON_REFERENCE_ORGANIZA;

alter table PERSON_MOVE_LOG
   drop constraint FK_PERSON_M_REFERENCE_PERSON;

alter table PERSON_MOVE_LOG
   drop constraint FK_PERSON_M_REFERENCE_ORGANIZA;

drop table CODER cascade constraints;

drop table ORGANIZATION cascade constraints;

drop table PERSON cascade constraints;

drop table PERSON_MOVE_LOG cascade constraints;

drop sequence MOVE_LOG_SEQ;

drop sequence PERSON_SEQ;

create sequence MOVE_LOG_SEQ;

create sequence PERSON_SEQ;

/*==============================================================*/
/* Table: CODER                                                 */
/*==============================================================*/
create table CODER  (
   ID                   VARCHAR2(50)                    not null,
   VERSION              INT                             not null,
   NEXTINDEX            INT                             not null,
   TABLE_NAME           VARCHAR2(50)                    not null,
   constraint PK_CODER primary key (ID)
);

comment on table CODER is
'主键生成表';

/*==============================================================*/
/* Table: ORGANIZATION                                          */
/*==============================================================*/
create table ORGANIZATION  (
   ID                   VARCHAR2(100)                   not null,
   NAME                 VARCHAR2(200)                   not null,
   PARENT_ID            VARCHAR2(100),
   IDX                  number                         default 0 not null,
   RANK                 number                         default 1 not null,
   constraint PK_ORGANIZATION primary key (ID)
);

comment on table ORGANIZATION is
'组织机构';

comment on column ORGANIZATION.NAME is
'名称';

comment on column ORGANIZATION.IDX is
'排序';

comment on column ORGANIZATION.RANK is
'层次';

/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
create table PERSON  (
   ID                   number                          not null,
   NAME                 VARCHAR2(100)                   not null,
   SEX                  number                          not null,
   AGE                  number,
   ORG_ID               VARCHAR2(100),
   constraint PK_PERSON primary key (ID)
);

comment on table PERSON is
'人员';

/*==============================================================*/
/* Table: PERSON_MOVE_LOG                                       */
/*==============================================================*/
create table PERSON_MOVE_LOG  (
   ID                   NUMBER                          not null,
   PERSON_ID            NUMBER                          not null,
   OLD_ORG_ID           VARCHAR2(100)                   not null,
   NEW_ORG_ID           VARCHAR2(100)                   not null,
   MOVE_TIME            DATE                            not null,
   constraint PK_PERSON_MOVE_LOG primary key (ID)
);

comment on table PERSON_MOVE_LOG is
'人员调动记录';

alter table PERSON
   add constraint FK_PERSON_REFERENCE_ORGANIZA foreign key (ORG_ID)
      references ORGANIZATION (ID);

alter table PERSON_MOVE_LOG
   add constraint FK_PERSON_M_REFERENCE_PERSON foreign key (PERSON_ID)
      references PERSON (ID);

alter table PERSON_MOVE_LOG
   add constraint FK_PERSON_M_REFERENCE_ORGANIZA foreign key (NEW_ORG_ID)
      references ORGANIZATION (ID);

