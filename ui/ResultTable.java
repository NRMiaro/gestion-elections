package ui;

import javax.swing.*;

import java.awt.Font;
import java.util.ArrayList;

public class ResultTable extends JTable {
    private TableModel model; 
    private Formulaire form;

    public ResultTable(Formulaire form){
        this.form = form;
        form.updateData();
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
