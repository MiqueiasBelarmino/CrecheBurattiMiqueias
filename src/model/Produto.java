/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Belarmino
 */
@Entity
@Table(name = "produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByCodigo", query = "SELECT p FROM Produto p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao LIKE :descricao"),
    @NamedQuery(name = "Produto.findByEstoque", query = "SELECT p FROM Produto p WHERE p.estoque = :estoque"),
    @NamedQuery(name = "Produto.findByUnidade", query = "SELECT p FROM Produto p WHERE p.unidade = :unidade"),
    @NamedQuery(name = "Produto.findByAtivo", query = "SELECT p FROM Produto p WHERE p.ativo = :ativo")})
public class Produto implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto", fetch = FetchType.LAZY)
    private List<Itempedido> itempedidoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "estoque")
    private int estoque;
    @Basic(optional = false)
    @Column(name = "unidade")
    private String unidade;
    @Basic(optional = false)
    @Column(name = "ativo")
    private int ativo;

    public Produto() {
    }

    public Produto(Integer codigo) {
        this.codigo = codigo;
    }

    public Produto(Integer codigo, String descricao, int estoque, String unidade, int ativo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.estoque = estoque;
        this.unidade = unidade;
        this.ativo = ativo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @XmlTransient
    public List<Itempedido> getItempedidoList() {
        return itempedidoList;
    }

    public void setItempedidoList(List<Itempedido> itempedidoList) {
        this.itempedidoList = itempedidoList;
    }
    
}
