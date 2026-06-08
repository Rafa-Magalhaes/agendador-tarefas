package com.rafael.agendadortarefas.infrastructure.controller;

import com.rafael.agendadortarefas.infrastructure.business.TarefaService;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    // ==================== MÉTODOS GET ====================

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable String id) {
        TarefaResponseDTO tarefa = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorUsuarioId(
            @RequestParam("usuarioId") Long usuarioId) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorPeriodo(
            @RequestParam("dataInicial")
            @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataInicial,

            @RequestParam("dataFinal")
            @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataFinal) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodo(dataInicial, dataFinal);
        return ResponseEntity.ok(tarefas);
    }
}