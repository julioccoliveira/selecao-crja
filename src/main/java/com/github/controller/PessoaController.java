package com.github.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.github.entity.Pessoa;
import com.github.entity.Tarefa;
import com.github.projection.PessoaGastoProjection;
import com.github.projection.PessoaProjection;
import com.github.service.PessoaService;
import com.github.service.TarefaService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/pessoas")
public class PessoaController {
    
    @Inject
    PessoaService pessoaService;

    @Inject
    TarefaService tarefaService;
    
    @GET
    public List<PessoaProjection> listPessoas() {
        List<PessoaProjection> pessoaProjection = new ArrayList<>();

        try {
            pessoaProjection = pessoaService.buscarIdENome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pessoaProjection;
    }

    @POST
    @Transactional
    public void addPessoa(Pessoa pessoa) {
        pessoaService.addPessoa(pessoa);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
        Pessoa entity = pessoaService.findPessoaById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        
        entity.setNome(pessoa.getNome());
        entity.setDepartamento(pessoa.getDepartamento());
        entity.setListaTarefas(pessoa.getListaTarefas());

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePessoa(Long id) {
        Pessoa entity = pessoaService.findPessoaById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        pessoaService.deletePessoa(id);
    }

    @GET
    @Path("/dep/{departamento}")
    public List<Pessoa> getPessoaDep(int departamento) {
        return pessoaService.getPessoasByDepartamento(departamento);
    }

    @GET
    @Path("/gastos")
    public List<PessoaGastoProjection> getPessoasGastos(
            @QueryParam("nome") String nome, 
            @QueryParam("tempoInicial") String tempoInicial,
            @QueryParam("tempoFinal") String tempoFinal) throws ParseException {

        List<Pessoa> entityPessoas = pessoaService.getPessoasByNomeLike(nome);
        List<Tarefa> entityTarefas = tarefaService.getAllTarefas();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date dataInicial = simpleDateFormat.parse(tempoInicial);
        Date dataFinal = simpleDateFormat.parse(tempoFinal);


        List<PessoaGastoProjection> pessoaGastoProjections;

        pessoaGastoProjections = entityPessoas
                .stream()
                .map(p ->  new PessoaGastoProjection() {
                                    @Override
                                    public String getNome() {
                                        return p.getNome();
                                    }

                                    @Override
                                    public double getMediaTempoGasto() {
                                        List<Tarefa> tarefaPorPessoa = entityTarefas
                                                .stream()
                                                .filter(t -> t.getPrazo().before(dataInicial) && t.getPrazo().before(dataFinal))
                                                .filter(t -> t.getPessoaAlocada() != null)
                                                .filter(t -> t.getPessoaAlocada().equals(p.getId()))
                                                .collect(Collectors.toList());

                                        double tempo = 0;
                                        for (Tarefa t : tarefaPorPessoa) {
                                            tempo += t.getDuracao();
                                        }
                                        return tempo / p.getListaTarefas().size();
                                    }
                            })
                .collect(Collectors.toList());
        
        return pessoaGastoProjections;
    }

}
