CREATE TABLE resource (
  id   text NOT NULL,
  resource  text NOT NULL,
  data  Text
);

ALTER TABLE ONLY resource ADD CONSTRAINT "ID_PKEY" PRIMARY KEY (id,resource);