package com.rafael.agendadortarefas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BffAgendadorAgendamentoRequestDTO {

    private Long usuarioId;

    private String titulo;

    private String descricao;

    private LocalDateTime dataHoraAgendada;
}