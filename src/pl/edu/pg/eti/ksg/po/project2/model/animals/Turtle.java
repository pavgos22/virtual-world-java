package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.awt.*;

public class Turtle extends Animal {
    private static final int turtleMoveRange = 1;
    private static final double turtleMoveChance = 0.25;
    private static final int turtleStrength = 2;
    private static final int turtleInitiative = 1;

    public Turtle(World world, Point position, int birthRound) {
        super(OrganismType.TURTLE, world, position, birthRound, turtleStrength, turtleInitiative);
        this.setMoveRange(turtleMoveRange);
        this.setMoveChance(turtleMoveChance);
        setColor(new Color(25, 114, 10));
        ImageIcon icon = new ImageIcon("src/images/turtle-icon.png");
        setIcon(icon);
    }

    @Override
    public String organismTypeToString() {
        return "Turtle";
    }

    @Override
    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        if (this == prey) {
            if (predator.getStrength() < 5 && predator.isAnimal()) {
                Narrator.addComment(ToString() + " uses its shell to repel the attack of " + predator.ToString());
                return true;
            } else return false;
        } else {
            if (predator.getStrength() >= prey.getStrength()) return false;
            else {
                if (prey.getStrength() < 5 && prey.isAnimal()) {
                    Narrator.addComment(ToString() + " uses its shell to repel the attack of " + prey.ToString());
                    return true;
                } else return false;
            }
        }
    }

    @Override
    public String toString() {
        return "Turtle";
    }
}
