/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
@Table(name = "pessoaautorizadacrianca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoaautorizadacrianca.findAll", query = "SELECT p FROM Pessoaautorizadacrianca p"),
    @NamedQuery(name = "Pessoaautorizadacrianca.findByCodigo", query = "SELECT p FROM Pessoaautorizadacrianca p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pessoaautorizadacrianca.findByCriancacodigo", query = "SELECT p FROM Pessoaautorizadacrianca p WHERE p.crianca = :criancacodigo"),
    @NamedQuery(name = "Pessoaautorizadacrianca.findByPessoaAutorizadacodigo", query = "SELECT p FROM Pessoaautorizadacrianca p WHERE p.pessoaautorizada = :pessoaAutorizadacodigo")})
public class Pessoaautorizadacrianca implements Serializable {

        private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Crianca crianca;
    @JoinColumn(name = "PessoaAutorizada_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pessoaautorizada pessoaautorizada;

    public Pessoaautorizadacrianca() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

   
    public Crianca getCrianca() {
        return crianca;
    }

    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    public Pessoaautorizada getPessoaautorizada() {
        return pessoaautorizada;
    }

    public void setPessoaautorizada(Pessoaautorizada pessoaautorizada) {
        this.pessoaautorizada = pessoaautorizada;
    }

    
}
