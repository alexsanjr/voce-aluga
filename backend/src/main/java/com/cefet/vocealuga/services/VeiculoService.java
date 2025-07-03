package com.cefet.vocealuga.services;

import com.cefet.vocealuga.dtos.VeiculoDTO;
import com.cefet.vocealuga.entities.Estoque;
import com.cefet.vocealuga.entities.Veiculo;
import com.cefet.vocealuga.repositories.EstoqueRepository;
import com.cefet.vocealuga.repositories.VeiculoRepository;
import com.cefet.vocealuga.services.exceptions.DatabaseException;
import com.cefet.vocealuga.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

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

    @Transactional
    public VeiculoDTO insert(VeiculoDTO dto) {
        Veiculo entity = convertToEntity(dto);
        entity = veiculoRepository.save(entity);
        return convertToDTO(entity);
    }

    @Transactional
    public VeiculoDTO update(Long id, VeiculoDTO dto) {
        try {
            Veiculo entity = veiculoRepository.getReferenceById(id);
            dto.setId(id);
            entity = convertToEntity(dto);
            entity = veiculoRepository.save(entity);
            return convertToDTO(entity);
        }
        catch (JpaObjectRetrievalFailureException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            veiculoRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
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
        dto.setEstoqueId(veiculo.getEstoque().getId());
        return dto;
    }

    public Veiculo convertToEntity(VeiculoDTO dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(dto.getId());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setGrupo(dto.getGrupo());
        veiculo.setAno(dto.getAno());
        veiculo.setCor(dto.getCor());
        veiculo.setValorDiaria(dto.getValorDiaria());
        veiculo.setQuilometragem(dto.getQuilometragem());
        veiculo.setStatusVeiculo(dto.getStatusVeiculo());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setMarca(dto.getMarca());

        Estoque estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        veiculo.setEstoque(estoque);
        return veiculo;
    }

}

