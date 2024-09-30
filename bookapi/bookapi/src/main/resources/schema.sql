CREATE TABLE IF NOT EXISTS Book (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    author varchar(250) NOT NULL,
    year INT NOT NULL,
    rating INT NOT NULL,
    status varchar(15) NOT NULL,
    PRIMARY KEY (id)
    );