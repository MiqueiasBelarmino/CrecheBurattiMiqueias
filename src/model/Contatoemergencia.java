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
@Table(name = "contatoemergencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contatoemergencia.findAll", query = "SELECT c FROM Contatoemergencia c"),
    @NamedQuery(name = "Contatoemergencia.findByCodigo", query = "SELECT c FROM Contatoemergencia c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Contatoemergencia.findByNome", query = "SELECT c FROM Contatoemergencia c WHERE c.nome = :nome"),
    @NamedQuery(name = "Contatoemergencia.findByVinculo", query = "SELECT c FROM Contatoemergencia c WHERE c.vinculo = :vinculo"),
    @NamedQuery(name = "Contatoemergencia.findByTelefone", query = "SELECT c FROM Contatoemergencia c WHERE c.telefone = :telefone"),
    @NamedQuery(name = "Contatoemergencia.findByAtivo", query = "SELECT c FROM Contatoemergencia c WHERE c.ativo = :ativo")})
public class Contatoemergencia implements Serializable {

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
    @Column(name = "vinculo")
    private String vinculo;
    @Basic(optional = false)
    @Column(name = "telefone")
    private String telefone;
    @Basic(optional = false)
    @Column(name = "ativo")
    private Character ativo;
    @JoinColumn(name = "crianca_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Crianca criancaCodigo;

    public Contatoemergencia() {
    }

    public Contatoemergencia(Integer codigo) {
        this.codigo = codigo;
    }

    public Contatoemergencia(Integer codigo, String nome, String vinculo, String telefone, Character ativo) {
        this.codigo = codigo;
        this.nome = nome;
        this.vinculo = vinculo;
        this.telefone = telefone;
        this.ativo = ativo;
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

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
    }

    public Crianca getCriancaCodigo() {
        return criancaCodigo;
    }

    public void setCriancaCodigo(Crianca criancaCodigo) {
        this.criancaCodigo = criancaCodigo;
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
        if (!(object instanceof Contatoemergencia)) {
            return false;
        }
        Contatoemergencia other = (Contatoemergencia) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Contatoemergencia[ codigo=" + codigo + " ]";
    }
    
}
