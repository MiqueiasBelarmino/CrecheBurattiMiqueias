/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javax.persistence.EntityManagerFactory;
import model.Pessoaautorizada;

/**
 *
 * @author Belarmino
 */
public class PessoaautorizadaJpaController implements Serializable {

    public PessoaautorizadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
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
            em.persist(pessoaautorizada);
            for (Crianca criancaListCrianca : pessoaautorizada.getCriancaList()) {
                criancaListCrianca.getPessoaautorizadaList().add(pessoaautorizada);
                criancaListCrianca = em.merge(criancaListCrianca);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoaautorizada pessoaautorizada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoaautorizada persistentPessoaautorizada = em.find(Pessoaautorizada.class, pessoaautorizada.getCodigo());
            List<Crianca> criancaListOld = persistentPessoaautorizada.getCriancaList();
            List<Crianca> criancaListNew = pessoaautorizada.getCriancaList();
            List<Crianca> attachedCriancaListNew = new ArrayList<Crianca>();
            for (Crianca criancaListNewCriancaToAttach : criancaListNew) {
                criancaListNewCriancaToAttach = em.getReference(criancaListNewCriancaToAttach.getClass(), criancaListNewCriancaToAttach.getCodigo());
                attachedCriancaListNew.add(criancaListNewCriancaToAttach);
            }
            criancaListNew = attachedCriancaListNew;
            pessoaautorizada.setCriancaList(criancaListNew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
