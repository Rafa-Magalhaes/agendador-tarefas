package com.rafael.agendadortarefas.infrastructure.repository;

import com.rafael.agendadortarefas.infrastructure.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    List<Tarefa> findByUsuarioId(Long usuarioId);
}