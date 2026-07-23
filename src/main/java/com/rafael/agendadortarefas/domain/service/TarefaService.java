package com.rafael.agendadortarefas.domain.service;

import com.rafael.agendadortarefas.infrastructure.mapper.TarefaConverter;
import com.rafael.agendadortarefas.api.dto.AgendadorBffMailResponseDTO;
import com.rafael.agendadortarefas.api.dto.BffAgendadorAgendamentoRequestDTO;
import com.rafael.agendadortarefas.domain.entity.StatusTarefa;
import com.rafael.agendadortarefas.domain.entity.Tarefa;
import com.rafael.agendadortarefas.domain.exceptions.ResourceNotFoundException;
import com.rafael.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;

    // ====================== CRIAR AGENDAMENTO ======================
    @Transactional
    public AgendadorBffMailResponseDTO criarTarefa(BffAgendadorAgendamentoRequestDTO request) {

        Tarefa novaTarefa = tarefaConverter.toEntity(request);

        // Apenas regras de negócio manuais. A dataCriacao o Spring resolve!
        novaTarefa.setStatus(StatusTarefa.PENDENTE);
        novaTarefa.setTentativas(0);

        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);

        return tarefaConverter.toResponseDTO(tarefaSalva);
    }

    // ====================== BUSCAR TAREFA POR ID ======================
    @Transactional(readOnly = true)
    public AgendadorBffMailResponseDTO buscarTarefaPorId(String tarefaId, Long usuarioId) {

        Tarefa tarefa = tarefaRepository.findByIdAndUsuarioId(tarefaId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada ou não pertence ao usuário."));

        return tarefaConverter.toResponseDTO(tarefa);
    }

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    @Transactional(readOnly = true)
    public List<AgendadorBffMailResponseDTO> listarTarefas(Long usuarioId) {

        List<Tarefa> tarefas = tarefaRepository.findAllByUsuarioId(usuarioId);

        return tarefas.stream()
                .map(tarefaConverter::toResponseDTO)
                .toList();
    }

    // ====================== DELETAR AGENDAMENTO  ======================
    @Transactional
    public void deletarTarefa(String tarefaId, Long usuarioId) {
        Tarefa tarefa = tarefaRepository.findByIdAndUsuarioId(tarefaId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada ou não pertence ao usuário."));

        tarefaRepository.delete(tarefa);
    }

    // ====================== SCHEDULER BFF: VERIFICAR PENDÊNCIAS ======================
    @Transactional(readOnly = true)
    public List<AgendadorBffMailResponseDTO> buscarTarefasPendentes() {
        List<Tarefa> tarefas = tarefaRepository.findByStatus("PENDENTE");
        return tarefas.stream()
                .map(tarefaConverter::toResponseDTO)
                .toList();
    }

    // ====================== ATUALIZAR STATUS ============================
    @Transactional
    public void alterarStatus(String id, String novoStatus) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id: " + id));

        tarefa.setStatus(novoStatus);
        tarefaRepository.save(tarefa);
    }

    // ====================== DELETAR AGENDAMENTOS DO USUARIO  ======================
    @Transactional
    public void deletarTarefaDefinitivo(Long usuarioId) {
        tarefaRepository.deleteAllByUsuarioId(usuarioId);
    }

    // ====================== BUSCAR POR PERÍODO ======================
    @Transactional(readOnly = true)
    public List<AgendadorBffMailResponseDTO> buscarPorPeriodo(Long usuarioId, LocalDateTime dataInicial, LocalDateTime dataFinal) {

        log.info(">>> [Agendador] Buscando tarefas do usuário ID: {} no período entre {} e {}", usuarioId, dataInicial, dataFinal);

        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }

        List<Tarefa> tarefas = tarefaRepository.findByUsuarioIdAndDataHoraAgendadaBetween(usuarioId, dataInicial, dataFinal);

        return tarefas.stream()
                .map(tarefaConverter::toResponseDTO)
                .toList();
    }
}