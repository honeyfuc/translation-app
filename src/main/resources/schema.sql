DROP TABLE IF EXISTS response;

DROP TABLE IF EXISTS request;

CREATE TABLE request (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    circulation_time TIMESTAMP NOT NULL,
    translate_from VARCHAR(2) NOT NULL,
    translate_to VARCHAR(2) NOT NULL,
    ip VARCHAR(45) DEFAULT NULL
);

CREATE TABLE response (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    content_part VARCHAR(255) NOT NULL,
    request_id INTEGER NOT NULL,
    FOREIGN KEY (request_id) REFERENCES request (id)
);
