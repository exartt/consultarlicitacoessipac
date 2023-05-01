CREATE TYPE tipo_requisicao AS ENUM ('GET', 'POST', 'PUT', 'DELETE');

CREATE TABLE logs (
    id BIGINT PRIMARY KEY,
    log_descricao VARCHAR(255),
    log_data TIMESTAMP,
    log_tipo_requisicao tipo_requisicao
);