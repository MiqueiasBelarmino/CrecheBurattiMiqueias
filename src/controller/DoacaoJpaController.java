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
import model.Doacao;
import model.Evento;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class DoacaoJpaController implements Serializable {

    public List<Doacao> findDescricao(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Doacao.findByDescricao");
        query.setParameter("descricao", "%" + str + "%");
        return query.getResultList();
    }
    
    public void create(Doacao doacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento eventoCodigo = doacao.getEventoCodigo();
            if (eventoCodigo != null) {
                eventoCodigo = em.getReference(eventoCodigo.getClass(), eventoCodigo.getCodigo());
                doacao.setEventoCodigo(eventoCodigo);
            }
            em.persist(doacao);
            if (eventoCodigo != null) {
                eventoCodigo.getDoacaoList().add(doacao);
                eventoCodigo = em.merge(eventoCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Doacao doacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doacao persistentDoacao = em.find(Doacao.class, doacao.getCodigo());
            Evento eventoCodigoOld = persistentDoacao.getEventoCodigo();
            Evento eventoCodigoNew = doacao.getEventoCodigo();
            if (eventoCodigoNew != null) {
                eventoCodigoNew = em.getReference(eventoCodigoNew.getClass(), eventoCodigoNew.getCodigo());
                doacao.setEventoCodigo(eventoCodigoNew);
            }
            doacao = em.merge(doacao);
            if (eventoCodigoOld != null && !eventoCodigoOld.equals(eventoCodigoNew)) {
                eventoCodigoOld.getDoacaoList().remove(doacao);
                eventoCodigoOld = em.merge(eventoCodigoOld);
            }
            if (eventoCodigoNew != null && !eventoCodigoNew.equals(eventoCodigoOld)) {
                eventoCodigoNew.getDoacaoList().add(doacao);
                eventoCodigoNew = em.merge(eventoCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = doacao.getCodigo();
                if (findDoacao(id) == null) {
                    throw new NonexistentEntityException("The doacao with id " + id + " no longer exists.");
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
            Doacao doacao;
            try {
                doacao = em.getReference(Doacao.class, id);
                doacao.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The doacao with id " + id + " no longer exists.", enfe);
            }
            Evento eventoCodigo = doacao.getEventoCodigo();
            if (eventoCodigo != null) {
                eventoCodigo.getDoacaoList().remove(doacao);
                eventoCodigo = em.merge(eventoCodigo);
            }
            em.remove(doacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Doacao> findDoacaoEntities() {
        return findDoacaoEntities(true, -1, -1);
    }

    public List<Doacao> findDoacaoEntities(int maxResults, int firstResult) {
        return findDoacaoEntities(false, maxResults, firstResult);
    }

    private List<Doacao> findDoacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Doacao.class));
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

    public Doacao findDoacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Doacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getDoacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Doacao> rt = cq.from(Doacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
