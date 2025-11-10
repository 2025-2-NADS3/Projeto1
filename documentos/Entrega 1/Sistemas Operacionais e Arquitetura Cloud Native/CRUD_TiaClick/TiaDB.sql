CREATE DATABASE TiaDB;
USE TiaDB;

SHOW TABLES;

/*Criação da tabela de cadastro do consumidor*/
CREATE TABLE consumidor(
	id_consumidor INT PRIMARY KEY AUTO_INCREMENT,
    nome_consumidor VARCHAR(255) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    idade INT,
    tipo_consumidor ENUM('aluno', 'professor', 'visitante') NOT NULL,
    ra INT UNIQUE
);
SELECT * FROM consumidor; /*Mostra dados da tabela de consumidores*/
DESCRIBE consumidor; /*Mostra a estrutura da tabela de consumidores*/


/*Criação da tabela de itens vendidos na comedoria*/
CREATE TABLE cardapio(
	id_item INT PRIMARY KEY AUTO_INCREMENT,
    tipo_item VARCHAR(50) NOT NULL,
    nome_item VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0),
    estoque INT,
    disponivel BOOLEAN DEFAULT TRUE,
    criacao_item TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizacao_item TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
SELECT * FROM cardapio; /*Mostra dados do cardapio*/
DESCRIBE cardapio; /*Mostra a estrutura da do cardapio*/


/*Criação da tabela de pedidos*/
CREATE TABLE pedido(
	id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    id_consumidor INT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status_pedido ENUM('aberto', 'fechado') NOT NULL DEFAULT 'aberto',
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0,
    criacao_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizacao_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_consumidor) REFERENCES consumidor(id_consumidor)
);
SELECT * FROM pedido; /*Mostra dados da tabela de pedidos*/
DESCRIBE pedido; /*Mostra a estrutura da tabela de pedidos*/


/*Criação da tabela intermediária de itens pedidos*/
CREATE TABLE item_pedido(
	id_item_pedido INT PRIMARY KEY AUTO_INCREMENT,
    id_item INT NOT NULL,
    id_pedido INT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10,2) NOT NULL CHECK (preco_unitario >= 0),
    
    FOREIGN KEY (id_item) REFERENCES cardapio(id_item),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);
SELECT * FROM item_pedido; /*Mostra dados da tabela de iens pedidos pelo consumidor*/
DESCRIBE item_pedido; /*Mostra a estrutura da tabela de itens pedidos pelo consumidor*/


/*Criação da tabela de pagamento*/
CREATE TABLE pagamento(
	id_pagamento INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    tipo_pagamento ENUM('debito', 'credito', 'app', 'pix', 'dinheiro') NOT NULL,
    valor_pago DECIMAL(10,2) NOT NULL CHECK (valor_pago >= 0),
    data_hora_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);
SELECT * FROM pagamento; /*Mostra dados da tabela de pagamentos*/
DESCRIBE pagamento; /*Mostra a estrutura da tabela de pagamentos*/


/*CRIAÇÃO DOS TRIGGERS*/
/*Trigger de validação de RA*/
DELIMITER //
CREATE TRIGGER trig_ra BEFORE INSERT ON consumidor
FOR EACH ROW
BEGIN
	IF NEW.tipo_consumidor = 'aluno' AND NEW.ra IS NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Aluno precisa ter RA';
	END IF;
    IF NEW.tipo_consumidor != 'aluno' AND NEW.ra IS NOT NULL THEN
		SIGNAL sqlSTATE '45000' SET MESSAGE_TEXT = 'Apenas alunos têm RA';
	END IF;
END;
//
DELIMITER ;


/*Trigger de cálculo do valor total de um pedido*/
DELIMITER //
CREATE TRIGGER trig_valor_total AFTER INSERT ON item_pedido
FOR EACH ROW
BEGIN
	UPDATE pedido
    SET valor_total = (
		SELECT IFNULL(SUM(quantidade * preco_unitario),0) 
        FROM item_pedido WHERE id_pedido = NEW.id_pedido
    )
    WHERE id_pedido = NEW.id_pedido;
END;
//
DELIMITER 