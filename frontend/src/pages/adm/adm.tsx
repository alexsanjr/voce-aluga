import ListaFrota from "./lista_frota/lista_frota";
import ListaTransferencia from "./lista_transferencia/lista_transferencia";
import { useState } from "react";

const Adm: React.FC = () => {
    const [pagina, setPagina] = useState<"frota" | "transferencia">("frota");

    return (
        <>
            {pagina === "frota" && <ListaFrota />}
            {pagina === "transferencia" && <ListaTransferencia />}
        </>
    );
};

export default Adm;
