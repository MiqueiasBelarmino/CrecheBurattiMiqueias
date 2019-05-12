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
import model.Ambiente;
import model.Reservaambiente;
import model.ReservaambientePK;
import model.Servidor;

/**
 *
 * @author Belarmino
 */
public class ReservaambienteJpaController implements Serializable {

    public ReservaambienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservaambiente reservaambiente) throws PreexistingEntityException, Exception {
        if (reservaambiente.getReservaambientePK() == null) {
            reservaambiente.setReservaambientePK(new ReservaambientePK());
        }
        reservaambiente.getReservaambientePK().setAmbientecodigo(reservaambiente.getAmbiente().getCodigo());
        reservaambiente.getReservaambientePK().setServidorcodigo(reservaambiente.getServidor().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ambiente ambiente = reservaambiente.getAmbiente();
            if (ambiente != null) {
                ambiente = em.getReference(ambiente.getClass(), ambiente.getCodigo());
                reservaambiente.setAmbiente(ambiente);
            }
            Servidor servidor = reservaambiente.getServidor();
            if (servidor != null) {
                servidor = em.getReference(servidor.getClass(), servidor.getCodigo());
                reservaambiente.setServidor(servidor);
            }
            em.persist(reservaambiente);
            if (ambiente != null) {
                ambiente.getReservaambienteList().add(reservaambiente);
                ambiente = em.merge(ambiente);
            }
            if (servidor != null) {
                servidor.getReservaambienteList().add(reservaambiente);
                servidor = em.merge(servidor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReservaambiente(reservaambiente.getReservaambientePK()) != null) {
                throw new PreexistingEntityException("Reservaambiente " + reservaambiente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservaambiente reservaambiente) throws NonexistentEntityException, Exception {
        reservaambiente.getReservaambientePK().setAmbientecodigo(reservaambiente.getAmbiente().getCodigo());
        reservaambiente.getReservaambientePK().setServidorcodigo(reservaambiente.getServidor().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservaambiente persistentReservaambiente = em.find(Reservaambiente.class, reservaambiente.getReservaambientePK());
            Ambiente ambienteOld = persistentReservaambiente.getAmbiente();
            Ambiente ambienteNew = reservaambiente.getAmbiente();
            Servidor servidorOld = persistentReservaambiente.getServidor();
            Servidor servidorNew = reservaambiente.getServidor();
            if (ambienteNew != null) {
                ambienteNew = em.getReference(ambienteNew.getClass(), ambienteNew.getCodigo());
                reservaambiente.setAmbiente(ambienteNew);
            }
            if (servidorNew != null) {
                servidorNew = em.getReference(servidorNew.getClass(), servidorNew.getCodigo());
                reservaambiente.setServidor(servidorNew);
            }
            reservaambiente = em.merge(reservaambiente);
            if (ambienteOld != null && !ambienteOld.equals(ambienteNew)) {
                ambienteOld.getReservaambienteList().remove(reservaambiente);
                ambienteOld = em.merge(ambienteOld);
            }
            if (ambienteNew != null && !ambienteNew.equals(ambienteOld)) {
                ambienteNew.getReservaambienteList().add(reservaambiente);
                ambienteNew = em.merge(ambienteNew);
            }
            if (servidorOld != null && !servidorOld.equals(servidorNew)) {
                servidorOld.getReservaambienteList().remove(reservaambiente);
                servidorOld = em.merge(servidorOld);
            }
            if (servidorNew != null && !servidorNew.equals(servidorOld)) {
                servidorNew.getReservaambienteList().add(reservaambiente);
                servidorNew = em.merge(servidorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ReservaambientePK id = reservaambiente.getReservaambientePK();
                if (findReservaambiente(id) == null) {
                    throw new NonexistentEntityException("The reservaambiente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ReservaambientePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservaambiente reservaambiente;
            try {
                reservaambiente = em.getReference(Reservaambiente.class, id);
                reservaambiente.getReservaambientePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservaambiente with id " + id + " no longer exists.", enfe);
            }
            Ambiente ambiente = reservaambiente.getAmbiente();
            if (ambiente != null) {
                ambiente.getReservaambienteList().remove(reservaambiente);
                ambiente = em.merge(ambiente);
            }
            Servidor servidor = reservaambiente.getServidor();
            if (servidor != null) {
                servidor.getReservaambienteList().remove(reservaambiente);
                servidor = em.merge(servidor);
            }
            em.remove(reservaambiente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservaambiente> findReservaambienteEntities() {
        return findReservaambienteEntities(true, -1, -1);
    }

    public List<Reservaambiente> findReservaambienteEntities(int maxResults, int firstResult) {
        return findReservaambienteEntities(false, maxResults, firstResult);
    }

    private List<Reservaambiente> findReservaambienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservaambiente.class));
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

    public Reservaambiente findReservaambiente(ReservaambientePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservaambiente.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaambienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservaambiente> rt = cq.from(Reservaambiente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
