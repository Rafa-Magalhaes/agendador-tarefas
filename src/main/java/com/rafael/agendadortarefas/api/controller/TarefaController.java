package com.rafael.agendadortarefas.api.controller;

import com.rafael.agendadortarefas.domain.service.TarefaService;
import com.rafael.agendadortarefas.api.dto.AgendadorBffMailResponseDTO;
import com.rafael.agendadortarefas.api.dto.BffAgendadorAgendamentoRequestDTO;
import com.rafael.agendadortarefas.api.dto.BffAgendadorStatusUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/internal/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    // ====================== BUSCAR TAREFA POR ID ======================
    @GetMapping("/{tarefaId}")
    public ResponseEntity<AgendadorBffMailResponseDTO> buscarTarefaPorId(
            @PathVariable("tarefaId") String tarefaId,
            @RequestParam("usuarioId") Long usuarioId) {

        AgendadorBffMailResponseDTO response = tarefaService.buscarTarefaPorId(tarefaId, usuarioId);
        return ResponseEntity.ok(response);
    }

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    @GetMapping
    public ResponseEntity<List<AgendadorBffMailResponseDTO>> listarTarefas(
            @RequestParam("usuarioId") Long usuarioId) {

        List<AgendadorBffMailResponseDTO> response = tarefaService.listarTarefas(usuarioId);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    // ====================== CRIAR AGENDAMENTO ======================
    @PostMapping
    public ResponseEntity<AgendadorBffMailResponseDTO> criarTarefa(@Valid @RequestBody BffAgendadorAgendamentoRequestDTO request) {
        AgendadorBffMailResponseDTO response = tarefaService.criarTarefa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ====================== DELETAR AGENDAMENTO ======================
    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> deletarTarefa(
            @PathVariable("tarefaId") String tarefaId,
            @RequestParam("usuarioId") Long usuarioId) {

        tarefaService.deletarTarefa(tarefaId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    // ====================== SCHEDULER BFF: VERIFICAR PENDÊNCIAS ======================
    @Operation(
            summary = "Lista tarefas com status PENDENTE",
            description = "Endpoint interno chamado pelo BFF para buscar tarefas pendentes que precisam ser notificadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas pendentes retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma tarefa pendente encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Token de serviço inválido")
    })
    @GetMapping("/status/pendentes")
    public ResponseEntity<List<AgendadorBffMailResponseDTO>> buscarTarefasPendentes() {
        List<AgendadorBffMailResponseDTO> tarefas = tarefaService.buscarTarefasPendentes();

        if (tarefas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarefas);
    }

    // ====================== ATUALIZAR STATUS ======================
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(
            @PathVariable String id,
            @RequestBody BffAgendadorStatusUpdateRequestDTO request) {

        String novoStatus = request.getStatus().toUpperCase();
        tarefaService.alterarStatus(id, novoStatus);

        return ResponseEntity.noContent().build();
    }

    // ====================== BUSCAR POR PERÍODO (BLINDADO CONTRA IDOR) ======================
    @GetMapping("/periodo")
    public ResponseEntity<List<AgendadorBffMailResponseDTO>> buscarPorPeriodo(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataFinal) {

        List<AgendadorBffMailResponseDTO> response = tarefaService.buscarPorPeriodo(usuarioId, dataInicial, dataFinal);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    // ====================== DELETAR AGENDAMENTOS DO USUARIO ======================
    @DeleteMapping("/perfil/{usuarioId}")
    public ResponseEntity<Void> deletarTarefasPorUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        tarefaService.deletarTarefaDefinitivo(usuarioId);
        return ResponseEntity.noContent().build();
    }
}