package com.cefet.vocealuga.services;

import com.cefet.vocealuga.dtos.ReservaDTO;
import com.cefet.vocealuga.entities.Filial;
import com.cefet.vocealuga.entities.Reserva;
import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.entities.enums.StatusReserva;
import com.cefet.vocealuga.repositories.FilialRepository;
import com.cefet.vocealuga.repositories.ReservaRepository;
import com.cefet.vocealuga.repositories.UsuarioRepository;
import com.cefet.vocealuga.services.exceptions.DatabaseException;
import com.cefet.vocealuga.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


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
    public ReservaDTO insert(ReservaDTO dto, Authentication authentication) {
        if (dto.getUsuarioId() == null) {
            throw new IllegalArgumentException("UsuárioId é obrigatório");
        }
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication é obrigatório");
        }
        if (dto.getLocalRetiradaId() == null) {
            throw new IllegalArgumentException("Local de retirada é obrigatório");
        }
        if (dto.getDataReserva() == null || dto.getDataVencimento() == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        if (!dto.getDataVencimento().isAfter(dto.getDataReserva())) {
            throw new IllegalArgumentException("Data de vencimento deve ser posterior à data de reserva");
        }

        // Obtém o usuário logado do contexto de autenticação
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        dto.setUsuarioId(usuarioLogado.getId());

        Reserva entity = convertToEntity(dto);
        entity = repository.save(entity);
        return convertToDTO(entity);
    }

    @Transactional
    public ReservaDTO update(Long id, ReservaDTO dto) {
        try {
            Reserva entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

            if (entity.getStatus() == StatusReserva.EM_ANDAMENTO) {
                throw new IllegalArgumentException("Não é permitido atualizar reservas aprovadas ou em andamento");
            }

            if (dto.getDataReserva() == null || dto.getDataVencimento() == null) {
                throw new IllegalArgumentException("Datas não podem ser nulas");
            }

            if (!dto.getDataVencimento().isAfter(dto.getDataReserva())) {
                throw new IllegalArgumentException("Data de vencimento deve ser posterior à data de reserva");
            }

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
        dto.setLocalRetiradaId(entity.getLocalRetirada() != null ? entity.getLocalRetirada().getId() : null);
        dto.setUsuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null);
        return dto;
    }

    public Reserva convertToEntity(ReservaDTO dto) {
        Reserva entity = new Reserva();
        entity.setId(dto.getId());
        entity.setDataReserva(dto.getDataReserva());
        entity.setDataVencimento(dto.getDataVencimento());
        entity.setCategoria(dto.getCategoria());
        entity.setStatus(dto.getStatus());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setId(dto.getUsuarioId());
        entity.setUsuario(usuario);

        Filial filial = filialRepository.findById(dto.getLocalRetiradaId())
                .orElseThrow(() -> new RuntimeException("Local de retirada não encontrada"));
        filial.setId(dto.getLocalRetiradaId());
        entity.setLocalRetirada(filial);

        return entity;
    }

}

