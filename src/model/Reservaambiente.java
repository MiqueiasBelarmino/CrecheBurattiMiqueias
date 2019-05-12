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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "reservaambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservaambiente.findAll", query = "SELECT r FROM Reservaambiente r")
    , @NamedQuery(name = "Reservaambiente.findByAmbientecodigo", query = "SELECT r FROM Reservaambiente r WHERE r.reservaambientePK.ambientecodigo = :ambientecodigo")
    , @NamedQuery(name = "Reservaambiente.findByServidorcodigo", query = "SELECT r FROM Reservaambiente r WHERE r.reservaambientePK.servidorcodigo = :servidorcodigo")
    , @NamedQuery(name = "Reservaambiente.findByData", query = "SELECT r FROM Reservaambiente r WHERE r.data = :data")
    , @NamedQuery(name = "Reservaambiente.findByHora", query = "SELECT r FROM Reservaambiente r WHERE r.hora = :hora")})
public class Reservaambiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservaambientePK reservaambientePK;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "Ambiente_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ambiente ambiente;
    @JoinColumn(name = "Servidor_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servidor servidor;

    public Reservaambiente() {
    }

    public Reservaambiente(ReservaambientePK reservaambientePK) {
        this.reservaambientePK = reservaambientePK;
    }

    public Reservaambiente(ReservaambientePK reservaambientePK, Date data) {
        this.reservaambientePK = reservaambientePK;
        this.data = data;
    }

    public Reservaambiente(int ambientecodigo, int servidorcodigo) {
        this.reservaambientePK = new ReservaambientePK(ambientecodigo, servidorcodigo);
    }

    public ReservaambientePK getReservaambientePK() {
        return reservaambientePK;
    }

    public void setReservaambientePK(ReservaambientePK reservaambientePK) {
        this.reservaambientePK = reservaambientePK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservaambientePK != null ? reservaambientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservaambiente)) {
            return false;
        }
        Reservaambiente other = (Reservaambiente) object;
        if ((this.reservaambientePK == null && other.reservaambientePK != null) || (this.reservaambientePK != null && !this.reservaambientePK.equals(other.reservaambientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Reservaambiente[ reservaambientePK=" + reservaambientePK + " ]";
    }
    
}
