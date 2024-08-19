package pl.edu.pg.eti.ksg.po.project2.model.plants;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Plant;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.awt.*;

public class Guarana extends Plant {
    private static final int strengthIncrease = 3;
    private static final int guaranaStrength = 0;
    private static final int guaranaInitiative = 0;

    public Guarana(World world, Point position, int birthRound) {
        super(OrganismType.GUARANA, world, position,
                birthRound, guaranaStrength, guaranaInitiative);
        setColor(new Color(232,59,59));
        ImageIcon icon = new ImageIcon("src/images/guarana-icon.png");
        setIcon(icon);
    }

    @Override
    public String organismTypeToString() {
        return "Guarana";
    }

    @Override
    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        Point tmpPozycja = this.getPosition();
        getWorld().removeOrganism(this);
        predator.makeMove(tmpPozycja);
        if(!predator.toString().equals("Human"))
            Narrator.addComment(predator.ToString() + " eats " + this.ToString() + "  its strength increases by 3");
        else Narrator.addComment(predator.ToString() + " eats " + this.ToString() + "  his strength increases by 3");
        predator.setStrength(predator.getStrength() + strengthIncrease);
        return true;
    }
}

