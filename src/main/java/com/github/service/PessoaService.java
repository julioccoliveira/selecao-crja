package com.github.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.entity.Pessoa;
import com.github.projection.PessoaProjection;
import com.github.repository.PessoaRepository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PessoaService extends PanacheEntity {
    
    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    TarefaService tarefaService;

    public List<Pessoa> findAllPessoas() {
        return pessoaRepository.findAll().list();
    }

    public Pessoa findPessoaById(Long id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> getPessoasByDepartamento(int departamento) {
        return pessoaRepository.find("departamento", departamento).list();
    }

    public List<Pessoa> getPessoasByNomeLike(String nome) {
        return pessoaRepository.find("nome like ?1", "%" + nome + "%").list();
    }

    public void addPessoa(Pessoa pessoa) {
        pessoaRepository.persist(pessoa);
    }

    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
       return pessoa; 
    }

    public void deletePessoa(Long id) {
        pessoaRepository.delete(pessoaRepository.findById(id));
    }

    public List<PessoaProjection> buscarIdENome() {
        List<Pessoa> pessoas = findAllPessoas();
        List<PessoaProjection> pessoaProjection;

        pessoaProjection = 
            pessoas.stream()
                .map(p -> new PessoaProjection() {
                    @Override
                    public int getDepartamento() {
                        return p.getDepartamento();
                    }

                    @Override
                    public String getNome() {
                        return p.getNome();
                    }
                    
                    @Override
                    public Long getTempoGasto() {
                        return sumTempoTarefa(p);
                    }
                })
                .collect(Collectors.toList());
        return pessoaProjection;
    }
    
    private Long sumTempoTarefa(Pessoa pessoa) {
        ArrayList<Long> duracoes = new ArrayList<>();
        Long sum = 0L;
        pessoa.getListaTarefas().forEach(t -> duracoes.add(tarefaService.getTarefaById(t).getDuracao()));
        for(Long duracao : duracoes) {
            sum += duracao;
        }
        return sum;

    }
}
