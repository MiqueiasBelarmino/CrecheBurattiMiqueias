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
import model.Controleretirada;

/**
 *
 * @author Belarmino
 */
public class ControleretiradaJpaController implements Serializable {

    public ControleretiradaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Controleretirada controleretirada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(controleretirada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Controleretirada controleretirada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            controleretirada = em.merge(controleretirada);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controleretirada.getCodigo();
                if (findControleretirada(id) == null) {
                    throw new NonexistentEntityException("The controleretirada with id " + id + " no longer exists.");
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
            Controleretirada controleretirada;
            try {
                controleretirada = em.getReference(Controleretirada.class, id);
                controleretirada.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controleretirada with id " + id + " no longer exists.", enfe);
            }
            em.remove(controleretirada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Controleretirada> findControleretiradaEntities() {
        return findControleretiradaEntities(true, -1, -1);
    }

    public List<Controleretirada> findControleretiradaEntities(int maxResults, int firstResult) {
        return findControleretiradaEntities(false, maxResults, firstResult);
    }

    private List<Controleretirada> findControleretiradaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Controleretirada.class));
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

    public Controleretirada findControleretirada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Controleretirada.class, id);
        } finally {
            em.close();
        }
    }

    public int getControleretiradaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Controleretirada> rt = cq.from(Controleretirada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
