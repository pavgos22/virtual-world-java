package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.world.Point;

import javax.swing.*;
import java.awt.*;

public class Wolf extends Animal {
    private static final int wolfMoveRange = 1;
    private static final int wolfMoveChance = 1;
    private static final int wolfStrength = 9;
    private static final int wolfInitiative = 5;

    public Wolf(World world, Point position, int birthRound) {
        super(OrganismType.WOLF, world, position, birthRound, wolfStrength, wolfInitiative);
        this.setMoveRange(wolfMoveRange);
        this.setMoveChance(wolfMoveChance);
        setColor(new Color(70, 70, 70));
        ImageIcon icon = new ImageIcon("src/images/wolf-icon.png");
        setIcon(icon);
    }

    @Override
    public String organismTypeToString() {
        return "Wolf";
    }

    @Override
    public String toString() {
        return "Wolf";
    }
}
