package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.Direction;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.awt.*;

public class Fox extends Animal {
    private static final int foxMoveRange = 1;
    private static final int foxMoveChance = 1;
    private static final int foxStrength = 3;
    private static final int foxInitiative = 7;

    public Fox(World world, Point position, int birthRound) {
        super(OrganismType.FOX, world, position, birthRound, foxStrength, foxInitiative);
        this.setMoveRange(foxMoveRange);
        this.setMoveChance(foxMoveChance);
        setColor(new Color(255, 106, 31));
        ImageIcon icon = new ImageIcon("src/images/fox-icon.png");
        setIcon(icon);
    }

    @Override
    public Point pickRandomField(Point position) {
        unlockAllDirections();
        int pozX = position.getX();
        int pozY = position.getY();
        int sizeX = getWorld().getSizeX();
        int sizeY = getWorld().getSizeY();
        int possibleDirections = 0;
        Organism tmpOrganism;

        if (pozX == 0) blockDirection(Direction.LEFT);
        else {
            tmpOrganism = getWorld().getBoard()[pozY][pozX - 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                blockDirection(Direction.LEFT);
            } else possibleDirections++;
        }

        if (pozX == sizeX - 1) blockDirection(Direction.RIGHT);
        else {
            tmpOrganism = getWorld().getBoard()[pozY][pozX + 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                blockDirection(Direction.RIGHT);
            } else possibleDirections++;
        }

        if (pozY == 0) blockDirection(Direction.UP);
        else {
            tmpOrganism = getWorld().getBoard()[pozY - 1][pozX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                blockDirection(Direction.UP);
            } else possibleDirections++;
        }

        if (pozY == sizeY - 1) blockDirection(Direction.DOWN);
        else {
            tmpOrganism = getWorld().getBoard()[pozY + 1][pozX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) {
                blockDirection(Direction.DOWN);
            } else possibleDirections++;
        }

        if (possibleDirections == 0) return new Point(pozX, pozY);

        Point newField;
        while ((newField = pickNewField(pozX, pozY)) == null) {}
        return newField;
    }

    @Override
    public String organismTypeToString() {
        return "Fox";
    }

    @Override
    public String toString() {
        return "Fox";
    }

}
