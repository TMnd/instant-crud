CREATE TABLE resource (
    apikey   text NOT NULL,
    resource  text NOT NULL,
    data_id  text NOT NULL,
    data  text
);

ALTER TABLE ONLY resource ADD CONSTRAINT "ID_PKEY" PRIMARY KEY (apikey,resource);