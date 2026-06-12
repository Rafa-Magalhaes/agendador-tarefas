package com.rafael.agendadortarefas.infrastructure.controller;

import com.rafael.agendadortarefas.infrastructure.business.TarefaService;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaRequestDTO;
import com.rafael.agendadortarefas.infrastructure.dto.TarefaResponseDTO;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
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
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaRequestDTO dto) {
        TarefaResponseDTO response = tarefaService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorUsuarioId(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(tarefaService.buscarPorUsuarioId(usuarioId));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataFinal) {
        return ResponseEntity.ok(tarefaService.buscarPorPeriodo(dataInicial, dataFinal));
    }

    // ==================== NOVOS MÉTODOS ====================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable String id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @PathVariable String id,
            @RequestBody TarefaRequestDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TarefaResponseDTO> alterarStatus(
            @PathVariable String id,
            @RequestBody StatusTarefa status) {           // ← Agora vem no corpo da requisição
        return ResponseEntity.ok(tarefaService.alterarStatus(id, status));
    }
}