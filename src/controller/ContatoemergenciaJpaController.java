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
import model.Contatoemergencia;
import model.Crianca;

/**
 *
 * @author Belarmino
 */
public class ContatoemergenciaJpaController implements Serializable {

    public void create(Contatoemergencia contatoemergencia) {
        EntityManager em = null;
        try {
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Crianca criancaCodigo = contatoemergencia.getCriancaCodigo();
            if (criancaCodigo != null) {
                criancaCodigo = em.getReference(criancaCodigo.getClass(), criancaCodigo.getCodigo());
                contatoemergencia.setCriancaCodigo(criancaCodigo);
            }
            em.persist(contatoemergencia);
            if (criancaCodigo != null) {
                criancaCodigo.getContatoemergenciaList().add(contatoemergencia);
                criancaCodigo = em.merge(criancaCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contatoemergencia contatoemergencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Contatoemergencia persistentContatoemergencia = em.find(Contatoemergencia.class, contatoemergencia.getCodigo());
            Crianca criancaCodigoOld = persistentContatoemergencia.getCriancaCodigo();
            Crianca criancaCodigoNew = contatoemergencia.getCriancaCodigo();
            if (criancaCodigoNew != null) {
                criancaCodigoNew = em.getReference(criancaCodigoNew.getClass(), criancaCodigoNew.getCodigo());
                contatoemergencia.setCriancaCodigo(criancaCodigoNew);
            }
            contatoemergencia = em.merge(contatoemergencia);
            if (criancaCodigoOld != null && !criancaCodigoOld.equals(criancaCodigoNew)) {
                criancaCodigoOld.getContatoemergenciaList().remove(contatoemergencia);
                criancaCodigoOld = em.merge(criancaCodigoOld);
            }
            if (criancaCodigoNew != null && !criancaCodigoNew.equals(criancaCodigoOld)) {
                criancaCodigoNew.getContatoemergenciaList().add(contatoemergencia);
                criancaCodigoNew = em.merge(criancaCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contatoemergencia.getCodigo();
                if (findContatoemergencia(id) == null) {
                    throw new NonexistentEntityException("The contatoemergencia with id " + id + " no longer exists.");
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
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Contatoemergencia contatoemergencia;
            try {
                contatoemergencia = em.getReference(Contatoemergencia.class, id);
                contatoemergencia.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contatoemergencia with id " + id + " no longer exists.", enfe);
            }
            Crianca criancaCodigo = contatoemergencia.getCriancaCodigo();
            if (criancaCodigo != null) {
                criancaCodigo.getContatoemergenciaList().remove(contatoemergencia);
                criancaCodigo = em.merge(criancaCodigo);
            }
            em.remove(contatoemergencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contatoemergencia> findContatoemergenciaEntities() {
        return findContatoemergenciaEntities(true, -1, -1);
    }

    public List<Contatoemergencia> findContatoemergenciaEntities(int maxResults, int firstResult) {
        return findContatoemergenciaEntities(false, maxResults, firstResult);
    }

    private List<Contatoemergencia> findContatoemergenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contatoemergencia.class));
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

    public Contatoemergencia findContatoemergencia(Integer id) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            return em.find(Contatoemergencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoemergenciaCount() {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contatoemergencia> rt = cq.from(Contatoemergencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
