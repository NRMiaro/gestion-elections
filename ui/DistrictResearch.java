package ui;

import core.*;

public class DistrictResearch extends DistrictSelector {
    static final District ALL_DISTRICTS = District.allChoicesParameter();
    
    public DistrictResearch(){}

    @Override public void updateChoices(Region selected){
        setEnabled(true);
        if (selected.getName().equals("TOUS"))
            forceChoosingAll();
        else {
            super.updateChoices(selected);

            removeItemAt(0);
            insertItemAt(ALL_DISTRICTS, 0);
            setSelectedIndex(0);
        }
    }

    public void forceChoosingAll(){
        this.removeAllItems();
        this.addItem(ALL_DISTRICTS);
        
        this.setSelectedIndex(0);
        this.setEnabled(false);
    }
}
