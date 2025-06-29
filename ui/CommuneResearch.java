package ui;

import core.*;

public class CommuneResearch extends CommuneSelector{
    static final Commune ALL_COMMUNES = Commune.allChoicesParameter();
    
    public CommuneResearch(){}

    @Override public void updateChoices(District selected){
        setEnabled(true);
        if (selected.getName().equals("TOUS"))
            forceChoosingAll();
        else {
            super.updateChoices(selected); // option vide en index 0

            removeItemAt(0);
            insertItemAt(ALL_COMMUNES, 0);
            setSelectedIndex(0);
        }
    }

    private void forceChoosingAll(){
        this.removeAllItems();
        this.addItem(ALL_COMMUNES);
        this.setSelectedIndex(0);
        this.setEnabled(false);
    }
}
