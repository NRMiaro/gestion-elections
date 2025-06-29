package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class BureauVote {
    private String code;
    private Commune commune;
    // private ArrayList<Candidat> candidats = new ArrayList<>();
    private HashMap<Candidat, Integer> votesMap;
    private static ArrayList<BureauVote> bureauxExistants = new ArrayList<>();
    private static final String filePath = "data/BureauVote.txt";

    public static BureauVote get(String code){
        if (bureauExists(code)){
            for (BureauVote bv : bureauxExistants){
                if (bv.getCode().equals(code))
                    return bv;
            }
            return null; // ne devrait pas arriver
        } else {
            BureauVote bv = new BureauVote(code);
            addNewBureau(bv);
            return bv;
        }
    }

    private BureauVote(String code){
        this.votesMap = new HashMap<>();
        this.code = code;
    }

    public String getCode(){ return this.code; }
    public Commune getCommune() { return this.commune; }
    public Set<Candidat> getCandidats(){ return votesMap.keySet(); }
    public Map<Candidat, Integer> getVotesMap(){ return votesMap; }
    public void setCommune(Commune commune){ this.commune = commune; }
    public void addCandidat(Candidat candidat) { this.votesMap.put(candidat, 0); }

    public static void addNewBureau(BureauVote bv){
        bureauxExistants.add(bv);
    }

    public static boolean bureauExists(String code){
        for (BureauVote bv : bureauxExistants){
            if (bv.getCode().equals(code))
                return true;
        }
        return false;
    }

    public static ArrayList<BureauVote> getAll() { return bureauxExistants; }

    public void setVotesCandidat(Candidat c, int votes){
        this.votesMap.put(c, votes);
    }
    public int getVotesOf(Candidat c){
        return votesMap.getOrDefault(c, 0);
    }

    public static void traiterData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null){
                traiterLigneData(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void traiterLigneData(String line){
        String[] infos = line.split(":");
        if (infos.length != 2) return;

        String[] infosCandidat = infos[1].split("->");
        if (infosCandidat.length != 2) return;

        String bvCode = infos[0];
        String candidatName = infosCandidat[0];
        int votes = Integer.parseInt(infosCandidat[1]);

        BureauVote bv = BureauVote.get(bvCode);
        Candidat candidat = Candidat.get(candidatName);
        bv.addCandidat(candidat);
        bv.setVotesCandidat(candidat, votes);
    }
}
