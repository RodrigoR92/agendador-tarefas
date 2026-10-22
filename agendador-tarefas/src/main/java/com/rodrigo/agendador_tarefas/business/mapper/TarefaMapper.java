package com.rodrigo.agendador_tarefas.business.mapper;

import com.rodrigo.agendador_tarefas.business.dto.TarefaDTO;
import com.rodrigo.agendador_tarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaEntity paraTarefaEntity(TarefaDTO tarefaDTO);

    TarefaDTO paraTarefaDTO(TarefaEntity tarefaEntity);
}
