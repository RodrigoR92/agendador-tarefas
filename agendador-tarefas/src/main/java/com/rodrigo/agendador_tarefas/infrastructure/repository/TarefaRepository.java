package com.rodrigo.agendador_tarefas.infrastructure.repository;

import com.rodrigo.agendador_tarefas.infrastructure.entity.TarefaEntity;
import com.rodrigo.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends MongoRepository<TarefaEntity, String> {

    List<TarefaEntity> findByDataEventoBetweenAndStatusNotificacaoEnum(LocalDateTime dataInicial,
                                                                       LocalDateTime dataFinal,
                                                                       StatusNotificacaoEnum status);

    List<TarefaEntity> findByEmailUsuario(String email);
}
