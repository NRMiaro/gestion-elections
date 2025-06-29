package ui;

import core.*;

public class RegionResearch extends RegionSelector {
    private static final Region ALL_REGIONS = Region.allChoicesParameter();
    
    public RegionResearch(){
        super();
        insertItemAt(ALL_REGIONS, 0);
        setSelectedIndex(0);
    }
    
}
