package com.rafael.agendadortarefas.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaResponseDTO {

    private String id;

    private Long usuarioId;

    private String titulo;

    private String descricao;

    private LocalDateTime dataHoraAgendada;

    private LocalDateTime dataCriacao;

    private String status;
}