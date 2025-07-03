INSERT INTO veiculo (marca, modelo, grupo, ano, cor, valor_Diaria, quilometragem, status_Veiculo, placa) VALUES ('Toyota', 'Corolla', 0, 2022, 2, 199.9, 34800L, 1, 'ABC-1234');
INSERT INTO veiculo (marca, modelo, grupo, ano, cor, valor_Diaria, quilometragem, status_Veiculo, placa)VALUES ('Honda', 'Civic', 1, 2023, 3, 249.90, 12800, 1, 'XYZ-5678');
INSERT INTO veiculo (marca, modelo, grupo, ano, cor, valor_Diaria, quilometragem, status_Veiculo, placa)VALUES ('Chevrolet', 'Onix', 2, 2021, 4, 159.50, 45200, 0, 'DEF-4321');
INSERT INTO veiculo (marca, modelo, grupo, ano, cor, valor_Diaria, quilometragem, status_Veiculo, placa)VALUES ('Ford', 'Ka', 2, 2020, 1, 129.99, 67800, 3, 'GHI-8765');

INSERT INTO filial (nome, local) VALUES ('Filial 1', 'Rio de Janeiro');
INSERT INTO filial (nome, local) VALUES ('Filial 2', 'São Paulo');
INSERT INTO filial (nome, local) VALUES ('Filial 3', 'Salvador');

INSERT INTO estoque (nome, filial_id) VALUES ('Estoque 1',1);
INSERT INTO estoque (nome, filial_id) VALUES ('Estoque 2',2);

INSERT INTO TRANSFERENCIA_VEICULOS (status, data, estoque_destino_id, estoque_origem_id) VALUES (1, TIMESTAMP WITH TIME ZONE '2022-07-25T15:00:00Z', 1, 2);

INSERT INTO TRANSFERENCIA_VEICULOS_VEICULO (transferencia_id, veiculo_id) VALUES (1 ,1);
INSERT INTO TRANSFERENCIA_VEICULOS_VEICULO (transferencia_id, veiculo_id) VALUES (1 ,2);

INSERT INTO ESTACAO_DE_SERVICO (local, nome) VALUES ('São Paulo - SP', 'Filial Centro');
INSERT INTO ESTACAO_DE_SERVICO (local, nome) VALUES ( 'Rio de Janeiro - RJ', 'Filial Centro');
INSERT INTO ESTACAO_DE_SERVICO (local, nome) VALUES ('Curitiba - PR', 'Pátio Sul');

INSERT INTO Agendar_Manutencao (data_manutencao, estacao_de_servico_id, veiculo_id, motivo_manutencao) VALUES (TIMESTAMP WITH TIME ZONE '2025-07-03T10:00:00Z', 2, 4, 'pneu furado');