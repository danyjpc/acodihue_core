CREATE SEQUENCE adm_phrase_sequence increment by 1 start with 1;

CREATE TABLE adm_phrase
(
    phrase_id BIGINT                  NOT NULL DEFAULT nextval('adm_phrase_sequence'),
    author    VARCHAR(25) DEFAULT ' ' NOT NULL,
    phrase    VARCHAR(25) DEFAULT ' ' NOT NULL,
    CONSTRAINT phrase_id PRIMARY KEY (phrase_id)
);



INSERT INTO adm_phrase (author, phrase)
VALUES ('Twitter', 'Kotlin is cool');
INSERT INTO adm_phrase (author, phrase)
VALUES ('TIOBE', 'Java is the king');
