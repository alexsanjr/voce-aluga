import { useQuery } from "@tanstack/react-query";
import { getVeiculos, type Veiculo } from "../lista-ficticia";

const useHookAdmList = () => {
    const {
        data: Lista_veiculos,
        // isLoading,
        // isError,
    } = useQuery<Veiculo[]>({
        queryKey: ["veiculos"],
        queryFn: getVeiculos,
    });

    return { Lista_veiculos };
};

export default useHookAdmList;
