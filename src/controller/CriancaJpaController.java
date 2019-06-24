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
import model.Pessoaautorizada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Observacao;
import model.Frequencia;
import model.Contatoemergencia;
import model.Remedio;
import model.Ocorrencia;
import model.Controleretirada;
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
        if (crianca.getObservacaoList() == null) {
            crianca.setObservacaoList(new ArrayList<Observacao>());
        }
        if (crianca.getFrequenciaList() == null) {
            crianca.setFrequenciaList(new ArrayList<Frequencia>());
        }
        if (crianca.getContatoemergenciaList() == null) {
            crianca.setContatoemergenciaList(new ArrayList<Contatoemergencia>());
        }
        if (crianca.getRemedioList() == null) {
            crianca.setRemedioList(new ArrayList<Remedio>());
        }
        if (crianca.getOcorrenciaList() == null) {
            crianca.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        if (crianca.getControleretiradaList() == null) {
            crianca.setControleretiradaList(new ArrayList<Controleretirada>());
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
            List<Observacao> attachedObservacaoList = new ArrayList<Observacao>();
            for (Observacao observacaoListObservacaoToAttach : crianca.getObservacaoList()) {
                observacaoListObservacaoToAttach = em.getReference(observacaoListObservacaoToAttach.getClass(), observacaoListObservacaoToAttach.getCodigo());
                attachedObservacaoList.add(observacaoListObservacaoToAttach);
            }
            crianca.setObservacaoList(attachedObservacaoList);
            List<Frequencia> attachedFrequenciaList = new ArrayList<Frequencia>();
            for (Frequencia frequenciaListFrequenciaToAttach : crianca.getFrequenciaList()) {
                frequenciaListFrequenciaToAttach = em.getReference(frequenciaListFrequenciaToAttach.getClass(), frequenciaListFrequenciaToAttach.getCodigo());
                attachedFrequenciaList.add(frequenciaListFrequenciaToAttach);
            }
            crianca.setFrequenciaList(attachedFrequenciaList);
            List<Contatoemergencia> attachedContatoemergenciaList = new ArrayList<Contatoemergencia>();
            for (Contatoemergencia contatoemergenciaListContatoemergenciaToAttach : crianca.getContatoemergenciaList()) {
                contatoemergenciaListContatoemergenciaToAttach = em.getReference(contatoemergenciaListContatoemergenciaToAttach.getClass(), contatoemergenciaListContatoemergenciaToAttach.getCodigo());
                attachedContatoemergenciaList.add(contatoemergenciaListContatoemergenciaToAttach);
            }
            crianca.setContatoemergenciaList(attachedContatoemergenciaList);
            List<Remedio> attachedRemedioList = new ArrayList<Remedio>();
            for (Remedio remedioListRemedioToAttach : crianca.getRemedioList()) {
                remedioListRemedioToAttach = em.getReference(remedioListRemedioToAttach.getClass(), remedioListRemedioToAttach.getCodigo());
                attachedRemedioList.add(remedioListRemedioToAttach);
            }
            crianca.setRemedioList(attachedRemedioList);
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : crianca.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getCodigo());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            crianca.setOcorrenciaList(attachedOcorrenciaList);
            List<Controleretirada> attachedControleretiradaList = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListControleretiradaToAttach : crianca.getControleretiradaList()) {
                controleretiradaListControleretiradaToAttach = em.getReference(controleretiradaListControleretiradaToAttach.getClass(), controleretiradaListControleretiradaToAttach.getCodigo());
                attachedControleretiradaList.add(controleretiradaListControleretiradaToAttach);
            }
            crianca.setControleretiradaList(attachedControleretiradaList);
            em.persist(crianca);
            for (Pessoaautorizada pessoaautorizadaListPessoaautorizada : crianca.getPessoaautorizadaList()) {
                pessoaautorizadaListPessoaautorizada.getCriancaList().add(crianca);
                pessoaautorizadaListPessoaautorizada = em.merge(pessoaautorizadaListPessoaautorizada);
            }
            for (Observacao observacaoListObservacao : crianca.getObservacaoList()) {
                Crianca oldCriancacodigoOfObservacaoListObservacao = observacaoListObservacao.getCriancacodigo();
                observacaoListObservacao.setCriancacodigo(crianca);
                observacaoListObservacao = em.merge(observacaoListObservacao);
                if (oldCriancacodigoOfObservacaoListObservacao != null) {
                    oldCriancacodigoOfObservacaoListObservacao.getObservacaoList().remove(observacaoListObservacao);
                    oldCriancacodigoOfObservacaoListObservacao = em.merge(oldCriancacodigoOfObservacaoListObservacao);
                }
            }
            for (Frequencia frequenciaListFrequencia : crianca.getFrequenciaList()) {
                Crianca oldCriancacodigoOfFrequenciaListFrequencia = frequenciaListFrequencia.getCriancacodigo();
                frequenciaListFrequencia.setCriancacodigo(crianca);
                frequenciaListFrequencia = em.merge(frequenciaListFrequencia);
                if (oldCriancacodigoOfFrequenciaListFrequencia != null) {
                    oldCriancacodigoOfFrequenciaListFrequencia.getFrequenciaList().remove(frequenciaListFrequencia);
                    oldCriancacodigoOfFrequenciaListFrequencia = em.merge(oldCriancacodigoOfFrequenciaListFrequencia);
                }
            }
            for (Contatoemergencia contatoemergenciaListContatoemergencia : crianca.getContatoemergenciaList()) {
                Crianca oldCriancaCodigoOfContatoemergenciaListContatoemergencia = contatoemergenciaListContatoemergencia.getCriancaCodigo();
                contatoemergenciaListContatoemergencia.setCriancaCodigo(crianca);
                contatoemergenciaListContatoemergencia = em.merge(contatoemergenciaListContatoemergencia);
                if (oldCriancaCodigoOfContatoemergenciaListContatoemergencia != null) {
                    oldCriancaCodigoOfContatoemergenciaListContatoemergencia.getContatoemergenciaList().remove(contatoemergenciaListContatoemergencia);
                    oldCriancaCodigoOfContatoemergenciaListContatoemergencia = em.merge(oldCriancaCodigoOfContatoemergenciaListContatoemergencia);
                }
            }
            for (Remedio remedioListRemedio : crianca.getRemedioList()) {
                Crianca oldCriancacodigoOfRemedioListRemedio = remedioListRemedio.getCriancacodigo();
                remedioListRemedio.setCriancacodigo(crianca);
                remedioListRemedio = em.merge(remedioListRemedio);
                if (oldCriancacodigoOfRemedioListRemedio != null) {
                    oldCriancacodigoOfRemedioListRemedio.getRemedioList().remove(remedioListRemedio);
                    oldCriancacodigoOfRemedioListRemedio = em.merge(oldCriancacodigoOfRemedioListRemedio);
                }
            }
            for (Ocorrencia ocorrenciaListOcorrencia : crianca.getOcorrenciaList()) {
                Crianca oldCriancacodigoOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getCriancacodigo();
                ocorrenciaListOcorrencia.setCriancacodigo(crianca);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldCriancacodigoOfOcorrenciaListOcorrencia != null) {
                    oldCriancacodigoOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldCriancacodigoOfOcorrenciaListOcorrencia = em.merge(oldCriancacodigoOfOcorrenciaListOcorrencia);
                }
            }
            for (Controleretirada controleretiradaListControleretirada : crianca.getControleretiradaList()) {
                Crianca oldCriancacodigoOfControleretiradaListControleretirada = controleretiradaListControleretirada.getCriancacodigo();
                controleretiradaListControleretirada.setCriancacodigo(crianca);
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

    public void edit(Crianca crianca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = utilities.GerenciamentoEntidades.getEntityManager();
            em.getTransaction().begin();
            Crianca persistentCrianca = em.find(Crianca.class, crianca.getCodigo());
            List<Pessoaautorizada> pessoaautorizadaListOld = persistentCrianca.getPessoaautorizadaList();
            List<Pessoaautorizada> pessoaautorizadaListNew = crianca.getPessoaautorizadaList();
            List<Observacao> observacaoListOld = persistentCrianca.getObservacaoList();
            List<Observacao> observacaoListNew = crianca.getObservacaoList();
            List<Frequencia> frequenciaListOld = persistentCrianca.getFrequenciaList();
            List<Frequencia> frequenciaListNew = crianca.getFrequenciaList();
            List<Contatoemergencia> contatoemergenciaListOld = persistentCrianca.getContatoemergenciaList();
            List<Contatoemergencia> contatoemergenciaListNew = crianca.getContatoemergenciaList();
            List<Remedio> remedioListOld = persistentCrianca.getRemedioList();
            List<Remedio> remedioListNew = crianca.getRemedioList();
            List<Ocorrencia> ocorrenciaListOld = persistentCrianca.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = crianca.getOcorrenciaList();
            List<Controleretirada> controleretiradaListOld = persistentCrianca.getControleretiradaList();
            List<Controleretirada> controleretiradaListNew = crianca.getControleretiradaList();
            List<String> illegalOrphanMessages = null;
            for (Observacao observacaoListOldObservacao : observacaoListOld) {
                if (!observacaoListNew.contains(observacaoListOldObservacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Observacao " + observacaoListOldObservacao + " since its criancacodigo field is not nullable.");
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
            for (Contatoemergencia contatoemergenciaListOldContatoemergencia : contatoemergenciaListOld) {
                if (!contatoemergenciaListNew.contains(contatoemergenciaListOldContatoemergencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contatoemergencia " + contatoemergenciaListOldContatoemergencia + " since its criancaCodigo field is not nullable.");
                }
            }
            for (Remedio remedioListOldRemedio : remedioListOld) {
                if (!remedioListNew.contains(remedioListOldRemedio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Remedio " + remedioListOldRemedio + " since its criancacodigo field is not nullable.");
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
            List<Pessoaautorizada> attachedPessoaautorizadaListNew = new ArrayList<Pessoaautorizada>();
            for (Pessoaautorizada pessoaautorizadaListNewPessoaautorizadaToAttach : pessoaautorizadaListNew) {
                pessoaautorizadaListNewPessoaautorizadaToAttach = em.getReference(pessoaautorizadaListNewPessoaautorizadaToAttach.getClass(), pessoaautorizadaListNewPessoaautorizadaToAttach.getCodigo());
                attachedPessoaautorizadaListNew.add(pessoaautorizadaListNewPessoaautorizadaToAttach);
            }
            pessoaautorizadaListNew = attachedPessoaautorizadaListNew;
            crianca.setPessoaautorizadaList(pessoaautorizadaListNew);
            List<Observacao> attachedObservacaoListNew = new ArrayList<Observacao>();
            for (Observacao observacaoListNewObservacaoToAttach : observacaoListNew) {
                observacaoListNewObservacaoToAttach = em.getReference(observacaoListNewObservacaoToAttach.getClass(), observacaoListNewObservacaoToAttach.getCodigo());
                attachedObservacaoListNew.add(observacaoListNewObservacaoToAttach);
            }
            observacaoListNew = attachedObservacaoListNew;
            crianca.setObservacaoList(observacaoListNew);
            List<Frequencia> attachedFrequenciaListNew = new ArrayList<Frequencia>();
            for (Frequencia frequenciaListNewFrequenciaToAttach : frequenciaListNew) {
                frequenciaListNewFrequenciaToAttach = em.getReference(frequenciaListNewFrequenciaToAttach.getClass(), frequenciaListNewFrequenciaToAttach.getCodigo());
                attachedFrequenciaListNew.add(frequenciaListNewFrequenciaToAttach);
            }
            frequenciaListNew = attachedFrequenciaListNew;
            crianca.setFrequenciaList(frequenciaListNew);
            List<Contatoemergencia> attachedContatoemergenciaListNew = new ArrayList<Contatoemergencia>();
            for (Contatoemergencia contatoemergenciaListNewContatoemergenciaToAttach : contatoemergenciaListNew) {
                contatoemergenciaListNewContatoemergenciaToAttach = em.getReference(contatoemergenciaListNewContatoemergenciaToAttach.getClass(), contatoemergenciaListNewContatoemergenciaToAttach.getCodigo());
                attachedContatoemergenciaListNew.add(contatoemergenciaListNewContatoemergenciaToAttach);
            }
            contatoemergenciaListNew = attachedContatoemergenciaListNew;
            crianca.setContatoemergenciaList(contatoemergenciaListNew);
            List<Remedio> attachedRemedioListNew = new ArrayList<Remedio>();
            for (Remedio remedioListNewRemedioToAttach : remedioListNew) {
                remedioListNewRemedioToAttach = em.getReference(remedioListNewRemedioToAttach.getClass(), remedioListNewRemedioToAttach.getCodigo());
                attachedRemedioListNew.add(remedioListNewRemedioToAttach);
            }
            remedioListNew = attachedRemedioListNew;
            crianca.setRemedioList(remedioListNew);
            List<Ocorrencia> attachedOcorrenciaListNew = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListNewOcorrenciaToAttach : ocorrenciaListNew) {
                ocorrenciaListNewOcorrenciaToAttach = em.getReference(ocorrenciaListNewOcorrenciaToAttach.getClass(), ocorrenciaListNewOcorrenciaToAttach.getCodigo());
                attachedOcorrenciaListNew.add(ocorrenciaListNewOcorrenciaToAttach);
            }
            ocorrenciaListNew = attachedOcorrenciaListNew;
            crianca.setOcorrenciaList(ocorrenciaListNew);
            List<Controleretirada> attachedControleretiradaListNew = new ArrayList<Controleretirada>();
            for (Controleretirada controleretiradaListNewControleretiradaToAttach : controleretiradaListNew) {
                controleretiradaListNewControleretiradaToAttach = em.getReference(controleretiradaListNewControleretiradaToAttach.getClass(), controleretiradaListNewControleretiradaToAttach.getCodigo());
                attachedControleretiradaListNew.add(controleretiradaListNewControleretiradaToAttach);
            }
            controleretiradaListNew = attachedControleretiradaListNew;
            crianca.setControleretiradaList(controleretiradaListNew);
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
            for (Observacao observacaoListNewObservacao : observacaoListNew) {
                if (!observacaoListOld.contains(observacaoListNewObservacao)) {
                    Crianca oldCriancacodigoOfObservacaoListNewObservacao = observacaoListNewObservacao.getCriancacodigo();
                    observacaoListNewObservacao.setCriancacodigo(crianca);
                    observacaoListNewObservacao = em.merge(observacaoListNewObservacao);
                    if (oldCriancacodigoOfObservacaoListNewObservacao != null && !oldCriancacodigoOfObservacaoListNewObservacao.equals(crianca)) {
                        oldCriancacodigoOfObservacaoListNewObservacao.getObservacaoList().remove(observacaoListNewObservacao);
                        oldCriancacodigoOfObservacaoListNewObservacao = em.merge(oldCriancacodigoOfObservacaoListNewObservacao);
                    }
                }
            }
            for (Frequencia frequenciaListNewFrequencia : frequenciaListNew) {
                if (!frequenciaListOld.contains(frequenciaListNewFrequencia)) {
                    Crianca oldCriancacodigoOfFrequenciaListNewFrequencia = frequenciaListNewFrequencia.getCriancacodigo();
                    frequenciaListNewFrequencia.setCriancacodigo(crianca);
                    frequenciaListNewFrequencia = em.merge(frequenciaListNewFrequencia);
                    if (oldCriancacodigoOfFrequenciaListNewFrequencia != null && !oldCriancacodigoOfFrequenciaListNewFrequencia.equals(crianca)) {
                        oldCriancacodigoOfFrequenciaListNewFrequencia.getFrequenciaList().remove(frequenciaListNewFrequencia);
                        oldCriancacodigoOfFrequenciaListNewFrequencia = em.merge(oldCriancacodigoOfFrequenciaListNewFrequencia);
                    }
                }
            }
            for (Contatoemergencia contatoemergenciaListNewContatoemergencia : contatoemergenciaListNew) {
                if (!contatoemergenciaListOld.contains(contatoemergenciaListNewContatoemergencia)) {
                    Crianca oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia = contatoemergenciaListNewContatoemergencia.getCriancaCodigo();
                    contatoemergenciaListNewContatoemergencia.setCriancaCodigo(crianca);
                    contatoemergenciaListNewContatoemergencia = em.merge(contatoemergenciaListNewContatoemergencia);
                    if (oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia != null && !oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia.equals(crianca)) {
                        oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia.getContatoemergenciaList().remove(contatoemergenciaListNewContatoemergencia);
                        oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia = em.merge(oldCriancaCodigoOfContatoemergenciaListNewContatoemergencia);
                    }
                }
            }
            for (Remedio remedioListNewRemedio : remedioListNew) {
                if (!remedioListOld.contains(remedioListNewRemedio)) {
                    Crianca oldCriancacodigoOfRemedioListNewRemedio = remedioListNewRemedio.getCriancacodigo();
                    remedioListNewRemedio.setCriancacodigo(crianca);
                    remedioListNewRemedio = em.merge(remedioListNewRemedio);
                    if (oldCriancacodigoOfRemedioListNewRemedio != null && !oldCriancacodigoOfRemedioListNewRemedio.equals(crianca)) {
                        oldCriancacodigoOfRemedioListNewRemedio.getRemedioList().remove(remedioListNewRemedio);
                        oldCriancacodigoOfRemedioListNewRemedio = em.merge(oldCriancacodigoOfRemedioListNewRemedio);
                    }
                }
            }
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Crianca oldCriancacodigoOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getCriancacodigo();
                    ocorrenciaListNewOcorrencia.setCriancacodigo(crianca);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldCriancacodigoOfOcorrenciaListNewOcorrencia != null && !oldCriancacodigoOfOcorrenciaListNewOcorrencia.equals(crianca)) {
                        oldCriancacodigoOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldCriancacodigoOfOcorrenciaListNewOcorrencia = em.merge(oldCriancacodigoOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            for (Controleretirada controleretiradaListNewControleretirada : controleretiradaListNew) {
                if (!controleretiradaListOld.contains(controleretiradaListNewControleretirada)) {
                    Crianca oldCriancacodigoOfControleretiradaListNewControleretirada = controleretiradaListNewControleretirada.getCriancacodigo();
                    controleretiradaListNewControleretirada.setCriancacodigo(crianca);
                    controleretiradaListNewControleretirada = em.merge(controleretiradaListNewControleretirada);
                    if (oldCriancacodigoOfControleretiradaListNewControleretirada != null && !oldCriancacodigoOfControleretiradaListNewControleretirada.equals(crianca)) {
                        oldCriancacodigoOfControleretiradaListNewControleretirada.getControleretiradaList().remove(controleretiradaListNewControleretirada);
                        oldCriancacodigoOfControleretiradaListNewControleretirada = em.merge(oldCriancacodigoOfControleretiradaListNewControleretirada);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Observacao> observacaoListOrphanCheck = crianca.getObservacaoList();
            for (Observacao observacaoListOrphanCheckObservacao : observacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Observacao " + observacaoListOrphanCheckObservacao + " in its observacaoList field has a non-nullable criancacodigo field.");
            }
            List<Frequencia> frequenciaListOrphanCheck = crianca.getFrequenciaList();
            for (Frequencia frequenciaListOrphanCheckFrequencia : frequenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Frequencia " + frequenciaListOrphanCheckFrequencia + " in its frequenciaList field has a non-nullable criancacodigo field.");
            }
            List<Contatoemergencia> contatoemergenciaListOrphanCheck = crianca.getContatoemergenciaList();
            for (Contatoemergencia contatoemergenciaListOrphanCheckContatoemergencia : contatoemergenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Contatoemergencia " + contatoemergenciaListOrphanCheckContatoemergencia + " in its contatoemergenciaList field has a non-nullable criancaCodigo field.");
            }
            List<Remedio> remedioListOrphanCheck = crianca.getRemedioList();
            for (Remedio remedioListOrphanCheckRemedio : remedioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Remedio " + remedioListOrphanCheckRemedio + " in its remedioList field has a non-nullable criancacodigo field.");
            }
            List<Ocorrencia> ocorrenciaListOrphanCheck = crianca.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOrphanCheckOcorrencia : ocorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Ocorrencia " + ocorrenciaListOrphanCheckOcorrencia + " in its ocorrenciaList field has a non-nullable criancacodigo field.");
            }
            List<Controleretirada> controleretiradaListOrphanCheck = crianca.getControleretiradaList();
            for (Controleretirada controleretiradaListOrphanCheckControleretirada : controleretiradaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crianca (" + crianca + ") cannot be destroyed since the Controleretirada " + controleretiradaListOrphanCheckControleretirada + " in its controleretiradaList field has a non-nullable criancacodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
