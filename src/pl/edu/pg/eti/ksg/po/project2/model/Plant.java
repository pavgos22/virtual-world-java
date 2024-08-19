package pl.edu.pg.eti.ksg.po.project2.model;

import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;

import java.util.Random;

public abstract class Plant extends Organism {

    protected Plant(OrganismType organismType, World world,
                    Point position, int birthRound, int strength, int initiative) {
        super(organismType, world, position, birthRound, strength, initiative);
        setReproductionChance(0.3);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpRand = rand.nextInt(upperbound);
        if (tmpRand < getReproductionChance() * 100) spreading();
    }

    @Override
    public boolean isAnimal() {
        return false;
    }


    protected void spreading() {
        Point tmp1Point = this.pickRandomFreeField(getPosition());
        if (tmp1Point.equals(getPosition())) return;
        else {
            Organism tmpOrganism = World.createNewOrganism(getTypOrganizmu(), this.getWorld(), tmp1Point);
            Narrator.addComment("As an effect of spreading a new plant has grown " + tmpOrganism.ToString());
            getWorld().addOrganism(tmpOrganism);
        }
    }

    @Override
    public void collision(Organism other) {
    }
}
