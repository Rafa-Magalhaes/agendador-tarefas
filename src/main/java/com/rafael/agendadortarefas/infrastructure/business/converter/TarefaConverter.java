package com.rafael.agendadortarefas.infrastructure.business.converter;

import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TarefaConverter {

    // ====================== DTO → Entity ======================
    public Tarefa toEntity(TarefaRequestDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setUsuarioId(dto.getUsuarioId());
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setDataHoraAgendada(dto.getDataHoraAgendada());
        // Status e dataCriacao são definidos no Service
        return tarefa;
    }

    // ====================== Entity → ResponseDTO ======================
    public TarefaResponseDTO toResponseDTO(Tarefa tarefa) {
        TarefaResponseDTO response = new TarefaResponseDTO();
        response.setId(tarefa.getId());
        response.setUsuarioId(tarefa.getUsuarioId());
        response.setTitulo(tarefa.getTitulo());
        response.setDescricao(tarefa.getDescricao());
        response.setDataHoraAgendada(tarefa.getDataHoraAgendada());
        response.setDataCriacao(tarefa.getDataCriacao());

        // Converte o enum para String
        if (tarefa.getStatus() != null) {
            response.setStatus(tarefa.getStatus().name());
        }

        return response;
    }
}