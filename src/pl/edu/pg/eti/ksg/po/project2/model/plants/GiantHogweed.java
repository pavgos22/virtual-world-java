package pl.edu.pg.eti.ksg.po.project2.model.plants;

import pl.edu.pg.eti.ksg.po.project2.enums.Direction;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Plant;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GiantHogweed extends Plant {
    private static final int giantHogweedStrength = 10;
    private static final int giantHogweedInitiative = 0;

    public GiantHogweed(World world, Point pozycja, int turaUrodzenia) {

        super(OrganismType.GIANT_HOGWEED, world, pozycja,
                turaUrodzenia, giantHogweedStrength, giantHogweedInitiative);
        setColor(new Color(164, 0, 223));
        ImageIcon icon = new ImageIcon("src/images/giant_hogweed-icon.png");
        setIcon(icon);
        setReproductionChance(0.05);
    }

    @Override
    public void action() {
        int pozX = getPosition().getX();
        int pozY = getPosition().getY();
        pickRandomField(getPosition());
        for (int i = 0; i < 8; i++) {
            Organism tmpOrganism = null;
            if (i == 0 && !isDirectionBlocked(Direction.DOWN))
                tmpOrganism = getWorld().onField(new Point(pozX, pozY + 1));
            else if (i == 1 && !isDirectionBlocked(Direction.UP))
                tmpOrganism = getWorld().onField(new Point(pozX, pozY - 1));
            else if (i == 2 && !isDirectionBlocked(Direction.LEFT))
                tmpOrganism = getWorld().onField(new Point(pozX - 1, pozY));
            else if (i == 3 && !isDirectionBlocked(Direction.RIGHT))
                tmpOrganism = getWorld().onField(new Point(pozX + 1, pozY));

            if (tmpOrganism != null && tmpOrganism.isAnimal()) {
                getWorld().removeOrganism(tmpOrganism);
                Narrator.addComment(ToString() + " kills " + tmpOrganism.ToString());
            }
        }
        Random rand = new Random();
        int tmpRand = rand.nextInt(100);
        if (tmpRand < getReproductionChance() * 100) spreading();
    }

    @Override
    public String organismTypeToString() {
        return "Giant Hogweed";
    }

    @Override
    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        if (predator.getStrength() >= 10) {
            getWorld().removeOrganism(this);
            Narrator.addComment(predator.ToString() + " eats " + this.ToString());
            predator.makeMove(prey.getPosition());
        }
        if ((predator.isAnimal())
                || predator.getStrength() < 10) {
            getWorld().removeOrganism(predator);
            Narrator.addComment(predator.toString() + " fell into " + this.ToString() + " [instant death]");
        }
        return true;
    }
}
