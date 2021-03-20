/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Doacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Crianca;
import model.Evento;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class EventoJpaController implements Serializable {

    public List<Evento> findNome(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Evento.findByNome");
        query.setParameter("nome", "%" + str + "%");
        return query.getResultList();
    }
    
     public List<Evento> findAtivo(int ativo) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Evento.findByAtivo");
        query.setParameter("ativo",ativo);
        return query.getResultList();
    }
    
    
    public void create(Evento evento) {
        if (evento.getDoacaoList() == null) {
            evento.setDoacaoList(new ArrayList<Doacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Doacao> attachedDoacaoList = new ArrayList<Doacao>();
            for (Doacao doacaoListDoacaoToAttach : evento.getDoacaoList()) {
                doacaoListDoacaoToAttach = em.getReference(doacaoListDoacaoToAttach.getClass(), doacaoListDoacaoToAttach.getCodigo());
                attachedDoacaoList.add(doacaoListDoacaoToAttach);
            }
            evento.setDoacaoList(attachedDoacaoList);
            em.persist(evento);
            for (Doacao doacaoListDoacao : evento.getDoacaoList()) {
                Evento oldEventoCodigoOfDoacaoListDoacao = doacaoListDoacao.getEventoCodigo();
                doacaoListDoacao.setEventoCodigo(evento);
                doacaoListDoacao = em.merge(doacaoListDoacao);
                if (oldEventoCodigoOfDoacaoListDoacao != null) {
                    oldEventoCodigoOfDoacaoListDoacao.getDoacaoList().remove(doacaoListDoacao);
                    oldEventoCodigoOfDoacaoListDoacao = em.merge(oldEventoCodigoOfDoacaoListDoacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evento evento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento persistentEvento = em.find(Evento.class, evento.getCodigo());
            List<Doacao> doacaoListOld = persistentEvento.getDoacaoList();
            List<Doacao> doacaoListNew = evento.getDoacaoList();
            List<String> illegalOrphanMessages = null;
            for (Doacao doacaoListOldDoacao : doacaoListOld) {
                if (!doacaoListNew.contains(doacaoListOldDoacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Doacao " + doacaoListOldDoacao + " since its eventoCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Doacao> attachedDoacaoListNew = new ArrayList<Doacao>();
            for (Doacao doacaoListNewDoacaoToAttach : doacaoListNew) {
                doacaoListNewDoacaoToAttach = em.getReference(doacaoListNewDoacaoToAttach.getClass(), doacaoListNewDoacaoToAttach.getCodigo());
                attachedDoacaoListNew.add(doacaoListNewDoacaoToAttach);
            }
            doacaoListNew = attachedDoacaoListNew;
            evento.setDoacaoList(doacaoListNew);
            evento = em.merge(evento);
            for (Doacao doacaoListNewDoacao : doacaoListNew) {
                if (!doacaoListOld.contains(doacaoListNewDoacao)) {
                    Evento oldEventoCodigoOfDoacaoListNewDoacao = doacaoListNewDoacao.getEventoCodigo();
                    doacaoListNewDoacao.setEventoCodigo(evento);
                    doacaoListNewDoacao = em.merge(doacaoListNewDoacao);
                    if (oldEventoCodigoOfDoacaoListNewDoacao != null && !oldEventoCodigoOfDoacaoListNewDoacao.equals(evento)) {
                        oldEventoCodigoOfDoacaoListNewDoacao.getDoacaoList().remove(doacaoListNewDoacao);
                        oldEventoCodigoOfDoacaoListNewDoacao = em.merge(oldEventoCodigoOfDoacaoListNewDoacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evento.getCodigo();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Doacao> doacaoListOrphanCheck = evento.getDoacaoList();
            for (Doacao doacaoListOrphanCheckDoacao : doacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evento (" + evento + ") cannot be destroyed since the Doacao " + doacaoListOrphanCheckDoacao + " in its doacaoList field has a non-nullable eventoCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evento.class));
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

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evento> rt = cq.from(Evento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
