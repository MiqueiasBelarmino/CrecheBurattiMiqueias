/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Frequenciaservidor;
import model.Servidor;

/**
 *
 * @author vfrei
 */
public class FrequenciaservidorJpaController implements Serializable {

    public FrequenciaservidorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Frequenciaservidor frequenciaservidor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servidor servidorcodigo = frequenciaservidor.getServidorcodigo();
            if (servidorcodigo != null) {
                servidorcodigo = em.getReference(servidorcodigo.getClass(), servidorcodigo.getCodigo());
                frequenciaservidor.setServidorcodigo(servidorcodigo);
            }
            em.persist(frequenciaservidor);
            if (servidorcodigo != null) {
                servidorcodigo.getFrequenciaservidorList().add(frequenciaservidor);
                servidorcodigo = em.merge(servidorcodigo);
            }
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
            Frequenciaservidor persistentFrequenciaservidor = em.find(Frequenciaservidor.class, frequenciaservidor.getCodigo());
            Servidor servidorcodigoOld = persistentFrequenciaservidor.getServidorcodigo();
            Servidor servidorcodigoNew = frequenciaservidor.getServidorcodigo();
            if (servidorcodigoNew != null) {
                servidorcodigoNew = em.getReference(servidorcodigoNew.getClass(), servidorcodigoNew.getCodigo());
                frequenciaservidor.setServidorcodigo(servidorcodigoNew);
            }
            frequenciaservidor = em.merge(frequenciaservidor);
            if (servidorcodigoOld != null && !servidorcodigoOld.equals(servidorcodigoNew)) {
                servidorcodigoOld.getFrequenciaservidorList().remove(frequenciaservidor);
                servidorcodigoOld = em.merge(servidorcodigoOld);
            }
            if (servidorcodigoNew != null && !servidorcodigoNew.equals(servidorcodigoOld)) {
                servidorcodigoNew.getFrequenciaservidorList().add(frequenciaservidor);
                servidorcodigoNew = em.merge(servidorcodigoNew);
            }
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
            Servidor servidorcodigo = frequenciaservidor.getServidorcodigo();
            if (servidorcodigo != null) {
                servidorcodigo.getFrequenciaservidorList().remove(frequenciaservidor);
                servidorcodigo = em.merge(servidorcodigo);
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