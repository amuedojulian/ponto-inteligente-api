SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


--
-- Base de datos: `ponto-inteligente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE IF NOT EXISTS `empresa` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `razon_social` varchar(20) NOT NULL,
    `cnpj` varchar(255) NOT NULL,
    `data_criacao` datetime NOT NULL,
    `data_atualizacao` datetime NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
CREATE TABLE IF NOT EXISTS `funcionario` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `nome` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `senha` varchar(255) NOT NULL,
    `cpf` varchar(255) NOT NULL,
    `valor_hora` decimal(19,2) DEFAULT NULL,
    `qtd_horas_trabalho_dia` float DEFAULT NULL,
    `qtd_horas_almoco` float DEFAULT NULL,
    `perfil` varchar(255) NOT NULL,
    `data_criacao` datetime NOT NULL,
    `data_atualizacao` datetime NOT NULL,
    `empresa_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
CREATE TABLE IF NOT EXISTS `lancamento` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `date` datetime NOT NULL,
    `descricao` varchar(255) NOT NULL,
    `localicacao` varchar(255) NOT NULL,
    `data_criacao` datetime NOT NULL,
    `data_atualizacao` datetime NOT NULL,
    `tipo` varchar(255) NOT NULL,
    `funcionario_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
COMMIT;


ALTER TABLE funcionario ADD FOREIGN KEY (empresa_id) REFERENCES empresa(id);

ALTER TABLE lancamento ADD FOREIGN KEY (funcionario_id) REFERENCES funcionario(id);
