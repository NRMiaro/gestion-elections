package ui;

import javax.swing.*;
import java.awt.*;
import core.*;

public class ResultPanel extends JPanel {
    private Formulaire form;
    private ResultTable table;
    private JTextArea infoText;
    private JScrollPane scrollPane;

    public ResultPanel(Formulaire form){
        this.form = form;
        setLayout(new BorderLayout());

        table = new ResultTable(form);
        infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoText.setMargin(new Insets(10, 10, 10, 10));

        scrollPane = new JScrollPane(table);

        add(infoText, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // par défaut on montre le tableau
    }

    public void refreshResults(){
        // 1. MAJ du tableau
        table.refresh();

        // 2. Affichage texte
        Region r = form.getRegion();
        District d = form.getDistrict();
        Commune c = form.getCommune();

        StringBuilder sb = new StringBuilder();
        if (!r.getName().equals("TOUS"))
            sb.append("Région   : ").append(r.getName()).append("\n");
        if (!d.getName().equals("TOUS"))
            sb.append("District : ").append(d.getName()).append("\n");
        if (!c.getName().equals("TOUS"))
            sb.append("Commune  : ").append(c.getName()).append("\n");

        boolean isExactSelection = 
            !r.getName().equals("TOUS") &&
            !d.getName().equals("TOUS") &&
            !c.getName().equals("TOUS");

        if (isExactSelection) {
            Candidat[] elus = c.getWinners();
            sb.append("Élu(s) : ");
            if (elus.length == 0) {
                sb.append("aucun");
            } else {
                for (int i = 0; i < elus.length; i++) {
                    sb.append(elus[i].getName());
                    sb.append(i == 0 ? " (premier)" : " (deuxième)");
                    sb.append(elus[i].getVotes() + " votes");
                    if (i < elus.length - 1) sb.append(", ");
                }
            }
        }
        if (!sb.toString().isEmpty())
            infoText.setText(sb.toString());

        // 3. Afficher ou masquer le tableau
        boolean afficherTableau = 
            r.getName().equals("TOUS") || 
            d.getName().equals("TOUS") || 
            c.getName().equals("TOUS");

        if (afficherTableau) {
            if (scrollPane.getParent() == null) 
                this.add(scrollPane, BorderLayout.CENTER);
        } else this.remove(scrollPane); // supprime le tableau si affiché
        

        revalidate();
        repaint();
    }
}
