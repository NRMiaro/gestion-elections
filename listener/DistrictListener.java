package listener;

import java.awt.event.*;
import core.*;
import ui.*;

public class DistrictListener implements ActionListener {
    private DistrictResearch actionned;
    private CommuneResearch commune;

    public DistrictListener(DistrictResearch actionned, CommuneResearch commune){
        this.actionned = actionned;
        this.commune = commune;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        District selected = actionned.getSelectedDistrict();
        if (selected == null) return;
        
        commune.updateChoices(selected);
        System.out.println("District choisi: " + selected.getName());
    }
    
}
