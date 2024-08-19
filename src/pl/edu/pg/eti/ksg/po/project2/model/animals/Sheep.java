package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.world.Point;

import javax.swing.*;
import java.awt.*;

public class Sheep extends Animal {
    private static final int sheepMoveRange = 1;
    private static final int sheepMoveChance = 1;
    private static final int sheepStrength = 4;
    private static final int sheepInitiative = 4;

    public Sheep(World world, Point pos, int birthRound) {
        super(OrganismType.SHEEP, world, pos, birthRound, sheepStrength, sheepInitiative);
        this.setMoveRange(sheepMoveRange);
        this.setMoveChance(sheepMoveChance);
        setColor(new Color(203, 203, 203));
        ImageIcon icon = new ImageIcon("src/images/sheep-icon.png");
        setIcon(icon);
    }

    @Override
    public String organismTypeToString() {
        return "Sheep";
    }

    @Override
    public String toString() {
        return "Sheep";
    }
}
