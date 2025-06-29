package ui;

import javax.swing.*;


public class ResultTable extends JTable {
    private TableModel model; 
    private Formulaire form;

    public ResultTable(Formulaire form){
        this.form = form;
        model = new TableModel(form);
        setModel(model);
        
        // style
        setRowHeight(40);
    }

    public void refresh(){
        form.updateData();
        model.changeStructure();
    }
}
