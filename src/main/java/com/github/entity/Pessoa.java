package com.github.entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Pessoa {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nome;
    
    private int departamento;
   
    private ArrayList<Long> listaTarefas;

    public Pessoa() {}
    
    public Pessoa(  Long id,
                    String nome,
                    int departamento,
                    ArrayList<Long> listaTarefas) {

        this.id = id;
        this.nome = nome;
        this.departamento = departamento;
        this.listaTarefas = listaTarefas;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public ArrayList<Long> getListaTarefas() {
        return this.listaTarefas;
    }

    public void setListaTarefas(ArrayList<Long> listaTarefas) {
		this.listaTarefas = listaTarefas;
	}

    public void addTarefa(Long id) {
        this.listaTarefas.add(id);
    }
}
