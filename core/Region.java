package core;

import java.io.*;
import java.util.ArrayList;

public class Region {
    private String name;
    private ArrayList<District> districts;
    private static ArrayList<Region> regionsExistantes = new ArrayList<>();
    private static final String filePath = "data/Region.txt";

    private Region(String name) {
        this.name = name.trim();
        this.districts = new ArrayList<>();
    }

    public static Region get(String name) {
        name = name.trim();
        for (Region region : regionsExistantes) {
            if (region.getName().equalsIgnoreCase(name)) {
                return region;
            }
        }
        Region newRegion = new Region(name);
        regionsExistantes.add(newRegion);
        return newRegion;
    }

    public String getName() {
        return name;
    }

    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void addDistrictIfAbsent(District district) {
        if (!districts.contains(district)) {
            districts.add(district);
        }
    }

    public static ArrayList<Region> getAll() {
        return regionsExistantes;
    }

    @Override
    public String toString() {
        return "Region : " + this.name;
    }

    public static Region allChoicesParameter() {
        return new Region("TOUS");
    }
}
