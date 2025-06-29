package listener;

import java.awt.event.*;
import core.*;
import ui.*;

public class RegionListener implements ActionListener {
    private RegionResearch actionned;
    private DistrictResearch district;
    private CommuneResearch commune;

    public RegionListener(RegionResearch actionned, DistrictResearch district, CommuneResearch commune){
        this.actionned = actionned;
        this.district = district;
        this.commune = commune;
    }

    @Override public void actionPerformed(ActionEvent event){
        Region selected = actionned.getSelectedRegion();
        if (selected == null) return;
        
        district.updateChoices(selected);
        commune.updateChoices(district.getSelectedDistrict());
        System.out.println("Région choisie: " + selected.getName());
    }
}
