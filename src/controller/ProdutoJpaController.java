/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Itempedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Produto;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class ProdutoJpaController implements Serializable {

    public void create(Produto produto) {
        if (produto.getItempedidoList() == null) {
            produto.setItempedidoList(new ArrayList<Itempedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Itempedido> attachedItempedidoList = new ArrayList<Itempedido>();
            for (Itempedido itempedidoListItempedidoToAttach : produto.getItempedidoList()) {
                itempedidoListItempedidoToAttach = em.getReference(itempedidoListItempedidoToAttach.getClass(), itempedidoListItempedidoToAttach.getItempedidoPK());
                attachedItempedidoList.add(itempedidoListItempedidoToAttach);
            }
            produto.setItempedidoList(attachedItempedidoList);
            em.persist(produto);
            for (Itempedido itempedidoListItempedido : produto.getItempedidoList()) {
                Produto oldProdutoOfItempedidoListItempedido = itempedidoListItempedido.getProduto();
                itempedidoListItempedido.setProduto(produto);
                itempedidoListItempedido = em.merge(itempedidoListItempedido);
                if (oldProdutoOfItempedidoListItempedido != null) {
                    oldProdutoOfItempedidoListItempedido.getItempedidoList().remove(itempedidoListItempedido);
                    oldProdutoOfItempedidoListItempedido = em.merge(oldProdutoOfItempedidoListItempedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getCodigo());
            List<Itempedido> itempedidoListOld = persistentProduto.getItempedidoList();
            List<Itempedido> itempedidoListNew = produto.getItempedidoList();
            List<String> illegalOrphanMessages = null;
            for (Itempedido itempedidoListOldItempedido : itempedidoListOld) {
                if (!itempedidoListNew.contains(itempedidoListOldItempedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Itempedido " + itempedidoListOldItempedido + " since its produto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Itempedido> attachedItempedidoListNew = new ArrayList<Itempedido>();
            for (Itempedido itempedidoListNewItempedidoToAttach : itempedidoListNew) {
                itempedidoListNewItempedidoToAttach = em.getReference(itempedidoListNewItempedidoToAttach.getClass(), itempedidoListNewItempedidoToAttach.getItempedidoPK());
                attachedItempedidoListNew.add(itempedidoListNewItempedidoToAttach);
            }
            itempedidoListNew = attachedItempedidoListNew;
            produto.setItempedidoList(itempedidoListNew);
            produto = em.merge(produto);
            for (Itempedido itempedidoListNewItempedido : itempedidoListNew) {
                if (!itempedidoListOld.contains(itempedidoListNewItempedido)) {
                    Produto oldProdutoOfItempedidoListNewItempedido = itempedidoListNewItempedido.getProduto();
                    itempedidoListNewItempedido.setProduto(produto);
                    itempedidoListNewItempedido = em.merge(itempedidoListNewItempedido);
                    if (oldProdutoOfItempedidoListNewItempedido != null && !oldProdutoOfItempedidoListNewItempedido.equals(produto)) {
                        oldProdutoOfItempedidoListNewItempedido.getItempedidoList().remove(itempedidoListNewItempedido);
                        oldProdutoOfItempedidoListNewItempedido = em.merge(oldProdutoOfItempedidoListNewItempedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getCodigo();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Itempedido> itempedidoListOrphanCheck = produto.getItempedidoList();
            for (Itempedido itempedidoListOrphanCheckItempedido : itempedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Itempedido " + itempedidoListOrphanCheckItempedido + " in its itempedidoList field has a non-nullable produto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
