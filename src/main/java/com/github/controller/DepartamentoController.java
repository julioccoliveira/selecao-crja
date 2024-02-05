package com.github.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.github.entity.Departamento;
import com.github.entity.Pessoa;
import com.github.entity.Tarefa;
import com.github.projection.GetDepartamentoProjection;
import com.github.service.DepartamentoService;
import com.github.service.PessoaService;
import com.github.service.TarefaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/departamentos")
public class DepartamentoController {

    @Inject
    DepartamentoService departamentoService;

    @Inject
    PessoaService pessoaService;

    @Inject
    TarefaService tarefaService;

    @GET
    public List<GetDepartamentoProjection> getDepartamento() {
        List<Departamento> departamentos = departamentoService.getAllDepartamento();
        List<Pessoa> pessoas = pessoaService.findAllPessoas();
        List<Tarefa> tarefas = tarefaService.getAllTarefas();

        List<GetDepartamentoProjection> getDepartamentoProjection = 
                departamentos
                        .stream()
                        .map(d -> new GetDepartamentoProjection() {
                            @Override
                            public String getNome() {
                                return d.getNome();
                            }

                            @Override
                            public Long getQtdTarefas() {
                                return tarefas.stream().filter(t -> t.getDepartamento() == d.getId()).count();
                            }

                            @Override
                            public Long getQtdPessoas() {
                                return pessoas.stream().filter(p -> p.getDepartamento() == d.getId()).count();
                            }
                        })
                        .collect(Collectors.toList());
        
        return getDepartamentoProjection;
    }
    
}
