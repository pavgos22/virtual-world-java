package pl.edu.pg.eti.ksg.po.project2.model.animals;

import pl.edu.pg.eti.ksg.po.project2.enums.Direction;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.game.Power;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.Animal;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Human extends Animal {
    private static final int humanMoveRange = 1;
    private static final int humanMoveChance = 1;
    private static final int humanStrength = 5;
    private static final int humanInitiative = 4;
    private Direction moveDirection;
    private final Power power;

    public Human(World world, Point position, int birthRound) {
        super(OrganismType.HUMAN, world, position, birthRound, humanStrength, humanInitiative);
        this.setMoveRange(humanMoveRange);
        this.setMoveChance(humanMoveChance);
        moveDirection = Direction.NO_DIRECTION;
        setColor(new Color(0, 0, 0));
        ImageIcon icon = new ImageIcon("src/images/human-icon.png");
        setIcon(icon);
        power = new Power();
    }

    private void AlzurShield() {
        pickRandomField(getPosition());
        int x = getPosition().getX();
        int y = getPosition().getY();
        Organism tmpOrganism = null;
        for (int i = 0; i < 4; i++) {
            if (i == 0 && !isDirectionBlocked(Direction.DOWN))
                tmpOrganism = getWorld().onField(new Point(x, y + 1));
            else if (i == 1 && !isDirectionBlocked(Direction.UP))
                tmpOrganism = getWorld().onField(new Point(x, y - 1));
            else if (i == 2 && !isDirectionBlocked(Direction.LEFT))
                tmpOrganism = getWorld().onField(new Point(x - 1, y));
            else if (i == 3 && !isDirectionBlocked(Direction.RIGHT))
                tmpOrganism = getWorld().onField(new Point(x + 1, y));

            if (tmpOrganism != null && tmpOrganism.isAnimal()) {
                int tmpX = tmpOrganism.getPosition().getX();
                int tmpY = tmpOrganism.getPosition().getY();
                Point tmpPoint = new Point(tmpX, tmpY);
                if (tmpOrganism.getPosition() != this.getPosition() && !Objects.equals(tmpOrganism.getPosition(), tmpPoint)) {
                    tmpOrganism.action();
                    Narrator.addComment(ToString() + " with ability 'Alzur's Shield' deters "
                            + tmpOrganism.ToString() + "from field (" + tmpX + ", " + tmpY + ") to field (" + tmpOrganism.getPosition().getX() + ", " + tmpOrganism.getPosition().getY() + ")");
                }
            }
        }
    }

    @Override
    protected Point planMove() {
        int x = getPosition().getX();
        int y = getPosition().getY();
        pickRandomField(getPosition());
        if (moveDirection == Direction.NO_DIRECTION ||
                isDirectionBlocked(moveDirection)) return getPosition();
        else {
            if (moveDirection == Direction.DOWN) return new Point(x, y + 1);
            if (moveDirection == Direction.UP) return new Point(x, y - 1);
            if (moveDirection == Direction.LEFT) return new Point(x - 1, y);
            if (moveDirection == Direction.RIGHT) return new Point(x + 1, y);
            else return new Point(x, y);
        }
    }

    @Override
    public void action() {
        if (power.getActive()) {
            Narrator.addComment(ToString() + " 'Alzur's shield' is active ("
                    + power.getDuration() + " rounds left)");
            AlzurShield();
        }
        for (int i = 0; i < getMoveRange(); i++) {
            Point nextPos = planMove();

            if (getWorld().isFieldFree(nextPos)
                    && getWorld().onField(nextPos) != this) {
                collision(getWorld().onField(nextPos));
                break;
            } else if (getWorld().onField(nextPos) != this) makeMove(nextPos);
            if (power.getActive()) AlzurShield();
        }
        moveDirection = Direction.NO_DIRECTION;
        power.checkConditions();
    }

    @Override
    public String organismTypeToString() {
        return "Human";
    }

    @Override
    public String toString() {
        return "Human";
    }

    public Power getPower() {
        return power;
    }

    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }
}
