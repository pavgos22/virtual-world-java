package pl.edu.pg.eti.ksg.po.project2.game;

import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.world.BoardField;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.world.WorldGUI;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final int sizeX;
    private final int sizeY;
    private final BoardField[][] boardFields;
    private final World world;
    private final WorldGUI worldGUI;

    public BoardPanel(World world, WorldGUI worldGUI, JPanel mainPanel, int SPACE, int BIG_SPACE, int ADDITIONAL_WIDTH) {
        super();
        this.world = world;
        this.worldGUI = worldGUI;
        this.sizeX = world.getSizeX();
        this.sizeY = world.getSizeY();

        setBounds(BIG_SPACE + ADDITIONAL_WIDTH + SPACE,
                mainPanel.getY() + SPACE,
                (mainPanel.getWidth() - ADDITIONAL_WIDTH) * 5 / 6 - SPACE * 4,
                mainPanel.getHeight() * 5 / 6 - SPACE);

        boardFields = new BoardField[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                boardFields[i][j] = new BoardField(j, i);
                boardFields[i][j].addActionListener(event -> {
                    if (event.getSource() instanceof BoardField) {
                        BoardField tmpPole = (BoardField) event.getSource();
                        if (tmpPole.isEmpty()) {
                            new Organisms(
                                    tmpPole.getX() + mainPanel.getX(),
                                    tmpPole.getY() + mainPanel.getY(),
                                    new Point(tmpPole.getPosX(), tmpPole.getPosY()),
                                    world,
                                    worldGUI::refreshWorld
                            );
                        }
                    }
                });
            }
        }

        setLayout(new GridLayout(sizeY, sizeX));
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                add(boardFields[i][j]);
            }
        }
    }

    public void refreshBoard() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Organism tmpOrganism = world.getBoard()[i][j];
                if (tmpOrganism != null) {
                    boardFields[i][j].setEmpty(false);
                    boardFields[i][j].setEnabled(false);
                    boardFields[i][j].setColor(tmpOrganism.getColor());
                    if (sizeX < 42 && sizeY < 42)
                        boardFields[i][j].setIcon(tmpOrganism.getIcon());
                } else {
                    boardFields[i][j].setEmpty(true);
                    boardFields[i][j].setEnabled(true);
                    boardFields[i][j].setColor(Color.WHITE);
                    boardFields[i][j].setIcon(null);
                }
            }
        }
    }
}
