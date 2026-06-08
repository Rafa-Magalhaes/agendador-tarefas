package com.rafael.agendadortarefas.infrastructure.business;

import com.rafael.agendadortarefas.infrastructure.business.converter.TarefaConverter;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import com.rafael.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.rafael.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;

    // ==================== MÉTODO POST ====================

    @Transactional
    public TarefaResponseDTO criarTarefa(TarefaRequestDTO dto) {

        // Validação via Feign Client (integração com o serviço de usuários)
        // UsuarioResponseDTO usuario = usuarioClient.buscarUsuarioPorId(dto.getUsuarioId());

        Tarefa tarefa = tarefaConverter.toEntity(dto);
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setDataCriacao(LocalDateTime.now());

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return tarefaConverter.toResponseDTO(tarefaSalva);
    }

    // ==================== MÉTODOS GET ====================

    @Transactional(readOnly = true)
    public TarefaResponseDTO buscarPorId(String id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id: " + id));
        return tarefaConverter.toResponseDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorUsuarioId(Long usuarioId) {
        List<Tarefa> tarefas = tarefaRepository.findByUsuarioId(usuarioId);
        return tarefas.stream()
                .map(tarefaConverter::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser maior que a data final");
        }

        List<Tarefa> tarefas = tarefaRepository.findByDataHoraAgendadaBetween(dataInicial, dataFinal);
        return tarefas.stream()
                .map(tarefaConverter::toResponseDTO)
                .toList();
    }
}