CREATE TABLE postgreslic_licitacoes (
    id BIGINT PRIMARY KEY,
    lic_codigo_licitacao VARCHAR(255),
    lic_processo VARCHAR(255),
    lic_status VARCHAR(255),
    lic_localizacao VARCHAR(255),
    lic_vigencia_inicio DATE,
    lic_vigencia_fim DATE,
    lic_url_visualizar VARCHAR(255),
    lic_url_itens_ata VARCHAR(255),
    lic_url_download VARCHAR(255),
    lic_registro_lido BOOLEAN,
    lic_url_processos VARCHAR(255),
    lic_descricao VARCHAR(255),
    lic_data_consulta DATE
);