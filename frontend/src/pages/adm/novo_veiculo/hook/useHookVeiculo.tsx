/////////////////////////////////////////////////////////////////
/// Importações de dependências necessárias
/////////////////////////////////////////////////////////////////
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";

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
        valor_diaria: "",
        km: "",
        status_veiculo: "",
        estoque_id: "",
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
            const dados = {
                marca: veiculo.marca,
                modelo: veiculo.modelo,
                grupo: Number(veiculo.grupo),
                ano: Number(veiculo.ano),
                cor: Number(veiculo.cor),
                valor_Diaria: Number(veiculo.valor_diaria),
                quilometragem: Number(veiculo.km),
                statusVeiculo: Number(veiculo.status_veiculo),
                estoqueId: Number(veiculo.estoque_id),
                placa: Number(veiculo.placa),
            };

            const response = ""; // Substituir pela chamada real da API
            return response;
        },
    });

    //////////////////////////////////////////////////////////////////
    /// Função responsável por interceptar o submit do formulário
    /// e disparar a mutação de envio
    //////////////////////////////////////////////////////////////////
    const enviar_form = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        ajuste_veiculo();
    };

    //////////////////////////////////////////////////////////////////
    /// Retorno das funções e estados utilizados no formulário de veículo
    //////////////////////////////////////////////////////////////////
    return { veiculo, pegarVeiculo, enviar_form };
};

export default useHookVeiculo;
