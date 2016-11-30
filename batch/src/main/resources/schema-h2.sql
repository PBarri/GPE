-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile
--------------------------------------------------------
-- Archivo creado  - miércoles-agosto-31-2016
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence S_GPE_ABSENCES
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_ABSENCES  MINVALUE 1 INCREMENT BY 1 START WITH 21 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_GOALS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_GOALS  MINVALUE 1 INCREMENT BY 1 START WITH 61 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_HISTORICAL_PROJECTS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_HISTORICAL_PROJECTS  MINVALUE 1 INCREMENT BY 1 START WITH 21 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_INCURRED
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_INCURRED  MINVALUE 1 INCREMENT BY 1 START WITH 21 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_PROJECTS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_PROJECTS  MINVALUE 1 INCREMENT BY 1 START WITH 61 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_REPORTS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_REPORTS  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_REQUIREMENTS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_REQUIREMENTS  MINVALUE 1 INCREMENT BY 1 START WITH 61 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_SERVERS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_SERVERS  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_TASKS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_TASKS  MINVALUE 1 INCREMENT BY 1 START WITH 361 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_TASKS_COMMENT
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_TASKS_COMMENT  MINVALUE 1 INCREMENT BY 1 START WITH 41 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_TIMESHEETS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_TIMESHEETS  MINVALUE 1 INCREMENT BY 1 START WITH 21 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_TODO
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_TODO  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_USERS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_USERS  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence S_GPE_VACATIONS
--------------------------------------------------------

   CREATE SEQUENCE  S_GPE_VACATIONS  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCYCLE ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_ABSENCES
--------------------------------------------------------

  CREATE TABLE T_GPE_M_ABSENCES
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"COMMENTARY" VARCHAR2(100 CHAR),
	"END_DATE" TIMESTAMP (6),
	"START_DATE" TIMESTAMP (6),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_DOCUMENTS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_DOCUMENTS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"DESCRIPTION" VARCHAR2(500 CHAR),
	"DEVELOPER_AVAILABILITY" NUMBER(1,0),
	"NAME" VARCHAR2(50 CHAR),
	"URL" VARCHAR2(255 CHAR),
	"DOCUMENT_TYPE_ID" NUMBER(19,0),
	"PROJECT_ID" NUMBER(19,0),
	"REQUIREMENT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_GOALS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_GOALS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"ACHIEVED" NUMBER(1,0),
	"DESCRIPTION" VARCHAR2(200 CHAR),
	"PRIORITY" NUMBER(10,0),
	"TITLE" VARCHAR2(30 CHAR),
	"REQUIREMENT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_INCURREDS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_INCURREDS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"COMMENTARY" VARCHAR2(200 CHAR),
	"EXTRA" NUMBER(1,0),
	"MINUTES" NUMBER(10,0),
	"TASK_ID" NUMBER(19,0),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_PROJECTS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_PROJECTS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"ARCHIVED" NUMBER(1,0),
	"CREATED_BY" VARCHAR2(255 CHAR),
	"CVS_CODE" VARCHAR2(255 CHAR),
	"DELETED" NUMBER(1,0),
	"DESCRIPTION" VARCHAR2(4000 CHAR),
	"END_DATE" TIMESTAMP (6),
	"LAST_EDITION_BY" VARCHAR2(255 CHAR),
	"NAME" VARCHAR2(20 CHAR),
	"PROJECT_CODE" VARCHAR2(30 CHAR),
	"PROJECT_LOGO" VARCHAR2(255 CHAR),
	"PROJECT_TITLE" VARCHAR2(3 CHAR),
	"START_DATE" TIMESTAMP (6),
	"PROJECT_LEADER" NUMBER(19,0),
	"METHODOLOGY" NUMBER(19,0),
	"PRODUCT_OWNER" NUMBER(19,0),
	"SCRUM_MASTER" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_REPORTS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_REPORTS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"FILENAME" VARCHAR2(255 CHAR),
	"NAME" VARCHAR2(255 CHAR),
	"REPORT_STATUS" NUMBER(19,0),
	"REPORT_TYPE" NUMBER(19,0),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_REQUIREMENTS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_REQUIREMENTS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"ARCHIVED" NUMBER(1,0),
	"CVS_CODE" VARCHAR2(255 CHAR),
	"DESCRIPTION" VARCHAR2(200 CHAR),
	"END_DATE" TIMESTAMP (6),
	"HOURS" NUMBER(19,0),
	"NAME" VARCHAR2(50 CHAR),
	"REQUIREMENT_CODE" VARCHAR2(30 CHAR),
	"START_DATE" TIMESTAMP (6),
	"CREATED_BY" NUMBER(19,0),
	"LAST_EDITION_BY" NUMBER(19,0),
	"PROJECT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_ROLES
