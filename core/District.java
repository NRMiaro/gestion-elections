package core;

import java.io.*;
import java.util.ArrayList;

public class District {
    private String name;
    private Region region;
    private ArrayList<Commune> communes = new ArrayList<>();
    private static ArrayList<District> districtsExistantes = new ArrayList<>();
    private static String filePath = "data/District.txt";

    private District(String name) {
        this.name = name;
    }

    public static District get(String name) {
        for (District district : districtsExistantes) {
            if (district.getName().equals(name)) 
                return district;
        }
        District newDistrict = new District(name);
        districtsExistantes.add(newDistrict);
        return newDistrict;
    }

    public String getName() { return name; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public ArrayList<Commune> getCommunes() { return communes; }
    public void addCommune(Commune c) {
        if (!communes.contains(c))
            communes.add(c);
    }

    public static void traiterData() {
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
        String[] infos = line.split(":");
        if (infos.length != 2) return;

        String[] nomEtRegion = infos[0].split(";");
        if (nomEtRegion.length != 2) return;

        String districtName = nomEtRegion[0];

        District district = District.get(districtName);

        String[] communeNames = infos[1].split(";");
        traiterCommunes(communeNames, district);
    }

    public static void traiterCommunes(String[] communeNames, District district){
        for (String communeName : communeNames) {
            Commune commune = Commune.get(communeName.trim());
            district.addCommune(commune);
            commune.setDistrict(district);
        }
    }

    public static ArrayList<District> getAll(){
        return districtsExistantes;
    }

    @Override public String toString(){
        return "District : " + name;
    }

    public static District allChoicesParameter(){
        return new District("TOUS");
    }
}
