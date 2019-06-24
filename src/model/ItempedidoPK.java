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
public class ItempedidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "pedido_codigo")
    private int pedidoCodigo;
    @Basic(optional = false)
    @Column(name = "produto_codigo")
    private int produtoCodigo;

    public ItempedidoPK() {
    }

    public ItempedidoPK(int pedidoCodigo, int produtoCodigo) {
        this.pedidoCodigo = pedidoCodigo;
        this.produtoCodigo = produtoCodigo;
    }

    public int getPedidoCodigo() {
        return pedidoCodigo;
    }

    public void setPedidoCodigo(int pedidoCodigo) {
        this.pedidoCodigo = pedidoCodigo;
    }

    public int getProdutoCodigo() {
        return produtoCodigo;
    }

    public void setProdutoCodigo(int produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pedidoCodigo;
        hash += (int) produtoCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItempedidoPK)) {
            return false;
        }
        ItempedidoPK other = (ItempedidoPK) object;
        if (this.pedidoCodigo != other.pedidoCodigo) {
            return false;
        }
        if (this.produtoCodigo != other.produtoCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ItempedidoPK[ pedidoCodigo=" + pedidoCodigo + ", produtoCodigo=" + produtoCodigo + " ]";
    }
    
}
