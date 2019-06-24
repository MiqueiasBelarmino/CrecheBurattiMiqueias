/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "itempedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itempedido.findAll", query = "SELECT i FROM Itempedido i"),
    @NamedQuery(name = "Itempedido.findByPedidoCodigo", query = "SELECT i FROM Itempedido i WHERE i.itempedidoPK.pedidoCodigo = :pedidoCodigo"),
    @NamedQuery(name = "Itempedido.findByProdutoCodigo", query = "SELECT i FROM Itempedido i WHERE i.itempedidoPK.produtoCodigo = :produtoCodigo"),
    @NamedQuery(name = "Itempedido.findByQuantidade", query = "SELECT i FROM Itempedido i WHERE i.quantidade = :quantidade")})
public class Itempedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItempedidoPK itempedidoPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @JoinColumn(name = "pedido_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pedido pedido;
    @JoinColumn(name = "produto_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;

    public Itempedido() {
    }

    public Itempedido(ItempedidoPK itempedidoPK) {
        this.itempedidoPK = itempedidoPK;
    }

    public Itempedido(ItempedidoPK itempedidoPK, int quantidade) {
        this.itempedidoPK = itempedidoPK;
        this.quantidade = quantidade;
    }

    public Itempedido(int pedidoCodigo, int produtoCodigo) {
        this.itempedidoPK = new ItempedidoPK(pedidoCodigo, produtoCodigo);
    }

    public ItempedidoPK getItempedidoPK() {
        return itempedidoPK;
    }

    public void setItempedidoPK(ItempedidoPK itempedidoPK) {
        this.itempedidoPK = itempedidoPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itempedidoPK != null ? itempedidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itempedido)) {
            return false;
        }
        Itempedido other = (Itempedido) object;
        if ((this.itempedidoPK == null && other.itempedidoPK != null) || (this.itempedidoPK != null && !this.itempedidoPK.equals(other.itempedidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Itempedido[ itempedidoPK=" + itempedidoPK + " ]";
    }
    
}
