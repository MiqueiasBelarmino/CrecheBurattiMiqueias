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
@Table(name = "remedio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Remedio.findAll", query = "SELECT r FROM Remedio r")
    , @NamedQuery(name = "Remedio.findByCodigo", query = "SELECT r FROM Remedio r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "Remedio.findByNome", query = "SELECT r FROM Remedio r WHERE r.nome = :nome")
    , @NamedQuery(name = "Remedio.findByPosologia", query = "SELECT r FROM Remedio r WHERE r.posologia = :posologia")
    , @NamedQuery(name = "Remedio.findByObservacao", query = "SELECT r FROM Remedio r WHERE r.observacao = :observacao")})
public class Remedio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "posologia")
    private String posologia;
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Crianca criancacodigo;

    public Remedio() {
    }

    public Remedio(Integer codigo) {
        this.codigo = codigo;
    }

    public Remedio(Integer codigo, String nome, String posologia) {
        this.codigo = codigo;
        this.nome = nome;
        this.posologia = posologia;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        if (!(object instanceof Remedio)) {
            return false;
        }
        Remedio other = (Remedio) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Remedio[ codigo=" + codigo + " ]";
    }
    
}
