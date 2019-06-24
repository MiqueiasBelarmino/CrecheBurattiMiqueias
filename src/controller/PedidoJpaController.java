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
import model.Servidor;
import model.Itempedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Pedido;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class PedidoJpaController implements Serializable {

    public void create(Pedido pedido) {
        if (pedido.getItempedidoList() == null) {
            pedido.setItempedidoList(new ArrayList<Itempedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servidor servidorCodigo = pedido.getServidorCodigo();
            if (servidorCodigo != null) {
                servidorCodigo = em.getReference(servidorCodigo.getClass(), servidorCodigo.getCodigo());
                pedido.setServidorCodigo(servidorCodigo);
            }
            List<Itempedido> attachedItempedidoList = new ArrayList<Itempedido>();
            for (Itempedido itempedidoListItempedidoToAttach : pedido.getItempedidoList()) {
                itempedidoListItempedidoToAttach = em.getReference(itempedidoListItempedidoToAttach.getClass(), itempedidoListItempedidoToAttach.getItempedidoPK());
                attachedItempedidoList.add(itempedidoListItempedidoToAttach);
            }
            pedido.setItempedidoList(attachedItempedidoList);
            em.persist(pedido);
            if (servidorCodigo != null) {
                servidorCodigo.getPedidoList().add(pedido);
                servidorCodigo = em.merge(servidorCodigo);
            }
            for (Itempedido itempedidoListItempedido : pedido.getItempedidoList()) {
                Pedido oldPedidoOfItempedidoListItempedido = itempedidoListItempedido.getPedido();
                itempedidoListItempedido.setPedido(pedido);
                itempedidoListItempedido = em.merge(itempedidoListItempedido);
                if (oldPedidoOfItempedidoListItempedido != null) {
                    oldPedidoOfItempedidoListItempedido.getItempedidoList().remove(itempedidoListItempedido);
                    oldPedidoOfItempedidoListItempedido = em.merge(oldPedidoOfItempedidoListItempedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getCodigo());
            Servidor servidorCodigoOld = persistentPedido.getServidorCodigo();
            Servidor servidorCodigoNew = pedido.getServidorCodigo();
            List<Itempedido> itempedidoListOld = persistentPedido.getItempedidoList();
            List<Itempedido> itempedidoListNew = pedido.getItempedidoList();
            List<String> illegalOrphanMessages = null;
            for (Itempedido itempedidoListOldItempedido : itempedidoListOld) {
                if (!itempedidoListNew.contains(itempedidoListOldItempedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Itempedido " + itempedidoListOldItempedido + " since its pedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (servidorCodigoNew != null) {
                servidorCodigoNew = em.getReference(servidorCodigoNew.getClass(), servidorCodigoNew.getCodigo());
                pedido.setServidorCodigo(servidorCodigoNew);
            }
            List<Itempedido> attachedItempedidoListNew = new ArrayList<Itempedido>();
            for (Itempedido itempedidoListNewItempedidoToAttach : itempedidoListNew) {
                itempedidoListNewItempedidoToAttach = em.getReference(itempedidoListNewItempedidoToAttach.getClass(), itempedidoListNewItempedidoToAttach.getItempedidoPK());
                attachedItempedidoListNew.add(itempedidoListNewItempedidoToAttach);
            }
            itempedidoListNew = attachedItempedidoListNew;
            pedido.setItempedidoList(itempedidoListNew);
            pedido = em.merge(pedido);
            if (servidorCodigoOld != null && !servidorCodigoOld.equals(servidorCodigoNew)) {
                servidorCodigoOld.getPedidoList().remove(pedido);
                servidorCodigoOld = em.merge(servidorCodigoOld);
            }
            if (servidorCodigoNew != null && !servidorCodigoNew.equals(servidorCodigoOld)) {
                servidorCodigoNew.getPedidoList().add(pedido);
                servidorCodigoNew = em.merge(servidorCodigoNew);
            }
            for (Itempedido itempedidoListNewItempedido : itempedidoListNew) {
                if (!itempedidoListOld.contains(itempedidoListNewItempedido)) {
                    Pedido oldPedidoOfItempedidoListNewItempedido = itempedidoListNewItempedido.getPedido();
                    itempedidoListNewItempedido.setPedido(pedido);
                    itempedidoListNewItempedido = em.merge(itempedidoListNewItempedido);
                    if (oldPedidoOfItempedidoListNewItempedido != null && !oldPedidoOfItempedidoListNewItempedido.equals(pedido)) {
                        oldPedidoOfItempedidoListNewItempedido.getItempedidoList().remove(itempedidoListNewItempedido);
                        oldPedidoOfItempedidoListNewItempedido = em.merge(oldPedidoOfItempedidoListNewItempedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getCodigo();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Itempedido> itempedidoListOrphanCheck = pedido.getItempedidoList();
            for (Itempedido itempedidoListOrphanCheckItempedido : itempedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the Itempedido " + itempedidoListOrphanCheckItempedido + " in its itempedidoList field has a non-nullable pedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Servidor servidorCodigo = pedido.getServidorCodigo();
            if (servidorCodigo != null) {
                servidorCodigo.getPedidoList().remove(pedido);
                servidorCodigo = em.merge(servidorCodigo);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
