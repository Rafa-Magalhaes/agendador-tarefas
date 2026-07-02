package com.rafael.agendadortarefas.infrastructure.repository;

import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import com.rafael.agendadortarefas.infrastructure.entity.StatusTarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    List<Tarefa> findByUsuarioId(Long usuarioId);

    Optional<Tarefa> findById(String id);

    List<Tarefa> findByDataHoraAgendadaBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Tarefa> findByStatus(StatusTarefa status);
}