/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Crianca_1;
import model.Controleretirada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Pessoaautorizada;

/**
 *
 * @author vfrei
 */
public class PessoaautorizadaJpaController implements Serializable {

    public PessoaautorizadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoaautorizada pessoaautorizada) {
        if (pessoaautorizada.getControleretiradaList() == null) {
            pessoaautorizada.setControleretiradaList(new ArrayList<Controleretirada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca_1 criancacodigo = pessoaautorizada.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo = em.getReference(criancacodigo.getClass(), criancacodigo.getCodigo());
                pessoaautorizada.setCriancacodigo(criancacodigo);
            }
            List<Controleretirada> attachedControleretiradaList = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListControleretiradaToAttach : pessoaautorizada.getControleretiradaList()) {
                controleretiradaListControleretiradaToAttach = em.getReference(controleretiradaListControleretiradaToAttach.getClass(), controleretiradaListControleretiradaToAttach.getCodigo());
                attachedControleretiradaList.add(controleretiradaListControleretiradaToAttach);
            }
            pessoaautorizada.setControleretiradaList(attachedControleretiradaList);
            em.persist(pessoaautorizada);
            if (criancacodigo != null) {
                criancacodigo.getPessoaautorizadaList().add(pessoaautorizada);
                criancacodigo = em.merge(criancacodigo);
            }
            for (Controleretirada controleretiradaListControleretirada : pessoaautorizada.getControleretiradaList()) {
                Pessoaautorizada oldPessoaAutorizadacodigoOfControleretiradaListControleretirada = controleretiradaListControleretirada.getPessoaAutorizadacodigo();
                controleretiradaListControleretirada.setPessoaAutorizadacodigo(pessoaautorizada);
                controleretiradaListControleretirada = em.merge(controleretiradaListControleretirada);
                if (oldPessoaAutorizadacodigoOfControleretiradaListControleretirada != null) {
                    oldPessoaAutorizadacodigoOfControleretiradaListControleretirada.getControleretiradaList().remove(controleretiradaListControleretirada);
                    oldPessoaAutorizadacodigoOfControleretiradaListControleretirada = em.merge(oldPessoaAutorizadacodigoOfControleretiradaListControleretirada);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoaautorizada pessoaautorizada) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoaautorizada persistentPessoaautorizada = em.find(Pessoaautorizada.class, pessoaautorizada.getCodigo());
            Crianca_1 criancacodigoOld = persistentPessoaautorizada.getCriancacodigo();
            Crianca_1 criancacodigoNew = pessoaautorizada.getCriancacodigo();
            List<Controleretirada> controleretiradaListOld = persistentPessoaautorizada.getControleretiradaList();
            List<Controleretirada> controleretiradaListNew = pessoaautorizada.getControleretiradaList();
            List<String> illegalOrphanMessages = null;
            for (Controleretirada controleretiradaListOldControleretirada : controleretiradaListOld) {
                if (!controleretiradaListNew.contains(controleretiradaListOldControleretirada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Controleretirada " + controleretiradaListOldControleretirada + " since its pessoaAutorizadacodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (criancacodigoNew != null) {
                criancacodigoNew = em.getReference(criancacodigoNew.getClass(), criancacodigoNew.getCodigo());
                pessoaautorizada.setCriancacodigo(criancacodigoNew);
            }
            List<Controleretirada> attachedControleretiradaListNew = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListNewControleretiradaToAttach : controleretiradaListNew) {
                controleretiradaListNewControleretiradaToAttach = em.getReference(controleretiradaListNewControleretiradaToAttach.getClass(), controleretiradaListNewControleretiradaToAttach.getCodigo());
                attachedControleretiradaListNew.add(controleretiradaListNewControleretiradaToAttach);
            }
            controleretiradaListNew = attachedControleretiradaListNew;
            pessoaautorizada.setControleretiradaList(controleretiradaListNew);
            pessoaautorizada = em.merge(pessoaautorizada);
            if (criancacodigoOld != null && !criancacodigoOld.equals(criancacodigoNew)) {
                criancacodigoOld.getPessoaautorizadaList().remove(pessoaautorizada);
                criancacodigoOld = em.merge(criancacodigoOld);
            }
            if (criancacodigoNew != null && !criancacodigoNew.equals(criancacodigoOld)) {
                criancacodigoNew.getPessoaautorizadaList().add(pessoaautorizada);
                criancacodigoNew = em.merge(criancacodigoNew);
            }
            for (Controleretirada controleretiradaListNewControleretirada : controleretiradaListNew) {
                if (!controleretiradaListOld.contains(controleretiradaListNewControleretirada)) {
                    Pessoaautorizada oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada = controleretiradaListNewControleretirada.getPessoaAutorizadacodigo();
                    controleretiradaListNewControleretirada.setPessoaAutorizadacodigo(pessoaautorizada);
                    controleretiradaListNewControleretirada = em.merge(controleretiradaListNewControleretirada);
                    if (oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada != null && !oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada.equals(pessoaautorizada)) {
                        oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada.getControleretiradaList().remove(controleretiradaListNewControleretirada);
                        oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada = em.merge(oldPessoaAutorizadacodigoOfControleretiradaListNewControleretirada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoaautorizada.getCodigo();
                if (findPessoaautorizada(id) == null) {
                    throw new NonexistentEntityException("The pessoaautorizada with id " + id + " no longer exists.");
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
            Pessoaautorizada pessoaautorizada;
            try {
                pessoaautorizada = em.getReference(Pessoaautorizada.class, id);
                pessoaautorizada.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoaautorizada with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Controleretirada> controleretiradaListOrphanCheck = pessoaautorizada.getControleretiradaList();
            for (Controleretirada controleretiradaListOrphanCheckControleretirada : controleretiradaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoaautorizada (" + pessoaautorizada + ") cannot be destroyed since the Controleretirada " + controleretiradaListOrphanCheckControleretirada + " in its controleretiradaList field has a non-nullable pessoaAutorizadacodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Crianca_1 criancacodigo = pessoaautorizada.getCriancacodigo();
            if (criancacodigo != null) {
                criancacodigo.getPessoaautorizadaList().remove(pessoaautorizada);
                criancacodigo = em.merge(criancacodigo);
            }
            em.remove(pessoaautorizada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoaautorizada> findPessoaautorizadaEntities() {
        return findPessoaautorizadaEntities(true, -1, -1);
    }

    public List<Pessoaautorizada> findPessoaautorizadaEntities(int maxResults, int firstResult) {
        return findPessoaautorizadaEntities(false, maxResults, firstResult);
    }

    private List<Pessoaautorizada> findPessoaautorizadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoaautorizada.class));
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

    public Pessoaautorizada findPessoaautorizada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoaautorizada.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaautorizadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoaautorizada> rt = cq.from(Pessoaautorizada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
