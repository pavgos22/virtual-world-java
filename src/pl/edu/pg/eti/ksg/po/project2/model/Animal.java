package pl.edu.pg.eti.ksg.po.project2.model;

import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;

import java.util.Random;

public abstract class Animal extends Organism {
    private int moveRange;
    private double moveChance;

    public Animal(OrganismType organismType, World world,
                  Point position, int birthDate, int strength, int initiative) {
        super(organismType, world, position, birthDate, strength, initiative);
        setReproducing(false);
        setReproductionChance(0.5);
    }

    @Override
    public void action() {
        for (int i = 0; i < moveRange; i++) {
            Point nextPos = planMove();

            if (getWorld().isFieldFree(nextPos)
                    && getWorld().onField(nextPos) != this) {
                collision(getWorld().onField(nextPos));
                break;
            } else if (getWorld().onField(nextPos) != this) makeMove(nextPos);
        }
    }

    @Override
    public void collision(Organism other) {
        if (getTypOrganizmu() == other.getTypOrganizmu()) {
            Random rand = new Random();
            int tmpRand = rand.nextInt(100);
            if (tmpRand < getReproductionChance() * 100) reproduction(other);
        } else {
            if (other.specialBehaviorWhileAttacking(this, other)) return;
            if (specialBehaviorWhileAttacking(this, other)) return;

            if (getStrength() >= other.getStrength()) {
                getWorld().removeOrganism(other);
                makeMove(other.getPosition());
                if (other.isAnimal()) {
                    Narrator.addComment(ToString() + " kills " + other.ToString());
                } else {
                    Narrator.addComment(ToString() + " eats " + other.ToString());
                }
            } else {
                getWorld().removeOrganism(this);
                Narrator.addComment(other.ToString() + (this.isAnimal() ? " kills " : " eats ") + ToString());
            }
        }
    }


    @Override
    public boolean isAnimal() {
        return true;
    }

    protected Point planMove() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpRand = rand.nextInt(upperbound);
        if (tmpRand >= (int) (moveChance * 100)) return getPosition();
        else return pickRandomField(getPosition());
    }

    private void reproduction(Organism other) {
        if (this.getReproducing() || other.getReproducing()) return;
        Point tmp1Point = this.pickRandomFreeField(getPosition());
        if (tmp1Point.equals(getPosition())) {
            Point tmp2Point = other.pickRandomFreeField(other.getPosition());
            if (tmp2Point.equals(other.getPosition())) return;
            else {
                Organism tmpOrganism = World.createNewOrganism(getTypOrganizmu(), this.getWorld(), tmp2Point);
                Narrator.addComment("As an effect of reproduction a new animal was born: " + tmpOrganism.ToString());
                getWorld().addOrganism(tmpOrganism);
                setReproducing(true);
                other.setReproducing(true);
            }
        } else {
            Organism tmpOrganism = World.createNewOrganism(getTypOrganizmu(), this.getWorld(), tmp1Point);
            Narrator.addComment("As an effect of reproduction a new animal was born: " + tmpOrganism.ToString());
            getWorld().addOrganism(tmpOrganism);
            setReproducing(true);
            other.setReproducing(true);
        }
    }

    public int getMoveRange() {
        return moveRange;
    }

    public void setMoveRange(int moveRange) {
        this.moveRange = moveRange;
    }

    public double getMoveChance() {
        return moveChance;
    }

    public void setMoveChance(double moveChance) {
        this.moveChance = moveChance;
    }
}
