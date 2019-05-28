/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "pessoaautorizada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoaautorizada.findAll", query = "SELECT p FROM Pessoaautorizada p"),
    @NamedQuery(name = "Pessoaautorizada.findByCodigo", query = "SELECT p FROM Pessoaautorizada p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pessoaautorizada.findByNome", query = "SELECT p FROM Pessoaautorizada p WHERE p.nome = :nome"),
    @NamedQuery(name = "Pessoaautorizada.findByCpf", query = "SELECT p FROM Pessoaautorizada p WHERE p.cpf = :cpf"),
    @NamedQuery(name = "Pessoaautorizada.findByEndereco", query = "SELECT p FROM Pessoaautorizada p WHERE p.endereco = :endereco"),
    @NamedQuery(name = "Pessoaautorizada.findByTelefone", query = "SELECT p FROM Pessoaautorizada p WHERE p.telefone = :telefone"),
    @NamedQuery(name = "Pessoaautorizada.findByEmail", query = "SELECT p FROM Pessoaautorizada p WHERE p.email = :email"),
    @NamedQuery(name = "Pessoaautorizada.findByStatusContato", query = "SELECT p FROM Pessoaautorizada p WHERE p.statusContato = :statusContato"),
    @NamedQuery(name = "Pessoaautorizada.findByCelular", query = "SELECT p FROM Pessoaautorizada p WHERE p.celular = :celular")})
public class Pessoaautorizada implements Serializable {

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
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Column(name = "statusContato")
    private Integer statusContato;
    @Column(name = "celular")
    private String celular;
    @ManyToMany(mappedBy = "pessoaautorizadaList", fetch = FetchType.LAZY)
    private List<Crianca> criancaList;

    public Pessoaautorizada() {
    }

    public Pessoaautorizada(Integer codigo) {
        this.codigo = codigo;
    }

    public Pessoaautorizada(Integer codigo, String nome, String cpf, String endereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatusContato() {
        return statusContato;
    }

    public void setStatusContato(Integer statusContato) {
        this.statusContato = statusContato;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @XmlTransient
    public List<Crianca> getCriancaList() {
        return criancaList;
    }

    public void setCriancaList(List<Crianca> criancaList) {
        this.criancaList = criancaList;
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
        if (!(object instanceof Pessoaautorizada)) {
            return false;
        }
        Pessoaautorizada other = (Pessoaautorizada) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pessoaautorizada[ codigo=" + codigo + " ]";
    }
    
}
