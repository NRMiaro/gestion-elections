package ui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private Formulaire form;
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();

    public TableModel(Formulaire form){
        this.form = form;
        this.columns = form.getColumns();
        this.data = form.getData();
        changeStructure();
    }

    @Override public int getColumnCount() {
        return columns.size();
    }

    @Override public int getRowCount() {
        return data.size();
    }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    @Override public String getColumnName(int column) {
        return columns.get(column);
    }

    public void changeStructure(){
        this.columns = form.getColumns();
        this.data = form.getData();

        fireTableStructureChanged();
    }

}
