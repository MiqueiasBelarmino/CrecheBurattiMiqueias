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
import model.Pessoaautorizada;
import model.Pessoaautorizadacrianca;

/**
 *
 * @author Belarmino
 */
public class PessoaautorizadacriancaJpaController implements Serializable {

    public PessoaautorizadacriancaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoaautorizadacrianca pessoaautorizadacrianca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca crianca = pessoaautorizadacrianca.getCrianca();
            if (crianca != null) {
                crianca = em.getReference(crianca.getClass(), crianca.getCodigo());
                pessoaautorizadacrianca.setCrianca(crianca);
            }
            Pessoaautorizada pessoaautorizada = pessoaautorizadacrianca.getPessoaautorizada();
            if (pessoaautorizada != null) {
                pessoaautorizada = em.getReference(pessoaautorizada.getClass(), pessoaautorizada.getCodigo());
                pessoaautorizadacrianca.setPessoaautorizada(pessoaautorizada);
            }
            em.persist(pessoaautorizadacrianca);
            if (crianca != null) {
                crianca.getPessoaautorizadacriancaList().add(pessoaautorizadacrianca);
                crianca = em.merge(crianca);
            }
            if (pessoaautorizada != null) {
                pessoaautorizada.getPessoaautorizadacriancaList().add(pessoaautorizadacrianca);
                pessoaautorizada = em.merge(pessoaautorizada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoaautorizadacrianca pessoaautorizadacrianca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoaautorizadacrianca persistentPessoaautorizadacrianca = em.find(Pessoaautorizadacrianca.class, pessoaautorizadacrianca.getCodigo());
            Crianca criancaOld = persistentPessoaautorizadacrianca.getCrianca();
            Crianca criancaNew = pessoaautorizadacrianca.getCrianca();
            Pessoaautorizada pessoaautorizadaOld = persistentPessoaautorizadacrianca.getPessoaautorizada();
            Pessoaautorizada pessoaautorizadaNew = pessoaautorizadacrianca.getPessoaautorizada();
            if (criancaNew != null) {
                criancaNew = em.getReference(criancaNew.getClass(), criancaNew.getCodigo());
                pessoaautorizadacrianca.setCrianca(criancaNew);
            }
            if (pessoaautorizadaNew != null) {
                pessoaautorizadaNew = em.getReference(pessoaautorizadaNew.getClass(), pessoaautorizadaNew.getCodigo());
                pessoaautorizadacrianca.setPessoaautorizada(pessoaautorizadaNew);
            }
            pessoaautorizadacrianca = em.merge(pessoaautorizadacrianca);
            if (criancaOld != null && !criancaOld.equals(criancaNew)) {
                criancaOld.getPessoaautorizadacriancaList().remove(pessoaautorizadacrianca);
                criancaOld = em.merge(criancaOld);
            }
            if (criancaNew != null && !criancaNew.equals(criancaOld)) {
                criancaNew.getPessoaautorizadacriancaList().add(pessoaautorizadacrianca);
                criancaNew = em.merge(criancaNew);
            }
            if (pessoaautorizadaOld != null && !pessoaautorizadaOld.equals(pessoaautorizadaNew)) {
                pessoaautorizadaOld.getPessoaautorizadacriancaList().remove(pessoaautorizadacrianca);
                pessoaautorizadaOld = em.merge(pessoaautorizadaOld);
            }
            if (pessoaautorizadaNew != null && !pessoaautorizadaNew.equals(pessoaautorizadaOld)) {
                pessoaautorizadaNew.getPessoaautorizadacriancaList().add(pessoaautorizadacrianca);
                pessoaautorizadaNew = em.merge(pessoaautorizadaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoaautorizadacrianca.getCodigo();
                if (findPessoaautorizadacrianca(id) == null) {
                    throw new NonexistentEntityException("The pessoaautorizadacrianca with id " + id + " no longer exists.");
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
            Pessoaautorizadacrianca pessoaautorizadacrianca;
            try {
                pessoaautorizadacrianca = em.getReference(Pessoaautorizadacrianca.class, id);
                pessoaautorizadacrianca.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaautorizadacrianca with id " + id + " no longer exists.", enfe);
            }
            Crianca crianca = pessoaautorizadacrianca.getCrianca();
            if (crianca != null) {
                crianca.getPessoaautorizadacriancaList().remove(pessoaautorizadacrianca);
                crianca = em.merge(crianca);
            }
            Pessoaautorizada pessoaautorizada = pessoaautorizadacrianca.getPessoaautorizada();
            if (pessoaautorizada != null) {
                pessoaautorizada.getPessoaautorizadacriancaList().remove(pessoaautorizadacrianca);
                pessoaautorizada = em.merge(pessoaautorizada);
            }
            em.remove(pessoaautorizadacrianca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoaautorizadacrianca> findPessoaautorizadacriancaEntities() {
        return findPessoaautorizadacriancaEntities(true, -1, -1);
    }

    public List<Pessoaautorizadacrianca> findPessoaautorizadacriancaEntities(int maxResults, int firstResult) {
        return findPessoaautorizadacriancaEntities(false, maxResults, firstResult);
    }

    private List<Pessoaautorizadacrianca> findPessoaautorizadacriancaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoaautorizadacrianca.class));
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

    public Pessoaautorizadacrianca findPessoaautorizadacrianca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoaautorizadacrianca.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaautorizadacriancaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoaautorizadacrianca> rt = cq.from(Pessoaautorizadacrianca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
