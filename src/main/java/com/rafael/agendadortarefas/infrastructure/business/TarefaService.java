package com.rafael.agendadortarefas.infrastructure.business;

import com.rafael.agendadortarefas.infrastructure.client.UsuarioClient;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import com.rafael.agendadortarefas.infrastructure.dto.UsuarioResponseDTO;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import com.rafael.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioClient usuarioClient;   // ← NOVO

    public TarefaResponseDTO criarTarefa(TarefaRequestDTO dto) {

        // ====================== NOVA INTEGRAÇÃO ======================
        // Chama o projeto 'usuario' para validar se o usuário existe
        UsuarioResponseDTO usuario = usuarioClient.buscarUsuarioPorId(dto.getUsuarioId());

        // ============================================================

        Tarefa tarefa = new Tarefa();
        tarefa.setUsuarioId(dto.getUsuarioId());
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setDataHoraAgendada(dto.getDataHoraAgendada());
        tarefa.setStatus(StatusTarefa.PENDENTE);

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return converterParaResponseDTO(tarefaSalva);
    }

    private TarefaResponseDTO converterParaResponseDTO(Tarefa tarefa) {
        TarefaResponseDTO response = new TarefaResponseDTO();
        response.setId(tarefa.getId());
        response.setUsuarioId(tarefa.getUsuarioId());
        response.setTitulo(tarefa.getTitulo());
        response.setDescricao(tarefa.getDescricao());
        response.setDataHoraAgendada(tarefa.getDataHoraAgendada());
        response.setDataCriacao(tarefa.getDataCriacao());
        response.setStatus(tarefa.getStatus().name());

        return response;
    }
}