package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Commune {
    private String name;
    private District district;
    private ArrayList<BureauVote> bureauxDeVote = new ArrayList<>();
    private ArrayList<Candidat> candidats = new ArrayList<>();

    private static final Map<String, Commune> communesExistantes = new HashMap<>();
    private static final String filePath = "data/Commune.txt";

    private Commune(String name) {
        this.name = name.trim();
    }

    public static Commune get(String name, String districtName) {
        name = name.trim();
        districtName = districtName.trim();
        String cle = name.toLowerCase() + "@" + districtName.toLowerCase();

        if (communesExistantes.containsKey(cle)) 
            return communesExistantes.get(cle);

        Commune commune = new Commune(name);
        communesExistantes.put(cle, commune);
        return commune;
    }

    public String getName() { return name; }
    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public ArrayList<Candidat> getCandidats() { return candidats; }
    public void addCandidat(Candidat c) {
        if (!candidats.contains(c)) {
            candidats.add(c);
        }
    }

    public void addBV(BureauVote bv) {
        if (!bureauxDeVote.contains(bv)) {
            bureauxDeVote.add(bv);
        }
    }

    public ArrayList<BureauVote> getBV() { return bureauxDeVote; }

    public static ArrayList<Commune> getAll() {
        return new ArrayList<>(communesExistantes.values());
    }

    @Override
    public String toString() {
        return "Commune : " + name;
    }

    public static Commune allChoicesParameter() {
        return new Commune("TOUS");
    }

    // utilise la méthode getVotesAt pour récupérer les votes de chaque candidat dans la commune
    public Candidat[] getWinners() {
        int nbCandidats = candidats.size();
        if (nbCandidats == 0) return new Candidat[0];

        Candidat first = null;
        Candidat second = null;

        for (Candidat c : candidats) {
            int votes = c.getVotesAt(this);
            if (first == null || votes > first.getVotesAt(this)) {
                second = first;
                first = c;
            } else if (second == null || votes > second.getVotesAt(this)) {
                second = c;
            }
        }

        if (second == null || second.getVotes() * 2 < first.getVotes()) {
            return new Candidat[]{first};
        } else {
            return new Candidat[]{first, second};
        }
    }
}