--------------------------------------------------------

  CREATE TABLE T_GPE_M_ROLES
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_SERVERS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_SERVERS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"HOSTNAME" VARCHAR2(255 CHAR),
	"IP" VARCHAR2(255 CHAR),
	"PROJECT_ID" NUMBER(19,0),
	"SERVER_TYPE" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_TASK_COMMENT
--------------------------------------------------------

  CREATE TABLE T_GPE_M_TASK_COMMENT
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"COMMENTARY" VARCHAR2(500 CHAR),
	"TASK_ID" NUMBER(19,0),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_TASKS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_TASKS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"CODE" VARCHAR2(255 CHAR),
	"DESCRIPTION" VARCHAR2(200 CHAR),
	"END_DATE" TIMESTAMP (6),
	"HOURS" FLOAT(126),
	"MANAGEMENT" NUMBER(1,0),
	"NAME" VARCHAR2(50 CHAR),
	"START_DATE" TIMESTAMP (6),
	"CREATED_BY" NUMBER(19,0),
	"LAST_EDITION_BY" NUMBER(19,0),
	"PARENT_ID" NUMBER(19,0),
	"REQUIREMENT_ID" NUMBER(19,0),
	"TASK_PRIORITY_ID" NUMBER(19,0),
	"TASK_STATUS_ID" NUMBER(19,0),
	"TASK_TYPE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_TIMESHEETS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_TIMESHEETS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"END_DATE" TIMESTAMP (6),
	"FRIDAY_HOURS" NUMBER(10,0),
	"MONDAY_HOURS" NUMBER(10,0),
	"SATURDAY_HOURS" NUMBER(10,0),
	"START_DATE" TIMESTAMP (6),
	"SUNDAY_HOURS" NUMBER(10,0),
	"THURSDAY_HOURS" NUMBER(10,0),
	"TUESDAY_HOURS" NUMBER(10,0),
	"WEDNESDAY_HOURS" NUMBER(10,0),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_TODOS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_TODOS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"DONE" NUMBER(1,0),
	"TASK" VARCHAR2(30 CHAR),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_USERS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_USERS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"ATTEMPTS" NUMBER(10,0),
	"BIRTHDAY" TIMESTAMP (6),
	"CITY" VARCHAR2(20 CHAR),
	"COUNTRY" VARCHAR2(20 CHAR),
	"EMAIL" VARCHAR2(255 CHAR),
	"EXPIRING_DATE" TIMESTAMP (6),
	"IDENTIFIER" VARCHAR2(20 CHAR),
	"IS_ENABLED" NUMBER(1,0),
	"LAST_LOGIN" TIMESTAMP (6),
	"LOCKED" NUMBER(1,0),
	"NAME" VARCHAR2(20 CHAR),
	"PASSWORD" VARCHAR2(255 CHAR),
	"PHONE" VARCHAR2(255 CHAR),
	"PHOTO_URL" VARCHAR2(255 CHAR),
	"SURNAME" VARCHAR2(50 CHAR),
	"ROLE_ID" NUMBER(19,0),
	"USER_POSITION" NUMBER(19,0),
	"VACATION_DAYS" NUMBER(2,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_M_VACATIONS
--------------------------------------------------------

  CREATE TABLE T_GPE_M_VACATIONS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"APPROVED" NUMBER(1,0),
	"END_DATE" TIMESTAMP (6),
	"START_DATE" TIMESTAMP (6),
	"YEAR" NUMBER(10,0),
	"USER_ID" NUMBER(19,0),
	"MANAGED" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_DOCUMENT_TYPES
--------------------------------------------------------

  CREATE TABLE T_GPE_P_DOCUMENT_TYPES
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_METHODOLOGIES
--------------------------------------------------------

  CREATE TABLE T_GPE_P_METHODOLOGIES
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_PRIORITY
--------------------------------------------------------

  CREATE TABLE T_GPE_P_PRIORITY
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_PUBLIC_HOLIDAYS
--------------------------------------------------------

  CREATE TABLE T_GPE_P_PUBLIC_HOLIDAYS
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR),
	"DAY" TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_REPORT_STATUS
