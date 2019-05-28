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
import model.Pessoaautorizada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Crianca;

/**
 *
 * @author Belarmino
 */
public class CriancaJpaController implements Serializable {
    
    public List<Crianca> findNome(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByNome");
        query.setParameter("nome", str + "%");
        return query.getResultList();
    }

    public List<Crianca> findCPF(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return query.getResultList();
    }

    public Crianca findCPFUnique(String str) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return (Crianca) query.getSingleResult();
    }

    public void create(Crianca crianca) {
        if (crianca.getPessoaautorizadaList() == null) {
            crianca.setPessoaautorizadaList(new ArrayList<Pessoaautorizada>());
        }
        EntityManager em = null;
        try {
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            List<Pessoaautorizada> attachedPessoaautorizadaList = new ArrayList<Pessoaautorizada>();
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizadaToAttach : crianca.getPessoaautorizadaList()) {
                pessoaautorizadaListPessoaautorizadaToAttach = em.getReference(pessoaautorizadaListPessoaautorizadaToAttach.getClass(), pessoaautorizadaListPessoaautorizadaToAttach.getCodigo());
                attachedPessoaautorizadaList.add(pessoaautorizadaListPessoaautorizadaToAttach);
            }
            crianca.setPessoaautorizadaList(attachedPessoaautorizadaList);
            em.persist(crianca);
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizada : crianca.getPessoaautorizadaList()) {
                pessoaautorizadaListPessoaautorizada.getCriancaList().add(crianca);
                pessoaautorizadaListPessoaautorizada = em.merge(pessoaautorizadaListPessoaautorizada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Crianca crianca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Crianca persistentCrianca = em.find(Crianca.class, crianca.getCodigo());
            List<Pessoaautorizada> pessoaautorizadaListOld = persistentCrianca.getPessoaautorizadaList();
            List<Pessoaautorizada> pessoaautorizadaListNew = crianca.getPessoaautorizadaList();
            List<Pessoaautorizada> attachedPessoaautorizadaListNew = new ArrayList<Pessoaautorizada>();
            for (Pessoaautorizada pessoaautorizadaListNewPessoaautorizadaToAttach : pessoaautorizadaListNew) {
                pessoaautorizadaListNewPessoaautorizadaToAttach = em.getReference(pessoaautorizadaListNewPessoaautorizadaToAttach.getClass(), pessoaautorizadaListNewPessoaautorizadaToAttach.getCodigo());
                attachedPessoaautorizadaListNew.add(pessoaautorizadaListNewPessoaautorizadaToAttach);
            }
            pessoaautorizadaListNew = attachedPessoaautorizadaListNew;
            crianca.setPessoaautorizadaList(pessoaautorizadaListNew);
            crianca = em.merge(crianca);
            for (Pessoaautorizada pessoaautorizadaListOldPessoaautorizada : pessoaautorizadaListOld) {
                if (!pessoaautorizadaListNew.contains(pessoaautorizadaListOldPessoaautorizada)) {
                    pessoaautorizadaListOldPessoaautorizada.getCriancaList().remove(crianca);
                    pessoaautorizadaListOldPessoaautorizada = em.merge(pessoaautorizadaListOldPessoaautorizada);
                }
            }
            for (Pessoaautorizada pessoaautorizadaListNewPessoaautorizada : pessoaautorizadaListNew) {
                if (!pessoaautorizadaListOld.contains(pessoaautorizadaListNewPessoaautorizada)) {
                    pessoaautorizadaListNewPessoaautorizada.getCriancaList().add(crianca);
                    pessoaautorizadaListNewPessoaautorizada = em.merge(pessoaautorizadaListNewPessoaautorizada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = crianca.getCodigo();
                if (findCrianca(id) == null) {
                    throw new NonexistentEntityException("The crianca with id " + id + " no longer exists.");
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
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Crianca crianca;
            try {
                crianca = em.getReference(Crianca.class, id);
                crianca.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crianca with id " + id + " no longer exists.", enfe);
            }
            List<Pessoaautorizada> pessoaautorizadaList = crianca.getPessoaautorizadaList();
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizada : pessoaautorizadaList) {
                pessoaautorizadaListPessoaautorizada.getCriancaList().remove(crianca);
                pessoaautorizadaListPessoaautorizada = em.merge(pessoaautorizadaListPessoaautorizada);
            }
            em.remove(crianca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Crianca> findCriancaEntities() {
        return findCriancaEntities(true, -1, -1);
    }

    public List<Crianca> findCriancaEntities(int maxResults, int firstResult) {
        return findCriancaEntities(false, maxResults, firstResult);
    }

    private List<Crianca> findCriancaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Crianca.class));
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

    public Crianca findCrianca(Integer id) {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            return em.find(Crianca.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriancaCount() {
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Crianca> rt = cq.from(Crianca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
