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
import model.Reservaambiente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Servidor;

/**
 *
 * @author Belarmino
 */
public class ServidorJpaController implements Serializable {

    public ServidorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servidor servidor) {
        if (servidor.getReservaambienteList() == null) {
            servidor.setReservaambienteList(new ArrayList<Reservaambiente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reservaambiente> attachedReservaambienteList = new ArrayList<Reservaambiente>();
            for (Reservaambiente reservaambienteListReservaambienteToAttach : servidor.getReservaambienteList()) {
                reservaambienteListReservaambienteToAttach = em.getReference(reservaambienteListReservaambienteToAttach.getClass(), reservaambienteListReservaambienteToAttach.getReservaambientePK());
                attachedReservaambienteList.add(reservaambienteListReservaambienteToAttach);
            }
            servidor.setReservaambienteList(attachedReservaambienteList);
            em.persist(servidor);
            for (Reservaambiente reservaambienteListReservaambiente : servidor.getReservaambienteList()) {
                Servidor oldServidorOfReservaambienteListReservaambiente = reservaambienteListReservaambiente.getServidor();
                reservaambienteListReservaambiente.setServidor(servidor);
                reservaambienteListReservaambiente = em.merge(reservaambienteListReservaambiente);
                if (oldServidorOfReservaambienteListReservaambiente != null) {
                    oldServidorOfReservaambienteListReservaambiente.getReservaambienteList().remove(reservaambienteListReservaambiente);
                    oldServidorOfReservaambienteListReservaambiente = em.merge(oldServidorOfReservaambienteListReservaambiente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servidor servidor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servidor persistentServidor = em.find(Servidor.class, servidor.getCodigo());
            List<Reservaambiente> reservaambienteListOld = persistentServidor.getReservaambienteList();
            List<Reservaambiente> reservaambienteListNew = servidor.getReservaambienteList();
            List<String> illegalOrphanMessages = null;
            for (Reservaambiente reservaambienteListOldReservaambiente : reservaambienteListOld) {
                if (!reservaambienteListNew.contains(reservaambienteListOldReservaambiente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservaambiente " + reservaambienteListOldReservaambiente + " since its servidor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Reservaambiente> attachedReservaambienteListNew = new ArrayList<Reservaambiente>();
            for (Reservaambiente reservaambienteListNewReservaambienteToAttach : reservaambienteListNew) {
                reservaambienteListNewReservaambienteToAttach = em.getReference(reservaambienteListNewReservaambienteToAttach.getClass(), reservaambienteListNewReservaambienteToAttach.getReservaambientePK());
                attachedReservaambienteListNew.add(reservaambienteListNewReservaambienteToAttach);
            }
            reservaambienteListNew = attachedReservaambienteListNew;
            servidor.setReservaambienteList(reservaambienteListNew);
            servidor = em.merge(servidor);
            for (Reservaambiente reservaambienteListNewReservaambiente : reservaambienteListNew) {
                if (!reservaambienteListOld.contains(reservaambienteListNewReservaambiente)) {
                    Servidor oldServidorOfReservaambienteListNewReservaambiente = reservaambienteListNewReservaambiente.getServidor();
                    reservaambienteListNewReservaambiente.setServidor(servidor);
                    reservaambienteListNewReservaambiente = em.merge(reservaambienteListNewReservaambiente);
                    if (oldServidorOfReservaambienteListNewReservaambiente != null && !oldServidorOfReservaambienteListNewReservaambiente.equals(servidor)) {
                        oldServidorOfReservaambienteListNewReservaambiente.getReservaambienteList().remove(reservaambienteListNewReservaambiente);
                        oldServidorOfReservaambienteListNewReservaambiente = em.merge(oldServidorOfReservaambienteListNewReservaambiente);
                    }
                }
            }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Reservaambiente> reservaambienteListOrphanCheck = servidor.getReservaambienteList();
            for (Reservaambiente reservaambienteListOrphanCheckReservaambiente : reservaambienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servidor (" + servidor + ") cannot be destroyed since the Reservaambiente " + reservaambienteListOrphanCheckReservaambiente + " in its reservaambienteList field has a non-nullable servidor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
