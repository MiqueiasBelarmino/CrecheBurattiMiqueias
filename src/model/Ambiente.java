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
@Table(name = "ambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ambiente.findAll", query = "SELECT a FROM Ambiente a"),
    @NamedQuery(name = "Ambiente.findByCodigo", query = "SELECT a FROM Ambiente a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Ambiente.findByNome", query = "SELECT a FROM Ambiente a WHERE a.nome = :nome"),
    @NamedQuery(name = "Ambiente.findByCapacidade", query = "SELECT a FROM Ambiente a WHERE a.capacidade = :capacidade")})
public class Ambiente implements Serializable {

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
    @Column(name = "capacidade")
    private int capacidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ambiente", fetch = FetchType.LAZY)
    private List<Reservaambiente> reservaambienteList;

    public Ambiente() {
    }

    public Ambiente(Integer codigo) {
        this.codigo = codigo;
    }

    public Ambiente(Integer codigo, String nome, int capacidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = capacidade;
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

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @XmlTransient
    public List<Reservaambiente> getReservaambienteList() {
        return reservaambienteList;
    }

    public void setReservaambienteList(List<Reservaambiente> reservaambienteList) {
        this.reservaambienteList = reservaambienteList;
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
        if (!(object instanceof Ambiente)) {
            return false;
        }
        Ambiente other = (Ambiente) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ambiente[ codigo=" + codigo + " ]";
    }
    
}
