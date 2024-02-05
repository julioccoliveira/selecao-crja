package com.github.repository;

import com.github.entity.Tarefa;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TarefaRepository implements PanacheRepository<Tarefa>{
    
}
