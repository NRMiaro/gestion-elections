package core;

import java.io.*;
import java.util.*;

public class District {
    private String name;
    private Region region;
    private ArrayList<Commune> communes = new ArrayList<>();
    private static Map<String, District> districtsExistants = new HashMap<>();
    private static final String filePath = "data/District.txt";

    private District(String name) {
        this.name = name.trim();
    }

    // méthode get avec clé composite
    public static District get(String name, String regionName) {
        name = name.trim();
        regionName = regionName.trim();
        String cle = name + "@" + regionName;

        if (districtsExistants.containsKey(cle))
            return districtsExistants.get(cle);
        
        District district = new District(name);

        districtsExistants.put(cle, district); // ajout dans le cache, avec la clé pour identifier
        return district;
    }

    public String getName() { return name; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }

    public ArrayList<Commune> getCommunes() { return communes; }

    public void addCommuneIfAbsent(Commune c) {
        if (!communes.contains(c))
            communes.add(c);
    }

    public static ArrayList<District> getAll(){
        return new ArrayList<>(districtsExistants.values());
    }

    @Override public String toString(){
        return "District : " + name;
    }

    public static District allChoicesParameter(){
        return new District("TOUS");
    }
}
