package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class Candidat {
    private String name;
    private Commune commune;
    private static ArrayList<Candidat> candidatsExistants = new ArrayList<>();
    private static String filePath = "data/Candidat.txt";

    private Candidat(String name) { this.name = name; }

    public static Candidat get(String name){
        if (candidatExist(name)){
            for (Candidat c : candidatsExistants){
                if (c.getName().equals(name))
                    return c;
            }
            return null;
        } else {
            Candidat candidat = new Candidat(name);
            addNewExistingCandidat(candidat);
            return candidat;
        }
    }

    public String getName() { return name; }
    public Commune getCommune() { return commune; }
    public static void addNewExistingCandidat(Candidat newCandidat){ 
        candidatsExistants.add(newCandidat);
    }

    public static boolean candidatExist(String candidatName){
        for (Candidat c : candidatsExistants){
            if (c.getName().equals(candidatName))
                return true;
        }
        return false;
    }

    public void setCommune(Commune commune) { this.commune = commune; }

    public static void traiterData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null){
                traiterDataLigne(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void traiterDataLigne(String line){
        String[] infos = line.split("->");
        if (infos.length != 2) return;

        String candidatName = infos[0];
        String bvCode = infos[1];
        
        Candidat candidat = Candidat.get(candidatName);
        BureauVote bv = BureauVote.get(bvCode);
        Commune commune = bv.getCommune();

        bv.addCandidat(candidat); // ajouter le candidat au bv
        candidat.setCommune(commune); // poser la commune du candidat
        commune.addCandidat(candidat); // ajouter un candidat à la commune
    }

    public static ArrayList<Candidat> getAll(){ return candidatsExistants; }

    @Override public int hashCode(){
        return Objects.hash(name);
    }

    @Override public boolean equals(Object o){
        if (this == o) 
            return true;
        if (o == null || !(o instanceof Candidat)) 
            return false;

        Candidat c = (Candidat) o;
        if (name.equals(c.getName())) 
            return true;
        else return false;
    }

    public int getVotes(){
        int total = 0;
        for (BureauVote bv : this.commune.getBV()){
            total += bv.getVotesOf(this);
        }
        return total;
    }

    public int getVotesAt(Commune communeCible){
        int total = 0;
        for (BureauVote bv : communeCible.getBV()){
            total += bv.getVotesOf(this);
        }
        return total;
    }
}
