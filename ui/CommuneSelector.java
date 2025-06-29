package ui;

import javax.swing.*;
import core.*;

public class CommuneSelector extends JComboBox<Commune> {
    public CommuneSelector(){}

    public void updateChoices(District selected){
        removeAllItems();
        if (selected == null) return;
        
        addItem(null);
        for (Commune c : selected.getCommunes()){
            addItem(c);
        }
    }

    public Commune getSelectedCommune(){
        return (Commune) getSelectedItem();
    }
}
