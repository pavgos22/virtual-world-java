package pl.edu.pg.eti.ksg.po.project2.world;

import javax.swing.*;
import java.awt.*;

public class BoardField extends JButton {
    private boolean isEmpty;
    private Color color;
    private final int posX;
    private final int posY;

    public BoardField(int X, int Y) {
        super();
        this.posX = X;
        this.posY = Y;
        this.color = Color.WHITE;
        setBackground(color);
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
