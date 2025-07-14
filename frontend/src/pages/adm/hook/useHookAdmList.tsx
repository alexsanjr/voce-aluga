import { useQuery } from "@tanstack/react-query";
import { getAllVeiculos } from "../../../services/veiculosService";

const useHookAdmList = () => {
    const {
        data,
        // isLoading,
        // isError,
    } = useQuery({
        queryKey: ["veiculos"],
        queryFn: getAllVeiculos,
    });

    // O backend pode retornar { content: Veiculo[] }, então normaliza
    const Lista_veiculos = data?.content || data || [];
    return { Lista_veiculos };
};

export default useHookAdmList;
