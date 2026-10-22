package com.rodrigo.agendador_tarefas.business;

import com.rodrigo.agendador_tarefas.business.dto.TarefaDTO;
import com.rodrigo.agendador_tarefas.business.mapper.TarefaMapper;
import com.rodrigo.agendador_tarefas.infrastructure.entity.TarefaEntity;
import com.rodrigo.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.rodrigo.agendador_tarefas.infrastructure.repository.TarefaRepository;
import com.rodrigo.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(String token, TarefaDTO tarefaDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefaDTO.setEmailUsuario(email);
        TarefaEntity entity = tarefaMapper.paraTarefaEntity(tarefaDTO);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }
}
