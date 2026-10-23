package com.rodrigo.agendador_tarefas.business;

import com.rodrigo.agendador_tarefas.business.dto.TarefaDTO;
import com.rodrigo.agendador_tarefas.business.mapper.TarefaMapper;
import com.rodrigo.agendador_tarefas.business.mapper.TarefaUpdateMapper;
import com.rodrigo.agendador_tarefas.infrastructure.entity.TarefaEntity;
import com.rodrigo.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.rodrigo.agendador_tarefas.infrastructure.exception.ResourceNotFoundException;
import com.rodrigo.agendador_tarefas.infrastructure.repository.TarefaRepository;
import com.rodrigo.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateMapper tarefaUpdateMapper;

    public TarefaDTO gravarTarefa(String token, TarefaDTO tarefaDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefaDTO.setEmailUsuario(email);
        TarefaEntity entity = tarefaMapper.paraTarefaEntity(tarefaDTO);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefaAgendadaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaMapper.paraListaTarefaDTO(tarefaRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefaDTO> buscaTarefaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaMapper.paraListaTarefaDTO(tarefaRepository.findByEmailUsuario(token));
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id, e.getCause());
        }
    }

    public TarefaDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefaEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + id, e.getCause());
        }
    }

    public TarefaDTO updateTarefa(TarefaDTO dto, String id) {
        try {
            TarefaEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateMapper.updateTarefa(dto, entity);
            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + id, e.getCause());
        }
    }
}
