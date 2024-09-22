CREATE TABLE IF NOT EXISTS Run (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp,
    completed_on timestamp,
    kilometers INT NOT NULL,
    location varchar(18) NOT NULL,
    PRIMARY KEY (id)
);