--------------------------------------------------------

  CREATE TABLE T_GPE_P_REPORT_STATUS
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_REPORT_TYPE
--------------------------------------------------------

  CREATE TABLE T_GPE_P_REPORT_TYPE
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_SERVER_TYPE
--------------------------------------------------------

  CREATE TABLE T_GPE_P_SERVER_TYPE
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_TASK_STATUS
--------------------------------------------------------

  CREATE TABLE T_GPE_P_TASK_STATUS
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR),
	"COLOR" VARCHAR2(7 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_TASK_TYPE
--------------------------------------------------------

  CREATE TABLE T_GPE_P_TASK_TYPE
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_P_USER_CATEGORY
--------------------------------------------------------

  CREATE TABLE T_GPE_P_USER_CATEGORY
   (	"ID" NUMBER(19,0),
	"CODE" VARCHAR2(50 CHAR),
	"DESCRIPTION" VARCHAR2(500 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_R_HISTORICAL_PROJECTS
--------------------------------------------------------

  CREATE TABLE T_GPE_R_HISTORICAL_PROJECTS
   (	"ID" NUMBER(19,0),
	"TIMESTAMP" TIMESTAMP (6),
	"VERSION" NUMBER(19,0),
	"END_DATE" TIMESTAMP (6),
	"START_DATE" TIMESTAMP (6),
	"PROJECT_ID" NUMBER(19,0),
	"REQUIREMENT_ID" NUMBER(19,0),
	"ROLE_ID" NUMBER(19,0),
	"USER_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_R_PROJECTS_MANAGED
--------------------------------------------------------

  CREATE TABLE T_GPE_R_PROJECTS_MANAGED
   (	"ID_USER" NUMBER(19,0),
	"ID_PROJECT" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_R_REQUIREMENTS_DEVELOPED
--------------------------------------------------------

  CREATE TABLE T_GPE_R_REQUIREMENTS_DEVELOPED
   (	"ID_USER" NUMBER(19,0),
	"ID_REQUIREMENT" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_R_TASKS_ASSIGNED
--------------------------------------------------------

  CREATE TABLE T_GPE_R_TASKS_ASSIGNED
   (	"ID_USER" NUMBER(19,0),
	"ID_TASK" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_GPE_R_TASKS_DEPENDENCIES
--------------------------------------------------------

  CREATE TABLE T_GPE_R_TASKS_DEPENDENCIES
   (	"TASK_ID" NUMBER(19,0),
	"DEPENDENCY_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for View V_GPE_LEADER_DASHBOARD
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW V_GPE_LEADER_DASHBOARD ("ID", "LEADER", "PROJECTS", "REQUIREMENTS", "MANAGERS", "DEVELOPERS", "ACTIVE_TASKS", "BLOCKED_TASKS", "DELAYED_TASKS", "STOPPED_TASKS") AS
  SELECT rownum id ,
    leader,
    projects,
    requirements,
    managers,
    developers,
    active_Tasks,
    blocked_Tasks,
    delayed_Tasks,
    stopped_Tasks
  FROM
    (WITH q1 AS
    (SELECT u.ID leader,
      COUNT(DISTINCT(p.id))       AS projects,
      COUNT(DISTINCT(r.id))       AS requirements,
      COUNT(DISTINCT(rd.ID_USER)) AS developers,
      COUNT(DISTINCT(pm.ID_USER)) AS managers
    FROM T_GPE_M_USERS u
    LEFT JOIN T_GPE_M_PROJECTS p
    ON u.ID = p.PROJECT_LEADER
    LEFT JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    LEFT JOIN T_GPE_R_REQUIREMENTS_DEVELOPED rd
    ON rd.ID_REQUIREMENT = r.ID
    LEFT JOIN T_GPE_R_PROJECTS_MANAGED pm
    ON pm.ID_PROJECT = p.ID
    WHERE u.ROLE_ID = 2
    GROUP BY u.ID
    ) ,
    q2 AS
    (SELECT NVL(SUM(COUNT(t.id)), 0) AS active_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 3
    OR st.id            = 12
    GROUP BY st.DESCRIPTION
    ),
    q3 AS
    (SELECT NVL(COUNT(t.id), 0) AS blocked_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 5
    GROUP BY st.DESCRIPTION
    ),
    q4 AS
    (SELECT NVL(COUNT(t.id), 0) AS delayed_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 9
    GROUP BY st.DESCRIPTION
    ),
    q5 AS
    (SELECT NVL(SUM(COUNT(t.id)), 0) AS stopped_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 4
    OR st.id            = 11
    GROUP BY st.DESCRIPTION
    )
  SELECT * FROM q1, q2, q3, q4, q5
    );
--------------------------------------------------------
--  DDL for View V_GPE_MANAGER_DASHBOARD
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW V_GPE_MANAGER_DASHBOARD ("ID", "MANAGER", "PROJECTS", "REQUIREMENTS", "DEVELOPERS", "ACTIVE_TASKS", "BLOCKED_TASKS", "DELAYED_TASKS", "STOPPED_TASKS") AS
  SELECT rownum id ,
    manager,
    projects,
    requirements,
    developers,
    active_Tasks,
    blocked_Tasks,
    delayed_Tasks,
    stopped_Tasks
  FROM
    (WITH q1 AS
    (SELECT u.ID                  AS manager,
      COUNT(DISTINCT(p.ID))       AS projects,
      COUNT(DISTINCT(r.ID))       AS requirements,
      COUNT(DISTINCT(rd.ID_USER)) AS developers
    FROM T_GPE_M_USERS u
    LEFT JOIN T_GPE_R_PROJECTS_MANAGED pm
    ON u.ID = pm.ID_USER
    LEFT JOIN T_GPE_M_PROJECTS p
    ON pm.ID_PROJECT = p.ID
    LEFT JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    LEFT JOIN T_GPE_R_REQUIREMENTS_DEVELOPED rd
    ON rd.ID_REQUIREMENT = r.ID
    WHERE u.ROLE_ID      = 3
    GROUP BY u.ID
    ) ,
    q2 AS
    (SELECT NVL(SUM(COUNT(t.id)), 0) AS active_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 3
    OR st.id            = 12
    GROUP BY st.DESCRIPTION
    ),
    q3 AS
    (SELECT NVL(COUNT(t.id), 0) AS blocked_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 5
    GROUP BY st.DESCRIPTION
    ),
    q4 AS
    (SELECT NVL(COUNT(t.id), 0) AS delayed_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 9
    GROUP BY st.DESCRIPTION
    ),
    q5 AS
    (SELECT NVL(SUM(COUNT(t.id)), 0) AS stopped_Tasks
    FROM T_GPE_M_PROJECTS p
    INNER JOIN T_GPE_M_REQUIREMENTS r
    ON p.ID = r.PROJECT_ID
    INNER JOIN T_GPE_M_TASKS t
    ON t.REQUIREMENT_ID = r.id
    RIGHT JOIN T_GPE_P_TASK_STATUS st
    ON t.TASK_STATUS_ID = st.ID
    WHERE st.id         = 4
    OR st.id            = 11
    GROUP BY st.DESCRIPTION
    )
  SELECT * FROM q1, q2, q3, q4, q5
    );
--------------------------------------------------------
--  DDL for Index UK_JVQQWOOHHHKFJMN1NL3BUMM4L
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_JVQQWOOHHHKFJMN1NL3BUMM4L ON T_GPE_M_PROJECTS ("NAME");
--------------------------------------------------------
--  DDL for Index UK_2UJ4V7AQRPADXG9MH0RLD74FP
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_2UJ4V7AQRPADXG9MH0RLD74FP ON T_GPE_M_USERS ("IDENTIFIER");
--------------------------------------------------------
--  DDL for Index UK_MPG2A2CHBLYN74QPT6A3237TI
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_MPG2A2CHBLYN74QPT6A3237TI ON T_GPE_P_TASK_TYPE ("CODE");
--------------------------------------------------------
--  DDL for Index UK_CBGUR4V3II5RE574NLBAMPJQI
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_CBGUR4V3II5RE574NLBAMPJQI ON T_GPE_P_DOCUMENT_TYPES ("CODE");
--------------------------------------------------------
--  DDL for Index UK_SFQC5WKBFRNPFMG9L06SSISHM
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_SFQC5WKBFRNPFMG9L06SSISHM ON T_GPE_M_USERS ("EMAIL");
--------------------------------------------------------
--  DDL for Index UK_24LHEVP98QXSMT7LMAH8R9K51
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_24LHEVP98QXSMT7LMAH8R9K51 ON T_GPE_P_REPORT_STATUS ("CODE");
--------------------------------------------------------
--  DDL for Index UK_90CQGFWT0W4UBJ4K8MKLXW7PF
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_90CQGFWT0W4UBJ4K8MKLXW7PF ON T_GPE_P_PRIORITY ("CODE");
--------------------------------------------------------
--  DDL for Index UK_BPJFI7IK1OOHGMANEQYDKRUKT
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_BPJFI7IK1OOHGMANEQYDKRUKT ON T_GPE_P_SERVER_TYPE ("CODE");
--------------------------------------------------------
--  DDL for Index UK_Q4JLEJED9XJCAKAK6P3VW31N7
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_Q4JLEJED9XJCAKAK6P3VW31N7 ON T_GPE_P_PUBLIC_HOLIDAYS ("CODE");
--------------------------------------------------------
--  DDL for Index UK_II1YYYI5W4E288DBI3GMHWWF5
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_II1YYYI5W4E288DBI3GMHWWF5 ON T_GPE_P_TASK_STATUS ("CODE");
--------------------------------------------------------
--  DDL for Index UK_QVANQGWA4CCTMBS8I5ULFQIKO
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_QVANQGWA4CCTMBS8I5ULFQIKO ON T_GPE_P_METHODOLOGIES ("CODE");
--------------------------------------------------------
--  DDL for Index UK_2WNWSN26CO09PYSQUPP9CXP4N
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_2WNWSN26CO09PYSQUPP9CXP4N ON T_GPE_P_REPORT_TYPE ("CODE");
--------------------------------------------------------
--  DDL for Index UK_BEQTRHSY0LWER7G6NCCSM67DT
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_BEQTRHSY0LWER7G6NCCSM67DT ON T_GPE_M_ROLES ("CODE");
--------------------------------------------------------
--  DDL for Index UK_3VVIG6UPW1VP5GYUX62GWV6ON
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_3VVIG6UPW1VP5GYUX62GWV6ON ON T_GPE_P_USER_CATEGORY ("CODE");
--------------------------------------------------------
--  DDL for Index UK_SV0ES9GMY2YQP6IW5NB2EFRPX
--------------------------------------------------------

  CREATE UNIQUE INDEX UK_SV0ES9GMY2YQP6IW5NB2EFRPX ON T_GPE_R_HISTORICAL_PROJECTS ("USER_ID", "PROJECT_ID", "ROLE_ID");
--------------------------------------------------------
--  Constraints for Table T_GPE_M_INCURREDS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_INCURREDS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER TASK_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER MINUTES SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER EXTRA SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_INCURREDS ALTER ID SET NOT NULL;
    CREATE PRIMARY KEY ON T_GPE_M_INCURREDS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_R_REQUIREMENTS_DEVELOPED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_REQUIREMENTS_DEVELOPED ALTER ID_REQUIREMENT SET NOT NULL;
  ALTER TABLE T_GPE_R_REQUIREMENTS_DEVELOPED ALTER ID_USER SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table T_GPE_P_TASK_TYPE
--------------------------------------------------------

  ALTER TABLE T_GPE_P_TASK_TYPE ADD CONSTRAINT "UK_MPG2A2CHBLYN74QPT6A3237TI" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_TASK_TYPE ALTER ID SET NOT NULL;
    CREATE PRIMARY KEY ON T_GPE_P_TASK_TYPE (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_R_HISTORICAL_PROJECTS
--------------------------------------------------------

  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ADD CONSTRAINT "UK_SV0ES9GMY2YQP6IW5NB2EFRPX" UNIQUE ("USER_ID", "PROJECT_ID", "ROLE_ID");
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER ROLE_ID SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER PROJECT_ID SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_R_HISTORICAL_PROJECTS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_DOCUMENT_TYPES
--------------------------------------------------------

  ALTER TABLE T_GPE_P_DOCUMENT_TYPES ADD CONSTRAINT "UK_CBGUR4V3II5RE574NLBAMPJQI" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_DOCUMENT_TYPES ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_DOCUMENT_TYPES (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_TASK_STATUS
--------------------------------------------------------

  ALTER TABLE T_GPE_P_TASK_STATUS ADD CONSTRAINT "UK_II1YYYI5W4E288DBI3GMHWWF5" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_TASK_STATUS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_TASK_STATUS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_R_TASKS_DEPENDENCIES
--------------------------------------------------------

  ALTER TABLE T_GPE_R_TASKS_DEPENDENCIES ALTER DEPENDENCY_ID SET NOT NULL;
  ALTER TABLE T_GPE_R_TASKS_DEPENDENCIES ALTER TASK_ID SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table T_GPE_M_GOALS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_GOALS ALTER REQUIREMENT_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER TITLE SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER PRIORITY SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER ACHIEVED SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_GOALS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_GOALS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_TASK_COMMENT
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER TASK_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER COMMENTARY SET NOT NULL;
  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_TASK_COMMENT ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_TASK_COMMENT (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_METHODOLOGIES
--------------------------------------------------------

  ALTER TABLE T_GPE_P_METHODOLOGIES ADD CONSTRAINT "UK_QVANQGWA4CCTMBS8I5ULFQIKO" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_METHODOLOGIES ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_METHODOLOGIES (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_REPORT_STATUS
--------------------------------------------------------

  ALTER TABLE T_GPE_P_REPORT_STATUS ADD CONSTRAINT "UK_24LHEVP98QXSMT7LMAH8R9K51" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_REPORT_STATUS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_REPORT_STATUS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_REPORTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_REPORTS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_REPORTS ALTER NAME SET NOT NULL;
  ALTER TABLE T_GPE_M_REPORTS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_REPORTS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_REPORTS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_REPORTS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_SERVER_TYPE
--------------------------------------------------------

  ALTER TABLE T_GPE_P_SERVER_TYPE ADD CONSTRAINT "UK_BPJFI7IK1OOHGMANEQYDKRUKT" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_SERVER_TYPE ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_SERVER_TYPE (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_USER_CATEGORY
--------------------------------------------------------

  ALTER TABLE T_GPE_P_USER_CATEGORY ADD CONSTRAINT "UK_3VVIG6UPW1VP5GYUX62GWV6ON" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_USER_CATEGORY ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_USER_CATEGORY (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_SERVERS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_SERVERS ALTER PROJECT_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_SERVERS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_SERVERS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_SERVERS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_SERVERS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_DOCUMENTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_DOCUMENTS ALTER DOCUMENT_TYPE_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER URL SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER NAME SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER DEVELOPER_AVAILABILITY SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_DOCUMENTS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_DOCUMENTS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_R_PROJECTS_MANAGED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_PROJECTS_MANAGED ALTER ID_PROJECT SET NOT NULL;
  ALTER TABLE T_GPE_R_PROJECTS_MANAGED ALTER ID_USER SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table T_GPE_P_PUBLIC_HOLIDAYS
--------------------------------------------------------

  ALTER TABLE T_GPE_P_PUBLIC_HOLIDAYS ADD CONSTRAINT "UK_Q4JLEJED9XJCAKAK6P3VW31N7" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_PUBLIC_HOLIDAYS ALTER DAY SET NOT NULL;
  ALTER TABLE T_GPE_P_PUBLIC_HOLIDAYS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_PUBLIC_HOLIDAYS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_VACATIONS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_VACATIONS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER YEAR SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER END_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER APPROVED SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_VACATIONS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_VACATIONS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_TODOS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TODOS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_TODOS ALTER TASK SET NOT NULL;
  ALTER TABLE T_GPE_M_TODOS ALTER DONE SET NOT NULL;
  ALTER TABLE T_GPE_M_TODOS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_TODOS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_TODOS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_TODOS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_USERS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_USERS ADD CONSTRAINT "UK_2UJ4V7AQRPADXG9MH0RLD74FP" UNIQUE ("IDENTIFIER");
  ALTER TABLE T_GPE_M_USERS ADD CONSTRAINT "UK_SFQC5WKBFRNPFMG9L06SSISHM" UNIQUE ("EMAIL");
  ALTER TABLE T_GPE_M_USERS ALTER ROLE_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER SURNAME SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER PASSWORD SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER NAME SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER LOCKED SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER IS_ENABLED SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER IDENTIFIER SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER ATTEMPTS SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_USERS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_USERS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_TASKS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TASKS ALTER LAST_EDITION_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER CREATED_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER CODE SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_TASKS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_TASKS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_ROLES
--------------------------------------------------------

  ALTER TABLE T_GPE_M_ROLES ADD CONSTRAINT "UK_BEQTRHSY0LWER7G6NCCSM67DT" UNIQUE ("CODE");
  ALTER TABLE T_GPE_M_ROLES ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_ROLES (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_TIMESHEETS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TIMESHEETS ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER WEDNESDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER TUESDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER THURSDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER SUNDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER SATURDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER MONDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER FRIDAY_HOURS SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER END_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_TIMESHEETS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_TIMESHEETS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_P_PRIORITY
--------------------------------------------------------

  ALTER TABLE T_GPE_P_PRIORITY ADD CONSTRAINT "UK_90CQGFWT0W4UBJ4K8MKLXW7PF" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_PRIORITY ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_PRIORITY (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_ABSENCES
--------------------------------------------------------

  ALTER TABLE T_GPE_M_ABSENCES ALTER USER_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_ABSENCES ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_ABSENCES ALTER END_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_ABSENCES ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_ABSENCES ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_ABSENCES ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_ABSENCES (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_REQUIREMENTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER PROJECT_ID SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER LAST_EDITION_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER CREATED_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER NAME SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER END_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER ARCHIVED SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_REQUIREMENTS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_REQUIREMENTS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_M_PROJECTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_PROJECTS ADD CONSTRAINT "UK_JVQQWOOHHHKFJMN1NL3BUMM4L" UNIQUE ("NAME");
  ALTER TABLE T_GPE_M_PROJECTS ALTER METHODOLOGY SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER PROJECT_LEADER SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER START_DATE SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER NAME SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER LAST_EDITION_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER DELETED SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER CREATED_BY SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER ARCHIVED SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER VERSION SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER TIMESTAMP SET NOT NULL;
  ALTER TABLE T_GPE_M_PROJECTS ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_M_PROJECTS (ID);

--------------------------------------------------------
--  Constraints for Table T_GPE_R_TASKS_ASSIGNED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_TASKS_ASSIGNED ALTER ID_TASK SET NOT NULL;
  ALTER TABLE T_GPE_R_TASKS_ASSIGNED ALTER ID_USER SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table T_GPE_P_REPORT_TYPE
--------------------------------------------------------

  ALTER TABLE T_GPE_P_REPORT_TYPE ADD CONSTRAINT "UK_2WNWSN26CO09PYSQUPP9CXP4N" UNIQUE ("CODE");
  ALTER TABLE T_GPE_P_REPORT_TYPE ALTER ID SET NOT NULL;
  CREATE PRIMARY KEY ON T_GPE_P_REPORT_TYPE (ID);

--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_ABSENCES
--------------------------------------------------------

  ALTER TABLE T_GPE_M_ABSENCES ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_DOCUMENTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_DOCUMENTS ADD FOREIGN KEY ("DOCUMENT_TYPE_ID")
	  REFERENCES T_GPE_P_DOCUMENT_TYPES ("ID");
  ALTER TABLE T_GPE_M_DOCUMENTS ADD FOREIGN KEY ("PROJECT_ID")
	  REFERENCES T_GPE_M_PROJECTS ("ID");
  ALTER TABLE T_GPE_M_DOCUMENTS ADD FOREIGN KEY ("REQUIREMENT_ID")
	  REFERENCES T_GPE_M_REQUIREMENTS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_GOALS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_GOALS ADD FOREIGN KEY ("REQUIREMENT_ID")
	  REFERENCES T_GPE_M_REQUIREMENTS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_INCURREDS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_INCURREDS ADD FOREIGN KEY ("TASK_ID")
	  REFERENCES T_GPE_M_TASKS ("ID");
  ALTER TABLE T_GPE_M_INCURREDS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_PROJECTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_PROJECTS ADD FOREIGN KEY ("PRODUCT_OWNER")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_M_PROJECTS ADD FOREIGN KEY ("METHODOLOGY")
	  REFERENCES T_GPE_P_METHODOLOGIES ("ID");
  ALTER TABLE T_GPE_M_PROJECTS ADD FOREIGN KEY ("SCRUM_MASTER")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_M_PROJECTS ADD FOREIGN KEY ("PROJECT_LEADER")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_REPORTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_REPORTS ADD FOREIGN KEY ("REPORT_STATUS")
	  REFERENCES T_GPE_P_REPORT_STATUS ("ID");
  ALTER TABLE T_GPE_M_REPORTS ADD FOREIGN KEY ("REPORT_TYPE")
	  REFERENCES T_GPE_P_REPORT_TYPE ("ID");
  ALTER TABLE T_GPE_M_REPORTS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_REQUIREMENTS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_REQUIREMENTS ADD FOREIGN KEY ("PROJECT_ID")
	  REFERENCES T_GPE_M_PROJECTS ("ID");
  ALTER TABLE T_GPE_M_REQUIREMENTS ADD FOREIGN KEY ("CREATED_BY")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_M_REQUIREMENTS ADD FOREIGN KEY ("LAST_EDITION_BY")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_SERVERS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_SERVERS ADD FOREIGN KEY ("PROJECT_ID")
	  REFERENCES T_GPE_M_PROJECTS ("ID");
  ALTER TABLE T_GPE_M_SERVERS ADD FOREIGN KEY ("SERVER_TYPE")
	  REFERENCES T_GPE_P_SERVER_TYPE ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_TASK_COMMENT
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TASK_COMMENT ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_M_TASK_COMMENT ADD FOREIGN KEY ("TASK_ID")
	  REFERENCES T_GPE_M_TASKS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_TASKS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("PARENT_ID")
	  REFERENCES T_GPE_M_TASKS ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("TASK_STATUS_ID")
	  REFERENCES T_GPE_P_TASK_STATUS ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("REQUIREMENT_ID")
	  REFERENCES T_GPE_M_REQUIREMENTS ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("TASK_TYPE_ID")
	  REFERENCES T_GPE_P_TASK_TYPE ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("TASK_PRIORITY_ID")
	  REFERENCES T_GPE_P_PRIORITY ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("CREATED_BY")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_M_TASKS ADD FOREIGN KEY ("LAST_EDITION_BY")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_TIMESHEETS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TIMESHEETS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_TODOS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_TODOS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_USERS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_USERS ADD FOREIGN KEY ("ROLE_ID")
	  REFERENCES T_GPE_M_ROLES ("ID");
  ALTER TABLE T_GPE_M_USERS ADD FOREIGN KEY ("USER_POSITION")
	  REFERENCES T_GPE_P_USER_CATEGORY ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_M_VACATIONS
--------------------------------------------------------

  ALTER TABLE T_GPE_M_VACATIONS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_R_HISTORICAL_PROJECTS
--------------------------------------------------------

  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ADD FOREIGN KEY ("PROJECT_ID")
	  REFERENCES T_GPE_M_PROJECTS ("ID");
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ADD FOREIGN KEY ("USER_ID")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ADD FOREIGN KEY ("ROLE_ID")
	  REFERENCES T_GPE_M_ROLES ("ID");
  ALTER TABLE T_GPE_R_HISTORICAL_PROJECTS ADD FOREIGN KEY ("REQUIREMENT_ID")
	  REFERENCES T_GPE_M_REQUIREMENTS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_R_PROJECTS_MANAGED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_PROJECTS_MANAGED ADD FOREIGN KEY ("ID_USER")
	  REFERENCES T_GPE_M_USERS ("ID");
  ALTER TABLE T_GPE_R_PROJECTS_MANAGED ADD FOREIGN KEY ("ID_PROJECT")
	  REFERENCES T_GPE_M_PROJECTS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_R_REQUIREMENTS_DEVELOPED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_REQUIREMENTS_DEVELOPED ADD FOREIGN KEY ("ID_REQUIREMENT")
	  REFERENCES T_GPE_M_REQUIREMENTS ("ID");
  ALTER TABLE T_GPE_R_REQUIREMENTS_DEVELOPED ADD FOREIGN KEY ("ID_USER")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_R_TASKS_ASSIGNED
--------------------------------------------------------

  ALTER TABLE T_GPE_R_TASKS_ASSIGNED ADD FOREIGN KEY ("ID_TASK")
	  REFERENCES T_GPE_M_TASKS ("ID");
  ALTER TABLE T_GPE_R_TASKS_ASSIGNED ADD FOREIGN KEY ("ID_USER")
	  REFERENCES T_GPE_M_USERS ("ID");
--------------------------------------------------------
--  Ref Constraints for Table T_GPE_R_TASKS_DEPENDENCIES
--------------------------------------------------------

  ALTER TABLE T_GPE_R_TASKS_DEPENDENCIES ADD FOREIGN KEY ("DEPENDENCY_ID")
	  REFERENCES T_GPE_M_TASKS ("ID");
  ALTER TABLE T_GPE_R_TASKS_DEPENDENCIES ADD FOREIGN KEY ("TASK_ID")
	  REFERENCES T_GPE_M_TASKS ("ID");
