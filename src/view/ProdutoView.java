/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProdutoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Component;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Produto;
import static org.jdesktop.observablecollections.ObservableCollections.observableList;

/**
 *
 * @author Belarmino
 */
public class ProdutoView extends javax.swing.JDialog {

    /**
     * Creates new form CriancaView
     */
    private ProdutoJpaController controllerProduto = null;
    private Produto produto = null;
    private boolean novo;

    public ProdutoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        habilitarComponentes(false);
        controllerProduto = new ProdutoJpaController();
        atualizarFiltro();

    }

    private void atualizarFiltro() {
        listProduto.clear();
        listProduto.addAll(controllerProduto.findAtivo(1));
    }

    private void montarDadosFormulario(int linha) {
        Produto p = listProduto.get(linha);
        txtDescricao.setText(p.getDescricao());
        txtUnidade.setText(p.getUnidade());
    }

    private void habilitarComponentes(boolean status) {

        for (Component component : painelDados.getComponents()) {
            component.setEnabled(status);
        }

        for (Component component : painelFiltro.getComponents()) {
            component.setEnabled(!status);
        }
        tableProduto.setEnabled(!status);
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

        listProduto = observableList(new ArrayList<model.Produto>());
        painelDados = new javax.swing.JPanel();
        txtDescricao = new javax.swing.JTextField();
        txtUnidade = new javax.swing.JTextField();
        painelFiltro = new javax.swing.JPanel();
        txtFiltrar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProduto = new javax.swing.JTable();
        btnListarTodos = new javax.swing.JButton();
        btnListarTodos1 = new javax.swing.JButton();
        painelBotoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento - Produto");
        setResizable(false);

        painelDados.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome"));
        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });

        txtUnidade.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidade"));
        txtUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelFiltro.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome do produto"));
        txtFiltrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyReleased(evt);
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listProduto, tableProduto);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${descricao}"));
        columnBinding.setColumnName("Descricao");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${unidade}"));
        columnBinding.setColumnName("Unidade");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${estoque}"));
        columnBinding.setColumnName("Estoque");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tableProduto);
        if (tableProduto.getColumnModel().getColumnCount() > 0) {
            tableProduto.getColumnModel().getColumn(0).setResizable(false);
            tableProduto.getColumnModel().getColumn(1).setResizable(false);
            tableProduto.getColumnModel().getColumn(1).setPreferredWidth(15);
            tableProduto.getColumnModel().getColumn(2).setResizable(false);
            tableProduto.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        btnListarTodos.setText("Listar Ativos e Inativos");
        btnListarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarTodosActionPerformed(evt);
            }
        });

        btnListarTodos1.setText("Apenas Ativos");
        btnListarTodos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarTodos1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelFiltroLayout = new javax.swing.GroupLayout(painelFiltro);
        painelFiltro.setLayout(painelFiltroLayout);
        painelFiltroLayout.setHorizontalGroup(
            painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFiltroLayout.createSequentialGroup()
                        .addComponent(txtFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnListarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnListarTodos1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        painelFiltroLayout.setVerticalGroup(
            painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelFiltroLayout.createSequentialGroup()
                        .addComponent(btnListarTodos)
                        .addGap(3, 3, 3)
                        .addComponent(btnListarTodos1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void txtUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadeActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitarComponentes(true);
        novo = true;
        produto = new Produto();
        txtDescricao.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        habilitarComponentes(true);
        novo = false;
        int row;
        row = tableProduto.getSelectedRow();
        if (row > -1) {
            produto = listProduto.get(row);
            tableProduto.setRowSelectionInterval(row, row);
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
            produto.setDescricao(txtDescricao.getText().trim());
            produto.setUnidade(txtUnidade.getText().trim());
            produto.setEstoque(0);
            produto.setAtivo(1);
            if (!novo) {
                try {
                    controllerProduto.edit(produto);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProdutoView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProdutoView.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                controllerProduto.create(produto);
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
        if (tableProduto.isRowSelected(tableProduto.getSelectedRow())) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?") == 0) {
                try {
                    produto = listProduto.get(tableProduto.getSelectedRow());
                    produto.setAtivo(0);
                    controllerProduto.edit(produto);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProdutoView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProdutoView.class.getName()).log(Level.SEVERE, null, ex);
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

    private void btnListarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarTodosActionPerformed
        listProduto.clear();
        listProduto.addAll(controllerProduto.findProdutoEntities());
    }//GEN-LAST:event_btnListarTodosActionPerformed

    private void btnListarTodos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarTodos1ActionPerformed
        atualizarFiltro();
    }//GEN-LAST:event_btnListarTodos1ActionPerformed

    private void pesquisar() {
        listProduto.clear();
        listProduto.addAll(controllerProduto.findDescricao(txtFiltrar.getText().trim()));
        int linha = listProduto.size() - 1;
        if (linha > 0) {
            tableProduto.setRowSelectionInterval(linha, linha);
        }
    }

    private boolean vazio() {
        boolean vazio = false;

        if (txtDescricao.getText().trim().isEmpty()) {
            vazio = true;
        } else if (txtUnidade.getText().trim().isEmpty()) {
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
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProdutoView dialog = new ProdutoView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnListarTodos;
    private javax.swing.JButton btnListarTodos1;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<model.Produto> listProduto;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelFiltro;
    private javax.swing.JTable tableProduto;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtFiltrar;
    private javax.swing.JTextField txtUnidade;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
