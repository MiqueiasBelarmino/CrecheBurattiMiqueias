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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Crianca;
import model.Ocorrencia;

/**
 *
 * @author Belarmino
 */
public class OcorrenciaJpaController implements Serializable {

    public OcorrenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ocorrencia ocorrencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca criancacodigo = ocorrencia.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo = em.getReference(criancacodigo.getClass(), criancacodigo.getCodigo());
                ocorrencia.setCriancacodigo(criancacodigo);
            }
            em.persist(ocorrencia);
            if (criancacodigo != null) {
                criancacodigo.getOcorrenciaList().add(ocorrencia);
                criancacodigo = em.merge(criancacodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOcorrencia(ocorrencia.getCodigo()) != null) {
                throw new PreexistingEntityException("Ocorrencia " + ocorrencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ocorrencia ocorrencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencia persistentOcorrencia = em.find(Ocorrencia.class, ocorrencia.getCodigo());
            Crianca criancacodigoOld = persistentOcorrencia.getCriancacodigo();
            Crianca criancacodigoNew = ocorrencia.getCriancacodigo();
            if (criancacodigoNew != null) {
                criancacodigoNew = em.getReference(criancacodigoNew.getClass(), criancacodigoNew.getCodigo());
                ocorrencia.setCriancacodigo(criancacodigoNew);
            }
            ocorrencia = em.merge(ocorrencia);
            if (criancacodigoOld != null && !criancacodigoOld.equals(criancacodigoNew)) {
                criancacodigoOld.getOcorrenciaList().remove(ocorrencia);
                criancacodigoOld = em.merge(criancacodigoOld);
            }
            if (criancacodigoNew != null && !criancacodigoNew.equals(criancacodigoOld)) {
                criancacodigoNew.getOcorrenciaList().add(ocorrencia);
                criancacodigoNew = em.merge(criancacodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ocorrencia.getCodigo();
                if (findOcorrencia(id) == null) {
                    throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.");
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
            Ocorrencia ocorrencia;
            try {
                ocorrencia = em.getReference(Ocorrencia.class, id);
                ocorrencia.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.", enfe);
            }
            Crianca criancacodigo = ocorrencia.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo.getOcorrenciaList().remove(ocorrencia);
                criancacodigo = em.merge(criancacodigo);
            }
            em.remove(ocorrencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ocorrencia> findOcorrenciaEntities() {
        return findOcorrenciaEntities(true, -1, -1);
    }

    public List<Ocorrencia> findOcorrenciaEntities(int maxResults, int firstResult) {
        return findOcorrenciaEntities(false, maxResults, firstResult);
    }

    private List<Ocorrencia> findOcorrenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ocorrencia.class));
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

    public Ocorrencia findOcorrencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocorrencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getOcorrenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ocorrencia> rt = cq.from(Ocorrencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
