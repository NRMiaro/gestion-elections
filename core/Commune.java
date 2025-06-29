package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Commune {
    private String name;
    private District district;
    private ArrayList<BureauVote> bureauxDeVote = new ArrayList<>();

    private ArrayList<Candidat> candidats = new ArrayList<>();
    private static ArrayList<Commune> communesExistantes = new ArrayList<>();
    private static String filePath = "data/Commune.txt";

    private Commune(String name) {
        this.name = name;
    }

    public static Commune get(String name) {
        for (Commune c : communesExistantes) {
            if (c.getName().equals(name)) 
                return c;
        }
        Commune newCommune = new Commune(name);
        communesExistantes.add(newCommune);
        return newCommune;
    }

    public String getName() { return name; }
    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }
    public ArrayList<Candidat> getCandidats() { return candidats; }
    public void addCandidat(Candidat c){
        if (!candidats.contains(c))
            candidats.add(c);
    }
    public void addBV(BureauVote bv) { bureauxDeVote.add(bv); }
    public ArrayList<BureauVote> getBV() { return bureauxDeVote; }
    public static ArrayList<Commune> getAll(){ return communesExistantes; }

    public static void traiterData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                traiterLigneData(line);
            }
        } catch (Exception e) {
            System.out.println("fichier " + filePath + " impossible à lire");
        }
    }

    private static void traiterLigneData(String line){
    String[] infos = line.split(":");
    String communeAndDistrictNames = infos[0];
    String communeName = communeAndDistrictNames.split(";")[0];
    String districtName = communeAndDistrictNames.split(";")[1];

    Commune commune = Commune.get(communeName); // ✅ toujours récupérer l’unique instance
    District district = District.get(districtName);

    commune.setDistrict(district);     // 🔁 même si c’est un set répétitif, c’est sûr
    district.addCommune(commune);      // 🔁 ajoute-la bien au district aussi si besoin

    String[] bvCodes = infos[1].split(";");
    traiterBV(bvCodes, commune);
}


    private static void traiterBV(String[] bvCodes, Commune commune){
        for (String bvCode : bvCodes){
            BureauVote bv = BureauVote.get(bvCode);
            commune.addBV(bv);
            bv.setCommune(commune);
        }
    }

    @Override public String toString(){
        return "Commune : " + name;
    }

    public static Commune allChoicesParameter(){
        return new Commune("TOUS");
    }

    public Candidat[] getWinners(){
        int nbCandidats = candidats.size();
        if (nbCandidats == 0) return new Candidat[0];

        Candidat first = null;
        Candidat second = null;

        for (Candidat c : candidats){
            if (first == null || c.getVotesAt(this) > first.getVotesAt(this)) {
                second = first;
                first = c;
            } else if (second == null || c.getVotesAt(this) > second.getVotesAt(this)) {
                second = c;
            }
        }

        if (second == null || second.getVotes() * 2 < first.getVotes()){
            return new Candidat[]{first};
        } else {
            return new Candidat[]{first, second};
        }
    }
}
