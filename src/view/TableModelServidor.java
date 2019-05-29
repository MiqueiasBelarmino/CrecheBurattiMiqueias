/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Frequenciaservidor;

/**
 *
 * @author Belarmino
 */
public class TableModelServidor extends AbstractTableModel {

    private static final int COL_CHECK = 1;
    private static final int COL_CRIANCA = 0;
    private static final int NUM_COLUNAS = 2;
    private List frequencia;
    private List check;

    public TableModelServidor(List frequencia) {
        this.frequencia = frequencia;
        check = new ArrayList();
        for (int i = 0; i < frequencia.size(); i++) {
            check.add(Boolean.FALSE); //Valor default.
        }
    }

    public int getColumnCount() {
        return NUM_COLUNAS;
    }

    public int getRowCount() {
        return frequencia.size();
    }

    public Object getValueAt(int linha, int coluna) {
        if (coluna == COL_CHECK) {
            return check.get(linha) == Boolean.TRUE;
        }
        if (coluna == COL_CRIANCA) {
            return ((Frequenciaservidor) frequencia.get(linha)).getServidorcodigo();
        }
        return null;
    }

    public Class getColumnClass(int coluna) {
        if (coluna == COL_CHECK) {
            return Boolean.class;
        }
        if ( coluna == COL_CRIANCA) {
            return String.class;
        }

        return super.getColumnClass(coluna);
    }

    public boolean isCellEditable(int linha, int coluna) {
        return (coluna == COL_CHECK);
    }

    public void setValueAt(Object aValue, int linha, int coluna) {
        if (coluna == COL_CHECK) {
            check.set(linha, (Boolean) aValue);
        }
    }

    public String getColumnName(int coluna) {
        if (coluna == 0) {
            return "Servidor";
        }
        if (coluna == 1) {
            return "Presença";
        }
        return null;
    }

    /**
     * Esse método é para permitir que você pergunte ao modelo se um autor está
     * ou não selecionado.
     */
    public boolean isChecked(Frequenciaservidor frequencia) {
        return check.get(this.frequencia.indexOf(frequencia)) == Boolean.TRUE;
    }
}
