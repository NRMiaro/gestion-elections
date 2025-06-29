package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class BureauVote {
    private String code;
    private Commune commune;
    private HashMap<Candidat, Integer> votesMap;
    private static ArrayList<BureauVote> bureauxExistants = new ArrayList<>();
    private static final String filePath = "data/BureauVote.txt";

    private BureauVote(String code) {
        this.code = code.trim();
        this.votesMap = new HashMap<>();
    }

    public static BureauVote get(String code) {
        code = code.trim();
        for (BureauVote bv : bureauxExistants) {
            if (bv.getCode().equals(code)) {
                return bv;
            }
        }
        BureauVote bv = new BureauVote(code);
        bureauxExistants.add(bv);
        return bv;
    }

    public String getCode() {
        return code;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public Set<Candidat> getCandidats() {
        return votesMap.keySet();
    }

    public Map<Candidat, Integer> getVotesMap() {
        return votesMap;
    }

    public void addCandidat(Candidat candidat) {
        votesMap.putIfAbsent(candidat, 0);
        if (!this.commune.getCandidats().contains(candidat))
            commune.addCandidat(candidat);
    }

    public void setVotesCandidat(Candidat c, int votes) {
        votesMap.put(c, votes);
    }

    public int getVotesOf(Candidat c) {
        return votesMap.getOrDefault(c, 0);
    }

    public static ArrayList<BureauVote> getAll() {
        return bureauxExistants;
    }

    public static void traiterData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                traiterLigneData(line);
            }
        } catch (Exception e) {
            System.out.println("fichier " + filePath + " impossible à lire");
        }
    }

    private static void traiterLigneData(String line) {
        String[] infos = line.split(":");
        if (infos.length != 2) return;

        String bvCode = infos[0].trim();
        String[] candidatVotes = infos[1].split("->");
        if (candidatVotes.length != 2) return;

        String candidatName = candidatVotes[0].trim();
        int votes;
        try {
            votes = Integer.parseInt(candidatVotes[1].trim());
        } catch (NumberFormatException e) {
            votes = 0;
        }

        BureauVote bv = BureauVote.get(bvCode);
        Candidat candidat = Candidat.get(candidatName);

        bv.addCandidat(candidat);
        bv.setVotesCandidat(candidat, votes);
    }
}
