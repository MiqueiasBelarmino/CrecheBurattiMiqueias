/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Frequencia;

/**
 *
 * @author Belarmino
 */
public class TableModelFrequenciaCrianca extends AbstractTableModel {

    private static final int COL_CHECK = 1;
    private static final int COL_CRIANCA = 0;
    private static final int NUM_COLUNAS = 2;
    private List<Frequencia> frequencia;
    private List check;

    public TableModelFrequenciaCrianca(List<Frequencia> frequencia, boolean novo) {
        this.frequencia = frequencia;
        check = new ArrayList();
        if (novo) {
            for (int i = 0; i < frequencia.size(); i++) {
                check.add(Boolean.TRUE); //Valor default.
            }
        } else {
            for (int i = 0; i < frequencia.size(); i++) {

                if (frequencia.get(i).getSituacao().equals("Presente")) {
                    check.add(Boolean.TRUE);
                } else {
                    check.add(Boolean.FALSE);
                }
            }
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
            return ((Frequencia) frequencia.get(linha)).getCriancacodigo();
        }
        return null;
    }

    public Class getColumnClass(int coluna) {
        if (coluna == COL_CHECK) {
            return Boolean.class;
        }
        if (coluna == COL_CRIANCA) {
            return String.class;
        }

        return super.getColumnClass(coluna);
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return (coluna == COL_CHECK);
    }

    @Override
    public void setValueAt(Object aValue, int linha, int coluna) {
        if (coluna == COL_CHECK) {
            check.set(linha, (Boolean) aValue);
        }
    }

    @Override
    public String getColumnName(int coluna) {
        if (coluna == 0) {
            return "Criança";
        }
        if (coluna == 1) {
            return "Presença";
        }
        return null;
    }

    public boolean isChecked(Frequencia frequencia) {
        return check.get(this.frequencia.indexOf(frequencia)) == Boolean.TRUE;
    }
}
