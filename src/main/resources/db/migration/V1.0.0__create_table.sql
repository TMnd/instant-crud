CREATE TABLE api (
     apikey text    NOT NULL    PRIMARY KEY,
     origin   text    NOT NULL
);

CREATE TABLE resource (
    data_id text    NOT NULL    PRIMARY KEY,
    apikey  text    NOT NULL,
    topic   text    NOT NULL,
    data    text    NOT NULL,
    FOREIGN KEY (apikey) REFERENCES api(apikey)
);