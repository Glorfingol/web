DROP TABLE IF EXISTS news_entry;
DROP TABLE IF EXISTS news_content;
DROP TABLE IF EXISTS news_image;

CREATE TABLE IF NOT EXISTS  news_entry (
    id BIGINT(20) NOT NULL,
    author VARCHAR(50) NOT NULL,
    tags VARCHAR(50) NULL,
    title VARCHAR(50) NOT NULL,
    image_id VARCHAR(50) NULL,
    content_id VARCHAR(50) NULL,
    creation_date DATETIME NOT NULL,
    modification_date DATETIME NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;

CREATE INDEX CREATION_DATE
ON news_entry (creation_date);

CREATE TABLE IF NOT EXISTS  news_content (
    id BIGINT(20) NOT NULL,
    content VARCHAR(200) NOT NULL,
    creation_date DATETIME NOT NULL,
    modification_date DATETIME NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS  news_image(
    id BIGINT(20) NOT NULL,
    src VARCHAR(65535) NOT NULL,
    alt VARCHAR(50) NOT NULL,
    legend VARCHAR(50) NOT NULL,
    width INT(10) NOT NULL,
    height INT(10) NOT NULL,
    creation_date DATETIME NOT NULL,
    modification_date DATETIME NOT NULL,
    PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;







