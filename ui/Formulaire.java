package ui;

import java.util.ArrayList;
import javax.swing.*;
import core.*;
import listener.*;

public class Formulaire extends JPanel {
    private RegionResearch regionResearch;
    private DistrictResearch districtResearch;
    private CommuneResearch communeResearch;
    private JButton btn;
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();

    public Formulaire(){
        regionResearch = new RegionResearch();
        districtResearch = new DistrictResearch();
        communeResearch = new CommuneResearch();
        this.add(regionResearch);
        this.add(districtResearch);
        this.add(communeResearch);
        districtResearch.updateChoices(regionResearch.getSelectedRegion());
        communeResearch.updateChoices(districtResearch.getSelectedDistrict());

        btn = new JButton("Rechercher");
        this.add(btn);

        regionResearch.addActionListener(new RegionListener(regionResearch, districtResearch, communeResearch));
        districtResearch.addActionListener(new DistrictListener(districtResearch, communeResearch));
    }

    public ArrayList<ArrayList<Object>> getData(){ 
        return this.data;
    }

    public Region getRegion(){ return regionResearch.getSelectedRegion(); }
    public District getDistrict(){ return districtResearch.getSelectedDistrict(); }
    public Commune getCommune(){ return communeResearch.getSelectedCommune(); }
    public JButton getBtn(){
        return this.btn;
    }

    public ArrayList<String> getColumns(){
        ArrayList<String> columns = new ArrayList<String>();

        Region region = getRegion();
        District district = getDistrict();
        Commune commune = getCommune();

        columns.add("Vainqueur(s)");
        if (commune.getName().equals("TOUS"))
            columns.add(0, "Communes");
        if (district.getName().equals("TOUS"))
            columns.add(0, "Districts");
        if (region.getName().equals("TOUS"))
            columns.add(0, "Régions");

        return columns;
    }

    public void updateData(){
        this.data.clear();

        Region region = getRegion(); System.out.println(region);
        District district = getDistrict(); System.out.println(district);
        Commune commune = getCommune(); System.out.println(commune);

        if (!commune.getName().equals("TOUS")){
            // System.out.println("data de la commune : " + commune.getName() + " entré");
            addData(commune);
        } else if (!district.getName().equals("TOUS")){
            for (Commune c : district.getCommunes())
            addData(c);
        } else if (!region.getName().equals("TOUS")){
            System.out.println(">> Région sélectionnée : " + region.getName());
            System.out.println(">> Nombre de districts : " + region.getDistricts().size()); // << ICI

            for (District d : region.getDistricts()){
                for (Commune c : d.getCommunes())
                    addData(c);
            }
        } else {
            for (Commune c : Commune.getAll())
                addData(c);
        }

        // for (ArrayList<Object> o : data){
        //     System.out.println("********");
        //     for (Object col : o)
        //         System.out.println(col);
        // }
        // System.out.println("fin\n\n");
    }

    private void addData(Commune commune){
        // System.out.println("Traitement des data de " + commune.getName());
        Candidat[] elus = commune.getWinners();
        System.out.println(elus.length);

        boolean first = true;
        for (Candidat c : elus) {
            System.out.println("élu : " + c.getName());
            ArrayList<Object> line = new ArrayList<>();

            if (getRegion().getName().equals("TOUS"))
                line.add(commune.getDistrict().getRegion().getName());
            if (getDistrict().getName().equals("TOUS"))
                line.add(commune.getDistrict().getName());
            if (getCommune().getName().equals("TOUS"))
                line.add(commune.getName());
            if (first){
                line.add(c.getName() + " (premier)");
                first = false;
            }
            else line.add(c.getName() + " (deuxième)");
            this.data.add(line);
        }
    }
}
