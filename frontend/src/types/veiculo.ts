export interface Veiculo {
  id: number;
  modelo: string;
  marca: string;
  placa: string;
  ano: number;
  cor: string;
  filial: string;
  statusVeiculo: string;
  imagemUrl?: string;
  [key: string]: any;
}
