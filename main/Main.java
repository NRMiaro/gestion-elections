package main;

import java.util.Map;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import core.*;
import ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        traiterData();
        for (Commune o : Commune.getAll()){
            for (Candidat c : o.getWinners())
                System.out.println(c.getName());
        }

        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        new Fenetre();
    }

    public static void traiterData(){
        Region.traiterData();
        District.traiterData();
        Commune.traiterData();
        Candidat.traiterData();
        BureauVote.traiterData();
    }
}
