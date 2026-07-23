package com.rafael.agendadortarefas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffAgendadorStatusUpdateRequestDTO {
    private String status;
}