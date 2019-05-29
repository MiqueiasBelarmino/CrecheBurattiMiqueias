/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Belarmino
 */
public class GeneralController {

    public boolean verificaCpf(String str, String classe) {
        Object object = null;
        EntityManager em = utilities.GerenciamentoEntidades.getEntityManager();
        Query query = em.createNamedQuery(classe + ".findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        try {
            object = query.getSingleResult();
            if (object != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
    
    /*
    public List<Crianca> findNome(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByNome");
        query.setParameter("nome", str + "%");
        return query.getResultList();
    }

    public List<Crianca> findCPF(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return query.getResultList();
    }
    
    public Pessoaautorizada findCPFUnique(String str) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Pessoaautorizada.findByCpf");
        query.setParameter("cpf", "%" + str + "%");
        return (Pessoaautorizada) query.getSingleResult();
    }
    */

    /*public boolean verificaCpf(String str) {
        Crianca crianca = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Crianca.findByCPF");
        query.setParameter("cpf", "%" + str + "%");
        crianca = (Crianca) query.getSingleResult();
        if (crianca != null) {
            return true;
        } else {
            return false;
        }
    }*/

}
