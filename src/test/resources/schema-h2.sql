CREATE SEQUENCE seq_distribuicao_id
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1;


CREATE SEQUENCE seq_familia_id
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1;

CREATE SEQUENCE seq_membro_id
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1;

CREATE TABLE distribuicao
(
    id                INTEGER  PRIMARY KEY ,
    distribuicao_data TIMESTAMP(6)
);

CREATE TABLE familia
(
    id          INTEGER PRIMARY KEY ,
    pontuacao   INTEGER,
    data_cadastro TIMESTAMP,
    distribuicao_id BIGINT CONSTRAINT fk_familia_distribuicao_id REFERENCES distribuicao,
    renda_total NUMERIC(38, 2)
);

CREATE TABLE membro
(
    id         INTEGER PRIMARY KEY ,
    idade      INTEGER,
    nome       VARCHAR(255),
    familia_id BIGINT CONSTRAINT fk_membro_familia_id REFERENCES familia
);