/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "servidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servidor.findAll", query = "SELECT s FROM Servidor s"),
    @NamedQuery(name = "Servidor.findByCodigo", query = "SELECT s FROM Servidor s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Servidor.findByNome", query = "SELECT s FROM Servidor s WHERE s.nome = :nome"),
    @NamedQuery(name = "Servidor.findByCpf", query = "SELECT s FROM Servidor s WHERE s.cpf = :cpf"),
    @NamedQuery(name = "Servidor.findByTelefone", query = "SELECT s FROM Servidor s WHERE s.telefone = :telefone"),
    @NamedQuery(name = "Servidor.findByCelular", query = "SELECT s FROM Servidor s WHERE s.celular = :celular"),
    @NamedQuery(name = "Servidor.findByEmail", query = "SELECT s FROM Servidor s WHERE s.email = :email"),
    @NamedQuery(name = "Servidor.findByEndereco", query = "SELECT s FROM Servidor s WHERE s.endereco = :endereco"),
    @NamedQuery(name = "Servidor.findByNivelacesso", query = "SELECT s FROM Servidor s WHERE s.nivelacesso = :nivelacesso"),
    @NamedQuery(name = "Servidor.findBySenha", query = "SELECT s FROM Servidor s WHERE s.senha = :senha")})
public class Servidor implements Serializable {

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
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "celular")
    private String celular;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @Column(name = "nivelacesso")
    private int nivelacesso;
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servidorcodigo", fetch = FetchType.LAZY)
    private List<Ocorrencia> ocorrenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servidor", fetch = FetchType.LAZY)
    private List<Reservaambiente> reservaambienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servidorcodigo", fetch = FetchType.LAZY)
    private List<Frequenciaservidor> frequenciaservidorList;

    public Servidor() {
    }

    public Servidor(Integer codigo) {
        this.codigo = codigo;
    }

    public Servidor(Integer codigo, String nome, String cpf, String endereco, int nivelacesso) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.nivelacesso = nivelacesso;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNivelacesso() {
        return nivelacesso;
    }

    public void setNivelacesso(int nivelacesso) {
        this.nivelacesso = nivelacesso;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public List<Ocorrencia> getOcorrenciaList() {
        return ocorrenciaList;
    }

    public void setOcorrenciaList(List<Ocorrencia> ocorrenciaList) {
        this.ocorrenciaList = ocorrenciaList;
    }

    @XmlTransient
    public List<Reservaambiente> getReservaambienteList() {
        return reservaambienteList;
    }

    public void setReservaambienteList(List<Reservaambiente> reservaambienteList) {
        this.reservaambienteList = reservaambienteList;
    }

    @XmlTransient
    public List<Frequenciaservidor> getFrequenciaservidorList() {
        return frequenciaservidorList;
    }

    public void setFrequenciaservidorList(List<Frequenciaservidor> frequenciaservidorList) {
        this.frequenciaservidorList = frequenciaservidorList;
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
        if (!(object instanceof Servidor)) {
            return false;
        }
        Servidor other = (Servidor) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Servidor[ codigo=" + codigo + " ]";
    }
    
}
