package com.rafael.agendadortarefas.infrastructure.controller;

import com.rafael.agendadortarefas.infrastructure.business.TarefaService;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(
            @RequestBody TarefaRequestDTO dto) {

        TarefaResponseDTO response = tarefaService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}