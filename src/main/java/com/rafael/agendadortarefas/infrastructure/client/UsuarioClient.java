package com.rafael.agendadortarefas.infrastructure.client;

import com.rafael.agendadortarefas.infrastructure.config.FeignClientConfig;
import com.rafael.agendadortarefas.infrastructure.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-service",
        url = "${usuario.service.url}",
        configuration = FeignClientConfig.class
)
public interface UsuarioClient {

    @GetMapping("/internal/usuarios/{id}")
    UsuarioResponseDTO buscarUsuarioPorId(@PathVariable("id") Long id);
}