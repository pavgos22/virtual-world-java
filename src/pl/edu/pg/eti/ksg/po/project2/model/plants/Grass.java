package pl.edu.pg.eti.ksg.po.project2.model.plants;

import pl.edu.pg.eti.ksg.po.project2.model.Plant;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;

import javax.swing.*;
import java.awt.*;

public class Grass extends Plant {
    private static final int grassStrength = 0;
    private static final int grassInitiative = 0;

    public Grass(World world, Point position, int birthRound) {
        super(OrganismType.GRASS, world, position, birthRound, grassStrength, grassInitiative);
        setColor(new Color(24, 240, 24));
        ImageIcon icon = new ImageIcon("src/images/grass-icon.png");
        setIcon(icon);
    }

    @Override
    public String organismTypeToString() {
        return "Grass";
    }
}
