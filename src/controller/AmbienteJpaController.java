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
import model.Ambiente;

/**
 *
 * @author Belarmino
 */
public class AmbienteJpaController implements Serializable {

    public AmbienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ambiente ambiente) {
        if (ambiente.getReservaambienteList() == null) {
            ambiente.setReservaambienteList(new ArrayList<Reservaambiente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reservaambiente> attachedReservaambienteList = new ArrayList<Reservaambiente>();
            for (Reservaambiente reservaambienteListReservaambienteToAttach : ambiente.getReservaambienteList()) {
                reservaambienteListReservaambienteToAttach = em.getReference(reservaambienteListReservaambienteToAttach.getClass(), reservaambienteListReservaambienteToAttach.getReservaambientePK());
                attachedReservaambienteList.add(reservaambienteListReservaambienteToAttach);
            }
            ambiente.setReservaambienteList(attachedReservaambienteList);
            em.persist(ambiente);
            for (Reservaambiente reservaambienteListReservaambiente : ambiente.getReservaambienteList()) {
                Ambiente oldAmbienteOfReservaambienteListReservaambiente = reservaambienteListReservaambiente.getAmbiente();
                reservaambienteListReservaambiente.setAmbiente(ambiente);
                reservaambienteListReservaambiente = em.merge(reservaambienteListReservaambiente);
                if (oldAmbienteOfReservaambienteListReservaambiente != null) {
                    oldAmbienteOfReservaambienteListReservaambiente.getReservaambienteList().remove(reservaambienteListReservaambiente);
                    oldAmbienteOfReservaambienteListReservaambiente = em.merge(oldAmbienteOfReservaambienteListReservaambiente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ambiente ambiente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ambiente persistentAmbiente = em.find(Ambiente.class, ambiente.getCodigo());
            List<Reservaambiente> reservaambienteListOld = persistentAmbiente.getReservaambienteList();
            List<Reservaambiente> reservaambienteListNew = ambiente.getReservaambienteList();
            List<String> illegalOrphanMessages = null;
            for (Reservaambiente reservaambienteListOldReservaambiente : reservaambienteListOld) {
                if (!reservaambienteListNew.contains(reservaambienteListOldReservaambiente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservaambiente " + reservaambienteListOldReservaambiente + " since its ambiente field is not nullable.");
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
            ambiente.setReservaambienteList(reservaambienteListNew);
            ambiente = em.merge(ambiente);
            for (Reservaambiente reservaambienteListNewReservaambiente : reservaambienteListNew) {
                if (!reservaambienteListOld.contains(reservaambienteListNewReservaambiente)) {
                    Ambiente oldAmbienteOfReservaambienteListNewReservaambiente = reservaambienteListNewReservaambiente.getAmbiente();
                    reservaambienteListNewReservaambiente.setAmbiente(ambiente);
                    reservaambienteListNewReservaambiente = em.merge(reservaambienteListNewReservaambiente);
                    if (oldAmbienteOfReservaambienteListNewReservaambiente != null && !oldAmbienteOfReservaambienteListNewReservaambiente.equals(ambiente)) {
                        oldAmbienteOfReservaambienteListNewReservaambiente.getReservaambienteList().remove(reservaambienteListNewReservaambiente);
                        oldAmbienteOfReservaambienteListNewReservaambiente = em.merge(oldAmbienteOfReservaambienteListNewReservaambiente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ambiente.getCodigo();
                if (findAmbiente(id) == null) {
                    throw new NonexistentEntityException("The ambiente with id " + id + " no longer exists.");
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
            Ambiente ambiente;
            try {
                ambiente = em.getReference(Ambiente.class, id);
                ambiente.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ambiente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reservaambiente> reservaambienteListOrphanCheck = ambiente.getReservaambienteList();
            for (Reservaambiente reservaambienteListOrphanCheckReservaambiente : reservaambienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ambiente (" + ambiente + ") cannot be destroyed since the Reservaambiente " + reservaambienteListOrphanCheckReservaambiente + " in its reservaambienteList field has a non-nullable ambiente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ambiente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ambiente> findAmbienteEntities() {
        return findAmbienteEntities(true, -1, -1);
    }

    public List<Ambiente> findAmbienteEntities(int maxResults, int firstResult) {
        return findAmbienteEntities(false, maxResults, firstResult);
    }

    private List<Ambiente> findAmbienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ambiente.class));
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

    public Ambiente findAmbiente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ambiente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAmbienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ambiente> rt = cq.from(Ambiente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
