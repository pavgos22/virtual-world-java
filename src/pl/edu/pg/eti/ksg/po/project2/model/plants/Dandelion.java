package pl.edu.pg.eti.ksg.po.project2.model.plants;

import pl.edu.pg.eti.ksg.po.project2.model.Plant;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;

import javax.swing.*;
import java.util.Random;
import java.awt.*;

public class Dandelion extends Plant {
    private static final int dandelionStrength = 0;
    private static final int dandelionInitiative = 0;
    private static final int tries = 3;

    public Dandelion(World world, Point position, int birthRound) {
        super(OrganismType.DANDELION, world, position,
                birthRound, dandelionStrength, dandelionInitiative);
        setColor(new Color(245,238,57));
        ImageIcon icon = new ImageIcon("src/images/dandelion-icon.png");
        setIcon(icon);
    }

    @Override
    public void action() {
        Random rand = new Random();
        for (int i = 0; i < tries; i++) {
            int tmpRand = rand.nextInt(100);
            if (tmpRand < getReproductionChance()) spreading();
        }
    }

    @Override
    public String organismTypeToString() {
        return "Dandelion";
    }
}
