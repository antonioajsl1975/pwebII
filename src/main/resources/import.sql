INSERT INTO pessoa (email, telefone) VALUES ('joao@gmail.com','11999998888');
INSERT INTO pessoa (email, telefone) VALUES ('maria@gmail.com','11988887777');
INSERT INTO pessoa (email, telefone) VALUES ('carlos@gmail.com','21977776666');
INSERT INTO pessoa (email, telefone) VALUES ('ana@gmail.com','31966665555');
INSERT INTO pessoa (email, telefone) VALUES ('x@gmail.com','41955554444');
INSERT INTO pessoa (email, telefone) VALUES ('y@outlook.com','51944443333');
INSERT INTO pessoa (email, telefone) VALUES ('z@bol.com.br','61933332222');
INSERT INTO pessoa (email, telefone) VALUES ('servicos@yahoo.com','71922221111');

INSERT INTO pessoa_fisica (id, nome, cpf) VALUES (1, 'João Silva', '12345678901');
INSERT INTO pessoa_fisica (id, nome, cpf) VALUES (2, 'Maria Oliveira', '98765432109');
INSERT INTO pessoa_fisica (id, nome, cpf) VALUES (3, 'Carlos Souza', '11122233344');
INSERT INTO pessoa_fisica (id, nome, cpf) VALUES (4, 'Ana Pereira', '44455566677');

INSERT INTO pessoa_juridica (id, razao_Social, cnpj) VALUES (5, 'Empresa X LTDA', '01234567000189');
INSERT INTO pessoa_juridica (id, razao_Social, cnpj) VALUES (6, 'Empresa Y SA', '98765432000156');
INSERT INTO pessoa_juridica (id, razao_Social, cnpj) VALUES (7, 'Comércio Z ME', '12345678000101');
INSERT INTO pessoa_juridica (id, razao_Social, cnpj) VALUES (8, 'Serviços W LTDA', '23456789000112');

INSERT INTO produto (descricao, valor) VALUES ('Arroz 1kg', 5.50);
INSERT INTO produto (descricao, valor) VALUES ('Feijão 1kg', 6.80);
INSERT INTO produto (descricao, valor) VALUES ('Óleo de Soja 900ml', 7.90);
INSERT INTO produto (descricao, valor) VALUES ('Açúcar 1kg', 4.20);
INSERT INTO produto (descricao, valor) VALUES ('Café 500g', 9.50);
INSERT INTO produto (descricao, valor) VALUES ('Macarrão 500g', 3.40);
INSERT INTO produto (descricao, valor) VALUES ('Farinha de Trigo 1kg', 4.80);
INSERT INTO produto (descricao, valor) VALUES ('Leite Integral 1L', 4.30);
INSERT INTO produto (descricao, valor) VALUES ('Pernil Suíno 1kg', 35.90);

INSERT INTO venda (data_venda, pessoa_id) VALUES ('2024-11-21T09:30:00', 1);
INSERT INTO venda (data_venda, pessoa_id) VALUES ('2024-11-22T10:45:30', 2);
INSERT INTO venda (data_venda, pessoa_id) VALUES ('2024-11-23T15:00:00', 5);
INSERT INTO venda (data_venda, pessoa_id) VALUES ('2024-12-23T15:00:00', 5);

INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (2.0, 1, 5.50, 1); -- 2 pacotes de Arroz na primeira venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (1.0, 2, 6.80, 1); -- 1 pacote de Feijão na primeira venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (3.0, 3, 7.90, 1); -- 3 garrafas de Óleo de Soja na primeira venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (1.0, 4, 4.20, 2); -- 1 pacote de Açúcar na segunda venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (2.0, 5, 9.50, 2); -- 2 pacotes de Café na segunda venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (3.0, 6, 3.40, 2); -- 3 pacotes de Macarrão na segunda venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (1.0, 7, 4.80, 3); -- 1 pacote de Farinha de Trigo na terceira venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (1.0, 2, 7.90, 3); -- 1 pacote de Farinha de Trigo na terceira venda
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (2.0, 8, 4.20, 4);
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (6.0, 1, 4.20, 4);
INSERT INTO item_venda (quantidade, produto_id, preco, venda_id) VALUES (3.0, 5, 4.20, 4);

INSERT INTO departamento(nome) VALUES ('Financeiro');
INSERT INTO departamento(nome) VALUES ('Recursos Humanos');
INSERT INTO departamento(nome) VALUES ('Contabilidade');
INSERT INTO departamento(nome) VALUES ('Tecnologia da Informação');

