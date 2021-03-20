/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DoacaoJpaController;
import controller.EventoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Component;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Doacao;
import model.Evento;
import static org.jdesktop.observablecollections.ObservableCollections.observableList;

/**
 *
 * @author Belarmino
 */
public class DoacaoView extends javax.swing.JDialog {

    /**
     * Creates new form CriancaView
     */
    private DoacaoJpaController controllerDoacao = null;
    private EventoJpaController ejc = null;
    private Doacao doacao = null;
    private Evento evento = null;
    private boolean novo;

    public DoacaoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        habilitarComponentes(false);
        controllerDoacao = new DoacaoJpaController();
        ejc = new EventoJpaController();
        //atualizarFiltro();
        listEvento.addAll(ejc.findEventoEntities());

    }

    private void atualizarFiltro() {
        listDoacao.clear();
        listDoacao.addAll(controllerDoacao.findDoacaoEntities());
    }

    private void montarDadosFormulario(int linha) {
        Doacao d = listDoacao.get(linha);
        txtUnidade.setText(d.getUnidade());
        txtDescricao.setText(d.getDescricao());
        txtQuantidade.setText(String.valueOf(d.getQuantidade()));
        if ("Alimento".equals(d.getCategoria())) {
            comboCategoria.setSelectedIndex(0);
        } else if ("Dinheiro".equals(d.getCategoria())) {
            comboCategoria.setSelectedIndex(1);
        } else {
            comboCategoria.setSelectedIndex(2);
        }
    }

    private void habilitarComponentes(boolean status) {

        for (Component component : painelDados.getComponents()) {
            component.setEnabled(status);
        }

        for (Component component : painelFiltro.getComponents()) {
            component.setEnabled(!status);
        }
        tableDoacao.setEnabled(!status);
        btnNovo.setEnabled(!status);
        btnSalvar.setEnabled(status);
        btnCancelar.setEnabled(status);
        btnAlterar.setEnabled(!status);
        btnDeletar.setEnabled(!status);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        listDoacao = observableList(new ArrayList<model.Doacao>());
        txtQuantidade1 = new javax.swing.JTextField();
        listEvento = observableList(new ArrayList<model.Evento>());
        painelDados = new javax.swing.JPanel();
        txtUnidade = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        comboCategoria = new javax.swing.JComboBox<>();
        txtQuantidade = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableEvento = new javax.swing.JTable();
        txtFiltrarEvento = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        painelFiltro = new javax.swing.JPanel();
        txtFiltrar = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDoacao = new javax.swing.JTable();
        painelBotoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        txtQuantidade1.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidade"));
        txtQuantidade1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidade1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento - Doação");
        setResizable(false);

        painelDados.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtUnidade.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidade"));
        txtUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadeActionPerformed(evt);
            }
        });

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        txtDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));
        jScrollPane1.setViewportView(txtDescricao);

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimento", "Dinheiro", "Produto Limpeza" }));
        comboCategoria.setBorder(javax.swing.BorderFactory.createTitledBorder("Categoria"));

        txtQuantidade.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidade"));
        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEvento, tableEvento);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${data}"));
        columnBinding.setColumnName("Data");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${categoria}"));
        columnBinding.setColumnName("Categoria");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane4.setViewportView(tableEvento);

        txtFiltrarEvento.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome do evento"));
        txtFiltrarEvento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrarEventoKeyReleased(evt);
            }
        });

        jButton1.setText("Confirmar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtFiltrarEvento))
                .addContainerGap())
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtFiltrarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelFiltro.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Descricao"));
        txtFiltrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyReleased(evt);
            }
        });

        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listDoacao, tableDoacao);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${descricao}"));
        columnBinding.setColumnName("Descricao");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${quantidade}"));
        columnBinding.setColumnName("Quantidade");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane3.setViewportView(tableDoacao);

        javax.swing.GroupLayout painelFiltroLayout = new javax.swing.GroupLayout(painelFiltro);
        painelFiltro.setLayout(painelFiltroLayout);
        painelFiltroLayout.setHorizontalGroup(
            painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addComponent(txtFiltrar))
                .addContainerGap())
        );
        painelFiltroLayout.setVerticalGroup(
            painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelBotoesLayout = new javax.swing.GroupLayout(painelBotoes);
        painelBotoes.setLayout(painelBotoesLayout);
        painelBotoesLayout.setHorizontalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelBotoesLayout.setVerticalGroup(
            painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnAlterar)
                    .addComponent(btnSalvar)
                    .addComponent(btnDeletar)
                    .addComponent(btnCancelar)
                    .addComponent(btnSair))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(painelDados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(painelFiltro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitarComponentes(true);
        novo = true;
        doacao = new Doacao();
        txtUnidade.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        habilitarComponentes(true);
        novo = false;
        int row;
        row = tableDoacao.getSelectedRow();
        if (row > -1) {
            doacao = listDoacao.get(row);
            tableDoacao.setRowSelectionInterval(row, row);
            montarDadosFormulario(row);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtFiltrarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarKeyReleased
        pesquisar();
    }//GEN-LAST:event_txtFiltrarKeyReleased

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (vazio()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        } else {
            habilitarComponentes(false);
            for (Doacao d : listDoacao) {
                controllerDoacao.create(d);
            }
            for (Component c : painelDados.getComponents()) {
                if (c instanceof JTextField) {
                    ((JTextField) c).setText("");
                }
                if (c instanceof JFormattedTextField) {
                    ((JFormattedTextField) c).setText("");
                }
            }
            atualizarFiltro();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        habilitarComponentes(false);
        for (Component c : painelDados.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            }
            if (c instanceof JFormattedTextField) {
                ((JFormattedTextField) c).setText("");
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        if (tableDoacao.isRowSelected(tableDoacao.getSelectedRow())) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?") == 0) {
                try {
                    doacao = listDoacao.get(tableDoacao.getSelectedRow());
                    doacao.setAtivo(0);
                    controllerDoacao.edit(doacao);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(DoacaoView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DoacaoView.class.getName()).log(Level.SEVERE, null, ex);
                }
                atualizarFiltro();
                habilitarComponentes(false);
                for (Component c : painelDados.getComponents()) {
                    if (c instanceof JTextField) {
                        ((JTextField) c).setText("");
                    }
                    if (c instanceof JFormattedTextField) {
                        ((JFormattedTextField) c).setText("");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um registro!");
        }
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void txtUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadeActionPerformed

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtQuantidade1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidade1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidade1ActionPerformed

    private void txtFiltrarEventoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarEventoKeyReleased
        pesquisarEvento();
    }//GEN-LAST:event_txtFiltrarEventoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doacao = new Doacao();
        doacao.setDescricao(txtDescricao.getText().trim());
        doacao.setCategoria(comboCategoria.getSelectedItem().toString());
        doacao.setUnidade(txtUnidade.getText().trim());
        doacao.setQuantidade(Integer.parseInt(txtQuantidade.getText().trim()));
        doacao.setEventoCodigo(listEvento.get(tableEvento.getSelectedRow()));
        doacao.setAtivo(1);
        listDoacao.add(doacao);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pesquisarEvento() {
        listEvento.clear();
        listEvento.addAll(ejc.findNome(txtFiltrarEvento.getText().trim()));
        int linha = listEvento.size() - 1;
        if (linha > 0) {
            tableEvento.setRowSelectionInterval(linha, linha);
        }
    }
    
    private void pesquisar() {
        listDoacao.clear();
        listDoacao.addAll(controllerDoacao.findDescricao(txtFiltrar.getText().trim()));
        int linha = listDoacao.size() - 1;
        if (linha > 0) {
            tableDoacao.setRowSelectionInterval(linha, linha);
        }
    }

    private boolean vazio() {
        boolean vazio = false;

        if (txtUnidade.getText().trim().isEmpty()) {
            vazio = true;
        } else if (txtDescricao.getText().trim().isEmpty()) {
            vazio = true;
        }

        return vazio;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DoacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoacaoView dialog = new DoacaoView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private java.util.List<model.Doacao> listDoacao;
    private java.util.List<model.Evento> listEvento;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelFiltro;
    private javax.swing.JTable tableDoacao;
    private javax.swing.JTable tableEvento;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtFiltrar;
    private javax.swing.JTextField txtFiltrarEvento;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtQuantidade1;
    private javax.swing.JTextField txtUnidade;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}