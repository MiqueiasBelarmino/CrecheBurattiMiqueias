/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "doacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doacao.findAll", query = "SELECT d FROM Doacao d"),
    @NamedQuery(name = "Doacao.findByCodigo", query = "SELECT d FROM Doacao d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Doacao.findByDescricao", query = "SELECT d FROM Doacao d WHERE d.descricao = :descricao"),
    @NamedQuery(name = "Doacao.findByQuantidade", query = "SELECT d FROM Doacao d WHERE d.quantidade = :quantidade"),
    @NamedQuery(name = "Doacao.findByUnidade", query = "SELECT d FROM Doacao d WHERE d.unidade = :unidade"),
    @NamedQuery(name = "Doacao.findByCategoria", query = "SELECT d FROM Doacao d WHERE d.categoria = :categoria")})
public class Doacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Basic(optional = false)
    @Column(name = "unidade")
    private String unidade;
    @Basic(optional = false)
    @Column(name = "categoria")
    private String categoria;
    @JoinColumn(name = "evento_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Evento eventoCodigo;
    
     @Basic(optional = false)
    @Column(name = "ativo")
    private int ativo;

    public Doacao() {
    }

    public Doacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Doacao(Integer codigo, String descricao, String unidade, String categoria) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.unidade = unidade;
        this.categoria = categoria;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Evento getEventoCodigo() {
        return eventoCodigo;
    }

    public void setEventoCodigo(Evento eventoCodigo) {
        this.eventoCodigo = eventoCodigo;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doacao)) {
            return false;
        }
        Doacao other = (Doacao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Doacao[ codigo=" + codigo + " ]";
    }
    
}
