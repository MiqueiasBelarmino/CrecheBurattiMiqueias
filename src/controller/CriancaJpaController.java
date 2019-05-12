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
import model.Remedio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Observacao;
import model.Ocorrencia;
import model.Frequencia;
import model.Pessoaautorizada;
import model.Controleretirada;
import model.Crianca;

/**
 *
 * @author vfrei
 */
public class CriancaJpaController implements Serializable {

    public CriancaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Crianca crianca_1) {
        if (crianca_1.getRemedioList() == null) {
            crianca_1.setRemedioList(new ArrayList<Remedio>());
        }
        if (crianca_1.getObservacaoList() == null) {
            crianca_1.setObservacaoList(new ArrayList<Observacao>());
        }
        if (crianca_1.getOcorrenciaList() == null) {
            crianca_1.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        if (crianca_1.getFrequenciaList() == null) {
            crianca_1.setFrequenciaList(new ArrayList<Frequencia>());
        }
        if (crianca_1.getPessoaautorizadaList() == null) {
            crianca_1.setPessoaautorizadaList(new ArrayList<Pessoaautorizada>());
        }
        if (crianca_1.getControleretiradaList() == null) {
            crianca_1.setControleretiradaList(new ArrayList<Controleretirada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Remedio> attachedRemedioList = new ArrayList<Remedio>();
            for (Remedio remedioListRemedioToAttach : crianca_1.getRemedioList()) {
                remedioListRemedioToAttach = em.getReference(remedioListRemedioToAttach.getClass(), remedioListRemedioToAttach.getCodigo());
                attachedRemedioList.add(remedioListRemedioToAttach);
            }
            crianca_1.setRemedioList(attachedRemedioList);
            List<Observacao> attachedObservacaoList = new ArrayList<Observacao>();
            for (Observacao observacaoListObservacaoToAttach : crianca_1.getObservacaoList()) {
                observacaoListObservacaoToAttach = em.getReference(observacaoListObservacaoToAttach.getClass(), observacaoListObservacaoToAttach.getCodigo());
                attachedObservacaoList.add(observacaoListObservacaoToAttach);
            }
            crianca_1.setObservacaoList(attachedObservacaoList);
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : crianca_1.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getCodigo());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            crianca_1.setOcorrenciaList(attachedOcorrenciaList);
            List<Frequencia> attachedFrequenciaList = new ArrayList<Frequencia>();
            for (Frequencia frequenciaListFrequenciaToAttach : crianca_1.getFrequenciaList()) {
                frequenciaListFrequenciaToAttach = em.getReference(frequenciaListFrequenciaToAttach.getClass(), frequenciaListFrequenciaToAttach.getCodigo());
                attachedFrequenciaList.add(frequenciaListFrequenciaToAttach);
            }
            crianca_1.setFrequenciaList(attachedFrequenciaList);
            List<Pessoaautorizada> attachedPessoaautorizadaList = new ArrayList<Pessoaautorizada>();
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizadaToAttach : crianca_1.getPessoaautorizadaList()) {
                pessoaautorizadaListPessoaautorizadaToAttach = em.getReference(pessoaautorizadaListPessoaautorizadaToAttach.getClass(), pessoaautorizadaListPessoaautorizadaToAttach.getCodigo());
                attachedPessoaautorizadaList.add(pessoaautorizadaListPessoaautorizadaToAttach);
            }
            crianca_1.setPessoaautorizadaList(attachedPessoaautorizadaList);
            List<Controleretirada> attachedControleretiradaList = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListControleretiradaToAttach : crianca_1.getControleretiradaList()) {
                controleretiradaListControleretiradaToAttach = em.getReference(controleretiradaListControleretiradaToAttach.getClass(), controleretiradaListControleretiradaToAttach.getCodigo());
                attachedControleretiradaList.add(controleretiradaListControleretiradaToAttach);
            }
            crianca_1.setControleretiradaList(attachedControleretiradaList);
            em.persist(crianca_1);
            for (Remedio remedioListRemedio : crianca_1.getRemedioList()) {
                Crianca oldCriancacodigoOfRemedioListRemedio = remedioListRemedio.getCriancacodigo();
                remedioListRemedio.setCriancacodigo(crianca_1);
                remedioListRemedio = em.merge(remedioListRemedio);
                if (oldCriancacodigoOfRemedioListRemedio != null) {
                    oldCriancacodigoOfRemedioListRemedio.getRemedioList().remove(remedioListRemedio);
                    oldCriancacodigoOfRemedioListRemedio = em.merge(oldCriancacodigoOfRemedioListRemedio);
                }
            }
            for (Observacao observacaoListObservacao : crianca_1.getObservacaoList()) {
                Crianca oldCriancacodigoOfObservacaoListObservacao = observacaoListObservacao.getCriancacodigo();
                observacaoListObservacao.setCriancacodigo(crianca_1);
                observacaoListObservacao = em.merge(observacaoListObservacao);
                if (oldCriancacodigoOfObservacaoListObservacao != null) {
                    oldCriancacodigoOfObservacaoListObservacao.getObservacaoList().remove(observacaoListObservacao);
                    oldCriancacodigoOfObservacaoListObservacao = em.merge(oldCriancacodigoOfObservacaoListObservacao);
                }
            }
            for (Ocorrencia ocorrenciaListOcorrencia : crianca_1.getOcorrenciaList()) {
                Crianca oldCriancacodigoOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getCriancacodigo();
                ocorrenciaListOcorrencia.setCriancacodigo(crianca_1);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldCriancacodigoOfOcorrenciaListOcorrencia != null) {
                    oldCriancacodigoOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldCriancacodigoOfOcorrenciaListOcorrencia = em.merge(oldCriancacodigoOfOcorrenciaListOcorrencia);
                }
            }
            for (Frequencia frequenciaListFrequencia : crianca_1.getFrequenciaList()) {
                Crianca oldCriancacodigoOfFrequenciaListFrequencia = frequenciaListFrequencia.getCriancacodigo();
                frequenciaListFrequencia.setCriancacodigo(crianca_1);
                frequenciaListFrequencia = em.merge(frequenciaListFrequencia);
                if (oldCriancacodigoOfFrequenciaListFrequencia != null) {
                    oldCriancacodigoOfFrequenciaListFrequencia.getFrequenciaList().remove(frequenciaListFrequencia);
                    oldCriancacodigoOfFrequenciaListFrequencia = em.merge(oldCriancacodigoOfFrequenciaListFrequencia);
                }
            }
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizada : crianca_1.getPessoaautorizadaList()) {
                Crianca oldCriancacodigoOfPessoaautorizadaListPessoaautorizada = pessoaautorizadaListPessoaautorizada.getCriancacodigo();
                pessoaautorizadaListPessoaautorizada.setCriancacodigo(crianca_1);
                pessoaautorizadaListPessoaautorizada = em.merge(pessoaautorizadaListPessoaautorizada);
                if (oldCriancacodigoOfPessoaautorizadaListPessoaautorizada != null) {
                    oldCriancacodigoOfPessoaautorizadaListPessoaautorizada.getPessoaautorizadaList().remove(pessoaautorizadaListPessoaautorizada);
                    oldCriancacodigoOfPessoaautorizadaListPessoaautorizada = em.merge(oldCriancacodigoOfPessoaautorizadaListPessoaautorizada);
                }
            }
            for (Controleretirada controleretiradaListControleretirada : crianca_1.getControleretiradaList()) {
                Crianca oldCriancacodigoOfControleretiradaListControleretirada = controleretiradaListControleretirada.getCriancacodigo();
                controleretiradaListControleretirada.setCriancacodigo(crianca_1);
                controleretiradaListControleretirada = em.merge(controleretiradaListControleretirada);
                if (oldCriancacodigoOfControleretiradaListControleretirada != null) {
                    oldCriancacodigoOfControleretiradaListControleretirada.getControleretiradaList().remove(controleretiradaListControleretirada);
                    oldCriancacodigoOfControleretiradaListControleretirada = em.merge(oldCriancacodigoOfControleretiradaListControleretirada);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Crianca crianca_1) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crianca persistentCrianca_1 = em.find(Crianca.class, crianca_1.getCodigo());
            List<Remedio> remedioListOld = persistentCrianca_1.getRemedioList();
            List<Remedio> remedioListNew = crianca_1.getRemedioList();
            List<Observacao> observacaoListOld = persistentCrianca_1.getObservacaoList();
            List<Observacao> observacaoListNew = crianca_1.getObservacaoList();
            List<Ocorrencia> ocorrenciaListOld = persistentCrianca_1.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = crianca_1.getOcorrenciaList();
            List<Frequencia> frequenciaListOld = persistentCrianca_1.getFrequenciaList();
            List<Frequencia> frequenciaListNew = crianca_1.getFrequenciaList();
            List<Pessoaautorizada> pessoaautorizadaListOld = persistentCrianca_1.getPessoaautorizadaList();
            List<Pessoaautorizada> pessoaautorizadaListNew = crianca_1.getPessoaautorizadaList();
            List<Controleretirada> controleretiradaListOld = persistentCrianca_1.getControleretiradaList();
            List<Controleretirada> controleretiradaListNew = crianca_1.getControleretiradaList();
            List<String> illegalOrphanMessages = null;
            for (Remedio remedioListOldRemedio : remedioListOld) {
                if (!remedioListNew.contains(remedioListOldRemedio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Remedio " + remedioListOldRemedio + " since its criancacodigo field is not nullable.");
                }
            }
            for (Observacao observacaoListOldObservacao : observacaoListOld) {
                if (!observacaoListNew.contains(observacaoListOldObservacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Observacao " + observacaoListOldObservacao + " since its criancacodigo field is not nullable.");
                }
            }
            for (Ocorrencia ocorrenciaListOldOcorrencia : ocorrenciaListOld) {
                if (!ocorrenciaListNew.contains(ocorrenciaListOldOcorrencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ocorrencia " + ocorrenciaListOldOcorrencia + " since its criancacodigo field is not nullable.");
                }
            }
            for (Frequencia frequenciaListOldFrequencia : frequenciaListOld) {
                if (!frequenciaListNew.contains(frequenciaListOldFrequencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Frequencia " + frequenciaListOldFrequencia + " since its criancacodigo field is not nullable.");
                }
            }
            for (Pessoaautorizada pessoaautorizadaListOldPessoaautorizada : pessoaautorizadaListOld) {
                if (!pessoaautorizadaListNew.contains(pessoaautorizadaListOldPessoaautorizada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pessoaautorizada " + pessoaautorizadaListOldPessoaautorizada + " since its criancacodigo field is not nullable.");
                }
            }
            for (Controleretirada controleretiradaListOldControleretirada : controleretiradaListOld) {
                if (!controleretiradaListNew.contains(controleretiradaListOldControleretirada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Controleretirada " + controleretiradaListOldControleretirada + " since its criancacodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Remedio> attachedRemedioListNew = new ArrayList<Remedio>();
            for (Remedio remedioListNewRemedioToAttach : remedioListNew) {
                remedioListNewRemedioToAttach = em.getReference(remedioListNewRemedioToAttach.getClass(), remedioListNewRemedioToAttach.getCodigo());
                attachedRemedioListNew.add(remedioListNewRemedioToAttach);
            }
            remedioListNew = attachedRemedioListNew;
            crianca_1.setRemedioList(remedioListNew);
            List<Observacao> attachedObservacaoListNew = new ArrayList<Observacao>();
            for (Observacao observacaoListNewObservacaoToAttach : observacaoListNew) {
                observacaoListNewObservacaoToAttach = em.getReference(observacaoListNewObservacaoToAttach.getClass(), observacaoListNewObservacaoToAttach.getCodigo());
                attachedObservacaoListNew.add(observacaoListNewObservacaoToAttach);
            }
            observacaoListNew = attachedObservacaoListNew;
            crianca_1.setObservacaoList(observacaoListNew);
            List<Ocorrencia> attachedOcorrenciaListNew = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListNewOcorrenciaToAttach : ocorrenciaListNew) {
                ocorrenciaListNewOcorrenciaToAttach = em.getReference(ocorrenciaListNewOcorrenciaToAttach.getClass(), ocorrenciaListNewOcorrenciaToAttach.getCodigo());
                attachedOcorrenciaListNew.add(ocorrenciaListNewOcorrenciaToAttach);
            }
            ocorrenciaListNew = attachedOcorrenciaListNew;
            crianca_1.setOcorrenciaList(ocorrenciaListNew);
            List<Frequencia> attachedFrequenciaListNew = new ArrayList<Frequencia>();
            for (Frequencia frequenciaListNewFrequenciaToAttach : frequenciaListNew) {
                frequenciaListNewFrequenciaToAttach = em.getReference(frequenciaListNewFrequenciaToAttach.getClass(), frequenciaListNewFrequenciaToAttach.getCodigo());
                attachedFrequenciaListNew.add(frequenciaListNewFrequenciaToAttach);
            }
            frequenciaListNew = attachedFrequenciaListNew;
            crianca_1.setFrequenciaList(frequenciaListNew);
            List<Pessoaautorizada> attachedPessoaautorizadaListNew = new ArrayList<Pessoaautorizada>();
            for (Pessoaautorizada pessoaautorizadaListNewPessoaautorizadaToAttach : pessoaautorizadaListNew) {
                pessoaautorizadaListNewPessoaautorizadaToAttach = em.getReference(pessoaautorizadaListNewPessoaautorizadaToAttach.getClass(), pessoaautorizadaListNewPessoaautorizadaToAttach.getCodigo());
                attachedPessoaautorizadaListNew.add(pessoaautorizadaListNewPessoaautorizadaToAttach);
            }
            pessoaautorizadaListNew = attachedPessoaautorizadaListNew;
            crianca_1.setPessoaautorizadaList(pessoaautorizadaListNew);
            List<Controleretirada> attachedControleretiradaListNew = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListNewControleretiradaToAttach : controleretiradaListNew) {
                controleretiradaListNewControleretiradaToAttach = em.getReference(controleretiradaListNewControleretiradaToAttach.getClass(), controleretiradaListNewControleretiradaToAttach.getCodigo());
                attachedControleretiradaListNew.add(controleretiradaListNewControleretiradaToAttach);
            }
            controleretiradaListNew = attachedControleretiradaListNew;
            crianca_1.setControleretiradaList(controleretiradaListNew);
            crianca_1 = em.merge(crianca_1);
            for (Remedio remedioListNewRemedio : remedioListNew) {
                if (!remedioListOld.contains(remedioListNewRemedio)) {
                    Crianca oldCriancacodigoOfRemedioListNewRemedio = remedioListNewRemedio.getCriancacodigo();
                    remedioListNewRemedio.setCriancacodigo(crianca_1);
                    remedioListNewRemedio = em.merge(remedioListNewRemedio);
                    if (oldCriancacodigoOfRemedioListNewRemedio != null && !oldCriancacodigoOfRemedioListNewRemedio.equals(crianca_1)) {
                        oldCriancacodigoOfRemedioListNewRemedio.getRemedioList().remove(remedioListNewRemedio);
                        oldCriancacodigoOfRemedioListNewRemedio = em.merge(oldCriancacodigoOfRemedioListNewRemedio);
                    }
                }
            }
            for (Observacao observacaoListNewObservacao : observacaoListNew) {
                if (!observacaoListOld.contains(observacaoListNewObservacao)) {
                    Crianca oldCriancacodigoOfObservacaoListNewObservacao = observacaoListNewObservacao.getCriancacodigo();
                    observacaoListNewObservacao.setCriancacodigo(crianca_1);
                    observacaoListNewObservacao = em.merge(observacaoListNewObservacao);
                    if (oldCriancacodigoOfObservacaoListNewObservacao != null && !oldCriancacodigoOfObservacaoListNewObservacao.equals(crianca_1)) {
                        oldCriancacodigoOfObservacaoListNewObservacao.getObservacaoList().remove(observacaoListNewObservacao);
                        oldCriancacodigoOfObservacaoListNewObservacao = em.merge(oldCriancacodigoOfObservacaoListNewObservacao);
                    }
                }
            }
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Crianca oldCriancacodigoOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getCriancacodigo();
                    ocorrenciaListNewOcorrencia.setCriancacodigo(crianca_1);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldCriancacodigoOfOcorrenciaListNewOcorrencia != null && !oldCriancacodigoOfOcorrenciaListNewOcorrencia.equals(crianca_1)) {
                        oldCriancacodigoOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldCriancacodigoOfOcorrenciaListNewOcorrencia = em.merge(oldCriancacodigoOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            for (Frequencia frequenciaListNewFrequencia : frequenciaListNew) {
                if (!frequenciaListOld.contains(frequenciaListNewFrequencia)) {
                    Crianca oldCriancacodigoOfFrequenciaListNewFrequencia = frequenciaListNewFrequencia.getCriancacodigo();
                    frequenciaListNewFrequencia.setCriancacodigo(crianca_1);
                    frequenciaListNewFrequencia = em.merge(frequenciaListNewFrequencia);
                    if (oldCriancacodigoOfFrequenciaListNewFrequencia != null && !oldCriancacodigoOfFrequenciaListNewFrequencia.equals(crianca_1)) {
                        oldCriancacodigoOfFrequenciaListNewFrequencia.getFrequenciaList().remove(frequenciaListNewFrequencia);
                        oldCriancacodigoOfFrequenciaListNewFrequencia = em.merge(oldCriancacodigoOfFrequenciaListNewFrequencia);
                    }
                }
            }
            for (Pessoaautorizada pessoaautorizadaListNewPessoaautorizada : pessoaautorizadaListNew) {
                if (!pessoaautorizadaListOld.contains(pessoaautorizadaListNewPessoaautorizada)) {
                    Crianca oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada = pessoaautorizadaListNewPessoaautorizada.getCriancacodigo();
                    pessoaautorizadaListNewPessoaautorizada.setCriancacodigo(crianca_1);
                    pessoaautorizadaListNewPessoaautorizada = em.merge(pessoaautorizadaListNewPessoaautorizada);
                    if (oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada != null && !oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada.equals(crianca_1)) {
                        oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada.getPessoaautorizadaList().remove(pessoaautorizadaListNewPessoaautorizada);
                        oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada = em.merge(oldCriancacodigoOfPessoaautorizadaListNewPessoaautorizada);
                    }
                }
            }
            for (Controleretirada controleretiradaListNewControleretirada : controleretiradaListNew) {
                if (!controleretiradaListOld.contains(controleretiradaListNewControleretirada)) {
                    Crianca oldCriancacodigoOfControleretiradaListNewControleretirada = controleretiradaListNewControleretirada.getCriancacodigo();
                    controleretiradaListNewControleretirada.setCriancacodigo(crianca_1);
                    controleretiradaListNewControleretirada = em.merge(controleretiradaListNewControleretirada);
                    if (oldCriancacodigoOfControleretiradaListNewControleretirada != null && !oldCriancacodigoOfControleretiradaListNewControleretirada.equals(crianca_1)) {
                        oldCriancacodigoOfControleretiradaListNewControleretirada.getControleretiradaList().remove(controleretiradaListNewControleretirada);
                        oldCriancacodigoOfControleretiradaListNewControleretirada = em.merge(oldCriancacodigoOfControleretiradaListNewControleretirada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = crianca_1.getCodigo();
                if (findCrianca_1(id) == null) {
                    throw new NonexistentEntityException("The crianca_1 with id " + id + " no longer exists.");
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
            Crianca crianca_1;
            try {
                crianca_1 = em.getReference(Crianca.class, id);
                crianca_1.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crianca_1 with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Remedio> remedioListOrphanCheck = crianca_1.getRemedioList();
            for (Remedio remedioListOrphanCheckRemedio : remedioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Remedio " + remedioListOrphanCheckRemedio + " in its remedioList field has a non-nullable criancacodigo field.");
            }
            List<Observacao> observacaoListOrphanCheck = crianca_1.getObservacaoList();
            for (Observacao observacaoListOrphanCheckObservacao : observacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Observacao " + observacaoListOrphanCheckObservacao + " in its observacaoList field has a non-nullable criancacodigo field.");
            }
            List<Ocorrencia> ocorrenciaListOrphanCheck = crianca_1.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOrphanCheckOcorrencia : ocorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Ocorrencia " + ocorrenciaListOrphanCheckOcorrencia + " in its ocorrenciaList field has a non-nullable criancacodigo field.");
            }
            List<Frequencia> frequenciaListOrphanCheck = crianca_1.getFrequenciaList();
            for (Frequencia frequenciaListOrphanCheckFrequencia : frequenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Frequencia " + frequenciaListOrphanCheckFrequencia + " in its frequenciaList field has a non-nullable criancacodigo field.");
            }
            List<Pessoaautorizada> pessoaautorizadaListOrphanCheck = crianca_1.getPessoaautorizadaList();
            for (Pessoaautorizada pessoaautorizadaListOrphanCheckPessoaautorizada : pessoaautorizadaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Pessoaautorizada " + pessoaautorizadaListOrphanCheckPessoaautorizada + " in its pessoaautorizadaList field has a non-nullable criancacodigo field.");
            }
            List<Controleretirada> controleretiradaListOrphanCheck = crianca_1.getControleretiradaList();
            for (Controleretirada controleretiradaListOrphanCheckControleretirada : controleretiradaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca_1 (" + crianca_1 + ") cannot be destroyed since the Controleretirada " + controleretiradaListOrphanCheckControleretirada + " in its controleretiradaList field has a non-nullable criancacodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(crianca_1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Crianca> findCrianca_1Entities() {
        return findCrianca_1Entities(true, -1, -1);
    }

    public List<Crianca> findCrianca_1Entities(int maxResults, int firstResult) {
        return findCrianca_1Entities(false, maxResults, firstResult);
    }

    private List<Crianca> findCrianca_1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
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

    public Crianca findCrianca_1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Crianca.class, id);
        } finally {
            em.close();
        }
    }

    public int getCrianca_1Count() {
        EntityManager em = getEntityManager();
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
