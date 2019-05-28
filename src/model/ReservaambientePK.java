/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Belarmino
 */
@Embeddable
public class ReservaambientePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @Column(name = "Ambiente_codigo")
    private int ambientecodigo;
    @Basic(optional = false)
    @Column(name = "Servidor_codigo")
    private int servidorcodigo;

    public ReservaambientePK() {
    }

    public ReservaambientePK(int codigo, int ambientecodigo, int servidorcodigo) {
        this.codigo = codigo;
        this.ambientecodigo = ambientecodigo;
        this.servidorcodigo = servidorcodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAmbientecodigo() {
        return ambientecodigo;
    }

    public void setAmbientecodigo(int ambientecodigo) {
        this.ambientecodigo = ambientecodigo;
    }

    public int getServidorcodigo() {
        return servidorcodigo;
    }

    public void setServidorcodigo(int servidorcodigo) {
        this.servidorcodigo = servidorcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigo;
        hash += (int) ambientecodigo;
        hash += (int) servidorcodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaambientePK)) {
            return false;
        }
        ReservaambientePK other = (ReservaambientePK) object;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (this.ambientecodigo != other.ambientecodigo) {
            return false;
        }
        if (this.servidorcodigo != other.servidorcodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ReservaambientePK[ codigo=" + codigo + ", ambientecodigo=" + ambientecodigo + ", servidorcodigo=" + servidorcodigo + " ]";
    }
    
}
