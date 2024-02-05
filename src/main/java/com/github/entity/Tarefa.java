package com.github.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tarefa {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String titulo;
    
    private String descricao;
    
    private Date prazo;

    private int departamento;
    
    private Long duracao;
    
    private Long pessoaAlocada;
    
    private Boolean finalizado;

    public Tarefa(  Long id, 
                    String titulo, 
                    Date prazo,
                    int departamento, 
                    Long duracao, 
                    Long pessoaAlocada, 
                    Boolean finalizado) {
        this.id = id;
        this.titulo = titulo;
        this.prazo = prazo;
        this.departamento = departamento;
        this.duracao = duracao;
        this.pessoaAlocada = pessoaAlocada;
        this.finalizado = finalizado;
    }
    public Tarefa() {}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getPrazo() {
        return this.prazo;
    }
    
    public int getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public Long getDuracao() {
        return this.duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public Long getPessoaAlocada() {
        return this.pessoaAlocada;
    }

    public void setPessoaAlocada(Long pessoaAlocada) {
        this.pessoaAlocada = pessoaAlocada;
    }

    public Boolean getFinalizado() {
        return this.finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void finalizarTarefa() {
        this.setFinalizado(true);
    }
}
