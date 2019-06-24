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
import javax.persistence.FetchType;
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
@Table(name = "reservaambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservaambiente.findAll", query = "SELECT r FROM Reservaambiente r"),
    @NamedQuery(name = "Reservaambiente.findByCodigo", query = "SELECT r FROM Reservaambiente r WHERE r.reservaambientePK.codigo = :codigo"),
    @NamedQuery(name = "Reservaambiente.findByAmbientecodigo", query = "SELECT r FROM Reservaambiente r WHERE r.reservaambientePK.ambientecodigo = :ambientecodigo"),
    @NamedQuery(name = "Reservaambiente.findByServidorcodigo", query = "SELECT r FROM Reservaambiente r WHERE r.reservaambientePK.servidorcodigo = :servidorcodigo"),
    @NamedQuery(name = "Reservaambiente.findByData", query = "SELECT r FROM Reservaambiente r WHERE r.data = :data"),
    @NamedQuery(name = "Reservaambiente.findByHora", query = "SELECT r FROM Reservaambiente r WHERE r.hora = :hora"),
    @NamedQuery(name = "Reservaambiente.findByAtivo", query = "SELECT r FROM Reservaambiente r WHERE r.ativo = :ativo")})
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
    @Basic(optional = false)
    @Column(name = "ativo")
    private Character ativo;
    @JoinColumn(name = "Ambiente_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ambiente ambiente;
    @JoinColumn(name = "Servidor_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Servidor servidor;

    public Reservaambiente() {
    }

    public Reservaambiente(ReservaambientePK reservaambientePK) {
        this.reservaambientePK = reservaambientePK;
    }

    public Reservaambiente(ReservaambientePK reservaambientePK, Date data, Character ativo) {
        this.reservaambientePK = reservaambientePK;
        this.data = data;
        this.ativo = ativo;
    }

    public Reservaambiente(int codigo, int ambientecodigo, int servidorcodigo) {
        this.reservaambientePK = new ReservaambientePK(codigo, ambientecodigo, servidorcodigo);
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

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
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
        return "model.Reservaambiente[ reservaambientePK=" + reservaambientePK + " ]";
    }
    
}
