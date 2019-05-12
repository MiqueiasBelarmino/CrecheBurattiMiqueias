/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Controleretirada;
import model.Crianca_1;
import model.Pessoaautorizada;

/**
 *
 * @author vfrei
 */
public class ControleretiradaJpaController implements Serializable {

    public ControleretiradaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Controleretirada controleretirada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca_1 criancacodigo = controleretirada.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo = em.getReference(criancacodigo.getClass(), criancacodigo.getCodigo());
                controleretirada.setCriancacodigo(criancacodigo);
            }
            Pessoaautorizada pessoaAutorizadacodigo = controleretirada.getPessoaAutorizadacodigo();
            if (pessoaAutorizadacodigo != null) {
                pessoaAutorizadacodigo = em.getReference(pessoaAutorizadacodigo.getClass(), pessoaAutorizadacodigo.getCodigo());
                controleretirada.setPessoaAutorizadacodigo(pessoaAutorizadacodigo);
            }
            em.persist(controleretirada);
            if (criancacodigo != null) {
                criancacodigo.getControleretiradaList().add(controleretirada);
                criancacodigo = em.merge(criancacodigo);
            }
            if (pessoaAutorizadacodigo != null) {
                pessoaAutorizadacodigo.getControleretiradaList().add(controleretirada);
                pessoaAutorizadacodigo = em.merge(pessoaAutorizadacodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Controleretirada controleretirada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Controleretirada persistentControleretirada = em.find(Controleretirada.class, controleretirada.getCodigo());
            Crianca_1 criancacodigoOld = persistentControleretirada.getCriancacodigo();
            Crianca_1 criancacodigoNew = controleretirada.getCriancacodigo();
            Pessoaautorizada pessoaAutorizadacodigoOld = persistentControleretirada.getPessoaAutorizadacodigo();
            Pessoaautorizada pessoaAutorizadacodigoNew = controleretirada.getPessoaAutorizadacodigo();
            if (criancacodigoNew != null) {
                criancacodigoNew = em.getReference(criancacodigoNew.getClass(), criancacodigoNew.getCodigo());
                controleretirada.setCriancacodigo(criancacodigoNew);
            }
            if (pessoaAutorizadacodigoNew != null) {
                pessoaAutorizadacodigoNew = em.getReference(pessoaAutorizadacodigoNew.getClass(), pessoaAutorizadacodigoNew.getCodigo());
                controleretirada.setPessoaAutorizadacodigo(pessoaAutorizadacodigoNew);
            }
            controleretirada = em.merge(controleretirada);
            if (criancacodigoOld != null && !criancacodigoOld.equals(criancacodigoNew)) {
                criancacodigoOld.getControleretiradaList().remove(controleretirada);
                criancacodigoOld = em.merge(criancacodigoOld);
            }
            if (criancacodigoNew != null && !criancacodigoNew.equals(criancacodigoOld)) {
                criancacodigoNew.getControleretiradaList().add(controleretirada);
                criancacodigoNew = em.merge(criancacodigoNew);
            }
            if (pessoaAutorizadacodigoOld != null && !pessoaAutorizadacodigoOld.equals(pessoaAutorizadacodigoNew)) {
                pessoaAutorizadacodigoOld.getControleretiradaList().remove(controleretirada);
                pessoaAutorizadacodigoOld = em.merge(pessoaAutorizadacodigoOld);
            }
            if (pessoaAutorizadacodigoNew != null && !pessoaAutorizadacodigoNew.equals(pessoaAutorizadacodigoOld)) {
                pessoaAutorizadacodigoNew.getControleretiradaList().add(controleretirada);
                pessoaAutorizadacodigoNew = em.merge(pessoaAutorizadacodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controleretirada.getCodigo();
                if (findControleretirada(id) == null) {
                    throw new NonexistentEntityException("The controleretirada with id " + id + " no longer exists.");
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
            Controleretirada controleretirada;
            try {
                controleretirada = em.getReference(Controleretirada.class, id);
                controleretirada.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controleretirada with id " + id + " no longer exists.", enfe);
            }
            Crianca_1 criancacodigo = controleretirada.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo.getControleretiradaList().remove(controleretirada);
                criancacodigo = em.merge(criancacodigo);
            }
            Pessoaautorizada pessoaAutorizadacodigo = controleretirada.getPessoaAutorizadacodigo();
            if (pessoaAutorizadacodigo != null) {
                pessoaAutorizadacodigo.getControleretiradaList().remove(controleretirada);
                pessoaAutorizadacodigo = em.merge(pessoaAutorizadacodigo);
            }
            em.remove(controleretirada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Controleretirada> findControleretiradaEntities() {
        return findControleretiradaEntities(true, -1, -1);
    }

    public List<Controleretirada> findControleretiradaEntities(int maxResults, int firstResult) {
        return findControleretiradaEntities(false, maxResults, firstResult);
    }

    private List<Controleretirada> findControleretiradaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Controleretirada.class));
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

    public Controleretirada findControleretirada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Controleretirada.class, id);
        } finally {
            em.close();
        }
    }

    public int getControleretiradaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Controleretirada> rt = cq.from(Controleretirada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
