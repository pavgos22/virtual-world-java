package pl.edu.pg.eti.ksg.po.project2.game;

import javax.swing.*;
import java.awt.*;

public class Legend extends JPanel {

    public Legend(BoardPanel boardPanel, JPanel mainPanel, int SPACE) {
        super();
        setBounds(boardPanel.getX() + boardPanel.getWidth() + SPACE, mainPanel.getY() + SPACE,
                boardPanel.getWidth() / 4 - SPACE * 2,
                boardPanel.getHeight());
        setBackground(Color.WHITE);
        Font font = new Font("Verdana", Font.BOLD, 14);
        Dimension maxSize = new Dimension(30, 60);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        int TYPES = 11;
        JButton[] iconButtons = new JButton[TYPES];
        JTextArea[] textAreas = new JTextArea[TYPES];

        ImageIcon icon1 = new ImageIcon("src/images/human-icon.png");
        ImageIcon icon2 = new ImageIcon("src/images/antelope-icon.png");
        ImageIcon icon3 = new ImageIcon("src/images/sheep-icon.png");
        ImageIcon icon4 = new ImageIcon("src/images/fox-icon.png");
        ImageIcon icon5 = new ImageIcon("src/images/turtle-icon.png");
        ImageIcon icon6 = new ImageIcon("src/images/wolf-icon.png");
        ImageIcon icon7 = new ImageIcon("src/images/grass-icon.png");
        ImageIcon icon8 = new ImageIcon("src/images/dandelion-icon.png");
        ImageIcon icon9 = new ImageIcon("src/images/guarana-icon.png");
        ImageIcon icon10 = new ImageIcon("src/images/deadly_nightshade-icon.png");
        ImageIcon icon11 = new ImageIcon("src/images/giant_hogweed-icon.png");

        textAreas[0] = new JTextArea("Human");
        textAreas[1] = new JTextArea("Antelope");
        textAreas[2] = new JTextArea("Sheep");
        textAreas[3] = new JTextArea("Fox");
        textAreas[4] = new JTextArea("Turtle");
        textAreas[5] = new JTextArea("Wolf");
        textAreas[6] = new JTextArea(" Grass");
        textAreas[7] = new JTextArea("Dandelion");
        textAreas[8] = new JTextArea("Guarana");
        textAreas[9] = new JTextArea("Nightshade");
        textAreas[10] = new JTextArea("Hogweed");

        iconButtons[0] = new JButton(icon1);
        iconButtons[1] = new JButton(icon2);
        iconButtons[2] = new JButton(icon3);
        iconButtons[3] = new JButton(icon4);
        iconButtons[4] = new JButton(icon5);
        iconButtons[5] = new JButton(icon6);
        iconButtons[6] = new JButton(icon7);
        iconButtons[7] = new JButton(icon8);
        iconButtons[8] = new JButton(icon9);
        iconButtons[9] = new JButton(icon10);
        iconButtons[10] = new JButton(icon11);

        iconButtons[0].setBackground(new Color(0, 0, 0));
        iconButtons[1].setBackground(new Color(138, 63, 48));
        iconButtons[2].setBackground(new Color(203, 203, 203));
        iconButtons[3].setBackground(new Color(255, 106, 31));
        iconButtons[4].setBackground(new Color(25, 114, 10));
        iconButtons[5].setBackground(new Color(70, 70, 70));
        iconButtons[6].setBackground(new Color(24, 240, 24));
        iconButtons[7].setBackground(new Color(245, 238, 57));
        iconButtons[8].setBackground(new Color(232, 59, 59));
        iconButtons[9].setBackground(new Color(82, 92, 232));
        iconButtons[10].setBackground(new Color(164, 0, 223));

        for (int i = 0; i < TYPES; i++) {
            iconButtons[i].setEnabled(false);
            textAreas[i].setEnabled(false);
            iconButtons[i].setSize(maxSize);
            textAreas[i].setFont(font);
            textAreas[i].setForeground(Color.BLACK);

            add(iconButtons[i]);
            add(textAreas[i]);
        }
    }
}
