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
import javax.persistence.FetchType;
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
 * @author Belarmino
 */
@Entity
@Table(name = "ocorrencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocorrencia.findAll", query = "SELECT o FROM Ocorrencia o"),
    @NamedQuery(name = "Ocorrencia.findByCodigo", query = "SELECT o FROM Ocorrencia o WHERE o.codigo = :codigo"),
    @NamedQuery(name = "Ocorrencia.findByData", query = "SELECT o FROM Ocorrencia o WHERE o.data = :data"),
    @NamedQuery(name = "Ocorrencia.findByDescricao", query = "SELECT o FROM Ocorrencia o WHERE o.descricao = :descricao")})
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "Crianca_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Crianca criancacodigo;
    @JoinColumn(name = "Servidor_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Servidor servidorcodigo;

    public Ocorrencia() {
    }

    public Ocorrencia(Integer codigo) {
        this.codigo = codigo;
    }

    public Ocorrencia(Integer codigo, Date data) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Crianca getCriancacodigo() {
        return criancacodigo;
    }

    public void setCriancacodigo(Crianca criancacodigo) {
        this.criancacodigo = criancacodigo;
    }

    public Servidor getServidorcodigo() {
        return servidorcodigo;
    }

    public void setServidorcodigo(Servidor servidorcodigo) {
        this.servidorcodigo = servidorcodigo;
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
        if (!(object instanceof Ocorrencia)) {
            return false;
        }
        Ocorrencia other = (Ocorrencia) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ocorrencia[ codigo=" + codigo + " ]";
    }
    
}
