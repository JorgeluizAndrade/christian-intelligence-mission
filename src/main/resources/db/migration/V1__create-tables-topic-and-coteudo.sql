CREATE TABLE topic (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    topic_nome VARCHAR(50) NOT NULL,
    topic_desc VARCHAR(155) NOT NULL,
    topico_pai_id BIGINT,
    tipo VARCHAR(50),
    CONSTRAINT fk_topic_pai FOREIGN KEY (topico_pai_id) REFERENCES topic(id)
);

CREATE TABLE conteudo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(40) NOT NULL,
    descricao VARCHAR(140),
    autor VARCHAR(30) NOT NULL,
    link VARCHAR(130) NOT NULL,
    tipo_conteudo VARCHAR(20) NOT NULL,
    topico_id BIGINT,
    CONSTRAINT fk_topico_conteudo FOREIGN KEY (topico_id) REFERENCES topic(id)
);
