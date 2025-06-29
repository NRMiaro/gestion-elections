package core;

import java.io.*;
import java.util.ArrayList;

public class Region {
    private String name;
    private ArrayList<District> districts;
    private static ArrayList<Region> regionsExistantes = new ArrayList<>();
    private final static String filePath = "data/Region.txt";

    private Region(String name) {
        this.name = name;
        this.districts = new ArrayList<>();
    }

    public static Region get(String name) {
        if (regionExists(name)) {
            for (Region region : regionsExistantes) {
                if (region.getName().equals(name))
                    return region;
            }
            return null; // Ne devrait pas arriver
        } else {
            Region newRegion = new Region(name);
            addToExistingRegions(newRegion);
            return newRegion;
        }
    }

    private static boolean regionExists(String name) {
        for (Region region : regionsExistantes) {
            if (region.getName().equals(name))
                return true;
        }
        return false;
    }

    private static void addToExistingRegions(Region region) {
        regionsExistantes.add(region);
    }

    public String getName() {
        return name;
    }

    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void addDistrict(District district) {
        districts.add(district);
    }

    public static void traiterData(){
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null){
                traiterLigneData(line);
            }
        } catch (Exception e) {
            System.out.println("fichier " + filePath + " impossible à lire");
        }
    }

    public static void traiterLigneData(String line){   
        // format: region:district01;district02;etc...
        String[] infos = line.split(":");
        if (infos.length != 2) return;

        String regionName = infos[0];
        Region region = Region.get(regionName);

        String[] nomsDistricts = infos[1].split(";");
        traiterDistricts(nomsDistricts, region);
    }

    public static void traiterDistricts(String[] nomsDistricts, Region region){
        for (String districtName : nomsDistricts){
            District district = District.get(districtName);
            district.setRegion(region);
            region.addDistrict(district);
        }
    }

    public static ArrayList<Region> getAll(){
        return regionsExistantes;        
    }

    @Override public String toString(){
        return "Region : " + this.name;
    }

    public static Region allChoicesParameter(){
        return new Region("TOUS");
    }
}
