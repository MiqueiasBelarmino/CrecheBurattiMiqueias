/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "crianca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crianca.findAll", query = "SELECT c FROM Crianca c"),
    @NamedQuery(name = "Crianca.findByCodigo", query = "SELECT c FROM Crianca c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Crianca.findByNome", query = "SELECT c FROM Crianca c WHERE c.nome LIKE :nome"),
    @NamedQuery(name = "Crianca.findByCpf", query = "SELECT c FROM Crianca c WHERE c.cpf LIKE :cpf"),
    @NamedQuery(name = "Crianca.findByDataNascimento", query = "SELECT c FROM Crianca c WHERE c.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "Crianca.findByTelefone", query = "SELECT c FROM Crianca c WHERE c.telefone = :telefone"),
    @NamedQuery(name = "Crianca.findByCelular", query = "SELECT c FROM Crianca c WHERE c.celular = :celular"),
    @NamedQuery(name = "Crianca.findByResponsavel", query = "SELECT c FROM Crianca c WHERE c.responsavel = :responsavel")})
public class Crianca implements Serializable {

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
    @Column(name = "cpf")
    private String cpf;
    @Basic(optional = false)
    @Column(name = "dataNascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @Column(name = "responsavel")
    private String responsavel;
    @JoinTable(name = "pessoaautorizadacrianca", joinColumns = {
        @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "PessoaAutorizada_codigo", referencedColumnName = "codigo")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pessoaautorizada> pessoaautorizadaList;

    public Crianca() {
    }

    public Crianca(Integer codigo) {
        this.codigo = codigo;
    }

    public Crianca(Integer codigo, String nome, String cpf, Date dataNascimento, String responsavel) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.responsavel = responsavel;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @XmlTransient
    public List<Pessoaautorizada> getPessoaautorizadaList() {
        return pessoaautorizadaList;
    }

    public void setPessoaautorizadaList(List<Pessoaautorizada> pessoaautorizadaList) {
        this.pessoaautorizadaList = pessoaautorizadaList;
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
        if (!(object instanceof Crianca)) {
            return false;
        }
        Crianca other = (Crianca) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
