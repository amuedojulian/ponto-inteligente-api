INSERT INTO `empresa` (`id`, `razon_social`, `cnpj`, `data_criacao`, `data_atualizacao`)
VALUES (NULL, 'Julian Amuedo', '82198127000121', CURRENT_DATE(), CURRENT_DATE());

INSERT INTO `funcionario` (`id`, `nome`, `email`, `senha`, `cpf`, `valor_hora`, `qtd_horas_trabalho_dia`, `qtd_horas_almoco`, `perfil`,
                           `data_criacao`, `data_atualizacao`, `empresa_id`)
VALUES (NULL, 'Julián Amuedo', 'amuedojulian05@gmail.com', '$2a$10$pLuryUVYs.ZFoij8bpTbp.uw5wrOc0IaFSfUJXztiuulTfU0W8EHG', '71661146147',
        NULL, NULL, NULL, 'ROLE_ADMIN', CURRENT_DATE(), CURRENT_DATE(),
        (SELECT `id` FROM `empresa` WHERE `cnpj` = '11861136000102'));
