package com.github.service;

import java.util.List;

import com.github.entity.Tarefa;
import com.github.repository.TarefaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TarefaService {

    @Inject
    TarefaRepository tarefaRepository;

    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll().list();
    }

    public List<Tarefa> getTarefasOrderByPrazo() {
        return tarefaRepository.list("ORDER BY prazo");
    }

    public Tarefa getTarefaById(Long id){
        return tarefaRepository.findById(id);
    }

    public void addTarefa(Tarefa tarefa) {
        tarefaRepository.persist(tarefa);
    }
   
}
