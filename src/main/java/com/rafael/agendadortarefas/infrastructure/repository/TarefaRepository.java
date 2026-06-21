package com.rafael.agendadortarefas.infrastructure.repository;

import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    // Busca todas as tarefas de um usuário específico
    List<Tarefa> findByUsuarioId(Long usuarioId);

    // Busca uma tarefa pelo ID (já vem do MongoRepository, mas podemos deixar explícito)
    Optional<Tarefa> findById(String id);

    // Busca tarefas entre duas datas (para todos os usuários)
    List<Tarefa> findByDataHoraAgendadaBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);
}