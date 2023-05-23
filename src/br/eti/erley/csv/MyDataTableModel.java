package br.eti.erley.csv;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Erley
 */
public class MyDataTableModel extends DefaultTableModel {
    
    private ArrayList<String[]> values;
    private String[] columns;

    public MyDataTableModel(ArrayList<String[]> values, String[] columns) {
        this.values = values;
        this.columns = columns;
    }

    public MyDataTableModel() {
    }

    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return values.get(row)[column];
    }

    @Override
    public String getColumnName(int column) {
        System.err.println("getColumn Name");
        return columns[column];
    }

    @Override
    public int getColumnCount() {
        System.out.println("MyDataTableModel.getColumnCount()");
        if(columns == null)
            return 0;
        return columns.length;
    }

    @Override
    public int getRowCount() {
        if(values == null)
            return 0;
        return values.size();
    }
    
}
