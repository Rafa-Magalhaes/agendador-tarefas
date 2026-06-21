package com.rafael.agendadortarefas.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tarefas")
public class Tarefa {

    @Id
    private String id;

    private Long usuarioId;

    private String titulo;

    private String descricao;

    private LocalDateTime dataHoraAgendada;

    @CreatedDate
    private LocalDateTime dataCriacao;

    private StatusTarefa status = StatusTarefa.PENDENTE;
}