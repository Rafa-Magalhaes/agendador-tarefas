package com.rafael.agendadortarefas.infrastructure.mapper;

import com.rafael.agendadortarefas.api.dto.AgendadorBffMailResponseDTO;
import com.rafael.agendadortarefas.api.dto.BffAgendadorAgendamentoRequestDTO;
import com.rafael.agendadortarefas.domain.entity.Tarefa;
import org.springframework.stereotype.Component;

@Component
public class TarefaConverter {

    // ====================== Entity → ResponseDTO ======================
    public AgendadorBffMailResponseDTO toResponseDTO(Tarefa tarefa) {
        AgendadorBffMailResponseDTO response = new AgendadorBffMailResponseDTO();
        response.setId(tarefa.getId());
        response.setUsuarioId(tarefa.getUsuarioId());
        response.setTitulo(tarefa.getTitulo());
        response.setDescricao(tarefa.getDescricao());
        response.setDataHoraAgendada(tarefa.getDataHoraAgendada());
        response.setDataCriacao(tarefa.getDataCriacao());
        response.setStatus(tarefa.getStatus());

        return response;
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