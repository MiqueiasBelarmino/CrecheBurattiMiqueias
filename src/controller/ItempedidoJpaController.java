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
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class ItempedidoJpaController implements Serializable {
    
    public List<Itempedido> findAll(int codigo) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Itempedido.findByPedidoCodigo");
        query.setParameter("pedidoCodigo", codigo);
        return query.getResultList();
    }

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
            em.persist(itempedido);
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
            itempedido = em.merge(itempedido);
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
