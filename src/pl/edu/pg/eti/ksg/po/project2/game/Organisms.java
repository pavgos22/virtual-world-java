package pl.edu.pg.eti.ksg.po.project2.game;

import pl.edu.pg.eti.ksg.po.project2.enums.OrganismType;
import pl.edu.pg.eti.ksg.po.project2.model.Organism;
import pl.edu.pg.eti.ksg.po.project2.world.Point;
import pl.edu.pg.eti.ksg.po.project2.world.World;

import javax.swing.*;

public class Organisms extends JFrame {
    private final String[] organismList;
    private final OrganismType[] organismTypeList;
    private final JList<String> jList;

    public Organisms(int x, int y, Point point, World world, Runnable refreshWorld) {
        super("Organisms list");
        setBounds(x, y, 100, 300);
        organismList = new String[]{"Giant Hogweed", "Guarana", "Dandelion", "Grass",
                "Deadly Nightshade", "Antelope", "Fox", "Sheep", "Wolf", "Turtle"};

        organismTypeList = new OrganismType[]{
                OrganismType.GIANT_HOGWEED,
                OrganismType.GUARANA,
                OrganismType.DANDELION,
                OrganismType.GRASS,
                OrganismType.DEADLY_NIGHTSHADE,
                OrganismType.ANTELOPE,
                OrganismType.FOX,
                OrganismType.SHEEP,
                OrganismType.WOLF,
                OrganismType.TURTLE
        };

        jList = new JList<>(organismList);
        jList.setVisibleRowCount(organismList.length);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(event -> {
            Organism tmpOrganism = World.createNewOrganism(organismTypeList[jList.getSelectedIndex()], world, point);
            world.addOrganism(tmpOrganism);
            Narrator.addComment("New organism created " + tmpOrganism.ToString());
            refreshWorld.run();
            dispose();
        });

        JScrollPane sp = new JScrollPane(jList);
        add(sp);

        setVisible(true);
    }
}
