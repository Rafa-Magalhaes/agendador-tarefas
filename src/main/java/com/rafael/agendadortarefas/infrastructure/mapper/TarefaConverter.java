package com.rafael.agendadortarefas.infrastructure.mapper;

import com.rafael.agendadortarefas.api.dto.AgendadorBffMailResponseDTO;
import com.rafael.agendadortarefas.api.dto.BffAgendadorAgendamentoRequestDTO;
import com.rafael.agendadortarefas.domain.entity.Tarefa;
import org.springframework.stereotype.Component;

@Component
public class TarefaConverter {

    // ====================== Entity → ResponseDTO ======================
    public AgendadorBffMailResponseDTO toResponseDTO(Tarefa tarefa) {
        return AgendadorBffMailResponseDTO.builder()
                .id(tarefa.getId())
                .usuarioId(tarefa.getUsuarioId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .dataHoraAgendada(tarefa.getDataHoraAgendada())
                .dataCriacao(tarefa.getDataCriacao())
                .status(tarefa.getStatus())
                .build();
    }

    // ====================== DTO → Entity ======================
    public Tarefa toEntity(BffAgendadorAgendamentoRequestDTO dto) {
        return Tarefa.builder()
                .usuarioId(dto.getUsuarioId())
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .dataHoraAgendada(dto.getDataHoraAgendada())
                .build();
    }

}