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
public class AgendadorBffMailResponseDTO {

    private String id;
    private Long usuarioId;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraAgendada;
    private LocalDateTime dataCriacao;
    private String status;
}