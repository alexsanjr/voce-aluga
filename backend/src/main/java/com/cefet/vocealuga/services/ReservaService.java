package com.cefet.vocealuga.services;

import com.cefet.vocealuga.dtos.ReservaDTO;
import com.cefet.vocealuga.entities.Filial;
import com.cefet.vocealuga.entities.Reserva;
import com.cefet.vocealuga.repositories.FilialRepository;
import com.cefet.vocealuga.repositories.ReservaRepository;
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
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private FilialRepository filialRepository;
    

    @Transactional(readOnly = true)
    public ReservaDTO findById(Long id) {

        Reserva entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Estação de serviço não encontrada"));
        return convertToDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ReservaDTO> findAll(Pageable pageable) {
        Page<Reserva> result = repository.findAll(pageable);
        return result.map(this::convertToDTO);
    }

    @Transactional
    public ReservaDTO insert(ReservaDTO dto) {
        Reserva entity = convertToEntity(dto);
        entity = repository.save(entity);
        return convertToDTO(entity);
    }

    @Transactional
    public ReservaDTO update(Long id, ReservaDTO dto) {
        try {
            Reserva entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
            dto.setId(entity.getId());
            entity = convertToEntity(dto);
            entity = repository.save(entity);
            return convertToDTO(entity);
        }
        catch (JpaObjectRetrievalFailureException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    public ReservaDTO convertToDTO(Reserva entity) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(entity.getId());
        dto.setDataReserva(entity.getDataReserva());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setCategoria(entity.getCategoria());
        dto.setStatus(entity.getStatus());
        dto.setLocalRetiradaId(entity.getLocalRetirada().getId());
        return dto;
    }

    public Reserva convertToEntity(ReservaDTO dto) {
        Reserva entity = new Reserva();
        entity.setId(dto.getId());
        entity.setDataReserva(dto.getDataReserva());
        entity.setDataVencimento(dto.getDataVencimento());
        entity.setCategoria(dto.getCategoria());
        entity.setStatus(dto.getStatus());


        Filial filial = filialRepository.findById(dto.getLocalRetiradaId())
                .orElseThrow(() -> new RuntimeException("Local de retirada não encontrada"));
        filial.setId(dto.getLocalRetiradaId());
        entity.setLocalRetirada(filial);

        return entity;
    }

}

