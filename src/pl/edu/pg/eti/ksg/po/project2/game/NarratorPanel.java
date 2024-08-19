package pl.edu.pg.eti.ksg.po.project2.game;

import javax.swing.*;
import java.awt.*;

public class NarratorPanel extends JPanel {
    private String text;
    private final JTextArea textArea;

    public NarratorPanel(JPanel mainPanel, int SPACE) {
        super();
        setBounds(mainPanel.getX() + SPACE, mainPanel.getY() + SPACE,
                mainPanel.getHeight() * 3 / 7 - SPACE, mainPanel.getHeight() * 5 / 6 - SPACE);
        Font font = new Font("Verdana", Font.BOLD, 12);
        text = Narrator.getText();
        textArea = new JTextArea(text);
        textArea.setEditable(false);
        setLayout(new CardLayout());
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setForeground(Color.BLACK);
        JScrollPane sp = new JScrollPane(textArea);
        add(sp);
    }

    public void refreshComments() {
        String instriction = "VIRTUAL WORLD 2022\nMovement - arrows\n" +
                "Special ability [P]\nNext round [ENTER]\n";
        text = instriction + Narrator.getText();
        textArea.setText(text);
        textArea.setForeground(Color.BLACK);
    }
}
