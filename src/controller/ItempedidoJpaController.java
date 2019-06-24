/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Itempedido;
import model.ItempedidoPK;
import model.Pedido;
import model.Produto;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class ItempedidoJpaController implements Serializable {

    public void create(Itempedido itempedido) throws PreexistingEntityException, Exception {
        if (itempedido.getItempedidoPK() == null) {
            itempedido.setItempedidoPK(new ItempedidoPK());
        }
        itempedido.getItempedidoPK().setPedidoCodigo(itempedido.getPedido().getCodigo());
        itempedido.getItempedidoPK().setProdutoCodigo(itempedido.getProduto().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = itempedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getCodigo());
                itempedido.setPedido(pedido);
            }
            Produto produto = itempedido.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getCodigo());
                itempedido.setProduto(produto);
            }
            em.persist(itempedido);
            if (pedido != null) {
                pedido.getItempedidoList().add(itempedido);
                pedido = em.merge(pedido);
            }
            if (produto != null) {
                produto.getItempedidoList().add(itempedido);
                produto = em.merge(produto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItempedido(itempedido.getItempedidoPK()) != null) {
                throw new PreexistingEntityException("Itempedido " + itempedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itempedido itempedido) throws NonexistentEntityException, Exception {
        itempedido.getItempedidoPK().setPedidoCodigo(itempedido.getPedido().getCodigo());
        itempedido.getItempedidoPK().setProdutoCodigo(itempedido.getProduto().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itempedido persistentItempedido = em.find(Itempedido.class, itempedido.getItempedidoPK());
            Pedido pedidoOld = persistentItempedido.getPedido();
            Pedido pedidoNew = itempedido.getPedido();
            Produto produtoOld = persistentItempedido.getProduto();
            Produto produtoNew = itempedido.getProduto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getCodigo());
                itempedido.setPedido(pedidoNew);
            }
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getCodigo());
                itempedido.setProduto(produtoNew);
            }
            itempedido = em.merge(itempedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getItempedidoList().remove(itempedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getItempedidoList().add(itempedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getItempedidoList().remove(itempedido);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getItempedidoList().add(itempedido);
                produtoNew = em.merge(produtoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ItempedidoPK id = itempedido.getItempedidoPK();
                if (findItempedido(id) == null) {
                    throw new NonexistentEntityException("The itempedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItempedidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itempedido itempedido;
            try {
                itempedido = em.getReference(Itempedido.class, id);
                itempedido.getItempedidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itempedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = itempedido.getPedido();
            if (pedido != null) {
                pedido.getItempedidoList().remove(itempedido);
                pedido = em.merge(pedido);
            }
            Produto produto = itempedido.getProduto();
            if (produto != null) {
                produto.getItempedidoList().remove(itempedido);
                produto = em.merge(produto);
            }
            em.remove(itempedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itempedido> findItempedidoEntities() {
        return findItempedidoEntities(true, -1, -1);
    }

    public List<Itempedido> findItempedidoEntities(int maxResults, int firstResult) {
        return findItempedidoEntities(false, maxResults, firstResult);
    }

    private List<Itempedido> findItempedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itempedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Itempedido findItempedido(ItempedidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itempedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getItempedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itempedido> rt = cq.from(Itempedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
