package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.util.Random;
import java.awt.*;

public class Antelope extends Animal {
    private static final int antelopeMoveRange = 2;
    private static final int antelopeMoveChance = 1;
    private static final int antelopeStrength = 4;
    private static final int antelopeInitiative = 4;


    public Antelope(World world, Point position, int birthRound) {
        super(OrganismType.ANTELOPE, world, position, birthRound, antelopeStrength, antelopeInitiative);
        this.setMoveRange(antelopeMoveRange);
        this.setMoveChance(antelopeMoveChance);
        ImageIcon icon = new ImageIcon("src/images/antelope-icon.png");
        setColor(new Color(138, 63, 48));
        setIcon(icon);
    }

    @Override
    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        Random rand = new Random();
        int tmpRand = rand.nextInt(100);
        if (tmpRand < 50) {
            if (this == predator) {
                Narrator.addComment(ToString() + " escapes from " + prey.ToString());
                Point tmpPos = pickRandomFreeField(prey.getPosition());
                if (!tmpPos.equals(prey.getPosition()))
                    makeMove(tmpPos);
            } else if (this == prey) {
                Narrator.addComment(ToString() + " escapes from " + predator.ToString());
                Point tmpPos = this.getPosition();
                makeMove(pickRandomFreeField(this.getPosition()));
                if (getPosition().equals(tmpPos)) {
                    getWorld().removeOrganism(this);
                    Narrator.addComment(predator.ToString() + " kills " + ToString());
                }
                predator.makeMove(tmpPos);
            }
            return true;
        } else return false;
    }

    @Override
    public String organismTypeToString() {
        return "Antelope";
    }

    @Override
    public String toString() {
        return "Antelope";
    }
}
