package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.util.*;
import listener.ValidationListener;

public class Fenetre extends JFrame {
    private Formulaire form;
    private ResultPanel middle;

    public Fenetre(){
        setUIFont(new FontUIResource("Cascadia Code", Font.PLAIN, 18));
        setTitle("Elections");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        form = new Formulaire(); 
        middle = new ResultPanel(form); 

        // layout de la fenetre
        setLayout(new BorderLayout());
        add(form, BorderLayout.NORTH);
        add(middle, BorderLayout.CENTER);

        JButton validationBtn = form.getBtn();
        validationBtn.addActionListener(new ValidationListener(validationBtn, middle));

        setVisible(true);
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
