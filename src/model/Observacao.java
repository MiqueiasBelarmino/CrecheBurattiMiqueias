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
 * @author vfrei
 */
@Entity
@Table(name = "observacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observacao.findAll", query = "SELECT o FROM Observacao o")
    , @NamedQuery(name = "Observacao.findByCodigo", query = "SELECT o FROM Observacao o WHERE o.codigo = :codigo")
    , @NamedQuery(name = "Observacao.findByDescricao", query = "SELECT o FROM Observacao o WHERE o.descricao = :descricao")
    , @NamedQuery(name = "Observacao.findByPrioridade", query = "SELECT o FROM Observacao o WHERE o.prioridade = :prioridade")})
public class Observacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "prioridade")
    private Character prioridade;
    @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Crianca criancacodigo;

    public Observacao() {
    }

    public Observacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Observacao(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
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

    public Character getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Character prioridade) {
        this.prioridade = prioridade;
    }

    public Crianca getCriancacodigo() {
        return criancacodigo;
    }

    public void setCriancacodigo(Crianca criancacodigo) {
        this.criancacodigo = criancacodigo;
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
        if (!(object instanceof Observacao)) {
            return false;
        }
        Observacao other = (Observacao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Observacao[ codigo=" + codigo + " ]";
    }
    
}
