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
import model.Crianca;
import model.Frequencia;

/**
 *
 * @author Belarmino
 */
public class FrequenciaJpaController implements Serializable {

    public FrequenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Frequencia frequencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca criancacodigo = frequencia.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo = em.getReference(criancacodigo.getClass(), criancacodigo.getCodigo());
                frequencia.setCriancacodigo(criancacodigo);
            }
            em.persist(frequencia);
            if (criancacodigo != null) {
                criancacodigo.getFrequenciaList().add(frequencia);
                criancacodigo = em.merge(criancacodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Frequencia frequencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Frequencia persistentFrequencia = em.find(Frequencia.class, frequencia.getCodigo());
            Crianca criancacodigoOld = persistentFrequencia.getCriancacodigo();
            Crianca criancacodigoNew = frequencia.getCriancacodigo();
            if (criancacodigoNew != null) {
                criancacodigoNew = em.getReference(criancacodigoNew.getClass(), criancacodigoNew.getCodigo());
                frequencia.setCriancacodigo(criancacodigoNew);
            }
            frequencia = em.merge(frequencia);
            if (criancacodigoOld != null && !criancacodigoOld.equals(criancacodigoNew)) {
                criancacodigoOld.getFrequenciaList().remove(frequencia);
                criancacodigoOld = em.merge(criancacodigoOld);
            }
            if (criancacodigoNew != null && !criancacodigoNew.equals(criancacodigoOld)) {
                criancacodigoNew.getFrequenciaList().add(frequencia);
                criancacodigoNew = em.merge(criancacodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = frequencia.getCodigo();
                if (findFrequencia(id) == null) {
                    throw new NonexistentEntityException("The frequencia with id " + id + " no longer exists.");
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
            Frequencia frequencia;
            try {
                frequencia = em.getReference(Frequencia.class, id);
                frequencia.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The frequencia with id " + id + " no longer exists.", enfe);
            }
            Crianca criancacodigo = frequencia.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo.getFrequenciaList().remove(frequencia);
                criancacodigo = em.merge(criancacodigo);
            }
            em.remove(frequencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Frequencia> findFrequenciaEntities() {
        return findFrequenciaEntities(true, -1, -1);
    }

    public List<Frequencia> findFrequenciaEntities(int maxResults, int firstResult) {
        return findFrequenciaEntities(false, maxResults, firstResult);
    }

    private List<Frequencia> findFrequenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Frequencia.class));
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

    public Frequencia findFrequencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Frequencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getFrequenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Frequencia> rt = cq.from(Frequencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
