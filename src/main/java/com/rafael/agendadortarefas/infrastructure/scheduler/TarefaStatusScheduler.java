package com.rafael.agendadortarefas.infrastructure.scheduler;

import com.rafael.agendadortarefas.domain.entity.StatusTarefa;
import com.rafael.agendadortarefas.domain.entity.Tarefa;
import com.rafael.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.rafael.agendadortarefas.domain.entity.StatusTarefa.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class TarefaStatusScheduler {

    private final TarefaRepository tarefaRepository;

    // ====================== ALTERACAO DE STATUS PÓS EXPIRAÇÃO  ======================
    @Scheduled(cron = "0 * * * * *")
    public void processarTarefasExpiradas() {
        log.info("=== [Agendador] Verificando tarefas expiradas ===");

        LocalDateTime limite = LocalDateTime.now().minusMinutes(1);

        List<Tarefa> tarefasExpiradas = tarefaRepository
                .findByDataHoraAgendadaBeforeAndStatusIn(limite, List.of(PENDENTE, ENVIADO, FALHOU));

        for (Tarefa tarefa : tarefasExpiradas) {

            switch (tarefa.getStatus()) {
                case ENVIADO:
                    tarefa.setStatus(CONCLUIDO);
                    log.info("Tarefa {} marcada como CONCLUIDO.", tarefa.getId());
                    break;

                case PENDENTE:
                    tarefa.setStatus(PENDENTE_POR_TEMPO);
                    log.info("Tarefa {} marcada como PENDENTE_POR_TEMPO.", tarefa.getId());
                    break;

                case FALHOU:
                    tarefa.setStatus(FALHOU_POR_TEMPO);
                    log.info("Tarefa {} marcada como FALHOU_POR_TEMPO.", tarefa.getId());
                    break;

                default:
                    // Ignora caso algum outro status caia aqui por engano
                    break;
            }

            tarefaRepository.save(tarefa);
        }

        log.info("=== [Agendador] Finalizada a verificação de tarefas expiradas ===");
    }

    @Value("${tarefa.max-tentativas:5}")
    private int maxTentativas;

    @Scheduled(cron = "0 */5 * * * *")
    public void reprocessarTarefasFalhas() {
        log.info("=== [Agendador] Iniciando reprocessamento de tarefas FALHOU ===");

        List<Tarefa> tarefasFalhas = tarefaRepository.findByStatus(FALHOU);

        for (Tarefa tarefa : tarefasFalhas) {

            if (tarefa.getTentativas() < maxTentativas) {
                tarefa.setStatus(StatusTarefa.PENDENTE);
                tarefa.setTentativas(tarefa.getTentativas() + 1);

                tarefaRepository.save(tarefa);

                log.info("Tarefa {} voltou para PENDENTE (tentativa {}/{})",
                        tarefa.getId(), tarefa.getTentativas(), maxTentativas);

            } else {
                tarefa.setStatus(StatusTarefa.FALHOU_DEFINITIVO);
                tarefaRepository.save(tarefa);

                log.warn("Tarefa {} atingiu o limite máximo de tentativas ({}). Status alterado para FALHOU_DEFINITIVO.",
                        tarefa.getId(), maxTentativas);
            }
        }

        log.info("=== [Agendador] Finalizado reprocessamento de tarefas FALHOU ===");
    }

}