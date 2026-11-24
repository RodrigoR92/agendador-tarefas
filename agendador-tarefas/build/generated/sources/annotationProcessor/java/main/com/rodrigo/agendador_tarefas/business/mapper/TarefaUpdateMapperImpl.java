package com.rodrigo.agendador_tarefas.business.mapper;

import com.rodrigo.agendador_tarefas.business.dto.TarefaDTO;
import com.rodrigo.agendador_tarefas.infrastructure.entity.TarefaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-18T17:00:31-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-java-compiler-worker-9.8.1.jar, environment: Java 21.0.12.1 (Azul Systems, Inc.)"
)
@Component
public class TarefaUpdateMapperImpl implements TarefaUpdateMapper {

    @Override
    public void updateTarefa(TarefaDTO dto, TarefaEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNomeDaTarefa() != null ) {
            entity.setNomeDaTarefa( dto.getNomeDaTarefa() );
        }
        if ( dto.getDescricao() != null ) {
            entity.setDescricao( dto.getDescricao() );
        }
        if ( dto.getDataCriacao() != null ) {
            entity.setDataCriacao( dto.getDataCriacao() );
        }
        if ( dto.getDataEvento() != null ) {
            entity.setDataEvento( dto.getDataEvento() );
        }
        if ( dto.getEmailUsuario() != null ) {
            entity.setEmailUsuario( dto.getEmailUsuario() );
        }
        if ( dto.getDataAlteracao() != null ) {
            entity.setDataAlteracao( dto.getDataAlteracao() );
        }
        if ( dto.getStatusNotificacaoEnum() != null ) {
            entity.setStatusNotificacaoEnum( dto.getStatusNotificacaoEnum() );
        }
    }
}
