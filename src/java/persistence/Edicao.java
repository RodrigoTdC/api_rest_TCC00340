/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rotos
 */
@Entity
public class Edicao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int numero;
    private int ano;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String cidade;
    @OneToOne
    private Evento evento;
    @OneToOne
    private Usuario organizador;
    
    private String chamada;
    private LocalDate limiteSubmissao;
    private LocalDate limiteDivulgacao;
    private LocalDate limiteEntrega;
    private String informacoes;
    @ManyToMany
    private List<Usuario> membros;
    

    public Edicao(int numero, int ano, LocalDate dataInicial, LocalDate dataFinal, String cidade, Evento evento) {
        this.numero = numero;
        this.ano = ano;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.cidade = cidade;
        this.evento = evento;
    }


    //Getters
    
    public Long getId() {
        return id;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Evento getEvento() {
        return evento;
    }
    
    public Usuario getOrganizador(){
        return organizador;
    }

    public String getChamada(){
        return chamada;
    }
    
    public LocalDate getLimiteSubmissao(){
        return limiteSubmissao;
    }
    
    public LocalDate getLimiteDivulgacao(){
        return limiteDivulgacao;
    }
    
    public LocalDate getLimiteEntrega(){
        return limiteEntrega;
    }
    
    public String getInformacoes(){
        return informacoes;
    }
    
    public List<Usuario> getMembros(){
        return membros;
    }
    
//Setters
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public void setChamada(String chamada) {
        this.chamada = chamada;
    }

    public void setLimiteSubmissao(LocalDate limiteSubmissao) {
        this.limiteSubmissao = limiteSubmissao;
    }

    public void setLimiteDivulgacao(LocalDate limiteDivulgacao) {
        this.limiteDivulgacao = limiteDivulgacao;
    }

    public void setLimiteEntrega(LocalDate limiteEntrega) {
        this.limiteEntrega = limiteEntrega;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Edicao)) {
            return false;
        }
        Edicao other = (Edicao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Edicao[ id=" + id + " ]";
    }
    
}
