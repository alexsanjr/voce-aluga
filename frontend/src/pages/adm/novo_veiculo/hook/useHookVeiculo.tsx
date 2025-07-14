/////////////////////////////////////////////////////////////////
/// Importações de dependências necessárias
/////////////////////////////////////////////////////////////////

import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { createVeiculo } from "../../../../services/veiculosService";

/////////////////////////////////////////////////////////////////
/// Hook customizado para controle do formulário de veículo
/////////////////////////////////////////////////////////////////
const useHookVeiculo = () => {
    //////////////////////////////////////////////////////////////////
    /// Estado principal do formulário contendo os dados do veículo
    //////////////////////////////////////////////////////////////////
    const [veiculo, setVeiculo] = useState({
        marca: "",
        modelo: "",
        grupo: "",
        ano: "",
        cor: "",
        valorDiaria: "",
        quilometragem: "",
        statusVeiculo: "",
        estoqueId: "",
        placa: "",
    });

    //////////////////////////////////////////////////////////////////
    /// Atualiza os campos do estado `veiculo` dinamicamente
    /// com base no nome do campo e seu valor
    //////////////////////////////////////////////////////////////////
    const pegarVeiculo = (name: string, value: string) => {
        setVeiculo((i) => ({ ...i, [name]: value }));
    };

    //////////////////////////////////////////////////////////////////
    /// Mutação responsável por enviar os dados do veículo para a API
    /// - Os dados são convertidos para os tipos esperados
    /// - Substitua a constante `response` pela chamada real à API
    //////////////////////////////////////////////////////////////////
    const { mutateAsync: ajuste_veiculo } = useMutation({
        mutationKey: ["ajuste_veiculo"],
        mutationFn: async () => {
            // Envia os campos com nomes e tipos corretos
            const dados = {
                marca: veiculo.marca,
                modelo: veiculo.modelo,
                grupo: veiculo.grupo,
                ano: Number(veiculo.ano),
                cor: veiculo.cor,
                valorDiaria: Number(veiculo.valorDiaria),
                quilometragem: Number(veiculo.quilometragem),
                statusVeiculo: veiculo.statusVeiculo,
                estoqueId: veiculo.estoqueId,
                placa: veiculo.placa,
            };
            return await createVeiculo(dados);
        },
    });

    //////////////////////////////////////////////////////////////////
    /// Função responsável por interceptar o submit do formulário
    /// e disparar a mutação de envio
    //////////////////////////////////////////////////////////////////
    const enviar_form = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // Validação simples: todos os campos obrigatórios preenchidos
        const obrigatorios = [
            veiculo.marca,
            veiculo.modelo,
            veiculo.grupo,
            veiculo.ano,
            veiculo.cor,
            veiculo.valorDiaria,
            veiculo.quilometragem,
            veiculo.statusVeiculo,
            veiculo.estoqueId,
            veiculo.placa,
        ];
        // Permite zero como valor válido para campos numéricos
        const algumVazio = obrigatorios.some((v) => v === undefined || v === null || (typeof v === "string" && v.trim() === ""));
        if (algumVazio) {
            alert("Preencha todos os campos obrigatórios antes de salvar.");
            return;
        }
        ajuste_veiculo();
    };

    //////////////////////////////////////////////////////////////////
    /// Retorno das funções e estados utilizados no formulário de veículo
    //////////////////////////////////////////////////////////////////
    return { veiculo, pegarVeiculo, enviar_form };
};

export default useHookVeiculo;
