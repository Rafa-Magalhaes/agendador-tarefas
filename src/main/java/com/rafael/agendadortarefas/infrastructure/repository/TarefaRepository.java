package com.rafael.agendadortarefas.infrastructure.repository;

<<<<<<< HEAD
import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
=======
import com.rafael.agendadortarefas.domain.entity.Tarefa;
>>>>>>> origin/develop
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

<<<<<<< HEAD
    List<Tarefa> findByUsuarioId(Long usuarioId);

    Optional<Tarefa> findById(String id);

    List<Tarefa> findByDataHoraAgendadaBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Tarefa> findByStatus(StatusTarefa status);
=======
    // ====================== DELETAR AGENDAMENTO  ======================
    Optional<Tarefa> findByIdAndUsuarioId(String id, Long usuarioId);

    // ====================== ALTERACAO DE STATUS PÓS EXPIRAÇÃO  ======================
    List<Tarefa> findByDataHoraAgendadaBeforeAndStatusIn(LocalDateTime dataHora, List<String> statusList);

    // ====================== SCHEDULER BFF: VERIFICAR PENDÊNCIAS ======================
    List<Tarefa> findByStatus(String status);

    // ====================== ATUALIZAR STATUS ============================
    Optional<Tarefa> findById(String id);

    // ====================== DELETAR AGENDAMENTOS DO USUARIO  ======================
    void deleteAllByUsuarioId(Long usuarioId);

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    List<Tarefa> findAllByUsuarioId(Long usuarioId);

    // ====================== BUSCAR POR PERÍODO ======================
    List<Tarefa> findByUsuarioIdAndDataHoraAgendadaBetween(Long usuarioId, LocalDateTime dataInicial, LocalDateTime dataFinal);
>>>>>>> origin/develop
}