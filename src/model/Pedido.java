/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findLast", query = "SELECT MAX(p.codigo) FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByCodigo", query = "SELECT p FROM Pedido p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pedido.findByData", query = "SELECT p FROM Pedido p WHERE p.data = :data"),
    @NamedQuery(name = "Pedido.findBySituacao", query = "SELECT p FROM Pedido p WHERE p.situacao LIKE :situacao"),
    @NamedQuery(name = "Pedido.findByAtivo", query = "SELECT p FROM Pedido p WHERE p.ativo = :ativo")})
public class Pedido implements Serializable {

    @Basic(optional = false)
    @Column(name = "ativo")
    private int ativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<Itempedido> itempedidoList;
    @JoinColumn(name = "servidor_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Servidor servidorCodigo;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "situacao")
    private String situacao;

    public Pedido() {
    }

    public Pedido(Integer codigo) {
        this.codigo = codigo;
    }

    public Pedido(Integer codigo, Date data, String situacao) {
        this.codigo = codigo;
        this.data = data;
        this.situacao = situacao;
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
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
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pedido[ codigo=" + codigo + " ]";
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    @XmlTransient
    public List<Itempedido> getItempedidoList() {
        return itempedidoList;
    }

    public void setItempedidoList(List<Itempedido> itempedidoList) {
        this.itempedidoList = itempedidoList;
    }

    public Servidor getServidorCodigo() {
        return servidorCodigo;
    }

    public void setServidorCodigo(Servidor servidorCodigo) {
        this.servidorCodigo = servidorCodigo;
    }
    
}
