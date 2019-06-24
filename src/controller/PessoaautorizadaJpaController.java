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
import model.Crianca;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Controleretirada;
import model.Pessoaautorizada;
import static utilities.GerenciamentoEntidades.getEntityManager;

/**
 *
 * @author Belarmino
 */
public class PessoaautorizadaJpaController implements Serializable {
    
    public List<Pessoaautorizada> findNome(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Pessoaautorizada.findByNome");
        query.setParameter("nome", str + "%");
        return query.getResultList();
    }

    public List<Pessoaautorizada> findCPF(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Pessoaautorizada.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return query.getResultList();
    }

    public Pessoaautorizada findCPFUnique(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Pessoaautorizada.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return (Pessoaautorizada) query.getSingleResult();
    }
    
    public void create(Pessoaautorizada pessoaautorizada) {
        if (pessoaautorizada.getCriancaList() == null) {
            pessoaautorizada.setCriancaList(new ArrayList<Crianca>());
        }
        if (pessoaautorizada.getControleretiradaList() == null) {
            pessoaautorizada.setControleretiradaList(new ArrayList<Controleretirada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Crianca> attachedCriancaList = new ArrayList<Crianca>();
            for (Crianca criancaListCriancaToAttach : pessoaautorizada.getCriancaList()) {
                criancaListCriancaToAttach = em.getReference(criancaListCriancaToAttach.getClass(), criancaListCriancaToAttach.getCodigo());
                attachedCriancaList.add(criancaListCriancaToAttach);
            }
            pessoaautorizada.setCriancaList(attachedCriancaList);
            List<Controleretirada> attachedControleretiradaList = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListControleretiradaToAttach : pessoaautorizada.getControleretiradaList()) {
                controleretiradaListControleretiradaToAttach = em.getReference(controleretiradaListControleretiradaToAttach.getClass(), controleretiradaListControleretiradaToAttach.getCodigo());
                attachedControleretiradaList.add(controleretiradaListControleretiradaToAttach);
            }
            pessoaautorizada.setControleretiradaList(attachedControleretiradaList);
            em.persist(pessoaautorizada);
            for (Crianca criancaListCrianca : pessoaautorizada.getCriancaList()) {
                criancaListCrianca.getPessoaautorizadaList().add(pessoaautorizada);
                criancaListCrianca = em.merge(criancaListCrianca);
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
            List<Crianca> criancaListOld = persistentPessoaautorizada.getCriancaList();
            List<Crianca> criancaListNew = pessoaautorizada.getCriancaList();
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
            List<Crianca> attachedCriancaListNew = new ArrayList<Crianca>();
            for (Crianca criancaListNewCriancaToAttach : criancaListNew) {
                criancaListNewCriancaToAttach = em.getReference(criancaListNewCriancaToAttach.getClass(), criancaListNewCriancaToAttach.getCodigo());
                attachedCriancaListNew.add(criancaListNewCriancaToAttach);
            }
            criancaListNew = attachedCriancaListNew;
            pessoaautorizada.setCriancaList(criancaListNew);
            List<Controleretirada> attachedControleretiradaListNew = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListNewControleretiradaToAttach : controleretiradaListNew) {
                controleretiradaListNewControleretiradaToAttach = em.getReference(controleretiradaListNewControleretiradaToAttach.getClass(), controleretiradaListNewControleretiradaToAttach.getCodigo());
                attachedControleretiradaListNew.add(controleretiradaListNewControleretiradaToAttach);
            }
            controleretiradaListNew = attachedControleretiradaListNew;
            pessoaautorizada.setControleretiradaList(controleretiradaListNew);
            pessoaautorizada = em.merge(pessoaautorizada);
            for (Crianca criancaListOldCrianca : criancaListOld) {
                if (!criancaListNew.contains(criancaListOldCrianca)) {
                    criancaListOldCrianca.getPessoaautorizadaList().remove(pessoaautorizada);
                    criancaListOldCrianca = em.merge(criancaListOldCrianca);
                }
            }
            for (Crianca criancaListNewCrianca : criancaListNew) {
                if (!criancaListOld.contains(criancaListNewCrianca)) {
                    criancaListNewCrianca.getPessoaautorizadaList().add(pessoaautorizada);
                    criancaListNewCrianca = em.merge(criancaListNewCrianca);
                }
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
            List<Crianca> criancaList = pessoaautorizada.getCriancaList();
            for (Crianca criancaListCrianca : criancaList) {
                criancaListCrianca.getPessoaautorizadaList().remove(pessoaautorizada);
                criancaListCrianca = em.merge(criancaListCrianca);
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
