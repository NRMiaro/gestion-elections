package main;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import core.*;
import ui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        traiterData();
        // for (Region o : Region.getAll()){
        //     System.out.println(o);
        //     for (District d : o.getDistricts()){
        //         System.out.println("---" + d.getName());
        //         for (Commune c : d.getCommunes()){
        //             System.out.println(c.getWinners().length);
        //             System.out.println("------" + c.getName());
        //         }
        //     }
        // }

        // for (BureauVote d : BureauVote.getAll())
        //     System.out.println(d.getCode());

        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        for (Commune c : Commune.getAll()){
            System.out.println(c.getWinners().length);
        }
        
        new Fenetre();
    }

    public static void traiterData(){
        Utils.traiterData();

        Candidat.traiterData();
        BureauVote.traiterData();
    }
}
