/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Belarmino
 */
public class GeneralController {

    public boolean verificaCpf(String str, String classe) {
        Object object = null;
        EntityManager em = utilities.GerenciamentoEntidade.getEntityManager();
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
}
