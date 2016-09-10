CREATE TABLE developers (
  developerId           serial  NOT NULL,
  developerToken        TEXT    NOT NULL,
  email                 TEXT    NOT NULL,
  password              TEXT    NOT NULL,
  name                  TEXT    NOT NULL,
  enabled               boolean NOT NULL,
  dateOfRecordCreated   serial  NOT NULL,
  dateOfLastUsed        serial  NOT NULL,
  CONSTRAINT developers_pkey PRIMARY KEY (developerId)
)
WITH (
OIDS = FALSE
);

CREATE TABLE user_roles (
  id       SERIAL NOT NULL,
  email    TEXT   NOT NULL,
  role     TEXT   NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE applications
(
  developerId           serial  NOT NULL,
  developerToken        text    NOT NULL,
  applicationId         serial  NOT NULL,
  applicationToken      text    NOT NULL,
  shortName             text    NOT NULL,
  response              text    NOT NULL,
  message               text    NOT NULL,
  documentationUrl      text    NOT NULL,
  dateOfRecordCreated   serial  NOT NULL,
  dateOfLastUsedByApi   serial  NOT NULL,
  CONSTRAINT applications_pkey PRIMARY KEY (applicationId)
)
WITH (
OIDS=FALSE
);

CREATE TABLE change_email
(
  id                    serial  NOT NULL,
  developerToken        text    NOT NULL,
  old_email             text    NOT NULL,
  old_email_confirm     boolean    NOT NULL,
  new_email             text    NOT NULL,
  new_email_confirm     boolean    NOT NULL,
  dateOfRecordCreated   serial  NOT NULL,
  CONSTRAINT change_email_pkey PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);

CREATE TABLE reset_password (
  id            SERIAL NOT NULL,
  token         TEXT   NOT NULL,
  email         TEXT   NOT NULL,
  CONSTRAINT reset_password_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);