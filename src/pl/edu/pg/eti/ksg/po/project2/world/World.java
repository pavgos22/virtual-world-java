package pl.edu.pg.eti.ksg.po.project2.world;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

import pl.edu.pg.eti.ksg.po.project2.game.Narrator;
import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.model.plants.*;
import pl.edu.pg.eti.ksg.po.project2.model.animals.*;

public class World {
    private final int sizeX;
    private final int sizeY;
    private int roundNumber;
    private Organism[][] board;
    private boolean isHumanAlive;
    private boolean isGameOver;
    private boolean pause;
    private final ArrayList<Organism> organisms;
    private Human human;
    private WorldGUI worldGUI;

    public World(WorldGUI worldGUI) {
        this.sizeX = 0;
        this.sizeY = 0;
        roundNumber = 0;
        isHumanAlive = true;
        isGameOver = false;
        pause = true;
        organisms = new ArrayList<>();
        this.worldGUI = worldGUI;
    }

    public World(int sizeX, int sizeY, WorldGUI worldGUI) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        roundNumber = 0;
        isHumanAlive = true;
        isGameOver = false;
        pause = true;
        board = new Organism[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                board[i][j] = null;
            }
        }
        organisms = new ArrayList<>();
        this.worldGUI = worldGUI;
    }

    public static Organism createNewOrganism(OrganismType organismType, World world, Point position) {
        switch (organismType) {
            case WOLF:
                return new Wolf(world, position, world.getRoundNumber());
            case SHEEP:
                return new Sheep(world, position, world.getRoundNumber());
            case FOX:
                return new Fox(world, position, world.getRoundNumber());
            case TURTLE:
                return new Turtle(world, position, world.getRoundNumber());
            case ANTELOPE:
                return new Antelope(world, position, world.getRoundNumber());
            case HUMAN:
                return new Human(world, position, world.getRoundNumber());
            case GRASS:
                return new Grass(world, position, world.getRoundNumber());
            case DANDELION:
                return new Dandelion(world, position, world.getRoundNumber());
            case GUARANA:
                return new Guarana(world, position, world.getRoundNumber());
            case DEADLY_NIGHTSHADE:
                return new DeadlyNightshade(world, position, world.getRoundNumber());
            case GIANT_HOGWEED:
                return new GiantHogweed(world, position, world.getRoundNumber());
            default:
                return null;
        }
    }

    public void resetWorld() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++)
                board[i][j] = null;
        }

        organisms.clear();

        roundNumber = 0;
        isHumanAlive = true;
        isGameOver = false;
        human = null;
    }


    public void saveWorld(String fileName) {
        try {
            File saveDirectory = new File("save");
            if (!saveDirectory.exists()) {
                saveDirectory.mkdir();
            }

            fileName = "save/" + fileName + ".txt";
            File file = new File(fileName);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(sizeX + " ");
            pw.print(sizeY + " ");
            pw.print(roundNumber + " ");
            pw.print(isHumanAlive + " ");
            pw.print(isGameOver + "\n");
            for (Organism organism : organisms) {
                pw.print(organism.getTypOrganizmu() + " ");
                pw.print(organism.getPosition().getX() + " ");
                pw.print(organism.getPosition().getY() + " ");
                pw.print(organism.getStrength() + " ");
                pw.print(organism.getBirthRound() + " ");
                pw.print(organism.getDead());
                if (organism.getTypOrganizmu() == OrganismType.HUMAN) {
                    pw.print(" " + human.getPower().getDuration() + " ");
                    pw.print(human.getPower().getCooldown() + " ");
                    pw.print(human.getPower().getActive() + " ");
                    pw.print(human.getPower().getCanActivate());
                }
                pw.println();
            }
            pw.close();
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }
    }


    public static World loadWorld(String nameOfFile, WorldGUI worldGUI) {
        try {
            nameOfFile = "save/" + nameOfFile + ".txt";
            File file = new File(nameOfFile);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            World tmpWorld = new World(sizeX, sizeY, worldGUI);
            tmpWorld.resetWorld();

            tmpWorld.roundNumber = Integer.parseInt(properties[2]);
            tmpWorld.isHumanAlive = Boolean.parseBoolean(properties[3]);
            tmpWorld.isGameOver = Boolean.parseBoolean(properties[4]);
            tmpWorld.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                OrganismType organismType = OrganismType.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organism tmpOrganism = createNewOrganism(organismType, tmpWorld, new Point(x, y));
                int strength = Integer.parseInt(properties[3]);
                tmpOrganism.setStrength(strength);
                int birthRound = Integer.parseInt(properties[4]);
                tmpOrganism.setBirthRound(birthRound);
                boolean isDead = Boolean.parseBoolean(properties[5]);
                tmpOrganism.setDead(isDead);

                if (organismType == OrganismType.HUMAN) {
                    tmpWorld.human = (Human) tmpOrganism;
                    int duration = Integer.parseInt(properties[6]);
                    tmpWorld.human.getPower().setDuration(duration);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpWorld.human.getPower().setCooldown(cooldown);
                    boolean isActive = Boolean.parseBoolean(properties[8]);
                    tmpWorld.human.getPower().setActive(isActive);
                    boolean canActivate = Boolean.parseBoolean(properties[9]);
                    tmpWorld.human.getPower().setCanActivate(canActivate);
                }
                tmpWorld.addOrganism(tmpOrganism);
            }
            scanner.close();

            return tmpWorld;
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }
        return null;
    }


    public void generateWorld(double organismsNumber) {
        int organismsAmount = (int) Math.floor(sizeX * sizeY * organismsNumber);
        Point position = pickRandomFreeField();
        Organism tmpOrganism = createNewOrganism(OrganismType.HUMAN, this, position);
        addOrganism(tmpOrganism);
        human = (Human) tmpOrganism;
        for (int i = 0; i < organismsAmount - 1; i++) {
            position = pickRandomFreeField();
            if (!Objects.equals(position, new Point(-1, -1))) {
                addOrganism(createNewOrganism(Organism.pickRandomType(), this, position));
            } else return;
        }
    }

    public void executeTurn() {
        if (isGameOver) return;
        roundNumber++;
        Narrator.addComment("\nROUND " + roundNumber);
        System.out.println(roundNumber);
        System.out.println(organisms.size() + "\n");
        sortOrganisms();
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getBirthRound() != roundNumber && !organisms.get(i).getDead()) {
                organisms.get(i).action();
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getDead()) {
                organisms.remove(i);
                i--;
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setReproducing(false);
        }
    }

    private void sortOrganisms() {
        organisms.sort(new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.getInitiative() != o2.getInitiative())
                    return Integer.compare(o2.getInitiative(), o1.getInitiative());
                else
                    return Integer.compare(o1.getBirthRound(), o2.getBirthRound());
            }
        });
    }

    public Point pickRandomFreeField() {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (board[y][x] == null) return new Point(x, y);
                    }
                }
            }
        }
        return new Point(-1, -1);
    }

    public boolean isFieldFree(Point point) {
        return board[point.getY()][point.getX()] != null;
    }

    public Organism onField(Point point) {
        return board[point.getY()][point.getX()];
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
        board[organism.getPosition().getY()][organism.getPosition().getX()] = organism;
    }

    public void removeOrganism(Organism organism) {
        board[organism.getPosition().getY()][organism.getPosition().getX()] = null;
        organism.setDead(true);
        if (organism.getTypOrganizmu() == OrganismType.HUMAN) {
            isHumanAlive = false;
            human = null;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Organism[][] getBoard() {
        return board;
    }

    public boolean getHumanAlive() {
        return isHumanAlive;
    }

    public Human getHuman() {
        return human;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setWorldGUI(WorldGUI worldGUI) {
        this.worldGUI = worldGUI;
    }
}
