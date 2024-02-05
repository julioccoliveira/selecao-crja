package com.github.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.entity.Pessoa;
import com.github.entity.Tarefa;
import com.github.service.PessoaService;
import com.github.service.TarefaService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/tarefas")
public class TarefaController {
    
    @Inject
    TarefaService tarefaService;

    @Inject
    PessoaService pessoaService;

    @GET
    public List<Tarefa> listarTarefas() {

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            tarefas = tarefaService.getAllTarefas();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    @POST
    @Transactional
    public void addTarefa(Tarefa tarefa) {
        tarefaService.addTarefa(tarefa);
    }

    @PUT
    @Path("/finalizar/{id}")
    @Transactional
    public Tarefa finishTarefa(Long id) {
        Tarefa entity = tarefaService.getTarefaById(id);
        if(entity == null) {
            throw new NotFoundException();
        }

        entity.finalizarTarefa();
        
        return entity;
    }

    @PUT
    @Path("/alocar/{id}")
    @Transactional
    public Tarefa alocarPessoa(Long id) {
        Tarefa entityTarefa = tarefaService.getTarefaById(id);
        List<Pessoa> entityPessoas = pessoaService.getPessoasByDepartamento(entityTarefa.getDepartamento());
        Comparator<Pessoa> comparadorPorQuantidadeDeTarefas = Comparator.comparingInt(pe -> pe.getListaTarefas().size());

        if(entityTarefa.getPessoaAlocada() != null || entityPessoas.isEmpty() || entityPessoas == null) {
            throw new NotAcceptableException();
        }

        entityPessoas.sort(comparadorPorQuantidadeDeTarefas.reversed());

        entityPessoas.get(0).addTarefa(id);

        pessoaService.updatePessoa(entityPessoas.get(0).getId(), entityPessoas.get(0));

        entityTarefa.setPessoaAlocada(entityPessoas.get(0).getId());

        return entityTarefa;

    }
    
    @GET
    @Path("/pendentes")
    public List<Tarefa> listPendentes() {
        List<Tarefa> tarefas = 
                tarefaService.getTarefasOrderByPrazo()
                        .stream()
                        .filter(t -> t.getPessoaAlocada() == null)
                        .collect(Collectors.toList());
        return tarefas.subList(0, Math.min(tarefas.size(), 3));
    }
}
