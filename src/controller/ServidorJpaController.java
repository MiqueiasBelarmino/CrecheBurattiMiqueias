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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Servidor;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class ServidorJpaController implements Serializable {

    public Servidor validaLogin(String login, String senha) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        TypedQuery<Servidor> query = em.createQuery("SELECT s FROM Servidor s WHERE s.cpf = :scpf AND s.senha =:ssenha", Servidor.class);
        query.setParameter("scpf", login);
        query.setParameter("ssenha", senha);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Servidor> findNome(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Servidor.findByNome");
        query.setParameter("nome", str + "%");
        return query.getResultList();
    }

    public List<Servidor> findCPF(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Servidor.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return query.getResultList();
    }

    public Servidor findCPFUnique(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Servidor.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return (Servidor) query.getSingleResult();
    }
    
    public void create(Servidor servidor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(servidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servidor servidor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            servidor = em.merge(servidor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servidor.getCodigo();
                if (findServidor(id) == null) {
                    throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.");
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
            Servidor servidor;
            try {
                servidor = em.getReference(Servidor.class, id);
                servidor.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.", enfe);
            }
            em.remove(servidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servidor> findServidorEntities() {
        return findServidorEntities(true, -1, -1);
    }

    public List<Servidor> findServidorEntities(int maxResults, int firstResult) {
        return findServidorEntities(false, maxResults, firstResult);
    }

    private List<Servidor> findServidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servidor.class));
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

    public Servidor findServidor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getServidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servidor> rt = cq.from(Servidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
