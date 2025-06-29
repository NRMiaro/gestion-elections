package ui;

import javax.swing.*;
import core.*;

public class DistrictSelector extends JComboBox<District> {

    public DistrictSelector(){}

    public void updateChoices(Region selected){
        removeAllItems();
        if (selected == null) return;

        addItem(null); // option vide
        for (District d : selected.getDistricts()) {
            addItem(d);
        }
    }

    public District getSelectedDistrict(){
        return (District) getSelectedItem();
    }
}
