package com.rafael.agendadortarefas.domain.entity;

public final class StatusTarefa {

    public static final String PENDENTE = "PENDENTE";
    public static final String ENVIADO = "ENVIADO";
    public static final String FALHOU = "FALHOU";

    public static final String FALHOU_DEFINITIVO = "FALHOU_DEFINITIVO"; // Corrigido
    public static final String CONCLUIDO = "CONCLUIDO";
    public static final String PENDENTE_POR_TEMPO = "PENDENTE_POR_TEMPO"; // Corrigido
    public static final String FALHOU_POR_TEMPO = "FALHOU_POR_TEMPO"; // Corrigido

    private StatusTarefa() {
        // Evita que a classe seja instanciada
    }
}