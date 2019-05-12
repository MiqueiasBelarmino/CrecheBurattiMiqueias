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
@Table(name = "frequenciaservidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frequenciaservidor.findAll", query = "SELECT f FROM Frequenciaservidor f")
    , @NamedQuery(name = "Frequenciaservidor.findByCodigo", query = "SELECT f FROM Frequenciaservidor f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "Frequenciaservidor.findByData", query = "SELECT f FROM Frequenciaservidor f WHERE f.data = :data")
    , @NamedQuery(name = "Frequenciaservidor.findBySituacao", query = "SELECT f FROM Frequenciaservidor f WHERE f.situacao = :situacao")
    , @NamedQuery(name = "Frequenciaservidor.findByJustificativa", query = "SELECT f FROM Frequenciaservidor f WHERE f.justificativa = :justificativa")})
public class Frequenciaservidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "situacao")
    private Character situacao;
    @Column(name = "justificativa")
    private String justificativa;
    @JoinColumn(name = "Servidor_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Servidor servidorcodigo;

    public Frequenciaservidor() {
    }

    public Frequenciaservidor(Integer codigo) {
        this.codigo = codigo;
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

    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
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
        if (!(object instanceof Frequenciaservidor)) {
            return false;
        }
        Frequenciaservidor other = (Frequenciaservidor) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Frequenciaservidor[ codigo=" + codigo + " ]";
    }
    
}
