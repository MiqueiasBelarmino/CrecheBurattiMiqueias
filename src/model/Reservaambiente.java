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
 * @author Belarmino
 */
@Entity
@Table(name = "reservaambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservaambiente.findAll", query = "SELECT r FROM Reservaambiente r"),
    @NamedQuery(name = "Reservaambiente.findByAmbientecodigo", query = "SELECT r FROM Reservaambiente r WHERE r.ambiente = :ambientecodigo"),
    @NamedQuery(name = "Reservaambiente.findByServidorcodigo", query = "SELECT r FROM Reservaambiente r WHERE r.servidor = :servidorcodigo"),
    @NamedQuery(name = "Reservaambiente.findByData", query = "SELECT r FROM Reservaambiente r WHERE r.data = :data"),
    @NamedQuery(name = "Reservaambiente.findByHora", query = "SELECT r FROM Reservaambiente r WHERE r.hora = :hora")})
public class Reservaambiente implements Serializable {

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
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "Ambiente_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ambiente ambiente;
    @JoinColumn(name = "Servidor_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Servidor servidor;

    public Reservaambiente() {
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

}
