package pl.edu.pg.eti.ksg.po.project2.model.plants;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Plant;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.util.Random;
import java.awt.*;

public class DeadlyNightshade extends Plant {
    private static final int deadlyNightshadeStrength = 99;
    private static final int deadlyNightShadeInitiative = 0;

    public DeadlyNightshade(World world, Point position, int birthRound) {
        super(OrganismType.DEADLY_NIGHTSHADE, world, position, birthRound, deadlyNightshadeStrength, deadlyNightShadeInitiative);
        setColor(new Color(82, 92, 232));
        ImageIcon icon = new ImageIcon("src/images/deadly_nightshade-icon.png");
        setIcon(icon);
        setReproductionChance(0.05);
    }


    @Override
    public void action() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpRand = rand.nextInt(upperbound);
        if (tmpRand < getReproductionChance() * 100) spreading();
    }

    @Override
    public String organismTypeToString() {
        return "Deadly Nightshade";
    }

    @Override
    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        Narrator.addComment(predator.ToString() + " eats " + this.ToString());
        if (predator.getStrength() >= 99) {
            getWorld().removeOrganism(this);
            Narrator.addComment(predator.ToString() + " destroys a deadly nightshade bush");
        }
        else if (predator.isAnimal()) {
            getWorld().removeOrganism(predator);
            Narrator.addComment(predator.ToString() + " got poisoned with deadly nightshade and died");
        }
        return true;
    }
}
