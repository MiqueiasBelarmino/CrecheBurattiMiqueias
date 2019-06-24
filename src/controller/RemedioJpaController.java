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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Crianca;
import model.Remedio;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class RemedioJpaController implements Serializable {

    public void create(Remedio remedio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca criancacodigo = remedio.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo = em.getReference(criancacodigo.getClass(), criancacodigo.getCodigo());
                remedio.setCriancacodigo(criancacodigo);
            }
            em.persist(remedio);
            if (criancacodigo != null) {
                criancacodigo.getRemedioList().add(remedio);
                criancacodigo = em.merge(criancacodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Remedio remedio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Remedio persistentRemedio = em.find(Remedio.class, remedio.getCodigo());
            Crianca criancacodigoOld = persistentRemedio.getCriancacodigo();
            Crianca criancacodigoNew = remedio.getCriancacodigo();
            if (criancacodigoNew != null) {
                criancacodigoNew = em.getReference(criancacodigoNew.getClass(), criancacodigoNew.getCodigo());
                remedio.setCriancacodigo(criancacodigoNew);
            }
            remedio = em.merge(remedio);
            if (criancacodigoOld != null && !criancacodigoOld.equals(criancacodigoNew)) {
                criancacodigoOld.getRemedioList().remove(remedio);
                criancacodigoOld = em.merge(criancacodigoOld);
            }
            if (criancacodigoNew != null && !criancacodigoNew.equals(criancacodigoOld)) {
                criancacodigoNew.getRemedioList().add(remedio);
                criancacodigoNew = em.merge(criancacodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = remedio.getCodigo();
                if (findRemedio(id) == null) {
                    throw new NonexistentEntityException("The remedio with id " + id + " no longer exists.");
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
            Remedio remedio;
            try {
                remedio = em.getReference(Remedio.class, id);
                remedio.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The remedio with id " + id + " no longer exists.", enfe);
            }
            Crianca criancacodigo = remedio.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo.getRemedioList().remove(remedio);
                criancacodigo = em.merge(criancacodigo);
            }
            em.remove(remedio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Remedio> findRemedioEntities() {
        return findRemedioEntities(true, -1, -1);
    }

    public List<Remedio> findRemedioEntities(int maxResults, int firstResult) {
        return findRemedioEntities(false, maxResults, firstResult);
    }

    private List<Remedio> findRemedioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Remedio.class));
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

    public Remedio findRemedio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Remedio.class, id);
        } finally {
            em.close();
        }
    }

    public int getRemedioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Remedio> rt = cq.from(Remedio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
