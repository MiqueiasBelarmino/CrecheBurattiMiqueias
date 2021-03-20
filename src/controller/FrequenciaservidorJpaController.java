/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Frequenciaservidor;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class FrequenciaservidorJpaController implements Serializable {

    public List<Frequenciaservidor> findFrequenciaByDate(Date str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Frequenciaservidor.findByData");
        query.setParameter("data", str);
        return query.getResultList();
    }
    
    public List<Frequenciaservidor> findSituacao(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Frequenciaservidor.findBySituacao");
        query.setParameter("situacao", "%" +str + "%");
        return query.getResultList();
    }
    
    public List<Frequenciaservidor> findDate(Date data) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Frequenciaservidor.findByData");
        query.setParameter("data", data);
        return query.getResultList();
    }
       public void create(Frequenciaservidor frequenciaservidor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(frequenciaservidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Frequenciaservidor frequenciaservidor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            frequenciaservidor = em.merge(frequenciaservidor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = frequenciaservidor.getCodigo();
                if (findFrequenciaservidor(id) == null) {
                    throw new NonexistentEntityException("The frequenciaservidor with id " + id + " no longer exists.");
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
            Frequenciaservidor frequenciaservidor;
            try {
                frequenciaservidor = em.getReference(Frequenciaservidor.class, id);
                frequenciaservidor.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The frequenciaservidor with id " + id + " no longer exists.", enfe);
            }
            em.remove(frequenciaservidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Frequenciaservidor> findFrequenciaservidorEntities() {
        return findFrequenciaservidorEntities(true, -1, -1);
    }

    public List<Frequenciaservidor> findFrequenciaservidorEntities(int maxResults, int firstResult) {
        return findFrequenciaservidorEntities(false, maxResults, firstResult);
    }

    private List<Frequenciaservidor> findFrequenciaservidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Frequenciaservidor.class));
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

    public Frequenciaservidor findFrequenciaservidor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Frequenciaservidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFrequenciaservidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Frequenciaservidor> rt = cq.from(Frequenciaservidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
