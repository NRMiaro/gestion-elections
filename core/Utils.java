package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Utils {
    private static final String filePath = "data/Data.txt";

    public static void traiterData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                traiterLigneData(line);
            }
        } catch (Exception e) {
            System.out.println("Impossible de lire le ficher : " + filePath);
        }
    }

    private static void traiterLigneData(String line){
        // format: region->district->commune:bv;bv;bv;...

        if (line.isEmpty()) return;
        String[] parts = line.split(":");
        if (parts.length != 2) return;

        String[] hierarchy = parts[0].split("->");
        if (hierarchy.length != 3) return;

        String regionName = hierarchy[0].trim();
        String districtName = hierarchy[1].trim();
        String communeName = hierarchy[2].trim();

        Region region = Region.get(regionName);
        District district = District.get(districtName, regionName);
        region.addDistrictIfAbsent(district);
        district.setRegion(region);

        Commune commune = Commune.get(communeName, districtName);
        commune.setDistrict(district);
        district.addCommuneIfAbsent(commune);

        String[] bvCodes = parts[1].split(";");
        for (String bvCode : bvCodes) {
            BureauVote bv = BureauVote.get(bvCode);
            commune.addBV(bv);
            bv.setCommune(commune);
        }
    }

}
