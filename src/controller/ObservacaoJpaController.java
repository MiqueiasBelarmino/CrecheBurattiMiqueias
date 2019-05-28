/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Observacao;

/**
 *
 * @author Belarmino
 */
public class ObservacaoJpaController implements Serializable {

    public ObservacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Observacao observacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(observacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Observacao observacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            observacao = em.merge(observacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = observacao.getCodigo();
                if (findObservacao(id) == null) {
                    throw new NonexistentEntityException("The observacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Observacao observacao;
            try {
                observacao = em.getReference(Observacao.class, id);
                observacao.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The observacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(observacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Observacao> findObservacaoEntities() {
        return findObservacaoEntities(true, -1, -1);
    }

    public List<Observacao> findObservacaoEntities(int maxResults, int firstResult) {
        return findObservacaoEntities(false, maxResults, firstResult);
    }

    private List<Observacao> findObservacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Observacao.class));
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

    public Observacao findObservacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Observacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getObservacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Observacao> rt = cq.from(Observacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
