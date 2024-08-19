package pl.edu.pg.eti.ksg.po.project2.model;

import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.enums.Direction;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Organism {

    private int strength;
    private int initiative;
    private int birthRound;
    private Color color;
    private ImageIcon icon;
    private boolean isDead;
    private final boolean[] direction;
    private boolean isReproducing;
    private final World world;
    private Point position;
    private final OrganismType organismType;
    private double reproductionChance;
    private static final int speciesAmount = 12;

    public abstract String organismTypeToString();

    public abstract void action();

    public abstract void collision(Organism other);

    public abstract boolean isAnimal();

    public Organism(OrganismType organismType, World world,
                    Point position, int birthRound, int strength, int initiative) {
        this.organismType = organismType;
        this.world = world;
        this.position = position;
        this.birthRound = birthRound;
        this.strength = strength;
        this.initiative = initiative;
        isDead = false;
        direction = new boolean[] {true, true, true, true};
    }

    public boolean specialBehaviorWhileAttacking(Organism predator, Organism prey) {
        return false;
    }

    public String ToString() {
        return (organismTypeToString() + " (" + position.getX() + ", "
                + position.getY() + ")");
    }

    public void makeMove(Point nextPos) {
        int x = nextPos.getX();
        int y = nextPos.getY();
        world.getBoard()[position.getY()][position.getX()] = null;
        world.getBoard()[y][x] = this;
        position.setX(x);
        position.setY(y);
    }

    public static OrganismType pickRandomType() {
        Random rand = new Random();
        int tmp = rand.nextInt(speciesAmount - 1);
        if (tmp == 0) return OrganismType.ANTELOPE;
        if (tmp == 1) return OrganismType.SHEEP;
        if (tmp == 2) return OrganismType.FOX;
        if (tmp == 3) return OrganismType.TURTLE;
        if (tmp == 4) return OrganismType.WOLF;
        if (tmp == 5) return OrganismType.GRASS;
        if (tmp == 6) return OrganismType.DANDELION;
        if (tmp == 7) return OrganismType.GUARANA;
        if (tmp == 8) return OrganismType.GIANT_HOGWEED;
        else return OrganismType.DEADLY_NIGHTSHADE;
    }

    public Point pickRandomField(Point position) {
        unlockAllDirections();
        int pozX = position.getX();
        int pozY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int possibleDirections = 0;

        if (pozX == 0) blockDirection(Direction.LEFT);
        else possibleDirections++;
        if (pozX == sizeX - 1) blockDirection(Direction.RIGHT);
        else possibleDirections++;
        if (pozY == 0) blockDirection(Direction.UP);
        else possibleDirections++;
        if (pozY == sizeY - 1) blockDirection(Direction.DOWN);
        else possibleDirections++;

        if (possibleDirections == 0) return position;
        while (true) {
            Point newPoint = pickNewField(pozX, pozY);
            if (newPoint != null) {
                return newPoint;
            }
        }
    }

    protected Point pickNewField(int pozX, int pozY) {
        Random rand = new Random();
        int tmpRand = rand.nextInt(100);
        if (tmpRand < 25 && !isDirectionBlocked(Direction.LEFT))
            return new Point(pozX - 1, pozY);
        else if (tmpRand >= 25 && tmpRand < 50 && !isDirectionBlocked(Direction.RIGHT))
            return new Point(pozX + 1, pozY);
        else if (tmpRand >= 50 && tmpRand < 75 && !isDirectionBlocked(Direction.UP))
            return new Point(pozX, pozY - 1);
        else if (tmpRand >= 75 && !isDirectionBlocked(Direction.DOWN))
            return new Point(pozX, pozY + 1);
        return null;
    }

    public Point pickRandomFreeField(Point pos) {
        unlockAllDirections();
        int pozX = pos.getX();
        int pozY = pos.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int possibleDirections = 0;

        if (pozX == 0) blockDirection(Direction.LEFT);
        else {
            if (!world.isFieldFree(new Point(pozX - 1, pozY))) possibleDirections++;
            else blockDirection(Direction.LEFT);
        }

        if (pozX == sizeX - 1) blockDirection(Direction.RIGHT);
        else {
            if (!world.isFieldFree(new Point(pozX + 1, pozY))) possibleDirections++;
            else blockDirection(Direction.RIGHT);
        }

        if (pozY == 0) blockDirection(Direction.UP);
        else {
            if (!world.isFieldFree(new Point(pozX, pozY - 1))) possibleDirections++;
            else blockDirection(Direction.UP);
        }

        if (pozY == sizeY - 1) blockDirection(Direction.DOWN);
        else {
            if (!world.isFieldFree(new Point(pozX, pozY + 1))) possibleDirections++;
            else blockDirection(Direction.DOWN);
        }

        if (possibleDirections == 0) return new Point(pozX, pozY);
        while (true) {
            Point newPoint = pickNewField(pozX, pozY);
            if (newPoint != null && !world.isFieldFree(newPoint)) {
                return newPoint;
            }
        }
    }

    protected void blockDirection(Direction direction) {
        this.direction[direction.getValue()] = false;
    }

    protected void unlockDirection(Direction direction) {
        this.direction[direction.getValue()] = true;
    }

    protected void unlockAllDirections() {
        unlockDirection(Direction.LEFT);
        unlockDirection(Direction.RIGHT);
        unlockDirection(Direction.UP);
        unlockDirection(Direction.DOWN);
    }

    protected boolean isDirectionBlocked(Direction direction) {
        return !(this.direction[direction.getValue()]);
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getBirthRound() {
        return birthRound;
    }

    public boolean getDead() {
        return isDead;
    }

    public boolean getReproducing() {
        return isReproducing;
    }

    public World getWorld() {
        return world;
    }

    public Point getPosition() {
        return position;
    }

    public OrganismType getTypOrganizmu() {
        return organismType;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setBirthRound(int birthRound) {
        this.birthRound = birthRound;
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    public void setReproducing(boolean reproducing) {
        this.isReproducing = reproducing;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public double getReproductionChance() {
        return reproductionChance;
    }

    public void setReproductionChance(double reproductionChance) {
        this.reproductionChance = reproductionChance;
    }
}