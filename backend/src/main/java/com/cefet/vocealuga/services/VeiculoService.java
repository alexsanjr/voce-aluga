package com.cefet.vocealuga.services;

import com.cefet.vocealuga.dtos.VeiculoDTO;
import com.cefet.vocealuga.entities.Veiculo;
import com.cefet.vocealuga.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(readOnly = true)
    public VeiculoDTO findById(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Veiculo não encontrado")
        );
        return convertToDTO(veiculo);
    }

    @Transactional(readOnly = true)
    public Page<VeiculoDTO> findAll(String name, Pageable pageable) {
        Page<Veiculo> result = veiculoRepository.searchByBrand(name, pageable);
        return result.map(this::convertToDTO);
    }



    public VeiculoDTO convertToDTO(Veiculo veiculo) {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setModelo(veiculo.getModelo());
        dto.setGrupo(veiculo.getGrupo());
        dto.setAno(veiculo.getAno());
        dto.setCor(veiculo.getCor());
        dto.setValorDiaria(veiculo.getValorDiaria());
        dto.setQuilometragem(veiculo.getQuilometragem());
        dto.setStatusVeiculo(veiculo.getStatusVeiculo());
        dto.setPlaca(veiculo.getPlaca());
        dto.setMarca(veiculo.getMarca());
        return dto;
    }

}

