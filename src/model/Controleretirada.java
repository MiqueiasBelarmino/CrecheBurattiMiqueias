/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vfrei
 */
@Entity
@Table(name = "controleretirada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Controleretirada.findAll", query = "SELECT c FROM Controleretirada c")
    , @NamedQuery(name = "Controleretirada.findByCodigo", query = "SELECT c FROM Controleretirada c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Controleretirada.findByData", query = "SELECT c FROM Controleretirada c WHERE c.data = :data")
    , @NamedQuery(name = "Controleretirada.findByJustificativa", query = "SELECT c FROM Controleretirada c WHERE c.justificativa = :justificativa")})
public class Controleretirada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "justificativa")
    private String justificativa;
    @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Crianca criancacodigo;
    @JoinColumn(name = "PessoaAutorizada_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Pessoaautorizada pessoaAutorizadacodigo;

    public Controleretirada() {
    }

    public Controleretirada(Integer codigo) {
        this.codigo = codigo;
    }

    public Controleretirada(Integer codigo, Date data) {
        this.codigo = codigo;
        this.data = data;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Crianca getCriancacodigo() {
        return criancacodigo;
    }

    public void setCriancacodigo(Crianca criancacodigo) {
        this.criancacodigo = criancacodigo;
    }

    public Pessoaautorizada getPessoaAutorizadacodigo() {
        return pessoaAutorizadacodigo;
    }

    public void setPessoaAutorizadacodigo(Pessoaautorizada pessoaAutorizadacodigo) {
        this.pessoaAutorizadacodigo = pessoaAutorizadacodigo;
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
        if (!(object instanceof Controleretirada)) {
            return false;
        }
        Controleretirada other = (Controleretirada) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Controleretirada[ codigo=" + codigo + " ]";
    }
    
}
