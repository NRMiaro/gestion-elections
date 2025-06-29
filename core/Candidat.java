package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Candidat {
    private String name;
    private static ArrayList<Candidat> candidatsExistants = new ArrayList<>();
    private static final String filePath = "data/Candidat.txt";

    private Candidat(String name) {
        this.name = name.trim();
    }

    public static Candidat get(String name) {
        name = name.trim();
        for (Candidat c : candidatsExistants) {
            if (c.getName().equalsIgnoreCase(name))
                return c;
        }
        Candidat newCandidat = new Candidat(name);
        candidatsExistants.add(newCandidat);
        return newCandidat;
    }

    public String getName() { return name; }

    // Récupère toutes les communes où le candidat est présent via les bureaux de vote
    public Set<Commune> getCommunes() {
        Set<Commune> communes = new HashSet<>();
        for (BureauVote bv : BureauVote.getAll()) {
            if (bv.getCandidats().contains(this)) {
                communes.add(bv.getCommune());
            }
        }
        return communes;
    }

    public static void traiterData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                traiterDataLigne(line);
            }
        } catch (Exception e) {
            System.out.println("fichier " + filePath + " impossible à lire");
        }
    }

    private static void traiterDataLigne(String line) {
        // format : candidat->BV
        String[] infos = line.split("->");
        if (infos.length != 2) return;

        String candidatName = infos[0].trim();
        String bvCode = infos[1].trim();

        Candidat candidat = Candidat.get(candidatName);
        BureauVote bv = BureauVote.get(bvCode);

        bv.addCandidat(candidat);
    }

    public static ArrayList<Candidat> getAll() {
        return candidatsExistants;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Candidat))
            return false;
        Candidat c = (Candidat) o;
        return name.equalsIgnoreCase(c.getName());
    }

    // Votes total sur tous les bureaux de vote où le candidat est présent
    public int getVotes() {
        int total = 0;
        for (BureauVote bv : BureauVote.getAll()) {
            total += bv.getVotesOf(this);
        }
        return total;
    }

    // Votes du candidat dans une commune donnée (somme des bureaux de vote dans cette commune)
    public int getVotesAt(Commune commune) {
        int total = 0;
        for (BureauVote bv : commune.getBV()) {
            total += bv.getVotesOf(this);
        }
        return total;
    }
}
