package listener;

import java.awt.event.*;
import javax.swing.*;

import ui.ResultPanel;

public class ValidationListener implements ActionListener {
    JButton btn;
    ResultPanel resultPanel;

    public ValidationListener(JButton onClickButton, ResultPanel resultPanel){
        this.btn = onClickButton;
        this.resultPanel = resultPanel;
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (!e.getSource().equals(btn)) return;

        resultPanel.refreshResults();
    }
    
}
