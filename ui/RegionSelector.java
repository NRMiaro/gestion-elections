package ui;

import javax.swing.*;
import java.util.*;
import core.*;

public class RegionSelector extends JComboBox<Region> {

    public RegionSelector(){
        ArrayList<Region> regions = Region.getAll();
        for (Region r : regions)
            addItem(r);
    }

    public Region getSelectedRegion(){
        return (Region) getSelectedItem();
    }
}
