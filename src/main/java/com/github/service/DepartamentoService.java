package com.github.service;

import java.util.List;

import com.github.entity.Departamento;
import com.github.repository.DepartamentoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DepartamentoService {

    @Inject
    DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamento() {
        return departamentoRepository.findAll().list();
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }
    
}
